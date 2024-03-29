package de.zookeeper.leaderelection;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class LeaderElection implements Watcher {

    private static final String ZOOKEEPER_ADDRESS = "localhost:2181";
    private ZooKeeper zooKeeper;
    private static final int SESSION_TIMEOUT = 3000;
    private static final String ELECTION_NAMESPACE = "/election";
    private String currentZNodeName;


    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    System.out.println("Successfully connected to zookeeper!!");
                } else {
                    synchronized (zooKeeper) {
                        System.out.println("Disconnected from zookeeper event");
                        zooKeeper.notifyAll();
                    }
                }
                break;
            case NodeDeleted:
                try {
                    reelectLeader();
                } catch (KeeperException | InterruptedException e) {
                }

                System.out.println(ELECTION_NAMESPACE + " got deleted!!");
            case NodeCreated:
                System.out.println(ELECTION_NAMESPACE + " got created!!");
            case NodeDataChanged:
                System.out.println(ELECTION_NAMESPACE + " data changed!!");
            case NodeChildrenChanged:
                System.out.println(ELECTION_NAMESPACE + " children changed!!");
            default:
                break;
        }

        try {
            watchTargetZNode();
        } catch (KeeperException e) {
        } catch (InterruptedException e) {
        }
    }

    private void watchTargetZNode() throws InterruptedException, KeeperException {
        Stat stat = zooKeeper.exists(ELECTION_NAMESPACE, this);

        if (stat == null) {
            return;
        }

        byte[] data = zooKeeper.getData(ELECTION_NAMESPACE, this, stat);
        List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, this);

        System.out.println("Data: " + new String(data) + " Children: " + children);
    }

    public void connectToZookeeper() throws IOException {
        this.zooKeeper = new ZooKeeper(ZOOKEEPER_ADDRESS, SESSION_TIMEOUT, this);
    }

    public void volunteerForLeadership() throws InterruptedException, KeeperException {
        String zNodePrefix = ELECTION_NAMESPACE + "/c_";
        // my_path = /election/c_0000001 (EPHEMERAL_SEQUENTIAL)
        String zNodeFullPath = zooKeeper.create(zNodePrefix, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println("znode_name: " + zNodeFullPath);
        this.currentZNodeName = zNodeFullPath.replace(ELECTION_NAMESPACE + "/", "");
    }

    public void reelectLeader() throws InterruptedException, KeeperException {
        Stat predecessorStat = null;
        String predecessorZnodeName = "";

        while (predecessorStat == null) {
            List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
            Collections.sort(children);

            String smallestChild = children.get(0);

            if (smallestChild.equals(currentZNodeName)) {
                System.out.println("I'm the leader!!");
            } else {
                System.out.println("I'm not the leader ");

                int predecessorIndex = Collections.binarySearch(children, currentZNodeName) - 1;
                predecessorZnodeName = children.get(predecessorIndex);
                predecessorStat = zooKeeper.exists(ELECTION_NAMESPACE + "/" + predecessorZnodeName, this);
            }
        }

        System.out.println("Watching node: " + predecessorZnodeName);
        System.out.println();
    }

    public void run() throws InterruptedException {
        synchronized (zooKeeper) {
            zooKeeper.wait();
        }
    }

    public void close() throws InterruptedException {
        zooKeeper.close();
    }
}

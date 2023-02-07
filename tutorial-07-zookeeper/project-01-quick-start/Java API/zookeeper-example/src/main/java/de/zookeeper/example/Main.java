package de.zookeeper.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        ZooKeeper zk = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            int retryCnt = 0;

            @Override
            public void process(WatchedEvent event) {
                if(event.getState() == Event.KeeperState.SyncConnected){
                    System.out.println("Connected to zookeeper!!!");
                }
                if(event.getState() == Event.KeeperState.Disconnected || event.getState() == Event.KeeperState.AuthFailed) {
                    if (retryCnt > 3){
                        return;
                    }
                    retryCnt++;
                    //retry
                }
            }
        });

        Thread.sleep(5000);


        // create
//        String rootPath = zk.create("/export", "root".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        System.out.println("root Path: " + rootPath);

        String rootPath = "/export";

        //update
        int latestVersion = zk.exists(rootPath, true).getVersion();
        zk.setData(rootPath, "root2".getBytes(StandardCharsets.UTF_8), latestVersion);

        // get
        Stat stat = new Stat();
        byte[] result = zk.getData(rootPath, null, stat);
        System.out.println("The value of " + rootPath + " = " + new String(result));
        System.out.println("The stat of " + rootPath + " = " + stat);

        //addWatch
        zk.addWatch(rootPath, AddWatchMode.PERSISTENT);


    }
}

package de.zookeeper.leaderelection;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        LeaderElection leaderElection = new LeaderElection();
        leaderElection.connectToZookeeper();
        leaderElection.volunteerForLeadership();
        leaderElection.reelectLeader();
        leaderElection.run();
        leaderElection.close();

        System.out.println("Disconnected from zooKeeper, exiting application!");
    }
}

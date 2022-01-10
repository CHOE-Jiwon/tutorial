public class ZooKeeper {

    public void feed(Predetor predetor) {
        System.out.println(predetor.getFood());
    }

    public static void main(String[] args) {
        ZooKeeper zooKeeper = new ZooKeeper();
        Tiger tiger = new Tiger();
        Lion lion = new Lion();
        zooKeeper.feed(tiger);
        zooKeeper.feed(lion);
    }
}
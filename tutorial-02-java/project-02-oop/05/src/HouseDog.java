public class HouseDog extends Dog {
    public void sleep(){
        System.out.println(this.name + " Zzz in house.");
    }

    public void sleep(int hour) {
        System.out.println(this.name + " Zzz in house for " + hour + " hours.");
    }

    public static void main(String[] args){
        HouseDog houseDog = new HouseDog();
        houseDog.setName("Areum-e");
        houseDog.sleep();
        houseDog.sleep(5);
    }
}
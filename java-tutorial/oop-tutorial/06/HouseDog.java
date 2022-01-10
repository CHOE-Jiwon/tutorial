public class HouseDog extends Dog {
    public void sleep(){
        System.out.println(this.name + " Zzz in house.");
    }

    public void sleep(int hour) {
        System.out.println(this.name + " Zzz in house for " + hour + " hours.");
    }

    public HouseDog(String name) {
        this.name = name;
    }

    public HouseDog(int type) {
        if (type == 1) {
            this.setName("yorkshire");
        } else if (type == 2) {
            this.setName("bulldog");
        }
    }

    public static void main(String[] args){
        HouseDog houseDog = new HouseDog("Areum-e");
        HouseDog houseDog2 = new HouseDog(2);

        System.out.println(houseDog.name);
        System.out.println(houseDog2.name);
    }
}
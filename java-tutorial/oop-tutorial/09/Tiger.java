public class Tiger extends Predator implements Barkable{
    public String getFood() {
        return "Apple";
    }

    public void bark() {
        System.out.println("어흥");
    }
}
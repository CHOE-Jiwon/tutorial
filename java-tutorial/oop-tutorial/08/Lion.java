public class Lion extends Animal implements BarkablePredator{
    public String getFood() {
        return "Banana";
    }

    public void bark(){
        System.out.println("으르렁");
    }
}
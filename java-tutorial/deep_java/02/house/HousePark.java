package house;

public class HousePark {
    protected String lastname = "park";
    public String info = "this is public message.";

    public static void main(String[] args){
        HouseKim kim = new HouseKim();
        System.out.println(kim.lastname);
    }
}
public class Dog extends Animal {
    public Dog() {

    }

    public void sleep(){
        System.out.println("Zzz...");
    }
    
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.setName("Areum-e");
        System.out.println(dog.name);
        dog.sleep();
    }
}
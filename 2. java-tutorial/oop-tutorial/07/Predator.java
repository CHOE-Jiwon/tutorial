public interface Predator {
    public String getFood();

    default void printFood() {
        System.out.printf("my food is %s\n", getFood());
    }

    int LEG_COUNT = 4;  // 인터페이스 상수
    static int speed() {
        return LEG_COUNT*30;
    }
}
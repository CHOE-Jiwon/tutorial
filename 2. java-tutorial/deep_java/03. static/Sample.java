import java.text.SimpleDateFormat;
import java.util.Date;

class Singleton {
    private static Singleton one;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (one == null) {
            one = new Singleton();
        }
        return one;
    }
}

class Util {
    public static String getCurrentDate(String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(new Date());
    }
}

class Counter {
    static int count = 0;

    Counter() {
        this.count++;
        System.out.println(this.count);
    }

    public static int getCount() {
        return count;
    }
}

class HouseLee {
    static String lastname = "이";
}

public class Sample {
    public static void main(String[] args) {
        // HouseLee lee1 = new HouseLee();
        // HouseLee lee2 = new HouseLee();

        // Counter c1 = new Counter();
        // Counter c2 = new Counter();

        // System.out.println(Counter.getCount());

        // System.out.println(Util.getCurrentDate("yyyyMMdd"));

        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();

        System.out.println(singleton1 == singleton2);
    }
}
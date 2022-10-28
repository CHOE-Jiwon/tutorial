class FoolException extends Exception {

}

public class Sample {
    public void shouldBeRun() {
        System.out.println("ok thanks.");
    }

    public void sayNick(String nick) throws FoolException {
        if ("fool".equals(nick)) {
            throw new FoolException();
        }
        System.out.println("당신의 별명은 " + nick + "입니다.");
    }

    public static void main(String[] args) {
        Sample sample = new Sample();

        try{
            sample.sayNick("fool");
            sample.sayNick("genious");
        } catch (FoolException e){
            System.err.println("FoolException is occured in main");
        }

    }
}
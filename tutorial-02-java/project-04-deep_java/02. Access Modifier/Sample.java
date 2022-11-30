public class Sample {
    private String secret;

    private String getSecret() {
        return this.secret;
    }

    private void setSecret() {
        this.secret = "A Secret makes a woman woman.";
    }

    public static void main(String[] args){
        Sample sample = new Sample();

        sample.setSecret();

        System.out.println(sample.getSecret());
    }
}
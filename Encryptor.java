import java.util.Base64;

public class Encryptor {
    public static void main(String[] args) {
        String data = "devops message";
        String encoded = Base64.getEncoder().encodeToString(data.getBytes());
        System.out.println("Encoded message: " + encoded);
    }
}


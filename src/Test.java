import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.*;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) throws Exception {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // KeySize
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("assets/bidon.txt"));

        byte[] data = bufferedInputStream.readAllBytes();

        bufferedInputStream.close();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);
        sig.update(data);
        byte[] signature = sig.sign();

        System.out.println(Arrays.toString(signature));


        Signature sign2 = Signature.getInstance("SHA256withRSA");
        sign2.initVerify(publicKey);
        sign2.update(data);

        System.out.print(sign2.verify(signature));
    }
}

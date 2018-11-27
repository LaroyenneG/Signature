import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

public class SignatureTools {

    private List<PublicKey> publicKeys;

    private char[] password;
    private File file;
    private String type;
    private String distinguishedName;

    public SignatureTools(String filePath, char[] password, String type, String distinguishedName) {
        this.file = new File(filePath);
        this.password = password;
        this.type = type;
        this.distinguishedName = distinguishedName;
        publicKeys = new ArrayList<>();
    }

    public static void main(String[] args) {
        System.out.println("hello");
    }

    public boolean verify(String fileName, byte[] signature) {
        return true;
    }

    public void readFile() {

        try {

            FileInputStream stream = new FileInputStream(file);

            KeyStore ks = KeyStore.getInstance(type);

            ks.load(stream, password);

            stream.close();
        } catch (IOException | KeyStoreException | CertificateException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

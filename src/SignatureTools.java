import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.PublicKey;
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
        publicKeys = null;
    }

    public void readFile() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));


            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }

    public boolean verify(String fileName, byte[] signature) {
        return true;
    }
}

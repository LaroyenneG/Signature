import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SignatureTools {

    private List<PublicKey> publicKeys;

    public SignatureTools(String filePath, char[] password, String type, String distinguishedName) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        publicKeys = new ArrayList<>();

        loadPublicKeys(new File(filePath), password, type, distinguishedName);

    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage : SignatureTools <file name>");
            System.exit(-1);
        }

        String path = args[0];

        try {

            SignatureTools signatureTools = new SignatureTools(path, "password".toCharArray(), "JCEKS", "dfvfd");

            System.out.println(signatureTools);

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            e.printStackTrace();
        }
    }

    //public boolean verify(String fileName, byte[] signature) {


    //}

    private void loadPublicKeys(File file, char[] password, String type, String DN) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {

        FileInputStream stream = new FileInputStream(file);

        KeyStore ks = KeyStore.getInstance(type);

        ks.load(stream, password);

        stream.close();

        Enumeration<String> aliases = ks.aliases();

        while (aliases.hasMoreElements()) {

            String alias = aliases.nextElement();

            if (ks.isCertificateEntry(alias) || ks.entryInstanceOf(alias, KeyStore.PrivateKeyEntry.class)) {

                Certificate cert = ks.getCertificate(alias);

                if (cert instanceof X509Certificate) {
                    if (((X509Certificate) cert).getSubjectDN().toString().equals(DN)) {
                        PublicKey pbk = cert.getPublicKey();
                        this.publicKeys.add(pbk);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return publicKeys.toString();
    }
}

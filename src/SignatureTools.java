import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
public class SignatureTools {

    public static final String TYPE = "JCEKS";

    private static final String SUN = "SUN";
    private static final String SUN_EC = "SunEC";
    private static final String SHA256_WITH_DSA = "SHA256withDSA";
    private static final String SHA256_WITH_ECDSA = "SHA256withECDSA";
    private static final String SHA256_WITT_RSA = "SHA256withRSA";

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

            SignatureTools signatureTools = new SignatureTools(path, "azerty".toCharArray(), TYPE, "CN=Patrick Guichet, OU=FST, O=UHA, L=Mulhouse, ST=68093, C=FR");

            System.out.println(signatureTools);

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            e.printStackTrace();
        }
    }


    public boolean verify(String fileName, byte[] signature) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName));

        byte[] fileBytes = bufferedInputStream.readAllBytes();

        bufferedInputStream.close();

        for (PublicKey publicKey : publicKeys) {

            Signature sigDSA = Signature.getInstance(SHA256_WITH_DSA, SUN);
            sigDSA.update(fileBytes);

            Signature sigECDSA = Signature.getInstance(SHA256_WITH_ECDSA, SUN_EC);
            sigECDSA.update(fileBytes);

            Signature sigRSA = Signature.getInstance(SHA256_WITT_RSA, SUN);
            sigRSA.update(fileBytes);

            sigDSA.initVerify(publicKey);
            sigRSA.initVerify(publicKey);
            sigECDSA.initVerify(publicKey);

            if (sigDSA.verify(signature) || sigECDSA.verify(signature) || sigRSA.verify(signature)) {
                return true;
            }
        }
        return false;
    }

    private void loadPublicKeys(File file, char[] password, String type, String distName) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {

        FileInputStream stream = new FileInputStream(file);

        KeyStore keyStore = KeyStore.getInstance(type);

        keyStore.load(stream, password);

        stream.close();

        Enumeration<String> aliases = keyStore.aliases();

        while (aliases.hasMoreElements()) {

            String alias = aliases.nextElement();

            if (keyStore.isCertificateEntry(alias) || keyStore.entryInstanceOf(alias, KeyStore.PrivateKeyEntry.class)) {

                Certificate cert = keyStore.getCertificate(alias);

                if (cert instanceof X509Certificate) {
                    if (((X509Certificate) cert).getSubjectDN().toString().equals(distName)) {
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

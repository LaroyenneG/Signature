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

        if (args.length != 2) {
            System.err.println("Usage : SignatureTools <file name> <password>");
            System.exit(-1);
        }

        String path = args[0];
        String password = args[1];

        try {

            SignatureTools signatureTools = new SignatureTools(path, password.toCharArray(), TYPE, "CN=Paul Lemettre, OU=uha, O=ensisa, L=mulhouse, ST=france, C=FR");

            System.out.println(signatureTools);

            System.out.println(signatureTools.verify("assets/bidon.txt", "absdtkjdtvolpmqtrgtfnbbnjhgdvgdtabsdtkjdtvolpmqtrgtfnbbnjhgdvgdtabsdtkjdtvolpmqtrgtfnbbnjhgdvgdtabsdtkjdtvolpmqtrgtfnbbnjhgdvgdtabsdtkjdtvolpmqtrgtfnbbnjhgdvgdtabsdtkjdtvolpmqtrgtfnbbnjhgdvgdtabsdtkjdtvolpmqtrgtfnbbnjhgdvgdtabsdtkjdtvolpmqtrgtfnbbnjhgdvgdt".getBytes()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean verify(String fileName, byte[] signature) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName));

        byte[] fileBytes = bufferedInputStream.readAllBytes();

        bufferedInputStream.close();

        for (PublicKey publicKey : publicKeys) {

            Signature sign = null;

            switch (publicKey.getAlgorithm()) {
                case SHA256_WITH_DSA:
                    sign = Signature.getInstance(SHA256_WITH_DSA, SUN);
                    break;

                case SHA256_WITH_ECDSA:
                    sign = Signature.getInstance(SHA256_WITH_ECDSA, SUN_EC);
                    break;

                case "RSA":
                    sign = Signature.getInstance(SHA256_WITT_RSA);
                    break;

                default:
                    System.err.println("Unknown algorithm :" + publicKey.getAlgorithm());
                    return false;
            }

            sign.initVerify(publicKey);
            sign.update(fileBytes);

            if (sign.verify(signature)) {
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

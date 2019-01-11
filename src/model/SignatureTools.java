package model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
public class SignatureTools {

    /*******************************************************************************************************************
     * Constants
     *******************************************************************************************************************/
    protected static final String TYPE = "JCEKS";

    /*
     * Signature algorithm
     */
    private static final String SHA256_WITH_DSA = "SHA256withDSA";
    private static final String SHA256_WITH_ECDSA = "SHA256withECDSA";
    private static final String SHA256_WITH_RSA = "SHA256withRSA";

    /*
     * Key algorithm
     */
    private static final String RSA_ALGORITHM = "RSA";
    private static final String DSA_ALGORITHM = "DSA";
    private static final String EC_ALGORITHM = "EC";


    private List<PublicKey> publicKeys;
    private List<PrivateKey> privateKeys;

    public SignatureTools(String filePath, char[] password, String type, String distinguishedName) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException {

        publicKeys = new ArrayList<>();
        privateKeys = new ArrayList<>();

        loadPublicKeys(new File(filePath), password, type, distinguishedName);
        loadPrivateKeys(new File(filePath), password, type, distinguishedName);
    }

    private static boolean checkInvalidSignatureByPublicKey(byte[] signature, PublicKey publicKey) {

        switch (publicKey.getAlgorithm()) {

            case EC_ALGORITHM:
             /*
               to complete
              */
                break;

            case RSA_ALGORITHM:
              /*
                 to complete
               */
                break;
        }

        return true;
    }

    public byte[] generateSignature(String fileName, PrivateKey privateKey) throws IOException, SignatureException, InvalidKeyException, NoSuchAlgorithmException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName));

        byte[] data = bufferedInputStream.readAllBytes();

        bufferedInputStream.close();

        Signature sign = null;

        switch (privateKey.getAlgorithm()) {

            case DSA_ALGORITHM:
                sign = Signature.getInstance(SHA256_WITH_DSA);
                break;

            case EC_ALGORITHM:
                sign = Signature.getInstance(SHA256_WITH_ECDSA);
                break;

            case RSA_ALGORITHM:
                sign = Signature.getInstance(SHA256_WITH_RSA);
                break;

            default:
                System.err.println("Unknown algorithm :" + privateKey.getAlgorithm());
                return null;
        }

        sign.initSign(privateKey);
        sign.update(data);

        return sign.sign();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPublicKeys(File file, char[] password, String type, String distinguishedName) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {

        FileInputStream fileInputStream = new FileInputStream(file);

        KeyStore keyStore = KeyStore.getInstance(type);

        keyStore.load(fileInputStream, password);

        fileInputStream.close();

        Enumeration<String> aliases = keyStore.aliases();

        while (aliases.hasMoreElements()) {

            String alias = aliases.nextElement();

            if (keyStore.isCertificateEntry(alias) || keyStore.entryInstanceOf(alias, KeyStore.PrivateKeyEntry.class)) {

                Certificate certificate = keyStore.getCertificate(alias);

                if (certificate instanceof X509Certificate)
                    if (((X509Certificate) certificate).getSubjectDN().toString().equals(distinguishedName)) {
                        PublicKey publicKey = certificate.getPublicKey();
                        publicKeys.add(publicKey);
                    }
            }
        }
    }


    List<PrivateKey> getPrivateKeys() {
        return privateKeys;
    }

    public List<PublicKey> getPublicKeys() {
        return publicKeys;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Public keys :\n");
        stringBuilder.append(publicKeys);
        stringBuilder.append("Private keys :\n");
        stringBuilder.append(privateKeys);

        return new String(stringBuilder);
    }

    public boolean verify(String fileName, byte[] signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName));

        byte[] data = bufferedInputStream.readAllBytes();

        bufferedInputStream.close();

        for (PublicKey publicKey : publicKeys) {

            if (checkInvalidSignatureByPublicKey(signature, publicKey)) {

                Signature sign = null;

                switch (publicKey.getAlgorithm()) {

                    case DSA_ALGORITHM:
                        sign = Signature.getInstance(SHA256_WITH_DSA);
                        break;

                    case EC_ALGORITHM:
                        sign = Signature.getInstance(SHA256_WITH_ECDSA);
                        break;

                    case RSA_ALGORITHM:
                        sign = Signature.getInstance(SHA256_WITH_RSA);
                        break;

                    default:
                        System.err.println("Unknown algorithm :" + publicKey.getAlgorithm());
                        return false;
                }

                sign.initVerify(publicKey);
                sign.update(data);

                if (sign.verify(signature)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void loadPrivateKeys(File file, char[] password, String type, String distinguishedName) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {

        FileInputStream fileInputStream = new FileInputStream(file);

        KeyStore keyStore = KeyStore.getInstance(type);

        keyStore.load(fileInputStream, password);

        fileInputStream.close();

        Enumeration<String> aliases = keyStore.aliases();

        while (aliases.hasMoreElements()) {

            String alias = aliases.nextElement();

            Key key = keyStore.getKey(alias, password);

            if (key instanceof PrivateKey) {

                PrivateKey privateKey = (PrivateKey) key;

                if ((privateKey instanceof RSAPrivateKey) || (privateKey instanceof DSAPrivateKey) || (privateKey instanceof ECPrivateKey)) {

                    if (keyStore.isCertificateEntry(alias) || keyStore.entryInstanceOf(alias, KeyStore.PrivateKeyEntry.class)) {

                        Certificate certificate = keyStore.getCertificate(alias);

                        if (certificate instanceof X509Certificate)
                            if (((X509Certificate) certificate).getSubjectDN().toString().equals(distinguishedName)) {
                                privateKeys.add(privateKey);
                            }
                    }
                }
            }
        }
    }
}

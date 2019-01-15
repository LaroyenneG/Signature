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

/**
 * This class is a toolbox for manage files signatures with those algorithms :
 * <ul>
 * <li>SHA256withDSA</li>
 * <li>SHA256withECDSA</li>
 * <li>SHA256withRSA</li>
 * </ul>
 */
public class SignatureTools {


    /**
     * Constant String which contains JCEKS
     */
    protected static final String TYPE = "JCEKS";

    /**
     * Constant String which contains SHA256withDSA
     */
    private static final String SHA256_WITH_DSA = "SHA256withDSA";
    /**
     * Constant String which contains SHA256withECDSA
     */
    private static final String SHA256_WITH_ECDSA = "SHA256withECDSA";
    /**
     * Constant String which contains SHA256withRSA
     */
    private static final String SHA256_WITH_RSA = "SHA256withRSA";

    /**
     * Constant String which contains RSA
     */
    private static final String RSA_ALGORITHM = "RSA";
    /**
     * Constant String which contains DSA
     */
    private static final String DSA_ALGORITHM = "DSA";
    /**
     * Constant String which contains EC
     */
    private static final String EC_ALGORITHM = "EC";


    /**
     * This parameters contains all public keys in the keystore
     */
    private List<PublicKey> publicKeys;

    /**
     * This parameters contains all private keys in the keystore
     */
    private List<PrivateKey> privateKeys;

    /**
     * @param filePath          keystore's file path
     * @param password          keystore's password
     * @param type              keystore's type
     * @param distinguishedName the distinguished name which represent a person
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws UnrecoverableKeyException
     */
    public SignatureTools(String filePath, char[] password, String type, String distinguishedName) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException {

        publicKeys = new ArrayList<>();
        privateKeys = new ArrayList<>();

        loadPublicKeys(new File(filePath), password, type, distinguishedName);
        loadPrivateKeys(new File(filePath), password, type, distinguishedName);
    }


    /**
     * This function return the list of all distinguished names contained in a keystore file
     *
     * @param filePath keystore's file path
     * @param password keystore's password
     * @param type     keystore's type
     * @return The list of distinguished names contained in the keystore file
     * @throws IOException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     */
    public static List<String> getAllDNList(String filePath, char[] password, String type) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {

        List<String> listDN = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream(filePath);
        KeyStore keyStore = KeyStore.getInstance(type);
        keyStore.load(fileInputStream, password);
        fileInputStream.close();

        Enumeration<String> aliases = keyStore.aliases();

        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            if (keyStore.isCertificateEntry(alias) || keyStore.entryInstanceOf(alias, KeyStore.PrivateKeyEntry.class)) {
                Certificate certificate = keyStore.getCertificate(alias);

                if (certificate instanceof X509Certificate) {
                    String strDN = ((X509Certificate) certificate).getSubjectDN().toString();
                    if (!listDN.contains(strDN)) {
                        listDN.add(strDN);
                    }
                }
            }
        }

        return listDN;
    }

    /**
     * This function build and return a signature file with private key
     *
     * @param filePath   data's file path
     * @param privateKey the private key of the signatory user
     * @return signature
     * @throws IOException
     * @throws SignatureException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public byte[] generateSignature(String filePath, PrivateKey privateKey) throws IOException, SignatureException, InvalidKeyException, NoSuchAlgorithmException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));

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

    /**
     * This function build all of the public keys contained in the keystore
     * @param keystoreFile      keystore's file path
     * @param password          keystore's password
     * @param type              keystore's type
     * @param distinguishedName the distinguished name which represent a person
     * @throws IOException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     */
    private void loadPublicKeys(File keystoreFile, char[] password, String type, String distinguishedName) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {

        FileInputStream fileInputStream = new FileInputStream(keystoreFile);

        KeyStore keyStore = KeyStore.getInstance(type);

        keyStore.load(fileInputStream, password);

        fileInputStream.close();

        Enumeration<String> aliases = keyStore.aliases();

        while (aliases.hasMoreElements()) {

            String alias = aliases.nextElement();

            if (keyStore.isCertificateEntry(alias) || keyStore.entryInstanceOf(alias, KeyStore.PrivateKeyEntry.class)) {

                Certificate certificate = keyStore.getCertificate(alias);

                if (certificate instanceof X509Certificate) {
                    if (((X509Certificate) certificate).getSubjectDN().toString().equals(distinguishedName)) {
                        PublicKey publicKey = certificate.getPublicKey();
                        publicKeys.add(publicKey);
                    }
                }
            }
        }
    }


    /**
     * This function return the list of instance's private keys
     * @return
     * List of instance's private keys
     */
    List<PrivateKey> getPrivateKeys() {
        return privateKeys;
    }

    /**
     * This function return the list of instance's public keys
     * @return
     * List of instance's public keys
     */
    public List<PublicKey> getPublicKeys() {
        return publicKeys;
    }

    /**
     * This function return a string which represent instance's attributes
     * @return
     * A string which represent instance's attributes
     */
    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Public keys :\n");
        stringBuilder.append(publicKeys);
        stringBuilder.append("Private keys :\n");
        stringBuilder.append(privateKeys);

        return new String(stringBuilder);
    }

    /**
     * This function return true if the signature file match with a public key else this function return false
     * @param fileName
     * data's file path
     * @param signature
     * signature to check
     * @return
     * if the signature file match with a public key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException
     */
    public boolean verify(String fileName, byte[] signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName));

        byte[] data = bufferedInputStream.readAllBytes();

        bufferedInputStream.close();

        for (PublicKey publicKey : publicKeys) {

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

            try {
                if (sign.verify(signature)) {
                    return true;
                }
            } catch (SignatureException ignored) {
            }
        }
        return false;
    }

    /**
     * This function build all of the private keys contained in the keystore
     * @param keystoreFile      keystore's file path
     * @param password          keystore's password
     * @param type              keystore's type
     * @param distinguishedName the distinguished name which represent a person
     * @throws IOException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws UnrecoverableKeyException
     */
    private void loadPrivateKeys(File keystoreFile, char[] password, String type, String distinguishedName) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {

        FileInputStream fileInputStream = new FileInputStream(keystoreFile);

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

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage : java SignatureTools <file name> <password>");
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
}

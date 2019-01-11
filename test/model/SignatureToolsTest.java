package model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class SignatureToolsTest {

    private static final String FILE_NAME_STORE_FILE = "assets/keystoreTest.jks";
    private static final String PASSWORD = "password";
    private static final String DISTINGUISHED_NAME = "CN=Paul Lemettre, OU=uha, O=ensisa, L=mulhouse, ST=france, C=FR";
    private static final String FILE_TO_TEST = "assets/fileTest.txt";


    private SignatureTools sut;

    @Test
    public void testConstructor() {

        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);
            assertFalse(sut.getPrivateKeys().isEmpty());
            assertFalse(sut.getPublicKeys().isEmpty());
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testConstructorWithBadDN() {

        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, "CN=Paul Lemettre, OU=uha, O=ensisa, L=mulhouse, ST=france, C=EN");
            assertTrue(sut.getPrivateKeys().isEmpty());
            assertTrue(sut.getPublicKeys().isEmpty());
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGenerateSignature() {

        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            assertEquals(3, sut.getPrivateKeys().size());

            byte[] signature0 = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(0));

            assertNotNull(signature0);

            System.out.println(Base64.getEncoder().encodeToString(signature0));
            assertEquals(71, signature0.length);

            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            byte[] signature1 = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(1));

            assertNotNull(signature1);
            assertEquals(256, signature1.length);

            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            byte[] signature2 = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(2));

            assertNotNull(signature2);
            assertEquals(63, signature2.length);

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void testVerifySignature() {

        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            assertEquals(3, sut.getPrivateKeys().size());

            byte[] signature0 = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(0));

            assertNotNull(signature0);

            assertTrue(sut.verify(FILE_TO_TEST, signature0));

            byte[] signature1 = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(1));

            assertNotNull(signature1);
            assertTrue(sut.verify(FILE_TO_TEST, signature1));

            byte[] signature2 = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(2));

            assertNotNull(signature2);
            assertTrue(sut.verify(FILE_TO_TEST, signature2));

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void testVerifySignatureRSA() {
        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            assertEquals(3, sut.getPrivateKeys().size());

            byte[] signature = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(1));

            System.out.println(sut.getPrivateKeys().get(1).getAlgorithm());

            assertNotNull(signature);

            assertEquals(256, signature.length);

            assertEquals("voyW2QK9UJzkPeNNSyzWpgMp8rSRnkPJRA/fmAqDbX+HWhvnuK8FRGvdpGF6j5nMJ6hu/cFZgRLm+L6v7C+pgnSKGxt5/qNe1aEI4IBvgkFT94UPNnUQTwZtoKmWkdQ7EzzgH6fn+X1p4EevbpYfhKuSFvgwEw8jtRTU9KZ+PHXeClawJB4yO/8h8EhfWVf/VA3HSd4CpxeSHTQEoNtX7zuqw/itMnYJXWOH+0hakfkWej9lIVEeWznpGBfLAw6tNFHEyI9ckvSHs/yMk1OtBIcau+zWwZIHg6ZRZ5u/ARziVuAMezovINvIL90+1iyFzXARHbSKtKplKMSCjru6Rg==", Base64.getEncoder().encodeToString(signature));

            assertTrue(sut.verify(FILE_TO_TEST, signature));

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            fail();
        }
    }
}

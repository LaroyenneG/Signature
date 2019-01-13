package model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SignatureToolsTest {

    private static final String FILE_NAME_STORE_FILE = "test/assets/keystoreTest.jks";
    private static final String PASSWORD = "password";
    private static final String DISTINGUISHED_NAME = "CN=Paul Lemettre, OU=uha, O=ensisa, L=mulhouse, ST=france, C=FR";
    private static final String FILE_TO_TEST = "test/assets/fileTest.txt";


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

            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            byte[] signature1 = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(1));

            assertNotNull(signature1);
            assertEquals(256, signature1.length);

            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            byte[] signature2 = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(2));

            assertNotNull(signature2);

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

            assertNotNull(signature);

            assertEquals(256, signature.length);

            assertEquals("voyW2QK9UJzkPeNNSyzWpgMp8rSRnkPJRA/fmAqDbX+HWhvnuK8FRGvdpGF6j5nMJ6hu/cFZgRLm+L6v7C+pgnSKGxt5/qNe1aEI4IBvgkFT94UPNnUQTwZtoKmWkdQ7EzzgH6fn+X1p4EevbpYfhKuSFvgwEw8jtRTU9KZ+PHXeClawJB4yO/8h8EhfWVf/VA3HSd4CpxeSHTQEoNtX7zuqw/itMnYJXWOH+0hakfkWej9lIVEeWznpGBfLAw6tNFHEyI9ckvSHs/yMk1OtBIcau+zWwZIHg6ZRZ5u/ARziVuAMezovINvIL90+1iyFzXARHbSKtKplKMSCjru6Rg==", Base64.getEncoder().encodeToString(signature));

            assertTrue(sut.verify(FILE_TO_TEST, signature));

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void testVerifySignatureDSA() {
        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            assertEquals(3, sut.getPrivateKeys().size());

            byte[] signature = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(2));

            assertNotNull(signature);

            assertTrue(sut.verify(FILE_TO_TEST, signature));

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void testVerifySignatureECDSA() {
        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            assertEquals(3, sut.getPrivateKeys().size());

            byte[] signature = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(0));

            assertNotNull(signature);

            assertTrue(sut.verify(FILE_TO_TEST, signature));

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testVerifySignatureECDSASignFail() {
        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            assertEquals(3, sut.getPrivateKeys().size());

            byte[] signature = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(0));
            signature[signature.length / 2] = (byte) (signature[signature.length / 2] + 1);

            assertNotNull(signature);

            assertFalse(sut.verify(FILE_TO_TEST, signature));

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void testVerifySignatureRSAFail() {
        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            assertEquals(3, sut.getPrivateKeys().size());

            byte[] signature = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(1));

            assertNotNull(signature);

            assertEquals(256, signature.length);

            assertFalse(sut.verify(FILE_TO_TEST, Base64.getDecoder().decode("voyW2QK8UJzkPeNNSyzWpgMp8rSRnkPJRA/fmAqDbX+HWhvnuK8FRGvdpGF6j5nMJ6hu/cFZgRLm+L6v7C+pgnSKGxt5/qNe1aEI4IBvgkFT94UPNnUQTwZtoKmWkdQ7EzzgH6fn+X1p4EevbpYfhKuSFvgwEw8jtRTU9KZ+PHXeClawJB4yO/8h8EhfWVf/VA3HSd4CpxeSHTQEoNtX7zuqw/itMnYJXWOH+0hakfkWej9lIVEeWznpGBfLAw6tNFHEyI9ckvSHs/yMk1OtBIcau+zWwZIHg6ZRZ5u/ARziVuAMezovINvIL90+1iyFzXARHbSKtKplKMSCjru6Rg==")));

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void testVerifySignatureDSAFail() {
        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);

            assertEquals(3, sut.getPrivateKeys().size());

            byte[] signature = sut.generateSignature(FILE_TO_TEST, sut.getPrivateKeys().get(2));
            signature[signature.length / 2] = (byte) (signature[signature.length / 2] + 1);

            assertNotNull(signature);

            assertFalse(sut.verify(FILE_TO_TEST, signature));

        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetAllDN() {

        try {
            List<String> dns = SignatureTools.getAllDNList(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE);
            assertFalse(dns.isEmpty());
            assertTrue(dns.contains(DISTINGUISHED_NAME));
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}

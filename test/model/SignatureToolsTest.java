package model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SignatureToolsTest {

    private static final String FILE_NAME_STORE_FILE = "assets/keystore.jks";
    private static final String PASSWORD = "password";
    private static final String DISTINGUISHED_NAME = "CN=Lemettre Paul, OU=uha, O=ensisa, L=mulhouse, ST=france, C=FR";

    private SignatureTools sut;

    @Test
    public void testConstructor() {

        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testVerify() {

        try {
            sut = new SignatureTools(FILE_NAME_STORE_FILE, PASSWORD.toCharArray(), SignatureTools.TYPE, DISTINGUISHED_NAME);
            assertTrue(sut.verify("assets/fileTest.txt", "81:60:66:FC:07:61:AF:C1:A0:0A:F4:9B:29:17:84:EE:06:85:92:61:05:CF:70:42:7F:C6:E8:24:BB:53:3D:F4".getBytes()));
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException | SignatureException | NoSuchProviderException | InvalidKeyException | UnrecoverableKeyException e) {
            e.printStackTrace();
            fail();
        }
    }
}

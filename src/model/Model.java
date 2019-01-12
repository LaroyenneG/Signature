package model;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private String signature;
    private String dataFilePath;
    private String keyStoreFilePath;
    private String password;
    private String dn;

    private SignatureTools signatureTools;

    private List<String> dns;

    public Model() {
        dns = new ArrayList<>();
        signature = "";
        dataFilePath = "";
        keyStoreFilePath = "";
        password = "";
        dn = "";
        signatureTools = null;
    }

    public boolean canBuild() {

        return !keyStoreFilePath.isEmpty() && !dn.isEmpty();
    }

    public void buildSignatureTools() throws Exception {

        signatureTools = new SignatureTools(keyStoreFilePath, password.toCharArray(), SignatureTools.TYPE, dn);
    }


    public Boolean verifySignature(byte[] signature) {

        Boolean status = null;

        try {
            status = signatureTools.verify(dataFilePath, signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException e) {
            e.printStackTrace();
        }

        return status;
    }

    public byte[] generateSignature(int keyId) {

        byte[] signature = null;

        try {
            signature = signatureTools.generateSignature(dataFilePath, signatureTools.getPrivateKeys().get(keyId));
        } catch (IOException | SignatureException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return signature;
    }

    public void findAllDN() throws Exception {

        List<String> strings = SignatureTools.getAllDNList(keyStoreFilePath, password.toCharArray(), SignatureTools.TYPE);

        dns.clear();

        dns.addAll(strings);
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDns(String dns) {
        this.dn = dns;
    }

    public void setKeyStoreFilePath(String keyStoreFilePath) {
        this.keyStoreFilePath = keyStoreFilePath;
    }

    public List<PrivateKey> getPrivateKey() {
        return signatureTools.getPrivateKeys();
    }

    public List<String> getDNs() {
        return dns;
    }
}

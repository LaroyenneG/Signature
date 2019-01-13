package model;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.List;

public class Model {

    private String signature;
    private String dataFilePath;
    private String keyStoreFilePath;
    private String password;
    private String dn;

    private int privateKey;

    private SignatureTools signatureTools;

    public Model() {
        privateKey = -1;
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

    public boolean canVerify() {
        return canBuild() && !dataFilePath.isEmpty() && !signature.isEmpty();
    }

    public boolean canGenerate() {
        return canBuild() && !dataFilePath.isEmpty() && privateKey >= 0;
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

    public byte[] generateSignature() {

        byte[] signature = null;

        try {
            signature = signatureTools.generateSignature(dataFilePath, signatureTools.getPrivateKeys().get(privateKey));
        } catch (IOException | SignatureException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return signature;
    }

    public List<String> findAllDN() throws Exception {

        return SignatureTools.getAllDNList(keyStoreFilePath, password.toCharArray(), SignatureTools.TYPE);
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

    public void setPrivateKey(int privateKey) {
        this.privateKey = privateKey;
    }
}

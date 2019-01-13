package controler;

import model.Model;
import view.SignatureToolsViewerPanel;

import javax.swing.*;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController {

    protected Model model;
    protected SignatureToolsViewerPanel viewer;


    public AbstractController(Model model, SignatureToolsViewerPanel viewer) {
        this.model = model;
        this.viewer = viewer;
    }

    protected void showErrorMessageException(Exception e) {
        showErrorMessage(e.getMessage());
    }

    protected void showErrorMessage(String text) {
        JOptionPane.showMessageDialog(viewer, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    protected void showSuccessMessage(String text) {
        JOptionPane.showMessageDialog(viewer, text, "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    protected boolean loadAllDN() {
        try {
            List<String> strings = model.findAllDN();
            viewer.displayDNs(strings);
            model.setDns(strings.get(0));
            loadAllInformationByDN();
            return true;
        } catch (Exception e) {
            showErrorMessageException(e);
        }

        return false;
    }

    protected void loadAllInformationByDN() {

        try {
            model.buildSignatureTools();

            List<PrivateKey> privateKeys = model.getPrivateKey();

            List<String> keys = new ArrayList<>();
            for (PrivateKey k : privateKeys) {
                keys.add(k.getAlgorithm() + " : " + k.getFormat());
            }

            if (!keys.isEmpty()) {
                model.setPrivateKey(0);
            }

            viewer.displayPrivateKeys(keys);

        } catch (Exception e) {
            showErrorMessageException(e);
        }
    }
}

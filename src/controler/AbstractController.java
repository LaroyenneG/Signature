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

    protected void showErrorMessage(Exception e) {
        JOptionPane.showMessageDialog(viewer, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    protected boolean loadAllDN() {
        try {
            model.findAllDN();
            viewer.displayDNs(model.getDNs());
            return !model.getDNs().isEmpty();
        } catch (Exception e) {
            showErrorMessage(e);
        }

        return false;
    }

    protected void loadAllInformationByDN() {

        try {
            model.buildSignatureTools();

            List<PrivateKey> privateKeys = model.getPrivateKey();

            List<String> keys = new ArrayList<>();
            for (PrivateKey k : privateKeys) {
                keys.add(k.toString());
            }

            viewer.displayPrivateKeys(keys);

        } catch (Exception e) {
            showErrorMessage(e);
        }
    }
}

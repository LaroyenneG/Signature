package controler;

import model.Model;
import view.SignatureToolsViewerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Base64;

public class ButtonController extends AbstractController implements ActionListener {


    public ButtonController(Model model, SignatureToolsViewerPanel viewer) {
        super(model, viewer);
    }

    private String selectFile() {

        JFileChooser fileChooser = new JFileChooser();

        File file = null;

        int returnVal = fileChooser.showOpenDialog(viewer);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }

        return (file != null) ? file.getAbsolutePath() : "";
    }

    private void dataFileSelect() {

        String filePath = selectFile();
        if (!filePath.isEmpty()) {
            model.setDataFilePath(filePath);
            viewer.displayDataFilePath((new File(filePath)).getName());
        }
    }

    private void verifySignature() {

        model.setSignature(viewer.getSignature());

        if (model.canVerify()) {

            boolean result = false;

            try {
                byte[] bytes = Base64.getDecoder().decode(viewer.getSignature());
                result = model.verifySignature(bytes);
            } catch (Exception ignored) {
            } // encoder exception

            if (result) {
                showSuccessMessage("La signature est correcte");
            } else {
                showErrorMessage("La signature est incorrecte");
            }

        } else {
            showErrorMessage("Pour vérifier une signature vous devez sélectionner un keystore,\nun fichier de données et une signature");
        }
    }

    private void generateSignature() {

        if (model.canGenerate()) {

            byte[] signature = model.generateSignature();

            viewer.displaySignature(Base64.getEncoder().encodeToString(signature));

        } else {
            showErrorMessage("Pour générer une signature vous devez sélectionner un keystore, une clé privée et un fichier de données");
        }
    }

    private void keyStoreSelect() {

        String filePathKS = selectFile();

        if (!filePathKS.isEmpty()) {

            JPasswordField passwordField = new JPasswordField();

            int status = JOptionPane.showConfirmDialog(viewer, passwordField, "Entrez le mot de passe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (status == JOptionPane.OK_OPTION) {

                // clean interface
                model.setKeyStoreFilePath("");
                model.setPassword("");
                viewer.displayDNs(new ArrayList<>());
                model.setPrivateKey(-1);
                viewer.displayKeyStoreFilePath("");
                viewer.displayPrivateKeys(new ArrayList<>());

                String password = new String(passwordField.getPassword());

                model.setKeyStoreFilePath(filePathKS);
                model.setPassword(password);

                if (loadAllDN()) {
                    viewer.displayKeyStoreFilePath((new File(filePathKS).getName()));
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        JButton button = (JButton) actionEvent.getSource();

        switch (button.getName()) {

            case SignatureToolsViewerPanel.BUTTON_GENERATE_NAME:
                generateSignature();
                break;

            case SignatureToolsViewerPanel.BUTTON_SELECT_KEY_STORE_NAME:
                keyStoreSelect();
                break;

            case SignatureToolsViewerPanel.BUTTON_SELECT_DATA_FILE_NAME:
                dataFileSelect();
                break;

            case SignatureToolsViewerPanel.BUTTON_VERIFY_NAME:
                verifySignature();
                break;

            default:
                throw new IllegalStateException();
        }
    }
}

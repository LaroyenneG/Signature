package controler;

import model.Model;
import view.SignatureToolsViewerPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        JButton button = (JButton) actionEvent.getSource();

        switch (button.getName()) {

            case SignatureToolsViewerPanel.BUTTON_GENERATE_NAME:
                break;

            case SignatureToolsViewerPanel.BUTTON_SELECT_KEY_STORE_NAME:
                String filePathKS = selectFile();

                if (!filePathKS.isEmpty()) {

                    JPasswordField passwordField = new JPasswordField();

                    int status = JOptionPane.showConfirmDialog(viewer, passwordField, "Entrez le mot de passe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (status == JOptionPane.OK_OPTION) {

                        String password = new String(passwordField.getPassword());

                        model.setKeyStoreFilePath(filePathKS);
                        viewer.displayKeyStoreFilePath((new File(filePathKS).getName()));

                        model.setPassword(password);

                        if (loadAllDN()) {
                            model.setDns(model.getDNs().get(0));
                            loadAllInformationByDN();
                        }
                    }
                }
                break;

            case SignatureToolsViewerPanel.BUTTON_SELECT_DATA_FILE_NAME:
                String filePath = selectFile();
                if (!filePath.isEmpty()) {
                    model.setDataFilePath(filePath);
                    viewer.displayDataFilePath((new File(filePath)).getName());
                }
                break;

            case SignatureToolsViewerPanel.BUTTON_VERIFY_NAME:

                break;

            default:
                throw new IllegalStateException();
        }
    }
}

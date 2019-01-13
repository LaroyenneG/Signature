
package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;

/**
 * @author guillaume laroyenne
 */
public class SignatureToolsViewerPanel extends javax.swing.JPanel {

    private static final String SPACE_BLOCK = "               ";
    public static final String BUTTON_GENERATE_NAME = "generate";
    public static final String BUTTON_VERIFY_NAME = "verify";
    public static final String BUTTON_SELECT_DATA_FILE_NAME = "data file";
    public static final String BUTTON_SELECT_KEY_STORE_NAME = "keystore";

    private javax.swing.JButton buildSignButton;
    private javax.swing.JPanel buildSignPanel;
    private javax.swing.JPanel dataFilePanel;
    private javax.swing.JComboBox<String> distinguishedComboBox;
    private javax.swing.JLabel distinguishedLabel;
    private javax.swing.JPanel distinguishedPanel;
    private javax.swing.JLabel dataFileLabel;
    private javax.swing.JButton keyStoreFileButton;
    private javax.swing.JLabel keyStoreFilePath;
    private javax.swing.JLabel keyStoreLabel;
    private javax.swing.JPanel keyStorePanel;
    private javax.swing.JComboBox<String> privateKeyComboBox;
    private javax.swing.JLabel privateKeyLabel;
    private javax.swing.JPanel privateKeyPanel;
    private javax.swing.JLabel signatureLabel;
    private javax.swing.JTextField signatureTextField;
    private javax.swing.JButton dataFileButton;
    private javax.swing.JLabel signatureToolsLabel;
    private javax.swing.JPanel signatureToolsPanel;
    private javax.swing.JButton verifySignButton;
    private javax.swing.JPanel verifySignPanel;

    public SignatureToolsViewerPanel() {
        initComponents();
        setNames();
    }

    private void setNames() {
        buildSignButton.setName(BUTTON_GENERATE_NAME);
        verifySignButton.setName(BUTTON_VERIFY_NAME);
        keyStoreFileButton.setName(BUTTON_SELECT_KEY_STORE_NAME);
        dataFileButton.setName(BUTTON_SELECT_DATA_FILE_NAME);
    }

    public void setActionListener(ActionListener actionListener) {
        buildSignButton.addActionListener(actionListener);
        verifySignButton.addActionListener(actionListener);
        dataFileButton.addActionListener(actionListener);
        keyStoreFileButton.addActionListener(actionListener);
    }

    public void setItemListenerComboBoxDN(ItemListener itemListener) {
        distinguishedComboBox.addItemListener(itemListener);
    }

    public void setItemListenerComboBoxPrivateKey(ItemListener itemListener) {
        privateKeyComboBox.addItemListener(itemListener);
    }

    public String getSignature() {
        return signatureTextField.getText();
    }

    public void displaySignature(String signature) {
        signatureTextField.setText(signature);
    }

    public void displayPrivateKeys(List<String> keys) {
        updateComboBoxIfNotEquals(keys, privateKeyComboBox);
    }

    private void updateComboBoxIfNotEquals(List<String> values, JComboBox<String> stringJComboBox) {

        boolean equals = values.size() == stringJComboBox.getItemCount();

        if (equals) {
            for (int i = 0; i < values.size(); i++) {
                if (!values.get(i).equals(stringJComboBox.getItemAt(i))) {
                    equals = false;
                    break;
                }
            }
        }

        if (!equals) {

            stringJComboBox.removeAllItems();

            for (String s : values) {
                stringJComboBox.addItem(s);
            }
        }
    }

    public void displayDNs(List<String> dns) {
        updateComboBoxIfNotEquals(dns, distinguishedComboBox);
    }

    public int getPrivateKeyItemSelect() {

        if (privateKeyComboBox.getItemCount() <= 0) {
            return -1;
        }

        return privateKeyComboBox.getSelectedIndex();
    }

    public String getDnItemSelect() {

        int i = distinguishedComboBox.getSelectedIndex();

        if (distinguishedComboBox.getItemCount() <= 0) {
            return "";
        }

        return distinguishedComboBox.getItemAt(distinguishedComboBox.getSelectedIndex());
    }


    public void displayKeyStoreFilePath(String path) {
        keyStoreFilePath.setText(SPACE_BLOCK + path);
    }

    public void displayDataFilePath(String path) {
        dataFileLabel.setText(path);
    }

    private void initComponents() {

        keyStorePanel = new javax.swing.JPanel();
        keyStoreFileButton = new javax.swing.JButton();
        keyStoreFilePath = new javax.swing.JLabel();
        keyStoreLabel = new javax.swing.JLabel();
        distinguishedPanel = new javax.swing.JPanel();
        distinguishedLabel = new javax.swing.JLabel();
        distinguishedComboBox = new javax.swing.JComboBox<>();
        signatureToolsPanel = new javax.swing.JPanel();
        signatureTextField = new javax.swing.JTextField();
        dataFilePanel = new javax.swing.JPanel();
        signatureToolsLabel = new javax.swing.JLabel();
        dataFileButton = new javax.swing.JButton();
        dataFileLabel = new javax.swing.JLabel();
        signatureLabel = new javax.swing.JLabel();
        buildSignPanel = new javax.swing.JPanel();
        buildSignButton = new javax.swing.JButton();
        verifySignPanel = new javax.swing.JPanel();
        verifySignButton = new javax.swing.JButton();
        privateKeyPanel = new javax.swing.JPanel();
        privateKeyComboBox = new javax.swing.JComboBox<>();
        privateKeyLabel = new javax.swing.JLabel();

        keyStoreFileButton.setText("Choisir");

        keyStoreFilePath.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N

        keyStoreLabel.setText("Fichier keystore :");

        javax.swing.GroupLayout keyStorePanelLayout = new javax.swing.GroupLayout(keyStorePanel);
        keyStorePanel.setLayout(keyStorePanelLayout);
        keyStorePanelLayout.setHorizontalGroup(
                keyStorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(keyStorePanelLayout.createSequentialGroup()
                                .addGroup(keyStorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(keyStorePanelLayout.createSequentialGroup()
                                                .addGap(101, 101, 101)
                                                .addComponent(keyStoreFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(keyStorePanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(keyStoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(keyStoreFileButton)
                                .addContainerGap())
        );
        keyStorePanelLayout.setVerticalGroup(
                keyStorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(keyStorePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(keyStorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(keyStoreFileButton)
                                        .addComponent(keyStoreFilePath)
                                        .addComponent(keyStoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        distinguishedLabel.setText("Choisissez votre distinguished name  :");

        javax.swing.GroupLayout distinguishedPanelLayout = new javax.swing.GroupLayout(distinguishedPanel);
        distinguishedPanel.setLayout(distinguishedPanelLayout);
        distinguishedPanelLayout.setHorizontalGroup(
                distinguishedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(distinguishedPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(distinguishedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(distinguishedPanelLayout.createSequentialGroup()
                                                .addComponent(distinguishedLabel)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(distinguishedComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        distinguishedPanelLayout.setVerticalGroup(
                distinguishedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(distinguishedPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(distinguishedLabel)
                                .addGap(7, 7, 7)
                                .addComponent(distinguishedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        distinguishedLabel.getAccessibleContext().setAccessibleName("Choisi");

        signatureTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        signatureToolsLabel.setText("Fichier de données :");

        dataFileButton.setText("Choisir");

        dataFileLabel.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N

        javax.swing.GroupLayout dataFilePanelLayout = new javax.swing.GroupLayout(dataFilePanel);
        dataFilePanel.setLayout(dataFilePanelLayout);
        dataFilePanelLayout.setHorizontalGroup(
                dataFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dataFilePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(signatureToolsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dataFileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dataFileButton)
                                .addContainerGap())
        );
        dataFilePanelLayout.setVerticalGroup(
                dataFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dataFilePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(dataFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(dataFileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(signatureToolsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(dataFileButton))
                                .addContainerGap())
        );

        signatureLabel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        signatureLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        signatureLabel.setText("Signature :");

        buildSignButton.setText("Générer");
        buildSignButton.setActionCommand("build");
        buildSignButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout buildSignPanelLayout = new javax.swing.GroupLayout(buildSignPanel);
        buildSignPanel.setLayout(buildSignPanelLayout);
        buildSignPanelLayout.setHorizontalGroup(
                buildSignPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(buildSignPanelLayout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addComponent(buildSignButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(140, Short.MAX_VALUE))
        );
        buildSignPanelLayout.setVerticalGroup(
                buildSignPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buildSignPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buildSignButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        verifySignButton.setActionCommand("verify");
        verifySignButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        verifySignButton.setText("Vérifier");

        javax.swing.GroupLayout verifySignPanelLayout = new javax.swing.GroupLayout(verifySignPanel);
        verifySignPanel.setLayout(verifySignPanelLayout);
        verifySignPanelLayout.setHorizontalGroup(
                verifySignPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, verifySignPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(verifySignButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(123, 123, 123))
        );
        verifySignPanelLayout.setVerticalGroup(
                verifySignPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, verifySignPanelLayout.createSequentialGroup()
                                .addContainerGap(30, Short.MAX_VALUE)
                                .addComponent(verifySignButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        privateKeyLabel.setText("Clé privée :");

        javax.swing.GroupLayout privateKeyPanelLayout = new javax.swing.GroupLayout(privateKeyPanel);
        privateKeyPanel.setLayout(privateKeyPanelLayout);
        privateKeyPanelLayout.setHorizontalGroup(
                privateKeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, privateKeyPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(privateKeyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(privateKeyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        privateKeyPanelLayout.setVerticalGroup(
                privateKeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(privateKeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(privateKeyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(privateKeyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout signatureToolsPanelLayout = new javax.swing.GroupLayout(signatureToolsPanel);
        signatureToolsPanel.setLayout(signatureToolsPanelLayout);
        signatureToolsPanelLayout.setHorizontalGroup(
                signatureToolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dataFilePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(signatureToolsPanelLayout.createSequentialGroup()
                                .addComponent(buildSignPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(verifySignPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(signatureToolsPanelLayout.createSequentialGroup()
                                .addComponent(signatureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(signatureTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addComponent(privateKeyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        signatureToolsPanelLayout.setVerticalGroup(
                signatureToolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, signatureToolsPanelLayout.createSequentialGroup()
                                .addComponent(dataFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(privateKeyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addGroup(signatureToolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(signatureLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                                        .addComponent(signatureTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(signatureToolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(verifySignPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buildSignPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(keyStorePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(distinguishedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(signatureToolsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(keyStorePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(distinguishedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(signatureToolsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
    }
}

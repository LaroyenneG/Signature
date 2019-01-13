package controler;

import model.Model;
import view.SignatureToolsViewerPanel;

public class ControlGroup {

    public ControlGroup(Model model, SignatureToolsViewerPanel viewer) {
        viewer.setActionListener(new ButtonController(model, viewer));
        viewer.setItemListenerComboBoxDN(new DNComboBoxController(model, viewer));
        viewer.setItemListenerComboBoxPrivateKey(new PrivateKeyComboBoxController(model, viewer));
    }
}

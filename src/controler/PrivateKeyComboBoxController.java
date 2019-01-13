package controler;

import model.Model;
import view.SignatureToolsViewerPanel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class PrivateKeyComboBoxController extends AbstractController implements ItemListener {

    public PrivateKeyComboBoxController(Model model, SignatureToolsViewerPanel viewer) {
        super(model, viewer);
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

        if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
            model.setPrivateKey(viewer.getPrivateKeyItemSelect());
        }
    }
}

package controler;

import model.Model;
import view.SignatureToolsViewerPanel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DNComboBoxController extends AbstractController implements ItemListener {

    public DNComboBoxController(Model model, SignatureToolsViewerPanel viewer) {
        super(model, viewer);
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

        if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
            model.setDns(viewer.getDnItemSelect());
            loadAllInformationByDN();
        }
    }
}

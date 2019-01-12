package controler;

import model.Model;
import view.SignatureToolsViewerPanel;

public class ControlGroup {

    public ControlGroup(Model model, SignatureToolsViewerPanel viewer) {
        viewer.setActionListener(new ButtonController(model, viewer));
        viewer.setItemListener(new ComboBoxController(model, viewer));
    }
}

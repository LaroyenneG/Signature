package controler;

import model.Model;
import view.SignatureToolsViewerPanel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ComboBoxController extends AbstractController implements ItemListener {

    public ComboBoxController(Model model, SignatureToolsViewerPanel viewer) {
        super(model, viewer);
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

        System.out.println(itemEvent.getSource());
    }
}

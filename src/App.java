import controler.ControlGroup;
import model.Model;
import view.Loader;
import view.SignatureToolsViewer;

import javax.swing.*;

public class App {

    public static void displayLoader() {

        final Loader[] loader = {null};

        SwingUtilities.invokeLater(() -> loader[0] = new Loader());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (loader[0] != null) {
            loader[0].dispose();
        }
    }

    public static void main(String[] args) {

        if (args.length != 0) {
            System.err.println("Usage : java -jar SignatureTools");
            System.exit(-1);
        }

        displayLoader();

        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            SignatureToolsViewer viewer = new SignatureToolsViewer();
            ControlGroup controlGroup = new ControlGroup(model, viewer.getPanel());
        });
    }
}

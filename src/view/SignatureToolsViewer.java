package view;

import javax.swing.*;


public class SignatureToolsViewer extends JFrame {

    private final static int WIDTH_SIZE = 757;
    private final static int HEIGHT_SIZE = 428;

    private SignatureToolsViewerPanel panel;

    public SignatureToolsViewer() {
        panel = new SignatureToolsViewerPanel();
        add(panel);
        setSize(WIDTH_SIZE, HEIGHT_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    public SignatureToolsViewerPanel getPanel() {
        return panel;
    }
}

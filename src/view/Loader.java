package view;

/**
 * @author guillaume laroyenne
 */
public class Loader extends javax.swing.JFrame {

    private static final int PICTURE_WIDTH = 262;
    private static final int PICTURE_HEIGHT = 172;

    private java.awt.Canvas canvas;

    public Loader() {
        initComponents();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }



    private void initComponents() {

        canvas = new LogoImage(PICTURE_WIDTH, PICTURE_HEIGHT);

        canvas.setSize(PICTURE_WIDTH, PICTURE_HEIGHT);
        canvas.setVisible(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(canvas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(canvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
}

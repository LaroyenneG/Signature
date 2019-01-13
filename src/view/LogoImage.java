package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LogoImage extends Canvas {

    private int width;
    private int height;

    public LogoImage(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void paint(Graphics g) {

        try {
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("assets/logo.png");

            assert inputStream != null;

            BufferedImage bufferedImage = ImageIO.read(inputStream);

            int imgWidth = bufferedImage.getWidth();
            int imgHeight = bufferedImage.getHeight();

            double imgAspect = (double) imgHeight / imgWidth;

            double canvasAspect = (double) height / width;

            int x1 = 0;
            int y1 = 0;
            int x2 = 0;
            int y2 = 0;

            if (imgWidth < width && imgHeight < height) {

                x1 = (width - imgWidth) / 2;
                y1 = (height - imgHeight) / 2;
                x2 = imgWidth + x1;
                y2 = imgHeight + y1;

            } else {
                if (canvasAspect > imgAspect) {
                    y1 = height;
                    height = (int) (width * imgAspect);
                    y1 = (y1 - height) / 2;
                } else {
                    x1 = width;
                    width = (int) (height / imgAspect);
                    x1 = (x1 - width) / 2;
                }
                x2 = width + x1;
                y2 = height + y1;
            }

            g.drawImage(bufferedImage, x1, y1, x2, y2, 0, 0, imgWidth, imgHeight, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

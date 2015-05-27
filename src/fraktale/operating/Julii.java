package fraktale.operating;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class Julii extends Fractal implements Runnable {

    
    /**
     * Default values
     *
     * @param pb
     * @param label
     */
    public Julii(JProgressBar pb, JLabel label) {
        super(pb, label);
    }

    
    @Override
    public synchronized void run() {
        pb.setMinimum(0);
        pb.setMaximum(HEIGHT);
        pb.setVisible(true);

        renderFractal();
    }

    /**
     * Reset default values
     */
    public void resetValues() {
        MINRE = -2.2;
        MAXRE = 1.5;
        MINIM = -1.1;
        zoom = 2;
        refactor();
    }
        

    public void renderFractal() {
        color();
        this.img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        double C_re = -0.123;
        double C_im = 0.745;

        for (int y = 0; y < HEIGHT; y++) {

                 //p_im = minY +  i * ratioY;
            double c_im = MAXIM - y * IM_FACTOR;
            
            for (int x = 0; x < WIDTH; x++) {
                pb.setValue(y);

                      //p_re = minX +  j * ratioX;
                double c_re = MINRE + x * RE_FACTOR;
                double Z_re = c_re, Z_im = c_im;

                int iteration = 0;
                boolean partSet = true;

                while (iteration < MAX_ITER) {

                    double Z_re2 = Z_re * Z_re, Z_im2 = Z_im * Z_im;

                    if (Z_re2 + Z_im2 > 4) {
                        partSet = false;
                        break;
                    }
                    
                    //temp-y
                    Z_im = 2 * Z_re * Z_im + C_im;
                    Z_re = Z_re2 - Z_im2 + C_re;

                    iteration++;
                }
                if (partSet) {
                    img.setRGB(x, y, 0);
                } else {
                    img.setRGB(x, y, colors[iteration]);
                }
            }
        }

        this.label.setIcon(new ImageIcon(img));
        this.pb.setVisible(false);
    }

}

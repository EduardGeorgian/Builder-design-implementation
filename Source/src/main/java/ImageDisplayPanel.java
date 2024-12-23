import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ImageDisplayPanel extends JPanel {
    private BufferedImage image;

    public ImageDisplayPanel(BufferedImage image) {
        this.image=image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // redimensionare si afisare imagine
            int width = getWidth();
            int height = getHeight();
            g.drawImage(image, 0, 0, width, height, this);
        }
    }

    public static void displayImageInWindow(BufferedImage image, String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Dimensiunea ferestrei
        frame.add(new ImageDisplayPanel(image));
        frame.setVisible(true);
    }
}

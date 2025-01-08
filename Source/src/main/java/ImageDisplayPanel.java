import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;


//clasa facuta pentru a testa daca imaginile s au creat cum trebuie, schimbata implementarea
//in varianta finala, iar imaginile se deschid direct in interfata
public class ImageDisplayPanel {
    public static void displayImageFromUrl(String imageUrl, String title) {
        try {
            BufferedImage image = ImageIO.read(new URL(imageUrl));
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel label = new JLabel(imageIcon);

            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(label, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load image from URL: " + imageUrl,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

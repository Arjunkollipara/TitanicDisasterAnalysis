import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainApp {

    static JLabel image1 = new JLabel();
    static JLabel image2 = new JLabel();
    static JLabel image3 = new JLabel();
    static JLabel image4 = new JLabel();

    public static void displayImages() {
        try {
            image1.setIcon(new ImageIcon(ImageIO.read(new File("output/confusion_matrix.png"))));
            image2.setIcon(new ImageIcon(ImageIO.read(new File("output/actual_vs_predicted.png"))));
            image3.setIcon(new ImageIcon(ImageIO.read(new File("output/coefficients.png"))));
            image4.setIcon(new ImageIcon(ImageIO.read(new File("output/extra_plot.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Titanic ML Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);

        String[] graphOptions = {
            "Survival vs Age",
            "Survival vs Sex",
            "Survival vs Fare",
            "Survival vs Pclass"
        };

        JComboBox<String> graphSelector = new JComboBox<>(graphOptions);
        JButton runBtn = new JButton("Run Analysis");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Choose Extra Graph:"));
        topPanel.add(graphSelector);
        topPanel.add(runBtn);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(4, 1));
        imagePanel.add(image1);
        imagePanel.add(image2);
        imagePanel.add(image3);
        imagePanel.add(image4);

        runBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String choice = (String) graphSelector.getSelectedItem();
                try {
                    ProcessBuilder pb = new ProcessBuilder("python", "graph_generator.py", choice);
                    pb.inheritIO();
                    Process process = pb.start();
                    process.waitFor();
                    displayImages();
                    frame.revalidate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(imagePanel), BorderLayout.CENTER);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}

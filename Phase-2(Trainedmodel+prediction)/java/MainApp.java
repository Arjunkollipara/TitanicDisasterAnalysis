import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class MainApp {

    static JLabel image1 = new JLabel();
    static JLabel image2 = new JLabel();
    static JLabel image3 = new JLabel();
    static JLabel image4 = new JLabel();
    static JLabel accuracyLabel = new JLabel("Accuracy: Not Calculated");

    static JTextField ageField = new JTextField(5);
    static JTextField sexField = new JTextField(5);
    static JTextField fareField = new JTextField(5);
    static JLabel predictionResult = new JLabel("Prediction: N/A");

    public static void displayImages() {
        try {
            image1.setIcon(new ImageIcon(ImageIO.read(new File("output/confusion_matrix.png"))));
            image2.setIcon(new ImageIcon(ImageIO.read(new File("output/actual_vs_predicted.png"))));
            image3.setIcon(new ImageIcon(ImageIO.read(new File("output/coefficients.png"))));
            image4.setIcon(new ImageIcon(ImageIO.read(new File("output/extra_plot.png"))));

            // Reading accuracy from the file and displaying it
            String accuracy = new String(Files.readAllBytes(Paths.get("output/accuracy.txt")), StandardCharsets.UTF_8).trim();
            accuracyLabel.setText("Accuracy: " + accuracy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void predictSurvival() {
        try {
            double age = Double.parseDouble(ageField.getText());
            int sex = Integer.parseInt(sexField.getText());
            double fare = Double.parseDouble(fareField.getText());

            ProcessBuilder pb = new ProcessBuilder("python", "predict_survival.py", String.valueOf(age), String.valueOf(sex), String.valueOf(fare));
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();

            File resultFile = new File("output/prediction_result.txt");
            String result = new String(Files.readAllBytes(resultFile.toPath()), StandardCharsets.UTF_8).trim();
            predictionResult.setText("Prediction: " + result);
        } catch (Exception e) {
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
            "Survival vs Pclass",
            "Survival vs Age&Sex"
        };

        JComboBox<String> graphSelector = new JComboBox<>(graphOptions);
        JButton runBtn = new JButton("Run Analysis");
        JButton predictBtn = new JButton("Predict Survival");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Choose Extra Graph:"));
        topPanel.add(graphSelector);
        topPanel.add(runBtn);

        JPanel predictPanel = new JPanel();
        predictPanel.add(new JLabel("Age:"));
        predictPanel.add(ageField);
        predictPanel.add(new JLabel("Sex (0=Male, 1=Female):"));
        predictPanel.add(sexField);
        predictPanel.add(new JLabel("Fare:"));
        predictPanel.add(fareField);
        predictPanel.add(predictBtn);

        JPanel accuracyPanel = new JPanel();
        accuracyPanel.add(accuracyLabel);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(5, 1));
        imagePanel.add(image1);
        imagePanel.add(image2);
        imagePanel.add(image3);
        imagePanel.add(image4);
        imagePanel.add(accuracyPanel);
        imagePanel.add(predictionResult);

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

        predictBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                predictSurvival();
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(imagePanel), BorderLayout.CENTER);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class TitanicPredictionApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Titanic Prediction App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Panel for graphs
        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(new GridLayout(2, 2));
        
        // Load images
        String confusionMatrixPath = "../python_app/output/confusion_matrix.png";
        String actualVsPredictedPath = "../python_app/output/actual_vs_predicted.png";
        String coefficientsPath = "../python_app/output/coefficient.png";

        try {
            BufferedImage confusionMatrixImg = ImageIO.read(new File(confusionMatrixPath));
            BufferedImage actualVsPredictedImg = ImageIO.read(new File(actualVsPredictedPath));
            BufferedImage coefficientsImg = ImageIO.read(new File(coefficientsPath));

            // Create image labels
            JLabel confusionMatrixLabel = new JLabel(new ImageIcon(confusionMatrixImg));
            JLabel actualVsPredictedLabel = new JLabel(new ImageIcon(actualVsPredictedImg));
            JLabel coefficientsLabel = new JLabel(new ImageIcon(coefficientsImg));

            // Add labels to graph panel
            graphPanel.add(confusionMatrixLabel);
            graphPanel.add(actualVsPredictedLabel);
            graphPanel.add(coefficientsLabel);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add graph panel to frame
        frame.add(graphPanel, BorderLayout.CENTER);

        // Dropdown for multivariate analysis
        String[] options = {"Survival vs Sex", "Survival vs Age", "Survival vs Fare", "Survival vs Pclass"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        frame.add(comboBox, BorderLayout.NORTH);

        // "Predict" button
        JButton predictButton = new JButton("Predict");
        predictButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open new input frame for prediction
                new PredictionInputFrame();
            }
        });
        frame.add(predictButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

class PredictionInputFrame extends JFrame {

    public PredictionInputFrame() {
        // Setup for the new input frame
        setTitle("Enter Data for Prediction");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // Add text fields for input
        add(new JLabel("Age:"));
        JTextField ageField = new JTextField();
        add(ageField);

        add(new JLabel("Sex (0=Male, 1=Female):"));
        JTextField sexField = new JTextField();
        add(sexField);

        add(new JLabel("Fare:"));
        JTextField fareField = new JTextField();
        add(fareField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call Python model prediction here with the input data
                String age = ageField.getText();
                String sex = sexField.getText();
                String fare = fareField.getText();
                
                // Assuming Python model prediction function is available
                // For now, we print the inputs as a placeholder
                System.out.println("Predicting with Age: " + age + ", Sex: " + sex + ", Fare: " + fare);
            }
        });
        add(submitButton);

        setVisible(true);
    }
}

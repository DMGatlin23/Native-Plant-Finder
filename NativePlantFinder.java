/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package nativeplantfinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
/**
 *
 * @author bingg
 */
public class NativePlantFinder extends JFrame{

   private JPanel mainPanel, searchPanel, resultPanel;
    private JTextField stateInput;
    private JButton searchButton;
    private final String PLACEHOLDER_TEXT = "Enter your state";

    public NativePlantFinder() {
        setTitle("Native Plant Finder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800); // 600 x 400

        mainPanel = new JPanel(new CardLayout()) ;
        mainPanel.setBackground(new Color(255, 250, 221));

        // Search Panel
        searchPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(255, 235, 121));
                
                // Calculate oval size 20% larger than input field width and height
                int ovalWidth = (int) (stateInput.getPreferredSize().width * 6);
                int ovalHeight = (int) (stateInput.getPreferredSize().height * 6);

                // Calculate position to center the oval behind the input field
                int x = (stateInput.getX() + stateInput.getWidth() / 2) - (ovalWidth / 2);
                int y = (stateInput.getY() + stateInput.getHeight() / 2) - (ovalHeight / 2);

                g2d.fillOval(x, y, ovalWidth, ovalHeight);
            }
        };
        searchPanel.setBackground(new Color(255, 250, 221));

        JLabel titleLabel = new JLabel("Native Plant Picker");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 60));
        titleLabel.setForeground(new Color(0, 125, 178));
        
// Add padding to the top
        GridBagConstraints titleConstraints = new GridBagConstraints();
        titleConstraints.gridx = 0;
        titleConstraints.gridy = 0;
        titleConstraints.weighty = 0.5; // 30% padding
        searchPanel.add(titleLabel, titleConstraints);

        // Input Field
        stateInput = new JTextField(PLACEHOLDER_TEXT);
        stateInput.setForeground(new Color(166, 166, 166));
        stateInput.setPreferredSize(new Dimension(200, 50)); // Set the height to 50 pixels
        stateInput.setBorder(null); // Set the border to null to remove it
        stateInput.setFont(new Font("Poppins", Font.BOLD, 30));

        // Add focus listener to clear placeholder text
        stateInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (stateInput.getText().equals(PLACEHOLDER_TEXT)) {
                    stateInput.setText("");
                    stateInput.setForeground(Color.BLACK); // Change text color
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (stateInput.getText().isEmpty()) {
                    stateInput.setText(PLACEHOLDER_TEXT);
                    stateInput.setForeground(new Color(166, 166, 166)); // Reset text color
                }
            }
        });

        searchButton = new JButton("Search!");
        searchButton.setForeground(new Color(0, 178, 82));
        searchButton.setContentAreaFilled(false);
        searchButton.setBorder(null);
        searchButton.setOpaque(false);
        searchButton.setFont(new Font("Poppins", Font.BOLD, 45));
        searchButton.setPreferredSize(new Dimension(100, 50)); 

        // Centering input and button
        GridBagConstraints inputConstraints = new GridBagConstraints();
        inputConstraints.gridx = 0;
        inputConstraints.gridy = 1;
        inputConstraints.weighty = 0.2;
        inputConstraints.fill = GridBagConstraints.HORIZONTAL;
        inputConstraints.insets = new Insets(10, 0, 10, 0); // Add padding
        searchPanel.add(stateInput, inputConstraints);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 2;
        buttonConstraints.weighty = 1;
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
        searchPanel.add(searchButton, buttonConstraints);

        mainPanel.add(searchPanel, "SEARCH");

        // Result Panel
        resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(new Color(255, 250, 221));
        // Populate resultPanel with plant information dynamically

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        mainPanel.add(scrollPane, "RESULT");

        add(mainPanel);
        setVisible(true);
    }

    // Method to switch between panels
    public void switchPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, panelName);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NativePlantFinder();
            }
        });
    }
}
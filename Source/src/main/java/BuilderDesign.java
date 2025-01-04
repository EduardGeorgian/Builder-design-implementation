import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuilderDesign extends JFrame {

    private JPanel MainPanel;
    private JPanel ConfigPanel;
    private JButton createVehicleButton;
    private JButton backToMainPanelButton;
    private Vehicle vehicle;



    public BuilderDesign() {
        ConfigPanel = new JPanel();
        ConfigPanel.setLayout(null);
        ConfigPanel.setBounds(0,0,1080,860);
        ConfigPanel.setVisible(false);

        backToMainPanelButton = new JButton("Back");
        backToMainPanelButton.setBounds(950,50,100,30);
        backToMainPanelButton.setFont(new Font("Arial",Font.BOLD,10));
        backToMainPanelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ConfigPanel.setVisible(false);
                MainPanel.setVisible(true);
                createVehicleButton.setVisible(true);
                createVehicleButton.setEnabled(true);
            }
        });


        setContentPane(MainPanel);
        setTitle("Builder Design Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1080,860);
        setLocationRelativeTo(null);
        setVisible(true);
        createVehicleButton.setFocusPainted(false);


        JLabel vehicleNameLabel=new JLabel("Vehicle Name");
        vehicleNameLabel.setBounds(50,50,150,30);
        JTextField vehicleNameField=new JTextField();
        vehicleNameField.setBounds(200,50,200,30);
        String[] vehicleName={""};



        JLabel vehicleTypeLabel = new JLabel("Vehicle Type");
        vehicleTypeLabel.setBounds(50,100,150,30);

        String[] vehicleTypes={"Car","Truck","Motorcycle","Bicycle","Bus"};
        JComboBox<String> vehicleTypeMenu = new JComboBox<>(vehicleTypes);
        vehicleTypeMenu.setBounds(200,100,200,30);
        final String[] vehicleType = {vehicleTypeMenu.getSelectedItem().toString()};



        ConfigPanel.add(backToMainPanelButton);
        ConfigPanel.add(vehicleNameLabel);
        ConfigPanel.add(vehicleNameField);
        ConfigPanel.add(vehicleTypeLabel);
        ConfigPanel.add(vehicleTypeMenu);

        MainPanel.setLayout(null);
        ConfigPanel.setVisible(false);
        MainPanel.add(ConfigPanel);

        createVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createVehicleButton.setEnabled(false);
                createVehicleButton.setVisible(false);
                ConfigPanel.setVisible(true);
            }
        });

        vehicleTypeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vehicleType[0] =vehicleTypeMenu.getSelectedItem().toString();
                System.out.println(vehicleType[0]);
            }
        });

        vehicleNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateVariable();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateVariable();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateVariable();
            }
            private void updateVariable() {
                vehicleName[0]=vehicleNameField.getText();
            }
        });

        setVisible(true);

    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new BuilderDesign();
    }
}

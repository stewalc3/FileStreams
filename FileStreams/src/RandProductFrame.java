import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class RandProductFrame extends JFrame {
    JPanel MainPanel;
    JPanel DataPanel;
    JPanel NamePanel;
    JPanel DescriptionPanel;
    JPanel IDPanel;
    JPanel CostPanel;
    JPanel ButtonPanel;
    JPanel RecordPanel;

    JTextField NameField;
    JTextField DescriptionField;
    JTextField IDField;
    JTextField CostField;
    JTextField RecordField;

    JLabel NameLabel;
    JLabel DescriptionLabel;
    JLabel IDLabel;
    JLabel CostLabel;
    JLabel RecordLabel;

    JButton QuitButton;
    JButton AddButton;

    String NameString;
    String DescriptionString;
    String IDString;
    String CostString;

    int RecordsAdded = 0;
    double CostDouble;

    Product newProduct;
    ArrayList<Product> list = new ArrayList<Product>();

    public RandProductFrame() throws HeadlessException {
        super("RandomProductMaker");
        MainPanel = new JPanel();
        DataPanel();
        ButtonPanel();
        MainPanel.add(DataPanel);
        MainPanel.add(ButtonPanel);
        add(MainPanel);

    }
    private void DataPanel() {
        DataPanel = new JPanel();
        DataPanel.setLayout(new BoxLayout(DataPanel, BoxLayout.Y_AXIS));
        NamePanel = new JPanel();
        DescriptionPanel = new JPanel();
        IDPanel = new JPanel();
        CostPanel = new JPanel();
        RecordPanel = new JPanel();
        NameField = new JTextField(35);
        NameField.setDocument(new JTextFieldLimit(35));
        NameLabel = new JLabel("Name:");
        DescriptionField = new JTextField(75);
        DescriptionField.setDocument(new JTextFieldLimit(75));
        DescriptionLabel = new JLabel("Description:");
        IDField = new JTextField(6);
        IDField.setDocument(new JTextFieldLimit(6));
        IDLabel = new JLabel("ID:");
        CostField = new JTextField(7);
        CostField.setColumns(7);
        CostField.setDocument(new JTextFieldLimit(10));
        CostLabel = new JLabel("Cost:");
        RecordField = new JTextField(String.valueOf(RecordsAdded), 3);
        RecordLabel = new JLabel("Records Added:");
        NamePanel.add(NameLabel);
        NamePanel.add(NameField);
        DescriptionPanel.add(DescriptionLabel);
        DescriptionPanel.add(DescriptionField);
        IDPanel.add(IDLabel);
        IDPanel.add(IDField);
        CostPanel.add(CostLabel);
        CostPanel.add(CostField);
        RecordPanel.add(RecordLabel);
        RecordPanel.add(RecordField);
        DataPanel.add(IDPanel);
        DataPanel.add(NamePanel);
        DataPanel.add(DescriptionPanel);
        DataPanel.add(CostPanel);
        DataPanel.add(RecordPanel);
    }
    private void ButtonPanel(){
        ButtonPanel = new JPanel();
        QuitButton = new JButton("Quit");
        QuitButton.addActionListener((ActionEvent ae) -> {System.exit(0);});
        AddButton = new JButton("Add");
        AddButton.addActionListener(new AddListener());
        ButtonPanel.add(QuitButton);
        ButtonPanel.add(AddButton);
    }
    public class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            IDString = IDField.getText();
            while (IDString.length() < 6){
                IDString = IDString + " ";
            }
            NameString = NameField.getText();
            while (NameString.length() < 35){
                NameString = NameString + " ";
            }
            DescriptionString = DescriptionField.getText();
            while(DescriptionString.length() < 75) {
                DescriptionString = DescriptionString + " ";
            }
            CostString = CostField.getText();
            CostDouble = Double.valueOf(CostString);

            newProduct = new Product(IDString, NameString,DescriptionString, CostDouble);
            System.out.println(IDString + ", " + NameString + ", " + DescriptionString + ", " + CostDouble);
            list.add(newProduct);
            File workingDirectory = new File(System.getProperty("user.dir"));
            Path file = Paths.get(workingDirectory.getPath() + "\\src\\RandProdData.csv");

            try
            {
                OutputStream out =
                        new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                BufferedWriter writer =
                        new BufferedWriter(new OutputStreamWriter(out));


                for(Product newProduct : list)
                {
                    writer.write(newProduct.toCSVDataRecord());
                    writer.newLine();
                }
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();}


            RecordsAdded++;
            RecordField.setText(String.valueOf(RecordsAdded));
            IDField.setText("");
            NameField.setText("");
            DescriptionField.setText("");
            CostField.setText("");
        }
    }
}


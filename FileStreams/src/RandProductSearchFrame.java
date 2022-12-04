import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.File.*;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

public class RandProductSearchFrame extends JFrame {
    JPanel MainPanel;
    JPanel SearchPanel;
    JPanel ButtonPanel;
    JPanel ResultsPanel;

    JTextField SearchField;
    JTextArea ResultsArea;

    JLabel SearchLabel;
    JLabel ResultsLabel;

    JButton QuitButton;
    JButton LoadButton;

    Scanner in;

    JFileChooser FileChooser;
    String word;
    Map<String, Integer> hashes;
    String[] words;
    Path FilePath;
    boolean FileLoaded = false;
    boolean ignore = false;
    JTextField FileName;
    ArrayList<String> WordList;

    File OriginalFile;

    public RandProductSearchFrame() throws HeadlessException{
        super("Product Search");
        MainPanel = new JPanel();
        ResultsPanel();
        ButtonPanel();
        MainPanel.add(ResultsPanel);
        MainPanel.add(ButtonPanel);
        add(MainPanel);


    }
    private void SearchPanel() {
        SearchPanel = new JPanel();
        SearchField = new JTextField(30);
        SearchLabel = new JLabel("Search Name or Partial Name: ");
        SearchPanel.add(SearchField);
        SearchPanel.add(SearchLabel);
    }
    private void ResultsPanel(){
        ResultsPanel = new JPanel();
        ResultsPanel.setLayout(new BoxLayout(ResultsPanel, BoxLayout.Y_AXIS));
        ResultsArea = new JTextArea(75, 75);
        ResultsLabel = new JLabel("Product Results: ");
        ResultsPanel.add(ResultsArea);
        ResultsPanel.add(ResultsLabel);
    }
    private void ButtonPanel(){
        ButtonPanel = new JPanel();
        QuitButton = new JButton("Quit");
        QuitButton.addActionListener((ActionEvent event) -> {System.exit(0);});
        LoadButton = new JButton("Load File");
        LoadButton.addActionListener(new LoadListener());
        ButtonPanel.add(QuitButton);
        ButtonPanel.add(LoadButton);
    }
    public class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            FileChooser = new JFileChooser();
            ResultsArea.setText("");

            try{
                File workingDirectory = new File(System.getProperty("user.dir"));
                FileChooser.setCurrentDirectory(workingDirectory);

                if (FileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    OriginalFile = FileChooser.getSelectedFile();
                    FilePath = OriginalFile.toPath();
                    FileLoaded = true;

                    FileName.setText(OriginalFile.getName());

                    Scanner in = new Scanner(System.in);
                    WordList = new ArrayList<>();
                    while(in.hasNextLine()){
                        String line = in.nextLine();
                        WordList.add(line.toLowerCase(Locale.ROOT));
                    }

                    in = new Scanner(OriginalFile);
                    hashes = new HashMap<String, Integer>();

                    while (in.hasNextLine()) {
                        String line = in.nextLine();
                        words = line.toLowerCase(Locale.ROOT).split(" ");
                        for (String word : words) {
                            ignore = false;
                            for(String stop : WordList){
                                if(word.equals(stop)){
                                    ignore = true;
                                    break;
                                }
                            }
                            if(!ignore) {
                                if (!hashes.containsKey(word)) {
                                    hashes.put(word, 1);
                                } else {
                                    hashes.put(word, hashes.get(word) + 1);
                                }
                            }
                        }
                    }

                    for(Map.Entry<String,Integer> entry : hashes.entrySet()){
                        ResultsArea.append(entry.getKey() + " : " + entry.getValue() + "\n");
                    }
                }
            } catch(FileNotFoundException e) {
                System.out.println("File not found!!!");
                e.printStackTrace();
            }
        }
    }

}

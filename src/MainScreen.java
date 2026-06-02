import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends JFrame{
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 720;

    private JScrollPane textScrollPane;
    private JPanel linesContainer;
    private final List<MusicLine> musicLinesList = new ArrayList<>();
    private static List<LineInput> presetTable = new ArrayList<>();

    public MainScreen(){
        initializeMainScreen();
        initializeMainScreenComponents();
        setPresetsList();
    }

    private void setPresetsList(){

    }


    private void initializeMainScreen() {
        setTitle("Five Nights at Michael Pepper's");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeMainScreenComponents(){
        this.linesContainer = new JPanel();
        this.linesContainer.setLayout(new BoxLayout(linesContainer, BoxLayout.Y_AXIS));
        setJMenuBar(createMenuBar());
        add(createWritingArea(), BorderLayout.CENTER);
        //add(createSidebarPanel(), BorderLayout.EAST);
    }

    private JScrollPane createWritingArea(){
        JScrollPane scrollPane = new JScrollPane(linesContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.textScrollPane = scrollPane;

        addNewLine();

        return scrollPane;
    }

    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createImportMenu());
        menuBar.add(createSaveButton());
        menuBar.add(createConvertToMidiButton());
        menuBar.add(createAddLineButton());

        return menuBar;
    }



    private void setMenuBarButtonParameters(JButton button){
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt){
                button.setContentAreaFilled(true);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                button.setContentAreaFilled(false);
            }
        });
    }

    private JButton createConvertToMidiButton(){
        JButton convertToMidiButton = new JButton("Convert to Midi");

        setMenuBarButtonParameters(convertToMidiButton);

        convertToMidiButton.addActionListener(e ->{
            System.out.println("converting to midi file");
            List<LineInput> lines = getAllLines(this.textScrollPane);
            saveText(lines);
            convertTextToMidi(lines);

        });

        return convertToMidiButton;
    }
    private void convertTextToMidi(List<LineInput> allLines){
        Parser parser = new Parser();
        List<List<ParserEvent>> parserEvents = parser.parseFullMusic(allLines);
        for (List<ParserEvent> parserEventList: parserEvents){
            for(ParserEvent parserEvent: parserEventList){
                System.out.println(parserEvent.getAbsoluteNote());
            }
        }
    }

    private void saveText(List<LineInput> allLines){
        System.out.println("saved progress");
        for (LineInput line: allLines){
            System.out.println(line.text());
            System.out.println(line.BPM());
            System.out.println(line.volume());
            System.out.println(line.instrument());
        }
        //to do: implement way to connect with saving file
        //to do: implement way of collecting Song Presets

        //System.out.println(sheet);
    }


    private JButton createSaveButton(){
        JButton saveButton = new JButton("Save");

        setMenuBarButtonParameters(saveButton);

        saveButton.addActionListener(e -> saveText(getAllLines(this.textScrollPane)));

        return saveButton;
    }

    private JButton createAddLineButton(){
        JButton addLineButton = new JButton("Add Line");
        setMenuBarButtonParameters(addLineButton);

        addLineButton.addActionListener(e -> addNewLine());

        return addLineButton;
    }

    private JMenu createImportMenu(){
        JMenu importMenu = new JMenu("Import");

        JMenuItem importText = new JMenuItem("import text");

        importText.addActionListener(a ->{
            System.out.println("importing text");
            // open new menu that shows a list of texts to import
        });

        JMenuItem importSheet = new JMenuItem("import sheet");

        importSheet.addActionListener(a ->{
            System.out.println("importing sheet");
            // open new menu that shows a list of sheets to import
        });


        // add more items to the menuBar
        importMenu.add(importText);
        importMenu.addSeparator();
        importMenu.add(importSheet);


        return importMenu;
    }


    private void addNewLine() {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0)); //fix magic numbers
        rowPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        //limits vertical size for row
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JTextField songInput = new JTextField("input text here");
        rowPanel.add(songInput, BorderLayout.CENTER);


        JPanel presetsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        //pegar info do field do array de presets
        presetsPanel.add(new JLabel("BPM:"));
        JTextField bpmInput = new JTextField("120", 3);
        presetsPanel.add(bpmInput);

        presetsPanel.add(new JLabel("Vol:"));
        JTextField volumeInput = new JTextField("100", 3);
        presetsPanel.add(volumeInput);

        presetsPanel.add(new JLabel("Inst:"));
        JTextField instrumentInput = new JTextField("0", 3);
        presetsPanel.add(instrumentInput);

        presetsPanel.add(new JLabel("Octave:"));
        JTextField octaveInput = new JTextField("4", 3);
        presetsPanel.add(octaveInput);

        //dataStructure for retrieval
        MusicLine musicLine = new MusicLine(rowPanel, songInput, bpmInput, volumeInput, instrumentInput, octaveInput);
        musicLinesList.add(musicLine);


        JButton deleteButton = new JButton("X");
        deleteButton.setForeground(Color.RED);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> {
            linesContainer.remove(rowPanel);
            musicLinesList.remove(musicLine);

            linesContainer.revalidate();
            linesContainer.repaint();
        });
        presetsPanel.add(deleteButton);

        rowPanel.add(presetsPanel, BorderLayout.EAST);

        // Empilha no container principal e atualiza a tela
        linesContainer.add(rowPanel);
        linesContainer.revalidate();
        linesContainer.repaint();
    }

    public List<LineInput> getAllLines(JScrollPane scrollPane){
        List<LineInput> lineValues = new ArrayList<>();

        for (MusicLine line : musicLinesList) {
            String inputText = line.songInput.getText();
            int BPM = Integer.parseInt(line.bpmInput.getText());
            int volume = Integer.parseInt(line.volumeInput.getText());
            int instrument = Integer.parseInt(line.instrumentInput.getText());
            int octave = Integer.parseInt(line.octaveInput.getText());
            LineInput lineValue = new LineInput(inputText, BPM, volume, instrument, octave);
            lineValues.add(lineValue);
        }
        return lineValues;
    }

}

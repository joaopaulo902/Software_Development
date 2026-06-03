import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;

// note that a lot of "magic numbers" that you may see here are a symptom of frontend development, where most of the
// size parameters are fine tuned to what looks good, so they don't have a real meaning behind them

public class MainScreen extends JFrame{
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 720;
    private final String textDirectoryPath = "Resources/Saved_Texts";
    private final String sheetDirectoryPath = "Resources/Saved_Songs";

    private JScrollPane textScrollPane;
    private JPanel linesContainer;
    private JPanel playPanel;

    private final List<MusicLine> musicLinesList = new ArrayList<>();
    private PresetTable presetTable = new PresetTable();

    private MusicBox musicBoxFeed;

    public MainScreen(){
        initializeMainScreen();
        initializeMainScreenComponents();
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
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(createPlayPanel());

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

    private JPanel createPlayPanel(){
        this.playPanel = new JPanel();
        playPanel.setOpaque(false);
        playPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton playButton = new JButton("▶ Play");
        setMenuBarButtonParameters(playButton);
        playButton.addActionListener( e ->{
            if(playButton.getText().equals("▶ Play")){
                playButton.setText("⏸ Pause");
                //sequencia de tocar musica
            }
            else{
                playButton.setText("▶ Play");
                //sequencia de parar musica
            }
        });

        JLabel playLabelFileName = new JLabel("No File in JukeBox...");


        playPanel.add(playLabelFileName);
        playPanel.add(playButton);

        return playPanel;
    }

    private JButton createConvertToMidiButton(){
        JButton convertToMidiButton = new JButton("Convert to Midi");

        setMenuBarButtonParameters(convertToMidiButton);

        convertToMidiButton.addActionListener(e ->{
            System.out.println("converting to midi file");
            List<LineInput> lines = getAllLines(this.textScrollPane);
            convertTextToMidi(lines);
        });

        return convertToMidiButton;
    }
    private void convertTextToMidi(List<LineInput> allLines){
        Parser parser = new Parser();
        List<List<ParserEvent>> parserEvents = parser.parseFullMusic(allLines);
        //fazer esquema de transformar em musica q eu fiz com o jordie hj

   }


    private JButton createSaveButton(){
        JButton saveButton = new JButton("Save");

        setMenuBarButtonParameters(saveButton);

        saveButton.addActionListener(e -> {

            String fileName = JOptionPane.showInputDialog(
                    this,
                    "Digite o nome do arquivo:",
                    "Salvar Arquivo",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (fileName != null && !fileName.trim().isEmpty()) {
                fileName = fileName.trim();
                if (!fileName.toLowerCase().endsWith(".txt")) {
                    fileName += ".txt";
                    saveText(fileName, getAllLines(this.textScrollPane));
                }
            }


        });

        return saveButton;
    }



    private void saveText(String fileName, List<LineInput> allLines){

        for (LineInput line: allLines){
            System.out.println(line.text());
            System.out.println(line.BPM());
            System.out.println(line.volume());
            System.out.println(line.instrument());
        }

        File fileToSave = new File(textDirectoryPath, fileName);

        try (FileWriter writer = new FileWriter(fileToSave, StandardCharsets.UTF_8)) {
            for(LineInput line: allLines){
                writer.write(line.text());
                writer.write("\n");
            }
            JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        //EXTRA: way to save and import song presets

        //System.out.println(sheet);
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
            FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text Archives (*.txt)", "txt");
            File inquiryResult = fileChooserSequence(textDirectoryPath, txtFilter);
            if(inquiryResult != null ){
                TextProcessor tp = new TextProcessor();
                String textBuffer = tp.processFile(inquiryResult);

                if (textBuffer != null && !textBuffer.trim().isEmpty()) {
                    linesContainer.removeAll();
                    musicLinesList.clear();

                    String[] lines = textBuffer.split("\\r?\\n");

                    int linesToImport = Math.min(lines.length, 16);


                    for (int i = 0; i < linesToImport; i++) {
                        createLine(lines[i]);
                    }

                    if (lines.length > 16) {
                        JOptionPane.showMessageDialog(this,
                                "O arquivo possui " + lines.length + " linhas, mas apenas as primeiras 16 foram importadas.",
                                "Aviso de Limite", JOptionPane.INFORMATION_MESSAGE);
                    }

                    linesContainer.revalidate();
                    linesContainer.repaint();
                }
            }
        });


        JMenuItem importSheet = new JMenuItem("import sheet");

        importSheet.addActionListener(a ->{
            System.out.println("importing sheet");
            FileNameExtensionFilter midiFilter = new FileNameExtensionFilter("Midi Files (*.midi)", "midi");
            File inquiryResult = fileChooserSequence(sheetDirectoryPath, midiFilter);
            if(inquiryResult != null){
                //da import na partitura a fazer
            }
        });


        // add more items to the menuBar
        importMenu.add(importText);
        importMenu.addSeparator();
        importMenu.add(importSheet);


        return importMenu;
    }

    private File fileChooserSequence(String directoryPath, FileNameExtensionFilter filter){
        JFileChooser chooser = new JFileChooser(new File(directoryPath));

        chooser.setDialogTitle("Select File For Importing");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);

        int inquiryResult = chooser.showOpenDialog(null);

        if(inquiryResult == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile();

        return null;
    }


    private void addNewLine() {
        // Validação de limite para adições manuais
        if (musicLinesList.size() >= 16) {
            JOptionPane.showMessageDialog(
                    this,
                    "Maximum limit (16) has been reached",
                    "Limit Reached",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        LinePreset linePreset = presetTable.getPresetLine(musicLinesList.size());


        createLine(linePreset.text());
        linesContainer.revalidate();
        linesContainer.repaint();
    }

    private void createLine(String textContent) {
        int currentIndex = musicLinesList.size();
        LinePreset linePreset = presetTable.getPresetLine(currentIndex);

        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JTextField songInput = new JTextField(textContent);
        rowPanel.add(songInput, BorderLayout.CENTER);

        JPanel presetsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        presetsPanel.add(new JLabel("BPM:"));
        JTextField bpmInput = new JTextField(linePreset.bpm(), 3);
        presetsPanel.add(bpmInput);

        presetsPanel.add(new JLabel("Vol:"));
        JTextField volumeInput = new JTextField(linePreset.volume(), 3);
        presetsPanel.add(volumeInput);

        presetsPanel.add(new JLabel("Inst:"));
        JTextField instrumentInput = new JTextField(linePreset.instrument(), 3);
        presetsPanel.add(instrumentInput);

        presetsPanel.add(new JLabel("Octave:"));
        JTextField octaveInput = new JTextField(linePreset.octave(), 3);
        presetsPanel.add(octaveInput);

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
        linesContainer.add(rowPanel);
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

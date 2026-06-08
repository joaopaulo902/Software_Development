import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * Class responsible for instancing the Main Screen
 */
public class MainScreen extends JFrame {

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 720;
    private static final int MAX_LINES = 16;

    private static final String TEXT_DIR = "Resources/Saved_Texts";
    private static final String SHEET_DIR = "Resources/Saved_Songs";
    private static final String PLAY_TEXT = "▶ Play";
    private static final String PAUSE_TEXT = "⏸ Pause";
    private final int ROCK_MUSIC_BPM = 120;

    private final List<MusicLine> musicLinesList = new ArrayList<>();
    private final PresetTable presetTable = new PresetTable();

    private JScrollPane textScrollPane;
    private JPanel linesContainer;
    private JPanel playPanel;
    private JLabel playLabel;
    private JButton playButton;

    private MusicBox musicBoxFeed;
    private String currentSongName;
    private boolean isUpToDate = false;
    private int globalBpm = ROCK_MUSIC_BPM;

    public MainScreen() {
        this.musicBoxFeed = new MusicBox();
        initializeWindow();
        initializeComponents();
    }

    private void initializeWindow() {
        setTitle("Five Nights at Michael Pepper's");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        linesContainer = new JPanel();
        linesContainer.setLayout(new BoxLayout(linesContainer, BoxLayout.Y_AXIS));

        setJMenuBar(createMenuBar());
        add(createWritingArea(), BorderLayout.CENTER);
    }

    private JScrollPane createWritingArea() {
        textScrollPane = new JScrollPane(linesContainer);
        textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        addNewLine();
        return textScrollPane;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createImportMenu());
        menuBar.add(createMenuButton("Save", e -> handleSaveAction()));
        menuBar.add(createMenuButton("Convert to Midi", e -> handleConvertToMidi()));
        menuBar.add(createMenuButton("Add Line", e -> addNewLine()));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(createBpmPanel());
        menuBar.add(createPlayPanel());

        return menuBar;
    }
    private JPanel createBpmPanel(){
        JPanel bpmPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        bpmPanel.setOpaque(false);

        playLabel = new JLabel("BPM:");
        JTextField bpmInput = new JTextField(String.valueOf(ROCK_MUSIC_BPM), 4);
        Runnable atualizarBpm = () -> {
            try {
                int newBpm = Integer.parseInt(bpmInput.getText().trim());
                if (newBpm > 0) {
                    this.globalBpm = newBpm;
                    this.isUpToDate = false;
                    System.out.println("[DEBUG] globalBpm atualizado para: " + this.globalBpm);
                } else {
                    bpmInput.setText(String.valueOf(this.globalBpm));
                }
            } catch (NumberFormatException e) {

                bpmInput.setText(String.valueOf(this.globalBpm));
            }
        };

        bpmInput.addActionListener(e -> atualizarBpm.run());


        bpmInput.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                atualizarBpm.run();
            }
        });

        bpmPanel.add(playLabel);
        bpmPanel.add(bpmInput);

        bpmPanel.setMaximumSize(bpmPanel.getPreferredSize());
        return bpmPanel;
    }


    private JButton createMenuButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        styleMenuButton(button);
        button.addActionListener(action);
        return button;
    }

    private void styleMenuButton(JButton button) {
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setContentAreaFilled(true);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setContentAreaFilled(false);
            }
        });
    }

    private JPanel createPlayPanel() {
        playPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        playPanel.setOpaque(false);

        playLabel = new JLabel("No File in JukeBox...");
        playButton = new JButton(PLAY_TEXT);
        JButton resetButton = new JButton("■ Reset");

        styleMenuButton(playButton);
        styleMenuButton(resetButton);

        playButton.addActionListener(e -> togglePlayback());
        resetButton.addActionListener(e -> resetPlayback());

        playPanel.add(playLabel);
        playPanel.add(playButton);
        playPanel.add(resetButton);


        playPanel.setMaximumSize(playPanel.getPreferredSize());
        return playPanel;
    }

    private void togglePlayback() {
        if (playButton.getText().equals(PLAY_TEXT)) {
            startPlayback();
        } else {
            pausePlayback();
        }
    }

    private void startPlayback() {
        playButton.setText(PAUSE_TEXT);
        if ("No File in JukeBox...".equals(playLabel.getText())) {
            handleConvertToMidi();
        }
        musicBoxFeed.play();
    }

    private void pausePlayback() {
        playButton.setText(PLAY_TEXT);
        musicBoxFeed.pause();
    }

    private void resetPlayback() {
        pausePlayback();
        musicBoxFeed.reset();
    }

    private void setPlayLabel(String name) {
        if (name == null) return;

        String cleanSongName = name.replaceAll("(?i)\\.(txt|midi|mid)$", "");
        playLabel.setText("Now Playing: " + cleanSongName);
    }

    private JMenu createImportMenu() {
        JMenu importMenu = new JMenu("Import");

        JMenuItem importText = new JMenuItem("Import Text");
        importText.addActionListener(e -> importTextFile());

        JMenuItem importSheet = new JMenuItem("Import Sheet");
        importSheet.addActionListener(e -> importSheetFile());

        importMenu.add(importText);
        importMenu.addSeparator();
        importMenu.add(importSheet);

        return importMenu;
    }

    private void importTextFile() {
        File file = chooseFile(TEXT_DIR, new FileNameExtensionFilter("Text Archives (*.txt)", "txt"));
        if (file == null) return;

        String textBuffer = new TextProcessor().processFile(file);
        if (textBuffer != null && !textBuffer.trim().isEmpty()) {
            clearAllLines();

            String[] lines = textBuffer.split("\\r?\\n");
            int linesToImport = Math.min(lines.length, MAX_LINES);

            for (int i = 0; i < linesToImport; i++) {
                createLine(lines[i]);
            }

            if (lines.length > MAX_LINES) {
                showInfoMessage("File has " + lines.length + " lines, but only the first " + MAX_LINES + " were imported.");
            }
            refreshLinesContainer();
        }
        isUpToDate = false;
    }

    private void importSheetFile() {
        File file = chooseFile(SHEET_DIR, new FileNameExtensionFilter("Midi Files (*.midi)", "midi"));
        if (file == null) {
            System.out.println("no file");
            return;
        }

        if (musicBoxFeed != null) {
            musicBoxFeed.close();
        }

        musicBoxFeed = new MusicBox();
        musicBoxFeed.load_saved(file.getAbsolutePath());

        this.currentSongName = file.getName();
        this.isUpToDate = true;

        setPlayLabel(file.getName());
    }

    private File chooseFile(String directoryPath, FileNameExtensionFilter filter) {
        JFileChooser chooser = new JFileChooser(new File(directoryPath));
        chooser.setDialogTitle("Select File");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    private void handleSaveAction() {
        if (!isUpToDate) {
            this.currentSongName = promptSaveDialogue();

        }
    }

    private void handleConvertToMidi() {
        List<LineInput> allLines = getAllLines();

        if (!validateLines(allLines)) {
            showWarningMessage("All fields must be formatted correctly and have positive values.");
            return;
        }

        if (!isUpToDate) {
            this.currentSongName = promptSaveDialogue();
            if (this.currentSongName == null) return;
        }

        processAndSaveMidi(allLines);
    }

    private boolean validateLines(List<LineInput> lines) {
        for (LineInput line : lines) {
            if (line.BPM() < 0 || line.volume() < 0 || line.instrument() < 0 || line.octave() < 0) {
                return false;
            }
        }
        return true;
    }

    private void processAndSaveMidi(List<LineInput> validLines) {
        if (musicBoxFeed != null) {
            musicBoxFeed.close();
        }
        musicBoxFeed = new MusicBox();
        List<List<ParserEvent>> parserMusic = new Parser().parseFullMusic(validLines);
        List<List<MusicEvent>> music = ParserToMusicEvent.createMusicEvents(parserMusic);

        int rowIndex = 0;
        for (List<MusicEvent> sheet : music) {
            musicBoxFeed.write_line(sheet, rowIndex++);
        }

        musicBoxFeed.save(this.currentSongName);
        setPlayLabel(this.currentSongName);
    }

    private String promptSaveDialogue() {
        String fileName = JOptionPane.showInputDialog(this, "Type file Name:", "Save File", JOptionPane.PLAIN_MESSAGE);
        String songName = fileName;
        if (fileName != null && !fileName.trim().isEmpty()) {
            fileName = fileName.trim();
            if (!fileName.toLowerCase().endsWith(".txt")) {
                fileName += ".txt";
            }
            saveTextFile(fileName, getAllLines());
            return songName;
        }
        return null;
    }

    private void saveTextFile(String fileName, List<LineInput> allLines) {
        File fileToSave = new File(TEXT_DIR, fileName);

        try (FileWriter writer = new FileWriter(fileToSave, StandardCharsets.UTF_8)) {
            for (LineInput line : allLines) {
                writer.write(line.text() + "\n");
            }
            isUpToDate = true;
            showInfoMessage("Successfully saved file.");
        } catch (IOException e) {
            showErrorMessage("Error Saving file: " + e.getMessage());
        }
    }

    private void addNewLine() {
        if (musicLinesList.size() >= MAX_LINES) {
            showWarningMessage("Maximum number of lines (" + MAX_LINES + ") exceeded.");
            return;
        }

        LinePreset preset = presetTable.getPresetLine(musicLinesList.size());
        createLine(preset.text());
        refreshLinesContainer();
    }

    private void createLine(String textContent) {
        LinePreset preset = presetTable.getPresetLine(musicLinesList.size());

        JPanel rowPanel = createRowPanel();

        JTextField songInput = new JTextField(textContent);
        songInput.addFocusListener(createModificationListener());
        rowPanel.add(songInput, BorderLayout.CENTER);


        JTextField volumeInput = createInputField(preset.volume());
        JTextField instrumentInput = createInputField(preset.instrument());
        JTextField octaveInput = createInputField(preset.octave());

        MusicLine musicLine = new MusicLine(rowPanel, songInput, volumeInput, instrumentInput, octaveInput);
        musicLinesList.add(musicLine);

        JButton deleteButton = createDeleteButton(rowPanel, musicLine);
        JPanel presetsPanel = buildPresetsPanel(volumeInput, instrumentInput, octaveInput, deleteButton);

        rowPanel.add(presetsPanel, BorderLayout.EAST);
        linesContainer.add(rowPanel);
    }

    private JPanel createRowPanel() {
        JPanel rowPanel = new JPanel(new BorderLayout(10, 0));
        rowPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        return rowPanel;
    }

    private JTextField createInputField(String text) {
        JTextField field = new JTextField(text, 3);
        field.addFocusListener(createModificationListener());
        return field;
    }

    private FocusAdapter createModificationListener() {
        return new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                isUpToDate = false;
            }
        };
    }

    private JButton createDeleteButton(JPanel rowPanel, MusicLine musicLine) {
        JButton deleteButton = new JButton("X");
        deleteButton.setForeground(Color.RED);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> {
            linesContainer.remove(rowPanel);
            musicLinesList.remove(musicLine);
            refreshLinesContainer();
        });
        return deleteButton;
    }

    private JPanel buildPresetsPanel(JTextField vol, JTextField inst, JTextField oct, JButton deleteBtn) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.add(new JLabel("Vol:"));  panel.add(vol);
        panel.add(new JLabel("Inst:")); panel.add(inst);
        panel.add(new JLabel("Oct:"));  panel.add(oct);
        panel.add(deleteBtn);
        return panel;
    }

    private void clearAllLines() {
        linesContainer.removeAll();
        musicLinesList.clear();
    }

    private void refreshLinesContainer() {
        linesContainer.revalidate();
        linesContainer.repaint();
    }

    private List<LineInput> getAllLines() {
        List<LineInput> lineValues = new ArrayList<>();
        for (MusicLine line : musicLinesList) {
            //set and normalize values
            int bpm = this.globalBpm;
            int volume = tryParseInt(line.volumeInput.getText());
            volume = volume <= ParserEvent.MIDI_SATURATION ? volume : ParserEvent.MIDI_SATURATION;
            int instrument = tryParseInt(line.instrumentInput.getText());
            instrument = instrument <= ParserEvent.MIDI_SATURATION ? instrument : ParserEvent.MIDI_PIANO;
            int octave = tryParseInt(line.octaveInput.getText());
            octave = octave <= ParserEvent.MAX_OCTAVE ? octave : ParserEvent.MAX_OCTAVE;

            lineValues.add(new LineInput(line.songInput.getText(), bpm, volume, instrument, octave));
        }
        return lineValues;
    }

    private int tryParseInt(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
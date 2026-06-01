import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainScreen extends JFrame{
    private final int SCREEN_WIDTH = 800;
    private final int SCREEN_HEIGHT = 720;

    private JTextField songInput;

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
        setJMenuBar(createMenuBar());
        add(createWritingArea(), BorderLayout.CENTER);
        add(createSidebarPanel(), BorderLayout.EAST);
    }

    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createSaveButton());
        menuBar.add(createImportMenu());

        return menuBar;
    }

    private JButton createSaveButton(){
        JButton saveButton = new JButton("Save");

        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);

        saveButton.addActionListener(e -> {
            System.out.println("saved progress");
        });

        saveButton.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt){
                saveButton.setContentAreaFilled(true);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt){
                saveButton.setContentAreaFilled(false);
            }

        });

        return saveButton;
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

    private JScrollPane createWritingArea(){
        JTextArea textArea = new JTextArea();

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);


        textArea.setText("input text here");


        JScrollPane scrollPane = new JScrollPane(textArea);


        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return scrollPane;
    }

    private JPanel createSidebarPanel(){
        JPanel sideBar = new JPanel();

        sideBar.setLayout(new GridLayout (0, 1, 5, 5));
        sideBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        sideBar.add(new JLabel("BPM:"));
        JTextField bpmInput = new JTextField("120");
        sideBar.add(bpmInput);

        sideBar.add(new JLabel("Volume:"));
        JTextField volumeInput = new JTextField("100");
        sideBar.add(volumeInput);

        sideBar.add(new JLabel("Instrumento:"));
        JTextField instrumentInput = new JTextField("Piano");
        sideBar.add(instrumentInput);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(sideBar, BorderLayout.NORTH);

        return wrapper;
    }

}

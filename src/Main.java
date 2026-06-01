import javax.swing.*;

public class Main {

    public static void main(String[] args){
        MainScreen screen = new MainScreen();
        SwingUtilities.invokeLater(() -> screen.setVisible(true));

        //TextFileProcessor testFile = new TextFileProcessor();
        //String input = testFile.LoadFile("test.txt");
        //TestParser.functionality(input);
    }
}

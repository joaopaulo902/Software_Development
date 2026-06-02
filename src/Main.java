import java.nio.file.Paths;
import javax.swing.*;

public class Main {

    public static void main(String[] args){
        MusicBox player = new MusicBox();
        player.open();
        player.multi_line_test();


        //String input = "[10] >>>A B C D E F G H";
        MainScreen screen = new MainScreen();
        SwingUtilities.invokeLater(() -> screen.setVisible(true));

        //TextFileProcessor testFile = new TextFileProcessor();
        //String input = testFile.LoadFile("test.txt");
        //TestParser.functionality(input);
    }
}

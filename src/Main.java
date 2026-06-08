import java.nio.file.Paths;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        MainScreen screen = new MainScreen();
        SwingUtilities.invokeLater(() -> screen.setVisible(true));



        // component testing

        //MusicBox player = new MusicBox();
        //player.open();
        //player.multi_line_test();


        //String input = "[10] >>>A B C D E F G H";
        //load screen


        // load file tester (can only be added to frontend)
        /*
        TextProcessor testFileContents = new TextProcessor();
        String input = testFileContents.LoadFile("Resources/Saved_Texts/test.txt");
        */

        //parsing action tester

    }
}

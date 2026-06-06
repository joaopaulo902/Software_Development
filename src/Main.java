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



        //ps: shield user loading stuff outside of pipeline (load text -> convert text -> play song OR load midi file -> play midi file)

        // load file tester (can only be added to frontend)
        /*
        TextProcessor testFileContents = new TextProcessor();
        String input = testFileContents.LoadFile("Resources/Saved_Texts/test.txt");
        */

        //parsing action tester
        /*
        List<LineInput> mockInput = new ArrayList<>();
        mockInput.add(new LineInput("[10] >>>A B C D E F G H", 120, 60, 0));
        mockInput.add(new LineInput("[10] >>>A B C D E F G H", 120, 60, 0));
        mockInput.add(new LineInput("[10] >>>A B C D E F G H", 120, 60, 0));
        TestParser.functionality(mockInput);
        */
    }
}

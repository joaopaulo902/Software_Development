
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LinesToFile {

    public void to_file(List<LineInput> line, String name){
        String output = new String();
        Path where = Paths.get(name + ".txt");
        int id = 0;
        for(LineInput input : line){
            output += input.text();
            output += "\n";
        }
        if(output == null){
            return;
        }
        if (output.length() == 0){
            return;
        }
        output = output.substring(0, output.length() - 1);
        try {
            Files.writeString(where, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

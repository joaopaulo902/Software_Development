import java.nio.file.Path;
import java.nio.file.Files;


//loads file contents into a string buffer
class TextProcessor extends FileResourceProcessor<String>  {

    @Override
    protected String processFile(Path path) throws Exception {
        return Files.readString(path);
    }
}

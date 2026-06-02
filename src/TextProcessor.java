import java.nio.file.Path;
import java.nio.file.Files;

class TextProcessor extends FileResourceProcessor<String>  {

    @Override
    protected String processFile(Path path) throws Exception {
        return Files.readString(path);
    }
}

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
    Generic processor for generating resources to be worked on by other classes
 */
public abstract class FileResourceProcessor<T> {
    /**
     * Loads the file from the parameter and returns a buffer from its contents
     * @return processFile(path) a list of generic type (depends on class extension)
     */
    public T LoadFile(String fileName){
        Path path = Paths.get(fileName);
        try {
            return processFile(path);
        } catch (Exception e) {
            System.err.println("Error Handling File " + fileName + ": " + e.getMessage());
            return null;
        }
    }

    protected abstract T processFile(Path path) throws Exception;
}

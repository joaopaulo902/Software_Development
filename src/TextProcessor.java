import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.File;


/**
 * Loads TXT file contents into a buffer
 */
class TextProcessor extends FileResourceProcessor<String>  {

    @Override
    protected String processFile(File file) {
        StringBuilder stringBuffer = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append(System.lineSeparator());
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Falha crítica na leitura do arquivo: " + file.getName(), e);
        }

        return stringBuffer.toString();
    }
}

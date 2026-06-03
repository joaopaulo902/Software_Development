import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.io.File;


//loads file contents into a string buffer
class TextProcessor extends FileResourceProcessor<String>  {

    @Override
    protected String processFile(File file) {
        StringBuffer stringBuffer = new StringBuffer();

        // O try-with-resources garante que o BufferedReader será fechado automaticamente
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
                // Adiciona a quebra de linha do sistema ( \n ou \r\n ) para manter a formatação do txt
                stringBuffer.append(System.lineSeparator());
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Falha crítica na leitura do arquivo: " + file.getName(), e);
        }

        return stringBuffer.toString();
    }
}

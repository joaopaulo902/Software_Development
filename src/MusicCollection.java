import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MusicCollection {
    public static List<String> view(){
        List<String>songs = new LinkedList<>();
        try (Stream<Path> paths = Files.list(Paths.get(DIRECTORY_PATH))) {
            songs = paths
                    .filter(Files::isRegularFile) // Filters out subdirectories
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }
    public static void save(Sequence to_save, String name, List<Path> text_files){
        Path texts = Paths.get(DIRECTORY_PATH + name + TEXT_DIRECTORY);
        try {
            Files.createDirectories(texts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] temp = MidiSystem.getMidiFileTypes(to_save);
        int save_type = temp[0];
        File old_file = new File(DIRECTORY_PATH +name+".midi");
        old_file.delete();
        File out_file = new File(DIRECTORY_PATH +name+".midi");
        try {
            MidiSystem.write(to_save, save_type, out_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        save_txts(name, text_files);
    }
    public static Sequence load_song(String name){
        File song = new File(DIRECTORY_PATH +name+".midi");
        if(!song.canRead()){
            Sequencer helper;
            try {
                helper = MidiSystem.getSequencer();
            } catch (MidiUnavailableException e) {
                throw new RuntimeException(e);
            }
            return helper.getSequence();
        }
        try {
            return MidiSystem.getSequence(song);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void load_texts(String name, Path work_directory){
        Path texts = Paths.get(DIRECTORY_PATH + name + TEXT_DIRECTORY);
        List<Path> text_files;
        try (Stream<Path> stream = Files.walk(texts)){
            text_files = stream
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toUnmodifiableList());
        } catch (IOException e) {
            return;
        }
        work_directory = Path.of(work_directory + name + "_");
        int i = 0;
        for (Path txt : text_files){
            Path output = Paths.get(work_directory + String.valueOf(i));
            try {
                Files.copy(output, txt, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            i++;
        }
    }
    public static void delete(String name){
        if (name.equals(EXAMPLE_SONG)){
            return;
        }
        Path old_file = Paths.get(DIRECTORY_PATH +name+".midi");
        try {
            Files.delete(old_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path old_directory = Paths.get(DIRECTORY_PATH + name + TEXT_DIRECTORY);
        try (Stream<Path> stream = Files.walk(old_directory)){
            stream.sorted(Comparator.reverseOrder())
                    .forEach(text_file -> {
                        try {
                            Files.delete(text_file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void save_txts(String name, List<Path> text_files){
        if (text_files == null){
            return;
        }
        int i = 0;
        for(Path txt : text_files){
            Path output = Paths.get(text_path(name, i));
            try {
                Files.copy(output, txt, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
    }
    private static String text_path(String name, int track_number){
        return DIRECTORY_PATH +name+ TEXT_DIRECTORY + "/" + name + "_" + String.valueOf(track_number) + ".txt";
    }

    private static final String EXAMPLE_SONG = "Sgt_Pepper";
    private static final String DIRECTORY_PATH = "Saved_songs/";
    private static final String TEXT_DIRECTORY = "_texts";
}

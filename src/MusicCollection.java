import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


/**
 * Loads and saves midi files
 */
public class MusicCollection {
    public static List<String> view(){
        List<String>songs = new LinkedList<>();
        try (Stream<Path> paths = Files.list(Paths.get(ResourcesPath.MIDIPATH.path))) {
            songs = paths
                    .filter(Files::isRegularFile) // Filters out subdirectories
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static void save(Sequence to_save, String name/*, List<Path> text_files*/){
        /*Path texts = Paths.get(ResourcesPath.TXTPATH.path + name);
       try {
            Files.createDirectories(texts);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        int[] temp = MidiSystem.getMidiFileTypes(to_save);
        int save_type = temp[0];
        File old_file = new File(ResourcesPath.MIDIPATH.path +name+".midi");
        old_file.delete();
        File out_file = new File(ResourcesPath.MIDIPATH.path +name+".midi");
        try {
            MidiSystem.write(to_save, save_type, out_file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //save_txts(name, text_files);
    }

    public static Sequence load_song(String name) {
        File song = new File(name);


        if (!song.exists() || !song.canRead()) {
            try {
                return new Sequence(Sequence.PPQ, 24);
            } catch (InvalidMidiDataException e) {
                throw new RuntimeException("Failed to create a new blank Sequence.", e);
            }
        }

        try {
            return MidiSystem.getSequence(song);
        } catch (InvalidMidiDataException | IOException e) {
            throw new RuntimeException("Failed to load MIDI file: " + name, e);
        }
    }

    public static void delete(String name){
        if (name.equals(EXAMPLE_SONG)){
            return;
        }
        Path old_file = Paths.get(ResourcesPath.MIDIPATH.path +name+ ".midi");
        try {
            Files.delete(old_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path old_directory = Paths.get(ResourcesPath.TXTPATH.path + name);
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


    private static final String EXAMPLE_SONG = "Sgt_Pepper";
}

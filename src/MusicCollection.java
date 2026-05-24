import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MusicCollection {
    public List<String> view(){
        List<String>songs = List.of();
        try (Stream<Path> paths = Files.list(Paths.get(DIR_PATH))) {
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
    public boolean save(Sequence to_save, String name){
        int[] temp = MidiSystem.getMidiFileTypes(to_save);
        int save_type = temp[0];
        File old_file = new File(DIR_PATH +name+".midi");
        old_file.delete();
        File out_file = new File(DIR_PATH +name+".midi");
        try {
            MidiSystem.write(to_save, save_type, out_file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public Sequence load(String name){
        File song = new File(DIR_PATH +name+".midi");
        try {
            return MidiSystem.getSequence(song);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(String name){
        File old_file = new File(DIR_PATH +name+".midi");
        old_file.delete();
    }
    public List<MusicEvent> decompress(Sequence sequence, int id){
        List<MusicEvent> output = List.of();
        Track[] temp = sequence.getTracks();
        Track work_track = temp[id];
        for(int i = 0; i < work_track.size(); i++){
            MusicEvent eve = parser(work_track.get(i));
            if(eve != null){
                output.add(eve);
            }
        }
        return output;
    }

    private MusicEvent parser(MidiEvent e){
        MidiMessage input = e.getMessage();
        MusicEvent output;
        byte[] message = input.getMessage();
        return output;
    }
    private final String DIR_PATH = "SONG_SAVES";
    private final int BPM_STATUS = 0xff;
    private final int BPM_STATUS = 0xff;
    private final int BPM_STATUS = 0xff;
    private final int BPM_STATUS = 0xff;
}

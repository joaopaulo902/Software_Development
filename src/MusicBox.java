import javax.sound.midi.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Stores and manages songs
 */
public class MusicBox {

    public static final int RESOLUTION = 24;

    public MusicBox(){
        open();
    }

    public void open(){
        maker = new MusicMaker();
        try {
            sequencer = MidiSystem.getSequencer();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            sequencer.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();//handle
        }
        try {
            sequence =  new Sequence(Sequence.PPQ, RESOLUTION, MusicMaker.NUMBER_OF_TRACKS);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        if (sequence == null){//handle
            System.out.println("Sequence is null");
            return;
        }
        maker.open(sequence.getTracks());
        open_it_is = true;
    }

    public boolean is_open(){
        return open_it_is;
    }

    public void close(){
        maker.close();
        sequencer.close();
        open_it_is = false;
    }

    public void write_line(List<MusicEvent> e, int id){
        if(!open_it_is){
            return;
        }
        maker.add_line(e, id);
    }

    public void delete_line(int id){
        if(!open_it_is){
            return;
        }
        maker.delete_line(id);
    }

    public void play(){

        if (!open_it_is || (maker.is_empty() && (sequence == null || sequence.getTickLength() == 0))){
            System.out.println("Invalid Song, won't be played");
            return;
        }

        try {
            sequencer.setSequence(sequence);
        }
        catch (InvalidMidiDataException e) {
            System.out.println("Error attributing sequence");
            e.printStackTrace();
        }

        sequencer.start();
    }

    public void pause(){
        if (!open_it_is || !sequencer.isRunning()){
            return;
        }
        sequencer.stop();
    }

    public void reset(){
        sequencer.setTickPosition(0);
    }

    public List<String> view_saved(){
        return MusicCollection.view();
    }

    public void save(String name/*, List<Path> text_files*/){
        MusicCollection.save(sequence, name/*, text_files*/);
    }

    public void delete(String name){
        MusicCollection.delete(name);
    }

    public void load_saved(String name/*, Path work_directory*/){
        sequence = MusicCollection.load_song(name);
        if (maker != null && sequence != null) {
            maker.open(sequence.getTracks());
        }
        //MusicCollection.load_texts(name, work_directory);
    }



    private MusicMaker maker;
    private Sequencer sequencer;
    private Sequence sequence;
    private boolean open_it_is =  false;


    public void line_test_simple(){
        List<MusicEvent> test_events = new LinkedList<>();
        MusicEvent bpm = new MusicEvent();
        bpm.new_bpm(random_correct_number());
        //test_events.add(bpm);
        //System.out.println("Initial BPM:" + bpm.get_bpm());
        MusicEvent first_instrument = new MusicEvent();
        first_instrument.new_instrument(random_correct_number());
        //System.out.println("Initial instrument:" + first_instrument.get_instrument());
        //test_events.add(first_instrument);
        test_ending(test_events);
        write_line(test_events, 0);
        play();
        save(TEST_SAVE/*, null*/);
    }
    private int random_correct_number(){
        Random number_gen = new Random();
        return number_gen.nextInt(CORRECT_LIMIT);
    }
    public void multi_line_test(){
        int initial_bpm = random_correct_number();
        MusicEvent bpm = new MusicEvent();
        bpm.new_bpm(initial_bpm);
        for(int i =0; i < NUMBER_OF_TEST_LINES; i++){
            List<MusicEvent> test_events = new LinkedList<>();
            MusicEvent bpm_copy;
            bpm_copy = bpm;
            test_events.add(bpm_copy);
            line_builder(test_events);
            write_line(test_events, i);
        }
        play();
        save(TEST_SAVE/*, null*/);
    }
    private void line_builder(List<MusicEvent> test_events){
        MusicEvent first_instrument = new MusicEvent();
        first_instrument.new_instrument(random_correct_number());
        System.out.println("Initial instrument:" + first_instrument.get_instrument());
        test_events.add(first_instrument);
        test_ending(test_events);
    }

    private void test_ending(List<MusicEvent> test_events) {
        for (int i = 0; i < NUMBER_OF_TEST_NOTES; i++){
            MusicEvent note = new MusicEvent();
            note.new_note(random_correct_number(), random_correct_number(), MusicEvent.QUARTER_NOTE);
            System.out.println("Note N " + i + " Tone " + note.get_note() + " Volume " + note.get_volume());
            test_events.add(note);
        }
        MusicEvent second_bpm = new MusicEvent();
        second_bpm.new_bpm(random_correct_number());
        test_events.add(second_bpm);
        System.out.println("Initial BPM:" + second_bpm.get_bpm());
        MusicEvent second_instrument = new MusicEvent();
        second_instrument.new_instrument(random_correct_number());
        System.out.println("Initial instrument:" + second_instrument.get_instrument());
        test_events.add(second_instrument);
        for (int i = 0; i < NUMBER_OF_TEST_NOTES; i++){
            MusicEvent note = new MusicEvent();
            note.new_note(random_correct_number(), random_correct_number(), MusicEvent.QUARTER_NOTE);
            int note_n = i + NUMBER_OF_TEST_NOTES;
            System.out.println("Note N " + note_n + " Tone " + note.get_note() + " Volume " + note.get_volume());
            test_events.add(note);
        }
    }



    private final int NUMBER_OF_TEST_LINES = 6;
    private final int CORRECT_LIMIT = 128;
    private final int NUMBER_OF_TEST_NOTES = 5;
    private final String TEST_SAVE = "TEST";
}

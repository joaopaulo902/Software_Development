import javax.sound.midi.*;
import java.util.List;

public class Music_box {// to do: handle exceptions
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException {

    }
    //constantes
    public static int NUMBER_OF_CHANNELS = 16;

    //metodos publicos
    public void open(){
        maker = new Music_maker();
        try {
            sequencer.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();//handle
        }
        sequence =  sequencer.getSequence();
        if (sequence == null){//handle
            System.out.println("Sequence is null");
            return;
        }
        maker.open(Music_box.NUMBER_OF_CHANNELS);
        maker.set_track(sequence.createTrack());
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

    public void write_line(List<Evento> e){
        if(!open_it_is){
            return;
        }
        maker.add_line(e);
    }

    public void ready_up(){
        if(!open_it_is){
            return;
        }
        maker.ready();
    }

    public boolean is_ready(){
        if(!open_it_is){
            return false;
        }
        ready_it_is = maker.is_ready();
        return ready_it_is;
    }

    public void play(){
        if (ready_it_is){
            return;
        }
        try {
            sequencer.setSequence(sequence);
        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        sequencer.start();
    }

    public void pause(){
        if (!ready_it_is){
            return;
        }
        sequencer.stop();
    }


    //variaveis privadas
    private Music_maker maker;
    private Sequencer sequencer;
    private Sequence sequence;
    private boolean open_it_is =  false;
    private boolean ready_it_is = false;

    //metodos privados
}

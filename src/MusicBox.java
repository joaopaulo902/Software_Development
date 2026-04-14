import javax.sound.midi.*;
import java.util.List;

public class MusicBox {// to do: handle exceptions
    //constantes
    public static final int RESOLUTION = 24;

    //metodos publicos
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
            sequence =  new Sequence(Sequence.PPQ, RESOLUTION, maker.NUMBER_OF_TRACKS);
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

    public void write_line(List<Evento> e){
        if(!open_it_is){
            return;
        }
        maker.add_line(e);
    }//o instrumento inicial deve ser o primeiro evento

    public void delete_line(int id){
        if(!open_it_is){
            return;
        }
        maker.delete_line(id);
    }

    public void play(){
        if (!open_it_is || maker.is_empty()){
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
        if (!open_it_is || !sequencer.isRunning()){
            return;
        }
        sequencer.stop();
    }


    //variaveis privadas
    private MusicMaker maker;
    private Sequencer sequencer;
    private Sequence sequence;
    private boolean open_it_is =  false;

    //metodos privados
}

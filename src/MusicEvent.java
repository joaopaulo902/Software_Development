public class MusicEvent {//to do: handle out of bounds cases
    //constantes
    public static final int MAX_VALUE = 127;//define o maior valor dos campos
    public static final int INSTRUMENT_CHANGE = 0;
    public static final int BPM_CHANGE = 1;
    public static final int SILENCE = 2;
    public static final int NEW_NOTE = 3;
    public static final int OUT_OF_BOUNDS = -1;
    public static final int NON_APLICABLE = 0;

    public static final double FULL_NOTE = 4;
    public static final double HALF_NOTE = 2;
    public static final double QUARTER_NOTE = 1;
    public static final double EIGHTH_NOTE = 0.5;
    public static final double SIXTEENTH_NOTE = 0.25;
    public static final double THIRTY_SECOND_NOTE = 0.125;


    //metodos publicos
    public int get_command()
    {
        return command;
    }

    public int get_note()
    {
        return note;
    }

    public int get_volume()
    {
        return volume;
    }

    public int get_bpm()
    {
        return bpm;
    }

    public int get_instrument(){
        return instrument;
    }

    public double get_duration()
    {
        return duration;
    }

    public void new_bpm(int _new){
        if(!bpm_is_right(_new)){
            return;
        }
        mark_all_unused();
        command = BPM_CHANGE;
        bpm = _new;
    }

    public void new_note(int new_tone, int new_volume, double new_duration){
        if(!note_parameters_are_right(new_tone, new_volume, new_duration)){
            return;
        }
        mark_all_unused();
        command = NEW_NOTE;
        note = new_tone;
        volume = new_volume;
        duration = new_duration;
    }

    public void new_silence(double new_duration){
        if(!duration_is_right(new_duration)){
            return;
        }
        mark_all_unused();
        command = SILENCE;
        duration = new_duration;
    }

    public void new_instrument(int new_instrument){
        if(!instrument_is_right(new_instrument)){
            return;
        }
        mark_all_unused();
        command = INSTRUMENT_CHANGE;
        instrument = new_instrument;
    }

    //dados privados
    private int command;
    private int note;
    private int volume;
    private int bpm;
    private int instrument;
    private double duration;


    //metodos privados
    private void mark_all_unused(){
        command = OUT_OF_BOUNDS;
        note = OUT_OF_BOUNDS;
        volume = OUT_OF_BOUNDS;
        bpm = OUT_OF_BOUNDS;
        instrument = OUT_OF_BOUNDS;
        duration = OUT_OF_BOUNDS;
    }
    private boolean bpm_is_right(int bpm){
        if(bpm < 0){
            return false;
        }
        return true;
    }
    private boolean note_parameters_are_right(int note, int volume, double duration){
        if(note > MAX_VALUE || note < 0){
            return false;
        }
        if(volume > MAX_VALUE || volume < 0){
            return false;
        }
        if(duration > FULL_NOTE || duration < THIRTY_SECOND_NOTE){
            return false;
        }
        return true;
    }
    private boolean duration_is_right(double duration){
        if(duration > FULL_NOTE || duration < THIRTY_SECOND_NOTE){
            return false;
        }
        return true;
    }
    private boolean instrument_is_right(int instrument){
        if(instrument > MAX_VALUE || instrument < 0){
            return false;
        }
        return true;
    }
}

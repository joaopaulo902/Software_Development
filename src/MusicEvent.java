public class MusicEvent {//to do: handle out of bounds cases
    //constantes
    public static final int MAX_VALUE = 127;//define o maior valor dos campos
    public static final int INSTRUMENT_CHANGE = 0;
    public static final int BPM_CHANGE = 1;
    public static final int SILENCE = 2;
    public static final int NOTE = 3;
    public static final int OUT_OF_BOUNDS = -1;
    public static final int NON_APLICABLE = 0;

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

    public long get_duration()
    {
        return duration;
    }

    public void set_music_event(int command, int note, int volume, long duration)
    {
        set_command(command);
        set_note(note);
        set_volume(volume);
        set_duration(duration);
    }

    //dados privados
    private int command;
    private int note;
    private int volume;
    public long duration;

    //metodos privados
    private void set_command(int command)
    {
        this.command = treat_command(command);
    }
    private void set_note(int note)
    {
        this.note = treat_note(note);
    }
    private void set_volume(int volume)
    {
        this.volume = treat_volume(volume);
    }
    private void set_duration(long duration)
    {
        this.duration = treat_duration(duration);
    }
    private int treat_command(int command)
    {
        if (command > NOTE) {
            return OUT_OF_BOUNDS;
        }
        return command;
    }
    private int treat_note(int note)
    {
        if (note > MAX_VALUE) {
            return OUT_OF_BOUNDS;
        }
        if (command == SILENCE || command == BPM_CHANGE) {
            return NON_APLICABLE;
        }
        return note;
    }
    private int treat_volume(int volume)
    {
        if (command == INSTRUMENT_CHANGE || command == BPM_CHANGE || command == SILENCE) {
            return NON_APLICABLE;
        }
        if (volume > MAX_VALUE) {
            return OUT_OF_BOUNDS;
        }
        return volume;
    }
    private long treat_duration(long duration)
    {
        if(command == INSTRUMENT_CHANGE) {
            return NON_APLICABLE;
        }
        if(command == BPM_CHANGE && duration >= NON_APLICABLE) {
            return duration;
        }
        if (duration > MAX_VALUE) {
            return OUT_OF_BOUNDS;
        }
        return duration;
    }

}

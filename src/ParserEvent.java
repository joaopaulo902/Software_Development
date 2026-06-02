import java.lang.reflect.Type;

public class ParserEvent {
    //size of an octave including midi halfNotes
    public final int OCTAVE_SIZE = 12;
    public final int BPM_VARIATION = 10;
    public final int MIDI_SATURATION = 127;

    public final int MIDI_HARMONICA = 22;
    public final int MIDI_BAGPIPES = 110;
    public final int MIDI_TUBULAR_BELLS = 15;
    public final int MIDI_AGOGO = 20;


    //tipo de musicEvent
    private TypeEventParser typeEvent;
    private int octave;
    private int note;
    //duração da nota
    private long duration;
    private long bpm;
    private long volume;
    private int instrument;


    public ParserEvent(int inputBpm, int inputInstrument, int inputVolume, int inputOctave, TypeEventParser inputEvent){
        final int FILLER_VALUE = 1;

        this.typeEvent = inputEvent;
        this.octave = inputOctave;
        this.note = 0;
        this.duration = FILLER_VALUE;
        this.bpm = inputBpm;
        this.volume = inputVolume;
        this.instrument = inputInstrument;
    }

    public ParserEvent(ParserEvent copy){
        this.typeEvent = copy.typeEvent;
        this.octave = copy.octave;
        this.note = copy.note;
        this.duration = copy.duration;
        this.bpm = copy.bpm;
        this.volume = copy.volume;
        this.instrument = copy.instrument;

    }



    public void setTypeEvent(TypeEventParser type){
        this.typeEvent = type;
    }

    public void setOctave(int octaveInput) { this.octave = octaveInput; }

    public void setNote(int noteInput) {
        this.note = noteInput;
    }

    public void setDuration(long inputDuration) {
        this.duration = inputDuration;
    }

    public void setBpm (long inputBpm) {
        this.bpm = inputBpm;
    }

    public void setInstrument(int inputInstrument) { this.instrument = inputInstrument; }

    public void setVolume(long volume) { this.volume = volume; }

    //should be indexed into an ENUM for types of events (is currently in MusicEventClass)
    public TypeEventParser getTypeEvent(){ return this.typeEvent; }

    public int getOctave(){
        return this.octave;
    }

    public int getNote(){
        return this.note;
    }

    public int getAbsoluteNote () {
        return (OCTAVE_SIZE * this.octave) + this.note;
    }

    public long getDuration(){ return this.duration; }

    public long getBpm(){ return this.bpm; }

    public long getVolume() { return this.volume; }

    public int getInstrument(){ return this.instrument; }


    public ParserEvent getEvent() {
        return this;
    }

}

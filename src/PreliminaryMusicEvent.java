

public class PreliminaryMusicEvent {
    //size of an octave including midi halfNotes
    public final int OCTAVE_SIZE = 12;
    public final int BPM_VARIATION = 10;
    public final int MIDI_SATURATION = 127;

    public final int MIDI_HARMONICA = 22;
    public final int MIDI_BAGPIPES = 110;
    public final int MIDI_TUBULAR_BELLS = 15;
    public final int MIDI_AGOGO = 20;


    //tipo de musicEvent
    private boolean playableEvent;



    private int octave;
    //valor relativo da nota
    private int note;

    //duração da nota
    private long duration;
    private long bpm;
    private long volume;
    private int instrument;

    // construtor genérico (atualizar para receber os parâmetros especificados na lista de requisitos)
    public PreliminaryMusicEvent() {
        this.playableEvent = false;
        this.note = 0;
        this.duration = 0;
        this.bpm = 0;
        this.volume = 1;
        this.instrument = 0;
    }

    public PreliminaryMusicEvent(PreliminaryMusicEvent copy){
        this.playableEvent = copy.playableEvent;
        this.octave = copy.octave;
        this.note = copy.note;
        this.duration = copy.duration;
        this.bpm = copy.bpm;
        this.volume = copy.volume;
        this.instrument = copy.instrument;

    }



    public void definePlayable(boolean isPlayable){
        this.playableEvent = isPlayable;
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
    public boolean isPlayableEvent(){ return this.playableEvent; }

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


    public PreliminaryMusicEvent getEvent() {
        return this;
    }

}

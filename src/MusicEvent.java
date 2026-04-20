

public class MusicEvent {
    //size of an octave including midi halfNotes
    public final int OCTAVE_SIZE = 12;
    public final int BPM_VARIATION = 10;

    public final int NOTE_C = 0;
    public final int NOTE_D = 2;
    public final int NOTE_E = 4;
    public final int NOTE_F = 5;
    public final int NOTE_G = 7;
    public final int NOTE_A = 9;
    public final int NOTE_B = 11;
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
    public MusicEvent() {
        this.playableEvent = false;
        this.note = 0;
        this.duration = 0;
        this.bpm = 0;
        this.volume = 0;
        this.instrument = 0;
    }

    public MusicEvent(MusicEvent copy){
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

    //might be useless
    public void setDuration(long inputDuration) {
        this.duration = inputDuration;
    }

    public void setBpm (long inputBpm) {
        this.bpm = inputBpm;
    }

    // definitely not useless
    public void setInstrument(int inputInstrument) { this.instrument = inputInstrument; }

    //not useless
    public void setVolume(long volume) { this.volume = volume; }


    public boolean isPlayableEvent(){ return this.playableEvent; }

    public int getOctave(){
        return octave;
    }

    public int getAbsoluteNote () {
        return (OCTAVE_SIZE * this.octave) + this.note;
    }

    public long getDuration(){ return this.duration; }

    public long getBpm(){ return this.bpm; }


    public MusicEvent getEvent() {
        return this;
    }

}

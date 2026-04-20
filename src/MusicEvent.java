

public class MusicEvent {
    //tipo de musicEvent
    private int type;

    //valor absoluto da nota (calculado no parsing em função da nota e da oitava)
    private int note;
    //duração da nota
    private long duration;
    private long bpm;
    private long volume;
    private int instrument;

    // construtor genérico
    public MusicEvent() {
        this.type = Evento.NON_APLICABLE;
        this.note = Evento.NON_APLICABLE;
        this.duration = Evento.NON_APLICABLE;
        this.bpm = Evento.NON_APLICABLE;
        this.volume = Evento.NON_APLICABLE;
        this.instrument = Evento.NON_APLICABLE;
    }

    public MusicEvent(MusicEvent copy){
        this.type = copy.type;
        this.note = copy.note;
        this.duration = copy.duration;
        this.bpm = copy.bpm;
        this.volume = copy.volume;
        this.instrument = copy.instrument;

    }



    public void setType(int typeInput){
        this.type = typeInput;
    }


    public void setNote(int noteInput) {
        this.note = noteInput;
    }

    public void setDuration(long inputDuration) {
        this.duration = inputDuration;
    }

    public void setBpm (long inputBpm) {
        this.bpm = inputBpm;
    }

    public void setInstrument(int inputInstrument) {
        this.instrument = inputInstrument;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public MusicEvent getEvent() {
        return this;
    }

}

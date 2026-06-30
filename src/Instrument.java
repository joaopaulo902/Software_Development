public class Instrument extends EventAttribute {
    //possible preset values for instruments
    public static final int MIDI_HARMONICA = 22;
    public static final int MIDI_BAGPIPES = 110;
    public static final int MIDI_TUBULAR_BELLS = 15;
    public static final int MIDI_AGOGO = 20;
    public static final int MIDI_PIANO = 0;
    private final int INSTRUMENT_SATURATION = 127;

    public Instrument(Instrument instrumentInput){
        setValue(instrumentInput.getValue());
    }
    public Instrument(int primitiveInput){
        setValue(primitiveInput);
    }

    public void addValueToInstrument(int input){
        int newInstrument = this.value + input;
        setValue(newInstrument);
    }

    @Override
    public void setValue(int input){
        //edge cases: saturate value
        if(input > INSTRUMENT_SATURATION){
            this.value = INSTRUMENT_SATURATION;
            return;
        }
        if(input < 0) {
            this.value = 0;
            return;
        }
        this.value = input;
    }
}

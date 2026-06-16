public class Octave extends EventAttribute{
    //standard maximum octave specified in requirements
    private final int MAX_OCTAVE = 9;
    //size of an octave including midi halfNotes
    public static final int OCTAVE_SIZE = 12;

    public Octave(Octave inputOctave){
        setValue(inputOctave.getValue());
    }
    public Octave(int inputPrimitive){
        setValue(inputPrimitive);
    }

    public int getNoteOffset(){
        return this.value * OCTAVE_SIZE;
    }

    public void increaseOctave(){
        int newValue = this.value + 1;
        setValue(newValue);
    }
    public void decreaseOctave(){
        int newValue = this.value - 1;
        setValue(newValue);
    }

    @Override
    public void setValue(int input){
        if(input > MAX_OCTAVE){
            this.value = MAX_OCTAVE;
            return;
        }
        else if (input < 0){
            this.value = 0;
            return;
        }
        this.value = input;
    }
}

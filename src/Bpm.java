public class Bpm extends EventAttribute{
    //standard bpm variation specified in requirements
    private final int BPM_VARIATION = 10;

    public Bpm(Bpm input){
        setValue(input.getValue());
    }

    public Bpm(int primitiveInput){
        setValue(primitiveInput);
    }

    @Override
    public void setValue(int input){
        if(input <= 0){
            this.value = 1;
            return;
        }
        this.value = input;
    }

    public void increaseBpm() {
        int newValue = (this.value + BPM_VARIATION);
        setValue(newValue);
    }

    public void decreaseBpm(){
        int newValue = (this.value - BPM_VARIATION);
        setValue(newValue);
    }
}

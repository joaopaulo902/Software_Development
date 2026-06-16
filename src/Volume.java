public class Volume extends EventAttribute{
    //Saturation for Byte long midi Parameters
    private final int VOLUME_SATURATION = 127;

    public Volume(Volume input){
        setValue(input.getValue());
    }
    public Volume(int input){
        setValue(input);
    }

    public void doubleVolume(){
        int newValue = 2 * this.value;
        setValue(newValue);
    }

    @Override
    public void setValue(int value){
        if(value > VOLUME_SATURATION){
            value = VOLUME_SATURATION;
            return;
        }
        else if(value < 0){
            value = 0;
            return;
        }
        this.value = value;
    }
}

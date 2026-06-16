public class Duration extends EventAttribute{
    private final int ONE_TEMPO_DURATION = 1;

    public Duration(){
        setValue(ONE_TEMPO_DURATION);
    }

    public void setValue(int value){
        this.value = value;
    }
}

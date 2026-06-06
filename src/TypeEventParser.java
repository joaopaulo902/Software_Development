
//enum for generating event types
public enum TypeEventParser {
    NEW_INSTRUMENT(0),
    NEW_BPM(1),
    SILENCE(2),
    NEW_NOTE(3),
    GENERIC(-1);

    private final int value;

    TypeEventParser(int i) {
        this.value = i;
    }

    public int getTypeEventValue(){
        return this.value;
    }
}

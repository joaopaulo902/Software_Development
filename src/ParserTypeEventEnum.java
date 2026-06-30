/**
 * Types of different events that can be generated
 */
public enum ParserTypeEventEnum {
    NEW_INSTRUMENT(0),
    NEW_BPM(1),
    SILENCE(2),
    NEW_NOTE(3),
    GENERIC(-1);

    private final int value;

    ParserTypeEventEnum(int i) {
        this.value = i;
    }

    public int getTypeEventValue(){
        return this.value;
    }
}

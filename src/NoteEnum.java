/**
 * Notes Possible Values
 */
public enum NoteEnum {
    NOTE_C(0, 'C'),
    NOTE_D(2, 'D'),
    NOTE_E(4, 'E'),
    NOTE_F(5, 'F'),
    NOTE_G(7, 'G'),
    NOTE_A(9, 'A'),
    NOTE_B(11, 'B'),
    NOTE_H(10, 'H');

    private final int note;
    private final char label;

    NoteEnum(int value, char label){
        this.note = value;
        this.label = label;
    }
    public static boolean contains(char c) {
        for (NoteEnum note : NoteEnum.values()) {
            if (note.getLabel() == c) {
                return true;
            }
        }
        return false;
    }

    public int getNote(){
        return this.note;
    }

    public char getLabel(){
        return this.label;
    }

}

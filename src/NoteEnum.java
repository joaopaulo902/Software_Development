// enum for storing note values

public enum NoteEnum {
    NOTE_C(0, 'C'),
    NOTE_D(2, 'D'),
    NOTE_E(4, 'E'),
    NOTE_F(5, 'F'),
    NOTE_G(7, 'G'),
    NOTE_A(9, 'A'),
    NOTE_B(11, 'B');

    private final int note;
    private final char label;

    NoteEnum(int value, char label){
        this.note = value;
        this.label = label;
    }

    public int getNote(){
        return this.note;
    }

    public char getLabel(){
        return this.label;
    }

}

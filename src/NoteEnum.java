// enum for storing note values

public enum NoteEnum {
    NOTE_C(0, 'C'),
    NOTE_D(2, 'D'),
    NOTE_E(4, 'E'),
    NOTE_F(5, 'F'),
    NOTE_G(7, 'G'),
    NOTE_A(9, 'A'),
    NOTE_B(11, 'B');

    private int note;
    private char label;

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

    public static NoteEnum noteFromChar(char c) {
        return switch (c) {
            case 'A' -> NoteEnum.NOTE_A;
            case 'B' -> NoteEnum.NOTE_B;
            case 'C' -> NoteEnum.NOTE_C;
            case 'D' -> NoteEnum.NOTE_D;
            case 'E' -> NoteEnum.NOTE_E;
            case 'F' -> NoteEnum.NOTE_F;
            case 'G' -> NoteEnum.NOTE_G;
            default -> throw new RuntimeException("not a note: " + c);
        };
    }
}

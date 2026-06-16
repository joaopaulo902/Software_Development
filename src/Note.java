public class Note extends EventAttribute{
    private final int MAX_NOTE = 11;
    public Note(Note inputNote){
        setValue(inputNote.getValue());
    }

    public Note(int primitiveInput){
        setValue(primitiveInput);
    }

    @Override
    public void setValue(int input){
        if(input <= MAX_NOTE && input >= 0)
            this.value = input;
    }

    /**
     * Decrements current value from note to represent flat note
     * @param note Note to be decremented
     */
    public void setFlatNote(Note note){
            this.value = note.getValue() - 1;
    }

    public enum NoteEnum {
        NOTE_C(0, 'C'),//check whether it's better to keep note attribute as int, or Note Object
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
        public static boolean containsByLabel(char c) {
            for (NoteEnum note : NoteEnum.values()) {
                if (note.getLabel() == c) {
                    return true;
                }
            }
            return false;
        }

        public static boolean containsByValue(int value){
            for(NoteEnum note : NoteEnum.values()){
                if(note.getNote() == value){
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

}

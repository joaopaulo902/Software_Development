public enum DirectoryPath {
    TXTPATH("Resources/Saved_Text/"),
    MIDIPATH("Resources/Saved_Songs/");

    public final String path;

    DirectoryPath(String input){
        this.path = input;
    }
}

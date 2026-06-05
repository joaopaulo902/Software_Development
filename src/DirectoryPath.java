public enum DirectoryPath {
    TXTPATH("Resources/Saved_Text/"),
    MIDIPATH("Resources/Saved_Songs/");

    String path;

    DirectoryPath(String input){
        this.path = input;
    }
}

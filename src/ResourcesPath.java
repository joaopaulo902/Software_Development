/**
 * Specifies directory Paths for resources
 */
public enum ResourcesPath {
    TEXT_PATH("Resources/Saved_Texts/"),
    MIDI_PATH("Resources/Saved_Songs/");

    public final String path;

    ResourcesPath(String input){
        this.path = input;
    }

    public String getContent(){
        return this.path;
    }
}

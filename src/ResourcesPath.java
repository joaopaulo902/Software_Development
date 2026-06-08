/**
 * Specifies directory Paths for resources
 */
public enum ResourcesPath {
    TXTPATH("Resources/Saved_Text/"),
    MIDIPATH("Resources/Saved_Songs/");

    public final String path;

    ResourcesPath(String input){
        this.path = input;
    }
}

@FunctionalInterface
public interface MusicStrategy {
    /**
     * Define the contract of what needs to be executed when processing a specific character
     * @param event The event template to be modified in the processing of the character.
     */
    void apply(PreliminaryMusicEvent event);
}
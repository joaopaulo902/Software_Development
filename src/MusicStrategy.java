@FunctionalInterface
/**
 * Interface that can be implemented in anyway needed by a new character functionality
 */
public interface MusicStrategy {
    /**
     * Define the contract of what needs to be executed when processing a specific character
     * @param event The event template to be modified in the processing of the character.
     */
    void apply(ParserEvent event);
}
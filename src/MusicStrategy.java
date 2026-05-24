@FunctionalInterface
public interface MusicStrategy {
    /**
     * Define a ação a ser executada para um caractere específico.
     * @param event O evento musical que será modificado.
     */
    void apply(PreliminaryMusicEvent event);
}
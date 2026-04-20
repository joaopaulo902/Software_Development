import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private char lastCharacter;
    private final Map<Character, MusicStrategy> strategy = new HashMap<>();

    public Parser() {
        createFunctionMap();
    }

    private void createFunctionMap() {
        // preenchimento do mapa
        strategy.put('A', (event) -> { /*Event for A*/ });
        strategy.put('B', (event) -> { /*Event for B*/ });
        strategy.put('C', (event) -> { /*Event for C*/ });
        strategy.put('D', (event) -> { /*Event for D*/ });
        strategy.put('E', (event) -> { /*Event for E*/ });
        strategy.put('F', (event) -> { /*Event for F*/ });
        strategy.put('G', (event) -> { /*Event for G*/ });
        strategy.put('H', (event) -> { /*Event for H*/ });


        strategy.put('?', (event) -> { /*increases an octave*/});
        strategy.put('V', (event) -> {/* decreases an octave */});
        strategy.put('>', (event) -> {/* Increases BPM */});
        strategy.put('<', (event) -> {/* Decreases BPM */});
        strategy.put('`', (event) -> { /* default logic */ });

    }

    public List<MusicEvent> createPartitura(String entryText) {
        List<MusicEvent> partitura = new ArrayList<>();
        MusicEvent currentState = new MusicEvent();

        for (char c : entryText.toCharArray()) {
            MusicEvent evento  = processarCaractere(c, currentState);
            partitura.add(evento);
        }

        return partitura;
    }

    private MusicEvent processarCaractere(char c, MusicEvent currentState) {
        // Busca a ação ou usa o _default_ (caractere '`')
        MusicStrategy action = strategy.getOrDefault(c, strategy.get('`'));
        MusicEvent newState = new MusicEvent(currentState);

        if (action != null) {
            // action.process(evento, lastCharacter);
            //altera o estado de newState
        }

        lastCharacter = c;
        return newState;
    }
}
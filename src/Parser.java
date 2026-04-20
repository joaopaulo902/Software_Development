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

        //evento de tocar lá
        this.strategy.put('A', (event) -> {
            event.setNote(event.NOTE_A);
            event.definePlayable(true);
        });
        //evento de tocar si
        this.strategy.put('B', (event) -> {
            event.setNote(event.NOTE_B);
            event.definePlayable(true);
        });

        //evento de tocar dó
        this.strategy.put('C', (event) -> {
            event.setNote(event.NOTE_C);
            event.definePlayable(true);
        });

        //evento de tocar ré
        this.strategy.put('D', (event) -> {
            event.setNote(event.NOTE_D);
            event.definePlayable(true);
        });

        //evento de tocar mi
        this.strategy.put('E', (event) -> {
            event.setNote(event.NOTE_E);
            event.definePlayable(true);
        });

        //evento de tocar fá
        this.strategy.put('F', (event) -> {
            event.setNote(event.NOTE_F);
            event.definePlayable(true);
        });

        // evento de tocar sol
        this.strategy.put('G', (event) -> {
            event.setNote(event.NOTE_G);
            event.definePlayable(true);
        });

        //evento de tocar si bemol
        this.strategy.put('H', (event) -> {
            event.setNote(event.NOTE_B - 1);
            event.definePlayable(true);
        });

        //increase octave event parameter alter
        this.strategy.put('?', (event) -> {
            event.setOctave(event.getOctave() + 1);
            event.definePlayable(false);
        });
        //decrease octave event parameter alter
        this.strategy.put('V', (event) -> {
            event.setOctave(event.getOctave() - 1);
            event.definePlayable(false);
        });

        //increase bpm event parameter alter
        this.strategy.put('>', (event) -> {
            event.setBpm(event.getBpm() + event.BPM_VARIATION);
            event.definePlayable(false);
        });

        //decrease bpm event parameter alter
        this.strategy.put('<', (event) -> {
            event.setBpm(event.getBpm() - event.BPM_VARIATION);
            event.definePlayable(false);
        });

        //play last note
        this.strategy.put('`', (event) -> {
            if( this.lastCharacter >= 'A' && this.lastCharacter <= 'H')
                event.definePlayable(true);
        });

    }

    public List<MusicEvent> createPartitura(String entryText) {
        List<MusicEvent> partitura = new ArrayList<>();
        MusicEvent currentState = new MusicEvent();

        for (char c : entryText.toCharArray()) {
            processCharacter(c, currentState);

            if(currentState.isPlayableEvent()){
                partitura.add(new MusicEvent(currentState));
            }
        }
        return partitura;
    }

    private void processCharacter(char c, MusicEvent event) {

        MusicStrategy action = this.strategy.getOrDefault(c, strategy.get('`'));

        if (action != null) {
            action.apply(event);
        }

        this.lastCharacter = c;
    }
}
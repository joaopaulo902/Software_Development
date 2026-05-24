import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final Map<Character, MusicStrategy> strategy = new HashMap<>();
    private char lastCharacter;


    public Parser() {
        createFunctionMap();
    }

    private void createFunctionMap() {
        // preenchimento do mapa de implementações de MusicStrategy
        //default action
        MusicStrategy defaultAction = (event) -> event.definePlayable(event.isPlayableEvent());

        //Comportamento das notas (checar se tá bom dps)
        for (NoteEnum note : NoteEnum.values()) {
            final char c = note.getLabel();
            strategy.put(c, (event) -> {
                event.setNote(note.getNote());
                event.definePlayable(true);
            });
        }

        /*this.strategy.put('b', (event) -> {
            if (this.lastCharacter == 'F' || this.lastCharacter == 'C'){
                defaultAction.apply(event);
            }
            else if (this.lastCharacter >= 'A' && this.lastCharacter <= 'G'){
               event.setNote(event.getNote() - 1);
               event.definePlayable(true);
            }
        });*/

        //evento de tocar si bemol
        this.strategy.put('H', (event) -> {
            event.setNote(NoteEnum.NOTE_B.getNote() - 1);
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

        //doubles volume and does wrap around if it reaches limit
        this.strategy.put(' ', (event) -> {
            event.setVolume((2 * event.getVolume()) % event.MIDI_SATURATION);
            event.definePlayable(false);
        });

        //increase bpm event parameter alter
        this.strategy.put('>', (event) -> {
            event.setBpm((event.getBpm() + event.BPM_VARIATION) % event.MIDI_SATURATION);
            event.definePlayable(false);
        });

        //decrease bpm event parameter alter
        this.strategy.put('<', (event) -> {
            long bpm = (event.getBpm() - event.BPM_VARIATION);
            event.setBpm(bpm > 0 ? bpm : event.getBpm());
            event.definePlayable(false);
        });

        //set instrument to MIDI Harmonica
        this.strategy.put('!', (event) -> {
            event.setInstrument(event.MIDI_HARMONICA);
            event.definePlayable(false);
        });

        //set instrument to MIDI BAGPIPES
        MusicStrategy bagpipesAction = (event) -> {
            event.setInstrument(event.MIDI_BAGPIPES);
            event.definePlayable(false);
        };
        this.strategy.put('I', bagpipesAction);
        this.strategy.put('O', bagpipesAction);
        this.strategy.put('U', bagpipesAction);


        for (char c = '0'; c <= '9'; c++) {
            final int value = Character.getNumericValue(c);
            //set instrument to MIDI # current + digit
            if (value % 2 == 0) {
                this.strategy.put(c, (event) -> {
                    event.setInstrument((event.getInstrument() + value) % event.MIDI_SATURATION);
                    event.definePlayable(false);
                });
            }
            //set instrument to TUBULAR BELLS
            else {
                this.strategy.put(c, (event) -> {
                    event.setInstrument(event.MIDI_TUBULAR_BELLS);
                    event.definePlayable(false);
                });
            }
        }

        //set instrument to TUBULAR BELLS
        this.strategy.put(';', (event) -> {
            event.setInstrument(event.MIDI_TUBULAR_BELLS);
            event.definePlayable(false);
        });

        //default behaviour (if it wasn't a note don't play it, if it was, keep playing)
        this.strategy.put('`', defaultAction);


    }

    private String[] parseLines(String entry) {
        String LINE_BREAK = "\\R";
        return entry.split(LINE_BREAK);
    }

    private List<PreliminaryMusicEvent> createPartitura(String entry) {
        List<PreliminaryMusicEvent> partitura = new ArrayList<>();
        PreliminaryMusicEvent currentState = new PreliminaryMusicEvent();

        for (char c : entry.toCharArray()) {
            processCharacter(c, currentState);

            if (currentState.isPlayableEvent()) {
                partitura.add(new PreliminaryMusicEvent(currentState));
            }
        }
        return partitura;
    }

    public List<List<PreliminaryMusicEvent>> parseFullMusic(String entryText) {
        List<List<PreliminaryMusicEvent>> completeSongEvents = new ArrayList<>();

        String[] lines = parseLines(entryText);

        for (String line : lines) {
            if (!line.trim().isEmpty()) { // Ignore Blank Lines
                List<PreliminaryMusicEvent> events = createPartitura(line);
                completeSongEvents.add(events);
            }
        }
        return completeSongEvents;
    }

    private void processCharacter(char c, PreliminaryMusicEvent event) {

        MusicStrategy action = this.strategy.getOrDefault(c, strategy.get('`'));

        if (action != null) {
            action.apply(event);
        }

        this.lastCharacter = c;
    }
}
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
        MusicStrategy defaultAction = (event) -> event.setTypeEvent(TypeEventParser.SILENCE);

        //Comportamento das notas (checar se tá bom dps)
        for (NoteEnum note : NoteEnum.values()) {
            final char c = note.getLabel();
            strategy.put(c, (event) -> {
                event.setNote(note.getNote());
                event.setTypeEvent(TypeEventParser.NEW_NOTE);
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
            event.setTypeEvent(TypeEventParser.NEW_NOTE);
        });

        //increase octave event parameter alter
        this.strategy.put('?', (event) -> {
            event.setOctave(event.getOctave() + 1);
            event.setTypeEvent(TypeEventParser.GENERIC);
        });
        //decrease octave event parameter alter
        this.strategy.put('V', (event) -> {
            event.setOctave(event.getOctave() - 1);
            event.setTypeEvent(TypeEventParser.GENERIC);
        });

        //doubles volume and does wrap around if it reaches limit
        this.strategy.put(' ', (event) -> {
            event.setVolume((2 * event.getVolume()) % event.MIDI_SATURATION);
            event.setTypeEvent(TypeEventParser.GENERIC);
        });

        //increase bpm event parameter alter
        this.strategy.put('>', (event) -> {
            event.setBpm((event.getBpm() + event.BPM_VARIATION) % event.MIDI_SATURATION);
            event.setTypeEvent(TypeEventParser.NEW_BPM);
        });

        //decrease bpm event parameter alter
        this.strategy.put('<', (event) -> {
            long bpm = (event.getBpm() - event.BPM_VARIATION);
            event.setBpm(bpm > 0 ? bpm : event.getBpm());
            event.setTypeEvent(TypeEventParser.NEW_BPM);
        });

        //set instrument to MIDI AGOGOGOGOGOGOGOGOGO
        this.strategy.put(',', (event) ->{
           event.setInstrument(event.MIDI_AGOGO);
           event.setTypeEvent(TypeEventParser.NEW_INSTRUMENT);
        });

        //set instrument to MIDI Harmonica
        this.strategy.put('!', (event) -> {
            event.setInstrument(event.MIDI_HARMONICA);
            event.setTypeEvent(TypeEventParser.NEW_INSTRUMENT);
        });

        //set instrument to MIDI BAGPIPES
        MusicStrategy bagpipesAction = (event) -> {
            event.setInstrument(event.MIDI_BAGPIPES);
            event.setTypeEvent(TypeEventParser.NEW_INSTRUMENT);
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
                    event.setTypeEvent(TypeEventParser.NEW_INSTRUMENT);
                });
            }
            //set instrument to TUBULAR BELLS
            else {
                this.strategy.put(c, (event) -> {
                    event.setInstrument(event.MIDI_TUBULAR_BELLS);
                    event.setTypeEvent(TypeEventParser.NEW_INSTRUMENT);
                });
            }
        }

        //set instrument to TUBULAR BELLS
        this.strategy.put(';', (event) -> {
            event.setInstrument(event.MIDI_TUBULAR_BELLS);
            event.setTypeEvent(TypeEventParser.NEW_INSTRUMENT);
        });

        //default behaviour (if it wasn't a note don't play it, if it was, keep playing)
        this.strategy.put('`', defaultAction);


    }

    //may not be useful in this context
    private String[] parseLines(String entry) {
        String LINE_BREAK = "\\R";
        return entry.split(LINE_BREAK);
    }

    private List<ParserEvent>createPartitura(LineInput line) {
        List<ParserEvent> sheet = new ArrayList<>();

        //add 2 first parameters separately
        ParserEvent currentState = new ParserEvent(line.BPM(), -1, line.volume(), line.octave(), TypeEventParser.NEW_BPM);
        sheet.add(currentState);
        currentState.setInstrument(line.instrument());
        currentState.setTypeEvent(TypeEventParser.NEW_INSTRUMENT);
        sheet.add(new ParserEvent(currentState));

        for (char c : line.text().toCharArray()) {
            processCharacter(c, currentState);
            sheet.add(new ParserEvent(currentState));
        }
        return sheet;
    }

    public List<List<ParserEvent>> parseFullMusic(List<LineInput> lines) {
        List<List<ParserEvent>> completeSongEvents = new ArrayList<>();

        for (LineInput line : lines) {
            if (!line.text().trim().isEmpty()) { // Ignore Blank Lines
                List<ParserEvent> events = createPartitura(line);
                completeSongEvents.add(events);
            }
        }
        return completeSongEvents;
    }

    private void processCharacter(char c, ParserEvent event) {

        MusicStrategy action = this.strategy.getOrDefault(c, strategy.get('`'));

        if (action != null) {
            action.apply(event);
        }

        this.lastCharacter = c;
    }
}
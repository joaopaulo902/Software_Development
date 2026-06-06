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
                if(this.lastCharacter == 'b' && (note.getLabel() != 'C' && note.getLabel() != 'F')){
                    event.setNote(note.getNote() - 1);
                }
                else {
                    event.setNote(note.getNote());
                }
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
            int octave = event.getOctave() + 1;
            event.setOctave(octave <= event.MAX_OCTAVE ? octave : event.MAX_OCTAVE);
            event.setTypeEvent(TypeEventParser.GENERIC);
        });
        //decrease octave event parameter alter
        this.strategy.put('V', (event) -> {
            int octave = event.getOctave() - 1;
            event.setOctave(octave >= 0 ? octave : 0);
            event.setTypeEvent(TypeEventParser.GENERIC);
        });

        //doubles volume and does wrap around if it reaches limit
        this.strategy.put(' ', (event) -> {
            final int SATURATION = 100;
            long newVolume = 2 * event.getVolume();

            event.setVolume((newVolume <= SATURATION) ? newVolume : SATURATION);
            event.setTypeEvent(TypeEventParser.GENERIC);
        });

        //increase bpm event parameter alter
        this.strategy.put('>', (event) -> {
            long bpm = (event.getBpm() + event.BPM_VARIATION);
            event.setBpm( bpm > 0 ? bpm : Integer.MAX_VALUE);
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

        this.strategy.put('b', (event)->{
            this.lastCharacter = 'b';
            event.setTypeEvent(TypeEventParser.GENERIC);
        });

        //set instrument to TUBULAR BELLS
        this.strategy.put(';', (event) -> {
            event.setInstrument(event.MIDI_TUBULAR_BELLS);
            event.setTypeEvent(TypeEventParser.NEW_INSTRUMENT);
        });

        //default behaviour (if it wasn't a note don't play it, if it was, keep playing)
        this.strategy.put('`', defaultAction);


    }

/*    //may not be useful in this context since text is imported as LineInput
    private String[] parseLines(String entry) {
        String LINE_BREAK = "\\R";
        return entry.split(LINE_BREAK);
    }*/



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

    private List<ParserEvent>createPartitura(LineInput line) {
        List<ParserEvent> sheet = new ArrayList<>();

        //add 2 first parameters separately instrument then bpm
        ParserEvent currentState = new ParserEvent(-1, line.instrument(), line.volume(), line.octave(), TypeEventParser.NEW_INSTRUMENT);
        sheet.add(new ParserEvent(currentState));
        currentState.setBpm(line.BPM());
        currentState.setTypeEvent(TypeEventParser.NEW_BPM);
        sheet.add(new ParserEvent(currentState));
        String text = line.text();
        int startIndex = 0;

        if (text.startsWith("[")) {
            int closeBracketIndex = text.indexOf("]");


            if (closeBracketIndex > 1) {
                try {
                    String numberStr = text.substring(1, closeBracketIndex);
                    int silenceCount = Integer.parseInt(numberStr);

                    char silenceChar = '`';

                    for (int i = 0; i < silenceCount; i++) {
                        processCharacter(silenceChar, currentState);
                        sheet.add(new ParserEvent(currentState));
                    }

                    startIndex = closeBracketIndex + 1;

                } catch (NumberFormatException e) {
                    System.out.println("Ignoring silent command: invalid content within brackets");
                }
            }
        }

        char[] chars = text.toCharArray();
        for (int i = startIndex; i < chars.length; i++) {
            processCharacter(chars[i], currentState);
            sheet.add(new ParserEvent(currentState));
            this.lastCharacter = chars[i];
        }
        return sheet;
    }

    private void processCharacter(char c, ParserEvent event) {

        MusicStrategy action = this.strategy.getOrDefault(c, strategy.get('`'));

        if (action != null) {
            action.apply(event);
        }

        //this.lastCharacter = c;
    }
}
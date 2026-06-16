import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses each MusicSheet/Line to create a list of Parser Events
 */
public class Parser {
    private final Map<Character, MusicStrategy> strategy = new HashMap<>();
    private char lastCharacter;


    public Parser() {
        createFunctionMap();
    }

    /**
     * Fills strategy Mapping
     */
    private void createFunctionMap() {

        setNoteBehaviour();

        setOctaveBehaviour();

        setVolumeBehaviour();

        setBpmBehaviour();

        setInstrumentBehaviour();

        setLowerCaseCharacterBehaviour();

        setDefaultBehaviour();


    }

    private void setDefaultBehaviour() {
        //default action
        MusicStrategy defaultAction = (event) -> {
            if(Note.NoteEnum.containsByLabel(this.lastCharacter)) {
                event.setTypeEvent(ParserTypeEventEnum.NEW_NOTE);
            }
            else {
                event.setTypeEvent(ParserTypeEventEnum.SILENCE);
            }

        };

        //default behaviour (generate silent note)
        this.strategy.put('`', defaultAction);
    }


    void setLowerCaseCharacterBehaviour() {
        //se é uma nota em minúsculo, toca Silent Note
        for (Note.NoteEnum note : Note.NoteEnum.values()) {
            final char c = Character.toLowerCase(note.getLabel());
            if(c == 'b'){
                //enables flag for next note to be flat
                this.strategy.put(c, (event)-> event.setTypeEvent(ParserTypeEventEnum.GENERIC));
            }
            else{
                this.strategy.put(c, (event)-> event.setTypeEvent(ParserTypeEventEnum.SILENCE));
                this.lastCharacter = c;
            }
        }
    }
    private void setInstrumentBehaviour() {
        //set instrument to MIDI AGOGOGOGOGOGOGOGOGO
        this.strategy.put(',', (event) -> event.updateInstrument(Instrument.MIDI_AGOGO));

        //set instrument to TUBULAR BELLS
        this.strategy.put(';', (event) -> event.updateInstrument(Instrument.MIDI_TUBULAR_BELLS));

        //set instrument to MIDI Harmonica
        this.strategy.put('!', (event) -> event.updateInstrument(Instrument.MIDI_HARMONICA));

        //set instrument to MIDI BAGPIPES
        MusicStrategy bagpipesAction = (event) -> event.updateInstrument(Instrument.MIDI_BAGPIPES);
        this.strategy.put('I', bagpipesAction);
        this.strategy.put('O', bagpipesAction);
        this.strategy.put('U', bagpipesAction);
        for (char c = '0'; c <= '9'; c++) {
            final int value = Character.getNumericValue(c);

            if (value % 2 == 0) {
                this.strategy.put(c, (event) -> event.addValueToInstrumentEvent(value));
            }
            //set instrument to TUBULAR BELLS
            else {
                this.strategy.put(c, (event) -> event.updateInstrument(Instrument.MIDI_TUBULAR_BELLS));
            }
        }

    }

    private void setBpmBehaviour() {
        //increase bpm event
        this.strategy.put('>', ParserEvent::increaseBpmEvent);

        //decrease bpm event parameter alter
        this.strategy.put('<', ParserEvent::decreaseBpmEvent);
    }

    private void setVolumeBehaviour() {
        //doubles volume and does wrap around if it reaches limit
        this.strategy.put(' ', ParserEvent::doubleVolumeEvent);
    }

    private void setOctaveBehaviour() {
        //increase octave event parameter alter
        this.strategy.put('?', ParserEvent::increaseOctaveEvent);

        this.strategy.put('V', ParserEvent::decreaseOctaveEvent);
    }

    private void setNoteBehaviour() {
        for (Note.NoteEnum note : Note.NoteEnum.values()) {
            final char c = note.getLabel();
            strategy.put(c, (event) -> {
                if(this.lastCharacter == 'b' && (note.getLabel() != 'C' && note.getLabel() != 'F')){
                    event.setFlatNoteEvent(new Note(note.getNote()));
                }
                else {
                    event.setNoteEvent(note.getNote());
                }
                event.setTypeEvent(ParserTypeEventEnum.NEW_NOTE);
            });
        }


    }


    public List<List<ParserEvent>> parseFullMusic(List<LineInput> lines) {
        List<List<ParserEvent>> completeSongEvents = new ArrayList<>();

        for (LineInput line : lines) {
                List<ParserEvent> events = createSheet(line);
                completeSongEvents.add(events);
        }
        return completeSongEvents;
    }

    private List<ParserEvent> createSheet(LineInput line) {
        List<ParserEvent> sheet = new ArrayList<>();

        ParserEvent currentState = setPresets(line, sheet);

        String text = line.text();

        int startIndex = generateInitialSilentNotes(text, currentState, sheet);

        char[] inputTextChars = text.toCharArray();

        for (int i = startIndex; i < inputTextChars.length; i++) {
            processCharacter(inputTextChars[i], currentState);
            sheet.add(new ParserEvent(currentState));
            this.lastCharacter = inputTextChars[i];
        }
        return sheet;
    }

    private int generateInitialSilentNotes(String text, ParserEvent currentState, List<ParserEvent> sheet) {
        int startIndex = 0;

        if (text.startsWith("[")) {
            int closeBracketIndex = text.indexOf("]");


            if (closeBracketIndex > 1) { //checks if close bracket is a valid String position
                try {
                    String numberStr = text.substring(1, closeBracketIndex);
                    int silenceCount = Integer.parseInt(numberStr);

                    char silenceChar = 'a';

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
        return startIndex;
    }

    private static ParserEvent setPresets(LineInput line, List<ParserEvent> sheet) {
        //add BPM and instrument preset parameters separately to avoid bugs
        //add instrument
        ParserEvent currentState = new ParserEvent(new Bpm(-1), line.instrument(), line.volume(), line.octave(), ParserTypeEventEnum.NEW_INSTRUMENT);
        sheet.add(new ParserEvent(currentState));
        //add bpm
        currentState.setBpm(line.bpm());
        currentState.setTypeEvent(ParserTypeEventEnum.NEW_BPM);
        sheet.add(new ParserEvent(currentState));
        return currentState;
    }

    private void processCharacter(char c, ParserEvent event) {

        MusicStrategy action = this.strategy.getOrDefault(c, strategy.get('`'));

        if (action != null) {
            action.apply(event);
        }

        this.lastCharacter = c;
    }
}
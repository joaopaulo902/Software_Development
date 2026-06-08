import java.util.List;
import java.util.LinkedList;

/**
 * Converts Parser Events into Music Events
 */
public class ParserToMusicEvent {

    public static List<List<MusicEvent>> createMusicEvents(List<List<ParserEvent>> parserEvents){
        List<List<MusicEvent>> musicEvents = new LinkedList<>();

        for(List<ParserEvent> eventList: parserEvents){
            List<MusicEvent> musicEventList = new LinkedList<>();
            for(ParserEvent event: eventList){
                MusicEvent musicEvent = translate(event);
                if(musicEvent != null)
                    musicEventList.add(musicEvent);
            }
            musicEvents.add(musicEventList);
        }

        return musicEvents;
    }

    private static MusicEvent translate(ParserEvent parserEvent){
        MusicEvent currentEvent = new MusicEvent();
        switch(parserEvent.getTypeEvent()){
            case TypeEventParser.NEW_BPM:
                currentEvent.new_bpm((int) parserEvent.getBpm());
                break;
            case TypeEventParser.NEW_INSTRUMENT:
                currentEvent.new_instrument(parserEvent.getInstrument());
                break;
            case TypeEventParser.SILENCE:
                currentEvent.new_silence(parserEvent.getDuration());
                break;
            case TypeEventParser.NEW_NOTE:
                currentEvent.new_note( parserEvent.getAbsoluteNote(),(int) parserEvent.getVolume(), parserEvent.getDuration());
                break;


            case TypeEventParser.GENERIC:
            default:
                currentEvent = null;
                break;
        }
        return currentEvent;
    }
}

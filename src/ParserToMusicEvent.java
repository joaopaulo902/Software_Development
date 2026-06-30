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
            case ParserTypeEventEnum.NEW_BPM:
                currentEvent.new_bpm(parserEvent.getBpm().getValue());
                break;
            case ParserTypeEventEnum.NEW_INSTRUMENT:
                currentEvent.new_instrument(parserEvent.getInstrument().getValue());
                break;
            case ParserTypeEventEnum.SILENCE:
                currentEvent.new_silence(parserEvent.getDuration().getValue());
                break;
            case ParserTypeEventEnum.NEW_NOTE:
                currentEvent.new_note( parserEvent.getAbsoluteNote(), parserEvent.getVolume().getValue(), parserEvent.getDuration().getValue());
                break;


            case ParserTypeEventEnum.GENERIC:
            default:
                currentEvent = null;
                break;
        }
        return currentEvent;
    }
}

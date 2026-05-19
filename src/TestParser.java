import java.util.List;

public class TestParser {

    public static void functionality(String input){
        Parser parser = new Parser();

        List<List<MusicEvent>> song =  parser.parseFullMusic(input);

        for(List<MusicEvent> line : song){
            for(MusicEvent event : line){
                System.out.println("nota absoluta: " + event.getAbsoluteNote());
                System.out.println("instrumento: "+ event.getInstrument());
                System.out.println("BPM: "+ event.getBpm());
                System.out.println("volume: "+ event.getVolume());
            }

        }
    }

}

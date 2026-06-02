import java.util.List;

public class TestParser {

    public static void functionality(String input){
        Parser parser = new Parser();

        List<List<ParserEvent>> song =  parser.parseFullMusic(input);

        for(List<ParserEvent> line : song){
            for(ParserEvent event : line){
                System.out.println("nota absoluta: " + event.getAbsoluteNote());
                System.out.println("instrumento: "+ event.getInstrument());
                System.out.println("BPM: "+ event.getBpm());
                System.out.println("volume: "+ event.getVolume());
            }

        }
    }

}

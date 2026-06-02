import java.util.ArrayList;
import java.util.List;

// presets list for iterating through 4 main values specified in requirements

public class PresetTable {
    private record PresetLine (String text, String bpm, String volume, String instrument,String octave){}
    private final int NUMBER_DIFFERENT_PRESETS = 4;
    public final List<PresetLine> PRESETS = new ArrayList<>();

    PresetTable(){
        //add main voice
        PRESETS.add(new PresetLine("input text here", "120", "100", "0", "6"));
        PRESETS.add(new PresetLine("input text here", "120", "80", "20", "5"));
        PRESETS.add(new PresetLine("input text here", "120", "60", "6", "4"));
        PRESETS.add(new PresetLine("input text here", "120", "40", "71", "3"));
    }


}

import java.util.ArrayList;
import java.util.List;

/**
 * For different default presets for when adding new lines (works like a CSV)
 */
public class PresetTable {
    private final int NUMBER_DIFFERENT_PRESETS = 4;
    public final List<LinePreset> PRESETS = new ArrayList<>();

    PresetTable(){
        //add main voice
        PRESETS.add(new LinePreset("input text here", "120", "100", "6", "6"));
        PRESETS.add(new LinePreset("input text here", "120", "80", "20", "5"));
        PRESETS.add(new LinePreset("input text here", "120", "60", "0", "4"));
        PRESETS.add(new LinePreset("input text here", "120", "40", "70", "3"));
    }

    public LinePreset getPresetLine(int i){
        return this.PRESETS.get(i % NUMBER_DIFFERENT_PRESETS);
    }
}

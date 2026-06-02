import javax.swing.*;

public class MusicLine {
    JPanel rowPanel;
    JTextField songInput;
    JTextField bpmInput;
    JTextField volumeInput;
    JTextField instrumentInput;
    JTextField octaveInput;

    public MusicLine(JPanel rowPanel, JTextField songInput, JTextField bpmInput, JTextField volumeInput, JTextField instrumentInput, JTextField octaveInput) {
        this.rowPanel = rowPanel;
        this.songInput = songInput;
        this.bpmInput = bpmInput;
        this.volumeInput = volumeInput;
        this.instrumentInput = instrumentInput;
        this.octaveInput = octaveInput;
    }
}

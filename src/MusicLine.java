import javax.swing.*;

public class MusicLine {
    JPanel rowPanel;
    JTextField songInput;
    JTextField bpmInput;
    JTextField volumeInput;
    JTextField instrumentInput;

    public MusicLine(JPanel rowPanel, JTextField songInput, JTextField bpmInput, JTextField volumeInput, JTextField instrumentInput) {
        this.rowPanel = rowPanel;
        this.songInput = songInput;
        this.bpmInput = bpmInput;
        this.volumeInput = volumeInput;
        this.instrumentInput = instrumentInput;
    }
}

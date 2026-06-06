import javax.swing.*;

// object for storing musicLine front end unit (stackable)

public class MusicLine {
    JPanel rowPanel;
    JTextField songInput;
    JTextField volumeInput;
    JTextField instrumentInput;
    JTextField octaveInput;

    public MusicLine(JPanel rowPanel, JTextField songInput, JTextField volumeInput, JTextField instrumentInput, JTextField octaveInput) {
        this.rowPanel = rowPanel;
        this.songInput = songInput;
        this.volumeInput = volumeInput;
        this.instrumentInput = instrumentInput;
        this.octaveInput = octaveInput;
    }
}

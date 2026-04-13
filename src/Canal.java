import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import java.util.List;

public class Canal {//to do handle exceptions
    //constantes
    public static final int ERRO_NO_CANAL = -1;

    //metodos publicos
    public void open(int id, Track track)
    {
        faixa = track;
        channel_number = id;
        is_open = true;
        tick = 0;
        clear_faixa();
    }
    public void close()
    {
        channel_number = ERRO_NO_CANAL;
        tick = 0;
        is_open = false;
        clear_faixa();
    }
    public void set_eventos(List<Evento> eventos)
    {
        Event_treat tratador = new Event_treat();
        tratador.start(faixa, channel_number);
        tick = 0;
        if (!is_open){
            return;
        }
        if (eventos.isEmpty()) {
            return;
        }
        for (Evento evento : eventos) {
            tick += tratador.treatment(evento, tick);
        }
        used = true;
    }
    public boolean in_use()
    {
        return used;
    }
    public void clear_faixa()
    {
        for(int i = faixa.size() -  1; i >= 0; i--){
            faixa.remove(faixa.get(i));
        }
        used = false;
    }

    //Variaveis privadas
    private int channel_number;
    private Track faixa;
    private long tick = 0;
    private boolean is_open = false;
    private boolean used = false;

    //metodos privados

}

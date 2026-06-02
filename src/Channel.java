
import javax.sound.midi.Track;
import java.util.List;

public class Channel {//to do handle exceptions
    //constantes
    public static final int CHANNEL_ERROR = -1;

    //metodos publicos
    public void open(int id, Track track)
    {
        this.track = track;
        channel_number = id;
        is_open = true;
        tick = 0;
        clear_faixa();
    }
    public void close()
    {
        channel_number = CHANNEL_ERROR;
        tick = 0;
        is_open = false;
        clear_faixa();
    }
    public void set_eventos(List<MusicEvent> musicEventList)
    {
        MusicEventHandler tratador = new MusicEventHandler();
        tratador.start(track, channel_number);
        tick = 0;
        if (!is_open){
            return;
        }
        if (musicEventList.isEmpty()) {
            return;
        }
        for (MusicEvent evento : musicEventList) {
            tick = tratador.treatment(evento, tick);
        }
        used = true;
    }
    public boolean in_use()
    {
        return used;
    }
    public void clear_faixa()
    {
        for(int i = track.size() -  1; i >= 0; i--){
            track.remove(track.get(i));
        }
        used = false;
    }

    //Variaveis privadas
    private int channel_number;
    private Track track;
    private long tick = 0;
    private boolean is_open = false;
    private boolean used = false;

    //metodos privados

}

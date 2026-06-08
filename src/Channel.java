
import javax.sound.midi.Track;
import java.util.List;

/**
 * class for dealing with individual channels from channel list
 */

public class Channel {

    public static final int CHANNEL_ERROR = -1;

    public void open(int id, Track track) {
        this.track = track;
        channel_number = id;
        is_open = true;
        tick = 0;

        used = this.track != null && this.track.size() > 0;
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
        if (!is_open || track == null) {
            return;
        }

        if (musicEventList.isEmpty()) {
            return;
        }

        MusicEventHandler tratador = new MusicEventHandler();
        tratador.start(track, channel_number);
        tick = 0;

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
        if (track == null) {
            used = false;
            return;
        }

        for(int i = track.size() -  1; i >= 0; i--){
            track.remove(track.get(i));
        }
        used = false;
    }
    private int channel_number;
    private Track track;
    private long tick = 0;
    private boolean is_open = false;
    private boolean used = false;

}

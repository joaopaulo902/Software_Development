import javax.sound.midi.Track;
import java.util.List;

/**
 * Creates full Song for API
 */
public class MusicMaker {

    public static final int NUMBER_OF_TRACKS = 16;


    public void open(Track[] tracks)
    {
        channels = new Channel[NUMBER_OF_TRACKS];
        empty_it_is = true;

        for(int i = 0; i < NUMBER_OF_TRACKS; i++){
            channels[i] = new Channel();
            if (i < tracks.length) {
                channels[i].open(i, tracks[i]);

                if (channels[i].in_use()) {
                    empty_it_is = false;
                }
            }
        }
        is_open = true;
    }

    public void close()
    {
        for (int i = 0; i < NUMBER_OF_TRACKS; i++){
            channels[i].close();
        }
        is_open = false;
    }

    public void add_line(List<MusicEvent> es, int id)
    {
        if(es.isEmpty()){
            return;
        }
        channels[id].set_eventos(es);
        empty_it_is = false;
    }

    public void delete_line(int id)
    {
        channels[id].clear_faixa();
        for(int i = 0; i < NUMBER_OF_TRACKS; i++){
            if(channels[i].in_use()){
                return;
            }
        }
        empty_it_is = true;
    }

    public boolean is_open()
    {
        return is_open;
    }

    public boolean is_empty()
    {
        return empty_it_is;
    }


    private Channel[] channels;
    private boolean is_open = false;
    private boolean empty_it_is = true;


}

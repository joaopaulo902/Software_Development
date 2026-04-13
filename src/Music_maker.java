import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Track;
import java.util.List;

public class Music_maker {//to do: handle exceptions
    //constantes
    public static final int NUMBER_OF_TRACKS = 16;

    //metodos publicos
    public void open(Track[] tracks)
    {
        for(int i = 0; i < NUMBER_OF_TRACKS; i++){
            faixas[i] = new Canal();
            faixas[i].open(i, tracks[i]);
        }
        is_open = true;
    }

    public void close()
    {
        for (int i = 0; i < NUMBER_OF_TRACKS; i++){
            faixas[i].close();
        }
        is_open = false;
    }

    public void add_line(List<Evento> es)//adicionar funcao de substituicao de canal
    {
        if(es.size() == 0){
            return;
        }
        int onde = right_channel();
        if(onde==-1){
            return;
        }
        faixas[onde].set_eventos(es);
        empty_it_is = false;
    }

    public void delete_line(int id)
    {
        faixas[id].clear_faixa();
        for(int i = 0; i < NUMBER_OF_TRACKS; i++){
            if(faixas[i].in_use()){
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

    //variaveis privadas
    private Canal[] faixas;
    private boolean is_open = false;
    private boolean empty_it_is = true;

    //metodos privados
    private int right_channel()
    {
        int i = 0;
        while (faixas[i].in_use() == true &&  i < NUMBER_OF_TRACKS){
            i++;
        }
        if (i < NUMBER_OF_TRACKS){
            return i;
        }
        return -1;
    }
}

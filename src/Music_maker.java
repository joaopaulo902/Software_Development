import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Track;
import java.util.List;

public class Music_maker {
    public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException {

    }
    //constantes


    //metodos publicos
    public void open(int numero_de_canais)
    {
        numero_linhas = numero_de_canais;
        linhas = new Canal[numero_de_canais];
        is_open = true;
    }
    public void close()
    {
        is_open = false;
    }
    public void set_track(Track track)
    {
        pronto =  track;
    }
    public void add_line(List<Evento> es)//adicionar funcao de substituicao de canal
    {
        int onde;
        onde = right_channel();
        if(onde == numero_linhas){
            return;
        }
        linhas[onde].set_eventos(es);
    }
    public Track get_track()
    {
        for(int i = 0; i < numero_linhas; i++){
            if(linhas[i] == null){
                break;
            }
            if(linhas[i].in_use()){
                for(MidiEvent ev : linhas[i].get_channel_events()){
                    pronto.add(ev);
                }
            }
        }
        return pronto;
    }

    //variaveis privadas
    private Track pronto;
    private Canal[] linhas;
    private int numero_linhas;
    private boolean is_open = false;

    //metodos privados
    private int right_channel()
    {
        int i = 0;
        while(linhas[i] == null && i < numero_linhas){
            i++;
        }
        if(i == numero_linhas){
            i = 0;
            while(linhas[i].in_use() && i < numero_linhas){
                i++;
            }
        }
        return i;
    }
}

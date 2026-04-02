import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Track;
import java.util.List;

public class Music_maker {//to do: handle exceptions
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
        ready_it_is = false;
    }

    public void set_track(Track track)
    {
        if (!is_open){
            return;
        }
        faixa =  track;
    }
    public void add_line(List<Evento> es)//adicionar funcao de substituicao de canal
    {
        if (!is_open){
            return;
        }
        int onde;
        onde = right_channel();
        if(onde == numero_linhas){
            return;
        }
        if(!linhas[onde].in_use()){
            linhas[onde].open(onde);
        }
        linhas[onde].set_eventos(es);
    }
    public Track get_track()
    {
        if (!is_open){
            return null;
        }
        return faixa;
    }

    public boolean is_open()
    {
        return is_open;
    }

    public boolean is_ready() {
        if (!is_open){
            return false;
        }
        return ready_it_is;
    }

    public void ready(){
        if (!is_open){
            return;
        }
        for(int i = 0; i < numero_linhas; i++){
            if(linhas[i] == null){
                break;
            }
            if(linhas[i].in_use()){
                for(MidiEvent ev : linhas[i].get_channel_events()){
                    faixa.add(ev);
                }
            }
        }
        ready_it_is = true;
    }

    //variaveis privadas
    private Track faixa;
    private Canal[] linhas;
    private int numero_linhas;
    private boolean is_open = false;
    private boolean ready_it_is;

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

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.List;

public class Canal {
    //constantes
    public static int ERRO_NO_CANAL = -1;

    //metodos publicos
    public void open(int id)
    {
        channel_number = id;
        is_open = true;
        tick = 0;
        prontos.clear();
    }
    public void close()
    {
        channel_number = ERRO_NO_CANAL;
        int tick = 0;
        is_open = false;
        prontos.clear();
    }
    public void set_eventos(List<Evento> eventos)
    {
        if (eventos.size() == 0) {
            return;
        }
        for (Evento evento : eventos) {
            if(evento.get_comando() == Evento.INSTRUMENT_CHANGE){
                MidiEvent ev = new MidiEvent(nova_mensagem(evento, ShortMessage.PROGRAM_CHANGE), tick);
                prontos.add(ev);
            }
            else if(evento.get_comando() == Evento.INSTRUMENT_CHANGE){
                MidiEvent ev = new MidiEvent(nova_mensagem(evento, ShortMessage.NOTE_ON), tick);
                prontos.add(ev);
                Evento aux_off_event =  new Evento();
                aux_off_event.set_evento(Evento.OUT_OF_BOUNDS, evento.get_nota(), Evento.NON_APLICABLE, Evento.NON_APLICABLE);
                tick = tick + evento.get_duracao();
                MidiEvent ev1 = new MidiEvent(nova_mensagem(evento, ShortMessage.NOTE_OFF), tick);
            }
        }
    }
    public List<MidiEvent> get_channel_events()
    {
        return prontos;
    }
    public boolean in_use()
    {
        return is_open;
    }

    //Variaveis privadas
    private int channel_number;
    private long tick = 0;
    private boolean is_open = false;
    private List<MidiEvent> prontos;

    //metodos privados
    private ShortMessage nova_mensagem(Evento evento, int j_sound_comand){
        ShortMessage msg = new ShortMessage();
        try {
            msg.setMessage(j_sound_comand, channel_number, evento.get_nota(), evento.get_volume());
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return msg;
    }
}

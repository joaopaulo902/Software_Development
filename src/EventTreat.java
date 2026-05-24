import javax.sound.midi.*;

public class EventTreat {
    //constantes publicas


    //metodos publicos
    public void start(Track f, int c){
        faixa = f;
        channel_number = c;
        started = true;
    }

    public long treatment(Evento evento, long onde){
        if (!started) {
            return 0;
        }
        int instrument = Evento.INSTRUMENT_CHANGE;
        switch (evento.get_comando()){
            case Evento.INSTRUMENT_CHANGE:
                treat_instrument(evento, onde);
                return ZERO;
            case Evento.BPM_CHANGE:
                treat_bpm(evento, onde);
                return ZERO;
            case Evento.SILENCE:
                return treat_silence(evento.get_duracao(), onde);
            case Evento.NOTE:
                return treat_nota(evento, onde);
            default:
                return ZERO;
        }
    }

    //metodos privados
    private void treat_instrument(Evento evento, long onde){
        faixa.add(new MidiEvent(nova_mensagem_curta(evento, ShortMessage.PROGRAM_CHANGE), onde));
    }

    private void treat_bpm(Evento evento, long onde){
        int mpq = (int)(CONVERSION/evento.get_duracao());
        int msg_size = 3;
        byte[] tempo = new byte[msg_size];
        tempo[0] = (byte) ((mpq >> 16) & 0xFF);
        tempo[1] = (byte) ((mpq >> 8) & 0xFF);
        tempo[2] = (byte) (mpq & 0xFF);

        MetaMessage msg = null;
        try {
            msg = new MetaMessage(TEMPO_MSG, tempo, msg_size);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        faixa.add(new MidiEvent(msg, onde));
    }

    private long treat_silence(long how_long, long onde){
        return onde + how_long;
    }

    private long treat_nota(Evento evento, long onde){
        faixa.add(new MidiEvent(nova_mensagem_curta(evento, ShortMessage.NOTE_ON), onde));
        onde = onde + evento.get_duracao();
        faixa.add(new MidiEvent(nova_mensagem_curta(evento, ShortMessage.NOTE_OFF), onde));
        return onde;
    }

    private ShortMessage nova_mensagem_curta(Evento evento, int j_sound_comand){
        ShortMessage msg = new ShortMessage();
        try {
            msg.setMessage(j_sound_comand, channel_number, evento.get_nota(), evento.get_volume());
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return msg;
    }


    //constantes privadas
    private static final long ZERO = 0;
    private static final int CONVERSION = 60000000;
    private static final int TEMPO_MSG = 0x51;

    //variaveis privadas
    private Track faixa;
    private int channel_number;
    private boolean started;
}

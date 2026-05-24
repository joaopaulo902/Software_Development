import javax.sound.midi.*;

public class MusicEventHandler {
    //constantes publicas


    //metodos publicos
    public void start(Track f, int c){
        track = f;
        channel_number = c;
        started = true;
    }

    public long treatment(MusicEvent musicEvent, long when){
        if (!started) {
            return 0;
        }
        switch (musicEvent.get_command()){
            case MusicEvent.INSTRUMENT_CHANGE:
                treat_instrument(musicEvent, when);
                return when;
            case MusicEvent.BPM_CHANGE:
                treat_bpm(musicEvent, when);
                return when;
            case MusicEvent.SILENCE:
                return treat_silence(musicEvent.get_duration(), when);
            case MusicEvent.NEW_NOTE:

                return treat_nota(musicEvent, when);
            default:
                return when;
        }
    }

    //metodos privados
    private void treat_instrument(MusicEvent musicEvent, long when){
        track.add(new MidiEvent(nova_mensagem_curta(musicEvent, ShortMessage.PROGRAM_CHANGE), when));
    }

    private void treat_bpm(MusicEvent musicEvent, long when){
        int mpq = (int)(CONVERSION/musicEvent.get_bpm());
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
        track.add(new MidiEvent(msg, when));
    }

    private long treat_silence(double how_long, long when){
        return (long) (when + how_long * MusicBox.RESOLUTION);
    }

    private long treat_nota(MusicEvent evento, long when){
        track.add(new MidiEvent(nova_mensagem_curta(evento, ShortMessage.NOTE_ON), when));
        when = (long) (when + evento.get_duration() * MusicBox.RESOLUTION);
        track.add(new MidiEvent(nova_mensagem_curta(evento, ShortMessage.NOTE_OFF), when));
        return when;
    }

    private ShortMessage nova_mensagem_curta(MusicEvent musicEvent, int j_sound_comand){
        ShortMessage msg = new ShortMessage();
        try {
            msg.setMessage(j_sound_comand, channel_number, musicEvent.get_note(), musicEvent.get_volume());
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        return msg;
    }


    //constantes privadas
    private static final int CONVERSION = 60000000;
    private static final int TEMPO_MSG = 0x51;

    //variaveis privadas
    private Track track;
    private int channel_number;
    private boolean started;
}

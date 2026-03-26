public class Evento {
    //constantes
    public static int MAX_VALUE = 127;//define o maior valor dos campos
    public static int INSTRUMENT_CHANGE = 0;
    public static int NOTE = 1;
    public static int OUT_OF_BOUNDS = -1;
    public static int NON_APLICABLE = 0;

    //metodos publicos
    public int get_comando()
    {
        return comando;
    }
    public int get_nota()
    {
        return nota;
    }
    public int get_volume()
    {
        return volume;
    }
    public long get_duracao()
    {
        return duracao;
    }
    public void set_evento(int comando, int nota, int volume, long duracao)
    {
        set_comando(comando);
        set_nota(nota);
        set_volume(volume);
        set_duracao(duracao);
    }

    //dados privados
    private int comando;
    private int nota;
    private int volume;
    public long duracao;

    //metodos privados
    private void set_comando(int comando)
    {
        int c = comando;
        treat_comando(c);
        this.comando = c;
    }
    private void set_nota(int nota)
    {
        int n = nota;
        treat_nota(n);
        this.nota = nota;
    }
    private void set_volume(int volume)
    {
        int v = volume;
        treat_volume(v);
        this.volume = volume;
    }
    private void set_duracao(long duracao)
    {
        long d = duracao;
        treat_duracao(d);
        this.duracao = duracao;
    }
    private int treat_comando(int comando)
    {
        if (comando > NOTE) {
            return OUT_OF_BOUNDS;
        }
        return comando;
    }
    private int treat_nota(int nota)
    {
        if (nota > MAX_VALUE) {
            return OUT_OF_BOUNDS;
        }
        return nota;
    }
    private int treat_volume(int volume)
    {
        if (comando == INSTRUMENT_CHANGE) {
            return NON_APLICABLE;
        }
        if (volume > MAX_VALUE) {
            return OUT_OF_BOUNDS;
        }
        return volume;
    }
    private long treat_duracao(long duracao)
    {
        if(comando == INSTRUMENT_CHANGE) {
            return NON_APLICABLE;
        }
        if (duracao > MAX_VALUE) {
            return OUT_OF_BOUNDS;
        }
        return duracao;
    }

}

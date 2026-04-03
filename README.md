# Software_Development
Este repositório documentará o processo de confecção do projeto geral da disciplina de Desenvolvimento de Software (INF01120) na UFRGS 

### Requisitos do Projeto
 - **RF** - Requisito Funcional
 - **RNF** - Requisito Não Funcional

| # | Project Directives | Requirement type |
| :-----:  | :----------------   | :--------- |
| 1.      | O software deve ler o texto de entrada num campo texto na interface do software.| **RF** |
| 2.      | O software deve interpretar caractere por caractere, aplicando as regras de mapeamento definidas na Tabela de Especificação dos Caracteres. | **RF** |
| 3.      | O software deve gerar uma saída musical audível e reproduzível associada à entrada. | **RF** |
| 4.      | O software deve ser configurável pelo usuário (ex: Volume, velocidade de reprodução, Instrumento inicial, Oitava, número de faixas de reprodução). | **RF** |
| 5.      | Deve ser possível salvar o texto e a configuração atual | **RF** |
| 6.      | Deve ser possível pausar a reprodução da sequência | **RF** |
| 7.      | Deve ser possível terminar a execução do programa | **RF** |  
| 8.      | Deve ser possível navegar dentro do texto de uma faixa para além do que é mostrado na tela inicialmente | **RF** |
| 9.      | O código deve ser testável | **RNF** |
| 10.     | O software deve estar dividido modularmente. Módulos modelo: Boot, Geração da User Interface, Captação do Texto, Alteração das configurações pré-reprodução, Interpretação/Tradução do Texto, Geração de Eventos, Reprodução, Salvamento. | **RNF** |
| 11.     | - Deve se usar a Aplicação dos princípios SOLID: <br> - S – Responsabilidade Única: cada classe deve ter apenas uma razão para mudar. <br> - O – Aberto/Fechado: o sistema deve ser extensível para novas regras ou novos formatos de saída sem modificar o código existente. <br> - L – Substituição de Liskov: as subclasses devem poder substituir suas classes base sem alterar o comportamento esperado. <br> - I – Segregação de Interfaces: interfaces específicas são melhores que uma interface genérica. <br> - D – Inversão de Dependência: depender de abstrações, não de implementações concretas.| **RNF** |
| 12.     | O software deve ser extensível | **RNF** |
| 13.     | O código deve ser legível | **RNF** |

### Tabela de Especificação dos Caracteres

| Caractere | Comportamento |
| :-----------------------:   |   :---------   |
| A | Nota Lá |
| B | Nota Si |
| C | Nota Dó |
| D | Nota Ré |
| E | Nota Mi |
| F | Nota Fá |
| G | Nota Sol |
| H | Nota Si♭ |
| SPACE | Dobra o volume até a **Altura de Saturação** |
| ! | Troca o instrumento para **MIDI #24** (Bandoneon) |
| [I, O, U] | Troca instrumento para **MIDI #110** (Gaita de fole) |
| Dígito par | Troca instrumento para **MIDI #[Atual + Dígito lido]** |
| ; ou Dígito ímpar | Troca instrumento para **MIDI #15** (Tubular Bells) |
| **,** | Troca instrumento para **MIDI #114** (Agogô) |
|[a, b, c, d, e, f, g, h] | Gera silêncio ou pausa |
| **Else* | Repete a **NOTA** tocada imediatamente antes, else silêncio ou pausa |

### Bibliotecas/APIs inclusas (provisório)

 - javax.sound.midi; // uso do MIDI
 - java.util; // Uso de Classes padrão de projetos Java (eg: HashMap, Array, String, etc.)
 - javax.swing; //para a interface
 - java.io; // uso de arquivos para salvamentos

### Estrutura dos dados (Protótipo)
```
//Classe restrita a receber dados de config das notas
Public Class TrackConfig {
    public int volumeInicial;
    public int oitavaInicial;
    public int instrumentoInicial;
    public String texto; // O texto da caixa de texto da UI
}
```

```
public class TrackPanelData {
    private JTextArea inputTexto;
    private JTextField campoBpm;
    private JComboBox<String> comboInstrumento;
    private JSlider sliderVolume;
    private JSpinner spinnerOitava;
    public TrackConfig toConfig(); //recebe os dados das caixas e passa para TrackConfig
}
```

```
Public Class Song {
    private List<MusicBox> faixas;
    
    //sets globais para uma música
    private Sequencer sequencer;
    private Synthesizer synthesizer;
    
    private int bpm;//BPM da musica

    public boolean addFaixa(TrackConfig preset); //deverá adicionar uma faixa nova para cada caixa de texto do parser

    public boolean removeFaixa(); //deverá remover uma faixa caso o usuário queira
    
    public void generateSong();

    public void playSong(){ //da play em todos os canais ativos simultaneamente
    sequencer.start();
    }
    
    public void pauseSong(); //pausa todas as faixas simultaneamente
}
```

```
Public Class MusicBox {
    //painel de controle da MusicBox
    TrackConfig currentConfig;
    
    //java sound

    private MidiChannel channel; //canal em que a faixa será tocada

    private List<MusicEvent> partitura; //lista de notas e parâmetros


    public MusicBox(); //construtor da classe


    public boolean setPartitura(List<MusicEvent> partitura); //recebe a partitura gerada pelo parser

    private void prepararMusica(); //usa o parser para gerar a partitura 

    montarTrilha(Track midiTrack); //Transforma  MusicEvent em mensagens MIDI dentro de uma trilha do Sequenciador.

    private  montarNota(MusicEvent evento, Track track, long tick);


    public void fechar(); //fecha a music box

}
```

```
Public Class Parser {
    private char ultimoCaractere;
    private Map <Caracter, estrategia> strategy = new HashMap<>(); //usa uma tabela hash de funções para facilitar o parse dos caracteres
    
    public Parser();
    private void createFunctionMap();

    public List<MusicEvent> createPartitura(String entryText); //itera processarCaractere para gerar a partitura completa

    private void processarCaractere(char c, List<MusicEvent> partitura){ //decidi inserir a implementação deste, pois me senti orgulhoso de usar essa ideia
        MusicStrategy acao = estrategias.getOrDefault(c, estrategias.get('`')); //escolhe função associada ao caractere no mapa, ou, caso não o encontre, usa função padrão
        
        //gera o evento e o insere na partitura com base na acao

        ultimoCaractere = c;
        
    }
}
```

### Croqui da Interface
![Croqui](MainScreen.png)

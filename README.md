# Software_Development
## Este repositório documentará o processo de confecção do projeto geral da disciplina de Desenvolvimento de Software (INF01120) na UFRGS 

### Requisitos do Projeto
RF - Requisito Funcional
RNF - Requisito Não Funcional
| # | Project Directives | Requirement type |
| -----:  | :----------------   | --------- |
| 1.      | O software deve ler o texto de entrada num campo texto na interface do software.| RF |
| 2.      | O software deve interpretar caractere por caractere, aplicando as regras de mapeamento definidas. | RF |
| 3.      | O software deve gerar uma saída musical audível e reproduzível associada à entrada. | RF |
| 4.      | O software deve ser configurável pelo usuário (ex: Volume, velocidade de reprodução, Instrumento inicial, Oitava, número de faixas de reprodução). | RF |
| 5.      | Deve ser possível salvar o texto e a configuração atual | RF |
| 6.      | Deve ser possível pausar a reprodução da sequência | RF |
| 7.      | Deve ser possível terminar a execução do programa | RF |  
| 8.      | Deve ser possível navegar dentro do texto de uma faixa para além do que é mostrado na tela inicialmente | RF |
| 9.      | O código deve ser testável | RNF |
| 10.     | O software deve estar dividido modularmente. Módulos modelo: Boot, Geração da User Interface, Captação do Texto, Alteração das configurações pré-reprodução, Interpretação/Tradução do Texto, Geração de Eventos, Reprodução, Salvamento. | RNF |
| 11.     | - Deve se usar a Aplicação dos princípios SOLID: <br> - S – Responsabilidade Única: cada classe deve ter apenas uma razão para mudar. <br> - O – Aberto/Fechado: o sistema deve ser extensível para novas regras ou novos formatos de saída sem modificar o código existente. <br> - L – Substituição de Liskov: as subclasses devem poder substituir suas classes base sem alterar o comportamento esperado. <br> - I – Segregação de Interfaces: interfaces específicas são melhores que uma interface genérica. <br> - D – Inversão de Dependência: depender de abstrações, não de implementações concretas.| RNF |
| 12.     | O software deve ser extensível | RNF |
| 13.     | O código deve ser legível | RNF |

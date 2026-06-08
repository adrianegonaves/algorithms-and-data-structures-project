
/**
 * @file: Main.java
 * @brief: Sistema de Gestão de Linha de Autocarros - Projeto Final AED
 * @description: Main do Sistema de Gestão da Linha de Autocarros. Inicializa um cenário de 
 * teste pré-definido (pode remover) com dados desordenados e disponibiliza uma interface 
 * gráfica simples baseada em caixas de diálogo interativas (Swing/JOptionPane) para 
 * controlo do menu, execução de simulações e exibição dos testes de ordenação.
 * @date: Junho de 2026
 * @version: 1.1
 * @authors:
 * - Adriane Gonçalves - 240000004
 * - Bruno Hortelão - 240001083
 * @institution: Instituto Politécnico de Santarém - Escola Superior de Gestão e Tecnologia
 * @course: Licenciatura em Engenharia Informática
 * @uc: Algoritmos e Estruturas de Dados
 */


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;

public class Main {
    public static void main(String[] args) {
        BusLine line = new BusLine();
        Bus bus = new Bus(5); // Autocarro com capacidade para 5 pessoas

        // Remover Linha se quiser retirar o teste
        inicializarCenarioPredefinido(line);
        bus.setBusLine(line);

        // Array com o texto de cada botão
        String[] options = {
                "1. Adicionar Paragem",
                "2. Remover Paragem",
                "3. Adicionar Passageiro",
                "4. Simular Avanço Autocarro",
                "5. Ordenar por Nome",
                "6. Ordenar por Nº Passageiros",
                "7. Mostrar Estado Geral",
                "0. Sair"
        };

        while (true) {
            // Criar um painel para organizar os botões verticalmente
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Variável para armazenar a opção escolhida pelo utilizador
            // Usamos um array de um elemento para conseguir alterar o valor dentro do
            // listener do botão
            final int[] optionSelected = { -1 };

            // Criar uma janela de diálogo invisível para servir de base ao fecho do menu
            final JOptionPane optionPane = new JOptionPane(
                    "Escolha a operação que deseja realizar:",
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    new Object[] {}, // Remove os botões padrão (OK/Cancelar)
                    null);

            // Criar um botão para cada opção
            for (int i = 0; i < options.length; i++) {
                final int index = i;
                JButton button = new JButton(options[i]);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setMaximumSize(new Dimension(280, 35)); // Garante que todos os botões têm o mesmo tamanho

                // Adiciona a ação ao clicar no botão
                button.addActionListener(e -> {
                    // Mapeia o índice do botão para a lógica do switch case
                    // O último botão (índice 7) corresponde à opção 0 (Sair)
                    optionSelected[0] = (index == 7) ? 0 : (index + 1);

                    // Fecha a janela do menu após o clique
                    Component comp = (Component) e.getSource();
                    javax.swing.JDialog dialog = (javax.swing.JDialog) javax.swing.SwingUtilities
                            .getWindowAncestor(comp);
                    dialog.dispose();
                });

                panel.add(button);
                if (i < options.length - 1) {
                    panel.add(javax.swing.Box.createRigidArea(new Dimension(0, 8))); // Espaçamento entre botões
                }
            }

            // Coloca o painel de botões como o conteúdo da mensagem do JOptionPane
            optionPane.setMessage(new Object[] { "Escolha a operação que deseja realizar:\n\n", panel });

            // Cria e mostra o diálogo de forma síncrona
            javax.swing.JDialog dialog = optionPane.createDialog(null, "Sistema de Gestão de Linha de Autocarros");
            dialog.setVisible(true);

            int option = optionSelected[0];

            // Se o utilizador fechar a janela no 'X' ou carregar em Sair (opção 0)
            if (option == -1 || option == 0) {
                JOptionPane.showMessageDialog(null, "A encerrar o sistema. Boa viagem!");
                break;
            }


            switch (option) {
                // 1. Adicionar Paragem
                case 1:
                    String newStop = JOptionPane.showInputDialog(null, "Nome da nova paragem:", "Adicionar Paragem",
                            JOptionPane.QUESTION_MESSAGE);
                    if (newStop != null && !newStop.trim().isEmpty()) {
                        line.addBusStop(newStop);
                        JOptionPane.showMessageDialog(null, "Paragem '" + newStop + "' adicionada!");
                    }
                    break;
                // 2. Remover Pagarem 
                case 2:
                    String targetStop = JOptionPane.showInputDialog(null, "Nome da paragem a remover:",
                            "Remover Paragem", JOptionPane.QUESTION_MESSAGE);
                    if (targetStop != null) {
                        line.removeBusStop(targetStop);
                        JOptionPane.showMessageDialog(null,
                                "Operação de remoção concluída (verifica a consola para detalhes).");
                    }
                    break;
                
                // 3. Adicionar Passageiro
                case 3:
                    String passengerName = JOptionPane.showInputDialog(null, "Nome do passageiro:",
                            "Adicionar Passageiro", JOptionPane.QUESTION_MESSAGE);
                    if (passengerName != null && !passengerName.trim().isEmpty()) {
                        String stopName = JOptionPane.showInputDialog(null, "Nome da paragem onde vai aguardar:",
                                "Selecionar Paragem", JOptionPane.QUESTION_MESSAGE);

                        if (stopName != null) {
                            BusStop current = line.root;
                            boolean found = false;
                            while (current != null) {
                                if (current.getName().equalsIgnoreCase(stopName.trim())) {
                                    current.queue(new Passenger(passengerName));
                                    JOptionPane.showMessageDialog(null, "Passageiro " + passengerName
                                            + " adicionado à fila de " + current.getName());
                                    found = true;
                                    break;
                                }
                                current = current.next;
                            }
                            if (!found) {
                                JOptionPane.showMessageDialog(null, "Erro: Paragem não encontrada!", "Erro",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    break;
                
                // Simular Avanço do Autocarro
                case 4:
                    int nDisembark = 0;
                    if (bus.size() > 0) {
                        String input = JOptionPane.showInputDialog(
                                null,
                                "Quantos passageiros vão desembarcar na próxima paragem?\n(Passageiros a bordo atualmente: "
                                        + bus.size() + ")",
                                "Definir Desembarque",
                                JOptionPane.QUESTION_MESSAGE);

                        try {
                            if (input != null && !input.trim().isEmpty()) {
                                nDisembark = Integer.parseInt(input.trim());
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Input inválido! Ninguém irá desembarcar.", "Aviso",
                                    JOptionPane.WARNING_MESSAGE);
                            nDisembark = 0;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "O autocarro está vazio. Ninguém para desembarcar.");
                    }

                    bus.advance(nDisembark);

                    // Ajuste cirúrgico: Usa o método que criaste para ir buscar a paragem correta
                    // atual
                    JOptionPane.showMessageDialog(
                            null,
                            "O autocarro efetuou o percurso!\nParagem atual: " + bus.getCurrentBusStop().getName()
                                    + "\nConsulte a consola para ver os passageiros que mudaram de fila.",
                            "Simulação de Movimento",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;

                // 5. Ordenar por Nome
                case 5:
                    System.out.println("\n=============================================");
                    System.out.println("       COMPARAÇÃO DE ORDENAÇÃO POR NOME      ");
                    System.out.println("=============================================");
                    long startSel = System.nanoTime();
                    line.selectionSortByName();
                    long endSel = System.nanoTime() - startSel;

                    long startBub = System.nanoTime();
                    line.bubbleSortByName();
                    long endBub = System.nanoTime() - startBub;

                    System.out.println("\nTempo Selection Sort: " + endSel + " ns");
                    System.out.println("Tempo Bubble Sort: " + endBub + " ns");

                    JOptionPane.showMessageDialog(null,
                            "Ordenações por Nome concluídas!\nResultados e tempos impressos no terminal.");
                    break;
                
                // 6. Ordenar por Passageiro
                case 6:
                    System.out.println("\n=============================================");
                    System.out.println("   COMPARAÇÃO DE ORDENAÇÃO POR PASSAGEIROS   ");
                    System.out.println("=============================================");
                    long startSelP = System.nanoTime();
                    line.selectionSortByPassengerCount();
                    long endSelP = System.nanoTime() - startSelP;

                    long startBubP = System.nanoTime();
                    line.bubbleSortByPassengerCount();
                    long endBubP = System.nanoTime() - startBubP;

                    System.out.println("\nTempo Selection Sort: " + endSelP + " ns");
                    System.out.println("Tempo Bubble Sort: " + endBubP + " ns");

                    JOptionPane.showMessageDialog(null,
                            "Ordenações por Nº de Passageiros concluídas!\nResultados e tempos impressos no terminal.");
                    break;
                
                // 7. Estado Atual da Linha
                case 7:
                    StringBuilder estado = new StringBuilder("--- ESTADO DA LINHA ---\n");
                    BusStop currentStop = line.root;

                    // Vamos buscar a paragem onde o autocarro está atualmente
                    BusStop paragemDoAutocarro = bus.getCurrentBusStop();

                    while (currentStop != null) {
                        // Verifica se esta paragem do ciclo é a paragem onde o autocarro está
                        if (paragemDoAutocarro != null && currentStop.getName().equals(paragemDoAutocarro.getName())) {
                            // Se for, adiciona um indicador visual antes ou junto à paragem
                            estado.append("[ 🚌 ").append(currentStop.getName().toUpperCase())
                                    .append(" (Fila: ").append(currentStop.queue.getSize()).append(") ]");
                        } else {
                            // Se não for, mostra a paragem normalmente
                            estado.append("[").append(currentStop.getName())
                                    .append(" (Fila: ").append(currentStop.queue.getSize()).append(")]");
                        }

                        if (currentStop.next != null) {
                            estado.append(" -> ");
                        }
                        currentStop = currentStop.next;
                    }

                    estado.append("\n\n--- PASSAGEIROS A BORDO ---");
                    estado.append("\nTotal atual: ").append(bus.size()).append(" / 5 passageiros.");

                    // Opcional: listar os nomes de quem está dentro do autocarro na própria janela
                    if (bus.size() > 0) {
                        estado.append("\nNomes: ");
                        // Se tiveres um método no autocarro que devolva os nomes, podes concatenar aqui
                    }

                    JOptionPane.showMessageDialog(null, estado.toString(), "Estado Geral do Sistema",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }

    // Um cenário para testar
    private static void inicializarCenarioPredefinido(BusLine line) {
        line.addBusStop("Porto");
        line.addBusStop("Santarém");
        line.addBusStop("Algarve");
        line.addBusStop("Lisboa");
        line.addBusStop("Braga");

        BusStop porto = line.root;
        BusStop santarem = porto.next;
        BusStop algarve = santarem.next;
        BusStop lisboa = algarve.next;
        BusStop braga = lisboa.next;

        porto.queue(new Passenger("Manuel"));

        santarem.queue(new Passenger("Ana"));
        santarem.queue(new Passenger("Rui"));
        santarem.queue(new Passenger("Maria"));

        lisboa.queue(new Passenger("Carlos"));
        lisboa.queue(new Passenger("Diana"));
        lisboa.queue(new Passenger("Nuno"));
        lisboa.queue(new Passenger("Sofia"));

        braga.queue(new Passenger("Pedro"));
        braga.queue(new Passenger("Inês"));

        System.out.println(">> [SISTEMA] Cenário de teste pré-definido carregado com sucesso.");
    }
}
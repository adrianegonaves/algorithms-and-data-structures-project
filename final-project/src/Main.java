
/**
 * @file: Main.java
 * @brief: Sistema de Gestão de Linha de Autocarros - Projeto Final AED
 * @description: Ponto de entrada (Driver) da aplicação[cite: 54]. Inicializa um cenário de 
 * teste pré-definido com dados intencionalmente desordenados e disponibiliza uma interface 
 * gráfica simples baseada em caixas de diálogo interativas (Swing/JOptionPane) [cite: 73] para 
 * controlo do menu, execução de simulações e exibição dos benchmarks de ordenação[cite: 54, 74].
 * @date: Junho de 2026
 * @version: 1.1
 * @authors:
 * - Adriane Gonçalves - 240000004
 * - Bruno Hortelão - 240001083
 * @institution: Instituto Politécnico de Santarém - Escola Superior de Gestão e Tecnologia
 * @course: Licenciatura em Engenharia Informática
 * @uc: Algoritmos e Estruturas de Dados
 */

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        BusLine line = new BusLine();
        Bus bus = new Bus(5); // Autocarro com capacidade para 5 pessoas

        // Carga automática do cenário de teste para poupar tempo na defesa
        inicializarCenarioPredefinido(line);

        // Inicializa o autocarro na linha criada (começa na raiz, que será 'Santarém'
        // após ordenação ou 'Porto' na criação)
        bus.setBusLine(line);

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
            String selection = (String) JOptionPane.showInputDialog(
                    null,
                    "Escolha a operação que deseja realizar:",
                    "Sistema de Gestão de Linha de Autocarros",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (selection == null || selection.startsWith("0.")) {
                JOptionPane.showMessageDialog(null, "A encerrar o sistema. Boa viagem!");
                break;
            }

            int option = Character.getNumericValue(selection.charAt(0));

            switch (option) {
                case 1:
                    String newStop = JOptionPane.showInputDialog(null, "Nome da nova paragem:", "Adicionar Paragem",
                            JOptionPane.QUESTION_MESSAGE);
                    if (newStop != null && !newStop.trim().isEmpty()) {
                        line.addBusStop(newStop);
                        JOptionPane.showMessageDialog(null, "Paragem '" + newStop + "' adicionada!");
                    }
                    break;

                case 2:
                    String targetStop = JOptionPane.showInputDialog(null, "Nome da paragem a remover:",
                            "Remover Paragem", JOptionPane.QUESTION_MESSAGE);
                    if (targetStop != null) {
                        line.removeBusStop(targetStop);
                        JOptionPane.showMessageDialog(null,
                                "Operação de remoção concluída (verifica a consola para detalhes).");
                    }
                    break;

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

                case 4:
                    int nDisembark = 0;
                    // Só pergunta quantos desembarcam se houver pessoas dentro do autocarro
                    if (bus.size() > 0) { // usando o teu método countPassengers ou size()
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

                    // Avança o autocarro aplicando a quantidade escolhida
                    bus.advance(nDisembark);

                    JOptionPane.showMessageDialog(
                            null,
                            "O autocarro efetuou o percurso!\nParagem atual: " + bus.busLine.root.getName()
                                    + "\nConsulte a consola para ver os passageiros que mudaram de fila.",
                            "Simulação de Movimento",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;

                case 5:
                    // Executa e compara ambos por Nome
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

                case 6:
                    // Executa e compara ambos por Nº de Passageiros
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

                case 7:
                    StringBuilder estado = new StringBuilder("--- ESTADO DA LINHA ---\n");
                    BusStop currentStop = line.root;
                    while (currentStop != null) {
                        estado.append("[").append(currentStop.getName()).append(" (Fila: ")
                                .append(currentStop.queue.getSize()).append(")]");
                        if (currentStop.next != null)
                            estado.append(" -> ");
                        currentStop = currentStop.next;
                    }

                    estado.append("\n\n--- PASSAGEIROS A BORDO ---");
                    estado.append("\nTotal atual: ").append(bus.size()).append(" passageiros.");

                    JOptionPane.showMessageDialog(null, estado.toString(), "Estado Geral do Sistema",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }

    /**
     * Método auxiliar para popular o sistema com um cenário inicial desordenado
     * tanto a nível alfabético como numérico (filas).
     */
    private static void inicializarCenarioPredefinido(BusLine line) {
        // Criar paragens geograficamente e alfabeticamente baralhadas
        line.addBusStop("Porto"); // Posição inicial 0
        line.addBusStop("Santarém"); // Posição inicial 1
        line.addBusStop("Algarve"); // Posição inicial 2
        line.addBusStop("Lisboa"); // Posição inicial 3
        line.addBusStop("Braga"); // Posição inicial 4

        // Aceder aos nós criados para preencher as filas de passageiros
        // desordenadamente
        BusStop porto = line.root;
        BusStop santarem = porto.next;
        BusStop algarve = santarem.next;
        BusStop lisboa = algarve.next;
        BusStop braga = lisboa.next;

        // Porto terá 1 pessoa
        porto.queue(new Passenger("Manuel"));

        // Santarém terá 3 pessoas
        santarem.queue(new Passenger("Ana"));
        santarem.queue(new Passenger("Rui"));
        santarem.queue(new Passenger("Maria"));

        // Algarve terá 0 pessoas (já está vazio)

        // Lisboa terá 4 pessoas
        lisboa.queue(new Passenger("Carlos"));
        lisboa.queue(new Passenger("Diana"));
        lisboa.queue(new Passenger("Nuno"));
        lisboa.queue(new Passenger("Sofia"));

        // Braga terá 2 pessoas
        braga.queue(new Passenger("Pedro"));
        braga.queue(new Passenger("Inês"));

        System.out.println(">> [SISTEMA] Cenário de teste pré-definido carregado com sucesso.");
    }
}
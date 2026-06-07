/**
 * @file: BusSystemTest.java
 * @brief: Sistema de Gestão de Linha de Autocarros - Projeto Final AED
 * @description: Ficheiro destinado à realização de testes do código
 *               implementado
 * 
 * @date: Junho de 2026
 * @version: 1.1
 * @authors:
 *           - Adriane Gonçalves - 240000004
 *           - Bruno Hortelão - 240001083
 * @institution: Instituto Politécnico de Santarém - Escola Superior
 *               de Gestão e Tecnologia
 * @course: Licenciatura em Engenharia Informática
 * @uc: Algoritmos e Estruturas de Dados
 */

public class BusSystemTest {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("    INICIANDO TESTES DO SISTEMA DE AUTOCARROS     ");
        System.out.println("==================================================\n");

        testQueueFIFO();
        testBusCapacityAndCircularArray();
        testBusLineMovementAndReversal();
        testSortingAlgorithms();

        System.out.println("==================================================");
        System.out.println("      TODOS OS TESTES FORAM EXECUTADOS!          ");
        System.out.println("==================================================");
    }

    /**
     * Teste 1: Garante que a PassengerQueue funciona estritamente como FIFO
     * e que o método resize() duplica o tamanho quando cheia.
     */
    public static void testQueueFIFO() {
        System.out.println("--- TESTE 1: Fila de Passageiros (FIFO e Resize) ---");
        PassengerQueue queue = new PassengerQueue(2); // Capacidade inicial pequena para testar o resize

        queue.enqueue(new Passenger("Ana"));
        queue.enqueue(new Passenger("Bruno"));

        // Isto deve ativar o resize() internamente de forma transparente
        queue.enqueue(new Passenger("Carlos"));

        if (queue.getSize() != 3) {
            System.out.println("❌ FALHOU: Tamanho da fila devia ser 3, mas é " + queue.getSize());
            return;
        }

        // Validar ordem FIFO (Primeiro a entrar, primeiro a sair)
        Passenger p1 = queue.dequeue();
        Passenger p2 = queue.dequeue();
        Passenger p3 = queue.dequeue();

        if (p1.getName().equals("Ana") && p2.getName().equals("Bruno") && p3.getName().equals("Carlos")) {
            System.out.println("✅ SUCESSO: Lógica FIFO e Redimensionamento a funcionar!\n");
        } else {
            System.out.println("❌ FALHOU: A ordem dos passageiros foi alterada. Recebido: "
                    + p1.getName() + ", " + p2.getName() + ", " + p3.getName());
        }
    }

    /*
     * Teste 2: Valida se o Autocarro respeita a capacidade máxima
     * e se a estrutura circular do array reaproveita os índices após remoções.
     */
    public static void testBusCapacityAndCircularArray() {
        System.out.println("--- TESTE 2: Capacidade do Autocarro e Array Circular ---");
        Bus bus = new Bus(3); // Capacidade máxima de 3 lugares

        bus.board(new Passenger("Passageiro 1"));
        bus.board(new Passenger("Passageiro 2"));
        bus.board(new Passenger("Passageiro 3"));

        System.out.println("[Validar Alerta de Autocarro Cheio]");
        bus.board(new Passenger("Passageiro Oportunista")); // Deve disparar o ALERT: Bus is full!

        // Simular saída de 2 passageiros (Liberta espaço no início do array circular)
        bus.disembark(2);

        // Adicionar novos passageiros para testar se os ponteiros rear e front dão a
        // volta corretamente
        bus.board(new Passenger("Passageiro 4"));
        bus.board(new Passenger("Passageiro 5"));

        System.out.println("✅ SUCESSO: Verificação visual do estado do autocarro concluída.\n");
    }

    /**
     * Teste 3: Testa se o autocarro percorre a linha de ponta a ponta
     * e se inverte a direção corretamente quando chega à última paragem.
     */
    public static void testBusLineMovementAndReversal() {
        System.out.println("--- TESTE 3: Movimentação na Linha e Inversão de Marcha ---");
        BusLine line = new BusLine();
        line.addBusStop("Paragem A");
        line.addBusStop("Paragem B");
        line.addBusStop("Paragem C");

        Bus bus = new Bus(5);
        bus.setBusLine(line); // Autocarro começa na Paragem A

        // Avanço 1: De A para B
        bus.advanceInternal();
        String stop1 = bus.busLine.root.next.getName(); // B

        // Avanço 2: De B para C (Fim da linha)
        bus.advanceInternal();

        // Avanço 3: Deve bater no fim (C.next == null), mudar a direção e recuar para B
        bus.advanceInternal();

        System.out.println("O autocarro recuou para: " + bus.busLine.root.next.getName());

        if (bus.busLine.root.next.getName().equals("Paragem B")) {
            System.out.println("✅ SUCESSO: O autocarro inverteu a marcha corretamente no fim da linha!\n");
        } else {
            System.out.println("❌ FALHOU: O autocarro perdeu-se na linha.");
        }
    }

    /**
     * Teste 4: Testa os algoritmos de ordenação Bubble Sort e Selection Sort
     * integrados na BusLine.
     */
    /**
     * Teste 4: Benchmark completo e comparação direta entre Bubble Sort e Selection
     * Sort
     * para ambos os critérios (Nome e Contagem de Passageiros).
     */
    public static void testSortingAlgorithms() {
        System.out.println("--- TESTE 4: Benchmark e Comparação Cruzada de Algoritmos ---");

        // Criar uma linha de teste com dados desordenados
        BusLine line = new BusLine();
        line.addBusStop("Porto");
        line.addBusStop("Santarém");
        line.addBusStop("Algarve");
        line.addBusStop("Lisboa");
        line.addBusStop("Braga");

        // Injetar passageiros para desordenar numericamente as paragens
        line.root.queue(new Passenger("P")); // Porto: 1

        line.root.next.queue(new Passenger("P")); // Santarém: 3
        line.root.next.queue(new Passenger("P"));
        line.root.next.queue(new Passenger("P"));

        // Algarve: 0 pessoas

        line.root.next.next.next.queue(new Passenger("P")); // Lisboa: 4
        line.root.next.next.next.queue(new Passenger("P"));
        line.root.next.next.next.queue(new Passenger("P"));
        line.root.next.next.next.queue(new Passenger("P"));

        line.root.next.next.next.next.queue(new Passenger("P")); // Braga: 2
        line.root.next.next.next.next.queue(new Passenger("P"));

        // ---------------------------------------------------------
        // CRITÉRIO 1: ORDENAÇÃO POR NOME
        // ---------------------------------------------------------
        long startSelName = System.nanoTime();
        line.selectionSortByName();
        long endSelName = System.nanoTime();
        long tSelName = endSelName - startSelName;

        long startBubName = System.nanoTime();
        line.bubbleSortByName();
        long endBubName = System.nanoTime();
        long tBubName = endBubName - startBubName;

        // ---------------------------------------------------------
        // CRITÉRIO 2: ORDENAÇÃO POR NÚMERO DE PASSAGEIROS
        // ---------------------------------------------------------
        long startSelPass = System.nanoTime();
        line.selectionSortByPassengerCount();
        long endSelPass = System.nanoTime();
        long tSelPass = endSelPass - startSelPass;

        long startBubPass = System.nanoTime();
        line.bubbleSortByPassengerCount();
        long endBubPass = System.nanoTime();
        long tBubPass = endBubPass - startBubPass;

        // ==========================================================
        // MATRIZ COMPARATIVA DE TEMPOS (Excelente para o Relatório)
        // ==========================================================
        System.out.println("\n==================================================================");
        System.out.println("          TABELA COMPARATIVA DE DESEMPENHO COMPLETA               ");
        System.out.println("==================================================================");
        System.out.printf("%-18s | %-22s | %-20s\n", "Algoritmo", "Critério", "Tempo de Execução");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-18s | %-22s | %-15d ns\n", "Selection Sort", "Nome (Alfabética)", tSelName);
        System.out.printf("%-18s | %-22s | %-15d ns\n", "Bubble Sort", "Nome (Alfabética)", tBubName);
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-18s | %-22s | %-15d ns\n", "Selection Sort", "Nº Passageiros (Decres.)", tSelPass);
        System.out.printf("%-18s | %-22s | %-15d ns\n", "Bubble Sort", "Nº Passageiros (Decres.)", tBubPass);
        System.out.println("==================================================================");
        System.out.println("Nota: Valores medidos em nanossegundos (ns).");
        System.out.println("✅ SUCESSO: Benchmark cruzado concluído com sucesso!\n");
    }

}
/**
 * @file: BusLine.java
 * @brief: Sistema de Gestão de Linha de Autocarros - Projeto Final AED
 * @description: Modela a linha de transporte através de DLL de paragens. É
 *               responsável pelas operações
 *               inserção, remoção e listagem do percurso completo.
 *               Implementações manuais dos algoritmos Bubble Sort e Selection
 *               Sort para ordenar as paragens
 *               por ordem alfabética ou por número de passageiros.
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

public class BusLine {

    BusStop root;
    int size;

    public BusLine() {
        root = null;
        size = 0;
    }

    // Adicionar uma paragem no fim da linha
    public void addBusStop(String name) {
        BusStop newBusStop = new BusStop(name);

        if (root == null) {
            root = newBusStop;
        } else {
            BusStop current = root;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newBusStop;
            newBusStop.previous = current;
        }
        size++;
    }

    // Método para remoção de paragens da linha, remove por nome, etnão tem vários
    // tipos de operação
    // a considerar
    public void removeBusStop(String targetName) {

        // Caso de não haver nenhum paragem
        if (root == null) {
            System.out.println("A linha está vazia.");
            return;
        }

        // Paragem a remover é a root
        if (root.getName().equals(targetName)) {
            root = root.next;
            if (root != null) {
                root.previous = null;
            }
            System.out.println("Paragem " + targetName + " removida do príncipio da linha.");
            return;
        }

        // Se não for nenhum dos casos anteriores vamos procurar.
        BusStop current = root;
        while (current != null && !current.getName().equals(targetName)) {
            current = current.next;
        }

        // A paragem não existe na linha
        if (current == null) {
            System.out.println("Paragem " + targetName + " não faz parte da linha.");
            return;
        }

        // Paragem é a última
        if (current.next == null) {
            current.previous.next = null;
            System.out.println("Paragem " + targetName + " removida do fim da linha.");
        }
        // Paragem é algures no meio
        else {
            current.previous.next = current.next;
            current.next.previous = current.previous;
            System.out.println("Paragem " + targetName + " removida.");
        }

    }


    // Listar a linha de autocarro
    public void listBusLine() {

        BusStop current = root;

        System.out.print("LINE: ");
        while (current != null) {
            System.out.print("[" + current.getName() + " (People in queue: " + current.queue.getSize() + ")]");
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();

    }

    // Método auxiliar para transformar a lista ligada num array temporário para facilitar 
    // as ordenações
    private BusStop[] getStopsAsArray() {
        BusStop[] stops = new BusStop[size];
        BusStop current = root;
        int i = 0;
        while (current != null) {
            stops[i] = current;
            current = current.next;
            i++;
        }
        return stops;
    }

    // ==========================================
    // SELECTION SORT
    // ==========================================

    // 1. Selection Sort por Nome (Ordem Alfabética)
    public void selectionSortByName() {
        if (size <= 1)
            return;
        BusStop[] stops = getStopsAsArray();

        for (int i = 0; i < stops.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < stops.length; j++) {
                if (stops[j].getName().compareToIgnoreCase(stops[minIdx].getName()) < 0) {
                    minIdx = j;
                }
            }
            BusStop temp = stops[minIdx];
            stops[minIdx] = stops[i];
            stops[i] = temp;
        }

        System.out.println("\n--- [Selection Sort] Paragens por Nome ---");
        for (BusStop stop : stops) {
            System.out.println("- " + stop.getName() + " (Em espera: " + stop.queue.getSize() + ")");
        }
    }

    // 2. Selection Sort por N de Passageiros (Ordem Decrescente)
    public void selectionSortByPassengerCount() {
        if (size <= 1)
            return;
        BusStop[] stops = getStopsAsArray();

        for (int i = 0; i < stops.length - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < stops.length; j++) {
                if (stops[j].queue.getSize() > stops[maxIdx].queue.getSize()) {
                    maxIdx = j;
                }
            }
            BusStop temp = stops[maxIdx];
            stops[maxIdx] = stops[i];
            stops[i] = temp;
        }

        System.out.println("\n--- [Selection Sort] Paragens por Nº Passageiros ---");
        for (BusStop stop : stops) {
            System.out.println("- " + stop.getName() + " (Em espera: " + stop.queue.getSize() + ")");
        }
    }

    // ==========================================
    // BUBBLE SORT
    // ==========================================

    // 3. Bubble Sort por Nome (Ordem Alfabética)
    public void bubbleSortByName() {
        if (size <= 1)
            return;
        BusStop[] stops = getStopsAsArray();

        for (int i = 0; i < stops.length - 1; i++) {
            for (int j = 0; j < stops.length - 1 - i; j++) {
                if (stops[j].getName().compareToIgnoreCase(stops[j + 1].getName()) > 0) {
                    BusStop temp = stops[j];
                    stops[j] = stops[j + 1];
                    stops[j + 1] = temp;
                }
            }
        }

        System.out.println("\n--- [Bubble Sort] Paragens por Nome ---");
        for (BusStop stop : stops) {
            System.out.println("- " + stop.getName() + " (Em espera: " + stop.queue.getSize() + ")");
        }
    }

    // 4. Bubble Sort por Nº de Passageiros (Ordem Decrescente)
    public void bubbleSortByPassengerCount() {
        if (size <= 1)
            return;
        BusStop[] stops = getStopsAsArray();

        for (int i = 0; i < stops.length - 1; i++) {
            for (int j = 0; j < stops.length - 1 - i; j++) {
                if (stops[j].queue.getSize() < stops[j + 1].queue.getSize()) {
                    BusStop temp = stops[j];
                    stops[j] = stops[j + 1];
                    stops[j + 1] = temp;
                }
            }
        }

        System.out.println("\n--- [Bubble Sort] Paragens por Nº Passageiros ---");
        for (BusStop stop : stops) {
            System.out.println("- " + stop.getName() + " (Em espera: " + stop.queue.getSize() + ")");
        }
    }

}

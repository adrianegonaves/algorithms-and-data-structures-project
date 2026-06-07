/**
 * @file: BusLine.java
 * @brief: Sistema de Gestão de Linha de Autocarros - Projeto Final AED
 * @description: Modela a linha de transporte através de uma estrutura de Lista
 *               Duplamente
 *               Ligada de paragens[cite: 18, 19]. É responsável pelas operações
 *               estruturais de inserção[cite: 26],
 *               remoção [cite: 27] e listagem do percurso completo[cite: 28].
 *               Adicionalmente, integra as
 *               implementações manuais dos algoritmos Bubble Sort e Selection
 *               Sort para ordenar as paragens
 *               por ordem alfabética ou por volume de passageiros[cite: 42, 44,
 *               46, 47].
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

    // Attributes
    BusStop root;
    int size;

    public BusLine() {
        root = null;
        size = 0;
    }

    // add a bus stop at the end of the line
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

    public void removeBusStop(String targetName) {

        if (root == null) {
            System.out.println("The Bus Line is empty.");
            return;
        }

        // The BusStop to remove is the root
        if (root.getName().equals(targetName)) {
            root = root.next;
            if (root != null) {
                root.previous = null;
            }
            System.out.println("Bus stop " + targetName + " removed from the start of the line.");
            return;
        }

        // We must search in case it's not the previous cases
        BusStop current = root;
        while (current != null && !current.getName().equals(targetName)) {
            current = current.next;
        }

        // We didn't find the stop
        if (current == null) {
            System.out.println("Bus stop " + targetName + " is not part of this line.");
            return;
        }

        // stop to remove is the last one
        if (current.next == null) {
            current.previous.next = null;
            System.out.println("Bus stop " + targetName + " removed from the end.");
        }
        // the stop is somewhere in the middle
        else {
            current.previous.next = current.next;
            current.next.previous = current.previous;
            System.out.println("Bus stop " + targetName + " removed.");
        }

    }

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

    // Método auxiliar para transformar a lista ligada num array temporário
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

    // 2. Selection Sort por Nº de Passageiros (Ordem Decrescente)
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

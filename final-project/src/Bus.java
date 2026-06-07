/**
 * @file: Bus.java
 * @brief: Sistema de Gestão de Linha de Autocarros - Projeto Final AED
 * @description: Controla o estado e o comportamento do autocarro em circulação. 
 * Utiliza um array circular para gerir dinamicamente os passageiros a bordo, 
 * simula o avanço bidirecional ao longo da linha (inverte a marcha nos terminais) 
 * e coordena os eventos sincronizados de desembarque controlado e embarque em cada paragem.
 * @date: Junho de 2026
 * @version: 1.1
 * @authors:
 * - Adriane Gonçalves - 240000004
 * - Bruno Hortelão - 240001083
 * @institution: Instituto Politécnico de Santarém - Escola Superior de Gestão e Tecnologia
 * @course: Licenciatura em Engenharia Informática
 * @uc: Algoritmos e Estruturas de Dados
 */

import java.util.concurrent.TimeUnit;

public class Bus {

    private Passenger[] passengers;
    private int countPassengers;
    private int capacity;
    private BusStop currentBusStop;
    BusLine busLine;
    private boolean direction; // if true the bus is going forward down the line, if false, it is returning
    private int rear;
    private int front;

    // CONSTRUCTORS
    // Default bus size is 5
    public Bus() {
        this(5);
    }

    // A size can be set
    public Bus(int size) {
        passengers = new Passenger[size];
        countPassengers = 0;
        capacity = size;
        busLine = null;
        currentBusStop = null;
        direction = true;
        front = 0;
        rear = 0;
    }

    // ACTIONS
    public void setBusLine(BusLine newBusLine) {
        busLine = newBusLine;
        currentBusStop = newBusLine.root;
    }

    public void board(Passenger passenger) {
        add(passenger);

    }

    public void add(Passenger passenger) {

        if (countPassengers == capacity) {
            System.out.println("ALERT: Bus is full!");
        } else {

            passengers[rear] = passenger;

            // Increase the 'rear' pointer in a circular way
            rear = (rear + 1) % passengers.length;

            countPassengers++;
        }
    }

    public Passenger remove() {
        if (countPassengers == 0) {
            System.out.println("Bus is empty!");
            return null;
        }

        Passenger removed = passengers[front];
        passengers[front] = null;

        front = (front + 1) % passengers.length;
        countPassengers--;

        return removed;
    }

    public void disembark(int nDisembark) {

        if (nDisembark > countPassengers) {
            System.out.println("ALERT: Cannot disembark " + nDisembark + " passengers. Only " + countPassengers
                    + " are on board.");
            nDisembark = countPassengers; // This way it will remove all passengers on board
        }

        for (int i = 0; i < nDisembark; i++) {
            remove();
        }
    }

    public void advance(int nDisembark) {
        if (busLine == null || currentBusStop == null) {
            System.out.println("ALERT: Either the BusLine hasn't been defined or it has no stops!");
            return;
        }

        try {
            System.out.println("\nAutocarro a mover-se para a próxima paragem...");
            TimeUnit.SECONDS.sleep(1);

        } catch (InterruptedException e) {
            System.out.println("The trip was cut short!");
            Thread.currentThread().interrupt();
            return;
        }

        advanceInternal();
        // Passa o número de desembarques decidido por ti para o evento da paragem
        simulateStopEvent(nDisembark);
    }

    // This method is used to make the bus advanced down the line
    public void advanceInternal() {

        if (direction) { // Going forward(True)
            if (currentBusStop.next != null) {
                currentBusStop = currentBusStop.next;
            } else {
                // The bus hits the end of the bus line and reverses the way
                direction = false;
                if (currentBusStop.previous != null) {
                    currentBusStop = currentBusStop.previous;
                }
            }
        } else { // Coming back (False)
            if (currentBusStop.previous != null) {
                currentBusStop = currentBusStop.previous;
            } else {
                // The bus hits the end of the bus line and reverses the way back
                direction = true;
                if (currentBusStop.next != null) {
                    currentBusStop = currentBusStop.next;
                }
            }
        }

    }

    public void getPassengers() {
        if (countPassengers == 0) {
            System.out.println("The bus has no passengers.");
            return;
        }

        System.out.println("Passengers on board (from first to last):");
        int currentIdx = front;
        for (int i = 0; i < countPassengers; i++) {
            System.out.println("- " + passengers[currentIdx].getName());
            currentIdx = (currentIdx + 1) % passengers.length;
        }
    }

    public int size() {
        return countPassengers;
    }

    public void simulateStopEvent(int nDisembark) {
        if (currentBusStop == null) {
            System.out.println("O autocarro não está em nenhuma paragem atualmente.");
            return;
        }

        System.out.println("\n=== [AUTOCARRO CHEGOU A: " + currentBusStop.getName() + "] ===");

        // 1. Simular Desembarque com base na escolha do utilizador
        if (nDisembark > countPassengers) {
            System.out.println("ALERT: Não podem desembarcar " + nDisembark + " passageiros. Apenas " + countPassengers
                    + " estão a bordo.");
            nDisembark = countPassengers; // Desembarca todos os que estão a bordo se o número pedido for maior
        }

        if (nDisembark > 0) {
            System.out.println("A processar o desembarque de " + nDisembark + " passageiro(s)...");
            for (int i = 0; i < nDisembark; i++) {
                // Remove o passageiro do autocarro
                Passenger leavingPassenger = remove();
                if (leavingPassenger != null) {
                    // REGRA NOVA: Vai diretamente para o fim da fila da paragem atual
                    currentBusStop.queue(leavingPassenger);
                    System.out.println("Passageiro " + leavingPassenger.getName()
                            + " saiu do autocarro e foi para o fim da fila de " + currentBusStop.getName() + ".");
                }
            }
        } else {
            System.out.println("Ninguém selecionado para desembarcar.");
        }

        // 2. Simular Embarque (Tirar da fila da paragem e colocar no autocarro)
        System.out.println("A iniciar o embarque de passageiros em espera...");
        // Guardamos o tamanho inicial da fila para não entrar em loop infinito
        // caso os passageiros que acabaram de sair tentassem reembarcar imediatamente
        // na mesma ronda
        int passengersToBoard = currentBusStop.queue.getSize() - nDisembark;
        if (passengersToBoard < 0)
            passengersToBoard = currentBusStop.queue.getSize();

        for (int i = 0; i < passengersToBoard; i++) {
            if (countPassengers == capacity) {
                System.out.println(
                        "ALERT: O autocarro encheu! Restam " + currentBusStop.queue.getSize() + " pessoas na fila.");
                break;
            }
            if (!currentBusStop.queue.isEmpty()) {
                Passenger nextPassenger = currentBusStop.queue.dequeue();
                board(nextPassenger);
            }
        }

        System.out.println(
                "Estado final do autocarro nesta paragem: " + countPassengers + "/" + capacity + " passageiros.");
    }

}

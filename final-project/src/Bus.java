/**
 * @file: Bus.java
 * @brief: Sistema de Gestão de Linha de Autocarros - Projeto Final AED
 * @description: Controla o estado e o comportamento do autocarro em circulação. 
 * Utiliza um array circular para gerir dinamicamente os passageiros a bordo, 
 * simula o avanço bidirecional ao longo da linha (inverte a marcha nas paragens terminais) 
 * e coordena os eventos de desembarque e embarque em cada paragem.
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
    private boolean direction; // True se o autocarro estiver a andar em frente
    private int rear;
    private int front;

    // CONSTRUCTORS
    // Tamanho por defeito será 5
    public Bus() {
        this(5);
    }

    // Se quisermos o autocarro com tamanho diferente
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

    // Alocar o autocarro a uma linha
    public void setBusLine(BusLine newBusLine) {
        busLine = newBusLine;
        currentBusStop = newBusLine.root;
    }

    // Embarque do passageiro
    public void board(Passenger passenger) {
        add(passenger);

    }

    public void add(Passenger passenger) {

        if (countPassengers == capacity) {
            System.out.println("ALERTA: Autocarro cheio!");
        } else {

            passengers[rear] = passenger;

            // Funcionamento do array circular
            rear = (rear + 1) % passengers.length;

            countPassengers++;
        }
    }

    public Passenger remove() {
        if (countPassengers == 0) {
            System.out.println("Autocarro vazio!");
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
            System.out.println("ALERTA: Não pode desembarcar " + nDisembark + " passageiros. Apenas " + countPassengers
                    + " estão a bordo.");
            // Se o nDisembark for maior que o num de passageiros no autocarro
            // apenas vai desembarcar os que estiverem
            nDisembark = countPassengers; 

        }

        for (int i = 0; i < nDisembark; i++) {
            remove();
        }
    }

    // Avanço do Autocarro 
    public void advance(int nDisembark) {
        if (busLine == null || currentBusStop == null) {
            System.out.println("ALERTA: Linha não foi definida ou não tem paragens!");
            return;
        }

        try {
            System.out.println("\nAutocarro a mover-se para a próxima paragem...");
            TimeUnit.SECONDS.sleep(1);

        } catch (InterruptedException e) {
            System.out.println("A viagem acabou inesperadamente!");
            Thread.currentThread().interrupt();
            return;
        }

        advanceInternal();
        // Passa o número de desembarques que o utilizador decidir
        simulateStopEvent(nDisembark);
    }

    // Avanço do autocarro pela linha
    public void advanceInternal() {

        if (direction) { // Em frente(True)
            if (currentBusStop.next != null) {
                currentBusStop = currentBusStop.next;
            } else {
                // Inversão da marcha
                direction = false;
                if (currentBusStop.previous != null) {
                    currentBusStop = currentBusStop.previous;
                }
            }
        } else { // A voltar (False)
            if (currentBusStop.previous != null) {
                currentBusStop = currentBusStop.previous;
            } else {
                // Nova inversão
                direction = true;
                if (currentBusStop.next != null) {
                    currentBusStop = currentBusStop.next;
                }
            }
        }

    }

    public void getPassengers() {
        if (countPassengers == 0) {
            System.out.println("Autocarro sem passageiros!");
            return;
        }

        System.out.println("Passageiros a bordo (por ordem de entrada):");
        int currentIdx = front;
        for (int i = 0; i < countPassengers; i++) {
            System.out.println("- " + passengers[currentIdx].getName());
            currentIdx = (currentIdx + 1) % passengers.length;
        }
    }

    // Quantidade de passageiros no autocarro
    public int size() {
        return countPassengers;
    }

    // Simulação da chegada à paragem
    public void simulateStopEvent(int nDisembark) {
        if (currentBusStop == null) {
            System.out.println("O autocarro não está em nenhuma paragem atualmente.");
            return;
        }

        System.out.println("\n=== [AUTOCARRO CHEGOU A: " + currentBusStop.getName() + "] ===");

        // Simular desembarque com base na escolha do utilizador
        if (nDisembark > countPassengers) {
            System.out.println("ALERT: Não podem desembarcar " + nDisembark + " passageiros. Apenas " + countPassengers
                    + " estão a bordo.");
            
            // Desembarca todos os que estão a bordo se o número pedido for maior
            nDisembark = countPassengers; 
        }

        if (nDisembark > 0) {
            System.out.println("A processar o desembarque de " + nDisembark + " passageiro(s)...");
            for (int i = 0; i < nDisembark; i++) {
                // Remove o passageiro do autocarro
                Passenger leavingPassenger = remove();
                if (leavingPassenger != null) {
                    // Vai diretamente para o fim da fila da paragem atual, como falado com a professora
                    currentBusStop.queue(leavingPassenger);
                    System.out.println("Passageiro " + leavingPassenger.getName()
                            + " saiu do autocarro e foi para o fim da fila de " + currentBusStop.getName() + ".");
                }
            }
        } else {
            System.out.println("Ninguém selecionado para desembarcar.");
        }

        // Simular Embarque (Tirar da fila da paragem e colocar no autocarro)
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

    public BusStop getCurrentBusStop() {
        return this.currentBusStop;
    }

}

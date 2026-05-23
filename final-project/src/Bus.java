// reutilizamos a estrutura da MyArrayList feita em aula para organizar os passageiros no ônibus.
// Essa estrutura é um escolha pois no onibus os passageiros entram e saem, e a estrutura de array 
// é mais eficiente para acessar os elementos do que uma fila, por exemplo. Além disso, a implementação 
// de um array dinâmico permite que o ônibus possa acomodar um número variável de passageiros sem se 
// preocupar com o limite inicial do array.

public class Bus {
    private Passenger[] passengers;
    private int countPassengers;
    private int capacity;
    private BusStop currentBusStop;
    private boolean direction; // if true the bus is going forward down the line, if false, it is returning

    // CONSTRUCTORS
    // construtor inicializa o ônibus com capacidade de 5 passageiros
    public Bus() {
        passengers = new Passenger[5];
        countPassengers = 0;
        capacity = 5;
        currentBusStop = null;
    }

    // Se quisermos com tamanho ajustável
    public Bus(int size) {
        passengers = new Passenger[size];
        countPassengers = 0;
        capacity = size;
        currentBusStop = null;
    }

    // ACTIONS

    public void board(Passenger passenger) {
        add(passenger);

    }

    public void add(Passenger passenger) {
        if (capacity == passengers.length) {
            System.out.println("ALERT: Bus is full!");
        } else {
            passengers[countPassengers] = passenger;
            countPassengers++;
        }

    }

    public void disembark(int nDisembark) {
        // Di
        for (int i = 0; i < nDisembark; i++) {

        }
    }

    // This method is used to make the bus advanced down the line
    public void advance() {
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

    public Passenger get(int index) {
        return passengers[index];
    }

    public int size() {
        return capacity;
    }

}

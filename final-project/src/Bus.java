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
            // Guarda o passageiro na posição correta do fim
            passengers[rear] = passenger;

            // Avança o ponteiro 'rear' de forma circular
            rear = (rear + 1) % passengers.length;

            // Incrementa o total de pessoas a bordo
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

        // Avança a frente de forma circular
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

    // This method is used to make the bus advanced down the line
    public void advance() {
        if (busLine == null || currentBusStop == null) {
            System.out.println("ALERT: Either the BusLine hasn't been defined or it has no stops!");
        } else {
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
            currentIdx = (currentIdx + 1) % passengers.length; // Avanço circular
        }
    }

    public int size() {
        return capacity;
    }

}

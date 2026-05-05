// reutilizamos a estrutura da MyArrayList feita em aula para organizar os passageiros no ônibus.Essa estrutura é um escolha pois no onibus os passageiros entram e saem, e a estrutura de array é mais eficiente para acessar os elementos do que uma fila, por exemplo. Além disso, a implementação de um array dinâmico permite que o ônibus possa acomodar um número variável de passageiros sem se preocupar com o limite inicial do array.

public class Bus {
    private Passenger[] passengers;
    private int countPassengers;
    private int capacity;
    private BusStop currentStop;

    // construtor inicializa o ônibus com capacidade de 10 passageiros
    public Bus() {
        passengers = new Passenger[10];
        countPassengers = 0;
        capacity = 10;
        currentStop = null;
    }

    public void board(Passenger passenger) {
            add(passenger);

    }

    public void add(Passenger passenger) {
        if (capacity == passengers.length) {
            resize();
        }
        passengers[capacity] = passenger;
        capacity++;
    }

    public void disembark() {
        for (int i = 0; i < countPassengers; i++) {
            
        }
    }

    public Passenger get(int index) {
        return passengers[index];
    }

    public int size() {
        return capacity;
    }

    private void resize() {
        Passenger[] newData = new Passenger[passengers.length * 2];
        for (int i = 0; i < passengers.length; i++) {
            newData[i] = passengers[i];
        }
        passengers = newData;
    }
}

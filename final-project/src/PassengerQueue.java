// reutilizamos a estrutura da fila feita em aula para organizar os passageiros
public class PassengerQueue {

    private Passenger[] queue;
    private int front;
    private int rear;
    private int size;

    public PassengerQueue() {
        this(5);
    }

    public PassengerQueue(int capacity) {
        queue = new Passenger[capacity];
        front = 0;
        rear = 0;
        size = 0;
    }

    public void enqueue(Passenger passenger) {
        if (size < queue.length) {
            rear = (rear + 1) % queue.length;
            queue[rear] = passenger;
            size++;
            System.out.println("Passageiro " + passenger.getName() + " entrou na fila.");
        } else {

            resize(queue);
        }
    }

    public Passenger dequeue() {
        if (size > 0) {
            Passenger value = queue[front];
            System.out.println("Passageiro " + value.getName() + " saiu da fila.");
            front = (front + 1) % queue.length;
            size--;

            return value;
        } else {
            throw new RuntimeException("Atenção: a fila está vazia");
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Double the queue size in case it is full
    private void resize(Passenger[] queue) {
        
        Passenger[] newQueue = new Passenger[queue.length * 2];

        int current = front;

        // Considering we are using a circular array for the queue
        // the copy works differently
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[current];
            current = (current + 1) % queue.length;
        }

        queue = newQueue;
        front = 0;
        rear = size;

    }

    public int getSize() {
        return size;
    }
}

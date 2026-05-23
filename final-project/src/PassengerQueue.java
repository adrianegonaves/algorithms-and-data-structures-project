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
        if (size == queue.length) {
            resize(queue); 
        }
        queue[rear] = passenger; 
        rear = (rear + 1) % queue.length; 
        size++;
        System.out.println("Passenger " + passenger.getName() + " joined the queue.");
    }

    public Passenger dequeue() {
        if (size > 0) {
            Passenger value = queue[front];
            System.out.println("Passenger " + value.getName() + " has left the queue.");
            front = (front + 1) % queue.length;
            size--;

            return value;
        } else {
            throw new RuntimeException("Alert: The queue is empty!");
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

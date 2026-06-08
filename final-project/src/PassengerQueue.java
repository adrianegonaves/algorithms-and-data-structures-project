/**
 * @file: PassengerQueue.java
 * @brief: Sistema de Gestão de Linha de Autocarros - Projeto Final AED
 * @description: Implementação de uma estrutura de dados de Fila
 *               Circular baseada num array para gerir os passageiros em espera.
 *               Segue a política FIFO e inclui um mecanismo dinâmico de
 *               redimensionamento automático (resize) para expandir a
 *               capacidade da fila sempre que esta se encontre cheia.
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
            resize();
        }
        queue[rear] = passenger;
        rear = (rear + 1) % queue.length;
        size++;
        System.out.println("Passageiro " + passenger.getName() + " juntou-se à fila.");
    }

    public Passenger dequeue() {
        if (size > 0) {
            Passenger value = queue[front];
            System.out.println("Passageiro " + value.getName() + " saiu da fila.");
            front = (front + 1) % queue.length;
            size--;

            return value;
        } else {
            throw new RuntimeException("Alerta: A fila está vazia!");
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Duplicar o tamanho caso a fila fique cheia
    private void resize() {

        Passenger[] newQueue = new Passenger[queue.length * 2];

        int current = front;

        // Tendo em conta que usamos arrays circulares
        // a cópia tem um funcionamento diferente 
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[current];
            current = (current + 1) % queue.length;
        }

        this.queue = newQueue;
        front = 0;
        rear = size;

    }

    public int getSize() {
        return size;
    }
}

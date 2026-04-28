public class TestMyStructures {
    public static void main(String[] args) {
        testQueue();
        System.out.println("Todos os testes passaram!");
    }

    static void testQueue() {
        PassengerQueue queue = new PassengerQueue(10);

        Passenger passagenger1 = new Passenger("Alice");
        Passenger passagenger2 = new Passenger("Miguel");

        queue.enqueue(passagenger1);
        queue.enqueue(passagenger2);

        Passenger passengerAlice = queue.dequeue(); 
        assert passengerAlice.getName().equals("Alice");
    }

}

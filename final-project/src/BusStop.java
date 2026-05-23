public class BusStop {

    String name;
    PassengerQueue queue;

    // Connection next;
    // Connection previous;

    BusStop previous;
    BusStop next;

    public BusStop(String name) {
        this.name = name;
        previous = null;
        next = null;
        queue = new PassengerQueue();
    }

    public String getName() {
        return name;
    }

    public void queue(Passenger passenger) {
        this.queue.enqueue(passenger);
    }



}

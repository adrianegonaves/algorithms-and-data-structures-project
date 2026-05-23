

public class BusLine {

    // Attributes
    BusStop root;
    int size;

    public BusLine() {
        root = null;
        size = 0;
    }

    // Adiciona uma paragem no fim
    public void addBusStop(String name) {
        BusStop newBusStop = new BusStop(name);

        if (root == null) {
            root = newBusStop;
        } else {
            BusStop current = root;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newBusStop;
        }
        size++;
    }

    // Vamos implementar com a remoção em qualquer lado?
    public void removeBusStop(String name) {

    }

    public void listBusLine() {

        BusStop current = root;
        while (current != null) {
            System.out.print(current.name + " -> ");
            current = current.next;
        }
        System.out.println();

    }
}

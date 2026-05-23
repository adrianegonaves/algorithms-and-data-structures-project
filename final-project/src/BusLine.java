
public class BusLine {

    // Attributes
    BusStop root;
    int size;

    public BusLine() {
        root = null;
        size = 0;
    }

    // add a bus stop at the end of the line
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

        BusStop current = root;

        // Line is empty
        if(root == null) {
            System.out.println("There are no Bus Stops to remove!");
            return;
        }

        // Line has 1 element
        if (current.next == null) {
            root = null;
            
        }

        // Line has 2 elements
        



    }

    public void listBusLine() {

        BusStop current = root;

        System.out.print("LINE: ");
        while (current != null) {
            System.out.print("[" + current.getName() + " (People in queue: " + current.queue.getSize() + ")]");
            if (current.next != null) {
                System.out.print(current.name + " -> ");

            }
            current = current.next;
        }
        System.out.println();

    }
}

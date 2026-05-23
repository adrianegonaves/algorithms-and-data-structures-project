
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


    public void removeBusStop(String targetName) {
    
        if (root == null) {
            System.out.println("The Bus Line is empty.");
            return;
        }

        // The BusStop to remove is the root
        if (root.getName().equals(targetName)) {
            root = root.next; 
            if (root != null) {
                root.previous = null; 
            }
            System.out.println("Bus stop " + targetName + " removed from the start of the line.");
            return;
        }

        // We must search in case it's not the previous cases
        BusStop current = root;
        while (current != null && !current.getName().equals(targetName)) {
            current = current.next;
        }

        // We didn't find the stop
        if (current == null) {
            System.out.println("Bus stop " + targetName + " is not part of this line.");
            return;
        }


        // stop to remove is the last one
        if (current.next == null) {
            current.previous.next = null; 
            System.out.println("Bus stop " + targetName + " removed from the end.");
        }
        // the stop is somewhere in the middle
        else {
            current.previous.next = current.next;
            current.next.previous = current.previous; 
            System.out.println("Bus stop " + targetName + " removed.");
        }

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

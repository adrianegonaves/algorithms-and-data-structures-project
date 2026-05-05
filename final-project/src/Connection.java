public class Connection {
    
    // distance between 2 bus stops
    private int distance;


    private BusStop[] stops;

    public Connection(int distance, BusStop stopOne, BusStop stopTwo) {

        this.distance = distance;
        stops = new BusStop[2]; 

        stops[0] = stopOne;
        stops[1] = stopTwo;

    }


}

package waterflow;

public class Edge {
    
    private Titik origin;//titik asal
    private Titik destination;//titik tujuan
    private double radian;//sudut arah mata panah

    public Edge(Titik origin, Titik destination) {
        this.origin = origin;
        this.destination = destination;
        double deltaX = destination.getX() - origin.getX();
        double deltaY = destination.getY() - origin.getY();
        this.radian = Math.atan2(deltaY, deltaX);        
    }

    public Titik getOrigin() {
        return origin;
    }

    public Titik getDestination() {
        return destination;
    }

    public double getRadian() {
        return radian;
    }
    
}

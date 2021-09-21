package offline3;

/**
 *
 * @author Ripon
 */
public class Vertex implements Comparable {

    public int id = -1;
    public double x = 0, y = 0;
    public int type = 1;
    public boolean left = false;

    public Vertex(int idd, double xx, double yy, int ttype) {
        id = idd;
        x = xx;
        y = yy;
        type = ttype;
    }

    
    public boolean equals(Vertex pb) {
        return (this.x == pb.x) && (this.y == pb.y);
    }

    public int compareTo(Object o) {

        if (!(o instanceof Vertex)) {
            return -1;
        }
        Vertex pb = (Vertex) o;
        if (this.equals(pb)) {
            return 0;
        }
        if (this.y > pb.y) {
            return 1;
        } else if (this.y < pb.y) {
            return -1;
        } else if (this.x < pb.x) {
            return 1;
        } else {
            return -1;
        }
    }

}

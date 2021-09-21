
package offline3;

/**
 *
 * @author Ripon
 */
public class Edge {

    public int _type = 1;
    
    public int _id = -1;
    public Vertex[] endpoints = {null, null};
    
    public double edgekey = 0;
    public int helper = -1;

    

    public Edge(Vertex ep1, Vertex ep2, int iType) {
        endpoints[0] = ep1;
        endpoints[1] = ep2;
        _id = (int) ++MonotonePolygon.l_id;
        _type = iType;
    }

    

    public int id() {
        return _id;
    }

    public Vertex endPoint(int i) {
        return endpoints[i];
    }

    public int type() {
        return _type;
    }

    public Comparable keyValue() {
        return edgekey;
    }

    public void setKeyValue(double y) {
        if (endpoints[1].y == endpoints[0].y) {
            edgekey = endpoints[0].x < endpoints[1].x ? endpoints[0].x : endpoints[1].x;
        } else {
            edgekey = (y - endpoints[0].y) * (endpoints[1].x - endpoints[0].x) / (endpoints[1].y - endpoints[0].y) + endpoints[0].x;
        }
    }

    public void reverse() {
       
        Vertex tmp = endpoints[0];
        endpoints[0] = endpoints[1];
        endpoints[1] = tmp;
    }

    
    public void setHelper(int i) {
        helper = i;
    }

    public int helper() {
        return helper;
    }

}

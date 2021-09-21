package offline3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Ripon
 */
public class MonotonePolygon {

    public static int l_id = 0;

    protected int _ncontours = 0;

    protected int _nVertices;
    protected HashMap _points = new HashMap();
    protected int[] _pointsKeys = null;
    
    private BinarySearchTree _edgebst = new BinarySearchTree();
    private ArrayList _mpolys = new ArrayList();
    protected HashMap _edges = new HashMap();
    protected int[] _edgesKeys = null;
    private PriorityQueue _qpoints = new PriorityQueue(30, new VertexComparatorCoordinatesReverse());
    private HashMap _startAdjEdgeMap = new HashMap();
    public HashMap _diagonals = new HashMap();

    

    private void initPolygon(int numVertices, double[][] vertices) {
        int j;
        _nVertices = numVertices;
        for (j = 0; j < numVertices; ++j) {
            _points.put(j + 1, new Vertex(j + 1, vertices[j][0], vertices[j][1], 2));

        }
        j = 1;
        Edge edge;

        for (; j + 1 <= _nVertices; ++j) {
            edge = new Edge((Vertex) _points.get(j), (Vertex) _points.get(j + 1), 2);
            _edges.put(l_id, edge);
        }
        edge = new Edge((Vertex) _points.get(j), (Vertex) _points.get(1), 2);
        _edges.put(l_id, edge);

    }

    MonotonePolygon(int numVertices, double[][] vertices) {
        l_id = 0;
        initPolygon(numVertices, vertices);
        initializate();

    }
    
    private int next(int i) {
        int j = 0;

        if (i < _nVertices) {
            j = i + 1;
        } else if (i == _nVertices) {

            j = 1;
        }

        return j;
    }


    private int prev(int i) {
        int j = 0;

        if (i == 1 || (i == _nVertices + 1)) {
            j = _nVertices;
        } else if (i <= _nVertices) {
            j = i - 1;
        }

        return j;
    }

    private void initializate() {
        Object[] temp = _points.keySet().toArray();
        _pointsKeys = new int[temp.length];
        for (int i = 0; i < temp.length; ++i) {
            _pointsKeys[i] = ((Integer) temp[i]).intValue();
        }
        //Arrays.sort(_pointsKeys);

        int id, idp, idn;
        Vertex p, pnext, pprev;
        double area;

        for (int i = 0; i < _pointsKeys.length; ++i) {
            id = _pointsKeys[i];
            idp = prev(id);
            idn = next(id);

            p = (Vertex) _points.get(id);
            pnext = (Vertex) _points.get(idn);
            pprev = (Vertex) _points.get(idp);

            if ((p.compareTo(pnext) > 0) && (pprev.compareTo(p) > 0)) {
                p.type = Offline3.REGULAR_DOWN;
            } else if ((p.compareTo(pprev) > 0) && (pnext.compareTo(p) > 0)) {
                p.type = Offline3.REGULAR_UP;
            } else {
                area = calculateArea(new double[]{pprev.x, pprev.y},
                        new double[]{p.x, p.y},
                        new double[]{pnext.x, pnext.y});

                if ((pprev.compareTo(p) > 0) && (pnext.compareTo(p) > 0)) {
                    p.type = (area > 0) ? Offline3.END : Offline3.MERGE;
                }
                if ((pprev.compareTo(p) < 0) && (pnext.compareTo(p) < 0)) {
                    p.type = (area > 0) ? Offline3.START : Offline3.SPLIT;
                }
            }

            _qpoints.add(p);

            
        }
    }

    private void addDiagonal(int i, int j) {
        int type = Offline3.INSERT;

        Edge diag = new Edge((Vertex) _points.get(i),
                (Vertex) _points.get(j),
                type);
        _edges.put(diag.id(), diag);

        

        _diagonals.put(diag.id(), diag);

    }

    private void handleStartVertex(int i) {

        double y = ((Vertex) _points.get(i)).y;

        Edge edge = (Edge) _edges.get(i);
        edge.setHelper(i);
        edge.setKeyValue(y);

        _edgebst.insert(edge);

    }

    private void handleEndVertex(int i) {
        
        int previ = prev(i);
        Edge edge = (Edge) _edges.get(previ);
        int helper = edge.helper();

        if (((Vertex) _points.get(helper)).type == Offline3.MERGE) {
            addDiagonal(i, helper);
        }
        _edgebst.delete(edge.keyValue());

    }

    private void handleSplitVertex(int i) {
        Vertex point = (Vertex) _points.get(i);
        double x = point.x, y = point.y;

        BSTNode leftnode = _edgebst.findMaxSmallerThan(x);
        Edge leftedge = (Edge) leftnode.data();

        int helper = leftedge.helper();
        addDiagonal(i, helper);

        leftedge.setHelper(i);
        Edge edge = (Edge) _edges.get(i);
        edge.setHelper(i);
        edge.setKeyValue(y);
        _edgebst.insert(edge);
    }

    private void handleMergeVertex(int i) {
        Vertex point = (Vertex) _points.get(i);
        double x = point.x, y = point.y;

        int previ = prev(i);
        Edge previEdge = (Edge) _edges.get(previ);
        int helper = previEdge.helper();

        Vertex helperPoint = (Vertex) _points.get(helper);

        if (helperPoint.type == Offline3.MERGE) {
            addDiagonal(i, helper);
        }

        _edgebst.delete(previEdge.keyValue());

        BSTNode leftnode = _edgebst.findMaxSmallerThan(x);
        Edge leftedge = (Edge) leftnode.data();

        helper = leftedge.helper();
        helperPoint = (Vertex) _points.get(helper);
        if (helperPoint.type == Offline3.MERGE) {
            addDiagonal(i, helper);
        }

        leftedge.setHelper(i);

    }

    private void handleRegularVertexDown(int i) {
        Vertex point = (Vertex) _points.get(i);

        double y = point.y;

        int previ = prev(i);

        Edge previEdge = (Edge) _edges.get(previ);

        int helper = previEdge.helper();

        Vertex helperPoint = (Vertex) _points.get(helper);

        if (helperPoint.type == Offline3.MERGE) {
            addDiagonal(i, helper);
        }

        _edgebst.delete(previEdge.keyValue());

        Edge edge = (Edge) _edges.get(i);
        edge.setHelper(i);
        edge.setKeyValue(y);
        _edgebst.insert(edge);

    }

    private void handleRegularVertexUp(int i) {
        Vertex point = (Vertex) _points.get(i);
        double x = point.x;
        BSTNode leftnode = _edgebst.findMaxSmallerThan(x);
        Edge leftedge = (Edge) leftnode.data();

        int helper = leftedge.helper();
        Vertex helperPoint = (Vertex) _points.get(helper);
        if (helperPoint.type == Offline3.MERGE) {
            addDiagonal(i, helper);
        }
        leftedge.setHelper(i);

    }

    public boolean partition2Monotone() {

        int id;
        while (_qpoints.size() > 0) {
            Vertex vertex = (Vertex) _qpoints.poll();

            id = vertex.id;

            switch (vertex.type) {
                case Offline3.START:
                    handleStartVertex(id);
                    break;
                case Offline3.END:
                    handleEndVertex(id);
                    break;
                case Offline3.MERGE:
                    handleMergeVertex(id);
                    break;
                case Offline3.SPLIT:
                    handleSplitVertex(id);
                    break;
                case Offline3.REGULAR_UP:
                    handleRegularVertexUp(id);
                    break;
                case Offline3.REGULAR_DOWN:
                    handleRegularVertexDown(id);
                    break;
                default:
                    return false;
            }
        }

        return true;
    }

        
    public double calculateArea(double[] pa, double[] pb, double[] pc) {
        double detleft, detright;

        detleft = (pa[0] - pc[0]) * (pb[1] - pc[1]);
        detright = (pa[1] - pc[1]) * (pb[0] - pc[0]);

        return detleft - detright;
    }
    

    public class VertexComparatorCoordinatesReverse implements Comparator {

        public int compare(Object o1, Object o2) {
            Vertex pb1 = (Vertex) o1;
            Vertex pb2 = (Vertex) o2;
            return (-pb1.compareTo(pb2));
        }

    }
}

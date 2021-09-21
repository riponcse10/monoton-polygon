/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package offline3;

import java.util.ArrayList;

/**
 *
 * @author Ripon
 */
public class Offline3 {

    public static final int UNKNOWN = 1;
    public static final int INPUT = 2;
    public static final int INSERT = 3;
    public static final int START = 4;
    public static final int END = 5;
    public static final int MERGE = 6;
    public static final int SPLIT = 7;
    public static final int REGULAR_UP = 8;
    public static final int REGULAR_DOWN = 9;

    public static int numVertices;
    public static double[][] vertices;

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        /*numVertices = 8;

        vertices = new double[][]{
            {0, 0}, {5, 3}, {10, 0}, {8, 5}, {10, 10}, {5, 8}, {0, 10}, {2, 5}
        };*/

        numVertices = 14;

        vertices = new double[][]{
            {0, 0}, {5, 2}, {10, 0}, {15, 4}, {20, 0}, {25, 6}, {30, 0}, {30, 30},{25,28},{20,30},{15,26},{10,30},{5,24},{0,30}
        };
        MonotonePolygon monotonpolygon = new MonotonePolygon(numVertices, vertices);
        
        monotonpolygon.partition2Monotone();
        Object[] temp = monotonpolygon._diagonals.values().toArray();
        for (int i=0;i<temp.length;i++)
        {
            System.out.println(((Edge)temp[i]).endpoints[0].id +" " +((Edge)temp[i]).endpoints[1].id );
        }
    }

}

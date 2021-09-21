/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package offline3;

/**
 *
 * @author Ripon
 */
public class BSTNode {
    
    protected Edge edge     = null;
    protected BSTNode     leftnode     = null;
    protected BSTNode     rightnode    = null;
    
	  
    public BSTNode(){
    }
    
    	
    public Edge data(){ return edge; }

    public BSTNode left(){ return leftnode; }
    
    public BSTNode right(){ return rightnode; }
    
    
    Comparable keyValue(){ return edge.keyValue(); }
    
}

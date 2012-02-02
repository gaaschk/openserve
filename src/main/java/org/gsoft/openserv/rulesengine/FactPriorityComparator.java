package org.gsoft.openserv.rulesengine;

import java.io.Serializable;
import java.util.Comparator;

/**
 * This comparator is used exclusively for ordering of Facts within a {@link PriorityQueue}. Per the contract of that
 * class, this comparator indicates that facts with a higher priority are "less than" facts with a lower priority. NOTE:
 * In order to be usable in a PriorityQueue, this comparator arbitrarily returns 1 for non-identical facts that should
 * have the same priority. If this returned 0, the PriorityQueue treats the facts as equal which causes problems for the
 * contains() method, etc.
 */
public class FactPriorityComparator implements Comparator<Fact>, Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -5512886930429971598L;
    
    /**
     * Determine whether the first or second fact should be ordered first in a PriorityQueue.
     * 
     * @return A negative number if o1 should be first; 0 if the instances are equal; 1 if ordering does not matter; a
     *         positive number if o2 should be first.
     */
    public int compare( final Fact o1, final Fact o2 ) {
        if ( o1 == o2 ) {
            return 0;
        }
        
        int returnVal = 0;
        
        if ( o1.isFast() ) {
            if ( o2.isFast() ) {
                returnVal = this.compareExpressions( o1, o2 );
            }
            else {
                returnVal = -1;
            }
        }
        else {
            if ( o2.isFast() ) {
                returnVal = 1;
            }
            else {
                returnVal = this.compareExpressions( o1, o2 );
            }
        }
        
        return returnVal == 0 ? 1 : returnVal;
    }
    
    /**
     * Return a negative number if o1 affects more expressions; 0 if both affect the same number; a positive number if
     * o2 affects more expressions.
     */
    private int compareExpressions( final Fact o1, final Fact o2 ) {
        return o2.getFactExpressions().size() - o1.getFactExpressions().size();
    }
}

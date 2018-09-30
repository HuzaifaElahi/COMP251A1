package A1;

import java.util.*;
import static A1.main.*;

public class Chaining {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public ArrayList<ArrayList<Integer>> Table;

    //Constructor for the class. sets up the data structure for you
    protected Chaining(int w, int seed) {
        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.Table = new ArrayList<ArrayList<Integer>>(m);
        for (int i = 0; i < m; i++) {
            Table.add(new ArrayList<Integer>());
        }
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
    }

    /**
     * Implements the hash function h(k)
     */
    public int chain(int key) {
        //ADD YOUR CODE HERE (change return statement)
    	return (this.A * key % power2(this.w)) >> (this.w - this.r);
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table.get(hashValue).size() == 0;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (chane return statement)
    	int hashValue = chain(key);
    	int collisions = 0;
    	// No Collision
    	if(isSlotEmpty(hashValue)) {
    		Table.get(hashValue).add(key);
    	} 
    	// Already inserted
    	else if(Table.get(hashValue).contains(key)) {
    		return collisions;
    	}
    	// Add and count collisions
    	else {
    		collisions = Table.get(hashValue).size();
    		Table.get(hashValue).add(key);
    	}
        return collisions;
    }

}

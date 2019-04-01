
/**
 * A "smart" implementation of a HashMap.
 * 
 * This version should avoid collisions using the
 * Linear Probing method demonstrated outside on Wednesday.
 * 
 * You should ALSO double the size of the array (then go up to the next prime)
 * when the size of the HashMap is half the array length or greater.
 * This only affects the put() method, since this is the only method that
 * can increase the array size.
 * 
 * You do NOT have to worry about making the array smaller if there are
 * too few entries.
 * 
 * Assignment: Write the methods:
 * get()
 * put()
 * remove()
 * resize()
 * 
 */
public class APHashMap<K, V>
{
    private Entry<K, V>[] entries;
    private int size;
    
    public APHashMap(int n) {
        entries = new Entry[getNextPrime(n)];
        size = 0;
    }
    
    /**
     * Complete the four methods below.
    */
    
    /**
     * Given a key, returns the corresponding value,
     * or null if there is no entry with that key.
     */
    public V get(K key) {
        int i = hash(key);
        if(entries[i] != null) {
            while(entries[i] != null && !entries[i].getKey().equals(key)) {
                i++;
                i = i % entries.length;
            }   
            return entries[i].getValue();
        }
        return null;
    }
    
    /**
     * Adds a new entry to the Map. If key was 
     * already in the map, return the old value.
     * If not, return null.
     */
    public V put(K key, V value) {
        int i = hash(key);
        while(entries[i] != null && !entries[i].getKey().equals(key)) { 
            i++;
            i = i % entries.length;
        }
        Entry<K, V> previous = entries[i];
        Entry<K, V> entry = new Entry<K, V>(key, value);
        entries[i] = entry;
        size++;
        if(size >= entries.length/2) {
            this.resize();
        }
        if(previous != null) {
            return previous.getValue();
        }
        return null;
    }
    
    /**
     * Removes and returns the value paired with key.
     * If there is no value, do not alter the map, and return null.
     */
    public V remove(K key) {
        int i = hash(key);
        while(entries[i] != null && !entries[i].getKey().equals(key)) {
            i++;
            i = i % entries.length;
        }
        if(entries[i] != null) {
            Entry<K, V> temp = entries[i];
            entries[i] = null;
            this.resize();
            return temp.getValue();
        }
        return null;
    }
    
    /**
     * If the size is half or more the length of the array, make the
     * array twice as long (and then the next prime number). Move
     * all the entries from the old array to the new one.
     * 
     * Note -- the hash values of all the keys are now different,
     * so you cannot just move each entry to the same index in the new
     * array. Instead, loop over the old array and move the entries over
     * using the put() method.
     * 
     * This private helper method should be called from put(), if a 
     * new entry causes the size to be too large.
     */
    private void resize() {
        Entry<K, V>[] temp = new Entry[entries.length];
        for(int i = 0; i < entries.length; i++) {
            temp[i] = entries[i];
        }
        
        entries = new Entry[getNextPrime(entries.length * 2)];
        this.size = 0;
        for(Entry<K, V> e: temp) {
            if(e != null) {
                this.put(e.getKey(), e.getValue());
            }
        }
    }
    
    /**
     * Methods below are pre-written. You do not need to modify these.
    */
    
    /**
     * Returns the number of non-null entries in the map.
     * Note this is NOT usually equal to entries.length, because
     * often several of the array elements in entries are null.
     */
    public int size() {
        return size;
    }
    
    /**
     * Prints something like,
     * key1, value1
     * key2, value2
     * .
     * .
     * .
     * 
     * in the Terminal for each non-null entry
     * in the map. You should skip over null entries.
     */
    public void printMap() {
        for(Entry e : entries) {
            if(e != null) {
                System.out.println(e.getKey() + ", " + e.getValue());
            }
        }
    }
    
    /**
     * Returns the hash value for a given key.
     */
    private int hash(K key) {
        return Math.abs(key.hashCode() % entries.length);
    }
    
    /**
     * Private helper method. Given a positive
     * integer n, returns the next prime at or after n.
     * 
     * For example:
     * getNextPrime(8) -> 11
     * getNextPrime(13) -> 13
     * getNextPrime(50) -> 53
     */
    private int getNextPrime(int n) {
        while(!isPrime(n)) {
            n++;
        }
        return n;
    }
    
    /**
     * Helper method called by getNextPrime().
     */
    private boolean isPrime(int n) {
        int factor = 2;
        while(n % factor != 0) {
            factor++;
        }
        return factor == n;
    }
}

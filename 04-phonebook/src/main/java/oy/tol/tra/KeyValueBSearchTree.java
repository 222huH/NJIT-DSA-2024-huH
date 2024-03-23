

package oy.tol.tra;

public class KeyValueBSearchTree<K extends Comparable<K>, V> implements Dictionary<K, V> {

    private TreeNode<K, V> root;
    private int count = 0;
    private int maxTreeDepth = 0;

    @Override
    public Type getType() {
        return Type.BST;
    }

    @Override
    public int size() {
        return count;
    }

    /**
     * Prints out the statistics of the tree structure usage.
     * Here you should print out member variable information which tell something
     * about your implementation.
     * <p>
     * For example, if you implement this using a hash table, update member
     * variables of the class
     * (int counters) in add(K) whenever a collision happens. Then print this counter
     * value here.
     * You will then see if you have too many collisions. It will tell you that your
     * hash function
     * is good or bad (too many collisions against data size).
     */
    @Override
    public String getStatus() {
        String toReturn = "Tree has a maximum depth of " + maxTreeDepth + ".\n";
        toReturn += "Longest collision chain in a tree node is " + TreeNode.longestCollisionChain + "\n";
        TreeAnalyzerVisitor<K, V> visitor = new TreeAnalyzerVisitor<>();
        root.accept(visitor);
        toReturn += "Minimum path height to the bottom: " + visitor.minHeight + "\n";
        toReturn += "Maximum path height to the bottom: " + visitor.maxHeight + "\n";
        toReturn += "Ideal height if balanced: " + Math.ceil(Math.log(count)) + "\n";
        return toReturn;
    }

    @Override
    public boolean add(K key, V value) throws IllegalArgumentException, OutOfMemoryError {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Neither key nor value can be null.");
        }
        if (root == null) {
            root = new TreeNode<>(key, value);
            count++;
            maxTreeDepth = 1; // Just added the root, so depth is 1
            return true;
        } else {
           
            int added = root.insert(key, value, key.hashCode());
            int depthAfter = TreeNode.currentAddTreeDepth;
            TreeNode.currentAddTreeDepth = 0; // Reset for the next addition
            if (added > 0) {
                count++; // Increase count if a new node was added
            }
            if (depthAfter > maxTreeDepth) {
                maxTreeDepth = depthAfter; // Update max depth if it increased
            }
            return added > 0;
        }
    }

    @Override
    public V find(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        return (root != null) ? root.find(key, key.hashCode()) : null;
    }

    @Override
    public void ensureCapacity(int size) throws OutOfMemoryError {
        // Nothing to do here. Trees need no capacity.
    }

    @Override
    public Pair<K, V>[] toSortedArray() {
        TreeToArrayVisitor<K, V> visitor = new TreeToArrayVisitor<>(count);
        root.accept(visitor);
        Pair<K, V>[] sorted = visitor.getArray();
        Algorithms.fastSort(sorted);
        return sorted;
    }

    @Override
    public void compress() throws OutOfMemoryError {
        // Nothing to do here, since BST does not use extra space like array-based structures.
    }
}
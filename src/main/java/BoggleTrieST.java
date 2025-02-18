import edu.princeton.cs.algs4.Queue;

public class BoggleTrieST<Value> {

    private static final int R = 26; // A-Z
    private static final int OFFSET = 65; // A-Z

    private Node root;
    private int n;

    public static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }

    public BoggleTrieST() {

    }

    public Node getRoot() {
        return root;
    }

    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.value;
    }

    public Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c-OFFSET], key, d+1);
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) delete(key);
        else root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.value == null) n++;
            x.value = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c-OFFSET] = put(x.next[c-OFFSET], key, val, d+1);
        return x;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.value != null) n--;
            x.value = null;
        }
        else {
            char c = key.charAt(d);
            x.next[c-OFFSET] = delete(x.next[c-OFFSET], key, d+1);
        }

        // remove subtrie rooted at x if it is completely empty
        if (x.value != null) return x;
        for (int c = 0; c < R; c++)
            if (x.next[c-OFFSET] != null)
                return x;
        return null;
    }

    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<String>();
        collect(root, new StringBuilder(), pattern, results);
        return results;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.value != null) results.enqueue(prefix.toString());
        for (char c = 0; c < R; c++) {
            prefix.append(c);
            collect(x.next[c-OFFSET], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.value != null)
            results.enqueue(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch++) {
                prefix.append(ch);
                collect(x.next[ch-OFFSET], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
        else {
            prefix.append(c);
            collect(x.next[c-OFFSET], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.value != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c-OFFSET], query, d+1, length);
    }

    public boolean isPrefix(String prefix) {
        return get(root, prefix, 0) != null;
    }

    public boolean isPrefix2(String prefix) {
        Node node = root;

        for (int i = 0; i < prefix.length() && node != null; i++) {
            char c = prefix.charAt(i);
            node = node.next[c - OFFSET];
        }

        return node != null;
    }

}



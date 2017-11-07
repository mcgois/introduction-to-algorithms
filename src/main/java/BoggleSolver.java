import java.util.TreeSet;

public final class BoggleSolver {

    private final BoggleTrieST<Boolean> dictionary;

    public BoggleSolver(String[] dictionary) {
        this.dictionary = new BoggleTrieST<>();
        for (String word : dictionary) {
            this.dictionary.put(word, Boolean.TRUE);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        TreeSet<String> words = new TreeSet<>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                searchWords(board, i, j, words);
            }
        }
        return words;
    }

    private void searchWords(BoggleBoard board, int i, int j, TreeSet<String> words) {
        boolean[][] visited = new boolean[board.rows()][board.cols()];
        dfs(board, i, j, words, visited, "");
    }

    private void dfs(BoggleBoard board, int i, int j, TreeSet<String> words, boolean[][] visited, String prefix) {
        if (i < 0 || i >= board.rows()) {
            return;
        }

        if (j < 0 || j >= board.cols()) {
            return;
        }

        if (visited[i][j]) {
            return;
        }

        char letter = board.getLetter(i, j);
        prefix = prefix + (letter == 'Q' ? "QU" : letter);

        if (prefix.length() > 2 && dictionary.contains(prefix)) {
            words.add(prefix);
        }

        if (!dictionary.isPrefix(prefix)) {
            return;
        }

        visited[i][j] = true;

        dfs(board, i - 1, j - 1, words, visited, prefix);
        dfs(board, i - 1, j, words, visited, prefix);
        dfs(board, i - 1, j + 1, words, visited, prefix);
        dfs(board, i, j - 1, words, visited, prefix);
        dfs(board, i, j + 1, words, visited, prefix);
        dfs(board, i + 1, j - 1, words, visited, prefix);
        dfs(board, i + 1, j, words, visited, prefix);
        dfs(board, i + 1, j + 1, words, visited, prefix);

        visited[i][j] = false;
    }

    public int scoreOf(String word) {
        if (dictionary.contains(word)) {
            if (word.length() < 3) {
                return 0;
            } else if (word.length() < 5) {
                return 1;
            } else if (word.length() < 6) {
                return 2;
            } else if (word.length() < 7) {
                return 3;
            } else if (word.length() < 8) {
                return 5;
            } else {
                return 11;
            }
        }
        return 0;
    }

}
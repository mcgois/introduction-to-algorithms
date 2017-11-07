import java.util.HashSet;
import java.util.Set;

public final class BoggleSolver {

    private final BoggleTrieST<Boolean> dictionary;

    public BoggleSolver(String[] dictionary) {
        this.dictionary = new BoggleTrieST<>();
        for (String word : dictionary) {
            this.dictionary.put(word, Boolean.TRUE);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> words = new HashSet<>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                searchWords(board, i, j, words);
            }
        }
        return words;
    }

    private void searchWords(BoggleBoard board, int i, int j, Set<String> words) {
        boolean[][] visited = new boolean[board.rows()][board.cols()];
        dfs(board, i, j, words, visited, "");
    }

    private void dfs(BoggleBoard board, int i, int j, Set<String> words, boolean[][] visited, String prefix) {
        if (!dictionary.isPrefix2(prefix)) {
            return;
        }

        visited[i][j] = true;

        char letter = board.getLetter(i, j);
        prefix = prefix + (letter == 'Q' ? "QU" : letter);
        if (prefix.length() > 2 && dictionary.contains(prefix)) {
            words.add(prefix);
        }

        for (int row = i - 1; row <= i + 1; row++) {
            for (int col = j - 1; col <= j + 1; col++) {
                if (isValidIndex(board, row, col) && !visited[row][col]) {
                    dfs(board, row, col, words, visited, prefix);
                }
            }
        }

        visited[i][j] = false;
    }

    private boolean isValidIndex(BoggleBoard board, int row, int col) {
        return row >= 0 && row < board.rows() && col >= 0 && col < board.cols();
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
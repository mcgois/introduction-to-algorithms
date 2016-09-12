import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class DequeTest {

    private Deque<String> deque;

    @Before
    public void setup() {
        deque = new Deque<>();
    }

    @Test(expected = NullPointerException.class)
    public void deveriaLevantarExcecaoNPEQuandoAddNuloFirst() {
        // when
        deque.addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void deveriaLevantarExcecaoNPEQuandoAddNuloLast() {
        // when
        deque.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void deveriaLevantarExcecaoNoSuchElementQuandoRemoveFirstEstruturaVazia() {
        // when
        deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void deveriaLevantarExcecaoNoSuchElementQuandoRemoveLastEstruturaVazia() {
        // when
        deque.removeLast();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deveriaLevantarExcecaoUnsupportedOperationQuandoMetodoRemoveIterator() {
        // when
        deque.iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void deveriaLevantarExcecaoNoSuchElementQuandoMetodoNextIterator() {
        // when
        deque.iterator().next();
    }

}

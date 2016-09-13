import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class RandomizedQueueTest {

    private RandomizedQueue<String> queue;

    @Before
    public void setup() {
        queue = new RandomizedQueue<>();
    }

    @Test(expected = NullPointerException.class)
    public void deveriaLevantarExcecaoNPEQuandoAddNulo() {
        // when
        queue.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void deveriaLevantarExcecaoNoSuchElementQuandoRemoveEstruturaVazia() {
        // when
        queue.dequeue();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void deveriaLevantarExcecaoUnsupportedOperationQuandoMetodoRemoveIterator() {
        // when
        queue.iterator().remove();
    }

//    @Test(expected = NoSuchElementException.class)
//    public void deveriaLevantarExcecaoNoSuchElementQuandoMetodoNextIterator() {
//        // when
//        queue.iterator().next();
//    }

}

package heap;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    /* Be sure to write randomized tests that can handle millions of items. To
     * test for runtime, compare the runtime of NaiveMinPQ vs ArrayHeapMinPQ on
     * a large input of millions of items. */




    // Add in decreasing order, change biggest to smallest.
    @Test
    public void gradeScopeTest1(){
        ArrayHeapMinPQ<String> heap = new ArrayHeapMinPQ<>();
        heap.add("a", 9);
        heap.add("b", 8);
        heap.add("c", 7);
        heap.add("d", 6);
        heap.add("e", 5);
        heap.changePriority("a", 4);
        assertEquals("a", heap.removeSmallest());

    }

    // Add in increasing order, change smallest to biggest.
    @Test
    public void gradeScopeTest2(){
        ArrayHeapMinPQ<String> heap = new ArrayHeapMinPQ<>();
        heap.add("e", 5);
        heap.add("d", 6);
        heap.add("c", 7);
        heap.add("b", 8);
        heap.add("a", 9);
        heap.changePriority("e", 10);
        assertEquals("d", heap.removeSmallest());
    }

    // Add in decreasing order, change smallest to biggest.
    @Test
    public void gradeScopeTest3(){
        ArrayHeapMinPQ<String> heap = new ArrayHeapMinPQ<>();
        heap.add("a", 9);
        heap.add("b", 8);
        heap.add("c", 7);
        heap.add("d", 6);
        heap.add("e", 5);
        heap.changePriority("e", 10);
        assertEquals("d", heap.getSmallest());

    }

    //Test increasing priority
    @Test
    public void gradeScopeTest4(){
        ArrayHeapMinPQ<String> heap = new ArrayHeapMinPQ<>();
        heap.add("a", 9);
        heap.add("b", 1);
        heap.add("c", 11);
        heap.add("d", 6);
        heap.add("e", 7);

        heap.changePriority("b", 8);


        assertEquals("d", heap.removeSmallest());
        assertEquals("e", heap.removeSmallest());

        heap.changePriority("b", 10);

        assertEquals("a", heap.removeSmallest());

        heap.changePriority("b", 12);

        assertEquals("c", heap.removeSmallest());
        assertEquals("b", heap.removeSmallest());
    }

    //Test decreasing priority
    @Test
    public void gradeScopeTest5(){
        ArrayHeapMinPQ<String> heap = new ArrayHeapMinPQ<>();
        heap.add("a", 15);
        heap.add("b", 1);
        heap.add("c", 20);
        heap.add("d", 6);
        heap.add("e", 10);

        heap.changePriority("b", 7);

        assertEquals("d", heap.removeSmallest());
        heap.changePriority("b", 11);
        assertEquals("e", heap.removeSmallest());

        heap.changePriority("b", 16);

        assertEquals("a", heap.removeSmallest());

        assertEquals("b", heap.removeSmallest());
        assertEquals("c", heap.removeSmallest());
    }

    //random Test
    // ***generate Integer instead of Double for convenience in debugging
    //change the bound of loop
    @SuppressWarnings("checkstyle:WhitespaceAfter")
    @Test
    public void testAddLastThenRemoveLast() {
        // Random seed ensures that each test run is reproducible
        int seed = 373; // or your favorite number
        Random random = new Random(seed);

        // Create a new ArrayDeque (reference implementation)
        //ArrayDeque<Integer> expectedDeque = new ArrayDeque<>();
        // Create a new LinkedDeque (testing implementation)
        //LinkedDeque<Integer> testingDeque = new LinkedDeque<>();
        ArrayHeapMinPQ<Integer> testingHeap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> expectedArray = new NaiveMinPQ<>();

        // Add 1000000 random integers to both the expectedDeque and testingDeque implementations
        for (int i = 0; i < 22; i += 1) {
            int n = random.nextInt(100);
            //expectedArray.add(i, n);
            testingHeap.add(i, n);
        }
        // Check that testingDeque matches expectedDeque on all 1000000 integers

        //int expectedValue = expectedArray.removeSmallest();
        int testingValue1 = testingHeap.removeSmallest();
        assertEquals(15, testingValue1);
        assertEquals(1, (int) testingHeap.removeSmallest());
        int a =  testingHeap.removeSmallest();
        int b = testingHeap.removeSmallest();
        boolean temp = (a == 11 || a ==  14);
        assertEquals(true, temp);
        assertEquals(true, b == 11 || b == 14);
        assertEquals(8, (int) testingHeap.removeSmallest());
        assertEquals(17, (int) testingHeap.removeSmallest());
        assertEquals(10, (int) testingHeap.removeSmallest());

        int c =  testingHeap.removeSmallest();
        int d = testingHeap.removeSmallest();
        boolean temp2 = (c == 18 || c ==  20);
        assertEquals(true, temp2);
        assertEquals(true, d == 18 || d == 20);

        assertEquals(19, (int) testingHeap.removeSmallest());

        int e =  testingHeap.removeSmallest();
        int f = testingHeap.removeSmallest();
        boolean temp3 = (e == 9 || e ==  2);
        assertEquals(true, temp2);
        assertEquals(true, f == 2 || f == 9);

        assertEquals(12, (int) testingHeap.removeSmallest());
        assertEquals(13, (int) testingHeap.removeSmallest());
        assertEquals(3, (int) testingHeap.removeSmallest());
        assertEquals(4, (int) testingHeap.removeSmallest());
        assertEquals(21, (int) testingHeap.removeSmallest());
        assertEquals(6, (int) testingHeap.removeSmallest());
        assertEquals(16, (int) testingHeap.removeSmallest());
        assertEquals(0, (int) testingHeap.removeSmallest());
        assertEquals(5, (int) testingHeap.removeSmallest());
        assertEquals(7, (int) testingHeap.removeSmallest());
    }

    @Test
    public void testRandomOperations() {
        // Random seed ensures that each test run is reproducible
        int seed = 373; // or your favorite number
        Random random = new Random(seed);

        // Create a new ArrayDeque (reference implementation)
        // ArrayDeque<Integer> expectedDeque = new ArrayDeque<>();
        // Create a new LinkedDeque (testing implementation)
        //LinkedDeque<Integer> testingDeque = new LinkedDeque<>();

        ArrayHeapMinPQ<Integer> testingHeap = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> expectedArray = new NaiveMinPQ<>();

        // Try writing testing code here to add a few thousands items to both deques

        // For 1000000 iterations...
        for (int i = 0; i < 375; i += 1) {
            // Roll a six-sided dice returning a value 0, 1, 2, 3, 4, or 5
            int choice = random.nextInt(6);

            // Choose to perform one of six different actions
            if (choice == 0) {
                int n = random.nextInt();
                expectedArray.add(i, n);
                testingHeap.add(i, n);
            } else if (choice == 1) {
                int n = random.nextInt();
                expectedArray.add(i, n);
                testingHeap.add(i, n);
            } else if (choice == 2) {
                int expectedSize = expectedArray.size();
                int testingSize = testingHeap.size();
                assertEquals("size() failed on iteration " + i, expectedSize, testingSize);
            } else if (choice == 3) {
                if (expectedArray.isEmpty()) {
                    i -= 1;
                    continue;
                }
                int expectedValue = expectedArray.removeSmallest();
                int testingValue = testingHeap.removeSmallest();
                assertEquals("removeFirst() failed on iteration " + i, expectedValue, testingValue);
            } else if (choice == 4) {
                if (expectedArray.isEmpty()) {
                    i -= 1;
                    continue;
                }
                int expectedValue = expectedArray.removeSmallest();
                int testingValue = testingHeap.removeSmallest();
                assertEquals("removeLast() failed on iteration " + i, expectedValue, testingValue);
            } else {
                if (expectedArray.isEmpty()) {
                    i -= 1;
                    continue;
                }
                int maxSize = expectedArray.size();
                int index = random.nextInt(maxSize);

                int expectedValue = expectedArray.getSmallest();
                int testingValue = testingHeap.getSmallest();
                assertEquals("get(" + index + ") failed on iteration " + i, expectedValue, testingValue);
            }
        }
    }

    //事后删了
    @Test
    public void simpleTest() {
        ExtrinsicMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        ExtrinsicMinPQ<Integer> control = new NaiveMinPQ<>();
        int[] numbers = new int[]{12, 34, 52, 56, 13, 0, 42, 78, 123, 22};
        double[] priorities = new double[]{12, 34, 52, 56, 13, 0, 42, 78, 123, 22};
        for (int i = 0; i < 10; i++) {
            test.add((Integer) numbers[i], priorities[i]);
            control.add((Integer) numbers[i], priorities[i]);
        }
        for (int i = 0; i < 10; i++) {

            test.removeSmallest();
            control.removeSmallest();
        }
    }

    @Test
    public void randomizedTest1() {
        ExtrinsicMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        ExtrinsicMinPQ<Integer> control = new NaiveMinPQ<>();
        List<Integer> items = new ArrayList<>();

        //generate test and control heap
        Random r = new Random(337);
        int size = 1000;
        for (int i = 0; i < size; i++) {
            int item = i;
            double priority = r.nextDouble();
            test.add(item, priority);
            control.add(item, priority);
            items.add(item);
        }

        for (int i = 0; i < size / 2; i++) {
            items.remove(test.getSmallest());
            assertEquals(test.removeSmallest(), control.removeSmallest());
        }

        // then mess with priorities
        for (int x : items) {
            double newPriority = r.nextDouble();
            test.changePriority(x, newPriority);
            control.changePriority(x, newPriority);
        }

        // lastly remove all items
        while (test.size() > 0) {
            items.remove(test.getSmallest());
            assertEquals(test.removeSmallest(), control.removeSmallest());
        }
        assertEquals(test.size(), 0);
    }



}

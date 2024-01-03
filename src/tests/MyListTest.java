package tests;

import model.adts.MyList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private MyList<Integer> testList;

    @BeforeEach
    void setUp() {
        testList = new MyList<Integer>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAdd() {
        testList.add(20);
        assertEquals(testList.get(0), 20);
    }
}
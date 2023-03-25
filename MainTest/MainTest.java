import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void recieveAndCheckIdTestIsInDiapason() {
        Main main = new Main();
        //short inputId = 0;
        assertTrue(main.recieveAndCheckId((short)1) > 0 && main.recieveAndCheckId((short)1) < 6);
    }
    @Test
    void recieveAndCheckIdTestIsBelowOne() {
        Main main = new Main();
        assertTrue(main.recieveAndCheckId((short)0) > 0);
    }
    @Test
    void recieveAndCheckIdTestIsAboveFive() {
        Main main = new Main();
        assertTrue(main.recieveAndCheckId((short)6) < 6);
    }
}
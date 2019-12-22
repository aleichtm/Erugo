

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class NonPlayerTest.
 *
 * @author  Ari
 * @version 2019-12-19
 */
public class NonPlayerTest
{
    /**
     * Default constructor for test class NonPlayerTest
     */
    public NonPlayerTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        NonPlayer nonPlaye1 = new NonPlayer("B", 1, 1000, 2, false);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}

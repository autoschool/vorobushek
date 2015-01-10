package ru.qatools.school.vorobushek.service;

import junit.framework.TestCase;
import org.junit.Before;
import ru.qatools.school.vorobushek.Server;

/**
 * Created by yurik
 * 24.11.14.
 */
public class DatabaseProviderTest extends TestCase  {

    @Before
    public void openHomePage() {
        new Server();
    }

    public void testGetLogger() throws Exception {

        DatabaseProvider.getLogger().info("TEST LOGGER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}

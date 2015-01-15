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

    public void testDbConnection() throws Exception {

        DatabaseProvider.getLogger().info("Database user: " + DatabaseProvider.getDBUSER());
        DatabaseProvider.getLogger().info("Database pass: " + DatabaseProvider.getDBPASS());
        DatabaseProvider.getLogger().info("Database url: " + DatabaseProvider.getDBURL());

        DatabaseProvider.openConnection();
    }
}

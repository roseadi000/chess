package client;

import dataaccess.DataAccessException;
import dataaccess.sql.SQLDataAccess;
import org.junit.jupiter.api.*;
import server.Server;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;



public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() throws Exception {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        String url = String.format("http://localhost:%d", port);
        serverFacade = new ServerFacade(port);
    }

    @BeforeAll
    public static void clearServer() throws Exception {
        SQLDataAccess database = new SQLDataAccess();
        database.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    void register() {
        String result = serverFacade.register("N", "Puppies", "N@email.com");

        assertEquals("Welcome N!", result);
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void createGame() {
    }

    @Test
    void listGames() {
    }

    @Test
    void joinGame() {
    }

    @Test
    void observeGame() {
    }

    @Test
    void run() {
    }

}

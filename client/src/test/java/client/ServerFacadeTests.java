package client;

import dataaccess.sql.SQLDataAccess;
import org.junit.jupiter.api.*;
import server.Server;

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

    @BeforeEach
    public void clearServer() throws Exception {
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
    void registerStatusCodeNot200(){
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String result = serverFacade.register("N", "Puppies", "N@email.com");

        assertEquals("Error: The username is already in use", result);
    }

    @Test
    void logout() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String result = serverFacade.logout();

        assertEquals("Have a nice day!\nLog out successful", result);
    }
    @Test
    void logoutStatusCodeNot200(){
        String result = serverFacade.logout();

        assertEquals("Error: No Authorization Token found. Please log in", result);
    }


    @Test
    void login() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String logout = serverFacade.logout();
        String result = serverFacade.login("N", "Puppies");

        assertEquals("Welcome N!", result);
    }
    @Test
    void loginStatusCodeNot200(){
        String create = serverFacade.login("N", "Puppies");
        String logout = serverFacade.logout();
        String result = serverFacade.login("N", "Puppy");

        assertEquals("Error: The username or password was incorrect", result);
    }

    @Test
    void createGame() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String result = serverFacade.createGame("Robots");
        String id = result.split(": ")[1];
        String expected = String.format("Robots was created with id: %s", id);

        assertEquals(expected, result);
    }
    @Test
    void createGameStatusCodeNot200() {
        String result = serverFacade.createGame("Robots");

        assertEquals("Error: Please log in", result);
    }

    @Test
    void listGames() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String game = serverFacade.createGame("Robots");
        String id = game.split(": ")[1];
        String result = serverFacade.listGames();

        String expected = String.format("""
                Active Games:
                1. Robots
                   ID: %s
                   White: null
                   Black: null""", id);

        assertEquals(expected, result);
    }
    @Test
    void listGamesMoreThanOneGame() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String game1 = serverFacade.createGame("Robots");
        String id1 = game1.split(": ")[1];
        String game2 = serverFacade.createGame("Dogs");
        String id2 = game2.split(": ")[1];

        String result = serverFacade.listGames();

        String expected = String.format("""
                Active Games:
                1. Robots
                   ID: %s
                   White: null
                   Black: null
                2. Dogs
                   ID: %s
                   White: null
                   Black: null""", id1, id2);

        assertEquals(expected, result);
    }
    @Test
    void listGamesEmpty() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String result = serverFacade.listGames();

        String expected = "Active Games:";

        assertEquals(expected, result);
    }
    @Test
    void listGamesStatusCodeNot200() {
        String result = serverFacade.createGame("Robots");

        assertEquals("Error: Please log in", result);
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

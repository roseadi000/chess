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
        String result = serverFacade.listGames();

        String expected = """
                Active Games:
                1. Robots
                   White: null
                   Black: null""";

        assertEquals(expected, result);
    }
    @Test
    void listGamesMoreThanOneGame() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String game1 = serverFacade.createGame("Robots");
        String game2 = serverFacade.createGame("Dogs");

        String result = serverFacade.listGames();

        String expected = """
                Active Games:
                1. Robots
                   White: null
                   Black: null
                2. Dogs
                   White: null
                   Black: null""";

        assertEquals(expected, result);
    }
    @Test
    void listGamesStatusCodeNot200() {
        String result = serverFacade.createGame("Robots");

        assertEquals("Error: Please log in", result);
    }
    @Test
    void listGamesEmpty() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String result = serverFacade.listGames();

        String expected = "Active Games:";

        assertEquals(expected, result);
    }

    /*@Test
    void joinGameWhite() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String game = serverFacade.createGame("Robots");
        String list = serverFacade.listGames();
        String result = serverFacade.joinGame("white", 1);

        assertEquals("You have joined game 1 as color: WHITE", result);
    }
    @Test
    void joinGameBlack() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String game = serverFacade.createGame("Robots");
        String list = serverFacade.listGames();
        String result = serverFacade.joinGame("Black", 1);

        assertEquals("You have joined game 1 as color: BLACK", result);
    }
    @Test
    void joinGameInvalidGameIndex() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String game = serverFacade.createGame("Robots");
        String list = serverFacade.listGames();
        String result = serverFacade.joinGame("black", 2);

        assertEquals("Please enter a valid game index. Use 'list' to see active games", result);
    }
    @Test
    void joinGameStatusCodeNot200() {
        String create = serverFacade.register("N", "Puppies", "N@email.com");
        String game = serverFacade.createGame("Robots");
        String list = serverFacade.listGames();
        String join = serverFacade.joinGame("WHITE", 1);
        String create2 = serverFacade.register("Cyn", "Bleh", "cyn@gmail.com");
        String list2 = serverFacade.listGames();
        String result = serverFacade.joinGame("WHITE", 1);

        assertEquals("Error: Color is full. Please choose another color or game", result);
    }*/

    @Test
    void observeGame() {
    }

    @Test
    void run() {
    }

}

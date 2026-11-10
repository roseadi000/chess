package client;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import exceptions.GameNotFoundException;
import request.*;
import result.*;
import ui.UI;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ServerFacade {

    private final Integer port;
    private final String url;
    private final UI ui;
    private States state;
    private String authToken;
    private Integer gameID;

    public ServerFacade(Integer port) {
        this.port = port;
        this.url = "http://localhost";
        this.ui = new UI();
        this.state = States.LOGGED_OUT;
        this.authToken = "";
        this.gameID = null;
    }

    public void run() {
        CommandParser parser = new CommandParser();
        Scanner scanner = new Scanner(System.in);
        while (state != States.QUIT) {
            try {
                if (state == States.LOGGED_OUT) {
                    var input = ui.prelogin(scanner);
                    evalPreloginCommand(parser.evalInput(input), input);
                } else if (state == States.LOGGED_IN) {
                    var input = ui.postlogin(scanner);
                    evalPostloginCommand(parser.evalInput(input), input);
                } else if (state == States.PLAYING_WHITE) {
                    ui.displayChessBoardWhite(new ChessGame().getBoard());
                    state = States.LOGGED_IN;
                } else if (state == States.PLAYING_BLACK) {
                    ui.displayChessBoardBlack(new ChessGame().getBoard());
                    state = States.LOGGED_IN;
                } else if(state == States.OBSERVING){
                    ui.displayChessBoardWhite(new ChessGame().getBoard());
                    state = States.LOGGED_IN;
                }
            } catch (Exception e) {
                ui.printMessage(String.format("Error: %s", e.getMessage()));
            }

        }
    }

    public String register(String username, String password, String email) {
        RegisterRequest registerRequest = new RegisterRequest(email, username, password);
        String request = new Gson().toJson(registerRequest);
        String result = "";
        String registerURL = String.format("%s:%d/user", url, port);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(registerURL))
                    .POST(HttpRequest.BodyPublishers.ofString(request))
                    .build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                RegisterResult registerResult = new Gson().fromJson(response.body(), RegisterResult.class);
                result = String.format("Welcome %s!", registerResult.username());
                state = States.LOGGED_IN;
                authToken = registerResult.authToken();
            }
            else{
                result = evalErrorCode(response);
            }
        } catch (Exception e) {
            result = String.format("Error: %s", e.getMessage());
        }
        return result;
    }

        public String login (String username, String password){
            LoginRequest loginRequest = new LoginRequest(username, password);
            String request = new Gson().toJson(loginRequest);
            String result = "";
            String loginURL = String.format("%s:%d/session", url, port);

            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(new URI(loginURL))
                        .POST(HttpRequest.BodyPublishers.ofString(request))
                        .build();
                HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    LoginResult loginResult = new Gson().fromJson(response.body(), LoginResult.class);
                    result = String.format("Welcome %s!", loginResult.username());
                    state = States.LOGGED_IN;
                    authToken = loginResult.authToken();
                }
                else{
                    result = evalErrorCode(response);
                }
            } catch (Exception e) {
                result = String.format("Error: %s", e.getMessage());
            }
            return result;
        }

        public String logout () {
            String result = "";
            String logoutURL = String.format("%s:%d/session", url, port);

            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(new URI(logoutURL))
                        .DELETE()
                        .header("Authorization", authToken)
                        .build();
                HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    result = "Have a nice day!\nLog out successful";
                    state = States.LOGGED_OUT;
                    authToken = "";
                }
                else{
                    result = evalErrorCode(response);
                }
            } catch (Exception e) {
                result = String.format("Error: %s", e.getMessage());
            }
            return result;

        }

        public String createGame (String gameName){
            CreateGameRequestBody createGameRequest = new CreateGameRequestBody(gameName);
            String request = new Gson().toJson(createGameRequest);
            String result = "";
            String createGameURL = String.format("%s:%d/game", url, port);

            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(new URI(createGameURL))
                        .POST(HttpRequest.BodyPublishers.ofString(request))
                        .header("Authorization", authToken)
                        .build();
                HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    CreateGameResult createGameResult = new Gson().fromJson(response.body(), CreateGameResult.class);
                    result = String.format("%s was successfully created", createGameRequest.gameName());
                }
                else{
                    result = evalErrorCode(response);
                }
            } catch (Exception e) {
                result = String.format("Error: %s", e.getMessage());
            }
            return result;
        }

        public String listGames () {
            String result = "";
            String listGameURL = String.format("%s:%d/game", url, port);

            try{
                HttpResponse<String> response = getListofGames(listGameURL);

                if (response.statusCode() == 200) {
                    ListGamesResult listGamesResult = new Gson().fromJson(response.body(), ListGamesResult.class);
                    result = ui.formatGamesList(listGamesResult);
                }
                else{
                    result = evalErrorCode(response);
                }
            } catch (Exception e) {
                result = String.format("Error: %s", e.getMessage());
            }
            return result;
        }

        public String joinGame (String color, Integer gameIndex) {
            String result = "";
            String joinGameURL = String.format("%s:%d/game", url, port);
            try {
                gameID = getGame(gameIndex);
                JoinGameRequestBody joinGameRequest = new JoinGameRequestBody(color.toUpperCase(), gameID);
                String request = new Gson().toJson(joinGameRequest);

                try (HttpClient client = HttpClient.newHttpClient()) {
                    HttpRequest httpRequest = HttpRequest.newBuilder()
                            .uri(new URI(joinGameURL))
                            .PUT(HttpRequest.BodyPublishers.ofString(request))
                            .header("Authorization", authToken)
                            .build();
                    HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

                    if (response.statusCode() == 200) {
                        result = String.format("You have joined game %d as color: %s", gameIndex, color.toUpperCase());
                        setGameColorState(color.toUpperCase());
                        this.gameID = gameID;
                    } else {
                        result = evalErrorCode(response);
                    }
                }
            } catch (Exception e) {
                result = String.format("Error: %s", e.getMessage());
            }
            return result;
        }

        public String observeGame (Integer gameIndex) {
            String result = "";
            try {
                int gameID = getGame(gameIndex);
                    result = String.format("You are now observing game %s", gameIndex);
                    state = States.OBSERVING;
                    this.gameID = gameID;
            } catch (Exception e) {
                result = String.format("Error: %s", e.getMessage());
            }
            return result;
        }

        private void evalPreloginCommand(CommandParser.InputCommands command, String[] input){
            String result = "";
            if(command == CommandParser.InputCommands.HELP){
                result = """
                login <username> <password> - Login in to play
                register <username> <password> <email> - Don't have an account? Register
                quit - Exit the program""";
            }
            else if (command == CommandParser.InputCommands.REGISTER) {
                result = register(input[1], input[2], input[3]);
            }
            else if(command == CommandParser.InputCommands.LOGIN){
                result = login(input[1], input[2]);
            }
            else if(command == CommandParser.InputCommands.QUIT){
                state = States.QUIT;
                result = "Exiting program...";
            }
            else{
                result = "Error: Please enter a valid command. Use 'help' to see valid commands";
            }
            ui.printMessage(result);
        }

        private void evalPostloginCommand(CommandParser.InputCommands command, String[]input){
            String result = "";
            if(command == CommandParser.InputCommands.HELP){
                result = """
                logout - Logout of your account
                create <Name> - Create a chess game
                list - See the list of chess games
                join <ID> <WHITE|BLACK> - Join a chess game
                observe <ID> - Watch a chess game
                quit - Exit the program""";
            }
            else if(command == CommandParser.InputCommands.LOGOUT){
                result = logout();
            }
            else if(command == CommandParser.InputCommands.CREATE){
                result = createGame(input[1]);
            }
            else if(command == CommandParser.InputCommands.LIST){
                result = listGames();
            }
            else if(command == CommandParser.InputCommands.JOIN){
                    try {
                        result = joinGame(input[2], Integer.valueOf(input[1]));
                    } catch (NumberFormatException e) {
                        result = "Please do join <ID> <WHITE|BLACK>";
                    }
            }
            else if(command == CommandParser.InputCommands.OBSERVE){
                try {
                    result = observeGame(Integer.valueOf(input[1]));
                } catch (NumberFormatException e) {
                    result = "ID must be a number";
                }
            }
            else if(command == CommandParser.InputCommands.QUIT){
                state = States.QUIT;
                result = "Exiting program...";
            }
            else{
                result = "Error: Please enter a valid command. Use 'help' to see valid commands";
            }
            ui.printMessage(result);
        }

        private String evalErrorCode(HttpResponse<String> response){
            ErrorResult error = new Gson().fromJson(response.body(), ErrorResult.class);
            return error.message();
        }

        private HttpResponse<String> getListofGames(String url) throws URISyntaxException, IOException, InterruptedException {
            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(new URI(url))
                        .GET()
                        .header("Authorization", authToken)
                        .build();
                return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            }
        }


       private Integer getGameID(Integer gameIndex, ListGamesResult games){
            if(gameIndex <= games.games().size() && gameIndex > 0){
                return games.games().get(gameIndex - 1).gameID();
            }
            else{
                return null;
            }
        }

        private void setGameColorState(String color){
            if(color.equals("WHITE")){
                state = States.PLAYING_WHITE;
            }
            else{
                state = States.PLAYING_BLACK;
            }
        }

        private Integer getGame(Integer gameIndex) throws Exception {
            ListGamesResult games = null;
            String getGameURL = String.format("%s:%d/game", url, port);
            try {
                HttpResponse<String> listGames = getListofGames(getGameURL);
                if (listGames.statusCode() == 200) {
                    games = new Gson().fromJson(listGames.body(), ListGamesResult.class);
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            if (games.games().size() > 0) {
                Integer gameID = getGameID(gameIndex, games);
                if (gameID != null) {
                    return gameID;
                } else {
                    throw new GameNotFoundException("Please enter a valid game index. Use 'list' to see active games");
                }
            } else {
                throw new GameNotFoundException("There are no active games. Use 'create' to create a game");
            }
        }
}

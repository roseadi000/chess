package client;

import com.google.gson.Gson;
import request.*;
import result.*;
import ui.UI;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ServerFacade {

    private final Integer port;
    private final String url;
    private UI ui;
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
        var result = "";
        while (state != States.QUIT) {
            if (state == States.LOGGED_OUT) {
                var input = ui.prelogin();
                if (input.length > 0) {
                    evalInput(input);
                } else {
                    System.out.println("Please enter a command. Type 'help' for list of commands");
                }
            }
            else if(state == States.LOGGED_IN){
                var input = ui.postlogin();
                if (input.length > 0) {
                    evalInput(input);
                } else {
                    System.out.println("Please enter a command. Type 'help' for list of commands");
                }
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
            result = String.format("Error: %s", e.getMessage()); //edit later
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
                result = String.format("Error: %s", e.getMessage()); //edit later
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
                result = String.format("Error: %s", e.getMessage()); //edit later
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
                    result = String.format("%s was created with id: %d", createGameRequest.gameName(), createGameResult.gameID());
                }
                else{
                    result = evalErrorCode(response);
                }
            } catch (Exception e) {
                result = String.format("Error: %s", e.getMessage()); //edit later
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
            ListGamesResult games = null;
            String joinGameURL = String.format("%s:%d/game", url, port);
            try {
                HttpResponse<String> listGames = getListofGames(joinGameURL);
                if(listGames.statusCode() == 200){
                    games = new Gson().fromJson(listGames.body(), ListGamesResult.class);
                }
            } catch (Exception e) {
                return String.format("Error: %s", e.getMessage());
            }
            if(games != null && games.games().size() > 0) {
                Integer gameID = getGameID(gameIndex, games);
                if (gameID != null) {
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
                            state = States.PLAYING;
                        } else {
                            result = evalErrorCode(response);
                        }
                    } catch (Exception e) {
                        result = String.format("Error: %s", e.getMessage());
                    }
                } else {
                    result = "Please enter a valid game index. Use 'list' to see active games";
                }
            }
            else {
                result = "There are no active games. Use 'create' to create a game";
            }

            return result;
        }

        public String observeGame (Integer gameIndex) {
            String result = "";
            ListGamesResult games = null;
            String joinGameURL = String.format("%s:%d/game", url, port);
            try {
                HttpResponse<String> listGames = getListofGames(joinGameURL);
                if(listGames.statusCode() == 200){
                    games = new Gson().fromJson(listGames.body(), ListGamesResult.class);
                }
            } catch (Exception e) {
                return String.format("Error: %s", e.getMessage());
            }
            if(games != null && games.games().size() > 0) {
                Integer gameID = getGameID(gameIndex, games);
                if (gameID != null) {
                    result = String.format("You are now observing game %s", gameIndex);
                }
                else {
                    result = "Please enter a valid game index. Use 'list' to see active games";
                }
            }
            else {
                result = "There are no active games. Use 'create' to create a game";
            }
            return result;
        }

        private void evalInput (String[]input){
            String result = "";
            if (input[0].equals("register")) {
                if (input.length >= 4) {
                    result = register(input[1], input[2], input[3]);
                }
            }
            else if(input[0].equals("login")){
                if(input.length >= 3){
                    result = login(input[1], input[2]);
                }
            }
            else if(input[0].equals("logout")){
                result = logout();
            }
            else if(input[0].equals("create")){
                if(input.length >= 2) {
                    result = createGame(input[1]);
                }
            }
            else if(input[0].equals("list")){
                result = listGames();
            }
            else if(input[0].equals("join")){
                result = joinGame(input[1], Integer.valueOf(input[2]));
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
}

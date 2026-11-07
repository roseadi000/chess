package client;

import com.google.gson.Gson;
import request.CreateGameRequestBody;
import request.LoginRequest;
import request.RegisterRequest;
import result.CreateGameResult;
import result.ErrorResult;
import result.LoginResult;
import result.RegisterResult;
import ui.UI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerFacade {

    private final Integer port;
    private final String url;
    private UI ui;
    private States state;
    private String authToken;

    public ServerFacade(Integer port) {
        this.port = port;
        this.url = "http://localhost";
        this.ui = new UI();
        this.state = States.LOGGED_OUT;
        this.authToken = "";
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

        public void listGames () {

        }

        public void joinGame () {

        }

        public void observeGame () {

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
             UI.printMessage(result);
        }

        private String evalErrorCode(HttpResponse<String> response){
            ErrorResult error = new Gson().fromJson(response.body(), ErrorResult.class);
            return error.message();
        }
}

package client;

import com.google.gson.Gson;
import request.RegisterRequest;
import result.RegisterResult;
import ui.UI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerFacade {

    private final Integer port;
    private final String url;
    private States state;
    private UI ui;

    public ServerFacade(Integer port) {
        this.port = port;
        this.url = "http://localhost";
        this.state = States.LOGGED_OUT;
        this.ui = new UI();
    }

    public void run() {
        var result = "";
        while (state != States.QUIT) {
            if (state != States.LOGGED_OUT) {
                var input = ui.prelogin();
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
            }
        } catch (Exception e) {
            result = String.format("Error: %s", e.getMessage()); //edit later
        }
        return result;
    }

        public void login (String username, String password){

        }

        public void logout () {

        }

        public void createGame (String gameName){

        }

        public void listGames () {

        }

        public void joinGame () {

        }

        public void observeGame () {

        }

        private void evalInput (String[]input){
            if (input[0].equals("register")) {
                if (input.length >= 4) {
                    register(input[1], input[2], input[3]);
                }
            }
        }
}

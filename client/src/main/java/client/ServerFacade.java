package client;

import ui.UI;

import java.net.URI;

public class ServerFacade {

    private final URI uri;
    private States state;
    private UI ui;
    public ServerFacade(URI uri){
        this.uri = uri;
        this.state = States.LOGGED_OUT;
        this.ui = new UI();
    }

    public void run(){
        var result = "";
        while(state != States.QUIT) {
            if(state != States.LOGGED_OUT){
                var input = ui.prelogin();
                evalInput(input);
            }

        }
    }

    public String register(String username, String password, String email){
        return "";
    }

    public void login(String username, String password){

    }

    public void logout(){

    }

    public void createGame(String gameName){

    }

    public void listGames(){

    }

    public void joinGame(){

    }

    public void observeGame(){

    }

    private void evalInput(String[] input){

    }



}

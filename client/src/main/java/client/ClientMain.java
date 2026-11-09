package client;

import chess.*;

public class ClientMain {
    public static void main(String[] args) {
        int port = 8080;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }
        ServerFacade serverFacade = new ServerFacade(port);
        serverFacade.run();
    }
}

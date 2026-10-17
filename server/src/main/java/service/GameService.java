package service;

import dataaccess.DataAccess;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import result.JoinGameResult;
import result.ListGamesResult;

public class GameService {

    private final DataAccess da;

    public GameService(DataAccess da) {
        this.da = da;
    }

    public ListGamesResult listGames(ListGamesRequest listGamesRequest) {
        throw new RuntimeException("Not implemented yet");
    }

    public ListGamesResult createGame(CreateGameRequest createGameRequest){
        throw new RuntimeException("Not implemented yet");
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) {
        throw new RuntimeException("Not implemented yet");
    }
}

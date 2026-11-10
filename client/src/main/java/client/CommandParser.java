package client;

import exceptions.FieldEmptyException;
import exceptions.InvalidInputException;

public class CommandParser {

    public enum InputCommands {
        HELP,
        REGISTER,
        LOGIN,
        LOGOUT,
        LIST,
        CREATE,
        JOIN,
        OBSERVE,
        QUIT
    }

    public  InputCommands evalInput(String[] input) throws FieldEmptyException, InvalidInputException {
        String command = input[0].toLowerCase();
        if(input.length == 0){
            throw new FieldEmptyException("Please enter a command. Type 'help' for list of commands");
        }
        if(input[0].equals("help")){
            return InputCommands.HELP;
        }
        else if (command.equals("register")) {
            if (input.length >= 4) {
                return InputCommands.REGISTER;
            }
            else{
                throw new FieldEmptyException("Error: Please enter a username, password, and email");
            }
        }
        else if(command.equals("login")){
            if(input.length >= 3){
                return InputCommands.LOGIN;
            }
            else{
                throw new FieldEmptyException("Please enter a username and password");
            }
        }
        else if(command.equals("logout")){
            return InputCommands.LOGOUT;
        }
        else if(command.equals("list")){
            return InputCommands.LIST;
        }
        else if(command.equals("create")){
            if(input.length >= 2) {
                return InputCommands.CREATE;
            }
            else{
                throw new FieldEmptyException("Please enter a game name");
            }
        }
        else if(command.equals("join")){
            if(input.length >= 3) {
                return InputCommands.JOIN;
            }
            else{
                throw new FieldEmptyException("Please enter a game number and WHITE or BLACK");
            }
        }
        else if(command.equals("observe")){
            if(input.length >= 2) {
                return InputCommands.OBSERVE;
            }
            else{
                throw new FieldEmptyException("Please enter a game number");
            }
        }
        else if(command.equals("quit")){
            return InputCommands.QUIT;
        }
        else{
            throw new InvalidInputException("Please enter a valid command. Use 'help' to see commands");
        }
    }
}

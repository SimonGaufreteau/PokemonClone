package exceptions;

public class WrongDescriptionException extends Exception {
    public WrongDescriptionException(String description){super(String.format("There was an error with the following description : %s",description));}
}

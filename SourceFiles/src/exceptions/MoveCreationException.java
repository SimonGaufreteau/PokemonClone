package exceptions;

public class MoveCreationException extends Exception {

    public MoveCreationException(){
        super("A problem occurred while creating the move.");
    }
}
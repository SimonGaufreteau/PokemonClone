package exceptions;

public class MoveNotFoundException extends NotFoundException {

    public MoveNotFoundException(String name){
        super("Move",name);
    }
}

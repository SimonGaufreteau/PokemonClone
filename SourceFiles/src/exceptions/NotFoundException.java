package exceptions;

public class NotFoundException extends Exception{
    public NotFoundException(String type , String name){
        super(String.format("Error. Could not find the %s with the following name : %s",type, name));
    }
}

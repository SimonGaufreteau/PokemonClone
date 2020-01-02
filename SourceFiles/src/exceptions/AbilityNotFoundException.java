package exceptions;

public class AbilityNotFoundException extends NotFoundException {

    public AbilityNotFoundException(String name){
        super("Ability",name);
    }
}

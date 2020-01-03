package exceptions;

public class AbilityNotFoundException extends NotFoundException {

    public AbilityNotFoundException(String name){
        super("Ability",name);
    }

    public AbilityNotFoundException(int id){
        super("Ability",id);
    }

}

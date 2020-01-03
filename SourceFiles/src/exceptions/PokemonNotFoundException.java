package exceptions;

public class PokemonNotFoundException extends NotFoundException {

    public PokemonNotFoundException(String name){
        super("Pokemon",name);
    }
    public PokemonNotFoundException(int id){
        super("Pokemon",String.valueOf(id));
    }
}

package exceptions;

public class WordNotFoundException extends Exception {
    public WordNotFoundException(String word){super(String.format("Could not resolve the word : \"%s\"",word ));}
    public WordNotFoundException(String word,String sentence){super(String.format("Could not resolve the word : \"%s\" in the following sentence \"%s\"",word,sentence));}

}

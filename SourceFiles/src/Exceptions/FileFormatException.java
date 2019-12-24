package Exceptions;

public class FileFormatException extends Exception {

    public FileFormatException(String fileName){
        super(String.format("A problem occurred while reading the file. Please check the format of : %s", fileName));
    }
}

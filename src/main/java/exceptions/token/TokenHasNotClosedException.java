package exceptions.token;

public class TokenHasNotClosedException extends RuntimeException{
    public TokenHasNotClosedException(String text) {
        super(text);
    }
}

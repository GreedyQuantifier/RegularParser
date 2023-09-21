package exceptions.interpreter;

public class NotValidArgumentException extends InstructionException {

    public NotValidArgumentException(String s) {
        super(s);
    }
}

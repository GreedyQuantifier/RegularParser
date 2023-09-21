package lexer;

public class Token {


    String token;
    Boolean symbolToken;

    public Token(String string, Boolean symbolToken) {
        this.token = string;
        this.symbolToken = symbolToken;
    }


    public String getToken() {
        return token;
    }


    @Override
    public String toString() {
        return token;
    }

}

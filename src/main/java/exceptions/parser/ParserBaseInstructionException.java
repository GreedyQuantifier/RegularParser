package exceptions.parser;

import lexer.Token;

import java.util.List;

public class ParserBaseInstructionException extends RuntimeException {
    public ParserBaseInstructionException(String message) {
        super(message);
    }

    public ParserBaseInstructionException(List<Token> tokens, int tokenId) {
        super(String.format("Error syntax instruction into Token( %s ) number %d. Tokens: %s",
                tokens.get(tokenId), tokenId, tokens.stream().reduce(
                        (token, token2) -> new Token(token.getToken() + " " + token2.getToken(), false))));
    }

}

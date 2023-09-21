package exceptions.parser;

import lexer.Token;

import java.util.List;

public class ParserInstructionBodyHasNotFoundException extends ParserBaseInstructionException {
    public ParserInstructionBodyHasNotFoundException(String message) {
        super(message);
    }

    public ParserInstructionBodyHasNotFoundException(List<Token> tokens, int tokenId) {
        super(tokens, tokenId);
    }
}

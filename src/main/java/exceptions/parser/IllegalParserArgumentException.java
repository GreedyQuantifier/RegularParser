package exceptions.parser;

import lexer.Token;

import java.util.List;

public class IllegalParserArgumentException extends ParserBaseInstructionException {
    public IllegalParserArgumentException(String message) {
        super(message);
    }

    public IllegalParserArgumentException(List<Token> tokens, int tokenId) {
        super(tokens, tokenId);
    }
}

package exceptions.parser;

import lexer.Token;

import java.util.List;

public class ParserInsertNotValidException extends ParserBaseInstructionException {

    public ParserInsertNotValidException(List<Token> tokens, int tokenId) {
        super(tokens, tokenId);
    }
}

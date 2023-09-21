package lexer;

import repository.Storage;

import java.util.ArrayList;
import java.util.List;


public class Lexer {


    public final List<Token> tokenize;

    public Lexer(String reg) {
        tokenize = tokenize(reg);
    }

    List<Token> tokenize(String lemma) {
        char[] chars = lemma.toCharArray();


        List<Token> tokens = new ArrayList<>();

        Boolean flagDirection = null;
        StringBuilder builder = new StringBuilder();
        for (char item : chars) {
            boolean temp = Storage.isSymbolInstruction(item);
            if (flagDirection == null) flagDirection = temp;

            if (flagDirection != temp || Storage.isInstruction(builder.toString())) {
                tokens.add(new Token(builder.toString().trim(), temp));
                builder = new StringBuilder();
            }
            flagDirection = temp;
            builder.append(item);

        }
        if (!builder.isEmpty())
            tokens.add(new Token(builder.toString(), false));

        return tokens;
    }

}


package repository.instruction.release.contextInstruction;

import Interpreter.Data;
import exceptions.interpreter.NotValidArgumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import repository.Type;
import repository.instruction.release.Instruction;
import repository.instruction.release.InstructionOverload;

import java.io.IOException;

public class HtmlGetInstruction extends Instruction {

    public HtmlGetInstruction(Object content) {
        super(new Data(content,Type.Document));
    }

    @Override
    protected InstructionOverload getValidRealize(Type[] type) {

        if (type[0].equals(Type.Str) && type.length == 1) {

            return list -> {
                try {
                    Document document = Jsoup.connect(list.get(0).getValue().getString()).get();
                    return Data.documentToData(document);
                } catch (IOException e) {
                    throw new NotValidArgumentException("");
                }
            };
        }

        throw new NotValidArgumentException("Not valid arguments in Instruction");


    }




}

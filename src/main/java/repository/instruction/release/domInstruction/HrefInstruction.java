package repository.instruction.release.domInstruction;

import exceptions.instruction.NotValidArgumentToInstructionException;
import org.jsoup.Jsoup;
import repository.Type;
import repository.instruction.release.Instruction;
import repository.instruction.release.InstructionOverload;

import java.util.Arrays;

public class HrefInstruction extends Instruction {

    private static String href(String content, String select) {
        return Jsoup.parse(content).select(select).attr("href");
    }

    /***
     * Args Input
     * Str or List - Source to parse
     * Str - Query Selector
     *
     * Ret
     * Function for get href
     * @param list
     * @param objects
     */


    @Override
    protected InstructionOverload getValidRealize(Type[] type) {

        if (Arrays.equals(type, new Type[]{Type.Str, Type.Str}))
            return null;


        throw new NotValidArgumentToInstructionException("Arguments not valid " + Arrays.toString(type));
    }
}

package repository;

import repository.instruction.release.*;
import repository.instruction.release.baseInstruction.*;
import repository.instruction.release.domInstruction.HrefInstruction;
import repository.instruction.release.stringInstruction.ConcatenationInstruction;
import repository.instruction.release.stringInstruction.ReplaceInstruction;

import java.util.*;


@FunctionalInterface
interface FabricInstruction {
    Instruction doInstruction(Object content);
}


public class Storage {
    static final Map<String, FabricInstruction> storageInstruction = new HashMap<>();
    static Set<Character> listSymbols = new HashSet<>();


    static Map<String,Instruction> storage = new HashMap<>();

    static {
        symbolInstructionInit();
        MainInstructionInit();
    }


    static public Instruction instructionByName(String name) {
        return instructionByName(name, null);
    }

    static public Instruction instructionByName(String name, String content) {
        return storageInstruction.get(name).doInstruction(content);
    }

    static private void symbolInstructionInit() {

        storageInstruction.put("(", content ->
                new ObjInstruction());
        storageInstruction.put(")", content ->
                new InstructionSymbol(")"));
        storageInstruction.put(",", content ->
                new InstructionSymbol(","));
        storageInstruction.put("?", content ->
                new InstructionSymbol("?"));
        storageInstruction.put("$", content ->
                new InsertInstruction());
        storageInstruction.put("'", content ->
                new InstructionSymbol("'"));
        storageInstruction.put("\"", content ->
                new InstructionSymbol("\""));



        storageInstruction.forEach((key, value) -> {
            char[] chars = key.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                listSymbols.add(chars[i]);
            }
        });

    }

    static private void MainInstructionInit() {


        storageInstruction.put("Null", content ->
                new NullInstruction());

        storageInstruction.put("href", content ->
                new HrefInstruction());

        storageInstruction.put("obj", content ->
                new ObjInstruction());

        storageInstruction.put("concat", content ->
                new ConcatenationInstruction());

        storageInstruction.put("replace", content ->
                new ReplaceInstruction());

        storageInstruction.put("put", content ->
                new PutInstruction()
        );


    }



    public static boolean isInstruction(String symbols) {
        return storageInstruction.containsKey(symbols);
    }

    public static boolean isNotSymbolInstruction(String s) {
        if (!isInstruction(s))
            return false;
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if (listSymbols.contains(aChar)) return false;
        }

        return true;
    }


    public static boolean isSymbolInstruction(char instruction) {
        return listSymbols.contains(instruction);
    }


    static Type[] types(Type... types) {
        return Arrays.stream(types).toArray(Type[]::new);
    }

    static Instruction getStorageByName(String name) {
        return storage.get(name);
    }


}
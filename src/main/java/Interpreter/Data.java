package Interpreter;

import org.jsoup.nodes.Document;
import repository.Type;

import java.util.Arrays;
import java.util.List;

public class Data {

    Object object;
    Type type;
    Type[] types;


    public Data(Object object, Type type) {
        this.object = object;
        this.type = type;

    }

    public Data(Object object, Type type, Type[] types) {
        this.object = object;
        this.type = type;
        this.types = types;

    }

    public static Data stringToData(String token) {
        return new Data(token, Type.Str);
    }

    public static Data nullToData() {
        return new Data("", Type.Null);
    }

    public static Data listToData(List<Object> list, Type[] types) {
        return new Data(list, Type.Arr, types);
    }

    public static Data objToData(Object object) {
        return new Data(object, Type.Obj);
    }

    public static Data documentToData(Document document) {
        return new Data(document, Type.Document);
    }

    @Override
    public String toString() {
        switch (type) {
            case Str -> {
                return (String) object;
            }
            case Obj -> {
                return "(" + Arrays.toString(types) + ")";
            }
            case Arr -> {
                return "List(" + Arrays.toString(types) + ")";
            }
            case Null -> {
                return "Null";
            }
        }
        return null;
    }

    public Object getObject() {
        return object;
    }


    <T> T getValue(Object value, Class<T> clazz) {
        return clazz.cast(value);
    }


    public Type getType() {
        return type;
    }

    public Type[] getTypes() {
        return types;
    }


    public String getString() {
        return (String) object;
    }

    public Document getDocument() {
        return (Document) object;
    }


    public List getArr() {
        return null;
    }


}
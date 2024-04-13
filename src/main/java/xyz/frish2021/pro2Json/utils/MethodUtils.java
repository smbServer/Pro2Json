package xyz.frish2021.pro2Json.utils;

public class MethodUtils {
    public static String internalize(String name) {
        return switch (name) {
            case "int" -> "I";
            case "float" -> "F";
            case "double" -> "D";
            case "long" -> "J";
            case "boolean" -> "Z";
            case "short" -> "S";
            case "byte" -> "B";
            case "void" -> "V";
            default -> name.replace('.', '/');
        };
    }

    public static boolean notPrimitive(String name) {
        return switch (name) {
            case "int", "float", "double", "long", "boolean", "short", "byte", "void" -> false;
            default -> true;
        };
    }
}

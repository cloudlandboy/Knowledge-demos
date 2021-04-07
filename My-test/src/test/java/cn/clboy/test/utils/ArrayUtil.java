package cn.clboy.test.utils;


import java.util.Collection;

public class ArrayUtil {

    public static void charArrayToCollection(char[] arr, Collection<Character> collection) {
        for (Object o : arr) {
            collection.add((Character) o);
        }
    }
}
package cn.clboy.test.utils;


import java.util.Collection;
import java.util.Collections;

public class ArrayUtil {

    public static <T> void arrayToCollection(char[] arr, Collection<T> collection) {
        for (Object o : arr) {
            collection.add((T) o);
        }
    }
}
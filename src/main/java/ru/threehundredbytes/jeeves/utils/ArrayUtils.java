package ru.threehundredbytes.jeeves.utils;

public class ArrayUtils {
    private ArrayUtils() {
    }

    public static <T> boolean contains(T[] array, T target) {
        for (T t : array) {
            if (t.equals(target)) {
                return true;
            }
        }

        return false;
    }
}

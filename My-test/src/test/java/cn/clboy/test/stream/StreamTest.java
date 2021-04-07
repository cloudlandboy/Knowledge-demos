package cn.clboy.test.stream;


import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamTest {

    @Test
    public void IntStreamTest() {
        List<Integer> list = IntStream.range(1, 50).boxed().collect(Collectors.toList());
        System.out.println(list);
    }
}
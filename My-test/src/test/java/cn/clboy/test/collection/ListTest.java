package cn.clboy.test.collection;


import cn.clboy.test.utils.ArrayUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

    static final String AELEBYSTRING = "ABCD";
    static final String BELEBYSTRING = "CDEF";

    List<Character> a;
    List<Character> b;


    /**
     * 取两个集合的交集
     */
    @Test
    public void intersectionTest() {
        a.retainAll(this.b);
        System.out.println(a);
    }

    /**
     * 取两个集合不同的元素（差集）
     */
    @Test
    public void diffTest() {
        //创建一份a集合副本
        ArrayList<Character> temp = new ArrayList<>(a);
        //取交集
        temp.retainAll(b);

        a.removeAll(temp);
        b.removeAll(temp);
        a.addAll(b);
        System.out.println(a);
    }


    @Before
    public void before() {
        char[] arr1 = AELEBYSTRING.toCharArray();
        char[] arr2 = BELEBYSTRING.toCharArray();
        a = new ArrayList<Character>();
        ArrayUtil.arrayToCollection(arr1, a);
        b = new ArrayList<Character>();
        ArrayUtil.arrayToCollection(arr2, b);

        System.out.println(a);
        System.out.println(b);
    }
}
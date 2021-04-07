package cn.clboy.test.obj;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NUllTest {

    /**
     * null值能不能直接转为某个对象
     * <p>
     * 可以
     */
    @Test
    public void nullCanTransfer() {
        Map<String, Object> context = new HashMap<>(1);
        List<String> list = (List) context.get("oneKey");
        System.out.println(list);
    }

    /**
     * map删除一个null不存在的key返回什么
     * <p>
     * 返回null
     * <p>
     * 注意如果一个存在的key的value就是null很容易混淆
     * 判断key是否存在最好用containsKey方法
     */
    @Test
    public void mapRemoveNull() {
        Map<String, Object> context = new HashMap<>(1);
        Object removed = context.remove("oneKey");
        System.out.println(removed);
    }
}
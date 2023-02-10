package com.snow_kitten.better_storage.structs;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataMap extends HashMap<String, ArrayList<Byte>> {
    @Override
    public int size() {
        AtomicInteger size = new AtomicInteger();
        forEach((key, item) -> size.addAndGet(16 + item.size()));

        return size.get();
    }

    public void addExisted(String k, ArrayList<Byte> v) {
        var old_array = get(k);
        var new_array = new ArrayList<Byte>();
        var longest = (old_array.size() > v.size()) ? old_array : v;
        var shortest = (old_array.size() > v.size()) ? v : old_array;
        var buf = 0L;
        for (int i = 0; i < longest.size(); i++) {
            var old_num = longest.get(i).toLong();
            var new_num = 0L;
            if (shortest.size() >= i + 1) {
                new_num = shortest.get(i).toLong();
            }

            var num = old_num + new_num + buf;
            new_array.add(new Byte(new BigInteger(num + "").divideAndRemainder(new BigInteger(256 + ""))[1]));
            buf = num >>> 8L;
        }

        if (buf > 0) {
            new_array.add(new Byte(new BigInteger(buf + "")));
        }

        replace(k, new_array);
    }
}

package com.snow_kitten.better_storage.structs;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import oshi.util.tuples.Pair;

import java.math.BigInteger;
import java.util.*;

public class DataCard extends Item {
    private int length = 0;
    private final int maxSize;
    private final DataMap items = new DataMap();
    private final Map<String, Item> idBindings = new HashMap<>();

    public DataCard(int size) {
        super(new Properties().stacksTo(1));
        maxSize = size;
    }

    public int getLength() {
        return length;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void addItem(Item item, long count) {
        var key = new ItemStack(item).getDescriptionId();
        if (!items.containsKey(key)) {
            idBindings.put(key, item);
            items.put(key, toByteArray(new BigInteger(count + "")));
        } else {
            items.addExisted(key, toByteArray(new BigInteger(count + "")));
        }

        length = items.size();
    }

    public ItemStack getItem(String key) {
        return getItem(key, 1);
    }

    public ItemStack getItem(String key, long count) {
        var big_count = new BigInteger(count + "");
        var item = idBindings.get(key);
        if (compareBuffer(key, count)) {
            big_count = toBigInteger(items.get(key));
        }

        items.addExisted(key, toByteArray(big_count.negate()));

        var stack = new ItemStack(item);
        stack.setCount(big_count.intValueExact());

        return stack;
    }

    public String getItemCount(String key) {
        return toString(items.get(key));
    }

    private ArrayList<Byte> toByteArray(BigInteger number) {
        var array = new ArrayList<Byte>();
        while (!number.equals(new BigInteger("0"))) {
            var div = number.divideAndRemainder(new BigInteger(256 + ""));
            array.add(new Byte(div[1]));
            number = div[0];
        }

        return array;
    }

    private BigInteger toBigInteger(ArrayList<Byte> buf) {
        BigInteger number = new BigInteger("0");
        for (int i=buf.size()-1; i>=0; i--) {
            number = number.shiftLeft(8);
            number = number.add(new BigInteger(buf.get(i).toLong().toString()));
        }

        return number;
    }

    private String toString(ArrayList<Byte> buf) {
        BigInteger number = new BigInteger("0");
        for (int i=buf.size()-1; i>=0; i--) {
            number = number.shiftLeft(8);
            number = number.add(new BigInteger(buf.get(i).toLong().toString()));
        }

        return number.toString();
    }

    private boolean compareBuffer(String key, long number) {
        return smallerBuffer(items.get(key), toByteArray(new BigInteger(number + "")));
    }

    private boolean smallerBuffer(ArrayList<Byte> a, ArrayList<Byte> b) {
        if (a.size() < b.size()) return true;
        if (a.size() > b.size()) return false;
        for (int i=a.size()-1; i>=0; i--) {
            if (a.get(i).toInt() < b.get(i).toInt()) return true;
            if (a.get(i).toInt() > b.get(i).toInt()) return false;
        }

        return false;
    }
}


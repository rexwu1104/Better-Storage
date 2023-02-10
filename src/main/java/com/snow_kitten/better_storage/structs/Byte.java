package com.snow_kitten.better_storage.structs;

import java.math.BigInteger;

public class Byte {
    private final boolean[] _buffer;

//    private Byte(boolean[] buffer) {
//        _buffer = buffer;
//    }

    public Byte(BigInteger number) {
        _buffer = new boolean[8];
        byte idx = 0;
        while (!number.equals(new BigInteger("0"))) {
            var div = number.divideAndRemainder(new BigInteger("2"));
            _buffer[idx] = div[1].equals(new BigInteger("1"));
            number = div[0];
            idx++;
        }
    }

//    public Byte(int number) {
//        _buffer = new Byte((long)number).get_buffer();
//    }
//
//    public Byte() {
//        _buffer = new boolean[8];
//    }

//    public Byte or(Byte b) {
//        var arr = new boolean[8];
//        var buf = b.get_buffer();
//        for (int i=0; i<8; i++) {
//            arr[i] = _buffer[i] | buf[i];
//        }
//
//        return new Byte(arr);
//    }

    public Short toShort() {
        short number = 0;
        boolean check = false;
        for (byte i = 7; i >= 0; i--) {
            var bit = _buffer[i];
            if (!bit && !check) continue;
            if (bit) check = true;
            number <<= 1;
            number += bit ? 1 : 0;
        }

        return number;
    }

    public Integer toInt() {
        return (int) toShort();
    }

    public Long toLong() {
        return (long) toInt();
    }

//    public boolean[] get_buffer() {
//        return _buffer;
//    }
}

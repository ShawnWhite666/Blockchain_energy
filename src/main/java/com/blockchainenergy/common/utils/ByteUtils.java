package com.blockchainenergy.common.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.Stream;

public class ByteUtils {
    public static final byte[] EMPTY_ARRAY = new byte[0];
    public static final byte[] EMPTY_BYTES = new byte[32];
    public static final String ZERO_HASH = Hex.encodeHexString(EMPTY_BYTES);

    /**
     * 将多个字节数组合并成一个字节数组
     */
    public static byte[] merge(byte[]... bytes) {
        Stream<Byte> stream = Stream.of();
        for (byte[] b : bytes) {
            stream = Stream.concat(stream, Arrays.stream(ArrayUtils.toObject(b)));
        }
        return ArrayUtils.toPrimitive(stream.toArray(Byte[]::new));
    }

    /**
     * 转 byte[]
     */
    public static byte[] toBytes(long val) {
        return ByteBuffer.allocate(Long.BYTES).putLong(val).array();
    }
}

package org.math.primesieve;

import com.google.common.primitives.UnsignedLong;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.LongStream;

public class StorePrimesTest {
    @Test
    public void testStorePrimes() {
        long[] primes = LongStream.of(PrimeSieve.generatePrimes(1, 100)).filter(p->p!=0).toArray();
        Assert.assertEquals(25, primes.length);
        Assert.assertArrayEquals(new long[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,
                41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97}, primes);
    }

    @Test
    public void testStorePrimesUL() {
        long[] primes = LongStream.of(PrimeSieve.generatePrimes(UnsignedLong.valueOf("9223372036854775783"),
                UnsignedLong.valueOf("9223372036854775931"))).filter(p->p!=0).toArray();
        Assert.assertEquals(4, primes.length);
        Assert.assertEquals(UnsignedLong.valueOf("9223372036854775783"), UnsignedLong.fromLongBits(primes[0]));
        Assert.assertEquals(UnsignedLong.valueOf("9223372036854775837"), UnsignedLong.fromLongBits(primes[1]));
        Assert.assertEquals(UnsignedLong.valueOf("9223372036854775907"), UnsignedLong.fromLongBits(primes[2]));
        Assert.assertEquals(UnsignedLong.valueOf("9223372036854775931"), UnsignedLong.fromLongBits(primes[3]));
    }

    @Test
    public void testNoPrimes() {
        Assert.assertEquals(0, LongStream.of(PrimeSieve.generatePrimes(20, 22)).filter(p->p!=0).toArray().length);
    }
}

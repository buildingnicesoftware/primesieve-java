package org.math.primesieve;

import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedLong;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Slf4j
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

    @Test
    public void testPrimesAgainstSieve() {
        int n = 1_000_000_000;
        long startTime = System.currentTimeMillis();
        int[] sieve_primes = sieveToPrimes(computeSieve(n), n).toArray();
        int nThreads = new PrimeSieve().get_num_threads();
        long diff1 = System.currentTimeMillis()-startTime;
        startTime = System.currentTimeMillis();
        long[] primesieve_native = PrimeSieve.generatePrimes(1, n);
        long diff2 = System.currentTimeMillis()-startTime;
        int actual_size = primesieve_native.length;
        while (primesieve_native[actual_size-1]==0) {
            actual_size--;
        }
        log.info("up until n={}: bf sieve = {}ms, primesieve = {}ms, nThreads={}, nPrimes={}", n, diff1, diff2, nThreads, actual_size);
        Assert.assertEquals(sieve_primes.length, actual_size);
        for (int i=0; i<sieve_primes.length; i++) {
            Assert.assertEquals(sieve_primes[i], primesieve_native[i]);
        }
    }

    private static boolean[] computeSieve(int n) {
        Preconditions.checkArgument(n > 2);
        // only need to store half the memory, don't need evens
        boolean[] sieve = new boolean[(n - 2)/2];
        for (int i = 3; i <= Math.sqrt(n); i+=2) {
            if (sieve[(i-1)/2 - 1]) {
                continue;
            }
            int j = i;
            if (j + i >= n) {
                break;
            }
            do {
                j += i;
                if (j%2 == 1 && j%i == 0) {
                    sieve[(j-1)/2 - 1] = true;
                }
            } while ((j+i) < n);
        }
        return sieve;
    }

    private static IntStream sieveToPrimes(boolean[] sieve, int maxN) {
        Preconditions.checkArgument(maxN >= 0);
        int n = 2*sieve.length + 2;
        if (maxN == 0) {
            maxN = n;
        } else if (maxN > n) {
            throw new IllegalArgumentException("Max sieve size is " + n + ", received " + maxN);
        }
        return IntStream.concat(IntStream.of(2), IntStream.range(1, (maxN-2)/2+1).map(i->2*i+1).filter(p->isPrime(sieve, p)));
    }

    private static boolean isPrime(boolean[] sieve, int n) {
        if (n <= 2) {
            return n == 2;
        }
        if (n%2 == 0) {
            return false;
        }
        int target = (n-1)/2 - 1;
        return target < sieve.length && !sieve[target];
    }
}

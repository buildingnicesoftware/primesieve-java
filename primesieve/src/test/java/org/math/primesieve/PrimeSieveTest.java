package org.math.primesieve;

import com.google.common.primitives.UnsignedLong;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigInteger;

@Slf4j
@NotThreadSafe
public class PrimeSieveTest {
    @Test
    public void testVersion() {
        String v = new PrimeSieve().primesieve_version();
        log.info("Testing version {}", v);
        Assert.assertNotEquals("", v);
    }

    @Test
    public void testSieveSize() {
        PrimeSieve sieve = new PrimeSieve();
        sieve.set_sieve_size(1024);
        Assert.assertEquals(1024, sieve.get_sieve_size());
        sieve.set_sieve_size(-100); // illegal argument, too low
        Assert.assertEquals(16, sieve.get_sieve_size());
        sieve.set_sieve_size(10000); // illegal argument, too high
        Assert.assertEquals(8192, sieve.get_sieve_size());
    }

    @Test
    public void testNumThreads() {
        PrimeSieve sieve = new PrimeSieve();
        sieve.set_num_threads(10);
        Assert.assertEquals(10, sieve.get_num_threads());
        sieve.set_num_threads(-1); // illegal argument, too low
        Assert.assertEquals(1, sieve.get_num_threads());
        sieve.set_num_threads(1024); // illegal argument, too high
        Assert.assertEquals(16, sieve.get_num_threads());
    }

    @Test
    public void testMaxStop() {
        PrimeSieve sieve = new PrimeSieve();
        log.info("{}", sieve.get_max_stop());
        Assert.assertEquals(UnsignedLong.MAX_VALUE, sieve.get_max_stop());
    }

    @Test
    public void testPrintPrimes() {
        PrimeSieve sieve = new PrimeSieve();
        // XXX There is nothing to test here. The output will go to console and cannot be captured (ie,
        // using System.setOut won't capture it). This is for testing purposes only.
        sieve.print_primes(UnsignedLong.ONE, UnsignedLong.valueOf(100));
    }

    @Test
    public void testNthPrime() {
        PrimeSieve sieve = new PrimeSieve();
        // next prime after 100
        Assert.assertEquals(101, sieve.nth_prime(0, UnsignedLong.valueOf(100)).longValue());
        // 10 primes back from 100
        Assert.assertEquals(53, sieve.nth_prime(-10, UnsignedLong.valueOf(101)).longValue());
        // 10 primes forward from 0
        Assert.assertEquals(29, sieve.nth_prime(10).longValue());
        // Tests unsigned value
        BigInteger outOfRangeStart = BigInteger.TWO.pow(63).add(BigInteger.ONE);
        Assert.assertTrue(outOfRangeStart.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0);
        Assert.assertEquals("9223372036854775837", sieve.nth_prime(1,
                UnsignedLong.valueOf(outOfRangeStart.toString())).toString());
    }

    @Test
    public void testCountPrimes() {
        PrimeSieve sieve = new PrimeSieve();
        Assert.assertEquals(80, sieve.count_primes(UnsignedLong.valueOf(50), UnsignedLong.valueOf(500)).longValue());
        Assert.assertEquals(80, sieve.count_primes(50, 500));
        // Tests unsigned value: https://www.wolframalpha.com/input?i=next+prime+after+2%5E63%2B1
        BigInteger outOfRangeStart = BigInteger.TWO.pow(63).add(BigInteger.ONE);
        UnsignedLong outOfRangeStop = UnsignedLong.valueOf("9223372036854775838");
        Assert.assertTrue(outOfRangeStart.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0);
        Assert.assertEquals(1, sieve.count_primes(
                UnsignedLong.valueOf(outOfRangeStart.toString()), outOfRangeStop).longValue());
    }

    @Test
    public void testTwinPrimes() {
        PrimeSieve sieve = new PrimeSieve();
        Assert.assertEquals(8, sieve.count_twins(0, 100));
    }

    @Test
    public void testPrimeTriplets() {
        PrimeSieve sieve = new PrimeSieve();
        // https://en.wikipedia.org/wiki/Prime_triplet
        Assert.assertEquals(12, sieve.count_triplets(0, 150));
    }

    @Test
    public void testPrimeQuadruplets() {
        PrimeSieve sieve = new PrimeSieve();
        // https://en.wikipedia.org/wiki/Prime_quadruplet
        Assert.assertEquals(6, sieve.count_quadruplets(0, 1500));
    }

    @Test
    public void testPrimeQuintuplets() {
        PrimeSieve sieve = new PrimeSieve();
        // http://www.jakebakermaths.org.uk/maths/primequadsandquints.html
        Assert.assertEquals(15, sieve.count_quintuplets(0, 22000));
    }
    @Test
    public void testPrimeSextuplets() {
        PrimeSieve sieve = new PrimeSieve();
        // https://en.wikipedia.org/wiki/90_(number)#Prime_sextuplets
        Assert.assertEquals(2, sieve.count_sextuplets(0, 115));
    }
}

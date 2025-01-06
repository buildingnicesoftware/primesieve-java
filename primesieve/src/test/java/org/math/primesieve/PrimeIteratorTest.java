package org.math.primesieve;

import com.google.common.primitives.UnsignedLong;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Iterator;

public class PrimeIteratorTest {
    @Test
    public void testPrimeIteration() throws Exception {
        try (PrimeIterator primeItr = PrimeSieve.newPrimeIterator()) {
            Assert.assertEquals(2, primeItr.nextPrime());
            Assert.assertEquals(3, primeItr.nextPrime());
            Assert.assertEquals(5, primeItr.nextPrime());
            Assert.assertEquals(7, primeItr.nextPrime());
            Assert.assertEquals(5, primeItr.prevPrime());
            Assert.assertTrue(primeItr.size() > 10); // more like 128 for buffer
            primeItr.clear();
            Assert.assertEquals(2, primeItr.nextPrime());
        }
        try (PrimeIterator itr = PrimeSieve.newPrimeIterator(100, 100)) {
            Assert.assertEquals(101, itr.nextPrime());
        }
    }
    @Test
    public void testPrimeIterationUL() throws Exception {
        BigInteger outOfRangeStart = BigInteger.TWO.pow(63).add(BigInteger.ONE);
        Assert.assertTrue(outOfRangeStart.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0);
        try (PrimeIterator primeItr = PrimeSieve.newPrimeIterator(UnsignedLong.valueOf(outOfRangeStart.toString()))) {
            Assert.assertEquals(UnsignedLong.valueOf("9223372036854775837"), primeItr.nextULPrime());
            Assert.assertEquals(UnsignedLong.valueOf("9223372036854775783"), primeItr.prevULPrime());
        }
    }

    @Test
    public void testIterator() throws Exception {
        try (PrimeIterator primeItr = PrimeSieve.newPrimeIterator(1000)) {
            Iterator<UnsignedLong> itr = primeItr.iterator();
            Assert.assertTrue(itr.hasNext());
            Assert.assertEquals(1009, itr.next().longValue());
            Assert.assertTrue(itr.hasNext());
            Assert.assertEquals(1013, itr.next().longValue());
            try {
                itr.remove();
                Assert.fail("itr.remove() not implemented");
            } catch (UnsupportedOperationException e) {
                // ok
            }
        }
    }
}

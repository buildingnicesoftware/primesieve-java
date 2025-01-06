package org.math.primesieve;

import com.google.common.primitives.UnsignedLong;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.LongStream;

import static org.math.primesieve.PrimeSieve.loadJNI;

/**
 * Iterator implements {@link AutoCloseable} and usages must close the iterator to free up memory. Closing is
 * not thread-safe to usage.
 */
public final class PrimeIterator implements AutoCloseable, Iterable<UnsignedLong>, PrimitiveIterator.OfLong {
    static {
        loadJNI();
    }

    private final NativeIterator iterator;

    PrimeIterator() {
        this.iterator = new PrimeIterator.NativeIterator();
    }

    PrimeIterator(long start) {
        this.iterator = new PrimeIterator.NativeIterator(start);
    }

    PrimeIterator(long start, long stopHint) {
        this.iterator = new PrimeIterator.NativeIterator(start, stopHint);
    }

    public int size() {
        assert iterator.itr != 0;
        return iterator.size(iterator.itr);
    }

    public void clear() {
        assert iterator.itr != 0;
        iterator.clear(iterator.itr);
    }

    public long nextPrime() {
        assert iterator.itr != 0;
        return iterator.nextPrime(iterator.itr);
    }

    public UnsignedLong nextULPrime() {
        return UnsignedLong.fromLongBits(nextPrime());
    }

    public long prevPrime() {
        assert iterator.itr != 0;
        return iterator.prevPrime(iterator.itr);
    }

    public UnsignedLong prevULPrime() {
        return UnsignedLong.fromLongBits(prevPrime());
    }

    public LongStream stream() {
        return null;
    }

    @Override
    public void close() throws Exception {
        iterator.delete(iterator.itr);
        iterator.itr = 0;
    }

    @Override
    public Iterator<UnsignedLong> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public UnsignedLong next() {
                return nextULPrime();
            }
        };
    }

    @Override
    public void forEach(Consumer<? super UnsignedLong> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<UnsignedLong> spliterator() {
        return Iterable.super.spliterator();
    }

    @Override
    public long nextLong() {
        return nextPrime();
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    private static class NativeIterator {
        private long itr;

        private NativeIterator() {
            this.itr = init();
        }

        private NativeIterator(long start) {
            this.itr = initAt(start);
        }

        private NativeIterator(long start, long stopHint) {
            this.itr = initAt(start, stopHint);
        }

        private native void clear(long itr);

        private native long init();

        private native long initAt(long start);

        private native long initAt(long start, long stopHint);

        private native int size(long itr);

        private native long nextPrime(long itr);

        private native long prevPrime(long itr);

        private native void delete(long itr);
    }

    static class StorePrimes {
        static native int countUpper(long start, long stop);

        static native void storePrimes(long start, long stop, long[] array);
    }
}

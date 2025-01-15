package org.math.primesieve;

import com.google.common.primitives.UnsignedLong;

/**
 * Native bindings for PrimeSieve in Java.
 *
 * @see <a href="https://github.com/kimwalisch/primesieve/blob/master/include/primesieve.hpp">primesieve.hpp</a>
 * @see <a href="https://github.com/kimwalisch/primesieve/blob/master/include/primesieve/iterator.hpp">iterator.hpp</a>
 * @see <a href="https://github.com/kimwalisch/primesieve/blob/master/include/primesieve/StorePrimes.hpp">StorePrimes.hpp</a>
 */
@SuppressWarnings("unused")
public class PrimeSieve {
    static {
        loadJNI();
    }

    static void loadJNI() {
        System.loadLibrary("primesieve");
    }

    public static PrimeIterator newPrimeIterator() {
        return new PrimeIterator();
    }

    public static PrimeIterator newPrimeIterator(long start) {
        return new PrimeIterator(start);
    }

    public static PrimeIterator newPrimeIterator(UnsignedLong start) {
        return new PrimeIterator(start.longValue());
    }

    public static PrimeIterator newPrimeIterator(long start, long stopHint) {
        return new PrimeIterator(start, stopHint);
    }

    public static PrimeIterator newPrimeIterator(UnsignedLong start, UnsignedLong stopHint) {
        return new PrimeIterator(start.longValue(), stopHint.longValue());
    }

    public static int countUpper(long start, long stop) {
        return PrimeIterator.StorePrimes.countUpper(start, stop);
    }

    public static long[] storePrimes(long start, long stop) {
        int len = countUpper(start, stop);
        assert len >= 0;
        long[] ret = new long[len];
        PrimeIterator.StorePrimes.storePrimes(start, stop, ret);
        return ret;
    }

    public static long[] storePrimes(UnsignedLong start, UnsignedLong stop) {
        return storePrimes(start.longValue(), stop.longValue());
    }

    public static native String primesieve_version();

    /**
     * Calls {@link #nth_prime(long, UnsignedLong)} with start=0
     */
    public static UnsignedLong nth_prime(long n) {
        return nth_prime(n, UnsignedLong.ZERO);
    }

    public static UnsignedLong nth_prime(long n, UnsignedLong start) {
        return UnsignedLong.fromLongBits(nth_prime(n, start.longValue()));
    }
    private static native long nth_prime(long n, long start);

    /**
     * Unsafe version of {@link #count_primes(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public static long count_primes(long start, long stop) {
        return count_primes_impl(start, stop);
    }

    public static UnsignedLong count_primes(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_primes_impl(start.longValue(), stop.longValue()));
    }
    private static native long count_primes_impl(long start, long stop);

    /**
     * Unsafe version of {@link #count_twins(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public static long count_twins(long start, long stop) {
        return count_twins_impl(start, stop);
    }

    public static UnsignedLong count_twins(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_twins_impl(start.longValue(), stop.longValue()));
    }
    private static native long count_twins_impl(long start, long stop);

    /**
     * Unsafe version of {@link #count_triplets(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public static long count_triplets(long start, long stop) {
        return count_triplets_impl(start, stop);
    }

    public static UnsignedLong count_triplets(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_triplets_impl(start.longValue(), stop.longValue()));
    }
    private static native long count_triplets_impl(long start, long stop);

    /**
     * Unsafe version of {@link #count_quadruplets(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public static long count_quadruplets(long start, long stop) {
        return count_quadruplets_impl(start, stop);
    }

    public static UnsignedLong count_quadruplets(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_quadruplets(start.longValue(), stop.longValue()));
    }
    private static native long count_quadruplets_impl(long start, long stop);

    /**
     * Unsafe version of {@link #count_quintuplets(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public static long count_quintuplets(long start, long stop) {
        return count_quintuplets_impl(start, stop);
    }

    public static UnsignedLong count_quintuplets(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_quintuplets_impl(start.longValue(), stop.longValue()));
    }
    private static native long count_quintuplets_impl(long start, long stop);

    /**
     * Unsafe version of {@link #count_quadruplets(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public static long count_sextuplets(long start, long stop) {
        return count_sextuplets_impl(start, stop);
    }

    public static UnsignedLong count_sextuplets(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_sextuplets_impl(start.longValue(), stop.longValue()));
    }
    private static native long count_sextuplets_impl(long start, long stop);


    public static void print_primes(UnsignedLong start, UnsignedLong stop) {
        print_primes(start.longValue(), stop.longValue());
    }
    private static native void print_primes(long start, long stop);


    public static UnsignedLong get_max_stop() {
        return UnsignedLong.fromLongBits(get_max_stop_impl());
    }

    private static native long get_max_stop_impl();

    public static native int get_sieve_size();

    public static native int get_num_threads();


    public static native void set_sieve_size(int sieve_size);


    public static native void set_num_threads(int num_threads);
}

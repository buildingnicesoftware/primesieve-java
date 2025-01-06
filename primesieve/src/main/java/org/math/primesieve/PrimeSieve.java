package org.math.primesieve;

import com.google.common.primitives.UnsignedLong;

/**
 * Native bindings for PrimeSieve in Java. Documentation is copied from hpp files.
 */
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

    public static long[] generatePrimes(long start, long stop) {
        int len = countUpper(start, stop);
        assert len >= 0;
        long[] ret = new long[len];
        PrimeIterator.StorePrimes.storePrimes(start, stop, ret);
        return ret;
    }

    public static long[] generatePrimes(UnsignedLong start, UnsignedLong stop) {
        return generatePrimes(start.longValue(), stop.longValue());
    }

    public native String primesieve_version();

    /**
     * Calls {@link #nth_prime(long, UnsignedLong)} with start=0
     */
    public UnsignedLong nth_prime(long n) {
        return nth_prime(n, UnsignedLong.ZERO);
    }

    /// Find the nth prime.
    /// By default all CPU cores are used, use
    /// primesieve::set_num_threads(int threads) to change the
    /// number of threads.
    ///
    /// Note that each call to nth_prime(n, start) incurs an
    /// initialization overhead of O(sqrt(start)) even if n is tiny.
    /// Hence it is not a good idea to use nth_prime() repeatedly in a
    /// loop to get the next (or previous) prime. For this use case it
    /// is better to use a primesieve::iterator which needs to be
    /// initialized only once.
    ///
    /// @param n  if n = 0 finds the 1st prime >= start, <br/>
    ///           if n > 0 finds the nth prime > start, <br/>
    ///           if n < 0 finds the nth prime < start (backwards).
    ///
    public UnsignedLong nth_prime(long n, UnsignedLong start) {
        return UnsignedLong.fromLongBits(nth_prime(n, start.longValue()));
    }
    private native long nth_prime(long n, long start);

    /**
     * Unsafe version of {@link #count_primes(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public long count_primes(long start, long stop) {
        return count_primes_impl(start, stop);
    }

    /// Count the primes within the interval [start, stop].
    /// By default all CPU cores are used, use
    /// primesieve::set_num_threads(int threads) to change the
    /// number of threads.
    ///
    /// Note that each call to count_primes() incurs an initialization
    /// overhead of O(sqrt(stop)) even if the interval [start, stop]
    /// is tiny. Hence if you have written an algorithm that makes
    /// many calls to count_primes() it may be preferable to use
    /// a primesieve::iterator which needs to be initialized only once.
    ///
    public UnsignedLong count_primes(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_primes_impl(start.longValue(), stop.longValue()));
    }
    private native long count_primes_impl(long start, long stop);

    /**
     * Unsafe version of {@link #count_twins(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public long count_twins(long start, long stop) {
        return count_twins_impl(start, stop);
    }
    /// Count the twin primes within the interval [start, stop].
    /// By default all CPU cores are used, use
    /// primesieve::set_num_threads(int threads) to change the
    /// number of threads.
    ///
    public UnsignedLong count_twins(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_twins_impl(start.longValue(), stop.longValue()));
    }
    private native long count_twins_impl(long start, long stop);

    /**
     * Unsafe version of {@link #count_triplets(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public long count_triplets(long start, long stop) {
        return count_triplets_impl(start, stop);
    }
    /// Count the prime triplets within the interval [start, stop].
    /// By default all CPU cores are used, use
    /// primesieve::set_num_threads(int threads) to change the
    /// number of threads.
    ///
    public UnsignedLong count_triplets(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_triplets_impl(start.longValue(), stop.longValue()));
    }
    private native long count_triplets_impl(long start, long stop);

    /**
     * Unsafe version of {@link #count_quadruplets(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public long count_quadruplets(long start, long stop) {
        return count_quadruplets_impl(start, stop);
    }
    /// Count the prime quadruplets within the interval [start, stop].
    /// By default all CPU cores are used, use
    /// primesieve::set_num_threads(int threads) to change the
    /// number of threads.
    ///
    public UnsignedLong count_quadruplets(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_quadruplets(start.longValue(), stop.longValue()));
    }
    private native long count_quadruplets_impl(long start, long stop);

    /**
     * Unsafe version of {@link #count_quintuplets(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public long count_quintuplets(long start, long stop) {
        return count_quintuplets_impl(start, stop);
    }
    /// Count the prime quintuplets within the interval [start, stop].
    /// By default all CPU cores are used, use
    /// primesieve::set_num_threads(int threads) to change the
    /// number of threads.
    ///
    public UnsignedLong count_quintuplets(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_quintuplets_impl(start.longValue(), stop.longValue()));
    }
    private native long count_quintuplets_impl(long start, long stop);

    /**
     * Unsafe version of {@link #count_quadruplets(UnsignedLong, UnsignedLong)}.
     * This will work only until {@link Long#MAX_VALUE}, otherwise use the unsigned version.
     */
    public long count_sextuplets(long start, long stop) {
        return count_sextuplets_impl(start, stop);
    }
    /// Count the prime sextuplets within the interval [start, stop].
    /// By default all CPU cores are used, use
    /// primesieve::set_num_threads(int threads) to change the
    /// number of threads.
    ///
    public UnsignedLong count_sextuplets(UnsignedLong start, UnsignedLong stop) {
        return UnsignedLong.fromLongBits(count_sextuplets_impl(start.longValue(), stop.longValue()));
    }
    private native long count_sextuplets_impl(long start, long stop);

    /// Print the primes within the interval [start, stop]
    /// to the standard output.
    ///
    public void print_primes(UnsignedLong start, UnsignedLong stop) {
        print_primes(start.longValue(), stop.longValue());
    }
    private native void print_primes(long start, long stop);

    /// Returns the largest valid stop number for primesieve.
    /// @return 2^64-1 (UINT64_MAX).
    public UnsignedLong get_max_stop() {
        return UnsignedLong.fromLongBits(get_max_stop_impl());
    }

    private native long get_max_stop_impl();

    /// Get the current set sieve size in KiB.
    public native int get_sieve_size();

    /// Get the current set number of threads.
    public native int get_num_threads();

    /// Set the sieve size in KiB (kibibyte).
    /// The best sieving performance is achieved with a sieve size
    /// of your CPU's L1 or L2 cache size (per core).
    /// @pre sieve_size >= 16 && <= 8192.
    ///
    public native void set_sieve_size(int sieve_size);

    /// Set the number of threads for use in
    /// primesieve::count_*() and primesieve::nth_prime().
    /// By default all CPU cores are used.
    ///
    public native void set_num_threads(int num_threads);
}

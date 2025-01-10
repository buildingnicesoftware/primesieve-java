#include <org_math_primesieve_PrimeIterator_StorePrimes.h>
#include <primesieve/iterator.hpp>
#include <primesieve/StorePrimes.hpp>
#include <stdio.h>

JNIEXPORT jint JNICALL Java_org_math_primesieve_PrimeIterator_00024StorePrimes_countUpper(JNIEnv *, jclass, jlong jstart, jlong jstop) {
    uint64_t start = static_cast<uint64_t>(jstart);
    uint64_t stop = static_cast<uint64_t>(jstop);
    return static_cast<jlong>(primesieve::prime_count_upper(start, stop));
}

JNIEXPORT void JNICALL Java_org_math_primesieve_PrimeIterator_00024StorePrimes_storePrimes(JNIEnv *env, jclass cls, jlong jstart, jlong jstop, jlongArray j_arr) {
    uint64_t start = static_cast<uint64_t>(jstart);
    uint64_t stop = static_cast<uint64_t>(jstop);
    if (start > stop)
        return;
    uint64_t maxPrime64bits = 18446744073709551557ull;
    if (start > maxPrime64bits)
        return;

    primesieve::iterator it(start, stop);
    it.generate_next_primes();
    std::size_t upper_len = primesieve::prime_count_upper(start, stop);

    uint64_t limit = std::min(stop, maxPrime64bits - 1);
    //printf("storePrimes: limit=%d, max_prime=%d\n", limit, it.primes_[it.size_ - 1]);
    long* java_array = env->GetLongArrayElements(j_arr, 0);
    int j_arr_index = 0;
    // memcpy buffers
    for (; it.primes_[it.size_ - 1] <= limit; it.generate_next_primes()) {
        //printf("memcpy: %d, index=%d, max_prime=%d\n", it.size_, j_arr_index, it.primes_[it.size_ - 1]);
        std::memcpy(reinterpret_cast<uint64_t*>(&java_array[j_arr_index]), it.primes_, it.size_*sizeof(jlong));
        j_arr_index += it.size_;
    }
    // Set rest up until limit
    for (std::size_t i = 0; it.primes_[i] <= limit; i++) {
        //printf("index set: %d\n", j_arr_index);
        java_array[j_arr_index] = static_cast<long>(it.primes_[i]);
        j_arr_index++;
    }
    if (stop >= maxPrime64bits)
        java_array[j_arr_index] = static_cast<long>(maxPrime64bits);
    env->ReleaseLongArrayElements(j_arr, java_array, 0);
}
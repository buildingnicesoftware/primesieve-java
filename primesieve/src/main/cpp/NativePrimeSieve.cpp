#include <org_math_primesieve_PrimeSieve.h>
#include <primesieve.hpp>
#include <stdio.h>
#include <inttypes.h>

JNIEXPORT jstring JNICALL Java_org_math_primesieve_PrimeSieve_primesieve_1version
  (JNIEnv *env, jclass cls) {
    return env->NewStringUTF(primesieve::primesieve_version().c_str());
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeSieve_nth_1prime(JNIEnv *env, jclass cls, jlong n, jlong start) {
    return static_cast<jlong>(primesieve::nth_prime(static_cast<int64_t>(n), static_cast<uint64_t>(start)));
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeSieve_count_1primes_1impl(JNIEnv *env, jclass cls, jlong start, jlong stop) {
    return static_cast<jlong>(primesieve::count_primes(static_cast<uint64_t>(start), static_cast<uint64_t>(stop)));
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeSieve_count_1twins_1impl(JNIEnv *env, jclass cls, jlong start, jlong stop) {
    return static_cast<jlong>(primesieve::count_twins(static_cast<uint64_t>(start), static_cast<uint64_t>(stop)));
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeSieve_count_1triplets_1impl(JNIEnv *env, jclass cls,  jlong start, jlong stop) {
    return static_cast<jlong>(primesieve::count_triplets(static_cast<uint64_t>(start), static_cast<uint64_t>(stop)));
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeSieve_count_1quadruplets_1impl(JNIEnv *env, jclass cls,  jlong start, jlong stop) {
    return static_cast<jlong>(primesieve::count_quadruplets(static_cast<uint64_t>(start), static_cast<uint64_t>(stop)));
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeSieve_count_1quintuplets_1impl(JNIEnv *env, jclass cls,  jlong start, jlong stop) {
    return static_cast<jlong>(primesieve::count_quintuplets(static_cast<uint64_t>(start), static_cast<uint64_t>(stop)));
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeSieve_count_1sextuplets_1impl(JNIEnv *env, jclass cls,  jlong start, jlong stop) {
    return static_cast<jlong>(primesieve::count_sextuplets(static_cast<uint64_t>(start), static_cast<uint64_t>(stop)));
}

JNIEXPORT void JNICALL Java_org_math_primesieve_PrimeSieve_print_1primes(JNIEnv *env, jclass cls, jlong start, jlong stop) {
    //printf("Value of stop is: %" PRIu64 "\n", static_cast<uint64_t>(stop));
    primesieve::print_primes(static_cast<uint64_t>(start), static_cast<uint64_t>(stop));
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeSieve_get_1max_1stop_1impl(JNIEnv *env, jclass cls) {
    //printf("Max stop is: %" PRIu64 "\n", primesieve::get_max_stop());
    return static_cast<jlong>(primesieve::get_max_stop());
}

JNIEXPORT jint JNICALL Java_org_math_primesieve_PrimeSieve_get_1sieve_1size(JNIEnv *env, jclass cls) {
    return primesieve::get_sieve_size();
}

JNIEXPORT jint JNICALL Java_org_math_primesieve_PrimeSieve_get_1num_1threads(JNIEnv *env, jclass cls) {
    return primesieve::get_num_threads();
}

JNIEXPORT void JNICALL Java_org_math_primesieve_PrimeSieve_set_1sieve_1size(JNIEnv *env, jclass cls, jint n) {
    primesieve::set_sieve_size(n);
}

JNIEXPORT void JNICALL Java_org_math_primesieve_PrimeSieve_set_1num_1threads(JNIEnv *env, jclass cls, jint n) {
    primesieve::set_num_threads(n);
}
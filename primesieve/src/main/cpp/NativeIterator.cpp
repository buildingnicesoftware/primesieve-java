#include <org_math_primesieve_PrimeIterator_NativeIterator.h>
#include <primesieve/iterator.hpp>
#include <stdio.h>

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeIterator_00024NativeIterator_init(JNIEnv *env, jobject self) {
    return reinterpret_cast<jlong>(new primesieve::iterator());
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeIterator_00024NativeIterator_initAt__J(JNIEnv *env, jobject self, jlong start) {
    return reinterpret_cast<jlong>(new primesieve::iterator(static_cast<uint64_t>(start)));
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeIterator_00024NativeIterator_initAt__JJ(JNIEnv *env, jobject self, jlong start, jlong stopHint) {
    return reinterpret_cast<jlong>(new primesieve::iterator(static_cast<uint64_t>(start), static_cast<uint64_t>(stopHint)));
}

JNIEXPORT void JNICALL Java_org_math_primesieve_PrimeIterator_00024NativeIterator_delete(JNIEnv *env, jobject self, jlong itr) {
    delete reinterpret_cast<primesieve::iterator*>(itr);
}

JNIEXPORT void JNICALL Java_org_math_primesieve_PrimeIterator_00024NativeIterator_clear(JNIEnv *env, jobject self, jlong itr) {
    reinterpret_cast<primesieve::iterator*>(itr)->clear();
}

JNIEXPORT jint JNICALL Java_org_math_primesieve_PrimeIterator_00024NativeIterator_size(JNIEnv *env, jobject self, jlong itr) {
    return static_cast<jint>(reinterpret_cast<primesieve::iterator*>(itr)->size_);
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeIterator_00024NativeIterator_nextPrime(JNIEnv *env, jobject self, jlong itr) {
    return static_cast<jlong>(reinterpret_cast<primesieve::iterator*>(itr)->next_prime());
}

JNIEXPORT jlong JNICALL Java_org_math_primesieve_PrimeIterator_00024NativeIterator_prevPrime(JNIEnv *env, jobject self, jlong itr) {
    return static_cast<jlong>(reinterpret_cast<primesieve::iterator*>(itr)->prev_prime());
}
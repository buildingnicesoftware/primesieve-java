# primesieve-java

primesieve-java provides a native wrapper to [kimwalisch/primesieve](https://github.com/kimwalisch/primesieve).

## Prerequisites
primesieve-java is currently built and tested on MacOSX x86_64.
For the exact dependencies tested and contribute on other platforms, see [Development](#Development).

primesieve-java requires primesieve to be installed, ex: `brew install primesieve`

Check that the libraries and headers are installed:

* `ls /usr/local/include/primesieve*`
  * Should include primesieve.hpp, primes/StorePrimes.hpp, primes/iterator.hpp
* `ls /usr/local/lib/libprimesieve.*`
  * ex: libprimesieve.a, libprimesieve.dylib

## Build
primesieve-java must be built locally, ex:
* `git clone https://github.com/buildingnicesoftware/primesieve-java.git`
* `cd primesieve-java`

```console
./gradlew run
> Task :primesieve-main:run
0    [main] INFO  org.math.primesieve.PrimeSieveMain  - Calling native primesieve...
1    [main] INFO  org.math.primesieve.PrimeSieveMain  - Version: 12.6
907  [main] INFO  org.math.primesieve.PrimeSieveMain  - Generated primes from 1 to 1000000000: found 50847534 primes
```

## Usage
The subproject [primesieve-main](primesieve-main) shows an example of using primesieve-java as a dependency, ex:
```kotlin
dependencies {
    // This dependency is used by the application.
    implementation(project(":primesieve"))
    //...
```
The unit tests show example usage of primesieve-java. For example:
```java
    @Test
    public void testStorePrimes() {
        long[] primes = LongStream.of(PrimeSieve.generatePrimes(1, 100)).filter(p->p!=0).toArray();
        Assert.assertEquals(25, primes.length);
        Assert.assertArrayEquals(new long[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,
                41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97}, primes);
    }
```
Unit tests are defined for every native function and include testing against a naive [Sieve of Eratosthenes](https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes) for primes until 500M.

Where appropriate `UnsignedLong` is used from the [guava](https://guava.dev/) dependency.
In general this is provided for convenience and it is not recommended to use boxed primitives for performance
reasons. As an example where unsigned long might be useful:
```java
    @Test
    public void testPrimeIterationUL() throws Exception {
        BigInteger outOfRangeStart = BigInteger.TWO.pow(63).add(BigInteger.ONE);
        Assert.assertTrue(outOfRangeStart.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0);
        try (PrimeIterator primeItr = PrimeSieve.newPrimeIterator(UnsignedLong.valueOf(outOfRangeStart.toString()))) {
            Assert.assertEquals(UnsignedLong.valueOf("9223372036854775837"), primeItr.nextULPrime());
            Assert.assertEquals(UnsignedLong.valueOf("9223372036854775783"), primeItr.prevULPrime());
        }
    }
```
Note that generating primes until `n` will generate long[] arrays larger than required. For performance
reasons primesieve-java natively sizes the array one time and uses `memcpy` for efficient
copies from `primesieve::iterator` Implementations need to check for trailing zeros which mark the
end of the arrays. See the classes and javadoc for more information.

## Development
primesieve-java uses [nokee](https://repo.nokee.dev/) to compile natively. The compiler and linker 
arguments are set in [primesieve/build.gradle.kts](build.gradle.kts) under the section "library." 
If building on other platforms, this section will need updating to be platform specific and include
the required options.

Note the following:
* Not all of the print options are wrapped because they don't make sense for library usage
* PrimeStore for n primes not implemented yet
* A repo distribution is not assembled or published currently but I can develop one of you have aneed for it
* Currently tested on the following:
  * x86_64-apple-darwin24.2.0 (Sequoia 15.2)
  * primesieve 12.6
  * Xcode 16.2 (Build version 16C5032a)
  * JDK: GraalVM 23.0.1+11.1 (build 23.0.1+11-jvmci-b01)

## Contributing
Contributions are welcome, please contact Rich Giuli <rich@giuli.com>.
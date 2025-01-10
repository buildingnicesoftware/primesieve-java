# primesieve-java

primesieve-java provides a native wrapper to [kimwalisch/primesieve](https://github.com/kimwalisch/primesieve).

## Prerequisites
primesieve-java is currently built and tested on MacOSX and ubuntu x86_64.
For the exact dependencies tested and contribute on other platforms, see [Development](#Development).

primesieve-java requires primesieve to be installed, for example on MacOSX run: `brew install primesieve`

Check that the libraries and headers are installed:
On MacOSX:
* `ls /usr/local/include/primesieve*`
  * Should include primesieve.hpp, primes/StorePrimes.hpp, primes/iterator.hpp
* `ls /usr/local/lib/libprimesieve.*`
  * ex: libprimesieve.a, libprimesieve.dylib
On ubuntu:
  * `ls /usr/local/include/primesieve*`
  * `ls /usr/lib/libprimesieve.*`

## Build
Ubuntu is built automatically as part of the github workflow, see [.github/workflows/gradle.yml](gradle.yml).

For MacOSX, primesieve-java must be built locally, ex:
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
In general all `primesieve` methods to count and get nth prime etc are implemented as well as `primesieve::iterator`
and `StorePrimes` generating primes efficiently until a max `n`. For more details see [Development](#Development).

Note that generating primes will allocate java `long[]` arrays larger than required. For performance
reasons primesieve-java natively sizes the array one time and uses `memcpy` for efficient
copies from `primesieve::iterator` Implementations need to check for trailing zeros which mark the
end of the arrays. See the classes and javadoc for more information.

## Performance
Using all `8` CPU cores and `16` threads primesieve-java generates all primes up to `1 billion` into
a java `long[]` array in `~500ms`. For comparison, a naive, serial [Sieve of Eratosthenes](https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes)
was implemented in Java and took over `8s`. The test is implemented in
[StorePrimesTest.java](primesieve/src/test/java/org/math/primesieve/StorePrimesTest.java).

## Development
primesieve-java uses [nokee](https://repo.nokee.dev/) to compile natively. The compiler and linker 
arguments are set in [primesieve/build.gradle.kts](build.gradle.kts) under the `library` section. 
If building on other platforms, this section will need updating to be platform specific and include
the required options.

Note the following:
* Not all of the print options are wrapped because they don't make sense for library usage
* PrimeStore generating n primes is implemented yet
* `primesieve` error conditions and handling mapped to exceptions not implemented yet
* A repo distribution is not assembled or published currently but I can develop one of you have need for it
* Currently tested on ubuntu using the github workflow, and MacOSX with the following:
  * x86_64-apple-darwin24.2.0 (Sequoia 15.2)
  * primesieve 12.6
  * Xcode 16.2 (Build version 16C5032a)
  * JDK: GraalVM 23.0.1+11.1 (build 23.0.1+11-jvmci-b01)

## Contributing
Contributions are welcome, please contact Rich Giuli <rich@giuli.com>.
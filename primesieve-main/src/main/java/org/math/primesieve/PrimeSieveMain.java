package org.math.primesieve;
import lombok.extern.slf4j.Slf4j;
import java.util.stream.LongStream;

@Slf4j
public class PrimeSieveMain {
    public static void main(String[] args) {
        try {
            log.info("Calling native primesieve...");
            log.info("Version: {}", PrimeSieve.primesieve_version());
            long stop = 1_000_000_000L;
            long[] primes = PrimeSieve.generatePrimes(1, stop);
            log.info("Generated primes from 1 to {}: found {} primes", stop, LongStream.of(primes).filter(p->p!=0).count());
        } catch (Throwable ex) {
            log.error("Failed to call primesieve", ex);
        }
    }
}
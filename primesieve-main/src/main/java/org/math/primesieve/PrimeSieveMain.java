package org.math.primesieve;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.LongStream;

@Slf4j
public class PrimeSieveMain {
    public static void main(String[] args) {
        try {
            PrimeSieve native_ps = new PrimeSieve();

            log.info("Calling native primesieve...");
            log.info("Version: {}", native_ps.primesieve_version());
            long stop = 1_000_000_000L;
            long[] primes = PrimeSieve.generatePrimes(1, stop);
            int z_index = 0;
            for (long prime : primes) {
                if (prime != 0) {
                    z_index++;
                } else {
                    break;
                }
            }
            log.info("Generated primes from 1 to {}: found {} primes, first zero={}", stop, LongStream.of(primes).filter(p->p!=0).count(), z_index);
        } catch (Throwable ex) {
            log.error("Failed to call primesieve", ex);
        }
    }
}
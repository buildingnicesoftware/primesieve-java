package org.math.primesieve;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrimeSieveMain {
    public static void main(String[] args) {
        try {
            PrimeSieve native_ps = new PrimeSieve();

            log.info("Calling native primesieve...");
            log.info("{}", native_ps.primesieve_version());
        } catch (Throwable ex) {
            log.error("Failed to call primesieve", ex);
        }
    }
}
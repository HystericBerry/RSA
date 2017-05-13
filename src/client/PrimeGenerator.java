package client;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Utility responsible for generating random primes.
 */
public final class PrimeGenerator {
	private static Random _rand = new Random(System.currentTimeMillis());
	private static Set<Integer> _primeCache = initPrimeCache();
	private static final int SIEVE_SIZE = 100000;

	// prevent primegenerator from being initialized
	private PrimeGenerator() {
	};

	private static Set<Integer> initPrimeCache() {
		Set<Integer> cache = new TreeSet<Integer>();
		boolean[] isPrime = generatePrimes(SIEVE_SIZE);

		// Populate cache
		for (int i = 2; i < SIEVE_SIZE; ++i)
			if (isPrime[i])
				cache.add(i);

		return cache;
	}

	public static boolean isPrime(int p) {
		if (p <= SIEVE_SIZE)
			return _primeCache.contains(p);

		// Brute force is prime.
		if (p % 2 == 0)
			return false;
		for (int i = 3; i * i <= p; i += 2)
			if (p % i == 0)
				return false;

		_primeCache.add(p); // add to cache
		return true;
	}

	public static int getPrime() {
		int limit = _primeCache.size();
		// Don't pick primes that are too large... values can overflow
		return getPrime(limit);
	}

	public static int getPrime(int limit) {
		int iterations = _rand.nextInt(limit);
		Iterator<Integer> iter = _primeCache.iterator();
		Integer prime = null;
		for (int i = 0; i < iterations; ++i)
			prime = iter.next();
		return prime;
	}

	//Iterates through the list of primes to get a coprime
	public static int getCoprime(int prime) {
		int randInt = 0, limit = 1000, gcd;
		do {
			gcd = PrimeGenerator.gcd(randInt = (prime + _rand.nextInt(limit)), prime);
		} while (gcd != 1);

		return randInt;
	}

	// Calculates greatest common denominator of two integers
	private static int gcd(int p, int q) {
		if (q == 0)
			return p;
		else
			return gcd(q, p % q);
	}

	// Creates an array of booleans representing if a number is prime. Boolean[i] is true if number i is prime.
	private static boolean[] generatePrimes(int n) {
		boolean[] primes = new boolean[n + 1];
		for (int i = 2; i <= n; ++i)
			primes[i] = true;

		for (int i = 2; i * i <= n; ++i)
			if (primes[i])
				for (int j = i; i * j <= n; j++)
					primes[i * j] = false;

		return primes;
	}
}
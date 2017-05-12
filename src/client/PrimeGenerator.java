package client;

import java.util.TreeSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * Utility class responsible for generating random primes.
 */
public final class PrimeGenerator
{
	private static Random _rand = new Random( System.currentTimeMillis() );
	private static Set<Integer> _primeCache = initPrimeCache();
	private static final int SIEVESIZE = 100000;
	
	// prevent primegenerator from being initialized
	private PrimeGenerator() {};
	
	private static Set<Integer> initPrimeCache()
	{
		Set<Integer> cache = new TreeSet<Integer>();
		boolean[] isPrime = generatePrimes( SIEVESIZE );
		
		// populate cache
		for( int i = 2; i < SIEVESIZE; ++i )
			if( isPrime[i] )
				cache.add( i );
		
		return cache;
	}
	
	public static boolean isPrime( int p )
	{
		if( p <= SIEVESIZE )
			return _primeCache.contains( p );
		
		// brute force is prime.
		if ( p%2 == 0 ) return false;
	    for( int i=3; i*i<=p; i+=2 )
	    	if( p%i == 0 )
	            return false;
	    
	    _primeCache.add( p ); // add to cache
	    return true;
	}
	
	public static int getPrime()
	{
		int limit = _primeCache.size();
		
		// don't pick primes that are too large... values can overflow
		return getPrime( limit );
	}
	
	public static int getPrime( int limit )
	{
		int iterations = _rand.nextInt( limit );
		
		Iterator<Integer> iter = _primeCache.iterator();
		Integer prime = null;
		for( int i = 0; i < iterations; ++i )
			prime = iter.next();
		
		return prime;
	}
	
	public static int getCoprime( int prime )
	{
		int randInt = 0, limit = 1000;
		while( PrimeGenerator.gcd( randInt = (prime+_rand.nextInt( limit )), prime ) != 1 );
		
		return randInt;
	}
	
	private static int gcd(int p, int q) {
        if (q == 0) return p;
        else return gcd(q, p % q);
    }
	
	// creates array boolean[0, 100000]
	private static boolean[] generatePrimes( int n )
	{
		boolean[] primes = new boolean[n+1];
		for ( int i = 2; i <= n; ++i )
			primes[i] = true;
		
        for ( int i = 2; i*i <= n; ++i )
        	if (primes[i])
            	for (int j = i; i*j <= n; j++)
                	primes[i*j] = false;
        
        return primes;
	}
}
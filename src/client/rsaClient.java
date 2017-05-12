package client;

public class rsaClient
{
	private static boolean[] isPrime;
	
	public static void main(String[] args)
	{
		generatePrimes( 10000 );
		
		int p = 61, q = 53, n = p * q, e = 17, t = (p - 1) * (q - 1), d = 2753;
		
		System.out.println("expected d = " + d);
		d = modInverse( e, t );
		System.out.println("actual d = " + d);
		
		System.out.println("test 27^-1 (mod 392) = " + modInverse( 27, 392 ));
	}
	
	// shouldn't use this version
	public static int bruteForceModInverse(int a, int m)
	{
	    a = a%m;
	    for (int x=1; x<m; x++)
	       if ((a*x) % m == 1)
	          return x;
	    return -1;
	}
	
	// change this version
	public static int modInverse( int a, int m )
	{
	    int m0 = m, t, q;
	    int x0 = 0, x1 = 1;
	    
	    if (m == 1)
	    	return 0;
	    
	    while (a > 1)
	    {
	        // q is quotient
	        q = a / m;
	        
	        t = m;
	        
	        // m is remainder now, process same as
	        // Euclid's algo
	        m = a % m;
	        a = t;
	        
	        t = x0;
	        
	        x0 = x1 - q * x0;
	        
	        x1 = t;
	    }
	    
	    // Make x1 positive
	    if (x1 < 0)
	    	x1 += m0;
	    
	    return x1;
	}
	
	private static void generatePrimes( int n )
	{
		isPrime = new boolean[n+1];
        for (int i = 2; i <= n; i++)
        	isPrime[i] = true;

        // mark non-primes <= n using Sieve of Eratosthenes
        for (int factor = 2; factor*factor <= n; factor++)
        {
            // if factor is prime, then mark multiples of factor as nonprime
            // suffices to consider mutiples factor, factor+1, ...,  n/factor
            if (isPrime[factor])
            {
                for (int j = factor; factor*j <= n; j++)
                	isPrime[factor*j] = false;
            }
        }
	}
}
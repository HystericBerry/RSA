package client;

public class rsaClient
{
	public static void main(String[] args)
	{
//		int p = 61, q = 53, n = p * q, e = 17, t = (p - 1) * (q - 1), d = 2753;
//		
//		System.out.println( "Random coprime = " + PrimeGenerator.getCoprime( t ) );
//		System.out.println("expected d = " + d);
//		d = modInverse( e, t );
//		System.out.println("actual d = " + d);
//		System.out.println("test 27^-1 (mod 392) = " + modInverse( 27, 392 ));
		
		KeyGenerator keyGen = new KeyGenerator();
		keyGen.generateKeys();
		
		PublicKey pubKey = keyGen.getPublicKey();
		PrivateKey privKey = keyGen.getPrivateKey();
		
		String msg = "Aleks says: Hello World!";
		System.out.println("original msg = "+msg);
		pubKey.encrypt( msg );
		
		System.out.println("encrypted message = "+pubKey.toString());
		System.out.println(privKey.decrypt(pubKey.getEncryptedMsg()));
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
}
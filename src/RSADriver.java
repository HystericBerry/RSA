import java.math.BigInteger;

public class RSADriver
{
	// old version
	public static void main( String[] args )
	{
		int p = 61, q = 53, n = p * q, e = 17, t = (p - 1) * (q - 1), d = modInverse(e, t);
//		int p = 29, q = 31, n = p * q, e = 11, t = (p - 1) * (q - 1), d = modInverse(e, t);
		
		System.out.println("p = " + p);
		System.out.println("q = " + q);
		System.out.println("n = " + n);
		System.out.println("t = " + t);
		System.out.println("e = " + e);
		System.out.println("d = " + d);
		
		
		String msg = "Aleks says: Hello World!";
		System.out.println("Original message = ");
		System.out.println(msg);
		
		BigInteger[] encryptedMessage = encrypt( msg, e, n );
		
		System.out.println("Encrypted message = ");
		for( BigInteger b : encryptedMessage )
			System.out.print(b.toString());
		System.out.println();
		
		
		String decryptedMsg = decrypt( encryptedMessage, d, n );
		
		System.out.println("Decrypted message = ");
		System.out.println(decryptedMsg);
	}
	
	public static BigInteger[] encrypt( String M, int e, int n )
	{
		BigInteger[] C = new BigInteger[M.length()];
		BigInteger bigE = new BigInteger( Integer.toString(e) );
		BigInteger bigN = new BigInteger( Integer.toString(n) );
		
		for( int i = 0; i < M.length(); ++i )
		{
			BigInteger bigM = new BigInteger( Integer.toString(M.charAt(i)) );
			
			C[i] = bigM.modPow( bigE, bigN );
		}
		return C;
	}
	
	public static String decrypt( BigInteger[] C, int e, int n )
	{
		char[] M = new char[C.length];
		BigInteger bigE = new BigInteger( Integer.toString(e) );
		BigInteger bigN = new BigInteger( Integer.toString(n) );
		
		for( int i = 0; i < M.length; ++i )
		{
			BigInteger bigC = C[i];
			M[i] = (char) bigC.modPow( bigE, bigN ).intValue();
		}
		return new String(M);
	}
	
	public static int modInverse(int a, int m)
	{
	    a = a%m;
	    for (int x=1; x<m; x++)
	       if ((a*x) % m == 1)
	          return x;
	    return -1;
	}
}
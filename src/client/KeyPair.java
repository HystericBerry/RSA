package client;

/**
 * Generates and stores public and private key pairs.
 */
public class KeyPair
{
	public static final int LIMIT = 100; // Limit this exercise to the first 100 primes

	public KeyPair()
	{
		generateKeys();
	}

	private void generateKeys()
	{
		int p, q;
		p = PrimeGenerator.getPrime(LIMIT); // Generates a prime p
		do {
			q = PrimeGenerator.getPrime(LIMIT);
		} while (q == p); // Make sure primes aren't duplicate

		int n = p * q;
		int totient = (p - 1) * (q - 1);

		int e = PrimeGenerator.getCoprime(totient);
		int d = modInverse(e, totient);

		// create public and private keys
		this._pubKey = new PublicKey(e, n);
		this._privKey = new PrivateKey(d, n);
	}

	public PublicKey getPublicKey()
	{
		return _pubKey;
	}

	public PrivateKey getPrivateKey()
	{
		return _privKey;
	}
	
	//Calculate modulus inverse using extended euclidean
	private int modInverse(int number, int modulus)
	{
		int m0 = modulus, temp, quotient;
		int x0 = 0, x1 = 1;
		while (number > 1) {
			quotient = number / modulus;
			temp = modulus;

			modulus = number % modulus;
			number = temp;
			
			temp = x0;
			x0 = x1 - quotient * x0;
			x1 = temp;
		}

		// Make x1 positive
		if (x1 < 0)
			x1 += m0;
		return x1;
	}

	private PublicKey _pubKey;
	private PrivateKey _privKey;
}
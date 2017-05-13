package client;

/**
 * Generates and stores public and private key pairs.
 */
public class KeyPair {
	public static final int LIMIT = 100; // Limit this exercise to the first 100 primes

	public KeyPair() {
		generateKeys();
	}

	private void generateKeys() {
		int p, q;
		p = PrimeGenerator.getPrime(LIMIT); // Generates a prime p
		do {
			q = PrimeGenerator.getPrime(LIMIT);
		} while (q == p); // Make sure primes aren't duplicate

		int n = p * q;
		int totient = (p - 1) * (q - 1);

		int e = PrimeGenerator.getCoprime(totient);
		int d = rsaClient.modInverse(e, totient);

		// create public and private keys
		this._pubKey = new PublicKey(e, n);
		this._privKey = new PrivateKey(d, n);
	}

	public PublicKey getPublicKey() {
		return _pubKey;
	}

	public PrivateKey getPrivateKey() {
		return _privKey;
	}

	private PublicKey _pubKey;
	private PrivateKey _privKey;
}
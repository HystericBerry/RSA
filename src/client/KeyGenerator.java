package client;

public class KeyGenerator
{
	public void generateKeys()
	{
		int p, q, limit = 100; // limit this exercise to the first 100 primes
		p = PrimeGenerator.getPrime( limit ); // gets p prime
		while( (q = PrimeGenerator.getPrime( limit )) == p ); // gets q prime
		
		int n = p * q, t = (p - 1) * (q - 1);
		
		int e = PrimeGenerator.getCoprime( t );
		int d = rsaClient.modInverse(e, t); // change this method later.
		
		// create public and private keys
		this._pubKey = new PublicKey( e, n );
		this._privKey = new PrivateKey( d, n );
	}
	
	public PublicKey getPublicKey()
	{
		if( _pubKey == null )
			throw new NullPointerException("Generate a key first.");
		return _pubKey;
	}
	
	public PrivateKey getPrivateKey()
	{
		if( _privKey == null )
			throw new NullPointerException("Generate a key first.");
		return _privKey;
	}
	
	PublicKey _pubKey;
	PrivateKey _privKey;
}
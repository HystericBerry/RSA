package client;

import java.math.BigInteger;

public class KeyGenerator
{
	public void generateKeys()
	{
		//int p = 61, q = 53, n = p * q, e = 17, t = (p - 1) * (q - 1), d = modInverse(e, t);
		int p = 61, q = 53, n = p * q, e = 17, t = (p - 1) * (q - 1);
		this._pubKey = new PublicKey( e, n );
		
		int d = rsaClient.modInverse(e, t); // change this method later.
		this._privKey = new PrivateKey( d, n );
	}
	
	public PublicKey getPublicKey()
	{
		return _pubKey;
	}
	
	public PrivateKey getPrivateKey()
	{
		return _privKey;
	}
	
	PublicKey _pubKey;
	PrivateKey _privKey;
}
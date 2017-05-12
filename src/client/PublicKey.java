package client;

import java.math.BigInteger;

public class PublicKey
{
	public PublicKey( int e, int n )
	{
		this._e = e;
		this._n = n;
	}
	
	public void encrypt( String M )
	{
		StringBuilder str = new StringBuilder();
		
		this._C = new BigInteger[M.length()];
		BigInteger bigE = new BigInteger( Integer.toString(this._e) );
		BigInteger bigN = new BigInteger( Integer.toString(this._n) );
		
		for( int i = 0; i < M.length(); ++i )
		{
			BigInteger bigM = new BigInteger( Integer.toString(M.charAt(i)) );
			
			this._C[i] = bigM.modPow( bigE, bigN );
			str.append(this._C[i]);
		}
		
		encryptedMsg = str.toString();
	}
	
	public BigInteger[] getEncryptedMsg()
	{
		return this._C;
	}
	
	@Override
	public String toString()
	{
		if( encryptedMsg == null )
			return "message has not been encrypted yet.";
		
		return encryptedMsg;
	}
	
	private String encryptedMsg;
	
	private BigInteger[] _C;
	
	private int _e, _n;
}
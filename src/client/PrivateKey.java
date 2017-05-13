package client;

import java.math.BigInteger;

public class PrivateKey {
	public PrivateKey(int d, int n) {
		_d = d;
		_n = n;
	}

	public String decrypt(BigInteger[] C) {
		char[] M = new char[C.length];
		BigInteger bigE = new BigInteger(Integer.toString(this._d));
		BigInteger bigN = new BigInteger(Integer.toString(this._n));

		for (int i = 0; i < M.length; ++i) {
			BigInteger bigC = C[i];
			M[i] = (char) bigC.modPow(bigE, bigN).intValue();
		}
		return new String(M);
	}

	private int _d, _n;
}
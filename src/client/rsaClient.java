package client;

public class rsaClient {
	public static void main(String[] args) {
		KeyPair keyPair = new KeyPair();
		PublicKey pubKey = keyPair.getPublicKey();
		PrivateKey privKey = keyPair.getPrivateKey();

		String msg = "Aleks says: Hello World!";
		System.out.println("original msg = " + msg);
		pubKey.encrypt(msg);

		System.out.println("encrypted message = " + pubKey.toString());
		System.out.println("decrypted message = " + privKey.decrypt(pubKey.getEncryptedMsg()));
	}

	//Calculate modulus inverse using extended euclidean
	public static int modInverse(int number, int modulus) {
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
}
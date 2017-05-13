package client;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class RSAClient
{
	public static void main(String[] args)
	{
		// If some messages are not decrypted successfully when they should be
		// it may be because of integer overflows please re-run the program
		System.out.println("**********VALUES MAY OVERFLOW**********");
		// Initialize keypair mapping
		String[] names = new String[] { "alice", "bob", "charles", "dave", 
				"ellie", "liam", "george", "harold" };
		Map<String, KeyPair> keypairMap = new HashMap<String, KeyPair>();
		Map<String, PublicKey> publicKeys = new HashMap<String, PublicKey>();
		for( int i = 0; i < names.length; ++i )
		{
			// Display the factors for creating the RSA Keys
			KeyPair currentPair = new KeyPair();
			System.out.printf("%s has the KeyPair factors of:\n", names[i]);
			System.out.println("PublicKey = "+currentPair.getPublicKey().getFactors());
			System.out.println("PrivateKey = "+currentPair.getPrivateKey().getFactors()+"\n");
			
			keypairMap.put( names[i], currentPair );
			
			// Every person that wants to send a message to person 
			// names[i] needs that person's public key
			publicKeys.put( names[i], currentPair.getPublicKey() );
		}
		
		// initialize secret messages
		String[] messages = new String[]
				{
				"alice says: \tHello World!",
				"bob says: \tEureka!",
				"charles says: \tInconceivable!",
				"dave says: \tThis is a secret message!",
				"ellie says: \tHere there everywhere!", 
				"liam says: \tI have a special set of skills...",
				"george says: \tPi R Squared.",
				"harold says: \tFood for thought."
				};
		Map<String, String> msgMap = new HashMap<String, String>();
		for( int i = 0; i < names.length; ++i )
		{
			System.out.printf("%s has the secret message:\n", names[i]);
			System.out.println("\""+messages[i]+"\"\n");
			msgMap.put( names[i], messages[i] );
		}
		
		crytpoExample(publicKeys, keypairMap, msgMap, names );
	}
	
	private static void crytpoExample( Map<String, PublicKey> publicKeys, 
			Map<String, KeyPair> keypairMap, Map<String, String> msgMap, String[] names )
	{
		// successfully encrypt, send, receive and decode different messages
		Random rand = new Random( System.currentTimeMillis() );
		for( int i = 0; i < 5; ++i )
		{
			// pick random sender, receiver and "hacker" (not really a hacker)
			int sender = rand.nextInt( names.length ), receiver, hacker;
			do receiver = rand.nextInt( names.length );
			while( sender == receiver );
			do hacker = rand.nextInt( names.length );
			while( hacker == receiver || hacker == sender );
			
			String senderName = names[sender], receiverName = names[receiver], hackerName = names[hacker];
			
			// sender needs to get receiver's PublicKey
			// receiver needs to use receiver's PrivateKey
			// "hacker" will attempt to decode using personal PrivateKey
			PublicKey rPublicKey = publicKeys.get( receiverName );
			PrivateKey rPrivateKey = keypairMap.get( receiverName ).getPrivateKey();
			
			// Print original message of sender
			String originalMsg = msgMap.get(senderName);
			System.out.printf("%s sends message:\n%s\n", senderName, originalMsg);
			
			// Sender uses receiver's PublicKey to ecrypt message
			rPublicKey.encrypt(originalMsg);
			System.out.printf("%s's encrypted message looks like:\n%s\n", senderName, rPublicKey.toString());
			
			// Receiver successfully decrypts encrypted message using private key
			String decryptedMsg = rPrivateKey.decrypt(rPublicKey.getEncryptedMsg());
			Assert.assertEquals( originalMsg, decryptedMsg );
			System.out.printf("%s's successfully decrypts message:\n%s\n", receiverName, decryptedMsg);
			
			// hacker "fails" to decrypt using personal private key 
			// (unless the private keys turn out to be the same by chance)
			PrivateKey wrongKey = keypairMap.get(hackerName).getPrivateKey();
			String wrongMsg = wrongKey.decrypt(rPublicKey.getEncryptedMsg());
			Assert.assertNotSame( originalMsg, wrongMsg );
			System.out.printf("%s's fails to decrypt message:\n%s\n\n", hackerName, wrongMsg);
		}
	}
}
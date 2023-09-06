package com.backend.payanam.service.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class KeyGenerator {
	
	private static final String SECRET_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4MFTrpBUnbBnbs+s39jm8BvzJ6G61UzsSyBwMgddns6E/mOarr+YvkUMEN5ewSGnObugnKUtZzstFxhbH8hK6l9B/FkV60OygGuFyDH8GWmQanULCMTx1x+eMO71QBVkdyRemDUoTXEej5AsdwuO8xkmteYJNjvzC9eOBj75ryq/nTm8Nu1Yx57Ct+BzNm0/AkCltHHs+QXEiZdxf+z+y/o8llIjp038jQ1ZZQKHmLMZQB41YORPWvX7kOmTriP7cVblWyn9f1zEKoU/6x7vRinygB8ezhGlZRdJtToCshNg9Mf1N2+JWWj1RRAQe9R0KhE588lwqigxmqUvtVRfUQIDAQAB";
	private static final String SECRET_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDgwVOukFSdsGduz6zf2ObwG/MnobrVTOxLIHAyB12ezoT+Y5quv5i+RQwQ3l7BIac5u6CcpS1nOy0XGFsfyErqX0H8WRXrQ7KAa4XIMfwZaZBqdQsIxPHXH54w7vVAFWR3JF6YNShNcR6PkCx3C47zGSa15gk2O/ML144GPvmvKr+dObw27VjHnsK34HM2bT8CQKW0cez5BcSJl3F/7P7L+jyWUiOnTfyNDVllAoeYsxlAHjVg5E9a9fuQ6ZOuI/txVuVbKf1/XMQqhT/rHu9GKfKAHx7OEaVlF0m1OgKyE2D0x/U3b4lZaPVFEBB71HQqETnzyXCqKDGapS+1VF9RAgMBAAECggEBAM2M4dx3kXt6Fs6ITkGW3HZiCXqIdheXt09qBlIq3Ftd4tHJBHGnNqlk+oj+5kfZYFfzmgtJIYIU48MIemZRxvocTnvceahljH3YWEzkMfyHyBUkoAfQHqGw//sev9tw9SXldEHd89MHjnybzZ6+SmlX6khXbrtUDo8Y/4IE3FNunWApnPDOMuiijICS8L3NFwFyD9lTRN8Zas7nFM81TOV0yNPU9C+m8toD0+nP9HLW4+sPeK5saSSh3aVbHPcCBGA3JRpqB2LTKfmjA72cMCRl+2ZSK3QM6bpEpOLkjLOeCOx2+UGgD/x6z3O+k270KCfeS4MY1ShILBoenyEN+DUCgYEA9Pm+aCBeZrQBAwPmdpSjTUAc05w1i5QRtZRi4ls5eaH3jLxwqsD7vXJ7BI2SAGbsbblv3BulLU5C7ypywR757zkQnMVSMQs3K0imt4pQemCoa8GNooOdvGf/BienGsIaqn1VPn6xbC5ERntJ9pyTV3gWQ89HuEvkNeLWnwe9TksCgYEA6t6iCq/ErHQLRfzNUVsL5nrwhdyLx8JsB/xnFXYXgvuKrvXGzZkqp7EO9sMvsw9e1/jPDuxKe7n9D+Fa0LAdwyOxpE9oJs9+81ZsvTWp88BgVgoHUJIExGSE5UuClVmFd1uy6QzjISAlXLcz5y8fUpJJVMs+EX5tCTNl6qa511MCgYB3oU4+gzAWb2jfFBSDa+qjWt+45tuvIcvUJHZ1m57hqxq8e3l5qWxGA5XXj1rxH1Ht81SQ5F8xVLqTGOOZM4tSK0fVhvawRI3YcU4Y606JtJIKrSaBakypKQ/87XhJNOjYwQPT1QLHlYB5+tMPvz6kxFv7GUoo0cbMGc49MwZbKQKBgQCUXGnTglAj93Gflh9EAtUzk/kXNcd+OcvwB3o+TkhvzuE9v7cC7HvLTcb8DTO2UkFA/8Qb0xsBqOy3+UxagsVqHMP73MUXrv6sS62In7tpY932u4L/XB1yyWRBSz+Dg/cQ/HW5TRC0PhAbzwYUROnuSMXVsETW3BuYMkVwwjymTQKBgQDdswhEWfkfnfn2vaiNKllglWGVONVB5rJx6tlzdlIVSWWkdvBE9Kk+aKT0NtIWcBKhx2ErLEoJZpXM1OYwKNWDRd88siV0Lxhu2hVY4HT2IAgENn0YZiILRpgz7ef6SQJFgJKcS+mWQMh/JRKvIKhVRgSrNqox2Ktl4in5L/hMEg==";
	private KeyPair keyPair;
	
	public KeyGenerator() {
		keyPair = generateKeyPair();
	}
	
	private KeyPair generateKeyPair() {
		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
			byte[] publicKeyBytes = Base64.decodeBase64(SECRET_PUBLIC_KEY);
			byte[] privateKeyBytes = Base64.decodeBase64(SECRET_PRIVATE_KEY);
			
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			PublicKey publicKey = factory.generatePublic(publicKeySpec);
			
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			PrivateKey privateKey = factory.generatePrivate(privateKeySpec);

			return new KeyPair(publicKey, privateKey);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			return null;
		}
	}
	
	public RSAPublicKey getPublicKey() {
		return (RSAPublicKey) this.keyPair.getPublic();
	}
	
	public RSAPrivateKey getPrivateKey() {
		return (RSAPrivateKey) this.keyPair.getPrivate();
	}
	
}

/*
 * SignatureFactory.java
 * PROJECT: JDigiDoc
 * DESCRIPTION: Digi Doc functions for creating
 *	and reading signed documents. 
 * AUTHOR:  Veiko Sinivee, Sunset Software OÜ
 *==================================================
 * Copyright (C) AS Sertifitseerimiskeskus
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * GNU Lesser General Public Licence is available at
 * http://www.gnu.org/copyleft/lesser.html
 *==================================================
 */

package ee.sk.digidoc.factory;
import ee.sk.digidoc.DigiDocException;
import ee.sk.digidoc.TokenKeyInfo;
import ee.sk.digidoc.Signature;
import java.security.cert.X509Certificate;

/**
 * Interface for signature and other cryptographic
 * functions.  
 * @author  Veiko Sinivee
 * @version 1.0
 */
public interface SignatureFactory 
{
	public static final String SIGFAC_TYPE_PKCS11 = "PKCS11";
	public static final String SIGFAC_TYPE_PKCS12 = "PKCS12";
	public static final String SIGFAC_TYPE_JKS = "JKS";
	
    /** 
     * initializes the implementation class 
     */
    public void init()
        throws DigiDocException;
    
    /**
     * Reads all useable token keys
     * @return list of available token/key info
     * @throws DigiDocException
     */
    public TokenKeyInfo[] getTokenKeys()
    	throws DigiDocException;
    
    /**
     * Finds keys of specific type
     * @param bSign true if searching signature keys
     * @return array of key infos
     */
    public TokenKeyInfo[] getTokensOfType(boolean bSign);
    
    /**
     * Method returns an array of strings representing the 
     * list of available token names.
     * @return an array of available token names.
     * @throws DigiDocException if reading the token information fails.
     */
    public String[] getAvailableTokenNames()
        throws DigiDocException;
    
    /**
     * Method returns a digital signature. It finds the RSA private 
     * key object from the active token and
     * then signs the given data with this key and RSA mechanism.
     * @param digest digest of the data to be signed.
     * @param token token index
     * @param pin users pin code
     * @param sig Signature object to provide info about desired signature method
     * @return an array of bytes containing digital signature.
     * @throws DigiDocException if signing the data fails.
     */
    public byte[] sign(byte[] digest, int token, String pin, Signature sig) 
        throws DigiDocException;
    
    /**
     * Method returns a X.509 certificate object readed 
     * from the active token and representing an
     * user public key certificate value.
     * @return X.509 certificate object.
     * @throws DigiDocException if getting X.509 public key certificate 
     * fails or the requested certificate type X.509 is not available in 
     * the default provider package
     */
    public X509Certificate getCertificate(int token, String pin)
        throws DigiDocException;
    
    /**
     * Method returns a X.509 certificate object readed 
     * from the active token and representing an
     * user public key certificate value.
     * @return X.509 certificate object.
     * @throws DigiDocException if getting X.509 public key certificate 
     * fails or the requested certificate type X.509 is not available in 
     * the default provider package
     */
    public X509Certificate getAuthCertificate(int token, String pin)
        throws DigiDocException;
    
    /**
     * Resets the previous session
     * and other selected values
     */
    public void reset() 
        throws DigiDocException;
        
	/**
	 * Method decrypts the data with the RSA private key
	 * corresponding to this certificate (which was used
	 * to encrypt it). Decryption will be done on the card.
	 * This operation closes the possibly opened previous
	 * session with signature token and opens a new one with
	 * authentication tokne if necessary
	 * @param data data to be decrypted.
	 * @param token index of authentication token
	 * @param pin PIN code
	 * @return decrypted data.
	 * @throws DigiDocException for all decryption errors
	 */
	public byte[] decrypt(byte[] data, int token, String pin) 
		throws DigiDocException;
				
	/**
	 * Returns signature factory type identifier
	 * @return factory type identifier
	 */
	public String getType();
	
	/**
     * Method closes the current session.
     * @throws DigiDocException if closing the session fails.
     */
    public void closeSession() 
        throws DigiDocException;
    
}


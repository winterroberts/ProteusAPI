package net.aionstudios.proteus.secure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class KeyStoreLoader {
	
	public static SSLServerSocketFactory loadKeyStoreToSocketFactory(File jks, String storepass, String keypass, String caAlias) throws NoSuchAlgorithmException, CertificateException, FileNotFoundException, IOException, KeyStoreException, UnrecoverableKeyException, KeyManagementException, InvalidAlgorithmParameterException {
		KeyStore keystore = KeyStore.getInstance("JKS");
		keystore.load(new FileInputStream(jks), storepass.toCharArray());
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(keystore, keypass.toCharArray());
		
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		tmf.init(keystore);
		
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(kmf.getKeyManagers(), null, null);
		
		if (!validateCertificatePath(keystore)) {
			System.err.println("WARN: The certificate path is invalid, this may cause SSL handshake to fail!");
		}
		
		return sslContext.getServerSocketFactory();
	}
	
	public static boolean validateCertificatePath(KeyStore keystore, TrustAnchor... additionalAnchors) throws InvalidAlgorithmParameterException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
		Set<TrustAnchor> anchors = getDefaultRootCAs();
		for (TrustAnchor anchor : additionalAnchors) {
			anchors.add(anchor);
		}
		
		PKIXParameters params = new PKIXParameters(anchors);
		CertPathValidator validator = CertPathValidator.getInstance("PKIX");
		params.setRevocationEnabled(false);
		
		List<Certificate> certs = new LinkedList<>();
		keystore.aliases().asIterator().forEachRemaining(alias -> {
			try {
				certs.add(keystore.getCertificate(alias));
			} catch (KeyStoreException e) {
				e.printStackTrace();
			}
		});
		
		try {
			validator.validate(CertificateFactory.getInstance("X509").generateCertPath(certs), params);
		} catch (CertPathValidatorException e) {
			return false;
		}
		return true;
	}
	
	private static Set<TrustAnchor> getDefaultRootCAs()
            throws NoSuchAlgorithmException, KeyStoreException {
        X509TrustManager x509tm = getDefaultX509TrustManager();

        Set<TrustAnchor> rootCAs = new HashSet<TrustAnchor>();
        for (X509Certificate c : x509tm.getAcceptedIssuers()) {
            rootCAs.add(new TrustAnchor(c, null));
        }
        return rootCAs;
    }
	
    private static X509TrustManager getDefaultX509TrustManager()
            throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory tmf = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null);

        for (TrustManager tm : tmf.getTrustManagers()) {
            if (tm instanceof X509TrustManager) {
                return (X509TrustManager) tm;
            }
        }
        throw new IllegalStateException("X509TrustManager is not found");
    }

}

package com.learnhub.auth.common.utils.secret;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

public class CertificateGenerator {

    public static void main(String[] args) throws Exception {
        // 生成密钥对
        KeyPair keyPair = generateKeyPair();

        // 创建自签名证书
        X509Certificate cert = generateSelfSignedCertificate(keyPair, "CN=localhost");

        // 保存证书和密钥对到文件
        saveCertificateAndKeyPair(cert, keyPair, "learnhub.jks", "learnhub123");
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static X509Certificate generateSelfSignedCertificate(KeyPair keyPair, String subjectDN)
            throws Exception {
        // 创建证书
        X509Certificate cert = null;
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            cert = (X509Certificate) certFactory.generateCertificate(
                    new ByteArrayInputStream(generateCertificateBytes(keyPair, subjectDN)));
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return cert;
    }

    public static byte[] generateCertificateBytes(KeyPair keyPair, String subjectDN) throws Exception {
        // 创建证书
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                new X500Name("CN=localhost"),
                BigInteger.valueOf(System.currentTimeMillis()),
                new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30),
                new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365 * 10)), // 10 years
                new X500Name(subjectDN),
                keyPair.getPublic());

        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate());
        X509CertificateHolder certHolder = certBuilder.build(contentSigner);

        return certHolder.toASN1Structure().getEncoded();
    }

    public static void saveCertificateAndKeyPair(X509Certificate cert, KeyPair keyPair, String filename, String password)
            throws Exception {
        // 创建一个PKCS12密钥库
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, null);
        keyStore.setKeyEntry("learnhub", keyPair.getPrivate(), password.toCharArray(), new Certificate[]{cert});

        // 将密钥库保存到文件
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            keyStore.store(fos, password.toCharArray());
        }
    }
}

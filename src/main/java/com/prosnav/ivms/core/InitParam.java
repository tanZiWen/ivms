package com.prosnav.ivms.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="session")
public class InitParam {
	private String privateKey;
	private String privateKeyFilePath;
	private String loadUserUrl;
	private String cid;
	private String expire;
	private String alg;
	private String authorizeTokenName;
	private String frontendPortalUrl;
	private String mainClientId;
	private String packageName;
	private String loginUrl;
	private String clientId;
	private String clientSecret;
	
	public String getPrivateKeyFilePath() {
		return privateKeyFilePath;
	}

	public void setPrivateKeyFilePath(String privateKeyFilePath) {
		this.privateKeyFilePath = privateKeyFilePath;
	}

	public String getPrivateKey() {
		return initPrivateKey(privateKeyFilePath);
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getLoadUserUrl() {
		return loadUserUrl;
	}

	public void setLoadUserUrl(String loadUserUrl) {
		this.loadUserUrl = loadUserUrl;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getAlg() {
		return alg;
	}

	public void setAlg(String alg) {
		this.alg = alg;
	}

	public String getAuthorizeTokenName() {
		return authorizeTokenName;
	}

	public void setAuthorizeTokenName(String authorizeTokenName) {
		this.authorizeTokenName = authorizeTokenName;
	}

	public String getFrontendPortalUrl() {
		return frontendPortalUrl;
	}

	public void setFrontendPortalUrl(String frontendPortalUrl) {
		this.frontendPortalUrl = frontendPortalUrl;
	}

	public String getMainClientId() {
		return mainClientId;
	}

	public void setMainClientId(String mainClientId) {
		this.mainClientId = mainClientId;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	private String initPrivateKey(String privateKeyFilePath) {
		InputStream in = null;
		try {
			in = new FileInputStream(privateKeyFilePath);
			int len = in.available();
			byte[] buf = new byte[len];
			in.read(buf);
			return new String(buf);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}

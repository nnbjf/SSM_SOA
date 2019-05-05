package com.catdog.jd.common;

import com.catdog.jd.utils.AESUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

//废弃
public class DataSourcePropertiesFactoryBean implements FactoryBean{

	private Properties properties;

	private static final String JDBC_USER = "user";

	private static final String JDBC_PASSWORD = "password";

	@Override
	public Object getObject() throws Exception {
		return getProperties();
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public Properties getProperties() {
		return this.properties;
	}

	public DataSourcePropertiesFactoryBean setProperties(Properties properties) {
		this.properties = properties;
		String user = properties.getProperty(JDBC_USER);
		String password = properties.getProperty(JDBC_PASSWORD);
		if(user!= null && !user.isEmpty())
			decodeStr(user,JDBC_USER);
		if(password!= null && !password.isEmpty())
			decodeStr(password,JDBC_PASSWORD);
		return this;
	}

	private void decodeStr(String s,String pKey) {
		try {
			String decode = AESUtils.aesDecode(s);
			properties.setProperty(pKey,decode);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.catdog.jd.common;

import com.catdog.jd.utils.AESUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 数据库密码解密配置
 */
public class PropertyPlaceholderConfigurerConfig extends PropertyPlaceholderConfigurer {

	private static final String JDBC_USER = "jdbc.user";
	private static final String JDBC_PASSWORD = "jdbc.password";

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		String userValue = props.getProperty(JDBC_USER);
		String pwdValue = props.getProperty(JDBC_PASSWORD);
		if (userValue != null && !userValue.trim().isEmpty()) {
			try {
				userValue = AESUtils.aesDecode(props.getProperty(JDBC_USER));
				props.setProperty(JDBC_USER, userValue);
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
		if (pwdValue != null && !pwdValue.trim().isEmpty()) {
			try {
				pwdValue = AESUtils.aesDecode(props.getProperty(JDBC_PASSWORD));
				props.setProperty(JDBC_PASSWORD, pwdValue);
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
		super.processProperties(beanFactoryToProcess, props);
	}
}

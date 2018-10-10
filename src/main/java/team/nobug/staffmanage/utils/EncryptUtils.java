package team.nobug.staffmanage.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class EncryptUtils {
	
	public static String encryptBySHA1(String pwd) {
		String salt = "as*df/a*/2*4^&*^*&^8afasfa";
		for (int i = 0; i < 10; i++) {
			pwd = DigestUtils.sha1Hex(pwd+salt).toString();
		}
		return pwd;
	}
	
	public static void main(String[] args) {
		System.out.println(encryptBySHA1("123456"));
	}
	
}

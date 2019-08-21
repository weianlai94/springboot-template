package com.example.demo.common.utils;


import java.util.Random;

public class CommonUtil {
	public static String createRandom(int length){
		Random random = new Random();
		String code = "";
		// 随机产生6位数字的字符串
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			code += rand;
		}
		return code;
	}
}

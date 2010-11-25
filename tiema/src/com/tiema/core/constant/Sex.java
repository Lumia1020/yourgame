package com.tiema.core.constant;

/**
 * @ClassName: Sex
 * @Description: 性别枚举
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 5:27:25 PM
 * 
 */
public enum Sex {

	UNKNOWN, MALE, FEMALE;

	public String getLabel() {
		switch (this) {
			case UNKNOWN:
				return "未知";
			case MALE:
				return "男";
			case FEMALE:
				return "女";
		}
		return super.toString();
	}

	public int getIndex() {
		return this.ordinal();
	}
}

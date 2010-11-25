package com.tiema.core.constant;

/**
 * @ClassName: State
 * @Description: 描述一条记录的状态
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 12:51:22 PM
 * 
 */
public enum State {

	UNKNOWN, VALID, INVALID;
	
	public int getIndex(){
		return this.ordinal();
	}

	public String getLabel() {

		switch (this) {
			case UNKNOWN:
				return "未知";
			case VALID:
				return "有效";
			case INVALID:
				return "无效";
		}

		return super.toString();
	}
}

package com.tiema.payment.constant;

/**
 * @ClassName: PaymentProporty
 * @Description: 支付方式类型的枚举
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date Nov 24, 2010 5:27:21 PM
 * 
 */
public enum PaymentType {

	CASH, PREPAY_CARD, DEBIT_CARD, CREDIT_CARD, OTHER;

	public String getLabel() {
		switch (this) {
			case CASH:
				return "现金";
			case PREPAY_CARD:
				return "储值卡";
			case DEBIT_CARD:
				return "借记卡";
			case CREDIT_CARD:
				return "信用卡";
			case OTHER:
				return "其他";
		}
		return super.toString();
	}

	public int getIndex() {
		return this.ordinal();
	}

}

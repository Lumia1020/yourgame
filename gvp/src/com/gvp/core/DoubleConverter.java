package com.gvp.core;

import java.util.Map;

import ognl.DefaultTypeConverter;

public class DoubleConverter extends DefaultTypeConverter {

	@Override
	public Object convertValue(Map content, Object value, Class toType) {
		if(Double.class == toType){
			String[] str = (String[]) value;
			String v = str[0];
			if(v == null || "".equals(v)){
				return null;
			}
			return Double.valueOf(str[0]);
		}
		if(String.class == toType){
			if(value == null){
				return value;
			}
			Double d = (Double) value;
			return String.valueOf(d);
		}
		return super.convertValue(content, value, toType);
	}

}
 
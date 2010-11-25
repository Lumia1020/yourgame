/*
 * @(#)MyUtils.java 2008-12-14
 *
 * Copyright YOURGAME. All rights reserved.
 */

package com.tiema.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.tiema.user.entity.User;

/**
 * Create on 2008-12-14 下午10:59:09
 * 
 * 工具类
 * 
 * @author 廖瀚卿
 * @version
 */
public class MyUtils {

	/**
	 * 将后面对象的不为空的值拷贝到目标对象相对应的属性上面去
	 * 
	 * @return
	 */
	public static <T> T copyProperties(T target, Object o) {
		try {
			for (Field f : getPrivateNonStaticFields(o)) {
				Object value = PropertyUtils.getProperty(o, f.getName());
				if (value != null) {
					PropertyUtils.setProperty(target, f.getName(), value);
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return target;
	}

	/**
	 * @Title: getPrivateNonStaticFields
	 * @Description: 获得指定对象中私有的不是静态的属性集合
	 * @param 
	 * @return List<Field> 
	 * @throws
	 */
	public static List<Field> getPrivateNonStaticFields(Object o) {
		Field serialVersionUID = null;
		try {
			serialVersionUID = o.getClass().getDeclaredField("serialVersionUID");
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			e1.printStackTrace();
		}
		Field[] declaredFields = o.getClass().getDeclaredFields(); //所有属性包括公共的和私有的静态属性
		Field[] fields = o.getClass().getFields(); //公共的静态属性

		List<Field> excludeFields = new ArrayList<Field>();
		List<Field> includeFields = new ArrayList<Field>();
		if (serialVersionUID != null) {
			excludeFields.add(serialVersionUID);
		}
		for (Field f : fields) {
			excludeFields.add(f);
		}
		for (Field f : declaredFields) {
			if (!excludeFields.contains(f)) {
				includeFields.add(f);
			}
		}
		return includeFields;
	}

	
	/**
	 * 取得给定时间,给定格式的日期时间字符串
	 * 
	 * @param date
	 *            日期,给定一个时间
	 * @param format
	 *            格式,如String format = "yyyy-MM-dd HH:mm:ss";
	 * @return String 取得给定时间,给定格式的日期时间字符串
	 */
	public static String dateToString(java.util.Date date, String format) {
		if(date == null)return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static void main(String[] args) {
		User u = new User();
		MyUtils.copyProperties(u, new User());
	}
	//	

}

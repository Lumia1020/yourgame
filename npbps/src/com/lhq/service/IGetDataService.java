package com.lhq.service;

import java.lang.reflect.InvocationTargetException;

import com.lhq.core.Page;
import com.lhq.po.GetData;

public interface IGetDataService {

	Page getGetDataList(Page page);

	boolean addGetData(GetData getData);

	boolean deleteGetData(GetData getData);

	boolean updateGetData(GetData getData) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

	GetData findById(String id);

	boolean updateFlags(Page page);

	boolean importGetData(String string);

}

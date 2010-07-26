package com.lhq.service;

import java.lang.reflect.InvocationTargetException;

import com.lhq.core.Page;
import com.lhq.po.Dispense;
import com.lhq.po.GetData;

public interface IDispenseService {

	Page getDispenseList(Page page);

	boolean addDispense(Dispense dispense);

	boolean deleteDispense(Dispense dispense);

	boolean updateDispense(Dispense dispense) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException;

	Dispense findById(String dispenseId);

	boolean updateFlags(Page page);

}

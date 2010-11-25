package com.tiema.golf.price.service;

import com.tiema.core.service.GenericService;
import com.tiema.golf.price.entity.ClubPrice;

/**
 * @ClassName: AgencyPriceService
 * @Description: 俱乐部报价管理业务接口
 * @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
 * @date 2010-11-20 上午01:01:19
 * 
 */
public interface ClubPriceService extends GenericService<ClubPrice, Long> {

	/**
	 * @Title: validateDateRange
	 * @Description: 校验俱乐部纪录日期范围的合法性
	 * @return boolean 是否有效
	 */
	public boolean validateDateRange(ClubPrice cp,boolean isUpdate);


}

package com.tiema.agency.price.service;

import com.tiema.agency.price.entity.AgencyPrice;
import com.tiema.core.service.GenericService;

/**
* @ClassName: AgencyPriceService
* @Description: 中介公司价格管理业务接口
* @author 廖瀚卿 <a>http://yourgame.javaeye.com</a>
* @date 2010-11-23 下午11:24:12
*
*/
public interface AgencyPriceService extends GenericService<AgencyPrice, Long> {

	/**
	 * @Title: validateDateRange
	 * @Description: 校验俱乐部纪录日期范围的合法性
	 * @return boolean 是否有效
	 */
	public boolean validateDateRange(AgencyPrice cp,boolean isUpdate);


}

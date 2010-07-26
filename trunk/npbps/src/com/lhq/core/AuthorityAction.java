/*
 * @(#)AuthorityAction.java 2009-9-17
 *
 * Copyright YOURGAME. All rights reserved.
 */

package com.lhq.core;

import java.util.HashMap;
import java.util.Map;

/**    
 * Create on 2009-9-17 下午09:30:54
 *
 *
 *
 * @author 廖瀚卿
 * @version  
 */
@SuppressWarnings("serial")
public class AuthorityAction extends BaseAction {

	private Map<String, Object> infos = new HashMap<String, Object>();
	
	public String invalidSession(){
		this.infos.put("tip", "对不起,当前会话无效!请重新登录!");
		return SUCCESS;
	}

	public Map<String, Object> getInfos() {
		return infos;
	}

	public void setInfos(Map<String, Object> infos) {
		this.infos = infos;
	} 
}
 
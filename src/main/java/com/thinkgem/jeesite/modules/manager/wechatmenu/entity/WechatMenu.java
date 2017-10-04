/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.wechatmenu.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微信菜单Entity
 * @author yt
 * @version 2017-10-04
 */
public class WechatMenu extends DataEntity<WechatMenu> {
	
	private static final long serialVersionUID = 1L;
	private String menu;		// menu
	
	public WechatMenu() {
		super();
	}

	public WechatMenu(String id){
		super(id);
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	
}
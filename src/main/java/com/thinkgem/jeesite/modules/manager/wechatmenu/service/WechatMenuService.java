/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.wechatmenu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.manager.wechatmenu.entity.WechatMenu;
import com.thinkgem.jeesite.modules.manager.wechatmenu.dao.WechatMenuDao;

/**
 * 微信菜单Service
 * @author yt
 * @version 2017-10-04
 */
@Service
@Transactional(readOnly = true)
public class WechatMenuService extends CrudService<WechatMenuDao, WechatMenu> {

	public WechatMenu get(String id) {
		return super.get(id);
	}
	
	public List<WechatMenu> findList(WechatMenu wechatMenu) {
		return super.findList(wechatMenu);
	}
	
	public Page<WechatMenu> findPage(Page<WechatMenu> page, WechatMenu wechatMenu) {
		return super.findPage(page, wechatMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(WechatMenu wechatMenu) {
		super.save(wechatMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(WechatMenu wechatMenu) {
		super.delete(wechatMenu);
	}
	
}
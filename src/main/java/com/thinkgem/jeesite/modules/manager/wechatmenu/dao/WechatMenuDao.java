/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.wechatmenu.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.wechatmenu.entity.WechatMenu;

/**
 * 微信菜单DAO接口
 * @author yt
 * @version 2017-10-04
 */
@MyBatisDao
public interface WechatMenuDao extends CrudDao<WechatMenu> {
	
}
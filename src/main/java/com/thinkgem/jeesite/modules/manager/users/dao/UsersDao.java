/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.users.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.users.entity.Users;

/**
 * 客户实体类DAO接口
 * @author yt
 * @version 2017-08-20
 */
@MyBatisDao
public interface UsersDao extends CrudDao<Users> {
	
}
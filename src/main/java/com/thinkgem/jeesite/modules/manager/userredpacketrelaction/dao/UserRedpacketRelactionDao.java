/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.userredpacketrelaction.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户红包关系实体类DAO接口
 *
 * @author yt
 * @version 2017-08-20
 */
@MyBatisDao
public interface UserRedpacketRelactionDao extends CrudDao<UserRedpacketRelaction> {


    public List<UserRedpacketRelaction> findByUserId(@Param("userId") String userId);


    public List<UserRedpacketRelaction> findEnableRedpacket(@Param("openId") String openId);
}
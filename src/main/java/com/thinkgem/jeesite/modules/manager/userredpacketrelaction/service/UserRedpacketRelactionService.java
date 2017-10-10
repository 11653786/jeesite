/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.userredpacketrelaction.service;

import com.thinkgem.jeesite.DictType;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.manager.redpacket.entity.Redpacket;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.dao.UserRedpacketRelactionDao;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import com.thinkgem.jeesite.modules.manager.users.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户红包关系实体类Service
 *
 * @author yt
 * @version 2017-08-20
 */
@Service
@Transactional(readOnly = true)
public class UserRedpacketRelactionService extends CrudService<UserRedpacketRelactionDao, UserRedpacketRelaction> {

    @Autowired
    private UserRedpacketRelactionDao userRedpacketRelactionDao;


    public UserRedpacketRelaction get(String id) {
        return super.get(id);
    }

    public List<UserRedpacketRelaction> findList(UserRedpacketRelaction userRedpacketRelaction) {
        return super.findList(userRedpacketRelaction);
    }

    public Page<UserRedpacketRelaction> findPage(Page<UserRedpacketRelaction> page, UserRedpacketRelaction userRedpacketRelaction) {
        return super.findPage(page, userRedpacketRelaction);
    }

    @Transactional(readOnly = false)
    public void save(UserRedpacketRelaction userRedpacketRelaction) {
        super.save(userRedpacketRelaction);
    }

    @Transactional(readOnly = false)
    public void delete(UserRedpacketRelaction userRedpacketRelaction) {
        super.delete(userRedpacketRelaction);
    }


    @Transactional(readOnly = false)
    public void save(Users users, Redpacket redpacket) {
        UserRedpacketRelaction userRedpacketRelaction = new UserRedpacketRelaction();
        userRedpacketRelaction.setCreateTime(new Date());
        userRedpacketRelaction.setRedpacketId(redpacket.getId());
        userRedpacketRelaction.setRedpacketName(redpacket.getName());
        userRedpacketRelaction.setRedpacketPrice(redpacket.getRedpacketPrice());
        userRedpacketRelaction.setRedpacketType(redpacket.getRedpacketType());
        userRedpacketRelaction.setUserid(users.getId());
        userRedpacketRelaction.setUserName(users.getUserName());
        userRedpacketRelaction.setInUse(Integer.valueOf(DictType.dict_redpacket_in_use.getValue()));
        userRedpacketRelaction.setOutTime(getLimitDate(redpacket.getLimitDay()));
        userRedpacketRelaction.setRemark(redpacket.getRemark());
        super.save(userRedpacketRelaction);
    }

    public List<UserRedpacketRelaction> findByUserId(String userId) {
        return userRedpacketRelactionDao.findByUserId(userId);
    }


    public List<UserRedpacketRelaction> findEnableRedpacket(String openId){
        return userRedpacketRelactionDao.findEnableRedpacket(openId);
    }

    private Date getLimitDate(Integer limitDay) {
        Calendar nowDate = Calendar.getInstance();
        nowDate.add(Calendar.DATE, limitDay);
        return nowDate.getTime();
    }

}
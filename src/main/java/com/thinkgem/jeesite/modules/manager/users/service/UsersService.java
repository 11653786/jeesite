/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.users.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.manager.redpacket.dao.RedpacketDao;
import com.thinkgem.jeesite.modules.manager.redpacket.entity.Redpacket;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.entity.UserRedpacketRelaction;
import com.thinkgem.jeesite.modules.manager.userredpacketrelaction.service.UserRedpacketRelactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.manager.users.entity.Users;
import com.thinkgem.jeesite.modules.manager.users.dao.UsersDao;

/**
 * 客户实体类Service
 *
 * @author yt
 * @version 2017-08-20
 */
@Service
@Transactional(readOnly = true)
public class UsersService extends CrudService<UsersDao, Users> {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private RedpacketDao redpacketDao;
    @Autowired
    private UserRedpacketRelactionService userRedpacketRelactionService;


    public Users get(String id) {
        return super.get(id);
    }

    public List<Users> findList(Users users) {
        return super.findList(users);
    }

    public Page<Users> findPage(Page<Users> page, Users users) {
        return super.findPage(page, users);
    }

    @Transactional(readOnly = false)
    public void save(Users users) {
        super.save(users);
    }

    @Transactional(readOnly = false)
    public void delete(Users users) {
        super.delete(users);
    }

    /**
     * 威信关注注册用户
     *
     * @param openId
     * @return
     */
    @Transactional(readOnly = false)
    public Users weChatRegister(String openId) {
        Users users = usersDao.findByOpenId(openId);
        if (users == null) {
            users = new Users();
            users.setOpenid(openId);
            users.setUserStatus(1 + "");
            super.save(users);
            //查询当前用户是否有红包没有就送一个
            Redpacket redpacket = redpacketDao.findRedByName("关注红包");
            List<UserRedpacketRelaction> userRedpacketRelaction = userRedpacketRelactionService.findByUserId(users.getId());
            //第一次关注就送红包
            if (userRedpacketRelaction == null || userRedpacketRelaction.isEmpty()) {
                userRedpacketRelactionService.save(users, redpacket);
            }
        } else {
            users.setUserStatus(1 + "");
            users.setWechatCancel(null);
            usersDao.update(users);
        }
        return users;
    }

    /**
     * 用户取关微信的操作
     *
     * @param openId
     * @return
     */
    @Transactional(readOnly = false)
    public Users weChatCancel(String openId) {
        Users users = usersDao.findByOpenId(openId);
        if (users != null) {
            users.setUserStatus(2 + "");
            users.setWechatCancel(new Date());
            usersDao.update(users);
        }
        return users;
    }

    public Users findByOpenId(String openId){
        return usersDao.findByOpenId(openId);
    }

}
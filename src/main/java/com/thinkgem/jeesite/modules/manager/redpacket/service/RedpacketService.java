/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.redpacket.service;

import java.util.List;

import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.manager.users.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.manager.redpacket.entity.Redpacket;
import com.thinkgem.jeesite.modules.manager.redpacket.dao.RedpacketDao;

/**
 * 优惠卷实体类Service
 *
 * @author yt
 * @version 2017-08-20
 */
@Service
@Transactional(readOnly = true)
public class RedpacketService extends CrudService<RedpacketDao, Redpacket> {

    public Redpacket get(String id) {
        return super.get(id);
    }

    public List<Redpacket> findList(Redpacket redpacket) {
        return super.findList(redpacket);
    }

    public Page<Redpacket> findPage(Page<Redpacket> page, Redpacket redpacket) {
        return super.findPage(page, redpacket);
    }

    @Transactional(readOnly = false)
    public void save(Redpacket redpacket) {
        super.save(redpacket);
    }

    @Transactional(readOnly = false)
    public void delete(Redpacket redpacket) {
        super.delete(redpacket);
    }


}
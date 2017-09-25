/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinet.service;

import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.enums.ResCodeMsgType;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.CabinetDao;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.DrawerDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Drawer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 快餐柜实体类Service
 *
 * @author yt
 * @version 2017-08-19
 */
@Service
@Transactional(readOnly = true)
public class CabinetService extends CrudService<CabinetDao, Cabinet> {

    @Autowired
    private DrawerDao drawerDao;
    @Autowired
    private CabinetDao cabinetDao;

    public Cabinet get(String id) {
        Cabinet cabinet = super.get(id);
        cabinet.setDrawerList(drawerDao.findList(new Drawer(cabinet)));
        return cabinet;
    }

    public List<Cabinet> findList(Cabinet cabinet) {
        return super.findList(cabinet);
    }

    public Page<Cabinet> findPage(Page<Cabinet> page, Cabinet cabinet) {
        return super.findPage(page, cabinet);
    }

    @Transactional(readOnly = false)
    public void save(Cabinet cabinet) {
        super.save(cabinet);
        for (Drawer drawer : cabinet.getDrawerList()) {
            if (drawer.getId() == null) {
                continue;
            }
            if (Drawer.DEL_FLAG_NORMAL.equals(drawer.getDelFlag())) {
                if (StringUtils.isBlank(drawer.getId())) {
//					drawer.setCabinet(cabinet);
                    drawer.setCabinetId(cabinet.getId());
                    drawer.preInsert();
                    drawerDao.insert(drawer);
                } else {
                    drawer.preUpdate();
                    drawerDao.update(drawer);
                }
            } else {
                drawerDao.delete(drawer);
            }
        }
    }

    @Transactional(readOnly = false)
    public void delete(Cabinet cabinet) {
        super.delete(cabinet);
        drawerDao.delete(new Drawer(cabinet));
    }

    @Transactional(readOnly = false)
    public PlatformRes<String> updatePassword(Cabinet cabinet, String sysPassword, String foodPassword) {
        Integer type = null;
        if (cabinet == null)
            return PlatformRes.error(ResCodeMsgType.PARAMS_NOT_EMPTY);

        if (StringUtils.isBlank(sysPassword) && StringUtils.isBlank(foodPassword))
            return PlatformRes.error(ResCodeMsgType.PASSWORD_NOT_ONE);
        else {
            if (StringUtils.isNotBlank(sysPassword) && StringUtils.isNotBlank(foodPassword))
                type = 2;
            else if (StringUtils.isNotBlank(sysPassword))
                type = 0;
            else if (StringUtils.isNotBlank(foodPassword))
                type = 1;
        }


        if (cabinet == null)
            return PlatformRes.error(ResCodeMsgType.CABINET_NOT_EXISTS);

        Integer isUpdate = cabinetDao.updatePassword(type, cabinet.getId(), sysPassword, foodPassword);
        if (isUpdate == 1)
            return PlatformRes.success(null);
        else
            return PlatformRes.error("数据库操作失败");

    }


}
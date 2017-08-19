package com.thinkgem.jeesite.modules.manager.drawer.service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.CabinetDao;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.DrawerDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Drawer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by erfeng on 17/8/19.
 */
@Service
@Transactional(readOnly = true)
public class DrawerService extends CrudService<DrawerDao, Drawer> {

    public Drawer get(String id) {
        Drawer drawer = super.get(id);
        return drawer;
    }

    public List<Drawer> findList(Drawer drawer) {
        return super.findList(drawer);
    }

    public Page<Drawer> findPage(Page<Drawer> page, Drawer drawer) {
        return super.findPage(page, drawer);
    }

    @Transactional(readOnly = false)
    public void save(Drawer drawer) {
        super.save(drawer);
    }

    @Transactional(readOnly = false)
    public void delete(Drawer drawer) {
        super.delete(drawer);
    }
}

package com.thinkgem.jeesite.modules.manager.drawer.service;

import com.thinkgem.jeesite.api.entity.res.PlatformRes;
import com.thinkgem.jeesite.api.enums.ResCodeMsgType;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.CabinetDao;
import com.thinkgem.jeesite.modules.manager.cabinet.dao.DrawerDao;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Drawer;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.dao.CabinetProductRelactionDao;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity.CabinetProductRelaction;
import com.thinkgem.jeesite.modules.manager.product.dao.ProductDao;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by erfeng on 17/8/19.
 */
@Service
@Transactional(readOnly = true)
public class DrawerService extends CrudService<DrawerDao, Drawer> {

    @Autowired
    private DrawerDao drawerDao;
    @Autowired
    private CabinetDao cabinetDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CabinetProductRelactionDao cabinetProductRelactionDao;


    public void unlockStatus(String drawerNo) {
        drawerDao.lockOrUnlockStatus(drawerNo, 1);
    }

    public Drawer get(String id) {
        Drawer drawer = super.get(id);
        return drawer;
    }

    /**
     * 根据柜子和抽屉编号获取抽屉
     *
     * @param cabinetNo
     * @param drawerNo
     * @return
     */
    public Drawer getDrawerByDrawerNo(String cabinetNo, String drawerNo) {
        Drawer drawer = new Drawer();
        drawer.setDrawerNo(drawerNo);
        drawer.setCabinetNo(cabinetNo);
        //抽屉可用
        drawer.setDrawerStatus("1");
        List<Drawer> drawers = drawerDao.findList(drawer);
        if (drawers == null || drawers.isEmpty())
            return null;
        else
            return drawers.get(0);

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
        cabinetProductRelactionDao.deleteByDrawerNo(drawer.getDrawerNo());
        super.delete(drawer);
    }


    @Transactional(readOnly = false)
    public PlatformRes<String> putFood(String productId, String foodPassword, String cabinetNo, String drawerNo) {
        if (StringUtils.isBlank(productId) || StringUtils.isBlank(cabinetNo) || StringUtils.isBlank(drawerNo) || StringUtils.isBlank(foodPassword))
            return PlatformRes.error(ResCodeMsgType.PARAMS_NOT_EMPTY);
        //判断放餐人员密码是否输入正确！
        Cabinet cabinet = cabinetDao.getCabinetByFoodPass(cabinetNo, foodPassword);
        if (cabinet == null)
            return PlatformRes.error(ResCodeMsgType.PUT_FOOD_PASS_ERROR);
        Product product = productDao.get(productId);
        if (product == null || product.getProductStatus().equals("0"))
            return PlatformRes.error(ResCodeMsgType.PRODUCT_NOT_USE);

        Drawer drawer = drawerDao.findCabinetAndDrawerNo(cabinetNo, drawerNo);
        if (drawer == null)
            return PlatformRes.error(ResCodeMsgType.CABINET_DRAWER_EXISTS);
        //柜子如果放餐了
        if (drawer.getFoodStatus().equals("1"))
            return PlatformRes.error(ResCodeMsgType.DRAWER_HAS_FOOD);


        CabinetProductRelaction cabinetProductRelaction = cabinetProductRelactionDao.findBydrawerNoAndProductId(cabinetNo,drawer.getDrawerNo(), productId);
        if (cabinetProductRelaction == null)
            return PlatformRes.error(ResCodeMsgType.DRAWER_NOT_PUT_PRODUCT);

        drawerDao.putFood(productId, product.getProductName(), cabinetNo, drawerNo);
        return PlatformRes.success("放餐成功！");
    }

}

/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.service;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.DictType;
import com.thinkgem.jeesite.modules.manager.cabinet.entity.Cabinet;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity.CabinetProductRelaction;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.dao.CabinetProductRelactionDao;

/**
 * 柜子商品关系配置Service
 *
 * @author yt
 * @version 2017-08-19
 */
@Service
@Transactional(readOnly = true)
public class CabinetProductRelactionService extends CrudService<CabinetProductRelactionDao, CabinetProductRelaction> {


    @Autowired
    private DictService dictService;
    @Autowired
    private CabinetProductRelactionDao cabinetProductRelactionDao;

    public CabinetProductRelaction get(String id) {
        return super.get(id);
    }

    public List<CabinetProductRelaction> findList(CabinetProductRelaction cabinetProductRelaction) {
        return super.findList(cabinetProductRelaction);
    }

    public Page<CabinetProductRelaction> findPage(Page<CabinetProductRelaction> page, CabinetProductRelaction cabinetProductRelaction) {
        return super.findPage(page, cabinetProductRelaction);
    }

    @Transactional(readOnly = false)
    public void save(Product product, Cabinet cabinet) {

        CabinetProductRelaction cabinetProductRelaction = new CabinetProductRelaction();
        cabinetProductRelaction.setCabinetId(cabinet.getId());
        cabinetProductRelaction.setCabinetName(cabinet.getCabinetName());
        cabinetProductRelaction.setCabinetNo(cabinet.getCabinetNos());
        Dict dict = dictService.findByTypeAndValue(DictType.cabinet_product_status_up.getType(), DictType.cabinet_product_status_up.getValue());
        cabinetProductRelaction.setCabinetProductStatus(dict.getValue());
        cabinetProductRelaction.setCreateTime(new Date());
        cabinetProductRelaction.setProductId(product.getId());
        cabinetProductRelaction.setProductName(product.getProductName());
        CabinetProductRelaction existsEntity = cabinetProductRelactionDao.findBycabinetIdAndProductId(cabinet.getId(), product.getId());
        if (existsEntity == null)
            super.save(cabinetProductRelaction);

    }

    @Transactional(readOnly = false)
    public void save(CabinetProductRelaction cabinetProductRelaction) {
        super.save(cabinetProductRelaction);
    }

    @Transactional(readOnly = false)
    public void delete(CabinetProductRelaction cabinetProductRelaction) {
        super.delete(cabinetProductRelaction);
    }

}
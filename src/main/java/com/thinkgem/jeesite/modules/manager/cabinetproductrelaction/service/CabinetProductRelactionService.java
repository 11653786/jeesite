/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.entity.CabinetProductRelaction;
import com.thinkgem.jeesite.modules.manager.cabinetproductrelaction.dao.CabinetProductRelactionDao;

/**
 * 柜子商品关系配置Service
 * @author yt
 * @version 2017-08-19
 */
@Service
@Transactional(readOnly = true)
public class CabinetProductRelactionService extends CrudService<CabinetProductRelactionDao, CabinetProductRelaction> {

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
	public void save(CabinetProductRelaction cabinetProductRelaction) {
		super.save(cabinetProductRelaction);
	}
	
	@Transactional(readOnly = false)
	public void delete(CabinetProductRelaction cabinetProductRelaction) {
		super.delete(cabinetProductRelaction);
	}
	
}
/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.manager.product.service;

import java.util.List;

import com.thinkgem.jeesite.modules.manager.product.entity.ProductRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.manager.product.entity.Product;
import com.thinkgem.jeesite.modules.manager.product.dao.ProductDao;

/**
 * 商品实体类Service
 * @author yt
 * @version 2017-08-19
 */
@Service
@Transactional(readOnly = true)
public class ProductService extends CrudService<ProductDao, Product> {

	@Autowired
	private ProductDao productDao;

	public Product get(String id) {
		return super.get(id);
	}
	
	public List<Product> findList(Product product) {
		return super.findList(product);
	}


	public List<ProductRes> findListByInterface(Product product) {
		return productDao.findListByInterface(product);
	}
	
	public Page<Product> findPage(Page<Product> page, Product product) {
		return super.findPage(page, product);
	}
	
	@Transactional(readOnly = false)
	public void save(Product product) {
		super.save(product);
	}
	
	@Transactional(readOnly = false)
	public void delete(Product product) {
		super.delete(product);
	}
	
}
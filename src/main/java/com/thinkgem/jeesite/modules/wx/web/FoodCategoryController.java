/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.wx.entity.FoodCategory;
import com.thinkgem.jeesite.modules.wx.service.FoodCategoryService;

/**
 * 菜品分类Controller
 * @author tgp
 * @version 2018-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/foodCategory")
public class FoodCategoryController extends BaseController {

	@Autowired
	private FoodCategoryService foodCategoryService;
	
	@ModelAttribute
	public FoodCategory get(@RequestParam(required=false) String id) {
		FoodCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = foodCategoryService.get(id);
		}
		if (entity == null){
			entity = new FoodCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("wx:foodCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(FoodCategory foodCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FoodCategory> page = foodCategoryService.findPage(new Page<FoodCategory>(request, response), foodCategory); 
		model.addAttribute("page", page);
		return "modules/wx/foodCategoryList";
	}

	@RequiresPermissions("wx:foodCategory:view")
	@RequestMapping(value = "form")
	public String form(FoodCategory foodCategory, Model model) {
		model.addAttribute("foodCategory", foodCategory);
		return "modules/wx/foodCategoryForm";
	}

	@RequiresPermissions("wx:foodCategory:edit")
	@RequestMapping(value = "save")
	public String save(FoodCategory foodCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, foodCategory)){
			return form(foodCategory, model);
		}
		foodCategoryService.save(foodCategory);
		addMessage(redirectAttributes, "保存菜品分类成功");
		return "redirect:"+Global.getAdminPath()+"/wx/foodCategory/?repage";
	}
	
	@RequiresPermissions("wx:foodCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(FoodCategory foodCategory, RedirectAttributes redirectAttributes) {
		foodCategoryService.delete(foodCategory);
		addMessage(redirectAttributes, "删除菜品分类成功");
		return "redirect:"+Global.getAdminPath()+"/wx/foodCategory/?repage";
	}

}
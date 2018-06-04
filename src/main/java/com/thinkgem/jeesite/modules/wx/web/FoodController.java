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
import com.thinkgem.jeesite.modules.wx.entity.Food;
import com.thinkgem.jeesite.modules.wx.service.FoodService;

/**
 * 菜品Controller
 * @author tgp
 * @version 2018-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/food")
public class FoodController extends BaseController {

	@Autowired
	private FoodService foodService;
	
	@ModelAttribute
	public Food get(@RequestParam(required=false) String id) {
		Food entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = foodService.get(id);
		}
		if (entity == null){
			entity = new Food();
		}
		return entity;
	}
	
	@RequiresPermissions("wx:food:view")
	@RequestMapping(value = {"list", ""})
	public String list(Food food, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Food> page = foodService.findPage(new Page<Food>(request, response), food); 
		model.addAttribute("page", page);
		return "modules/wx/foodList";
	}

	@RequiresPermissions("wx:food:view")
	@RequestMapping(value = "form")
	public String form(Food food, Model model) {
		model.addAttribute("food", food);
		return "modules/wx/foodForm";
	}

	@RequiresPermissions("wx:food:edit")
	@RequestMapping(value = "save")
	public String save(Food food, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, food)){
			return form(food, model);
		}
		foodService.save(food);
		addMessage(redirectAttributes, "保存菜品成功");
		return "redirect:"+Global.getAdminPath()+"/wx/food/?repage";
	}
	
	@RequiresPermissions("wx:food:edit")
	@RequestMapping(value = "delete")
	public String delete(Food food, RedirectAttributes redirectAttributes) {
		foodService.delete(food);
		addMessage(redirectAttributes, "删除菜品成功");
		return "redirect:"+Global.getAdminPath()+"/wx/food/?repage";
	}

}
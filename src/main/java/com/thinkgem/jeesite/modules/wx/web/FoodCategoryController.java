package com.thinkgem.jeesite.modules.wx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.entity.ResponseData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.wx.entity.FoodCategory;
import com.thinkgem.jeesite.modules.wx.service.FoodCategoryService;

import java.net.URLDecoder;

/**
 * 菜品分类Controller
 *
 * @author tgp
 * @version 2018-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/foodCategory")
public class FoodCategoryController extends BaseController {

    @Autowired
    private FoodCategoryService foodCategoryService;

    @ModelAttribute
    public FoodCategory get(@RequestParam(required = false) String id) {
        FoodCategory entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = foodCategoryService.get(id);
        }
        if (entity == null) {
            entity = new FoodCategory();
        }
        return entity;
    }

    /**
     * 菜品分类list
     */
    @RequiresPermissions("wx:foodCategory:view")
    @RequestMapping(value = {"list", ""})
    public String list(FoodCategory foodCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<FoodCategory> page = foodCategoryService.findPage(new Page<FoodCategory>(request, response), foodCategory);
        model.addAttribute("page", page);
        return "modules/wx/foodCategoryList";
    }

    /**
     * 菜品分类form
     */
    @RequiresPermissions("wx:foodCategory:view")
    @RequestMapping(value = "form")
    public String form(FoodCategory foodCategory, Model model) {
        model.addAttribute("foodCategory", foodCategory);
        return "modules/wx/foodCategoryForm";
    }

    /**
     * 保存菜品分类
     */
    @RequiresPermissions("wx:foodCategory:edit")
    @RequestMapping(value = "save")
    public String save(FoodCategory foodCategory, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, foodCategory)) {
            return form(foodCategory, model);
        }
        foodCategoryService.save(foodCategory);
        addMessage(redirectAttributes, "保存菜品分类成功");
        return "redirect:" + Global.getAdminPath() + "/wx/foodCategory/?repage";
    }

    /**
     * 删除菜品分类信息
     */
    @RequiresPermissions("wx:foodCategory:edit")
    @RequestMapping(value = "delete")
    public String delete(FoodCategory foodCategory, RedirectAttributes redirectAttributes) {
        foodCategoryService.delete(foodCategory);
        addMessage(redirectAttributes, "删除菜品分类成功");
        return "redirect:" + Global.getAdminPath() + "/wx/foodCategory/?repage";
    }

    /**
     * 校验菜品分类是否已存在
     */
    @RequiresPermissions("wx:foodCategory:edit")
    @RequestMapping(value = "checkCategoryName")
    @ResponseBody
    public ResponseData checkCategoryName(HttpServletRequest request) {
        ResponseData response = new ResponseData();
        try {
            String categoryName = URLDecoder.decode(request.getParameter("categoryName"), "UTF-8");
            String id = URLDecoder.decode(request.getParameter("id"), "UTF-8");
            if (StringUtils.isNotEmpty(categoryName)) {
                FoodCategory foodCategory = foodCategoryService.getByName(categoryName);
                if (null != foodCategory) {
                    if (StringUtils.isNotEmpty(id)) { // 修改菜品分类时，不校验当前菜品分类
                        if (!id.equals(foodCategory.getId())) {
                            response.setState("01");
                            return response;
                        }
                    } else { // 新增菜品分类
                        response.setState("01");
                        return response;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setState("00");
        return response;
    }

}
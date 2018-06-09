package com.thinkgem.jeesite.modules.wx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.entity.ActionBaseDto;
import com.thinkgem.jeesite.common.entity.ResponseData;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.service.StoreService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private StoreService storeService;

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
        // 判断当前用户的是否是店长，根据部门id为"d2716364f6d247af8748d873b9ace9cb"
        String officeId = UserUtils.getUser().getOffice().getId();
        if ("d2716364f6d247af8748d873b9ace9cb".equals(officeId)) {
            // 查询出店铺id
            Store store = storeService.getByUserId(UserUtils.getUser().getId());
            if (null == store) {
                logger.error("你好，你还不是某店铺的管理员，或者你所管理的店铺已被超级管理员删除，请联系超级管理员！");
                model.addAttribute("message", "你好，你还不是某店铺的管理员，或者你所管理的店铺已被超级管理员删除，请联系超级管理员！");
                return "modules/wx/noOperationPermissions";
            }
            foodCategory.setIsShopowner(true);
            foodCategory.setStoreId(store.getId());
        }

        Page<FoodCategory> page = foodCategoryService.findPage(new Page<FoodCategory>(request, response), foodCategory);
        if (!foodCategory.getIsShopowner()) { // 查询出所有的店铺
            Map<String, String> storeMap = new HashMap<String, String>();
            List<Store> storeList = storeService.listAllStore();
            for (Store store : storeList) {
                storeMap.put(store.getId(), store.getName());
            }
            model.addAttribute("storeMap", storeMap);
        }

        model.addAttribute("page", page);
        return "modules/wx/foodCategoryList";
    }

    /**
     * 菜品分类form
     */
    @RequiresPermissions("wx:foodCategory:view")
    @RequestMapping(value = "form")
    public String form(FoodCategory foodCategory, Model model) {
        // 判断当前用户的是否是店长，根据部门id为"d2716364f6d247af8748d873b9ace9cb"
        String officeId = UserUtils.getUser().getOffice().getId();
        if ("d2716364f6d247af8748d873b9ace9cb".equals(officeId)) {
            // 查询出店铺id
            Store store = storeService.getByUserId(UserUtils.getUser().getId());
            if (null == store) {
                logger.error("你好，你还不是某店铺的管理员，或者你所管理的店铺已被超级管理员删除，请联系超级管理员！");
                model.addAttribute("message", "你好，你还不是某店铺的管理员，或者你所管理的店铺已被超级管理员删除，请联系超级管理员！");
                return "modules/wx/noOperationPermissions";
            }
            foodCategory.setIsShopowner(true);
            foodCategory.setStoreId(store.getId());
        }

        if (!foodCategory.getIsShopowner()) { // 查询出所有的店铺
            List<Store> storeList = storeService.listAllStore();
            model.addAttribute("storeList", storeList);
        }

        model.addAttribute("foodCategory", foodCategory);
        return "modules/wx/foodCategoryForm";
    }

    /**
     * 保存菜品分类
     */
    @RequiresPermissions("wx:foodCategory:edit")
    @RequestMapping(value = "save")
    public String save(FoodCategory foodCategory, Model model, RedirectAttributes redirectAttributes) {
        if (null == foodCategory.getSort()) {
            foodCategory.setSort(0); // 默认排序为0
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
        if (null == foodCategory || StringUtils.isEmpty(foodCategory.getId())) {
            addMessage(redirectAttributes, "删除菜品分类失败，分类id不能为空！");
        } else {
            ActionBaseDto actionBaseDto = foodCategoryService.deleteByCategoryId(foodCategory.getId());
            if (actionBaseDto.isSuccess()) {
                addMessage(redirectAttributes, "删除菜品分类成功");
            } else {
                addMessage(redirectAttributes, "删除菜品分类失败，" + actionBaseDto.getDesc());
            }
        }
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
            String storeId = URLDecoder.decode(request.getParameter("storeId"), "UTF-8");
            String id = URLDecoder.decode(request.getParameter("id"), "UTF-8");
            if (StringUtils.isNotEmpty(categoryName) && StringUtils.isNotEmpty(storeId)) {
                FoodCategory foodCategory = foodCategoryService.getByNameAndStoreId(categoryName, storeId);
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
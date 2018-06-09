package com.thinkgem.jeesite.modules.wx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.entity.ActionBaseDto;
import com.thinkgem.jeesite.common.utils.ExceptionUtil;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.entity.FoodCategory;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.service.FoodCategoryService;
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
import com.thinkgem.jeesite.modules.wx.entity.Food;
import com.thinkgem.jeesite.modules.wx.service.FoodService;

import java.net.URLDecoder;
import java.util.*;

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

    @Autowired
    private FoodCategoryService foodCategoryService;

    @Autowired
    private StoreService storeService;

    // 商品推荐状态
    public static final Map<Boolean, String> recommendedStateMap = new LinkedHashMap<>();
    // 商品上架状态
    public static final Map<Boolean, String> shelfStateMap = new LinkedHashMap<>();

    static {
        recommendedStateMap.put(false, "否");
        recommendedStateMap.put(true, "是");

        shelfStateMap.put(false, "下架");
        shelfStateMap.put(true, "上架");
    }
    
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

    /**
     * 菜品list
     */
    @RequiresPermissions("wx:food:view")
    @RequestMapping(value = {"list", ""})
    public String list(Food food, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<FoodCategory> categoryList = new ArrayList<>();
        Map<String, String> storeMap = new HashMap<String, String>();
        // 判断当前用户是否是店长
        String officeId = UserUtils.getUser().getOffice().getId();
        if ("d2716364f6d247af8748d873b9ace9cb".equals(officeId)) {
            // 查询出店铺id
            Store store = storeService.getByUserId(UserUtils.getUser().getId());
            if (null == store) {
                logger.error("你好，你还不是某店铺的管理员，或者你所管理的店铺已被超级管理员删除，请联系超级管理员！");
                model.addAttribute("message", "你好，你还不是某店铺的管理员，或者你所管理的店铺已被超级管理员删除，请联系超级管理员！");
                return "modules/wx/noOperationPermissions";
            }
            food.setIsShopowner(true);
            food.setStoreId(store.getId());
            storeMap.put(store.getId(), store.getName());
        }

        if (StringUtils.isNotEmpty(food.getStoreId())) {
            // 查询出当前的所有分类
            categoryList = foodCategoryService.listAllFoodCategory(food.getStoreId());
        }

        if (!food.getIsShopowner()) { // 查询出所有的店铺
            List<Store> storeList = storeService.listAllStore();
            for (Store store : storeList) {
                storeMap.put(store.getId(), store.getName());
            }
            model.addAttribute("storeMap", storeMap);
        }

        Page<Food> page = foodService.findPage(new Page<Food>(request, response), food);
        List<Food> list = page.getList();
        for (Food foo : list) {
            FoodCategory foodCategory = foodCategoryService.get(foo.getCategoryId());
            if (null != foodCategory) {
                foo.setCategoryName(foodCategory.getName());
            }
        }

        model.addAttribute("page", page);
        model.addAttribute("list", list);
        model.addAttribute("categoryList", categoryList);
        return "modules/wx/foodList";
    }

    /**
     * 菜品form
     */
    @RequiresPermissions("wx:food:view")
    @RequestMapping(value = "form")
    public String form(Food food, Model model) {
        // 默认商品不推荐，默认商品已上架
        if (null == food.getRecommend()) {
            food.setRecommend(false);
        }
        if (null == food.getState()) {
            food.setState(true);
        }
        List<FoodCategory> categoryList = new ArrayList<>();

        // 判断当前用户是否是店长
        String officeId = UserUtils.getUser().getOffice().getId();
        if ("d2716364f6d247af8748d873b9ace9cb".equals(officeId)) {
            // 查询出店铺id
            Store store = storeService.getByUserId(UserUtils.getUser().getId());
            if (null == store) {
                logger.error("你好，你还不是某店铺的管理员，或者你所管理的店铺已被超级管理员删除，请联系超级管理员！");
                model.addAttribute("message", "你好，你还不是某店铺的管理员，或者你所管理的店铺已被超级管理员删除，请联系超级管理员！");
                return "modules/wx/noOperationPermissions";
            }
            food.setIsShopowner(true);
            food.setStoreId(store.getId());
        }

        if (StringUtils.isNotEmpty(food.getStoreId())) {
            // 查询出当前的所有分类
            categoryList = foodCategoryService.listAllFoodCategory(food.getStoreId());
        }

        if (!food.getIsShopowner()) { // 查询出所有的店铺
            Map<String, String> storeMap = new HashMap<String, String>();
            List<Store> storeList = storeService.listAllStore();
            for (Store store : storeList) {
                storeMap.put(store.getId(), store.getName());
            }
            model.addAttribute("storeMap", storeMap);
        }


        model.addAttribute("food", food);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("recommendedStateMap", recommendedStateMap);
        model.addAttribute("shelfStateMap", shelfStateMap);


        return "modules/wx/foodForm";
    }

    /**
     * 保存菜品
     */
    @RequiresPermissions("wx:food:edit")
    @RequestMapping(value = "save")
    public String save(Food food, Model model, RedirectAttributes redirectAttributes) {
        // 默认商品不推荐，默认商品已上架
        if (null == food.getRecommend()) {
            food.setRecommend(false);
        }
        if (null == food.getState()) {
            food.setState(true);
        }

        foodService.save(food);
        addMessage(redirectAttributes, "保存菜品成功");
        return "redirect:"+Global.getAdminPath()+"/wx/food/?repage";
    }

    /**
     * 推荐商品
     * @param food
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("wx:food:edit")
    @RequestMapping(value = "recommend")
    public String recommend(Food food, RedirectAttributes redirectAttributes) {
        if (null == food || StringUtils.isEmpty(food.getId())) {
            logger.error("推荐商品失败，商品id不能为空！");
            addMessage(redirectAttributes, "推荐商品失败，商品id不能为空");
        } else {
            food.setRecommend(true);
            try {
                ActionBaseDto actionBaseDto = foodService.updateById(food);
                if (actionBaseDto.isFailed()) {
                    addMessage(redirectAttributes, "推荐商品失败，" + actionBaseDto.getDesc());
                } else {
                    addMessage(redirectAttributes, "推荐商品成功");
                }
            } catch (Exception e) {
                logger.error("推荐商品失败，失败原因：{}", ExceptionUtil.getDesc(e));
                addMessage(redirectAttributes, "推荐商品失败，" + ExceptionUtil.getDesc(e));
            }
        }
        return "redirect:" + Global.getAdminPath() + "/wx/food/?repage";
    }

    /**
     * 推荐商品
     * @param food
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("wx:food:edit")
    @RequestMapping(value = "cancelRecommend")
    public String cancelRecommend(Food food, RedirectAttributes redirectAttributes) {
        if (null == food || StringUtils.isEmpty(food.getId())) {
            logger.error("取消推荐商品失败，商品id不能为空！");
            addMessage(redirectAttributes, "取消推荐商品失败，商品id不能为空");
        } else {
            food.setRecommend(false);
            try {
                ActionBaseDto actionBaseDto = foodService.updateById(food);
                if (actionBaseDto.isFailed()) {
                    addMessage(redirectAttributes, "取消推荐商品失败，" + actionBaseDto.getDesc());
                } else {
                    addMessage(redirectAttributes, "取消推荐商品成功");
                }
            } catch (Exception e) {
                logger.error("取消推荐商品失败，失败原因：{}", ExceptionUtil.getDesc(e));
                addMessage(redirectAttributes, "取消推荐商品失败，" + ExceptionUtil.getDesc(e));
            }
        }
        return "redirect:" + Global.getAdminPath() + "/wx/food/?repage";
    }

    /**
     * 上架商品
     * @param food
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("wx:food:edit")
    @RequestMapping(value = "grounding")
    public String grounding(Food food, RedirectAttributes redirectAttributes) {
        if (null == food || StringUtils.isEmpty(food.getId())) {
            logger.error("上架商品失败，商品id不能为空！");
            addMessage(redirectAttributes, "上架商品失败，商品id不能为空");
        } else {
            food.setState(true);
            try {
                ActionBaseDto actionBaseDto = foodService.updateById(food);
                if (actionBaseDto.isFailed()) {
                    addMessage(redirectAttributes, "上架商品失败，" + actionBaseDto.getDesc());
                } else {
                    addMessage(redirectAttributes, "上架商品成功");
                }
            } catch (Exception e) {
                logger.error("上架商品失败，失败原因：{}", ExceptionUtil.getDesc(e));
                addMessage(redirectAttributes, "上架商品失败，" + ExceptionUtil.getDesc(e));
            }
        }
        return "redirect:" + Global.getAdminPath() + "/wx/food/?repage";
    }

    /**
     * 下架商品
     * @param food
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("wx:food:edit")
    @RequestMapping(value = "undercarriage")
    public String undercarriage(Food food, RedirectAttributes redirectAttributes) {
        if (null == food || StringUtils.isEmpty(food.getId())) {
            logger.error("下架商品失败，商品id不能为空！");
            addMessage(redirectAttributes, "下架商品失败，商品id不能为空");
        } else {
            food.setState(false);
            try {
                ActionBaseDto actionBaseDto = foodService.updateById(food);
                if (actionBaseDto.isFailed()) {
                    addMessage(redirectAttributes, "下架商品失败，" + actionBaseDto.getDesc());
                } else {
                    addMessage(redirectAttributes, "下架商品成功");
                }
            } catch (Exception e) {
                logger.error("下架商品失败，失败原因：{}", ExceptionUtil.getDesc(e));
                addMessage(redirectAttributes, "下架商品失败，" + ExceptionUtil.getDesc(e));
            }
        }
        return "redirect:" + Global.getAdminPath() + "/wx/food/?repage";
    }

    /**
     * 根据店铺id查询改店铺下的分类
     * @param request
     * @return
     */
    @RequiresPermissions("wx:food:view")
    @RequestMapping(value = "/getCategoryList")
    @ResponseBody
    public List<FoodCategory> getCategoryList(HttpServletRequest request){
        List<FoodCategory> foodCategoryList = new ArrayList<>();
        try {
            String storeId = URLDecoder.decode(request.getParameter("storeId"), "UTF-8");
            foodCategoryList = foodCategoryService.listAllFoodCategory(storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodCategoryList;
    }


}
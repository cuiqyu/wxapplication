/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.entity.ResponseData;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.wx.entity.FoodCategory;
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
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.service.StoreService;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺管理Controller
 *
 * @author cqy
 * @version 2018-06-08
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/store")
public class StoreController extends BaseController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private SystemService systemService;

    @ModelAttribute
    public Store get(@RequestParam(required = false) String id) {
        Store entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = storeService.get(id);
        }
        if (entity == null) {
            entity = new Store();
        }
        return entity;
    }

    @RequiresPermissions("wx:store:view")
    @RequestMapping(value = {"list", ""})
    public String list(Store store, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Store> page = storeService.findPage(new Page<Store>(request, response), store);
        // 查询所有的店长
        List<User> userList = systemService.findUserByOfficeId("d2716364f6d247af8748d873b9ace9cb");
        Map<String, String> userMap = new HashMap<String, String>();
        for (User user : userList) {
            userMap.put(user.getId(), user.getName());
        }

        model.addAttribute("userMap", userMap);
        model.addAttribute("page", page);
        return "modules/wx/storeList";
    }

    @RequiresPermissions("wx:store:view")
    @RequestMapping(value = "form")
    public String form(Store store, Model model) {
        // 查询所有的未管理店铺的用户
        List<User> userList = systemService.findUserByOfficeIdAndNoManageStore("d2716364f6d247af8748d873b9ace9cb", store.getId());

        model.addAttribute("userList", userList);
        model.addAttribute("store", store);
        return "modules/wx/storeForm";
    }

    @RequiresPermissions("wx:store:edit")
    @RequestMapping(value = "save")
    public String save(Store store, Model model, RedirectAttributes redirectAttributes) {
        storeService.save(store);
        addMessage(redirectAttributes, "保存店铺管理成功");
        return "redirect:" + Global.getAdminPath() + "/wx/store/?repage";
    }

    @RequiresPermissions("wx:store:edit")
    @RequestMapping(value = "delete")
    public String delete(Store store, RedirectAttributes redirectAttributes) {
        storeService.delete(store);
        addMessage(redirectAttributes, "删除店铺管理成功");
        return "redirect:" + Global.getAdminPath() + "/wx/store/?repage";
    }

    /**
     * 校验店铺名称是否重复
     */
    @RequiresPermissions("wx:store:edit")
    @RequestMapping(value = "checkStoreName")
    @ResponseBody
    public ResponseData checkStoreName(HttpServletRequest request) {
        ResponseData response = new ResponseData();
        try {
            String storeName = URLDecoder.decode(request.getParameter("storeName"), "UTF-8");
            String id = URLDecoder.decode(request.getParameter("id"), "UTF-8");
            if (StringUtils.isNotEmpty(storeName)) {
                Store store = storeService.getByName(storeName);
                if (null != store) {
                    if (StringUtils.isNotEmpty(id)) { // 修改店铺时，不校验当前店铺
                        if (!id.equals(store.getId())) {
                            response.setState("01");
                            return response;
                        }
                    } else { // 新增店铺
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
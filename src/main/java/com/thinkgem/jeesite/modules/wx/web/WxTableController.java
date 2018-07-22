/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wx.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.entity.ActionBaseDto;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.service.StoreService;
import org.apache.commons.collections.CollectionUtils;
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
import com.thinkgem.jeesite.modules.wx.entity.WxTable;
import com.thinkgem.jeesite.modules.wx.service.WxTableService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 桌号管理Controller
 *
 * @author cqy
 * @version 2018-07-20
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/wxTable")
public class WxTableController extends BaseController {

    @Autowired
    private WxTableService wxTableService;

    @Autowired
    private StoreService storeService;

    @ModelAttribute
    public WxTable get(@RequestParam(required = false) String id) {
        WxTable entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = wxTableService.get(id);
        }
        if (entity == null) {
            entity = new WxTable();
        }
        return entity;
    }

    /**
     * 桌号列表
     * @param wxTable
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("wx:wxTable:view")
    @RequestMapping(value = {"list", ""})
    public String list(WxTable wxTable, HttpServletRequest request, HttpServletResponse response, Model model) {
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
            wxTable.setIsShopowner(true);
            wxTable.setStoreId(store.getId());
            wxTable.setStoreName(store.getName());
        }

        if (!wxTable.getIsShopowner()) { // 查询出所有的店铺
            List<Store> storeList = storeService.listAllStore();
            for (Store store : storeList) {
                storeMap.put(store.getId(), store.getName());
            }
        }

        Page<WxTable> page = wxTableService.findPage(new Page<WxTable>(request, response), wxTable);
        model.addAttribute("wxTable", wxTable);
        model.addAttribute("page", page);
        model.addAttribute("storeMap", storeMap);
        return "modules/wx/wxTableList";
    }

    @RequiresPermissions("wx:wxTable:view")
    @RequestMapping(value = "form")
    public String form(WxTable wxTable, Model model) {
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
            wxTable.setIsShopowner(true);
            wxTable.setStoreId(store.getId());
            wxTable.setStoreName(store.getName());
        }

        if (!wxTable.getIsShopowner()) { // 查询出所有的店铺
            List<Store> storeList = storeService.listAllStore();
            for (Store store : storeList) {
                storeMap.put(store.getId(), store.getName());
            }
        }

        model.addAttribute("storeMap", storeMap);
        model.addAttribute("wxTable", wxTable);
        return "modules/wx/wxTableForm";
    }

    @RequiresPermissions("wx:wxTable:edit")
    @RequestMapping(value = "save")
    public String save(WxTable wxTable, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("\\qrimg");
        String savePath = "\\qrimg";
        ActionBaseDto actionBaseDto = wxTableService.insert(wxTable, realPath, savePath);
        if (actionBaseDto.isSuccess()) {
            logger.info("保存桌号成功！");
            addMessage(redirectAttributes, "保存桌号成功");
        } else {
            logger.error("保存桌号失败，失败原因：{}", actionBaseDto.getDesc());
            addMessage(redirectAttributes, "保存桌号失败，失败原因：" + actionBaseDto.getDesc());
        }
        return "redirect:" + Global.getAdminPath() + "/wx/wxTable/?repage";
    }

}
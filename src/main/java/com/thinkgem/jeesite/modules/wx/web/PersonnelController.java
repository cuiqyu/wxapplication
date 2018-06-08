package com.thinkgem.jeesite.modules.wx.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * 人员管理
 *
 * @auther cuiqiongyu
 * @create 2018/6/7 21:38
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/personnel")
public class PersonnelController extends BaseController {

    @Autowired
    private SystemService systemService;

    @ModelAttribute
    public User get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return systemService.getUser(id);
        }else{
            return new User();
        }
    }

    @RequiresPermissions("wx:personnel:view")
    @RequestMapping(value = {"list", ""})
    public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        // 只查询公司为微信点餐，部门为店长部的用户信息
        user.setCompany(new Office("1")); // 直接写死 微信点餐 TOOD
        user.setOffice(new Office("d2716364f6d247af8748d873b9ace9cb")); // 直接写死 店长部 TODO
        Page<User> page = systemService.findUser(new Page<User>(request, response), user);
        model.addAttribute("page", page);
        return "modules/wx/userList";
    }

    @RequiresPermissions("wx:personnel:view")
    @RequestMapping(value = "form")
    public String form(User user, Model model) {
        if (user.getCompany() == null || user.getCompany().getId() == null) {
            user.setCompany(UserUtils.getUser().getCompany());
        }
        if (user.getOffice() == null || user.getOffice().getId() == null) {
            user.setOffice(UserUtils.getUser().getOffice());
        }

        List<Role> allRoles = systemService.findAllRole();
        Iterator<Role> iterator = allRoles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (!role.getId().equals("6")) { // 只展示店长的角色
                iterator.remove();
            }
        }

        model.addAttribute("user", user);
        model.addAttribute("allRoles", allRoles);
        return "modules/wx/userForm";
    }

    @RequiresPermissions("wx:personnel:edit")
    @RequestMapping(value = "save")
    public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        user.setCompany(new Office("1")); // 直接写死 微信点餐 TOOD
        user.setOffice(new Office("d2716364f6d247af8748d873b9ace9cb")); // 直接写死 店长部 TODO
        // 如果新密码为空，则不更换密码
        if (StringUtils.isNotBlank(user.getNewPassword())) {
            user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
        }
        if (!beanValidator(model, user)){
            return form(user, model);
        }
        if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
            addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
            return form(user, model);
        }

        // 保存用户信息
        systemService.saveUser(user);
        // 清除当前用户缓存
        if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
            UserUtils.clearCache();
        }
        addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");

        return "redirect:" + adminPath + "/wx/personnel/list?repage";
    }

    @RequiresPermissions("wx:personnel:edit")
    @RequestMapping(value = "delete")
    public String delete(User user, RedirectAttributes redirectAttributes) {
        if (UserUtils.getUser().getId().equals(user.getId())) {
            addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
        } else if (User.isAdmin(user.getId())) {
            addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
        } else {
            systemService.deleteUser(user);
            addMessage(redirectAttributes, "删除用户成功");
            return "redirect:" + adminPath + "/wx/personnel/list?repage";
        }

        return "redirect:" + adminPath + "/wx/personnel/list?repage";
    }

    /**
     * 验证登录名是否有效
     * @param oldLoginName
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "checkLoginName")
    public String checkLoginName(String oldLoginName, String loginName) {
        if (loginName !=null && loginName.equals(oldLoginName)) {
            return "true";
        } else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
            return "true";
        }
        return "false";
    }

}

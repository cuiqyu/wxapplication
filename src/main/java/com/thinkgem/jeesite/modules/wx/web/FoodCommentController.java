package com.thinkgem.jeesite.modules.wx.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.entity.Food;
import com.thinkgem.jeesite.modules.wx.entity.FoodComment;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.service.FoodCommentService;
import com.thinkgem.jeesite.modules.wx.service.FoodService;
import com.thinkgem.jeesite.modules.wx.service.StoreService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评价Controller
 *
 * @auther cuiqiongyu
 * @create 2018/7/7 11:25
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/comment")
public class FoodCommentController extends BaseController {

    @Autowired
    private FoodCommentService foodCommentService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private FoodService foodService;

    /**
     * 评价list
     * @return
     */
    @RequiresPermissions("wx:comment:view")
    @RequestMapping(value = {"list", ""})
    public String list(FoodComment foodComment, HttpServletRequest request, HttpServletResponse response, Model model) {
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
            foodComment.setStoreId(store.getId());
            foodComment.setIsShopowner(true);
            foodComment.setStoreName(store.getName());
            storeMap.put(store.getId(), store.getName());
        }

        if (!foodComment.getIsShopowner()) { // 查询出所有的店铺
            List<Store> storeList = storeService.listAllStore();
            for (Store store : storeList) {
                storeMap.put(store.getId(), store.getName());
            }
        }

        // 分页查询评价内容
        Page<FoodComment> page = foodCommentService.findPage(new Page<FoodComment>(request, response), foodComment);
        List<FoodComment> commentList = page.getList();
        if (CollectionUtils.isNotEmpty(commentList)) {
            for (FoodComment comment : commentList) {
                Food food = foodService.get(comment.getFoodId());
                if (null != food) {
                    comment.setFoodName(food.getName());
                }
            }
        }

        model.addAttribute("page", page);
        model.addAttribute("commentList", commentList);
        model.addAttribute("foodComment", foodComment);
        model.addAttribute("storeMap", storeMap);
        return "modules/wx/commentList";
    }

}

package com.thinkgem.jeesite.modules.wx.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.entity.Order;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.service.FoodCategoryService;
import com.thinkgem.jeesite.modules.wx.service.FoodService;
import com.thinkgem.jeesite.modules.wx.service.OrderService;
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
 * 订单controller
 *
 * @auther cuiqiongyu
 * @create 2018/7/7 14:02
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/order")
public class FoodOrderController extends BaseController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private FoodCategoryService foodCategoryService;

    @Autowired
    private FoodService foodService;

    /**
     * 订单list
     */
    @RequiresPermissions("wx:order:view")
    @RequestMapping(value = {"list", ""})
    public String list(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
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
            order.setIsShopowner(true);
            order.setStoreId(store.getId());
            order.setStoreName(store.getName());
        }

        if (!order.getIsShopowner()) { // 查询出所有的店铺
            List<Store> storeList = storeService.listAllStore();
            for (Store store : storeList) {
                storeMap.put(store.getId(), store.getName());
            }
        }

        Page<Order> page = orderService.findPage(new Page<Order>(request, response), order);
        List<Order> orderList = page.getList();
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (Order param : orderList) {
                String foodDetail = param.getFoodDetail();
                // List<Order2Food> foodDetailInfos = JsonUtils.Str2List(foodDetail, Order2Food.class);
                // param.setFoodDetailInfoList(foodDetailInfos);
            }
        }

        model.addAttribute("storeMap", storeMap);
        model.addAttribute("orderList", orderList);
        model.addAttribute("page", page);
        model.addAttribute("order", order);
        return "modules/wx/orderList";
    }

}

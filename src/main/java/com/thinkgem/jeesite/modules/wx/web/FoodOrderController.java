package com.thinkgem.jeesite.modules.wx.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.wx.constant.OrderState;
import com.thinkgem.jeesite.modules.wx.entity.Order;
import com.thinkgem.jeesite.modules.wx.entity.Order2Food;
import com.thinkgem.jeesite.modules.wx.entity.Store;
import com.thinkgem.jeesite.modules.wx.entity.vo.StoreOrderTotalAmountVo;
import com.thinkgem.jeesite.modules.wx.service.FoodCategoryService;
import com.thinkgem.jeesite.modules.wx.service.FoodService;
import com.thinkgem.jeesite.modules.wx.service.OrderService;
import com.thinkgem.jeesite.modules.wx.service.StoreService;
import com.thinkgem.jeesite.modules.wx.utils.JsonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 订单controller
 *
 * @auther cuiqiongyu
 * @create 23218/7/7 14:322
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

    private static List<String > oneTo12; // 1-12
    private static List<String> ttTo31; // 13-31

    static {
        oneTo12 = new ArrayList<>();
        oneTo12.add("1");
        oneTo12.add("2");
        oneTo12.add("3");
        oneTo12.add("4");
        oneTo12.add("5");
        oneTo12.add("6");
        oneTo12.add("7");
        oneTo12.add("8");
        oneTo12.add("9");
        oneTo12.add("10");
        oneTo12.add("11");
        oneTo12.add("12");

        ttTo31 = new ArrayList<>();
        ttTo31.add("13");
        ttTo31.add("14");
        ttTo31.add("15");
        ttTo31.add("16");
        ttTo31.add("17");
        ttTo31.add("18");
        ttTo31.add("19");
        ttTo31.add("20");
        ttTo31.add("21");
        ttTo31.add("22");
        ttTo31.add("23");
        ttTo31.add("24");
        ttTo31.add("25");
        ttTo31.add("26");
        ttTo31.add("27");
        ttTo31.add("28");
        ttTo31.add("29");
        ttTo31.add("30");
        ttTo31.add("31");
    }

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
                 List<Order2Food> foodDetailInfos = JsonUtils.Str2List(foodDetail, Order2Food.class);
                 param.setFoodDetailInfoList(foodDetailInfos);
            }
        }

        model.addAttribute("storeMap", storeMap);
        model.addAttribute("orderList", orderList);
        model.addAttribute("page", page);
        model.addAttribute("order", order);
        return "modules/wx/orderList";
    }

    /**
     * 业绩统计list
     */
    @RequiresPermissions("wx:order:view")
    @RequestMapping(value = "achievementList")
    public String achievementList(Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
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
            order.setStoreId(store.getId());
            order.setStoreName(store.getName());
            storeMap.put(store.getId(), store.getName());
            order.setIsShopowner(true);
        }

        if (!order.getIsShopowner()) {
            if (!order.getIsShopowner()) { // 查询出所有的店铺
                List<Store> storeList = storeService.listAllStore();
                for (Store store : storeList) {
                    storeMap.put(store.getId(), store.getName());
                }
            }
        }

        // 按店铺分组查询总额
        List<StoreOrderTotalAmountVo> orderTotalAmountList = orderService.findStoreTotalAmount(order);
        Map<String, Map<OrderState, Double>> orderTotalAmountMap = new HashMap<>();
        for (StoreOrderTotalAmountVo totalAmountVo : orderTotalAmountList) {
            Map<OrderState, Double> amountMap = orderTotalAmountMap.get(totalAmountVo.getStoreId());
            if (null == amountMap) {
                amountMap = new HashMap<>();
            }
            amountMap.put(totalAmountVo.getState(), totalAmountVo.getTotalAmount());
            orderTotalAmountMap.put(totalAmountVo.getStoreId(), amountMap);
        }

        // 按店铺分组查询详细总额
        List<StoreOrderTotalAmountVo> orderTotalDetailAmountList = new ArrayList<>();
        Map<String, Map<OrderState, Map<String, Double>>> orderTotalDetailAmountMap = new HashMap<>();
        if (("year".equals(order.getOrderTimeType()) && null != order.getCreateYear()) // 按年查
            || "month".equals(order.getOrderTimeType()) && null != order.getCreateMonth() // 按月查
            ) {

            // 处理按年查询的条件
            if (null != order.getCreateYear()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(order.getCreateYear());
                calendar.add(Calendar.YEAR, 1); // 加一年
                order.setCreateYearEnd(calendar.getTime());
            }
            orderTotalDetailAmountList = orderService.findStoreTotalDetailAmount(order);
        }
        if (CollectionUtils.isNotEmpty(orderTotalDetailAmountList)) {
            for (StoreOrderTotalAmountVo totalDetailAmountVo : orderTotalDetailAmountList) {
                Map<OrderState, Map<String, Double>> orderStateMapMap = orderTotalDetailAmountMap.get(totalDetailAmountVo.getStoreId());
                if (null == orderStateMapMap) { // 判断是否有店铺的信息
                    orderStateMapMap = new HashMap<>();
                }
                Map<String, Double> integerDoubleMap = orderStateMapMap.get(totalDetailAmountVo.getState()); // 判断是否有该订单状态信息
                if (null == integerDoubleMap) {
                    integerDoubleMap = new HashMap<>();
                }
                BigDecimal currentTotalAmount = ((null == integerDoubleMap.get("32")) ? BigDecimal.ZERO : new BigDecimal(Double.toString(integerDoubleMap.get("32"))))
                        .add(new BigDecimal(Double.toString(totalDetailAmountVo.getTotalAmount())));
                integerDoubleMap.put("32", currentTotalAmount.doubleValue()); // 统计总金额
                integerDoubleMap.put(totalDetailAmountVo.getNumber().toString(), totalDetailAmountVo.getTotalAmount());
                orderStateMapMap.put(totalDetailAmountVo.getState(), integerDoubleMap); // 塞入店铺的订单状态信息
                orderTotalDetailAmountMap.put(totalDetailAmountVo.getStoreId(), orderStateMapMap); // 塞入店铺信息
            }
        }

        // 订单状态
        Map<String, OrderState> orderStateMap = new HashMap<>();
        orderStateMap.put("paid", OrderState.PAID);
        orderStateMap.put("unpaid", OrderState.UNPAID);

        model.addAttribute("order", order);
        model.addAttribute("orderStateMap", orderStateMap);
        model.addAttribute("storeMap", storeMap);
        model.addAttribute("orderTotalAmountMap", orderTotalAmountMap);
        model.addAttribute("orderTotalDetailAmountMap", orderTotalDetailAmountMap);
        model.addAttribute("oneTo12", oneTo12);
        model.addAttribute("ttTo31", ttTo31);
        return "modules/wx/achievementList";
    }

}

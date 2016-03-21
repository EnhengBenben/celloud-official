package com.celloud.manager.action;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.ConstantsData;
import com.celloud.manager.model.App;
import com.celloud.manager.model.Price;
import com.celloud.manager.service.AppService;

/**
 * APP管理类
 * 
 * @author leamo
 * @date 2016年3月18日 下午4:21:51
 */
@Controller
@RequestMapping("app")
public class AppAction {
    Logger logger = LoggerFactory.getLogger(DataAction.class);
    @Resource
    private AppService appService;

    @RequestMapping("priceList")
    public ModelAndView toAppPriceList() {
        ModelAndView mv = new ModelAndView("app/app_price_list");
        List<App> appPriceList = appService
                .appPriceList(ConstantsData.getLoginCompanyId());
        mv.addObject("appPriceList", appPriceList);
        return mv;
    }

    @RequestMapping("priceHistory")
    public ModelAndView toAppPriceHistory(Integer appId, String appName,
            BigDecimal price) {
        ModelAndView mv = new ModelAndView("app/app_price_history");
        List<Price> appPriceList = appService.appPriceHistory(appId);
        mv.addObject("appPriceList", appPriceList);
        mv.addObject("appId", appId);
        mv.addObject("appName", appName);
        mv.addObject("currentPrice", price);
        return mv;
    }

    @RequestMapping("updatePice")
    @ResponseBody
    public Integer updatePice(Integer appId, BigDecimal price) {
        return appService.updatePrice(appId, price);
    }
}

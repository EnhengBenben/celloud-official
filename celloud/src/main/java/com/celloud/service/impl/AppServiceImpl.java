package com.celloud.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.AppConstants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.PriceType;
import com.celloud.constants.ReportPeriod;
import com.celloud.constants.ReportType;
import com.celloud.constants.UserRole;
import com.celloud.mapper.AppMapper;
import com.celloud.mapper.PriceMapper;
import com.celloud.mapper.UserMapper;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.AppVO;
import com.celloud.model.mysql.Price;
import com.celloud.model.mysql.User;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;

/**
 * app接口实现类
 *
 * @author han
 * @date 2015年12月25日 下午5:54:15
 */
@Service("appService")
public class AppServiceImpl implements AppService {

    @Resource
    private AppMapper appMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PriceMapper priceMapper;

    @Override
    public Integer countMyApp(Integer userId) {
        return appMapper.countMyApp(userId, AppConstants.ON,
                AppConstants.ALREADY_ADDED);
    }

    @Override
    public List<Map<String, String>> countMyApp(Integer userId, String time) {
        return appMapper.countMyAppRanNumByTime(userId, time, AppConstants.ON,
                AppConstants.ALREADY_ADDED, ReportType.PROJECT,
                ReportPeriod.COMPLETE);
    }

    @Override
    public List<Map<String, String>> getRanAPP(Integer userId) {
        return appMapper.getRanAPP(userId);
    }

    @Override
    public List<App> getAppByClassify(Integer classifyId, Integer userId) {
        return appMapper.getAppByClassify(classifyId, userId, AppConstants.ON,
                AppConstants.PRIVATE, AppConstants.PUBLIC);
    }

    @Override
    public PageList<App> getAppPageListByClassify(Integer classifyId,
            Integer classifyPId, Integer userId, String sortField,
            String sortType, Page page) {
        List<App> list = appMapper.getAppPageListByClassify(classifyId,
                classifyPId, userId, sortField, sortType, AppConstants.ON,
                PriceType.isApp, AppConstants.PRIVATE, AppConstants.PUBLIC,
                page);
        return new PageList<>(page, list);
    }

    @Override
    public App getAppById(Integer id, Integer userId) {
		return appMapper.getAppById(id, userId, PriceType.isApp, AppConstants.PUBLIC);
    }

    @Override
    public List<App> getMyAppList(Integer userId) {
        return appMapper.getMyAppList(userId, AppConstants.ON,
                AppConstants.ALREADY_ADDED);
    }

	@Override
	public List<App> getRightAppList(Integer authFrom, Integer userId) {
        return appMapper.getRightAppList(authFrom, userId, AppConstants.ON);
	}

    @Override
    public Integer userAddApp(Integer userId, Integer appId) {
		Integer num = appMapper.userUpdateApp(userId, appId, AppConstants.ALREADY_ADDED);
		if (num.equals(0)) {
			App app = appMapper.selectByPrimaryKey(appId);
			if (app.getAttribute().equals(AppConstants.PUBLIC)) {
				num = appMapper.insertUserAppRight(userId, appId, UserRole.ADMINISTRATOR, AppConstants.ALREADY_ADDED);
			}
		}
		return num;
    }

    @Override
    public Integer userRemoveApp(Integer userId, Integer appId) {
        return appMapper.userUpdateApp(userId, appId, AppConstants.NOT_ADDED);
    }

    @Override
    public List<App> findAppsByFormat(Integer userId, Integer formatId) {
        return appMapper.findAppsByFormat(userId, formatId, AppConstants.ON,
                AppConstants.ALREADY_ADDED);
    }

    @Override
    public App findAppById(Integer appId) {
        return appMapper.selectByPrimaryKey(appId);
    }

    @Override
    public List<App> findAppsByIds(String appIds) {
        return appMapper.findAppsByIds(appIds);
    }

    @Override
    public String findAppNamesByIds(String appIds) {
        List<App> list = appMapper.findAppsByIds(appIds);
        List<String> names = new ArrayList<>();
        for (App app : list) {
            names.add(app.getAppName());
        }
        return names.toString();
    }

    @Override
    public App selectByPrimaryKey(Integer appId) {
        return appMapper.selectByPrimaryKey(appId);
    }

    @Override
    public App findAppsByTag(Integer tagId) {
        return appMapper.findAppsByTag(tagId);
    }

    @Override
    public Boolean checkPriceToRun(List<Integer> appIds, Integer userId) {
		User user = userMapper.selectUserByIdNotIcon(userId);
        BigDecimal appPrice = BigDecimal.ZERO;
        for (Integer id : appIds) {
            Price price = priceMapper.selectByItemId(id, PriceType.isApp);
            appPrice = appPrice.add(price == null ? BigDecimal.ZERO : price.getPrice());
        }
        BigDecimal balances = user.getBalances();
        return balances.compareTo(appPrice) >= 0;
    }

	@Override
	public int addUserAppRight(Integer userId, Integer[] appIds, Integer authFrom) {
        return appMapper.addUserAppRight(userId, appIds, AppConstants.NOT_ADDED,
                authFrom);
	}

	@Override
	public boolean appDeleteByAuthFrom(Integer userId, Integer[] appIds) {
		Integer authFrom = ConstantsData.getLoginUserId();
		return appMapper.deleteByAuthFrom(userId, authFrom, appIds) > 0;
	}

    @Override
    public Integer getAppIdByTagId(Integer tagId) {
        return appMapper.getAppIdByTagId(tagId);
    }

    @Override
    public PageList<AppVO> listByClassifyId(Page page, Integer classifyId, Integer userId) {
        List<AppVO> datas = appMapper.selectByClassifyId(page, classifyId, userId, AppConstants.ON);
        if (datas == null || datas.isEmpty()) {
            return null;
        }
        return new PageList<AppVO>(page, datas);
    }

    @Override
    public Boolean updateUserAppRight(Integer userId, Integer appId, Integer isAdd) {
        Integer num = appMapper.updateUserAppRight(userId, appId, isAdd);
        return num.intValue() == 1;
    }

    @Override
    public App get(Integer id) {
        return appMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> getUserAppRight(Integer userId, Integer appId) {
        return appMapper.selectUserAppRight(userId, appId);
    }

    @Override
    public PageList<AppVO> selectBySelective(Page page, App app, Integer userId) {
        List<AppVO> datas = appMapper.selectBySelective(page, app, userId, AppConstants.ON);
        if (datas == null || datas.isEmpty()) {
            return null;
        }
        return new PageList<AppVO>(page, datas);
    }

}

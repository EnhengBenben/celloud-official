package com.celloud.manager.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.celloud.manager.constants.AppOffline;
import com.celloud.manager.constants.AppPermission;
import com.celloud.manager.constants.IconConstants;
import com.celloud.manager.constants.PriceType;
import com.celloud.manager.mapper.AppMapper;
import com.celloud.manager.mapper.PriceMapper;
import com.celloud.manager.mapper.ScreenMapper;
import com.celloud.manager.model.App;
import com.celloud.manager.model.Price;
import com.celloud.manager.model.Screen;
import com.celloud.manager.page.Page;
import com.celloud.manager.page.PageList;
import com.celloud.manager.service.AppService;
import com.celloud.manager.utils.PropertiesUtil;

/**
 * APP操作实现类
 * 
 * @author leamo
 * @date 2016年3月21日 上午11:12:17
 */
@Service("appService")
public class AppServiceImpl implements AppService {
    Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);
    @Resource
    private AppMapper appMapper;
    @Resource
    private PriceMapper priceMapper;
    @Resource
    private ScreenMapper screenMapper;

    @Override
    public List<App> appPriceList(Integer companyId) {
        return appMapper.appPriceList(companyId, AppOffline.ON, PriceType.isApp);
    }

    @Override
    public List<Price> appPriceHistory(Integer appId) {
        return priceMapper.getPriceList(appId, PriceType.isApp);
    }

    @Override
    public Integer updatePrice(Integer appId, BigDecimal price) {
        Price priceObjcx = new Price();
        priceObjcx.setItemId(appId);
        priceObjcx.setItemType(PriceType.isApp);
        priceObjcx.setExpireDate(new Date());
        priceMapper.updateExpireDate(priceObjcx);
        priceObjcx.setId(null);
        priceObjcx.setCreateDate(new Date());
        priceObjcx.setExpireDate(null);
        priceObjcx.setPrice(price);
        return priceMapper.insertSelective(priceObjcx);
    }

    @Override
    public List<App> getAppListByCompany(Integer companyId) {
        if (companyId == null) {
            return null;
        }
        return appMapper.getAppListByCompany(companyId, AppPermission.PRIVATE, AppOffline.ON);
    }

	@Override
	public List<App> getAppListByUserId(Integer userId) {
		if (userId == null) {
			return null;
		}
		return appMapper.getAppListByUserId(userId, AppPermission.PRIVATE, AppOffline.ON);
	}

    @Override
    public List<App> getAppListPulbicAdded() {
        return appMapper.getAppListAdded(AppPermission.PUBLIC, AppOffline.ON);
    }

    @Override
    public PageList<App> getAppByPage(Page page) {
        List<App> list = appMapper.getAppListByPage(page);
        return new PageList<>(page, list);
    }

    @Override
    public int updateAppOn(Integer appId) {
        if (appId != null) {
            return appMapper.updateAppOffline(AppOffline.ON, appId);
        }
        return 0;
    }

    @Override
    public int updateAppOff(Integer appId) {
        if (appId != null) {
            return appMapper.updateAppOffline(AppOffline.OFF, appId);
        }
        return 0;
    }

    @Override
    public int addApp(App app, String[] screenNames, Integer[] formatIds, Integer[] calssifyIds) {
        int flag = 0;
        try {
            app.setOffLine(AppOffline.ON);
            appMapper.insertApp(app);
            Integer appId = app.getAppId();
            if (appId != null) {
                String pictureName = app.getPictureName();
                if (StringUtils.isNotBlank(pictureName)) {
					FileUtils.moveFile(new File(IconConstants.getTempPath(pictureName)),
							new File(IconConstants.getAppPath(pictureName)));
                }
                if (screenNames != null && screenNames.length > 0) {
                    for (String screenName : screenNames) {
                        Screen screen = new Screen();
                        screen.setAppId(appId);
                        screen.setScreenName(screenName);
                        int result = screenMapper.insertScreen(screen);
                        if (result > 0) {
							FileUtils.moveFile(new File(IconConstants.getTempPath(screenName)),
									new File(IconConstants.getAppPath(screenName)));
                        }
                    }

                }
                if (formatIds != null && formatIds.length > 0) {
                    appMapper.insertAppFileFormatBatch(appId, formatIds);
                }
                if (calssifyIds != null && calssifyIds.length > 0) {
                    appMapper.insertAppClassifyBatch(appId, calssifyIds);
                }
				IconConstants.cleanTemp();
                flag = 1;
            }

        } catch (Exception e) {
            logger.error("新增App异常：{}", e.getMessage());
        }
        return flag;
    }

    @Override
    public int updateApp(App app, String[] screenNames, String[] delScreenNames, Integer[] formatIds,
            Integer[] calssifyIds) {
        if (app == null || app.getAppId() == null) {
            return 0;
        }
        int flag = 0;
        try {
            App a = appMapper.selectByPrimaryKey(app.getAppId());

            String oldPictureName = a.getPictureName();
            String newPictureName = app.getPictureName();

            a.setAddress(app.getAddress());
            a.setAppDoc(app.getAppDoc());
            a.setAppName(app.getAppName());
            a.setAttribute(app.getAttribute());
            a.setCommand(app.getCommand());
            a.setCompanyId(app.getCompanyId());
            a.setDataNum(app.getDataNum());
            a.setDescription(app.getDescription());
            a.setEnglishName(app.getEnglishName());
            a.setFlag(app.getFlag());
            a.setIntro(app.getIntro());
            a.setMaxTask(app.getMaxTask());
            a.setMethod(app.getMethod());
            a.setParam(app.getParam());
            a.setPictureName(app.getPictureName());
            a.setRunData(app.getRunData());
            a.setRunType(app.getRunType());
            a.setTitle(app.getTitle());
            @SuppressWarnings("unused")
            int result = appMapper.updateApp(a);
            if (StringUtils.isNotBlank(oldPictureName) && !oldPictureName.equals(newPictureName)) {
				File delFile = new File(IconConstants.getAppPath(oldPictureName));
                if (delFile.exists()) {
                    FileUtils.deleteQuietly(delFile);
                }
            }
            if (StringUtils.isNotBlank(newPictureName) && !newPictureName.equals(oldPictureName)) {
                try {
					FileUtils.moveFile(new File(IconConstants.getTempPath(newPictureName)),
							new File(IconConstants.getAppPath(newPictureName)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            appMapper.deleteAppClassify(a.getAppId());
            if (calssifyIds != null && calssifyIds.length > 0) {
                appMapper.insertAppClassifyBatch(a.getAppId(), calssifyIds);
            }
            appMapper.deleteAppFileFormat(a.getAppId());
            if (formatIds != null && formatIds.length > 0) {
                appMapper.insertAppFileFormatBatch(a.getAppId(), formatIds);
            }
            if (delScreenNames != null && delScreenNames.length > 0) {
                for (String del : delScreenNames) {
                    screenMapper.deleteByAppId(a.getAppId(), del);
                }
            }
            if (screenNames != null && screenNames.length > 0) {
                for (String screenName : screenNames) {
                    Screen screen = new Screen();
                    screen.setAppId(a.getAppId());
                    screen.setScreenName(screenName);
                    int r = screenMapper.insertScreen(screen);
                    if (r > 0) {
						FileUtils.moveFile(new File(IconConstants.getTempPath(screenName)),
								new File(IconConstants.getScreenPath(screenName)));
                    }
                }

            }
			IconConstants.cleanTemp();
            flag = 1;
        } catch (Exception e) {
            logger.error("更新App异常：{}", e.getMessage());
        }
        return flag;
    }

    @Override
    public int appNameExist(Integer appId, String appName) {
        return appMapper.appNameExist(appId, appName);
    }

    @Override
    public App getAppById(Integer appId) {
        if (appId == null) {
            return null;
        }
        return appMapper.getAppById(appId);
    }

    @Override
    public void deleteAppRightByAppIdsAndUserId(List<App> apps, Integer userId) {
        appMapper.deleteAppRightByAppIdsAndUserId(apps, userId);
    }

    @Override
    public List<Integer> getUserIdsByAppId(Integer appId) {
        return appMapper.findUserIdsByAppId(appId);
    }

    @Override
    public int grant(Integer appId, Integer[] userIds) {
        appMapper.deleteUserAppRight(appId, PropertiesUtil.testAccountIds);
        if (userIds != null) {
            return appMapper.insertUserAppRight(appId, userIds);
        } else {
            return 0;
        }
    }

	@Override
	public App selectByPrimaryKey(Integer appId) {
		return appMapper.selectByPrimaryKey(appId);
	}

}

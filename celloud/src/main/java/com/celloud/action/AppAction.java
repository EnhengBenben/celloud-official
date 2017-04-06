package com.celloud.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.constants.AppConstants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.IconConstants;
import com.celloud.constants.UserResource;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.AppVO;
import com.celloud.model.mysql.Classify;
import com.celloud.model.mysql.Company;
import com.celloud.model.mysql.Price;
import com.celloud.model.mysql.Screen;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppCommentService;
import com.celloud.service.AppService;
import com.celloud.service.ClassifyService;
import com.celloud.service.CompanyService;
import com.celloud.service.PriceService;
import com.celloud.service.ScreenService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.Response;

/**
 * 应用市场
 *
 * @author han
 * @date 2016年1月5日 下午4:01:20
 */
@Controller
@RequestMapping("app")
public class AppAction {
    Logger log = LoggerFactory.getLogger(AppAction.class);
    @Resource
    private AppService appService;
    @Resource
    private ClassifyService classifyService;
    @Resource
    private ScreenService screenService;
    @Resource
    private CompanyService companyService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private AppCommentService appCommentService;

	@ResponseBody
	@RequestMapping("toAddApp")
	public List<App> toAddApp(Integer userId) {
		Integer loginId = ConstantsData.getLoginUserId();
		List<App> all = appService.getRightAppList(null, loginId);
		List<App> have = appService.getRightAppList(loginId, userId);
		all.removeAll(have);
		return all;
	}
	@ResponseBody
	@RequestMapping("grantApp")
	public ResponseEntity<Response> grantApp(Integer userId, Integer[] apps) {
		appService.addUserAppRight(userId, apps, ConstantsData.getLoginUserId());
		return ResponseEntity.ok(new Response("200", "追加成功"));
	}

	@ResponseBody
	@RequestMapping("toRemoveApp")
	public List<App> toRemoveApp(Integer userId) {
		Integer authFrom = ConstantsData.getLoginUserId();
		return appService.getRightAppList(authFrom, userId);
	}

	@ResponseBody
	@RequestMapping("deleteApp")
	public ResponseEntity<Response> deleteApp(Integer userId, Integer[] apps) {
		appService.appDeleteByAuthFrom(userId, apps);
		return ResponseEntity.ok(new Response("200", "删除成功"));
	}

	@ResponseBody
	@RequestMapping("getRightAppList")
	public List<App> getRightAppList() {
		return appService.getRightAppList(null, ConstantsData.getLoginUserId());
	}

    @ActionLog(value = "获取用户已经运行过数据的APP列表（项目报告页面检索框用）", button = "报告管理")
    @ResponseBody
    @RequestMapping("getRanAPP")
	public PageList<Map<String, String>> getRanAPP() {
		PageList<Map<String, String>> pageList = new PageList<>();
		List<Map<String, String>> list = appService.getRanAPP(ConstantsData.getLoginUserId());
		pageList.setDatas(list);
		return pageList;
    }

    /**
     * 
     * @description 更新app的是否添加状态
     * @author miaoqi
     * @date 2017年3月24日 下午2:07:18
     * @param appId
     * @param isAdd
     * @return
     */
    @RequestMapping(value = "addOrRemoveApp", method = RequestMethod.PUT)
    public ResponseEntity<Void> addOrRemoveApp(Integer appId) {
        Integer role = ConstantsData.getLoginUser().getRole();
        if (role.intValue() == 5) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Integer userId = ConstantsData.getLoginUserId();
        if (appId == null) {
            log.error("用户 {} 更新app的添加状态, 参数错误, appId = {}, isAdd = {}", userId, appId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        log.info("用户 {} 更新app的添加状态 appId = {}, isAdd = {}", userId, appId);
        Boolean flag = appService.updateUserAppRight(userId, appId);
        if (!flag) {
            log.error("用户 {} 更新app的添加状态失败 appId = {}, isAdd = {}", userId, appId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        log.info("用户 {} 更新app的添加状态成功 appId = {}, isAdd = {}", userId, appId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "{appId}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> get(@PathVariable("appId") Integer appId) {
        Integer userId = ConstantsData.getLoginUserId();
        Map<String, Object> result = new HashMap<String, Object>();
        log.info("用户 {} 获取app详情, appId = {}", userId, appId);
        // 1. 根据id查询App信息
        App app = appService.get(appId);
        if (app == null) {
            log.error("用户 {} 获取app详情失败 appId = {}", appId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        result.put("app", app);
        // 2. 根据appId查询分类
        List<Classify> classifys = classifyService.listClassifyByAppId(app.getAppId());
        if (classifys != null && !classifys.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            classifys.forEach(c -> {
                sb.append(c.getClassifyName() + ",");
            });
            result.put("classifys", sb.toString().substring(0, sb.toString().length() - 1));
        }
        // 3. 根据companyId查询app的所有者
        Company company = companyService.selectByPrimaryKey(app.getCompanyId());
        if (company != null) {
            result.put("company", company.getCompanyName());
        }
        // 4. 根据appId获取价格
        Price price = priceService.getPriceByApp(app.getAppId());
        if (price != null) {
            result.put("price", price.getPrice());
        }
        // 5. 根据appId获取轮播图
        List<Screen> screens = screenService.getScreenByAppId(app.getAppId());
        if (screens != null && !screens.isEmpty()) {
            String[] screenArray = new String[screens.size()];
            for (int i = 0; i < screens.size(); i++) {
                screenArray[i] = screens.get(i).getScreenName();
            }
            result.put("screens", screenArray);
        }
        // 6. 查询用户是否拥有该app权限
        Map<String, Integer> map = appService.getUserAppRight(userId, appId);
        if (map != null && !map.isEmpty()) {
            result.put("isAdd", map.get("isAdd"));
        }
        // 7. 获取评分
        Map<String, Map<String, Integer>> countScore = appCommentService.countScore(appId);
        if (countScore != null && !countScore.isEmpty()) {
            result.put("countScore", countScore);
        }
        int totalCount = 0;
        for (Map.Entry<String, Map<String, Integer>> entry : countScore.entrySet()) {
            totalCount += entry.getValue().get("count");
        }
        result.put("totalCount", totalCount);
        // 8. 获取平均评分
        String avgScore = appCommentService.avgScore(appId);
        if (StringUtils.isNotBlank(avgScore)) {
            result.put("avgScore", avgScore);
        }
        // 9. 获取评论
        Map<String, Object> userComment = appCommentService.getAppComment(userId, appId);
        if (userComment != null && !userComment.isEmpty()) {
            result.put("userComment", userComment);
        }

        log.info("用户 {} 获取app详情成功, appId = {}", userId, appId);
        return ResponseEntity.ok(result);
    }

    /**
     * 
     * @description 获取精选app
     * @author miaoqi
     * @date 2017年3月28日 下午6:35:11
     * @return
     */
    @RequestMapping(value = "classic", method = RequestMethod.GET)
    public ResponseEntity<List<AppVO>> classic(
            @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        Integer userId = ConstantsData.getLoginUserId();
        App app = new App();
        app.setClassic(1);
        Page page = new Page(1, pageSize);
        PageList<AppVO> pageList = appService.selectBySelective(page, app, userId);
        if (pageList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        log.info("用户 {} 根据非空条件成功获取app列表", userId);
        return ResponseEntity.ok(pageList.getDatas());
    }

    /**
     * 
     * @description 获取推荐app
     * @author miaoqi
     * @date 2017年3月28日 下午6:43:18
     * @return
     */
    @RequestMapping(value = "recommend", method = RequestMethod.GET)
    public ResponseEntity<List<AppVO>> recommend(
            @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        Integer userId = ConstantsData.getLoginUserId();
        App app = new App();
        app.setFlag(1);
        Page page = new Page(1, pageSize);
        PageList<AppVO> pageList = appService.selectBySelective(page, app, userId);
        if (pageList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        log.info("用户 {} 根据非空条件成功获取app列表", userId);
        return ResponseEntity.ok(pageList.getDatas());
    }

    /**
     * 
     * @description 根据分类id获取app列表
     * @author miaoqi
     * @date 2017年3月24日 上午11:16:14
     * @param classifyId
     */
    @RequestMapping(value = "classifys/{classifyId}", method = RequestMethod.GET)
    public ResponseEntity<PageList<AppVO>> listByClassifyId(Page page,
            @PathVariable("classifyId") Integer classifyId) {
        Integer userId = ConstantsData.getLoginUserId();
        if (classifyId == null) {
            log.error("用户 {} 根据classifyId获取app列表 classifyId = {}", userId, classifyId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info("用户 {} 根据classifyId获取app列表 classifyId = {}", userId, classifyId);
        PageList<AppVO> list = appService.listByClassifyId(page, classifyId, userId);
        if (list == null) {
            log.error("用户 {} 根据classifyId没有获取到app列表 classifyId = {}", userId, classifyId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        log.info("用户 {} 根据classifyId成功获取app列表 classifyId = {}", userId, classifyId);
        return ResponseEntity.ok(list);
    }

    /**
     * 
     * @description 首页一级产品列表
     * @author miaoqi
     * @date 2017年4月5日 下午2:21:28
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "classifys", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> classifys(
            @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        Integer userId = ConstantsData.getLoginUserId();
        Page page = new Page(1, pageSize);
        List<Classify> classifys = classifyService.listClassifyByPid(0);
        if (classifys == null || classifys.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        classifys.forEach(c -> {
            PageList<AppVO> classifyPageList = appService.listByClassifyPId(page, c.getClassifyId(),
                    userId);
            if (classifyPageList != null) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("appList", classifyPageList.getDatas());
                map.put("classifyName", c.getClassifyName());
                map.put("classifyId", c.getClassifyId());
                result.add(map);
            }
        });
        return ResponseEntity.ok(result);
    }

	/**
	 * 获取用户的产品
	 * 
	 * @return
	 * @author lin
	 * @date 2016年9月9日下午1:53:11
	 */
	@RequestMapping("getProduct")
	@ResponseBody
	public Map<String, Object> getProduct() {
		Integer userId = ConstantsData.getLoginUserId();
		Map<String, Object> map = new HashMap<>();
		List<App> appList = appService.getMyAppList(userId);
        int appNum = 0;
		int bsiNum = 0;
		for (App app : appList) {
            if (AppConstants.BACTIVE_GROUP.contains(app.getAppId())) {
				map.put("app" + app.getAppId(), app.getAppId());
				bsiNum++;
			} else if (app.getAttribute() == AppConstants.PRIVATE) {
				appNum++;
			}
		}
		if (appNum == 0 && bsiNum == 1) {
			map.put("onlyBSI", true);
		} else {
            map.put("onlyBSI", false);
        }
		boolean hasUpload = SecurityUtils.getSubject().isPermitted(UserResource.ROCKYUPLOAD);
		map.put("rockyupload", hasUpload);
		if (!hasUpload) {
			map.put("rockyreport", SecurityUtils.getSubject().isPermitted(UserResource.ROCKYREPORT));
		}
		return map;
	}

    @ActionLog(value = "用户添加APP，即允许在数据管理运行中显示", button = "添加APP")
    @ResponseBody
    @RequestMapping("addApp")
    public Object userAddApp(Integer paramId, HttpServletResponse response) {
        Integer role = ConstantsData.getLoginUser().getRole();
        if (role.intValue() == 5) {
            return -1;
        }
        log.info("用户{}添加APP{}", ConstantsData.getLoginUserName(), paramId);
        Integer userId = ConstantsData.getLoginUserId();
        return appService.userAddApp(userId, paramId);
    }

    @ActionLog(value = "用户取消添加APP，即不允许在数据管理运行中显示", button = "删除APP")
    @ResponseBody
    @RequestMapping("removeApp")
    public Object userRemoveApp(Integer paramId, HttpServletResponse response) {
        Integer role = ConstantsData.getLoginUser().getRole();
        if (role.intValue() == 5) {
            return -1;
        }
        log.info("用户{}取消添加APP{}", ConstantsData.getLoginUserName(), paramId);
        Integer userId = ConstantsData.getLoginUserId();
        return appService.userRemoveApp(userId, paramId);
    }
    
    /**
     * 获取已保存app图标
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> appImage(String file) throws IOException {
		String path = IconConstants.getAppPath(file);
        File targetFile = new File(path);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
    
    /**
     * 获取已保存app截图
     * 
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "screen", method = RequestMethod.GET)
    public ResponseEntity<byte[]> appScreen(String file) throws IOException {
		String path = IconConstants.getScreenPath(file);
        File targetFile = new File(path);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }

    /**
     * 获取推荐APP大图
     * 
     * @param file
     * @return
     * @throws IOException
     * @author leamo
     * @date 2017年3月30日 下午1:35:15
     */
    @RequestMapping(value = "recommendMax", method = RequestMethod.GET)
    public ResponseEntity<byte[]> recommendMax(String file) throws IOException {
        String path = IconConstants.getRecommendMaxPath(file);
        File targetFile = new File(path);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }

    /**
     * 获取推荐APP小图
     * 
     * @param file
     * @return
     * @throws IOException
     * @author leamo
     * @date 2017年3月30日 下午1:35:15
     */
    @RequestMapping(value = "recommendMin", method = RequestMethod.GET)
    public ResponseEntity<byte[]> recommendMin(String file) throws IOException {
        String path = IconConstants.getRecommendMinPath(file);
        File targetFile = new File(path);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(targetFile), null, HttpStatus.OK);
    }
}

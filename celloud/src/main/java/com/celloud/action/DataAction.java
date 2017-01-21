package com.celloud.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.AppDataListType;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.IconConstants;
import com.celloud.constants.SparkPro;
import com.celloud.constants.TaskPeriod;
import com.celloud.model.DataFileEditForm;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Task;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.AppService;
import com.celloud.service.DataService;
import com.celloud.service.ExpensesService;
import com.celloud.service.ProjectService;
import com.celloud.service.ReportService;
import com.celloud.service.RunService;
import com.celloud.service.TaskService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.AppSubmitUtil;
import com.celloud.utils.Response;

/**
 * 数据管理
 * 
 * @author liuqx
 * @date 2015-12-30 下午4:08:06
 */

@Controller
@RequestMapping("data")
public class DataAction {

	Logger logger = LoggerFactory.getLogger(DataAction.class);
	@Resource
	private DataService dataService;
	@Resource
	private AppService appService;
	@Resource
	private ProjectService projectService;
	@Resource
	private ReportService reportService;
	@Resource
	private TaskService taskService;
    @Resource
    ExpensesService expenseService;
	@Resource
	RunService runService;
	private static final Response DELETE_DATA_FAIL = new Response("归档数据失败");

	/**
	 * 检索某个项目下的所有数据
	 * 
	 * @param projectId
	 * @return
	 * @date 2016-1-9 上午3:43:01
	 */
	@ActionLog(value = "检索某个项目下的所有数据（用于数据报告页面右侧菜单）", button = "数据报告")
	@RequestMapping("getDatasInProject")
	@ResponseBody
	public List<DataFile> getDatasInProject(Integer projectId) {
		return dataService.getDatasInProject(projectId);
	}

    /**
     * 
     * @description 根据项目id从TbTask中获取运行结束的数据报告
     * @author miaoqi
     * @date 2016年10月8日下午5:05:38
     *
     * @param projectId
     *            项目id
     * @return 文件集合json
     *
     */
    @ActionLog(value = "从tb_task中根据project_id检索数据", button = "报告管理/查看数据报告")
    @RequestMapping("getDataFromTbTask")
    @ResponseBody
    public List<Map<String, Object>> getDataFromTbTask(Integer projectId) {
        logger.info("项目报告中加载右侧浮动窗 projectId = {}", projectId);
        List<Map<String, Object>> dataList = null;
        // 从tb_task中加载数据
        dataList = this.dataService.getDataFileFromTbTask(projectId);
        if (null == dataList || dataList.isEmpty()) { // tb_task中查找不到数据代表是老数据采用老的方法加载数据
            dataList = this.dataService.getDatasMapInProject(projectId);
        }
        return dataList;
    }

	/**
	 * 获取全部数据列表
	 * 
	 * @param session
	 * @param page
	 * @param size
	 * @return
	 */
	@ActionLog(value = "打开数据管理页面", button = "数据管理")
	@RequestMapping("dataAllList.action")
	public ModelAndView dataAllList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int size) {
		ModelAndView mv = new ModelAndView("data/data_list");
		Page pager = new Page(page, size);
		PageList<DataFile> dataList = dataService.dataAllList(pager, ConstantsData.getLoginUserId());
		mv.addObject("dataList", dataList);
		logger.info("用户{}打开数据管理", ConstantsData.getLoginUserName());
		return mv;
	}

    @RequestMapping("dataPageList")
    @ResponseBody
    public PageList<DataFile> dataPageList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page pager = new Page(page, size);
        return dataService.dataAllList(pager, ConstantsData.getLoginUserId());
    }

	/**
	 * 根据条件获取数据列表
	 * 
	 * @param session
	 * @param page
	 *            当前页
	 * @param size
	 *            每页行数
	 * @param condition
	 *            检索条件
	 * @param sort
	 *            排序字段 0:create_date 1:file_name
	 * @param sortType
	 *            排序类型
	 * @return
	 */
	@ActionLog(value = "条件检索数据列表", button = "数据管理搜索/分页")
	@RequestMapping("dataPageListCondition")
	@ResponseBody
	public PageList<DataFile> dataPageListCondition(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String condition,
            @RequestParam(defaultValue = "0") int sort,
			@RequestParam(defaultValue = "desc") String sortDateType,
            @RequestParam(defaultValue = "asc") String sortNameType,
            @RequestParam(defaultValue = "asc") String sortRun,
            @RequestParam(defaultValue = "desc") String sortAnotherName) {
		Pattern p = Pattern.compile("\\_|\\%|\\'|\"");
		Matcher m = p.matcher(condition);
		StringBuffer con_sb = new StringBuffer();
		while (m.find()) {
			String rep = "\\\\" + m.group(0);
			m.appendReplacement(con_sb, rep);
		}
		m.appendTail(con_sb);
		Page pager = new Page(page, size);
		PageList<DataFile> dataList = dataService.dataLists(pager, ConstantsData.getLoginUserId(), con_sb.toString(),
                sort, sortDateType, sortNameType, sortAnotherName, sortRun);

		logger.info("用户{}根据条件检索数据列表", ConstantsData.getLoginUserName());
		return dataList;
	}

	/**
	 * 根据条件获取数据列表
	 * 
	 * @param session
	 * @param page
	 *            当前页
	 * @param size
	 *            每页行数
	 * @param condition
	 *            检索条件
	 * @param sort
	 *            排序字段 0:create_date 1:批次 2:file_name
	 * @param sortType
	 *            排序类型
	 * @return
	 */
	@ActionLog(value = "条件检索bsi数据列表", button = "我的搜索/分页")
    @RequestMapping("bsi/dataPageQuery")
    @ResponseBody
    public Map<String, Object> bsiDataPageQuery(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size, String condition,
            @RequestParam(defaultValue = "0") Integer sort,
			@RequestParam(defaultValue = "desc") String sortDate, @RequestParam(defaultValue = "asc") String sortBatch,
            @RequestParam(defaultValue = "asc") String sortName,
            Integer appId) {
        logger.info("用户 {} 根据条件检索数据列表", ConstantsData.getLoginUserId());
        Map<String, Object> dataMap = new HashMap<String, Object>();
		Page pager = new Page(page, size);
        PageList<DataFile> dataList = dataService.dataListByAppId(pager,
                ConstantsData.getLoginUserId(), appId,
                condition, sort, sortDate, sortName, sortBatch);
        dataMap.put("pageList", dataList);
        return dataMap;
	}

    @ActionLog(value = "条件检索bsi数据列表", button = "我的搜索/分页")
    @RequestMapping("bsiDataList")
    public ModelAndView bsiDataList(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size, String condition,
            @RequestParam(defaultValue = "0") Integer sort, @RequestParam(defaultValue = "desc") String sortDate,
            @RequestParam(defaultValue = "asc") String sortBatch, @RequestParam(defaultValue = "asc") String sortName) {
        ModelAndView mv = new ModelAndView("bsi/data_list");
        Page pager = new Page(page, size);
        PageList<DataFile> dataList = dataService.dataListByAppId(pager, ConstantsData.getLoginUserId(),
                IconConstants.APP_ID_BSI, condition, sort, sortDate, sortName, sortBatch);
        mv.addObject("pageList", dataList);
        logger.info("用户{}根据条件检索数据列表", ConstantsData.getLoginUserName());
        return mv;
    }

    // XXX 百菌探报证结束后删除（完全拷贝的↑）
    @RequestMapping("/baozheng/bsiDataList")
    public ModelAndView bsiDataList1(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size, String condition,
            @RequestParam(defaultValue = "0") Integer sort,
            @RequestParam(defaultValue = "desc") String sortDate,
            @RequestParam(defaultValue = "asc") String sortBatch,
            @RequestParam(defaultValue = "asc") String sortName) {
        ModelAndView mv = new ModelAndView("bsi/baozheng/data_list");
        Page pager = new Page(page, size);
        PageList<DataFile> dataList = dataService.dataListByAppId(pager,
                ConstantsData.getLoginUserId(), IconConstants.APP_ID_BSI,
                condition, sort, sortDate, sortName, sortBatch);
        mv.addObject("pageList", dataList);
        logger.info("用户{}根据条件检索数据列表", ConstantsData.getLoginUserName());
        return mv;
    }

	/**
	 * 
	 * @author miaoqi
	 * @description 用户查看乳腺癌数据列表页面
	 * @date 2017年1月13日上午11:13:49
	 *
	 * @param page
	 * @param size
	 * @param sample
	 * @param condition
	 * @param sidx
	 * @param sord
	 * @return
	 */
	@ActionLog(value = "打开rocky数据页面", button = "数据")
	@RequestMapping("rocky/list")
	@ResponseBody
	public Map<String, Object> rockyDataAllList(@RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "20") int size, String sample, String condition,
	        @RequestParam(defaultValue = "createDate") String sortField,
	        @RequestParam(defaultValue = "desc") String sortType) {
		Page pager = new Page(page, size);
		PageList<Map<String, Object>> dataList = dataService.filterRockyList(pager, sample, condition, sortField,
		        sortType);
		Integer userId = ConstantsData.getLoginUserId();
		Map<String, Object> periodMap = taskService.findTaskPeriodNum(IconConstants.APP_ID_ROCKY, userId);
		List<String> batchList = dataService.getBatchList(userId);
		periodMap.put("uploaded", batchList.size());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("dataList", dataList);
		dataMap.put("periodMap", periodMap);
		logger.info("用户{}打开乳腺癌数据", ConstantsData.getLoginUserName());
		return dataMap;
	}

	/**
	 * 根据数据编号获取数据类型
	 * 
	 * @param dataIds
	 * @return -1:所选类型大于一种
	 */
	@ActionLog(value = "获取数据类型", button = "运行")
	@RequestMapping("getFormatByDataIds.action")
	@ResponseBody
	public Integer getFormatByDataIds(String dataIds) {
		Integer result = 0;
		if (dataIds == null || dataIds.equals(""))
			return result;
		result = dataService.getFormatByIds(dataIds);
		logger.info("用户{}获取{}数据类型", ConstantsData.getLoginUserName(), dataIds);
		return result;
	}

	/**
	 * 根据数据类型获取可运行的app
	 * 
	 * @param formatId
	 * @return
	 */
	@ActionLog(value = "获取可运行的app", button = "运行")
	@RequestMapping("getRunApp.action")
	@ResponseBody
	public List<App> getRunApp(@RequestParam(defaultValue = "0") Integer formatId) {
		List<App> apps = appService.findAppsByFormat(ConstantsData.getLoginUserId(), formatId);
		logger.info("用户{}获取可运行数据类型{}的app", ConstantsData.getLoginUserName(), formatId);
		return apps;
	}

	/**
	 * 验证正在运行某APP的数据
	 * 
	 * @param dataIds
	 * @param appId
	 * @return
	 */
	@ActionLog(value = "验证数据是否正在运行APP", button = "运行")
	@RequestMapping("checkDataRunningApp.action")
	@ResponseBody
	public List<Integer> checkDataRunningApp(String dataIds, Integer appId) {
		List<Integer> dataIdList = new ArrayList<>();
		if (dataIds == null || dataIds.equals(""))
			return dataIdList;
		dataIdList = dataService.findRunningAppData(dataIds, appId);
		logger.info("用户{}验证数据{}是否正在运行APP{}", ConstantsData.getLoginUserName(), dataIds, appId);
		return dataIdList;
	}

	/**
	 * 删除数据
	 * 
	 * @param dataIds
	 * @return
	 * @author leamo
	 * @date 2016-1-10 下午9:49:10
	 */
	@ActionLog(value = "删除所选数据", button = "数据删除")
	@RequestMapping("delete.action")
	@ResponseBody
	public Response delete(String dataIds) {
		Integer result = 0;
		if (dataIds != null && !dataIds.equals(""))
			result = dataService.delete(dataIds);
		logger.info("用户{}删除数据{}{}", ConstantsData.getLoginUserName(), dataIds, result);
		return result > 0 ? Response.SUCCESS_FILED() : DELETE_DATA_FAIL;
	}

	/**
	 * 修改数据信息页面
	 * 
	 * @param dataId
	 * @return
	 * @author leamo
	 * @date 2016-1-10 下午10:04:24
	 */
	@ActionLog(value = "打开分别修改数据列表Modal", button = "数据编辑")
	@RequestMapping("toEachEditDatas.action")
	public ModelAndView toEachEditDatas(String dataIds) {
		ModelAndView mv = new ModelAndView("data/data_all_update");
		List<DataFile> dataList = dataService.findDatasById(dataIds);
		mv.addObject("dataList", dataList);
		logger.info("用户{}打开分别修改数据列表Modal", ConstantsData.getLoginUserName(), dataIds);
		return mv;
	}

	@ActionLog(value = "跳转数据编辑", button = "数据编辑")
	@RequestMapping("toEditData")
	@ResponseBody
	public Map<String, Object> toEditData(Integer dataId) {
		logger.info("用户{}打开修改数据弹窗，id={}", ConstantsData.getLoginUserName(), dataId);
		Map<String, Object> map = new HashMap<>();
		DataFile file = dataService.getDataById(dataId);
		List<App> apps = appService.findAppsByFormat(ConstantsData.getLoginUserId(), file.getFileFormat());
		map.put("file", file);
		map.put("appList", apps);
		return map;
	}

	@ActionLog(value = "数据编辑", button = "数据编辑")
	@RequestMapping("updateDataAndTag")
	@ResponseBody
	public Response updateDataAndTag(DataFile data) {
		return dataService.updateDataAndTag(data) == 1 ? Response.SUCCESS_UPDATE() : Response.FAIL();
	}

	/**
	 * 获取物种列表
	 * 
	 * @return
	 * @author leamo
	 * @date 2016-1-10 下午10:21:13
	 */
	@ActionLog(value = "获取物种列表", button = "数据编辑")
	@RequestMapping("getStrainList.action")
	@ResponseBody
	public List<Map<String, String>> getStrainList() {
		List<Map<String, String>> mapList = dataService.getStrainList(ConstantsData.getLoginUserId());
		return mapList;
	}

	/**
	 * 批量修改数据
	 * 
	 * @param dataIds
	 * @param data
	 * @return
	 * @author leamo
	 * @date 2016-1-10 下午11:04:34
	 */
	@ActionLog(value = "批量保存修改数据", button = "保存批量编辑")
	@RequestMapping("batchEditDataByIds.action")
	@ResponseBody
	public Integer batchEditDataByIds(String dataIds, DataFile data) {
		Integer result = 0;
		if (dataIds != null && !dataIds.equals(""))
			result = dataService.updateDataByIds(dataIds, data);
		logger.info("用户{}批量修改数据{}", ConstantsData.getLoginUserName(), dataIds);
		return result;
	}

	/**
	 * 批量修改数据
	 * 
	 * @param dataIds
	 * @param data
	 * @return
	 * @author leamo
	 * @date 2016-1-10 下午11:04:34
	 */
	@ActionLog(value = "分别保存所修改的数据", button = "保存单个编辑")
	@RequestMapping("eachEditDataByIds.action")
	@ResponseBody
	public Integer eachEditDataByIds(DataFileEditForm dataFileEditForm) {
		Integer result = dataService.updateDatas(dataFileEditForm.getDataList());
		logger.info("用户{}分别修改{}个数据", ConstantsData.getLoginUserName(), result);
		return result;
	}

	@ActionLog(value = "runWithProject", button = "运行")
	@RequestMapping("runWithProject")
	@ResponseBody
	public Response runWithProject(String dataIds) {
		Integer userId = ConstantsData.getLoginUserId();
		return runService.run(userId, dataIds);
	}

	/**
	 * 重新运行
	 * 
	 * @param dataIds
	 * @param command
	 * @return
	 * @author leamo
	 * @date 2016年5月24日 下午6:17:04
	 */
	@ActionLog(value = "重新运行数据", button = "百菌探重复运行")
	@RequestMapping("reRun")
	@ResponseBody
	public String reRun(String dataKey, Integer appId, Integer projectId) {
		Task task = taskService.findTaskDataAppPro(dataKey, appId, projectId);
		int runningNum = taskService.findRunningNumByAppId(AppDataListType.SPARK);
		if (task != null) {
			if (task.getPeriod() == 1) {
				String param = SparkPro.TOOLSPATH + task.getUserId() + "/" + appId + " ProjectID" + projectId;
				String killCommand = SparkPro.SPARKKILL + " " + param;
				AppSubmitUtil.ssh("spark", killCommand, false);
			}
			reportService.deleteBSIReport(dataKey, projectId, appId);
			if (runningNum < SparkPro.MAXTASK) {
				AppSubmitUtil.ssh("spark", task.getCommand(), false);
				taskService.updateToRunning(task.getTaskId());
				logger.info("{}重复运行任务：{}", task.getUserId(), task.getTaskId());
			} else {
				task.setPeriod(TaskPeriod.WAITTING);
				task.setUpdateDate(new Date());
				taskService.updateTask(task);
				logger.info("{}排队重新运行任务：{}", task.getUserId(), task.getTaskId());
			}
			return "true";
		}
		return "reRun failed";
	}
}

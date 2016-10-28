package com.celloud.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.ExperimentState;
import com.celloud.model.mysql.DataFile;
import com.celloud.model.mysql.Experiment;
import com.celloud.model.mysql.ExperimentDict;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.DataService;
import com.celloud.service.ExperimentDictService;
import com.celloud.service.ExperimentService;
import com.celloud.utils.ActionLog;

@Controller
@RequestMapping("experiment")
public class ExperimentAction {
	@Resource
	private ExperimentService es;
	@Resource
	private ExperimentDictService eds;
	@Resource
	private DataService ds;

	/**
	 * 初始化实验管理模块，获取实验流程动态菜单
	 * 
	 * @return
	 * @author lin
	 * @date 2016年3月30日下午5:13:55
	 */
	@ActionLog(value = "初始化实验管理模块", button = "实验管理")
	@RequestMapping("getExperimentDict")
	public ModelAndView getExperimentDict() {
		List<ExperimentDict> list = eds.getExperimentDictList();
		return new ModelAndView("experiment/experiment_main").addObject("list", list);
	}

	/**
	 * 分页获取doing状态的实验流程
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @author lin
	 * @date 2016年3月29日下午4:11:01
	 */
	@ActionLog(value = "获取doing状态的实验流程", button = "实验管理／Doing／分页")
	@RequestMapping("getPageList")
	public ModelAndView getPageList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE + "") Integer size) {
		Integer userId = ConstantsData.getLoginUserId();
		Page pager = new Page(page, size);
		PageList<Experiment> pl = es.getExperimentPageList(userId, pager);
		return new ModelAndView("experiment/experiment_doing_list").addObject("pageList", pl).addObject("dataStep",
				ExperimentState.RELAT_STEP);
	}

	/**
	 * 分页获取done状态的实验流程
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @author lin
	 * @date 2016年3月29日下午5:24:00
	 */
	@ActionLog(value = "获取done状态的实验流程", button = "Done／分页／检索")
	@RequestMapping("getDonePageList")
	public ModelAndView getDonePageList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE + "") Integer size, Integer sampleId,
			Integer methodId, Integer stepId, String start, String end) {
		Integer userId = ConstantsData.getLoginUserId();
		Page pager = new Page(page, size);
		PageList<Experiment> pl = es.getExpDonePageList(userId, pager, sampleId, methodId, stepId, start, end);
		return new ModelAndView("experiment/experiment_done_list").addObject("pageList", pl);
	}

	/**
	 * 跳转新建实验流程
	 * 
	 * @return
	 * @author lin
	 * @date 2016年3月30日上午10:18:41
	 */
	@ActionLog(value = "跳转新建实验流程", button = "新建")
	@RequestMapping("toAddExp")
	public ModelAndView toAddExp() {
		List<ExperimentDict> list = eds.getExperimentDictList();
		return new ModelAndView("experiment/experiment_add").addObject("list", list);
	}
	
	/**
	 * 根据别名获取数据
	 * 
	 * @param anotherName
	 * @return
	 * @author lin
	 * @date 2016年4月6日下午4:29:47
	 */
	@RequestMapping("getDataByAnotherName")
	public ModelAndView getDataByAnotherName(String anotherName) {
		Integer userId = ConstantsData.getLoginUserId();
		List<DataFile> list = ds.getDataByAnotherName(userId, anotherName);
		return new ModelAndView("experiment/experiment_data_list").addObject("list", list);
	}

	/**
	 * 校验实验编号是否存在
	 * 
	 * @param num
	 * @return
	 * @author lin
	 * @date 2016年4月1日上午11:28:33
	 */
	@ActionLog(value = "校验实验编号是否存在", button = "新增／编辑保存")
	@ResponseBody
	@RequestMapping("checkNumber")
	public Integer checkNumber(String num) {
		Integer userId = ConstantsData.getLoginUserId();
		return es.checkoutNumber(userId, num);
	}

	/**
	 * 新建实验流程
	 * 
	 * @param exp
	 * @return
	 * @author lin
	 * @date 2016年3月29日下午4:12:02
	 */
	@ActionLog(value = "新建实验流程", button = "新增保存")
	@ResponseBody
	@RequestMapping("addExperiment")
	public int addExperiment(Experiment exp) {
		exp.setState(ExperimentState.OPEN);
		exp.setUserId(ConstantsData.getLoginUserId());
		return es.insertSelective(exp);
	}

	/**
	 * 跳转编辑实验流程
	 * 
	 * @param expId
	 * @return
	 * @author lin
	 * @date 2016年4月1日下午3:13:59
	 */
	@ActionLog(value = "跳转编辑实验流程", button = "编辑")
	@RequestMapping("toEditExp")
	public ModelAndView toEditExp(Integer expId) {
		List<ExperimentDict> list = eds.getExperimentDictList();
		Experiment experiment = es.selectByPrimaryKey(expId);
		return new ModelAndView("experiment/experiment_edit").addObject("list", list).addObject("experiment",
				experiment).addObject("dataStep", ExperimentState.RELAT_STEP);
	}

	/**
	 * 编辑实验流程
	 * 
	 * @param exp
	 * @return
	 * @author lin
	 * @date 2016年3月30日下午2:46:48
	 */
	@ActionLog(value = "编辑实验流程", button = "关闭实验／保存")
	@ResponseBody
	@RequestMapping("updateExperiment")
	public int updateExperiment(Experiment exp) {
		return es.updateByPrimaryKeySelective(exp);
	}

}

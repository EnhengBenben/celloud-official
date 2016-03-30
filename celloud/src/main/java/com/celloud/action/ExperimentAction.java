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
import com.celloud.model.mysql.Experiment;
import com.celloud.model.mysql.ExperimentDict;
import com.celloud.page.Page;
import com.celloud.page.PageList;
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

	/**
	 * 分页获取doing状态的实验流程
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @author lin
	 * @date 2016年3月29日下午4:11:01
	 */
	@ActionLog(value = "获取doing状态的实验流程", button = "实验管理／Doing")
	@RequestMapping("getPageList")
	public ModelAndView getPageList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE + "") Integer size) {
		int userId = ConstantsData.getLoginUserId();
		Page pager = new Page(page, size);
		PageList<Experiment> pl = es.getExperimentPageList(userId, pager);
		return new ModelAndView("experiment/experiment_list").addObject("pageList", pl);
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
	@ActionLog(value = "获取done状态的实验流程", button = "Done")
	@RequestMapping("getDonePageList")
	public ModelAndView getDonePageList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE + "") Integer size) {
		int userId = ConstantsData.getLoginUserId();
		Page pager = new Page(page, size);
		PageList<Experiment> pl = es.getExpDonePageList(userId, pager);
		return new ModelAndView("experiment/experiment_list").addObject("pageList", pl);
	}

	/**
	 * 新增实验流程
	 * 
	 * @param exp
	 * @return
	 * @author lin
	 * @date 2016年3月29日下午4:12:02
	 */
	@ActionLog(value = "新增实验流程", button = "新增保存")
	@ResponseBody
	@RequestMapping("addExperiment")
	public int addExperiment(Experiment exp) {
		exp.setState(ExperimentState.OPEN);
		exp.setUserId(ConstantsData.getLoginUserId());
		return es.insertSelective(exp);
	}

	/**
	 * 检索实验流程动态菜单
	 * 
	 * @return
	 * @author lin
	 * @date 2016年3月30日上午10:18:41
	 */
	@ActionLog(value = "获取实验流程动态菜单", button = "跳转新增／跳转编辑")
	@RequestMapping("toAddExp")
	public ModelAndView toAddExp() {
		List<ExperimentDict> list = eds.getExperimentDictList();
		return new ModelAndView("experiment/experiment_add").addObject("list", list);
	}

	@ActionLog(value = "跳转实验流程编辑", button = "编辑")
	@RequestMapping("toEditExp")
	public ModelAndView toEditExp(Integer expId) {
		List<ExperimentDict> list = eds.getExperimentDictList();
		Experiment experiment = es.selectByPrimaryKey(expId);
		return new ModelAndView("experiment/experiment_edit").addObject("list", list).addObject("experiment",
				experiment);
	}

	/**
	 * 修改实验流程
	 * 
	 * @param exp
	 * @return
	 * @author lin
	 * @date 2016年3月30日下午2:46:48
	 */
	@ActionLog(value = "修改实验流程", button = "编辑／关闭实验")
	@ResponseBody
	@RequestMapping("updateExperiment")
	public int updateExperiment(Experiment exp) {
		return es.updateByPrimaryKeySelective(exp);
	}

}

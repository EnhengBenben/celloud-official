package com.celloud.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.Constants;
import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.Experiment;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ExperimentService;

@Controller
@RequestMapping("experiment")
public class ExperimentAction {
	@Resource
	private ExperimentService es;

	/**
	 * 分页获取doing状态的实验流程
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @author lin
	 * @date 2016年3月29日下午4:11:01
	 */
	@RequestMapping("getPageList")
	public ModelAndView getPageList(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = Constants.DEFAULT_PAGE_SIZE + "") Integer size) {
		int userId = ConstantsData.getLoginUserId();
		Page pager = new Page(page, size);
		PageList<Experiment> pl = es.getExperimentPageList(userId, pager);
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
	@ResponseBody
	@RequestMapping("addExperiment")
	public int addExperiment(Experiment exp){
		return es.insertSelective(exp);
	}
}

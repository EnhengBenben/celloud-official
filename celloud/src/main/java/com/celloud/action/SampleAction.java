package com.celloud.action;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.AppConstants;
import com.celloud.constants.ConstantsData;
import com.celloud.constants.SampleExperState;
import com.celloud.model.mysql.Sample;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.SampleService;
import com.celloud.utils.ActionLog;

/**
 * 样品管理
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2016年6月20日 下午1:53:13
 */
@Controller
@RequestMapping("sample")
public class SampleAction {
	Logger logger = LoggerFactory.getLogger(SampleAction.class);
	@Resource
	private SampleService sampleService;

	@ActionLog(value = "获取所有未保存样品列表", button = "样品")
	@RequestMapping("{app}/sampleList")
	public ModelAndView sampleList(@PathVariable String app) {
		String view = "bsi/sample_list";
		if ("rocky".equals(app)) {
			view = "rocky/sample/sample_main";
		}
		ModelAndView mv = new ModelAndView(view);
		List<Sample> samples = sampleService.allUnaddSample(ConstantsData.getLoginUserId());
		return mv.addObject("samples", samples);
	}

	@ActionLog(value = "新增样品", button = "样品输入框")
	@RequestMapping("{app}/addSample")
	@ResponseBody
	public Integer addSample(@PathVariable String app, String sampleName) {
		Boolean check = sampleService.checkSample(sampleName, ConstantsData.getLoginUserId());
		if (check) {
			return 2; // 检测样品已添加
		}
		return sampleService.saveSample(sampleName, ConstantsData.getLoginUserId());
	}

	@ActionLog(value = "提交暂存的样品列表", button = "提交样品")
	@RequestMapping("{app}/commitSamples")
	@ResponseBody
	public Integer commitSamples(@PathVariable String app, Integer[] sampleIds) {
		List<Integer> list = sampleIds == null || sampleIds.length <= 0 ? null : Arrays.asList(sampleIds);
		Integer appId = AppConstants.APP_ID_BSI;
		if ("rocky".equals(app)) {
			appId = AppConstants.APP_ID_ROCKY;
		}
		return sampleService.commitSamples(list,appId, ConstantsData.getLoginUserId());
	}

	@ActionLog(value = "删除暂存的样本", button = "删除样品")
	@RequestMapping("bsi/deleteOne")
	@ResponseBody
	public Integer deleteOne(Integer sampleId) {
		return sampleService.delete(sampleId);
	}

	@ActionLog(value = "删除暂存的样本列表", button = "删除样品")
	@RequestMapping("{app}/deleteList")
	@ResponseBody
	public Integer deleteList(@PathVariable String app, Integer[] sampleIds) {
		List<Integer> list = sampleIds == null || sampleIds.length <= 0 ? null : Arrays.asList(sampleIds);
		return sampleService.deleteList(list);
	}

    /**
     * 获取采样中的样本列表
     * 
     * @param page
     * @param size
     * @return
     * @author leamo
     * @date 2016年9月5日 下午3:25:27
     */
    @RequestMapping("getSamplingList")
    @ResponseBody
    public PageList<Sample> getSamplingList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return getSamples(page, size, SampleExperState.SAMPLING);
    }

    /**
     * 获取入库的样本列表
     * 
     * @param page
     * @param size
     * @return
     * @author leamo
     * @date 2016年9月5日 下午3:25:27
     */
    @RequestMapping("getToStorageSamples")
    @ResponseBody
    public PageList<Sample> getToStorageSamples(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return getSamples(page, size, SampleExperState.TO_STORAGE);
    }

    /**
     * 获取提DNA的样本列表
     * 
     * @param page
     * @param size
     * @return
     * @author leamo
     * @date 2016年9月5日 下午3:25:27
     */
    @RequestMapping("getToDnaSamples")
    @ResponseBody
    public PageList<Sample> getToDnaSamples(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return getSamples(page, size, SampleExperState.TO_DNA);
    }

    /**
     * 获取样本列表
     * 
     * @param page
     * @param size
     * @param experState
     * @return
     * @author leamo
     * @date 2016年9月5日 下午3:46:28
     */
    private PageList<Sample> getSamples(int page, int size, int experState) {
        return sampleService.getSamples(new Page(page, size),
                ConstantsData.getLoginUserId(), experState);
    }

    /**
     * 扫码样本入库
     * 
     * @param sampleName
     * @return
     * @author leamo
     * @date 2016年9月5日 下午4:49:53
     */
    @RequestMapping("updateToStorageSamples")
    @ResponseBody
    public Integer updateToStorageSamples(String sampleName) {
        Sample sample = sampleService.getByNameExperState(
                ConstantsData.getLoginUserId(), sampleName,
                SampleExperState.SAMPLING);
        if (sample != null) {
            return sampleService.updateExperState(
                    ConstantsData.getLoginUserId(), SampleExperState.TO_STORAGE,
                    sample.getSampleId());
        } else {
            return 0;
        }
    }
}

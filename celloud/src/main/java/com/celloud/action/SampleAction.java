package com.celloud.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.Sample;
import com.celloud.service.SampleService;
import com.celloud.utils.ActionLog;

/**
 * 样品收集
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
    @RequestMapping("bsi/sampleList")
    public ModelAndView sampleList() {
        ModelAndView mv = new ModelAndView("bsi/sample_list");
        List<Sample> samples = sampleService
                .allUnaddSample(ConstantsData.getLoginUserId());
        return mv.addObject("samples", samples);
    }

    @RequestMapping("bsi/checkSample")
    public Integer checkSample(String sampleName) {
        return sampleService.checkSample(sampleName,
                ConstantsData.getLoginUserId());
    }

    @ActionLog(value = "新增样品", button = "样品输入框")
    @RequestMapping("bsi/addSample")
    public Integer addSample(Sample sample) {
        return sampleService.saveSample(sample, ConstantsData.getLoginUserId());
    }

    @ActionLog(value = "提交暂存的样品列表", button = "提交样品")
    @RequestMapping("bsi/commitSamples")
    public Integer commitSamples(List<Integer> sampleIds) {
        return sampleService.commitSamples(sampleIds);
    }
}

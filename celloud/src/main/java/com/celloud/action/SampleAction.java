package com.celloud.action;

import java.io.File;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.IconConstants;
import com.celloud.constants.SampleExperState;
import com.celloud.model.mysql.Sample;
import com.celloud.model.mysql.SampleStorage;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.SampleService;
import com.celloud.utils.ActionLog;
import com.celloud.utils.DateUtil;
import com.celloud.utils.ExcelUtil;
import com.celloud.utils.FileTools;
import com.celloud.utils.PropertiesUtil;

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
        List<Sample> samples = sampleService
                .allUnaddSample(ConstantsData.getLoginUserId());
        return mv.addObject("samples", samples);
    }

    // XXX 百菌探报证结束后删除（完全拷贝的↑）
    @RequestMapping("/baozheng/bsi/sampleList")
    public ModelAndView sampleList1() {
        System.out.println("-------------------------");
        ModelAndView mv = new ModelAndView("bsi/baozheng/sample_list");
        List<Sample> samples = sampleService
                .allUnaddSample(ConstantsData.getLoginUserId());
        System.out.println(samples.size());
        return mv.addObject("samples", samples);
    }

    @ActionLog(value = "新增样品", button = "样品输入框")
    @RequestMapping("{app}/addSample")
    @ResponseBody
    public Integer addSample(@PathVariable String app, String sampleName) {
        Boolean check = sampleService.checkSample(sampleName,
                ConstantsData.getLoginUserId());
        if (check) {
            return 2; // 检测样品已添加
        }
        return sampleService.saveSample(sampleName,
                ConstantsData.getLoginUserId());
    }

    @ActionLog(value = "提交暂存的样品列表", button = "提交样品")
    @RequestMapping("{app}/commitSamples")
    @ResponseBody
    public Integer commitSamples(@PathVariable String app,
            Integer[] sampleIds) {
        List<Integer> list = sampleIds == null || sampleIds.length <= 0 ? null
                : Arrays.asList(sampleIds);
        Integer appId = IconConstants.APP_ID_BSI;
        if ("rocky".equals(app)) {
            appId = IconConstants.APP_ID_ROCKY;
        }
        return sampleService.commitSamples(list, appId,
                ConstantsData.getLoginUserId());
    }
    
    @ActionLog(value = "提交采样列表", button = "采样")
    @RequestMapping("commitSampling")
    @ResponseBody
    public Integer commitSampling(Integer[] sampleIds) {
        List<Integer> list = sampleIds == null || sampleIds.length <= 0 ? null
                : Arrays.asList(sampleIds);
        return sampleService.commitSamples(list,
                ConstantsData.getLoginUserId());
    }

    @ActionLog(value = "获取样品订单信息", button = "提交订单")
    @RequestMapping("getSampleOrderInfo")
    @ResponseBody
    public Map<String, Object> getSampleOrderInfo(Integer orderId) {
        return sampleService.getSampleOrderInfo(ConstantsData.getLoginUserId(),
                orderId);
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
        List<Integer> list = sampleIds == null || sampleIds.length <= 0 ? null
                : Arrays.asList(sampleIds);
        return sampleService.deleteList(list);
    }

    @ActionLog(value = "获取采样中的样本列表", button = "提取样本")
    @RequestMapping("getSamplingList")
    @ResponseBody
    public List<Sample> getSamplingList() {
        return sampleService.allUnaddSample(ConstantsData.getLoginUserId());
    }

    @ActionLog(value = "新增样本", button = "扫码添加")
    @RequestMapping("sampling")
    @ResponseBody
    public Integer sampling(String sampleName, Integer tagId, String type) {
        Integer userId = ConstantsData.getLoginUserId();
        Boolean check = sampleService.checkSample(sampleName, userId);
        if (check) {
            return 2; // 检测样品已添加
        }
        return sampleService.samplingAddSample(userId, sampleName, type, tagId);
    }

    @ActionLog(value = "编辑备注", button = "编辑备注")
    @RequestMapping("editRemark")
    @ResponseBody
    public Integer editRemark(String remark, Integer sampleId) {
        return sampleService.editRemark(sampleId, remark);
    }

    @ActionLog(value = "删除实验中的样本", button = "删除样本")
    @RequestMapping("removeSampleLog")
    @ResponseBody
    public Integer removeSampleLog(Integer sampleLogId) {
        return sampleService.deleteSampleLog(sampleLogId);
    }

    @ActionLog(value = "获取入库的样本列表", button = "样本入库")
    @RequestMapping("getScanStorageSamples")
    @ResponseBody
    public PageList<Sample> getScanStorageSamples(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageList<Sample> pageList = getSamples(page, size, SampleExperState.SCAN_STORAGE);
        return pageList;
    }

    @ActionLog(value = "扫码样本入库", button = "扫码入库")
    @RequestMapping("toScanStorage")
    @ResponseBody
    public Map<String, String> toScanStorage(String sampleName) {
        Map<String, String> map = new HashMap<>();
        String result = changeType(sampleName, SampleExperState.SAMPLING,
                SampleExperState.SCAN_STORAGE);
        map.put("result", result);
        return map;
    }

    @ActionLog(value = "获取提DNA的样本列表", button = "提DNA")
    @RequestMapping("getTokenDnaSamples")
    @ResponseBody
    public PageList<Sample> getTokenDnaSamples(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return getSamples(page, size, SampleExperState.TOKEN_DNA);
    }

    @ActionLog(value = "扫码提取DNA", button = "提取DNA")
    @RequestMapping("toTokenDNA")
    @ResponseBody
    public Map<String, String> toTokenDNA(String sampleName) {
        Map<String, String> map = new HashMap<>();
        String result = changeType(sampleName, SampleExperState.SCAN_STORAGE,
                SampleExperState.TOKEN_DNA);
        map.put("result", result);
        return map;
    }

    @ActionLog(value = "获取建库中的样本列表", button = "建库")
    @RequestMapping("getBuidLibrarySamples")
    @ResponseBody
    public Map<String,Object> getBuidLibrarySamples() {
        Map<String,Object> map = new HashMap<>();
        PageList<Sample> pageList = getSamples(1, 12, SampleExperState.BUID_LIBRARY);
        map.put("pageList", pageList);
        SecureRandom s = new SecureRandom();
        map.put("libraryName", DateUtil.getDateToString()
                + String.format("%02d", s.nextInt(99)));
        return map;
    }

    /**
     * 新增建库的样本
     * 
     * @param sampleName
     * @param sno
     *            序号
     * @return
     * @author leamo
     * @date 2016年9月7日 上午1:26:51
     */
    @RequestMapping("addSampleToLibrary")
    @ResponseBody
    public Integer addSampleToLibrary(String sampleName,
            String[] sindexs) {
        Sample prevSamp = sampleService.getByNameExperState(
                ConstantsData.getLoginUserId(), sampleName,
                SampleExperState.TOKEN_DNA);
        if (prevSamp == null)
            return 0;
        Sample currentSamp = sampleService.getByNameExperState(
                ConstantsData.getLoginUserId(), sampleName,
                SampleExperState.BUID_LIBRARY);
        if (currentSamp != null)
            return -1;
        List<String> sindexList = sindexs == null || sindexs.length <= 0 ? null
                : Arrays.asList(sindexs);
        return sampleService.updateExperStateAndIndex(
                ConstantsData.getLoginUserId(), SampleExperState.BUID_LIBRARY,
                prevSamp.getSampleId(), sindexList);
    }

    @RequestMapping("addLibrary")
    @ResponseBody
    public SampleStorage addLibrary(String libraryName, String sindex,
            Integer[] sampleIds) {
        List<Integer> list = sampleIds == null || sampleIds.length <= 0 ? null
                : Arrays.asList(sampleIds);
        SampleStorage ss = sampleService.addStorage(libraryName, sindex, list,
                ConstantsData.getLoginUserId());
        List<String> header = Arrays.asList("文库编号", "文库index", "样品编号", "样品类型",
                "建库时间", "样本index");
        ExcelUtil.listToExcel(header,
                sampleService.sampleListInStorage(
                        ConstantsData.getLoginUserId(), ss.getId()),
                PropertiesUtil.experimentExcelPath + libraryName + ss
                        + ".xls");
        return ss;
    }

    @RequestMapping("getSampleStorages")
    @ResponseBody
    public PageList<SampleStorage> getSampleStorages(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return sampleService.getSampleStorages(new Page(page, size),
                ConstantsData.getLoginUserId());
    }

    @RequestMapping("sampleListInStorage")
    @ResponseBody
    public List<Map<String, Object>> sampleListInStorage(Integer ssId) {
        return sampleService.sampleListInStorage(ConstantsData.getLoginUserId(),
                ssId);
    }

    @ActionLog(value = "下载excel", button = "下载excel")
    @RequestMapping("downExperExcel")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Integer downExperExcel(String storageName, Integer ssId) {
        String filePath = PropertiesUtil.experimentExcelPath + storageName
                + ssId
                + ".xls";
        if (new File(filePath).exists()) {
            FileTools.fileDownLoad(ConstantsData.getResponse(), filePath);
            return 0;
        }
        return 1;
    }

    /**
     * 获取样本列表
     * 
     * @param page
     * @param size
     * @param experState
     * @return
     */
    private PageList<Sample> getSamples(int page, int size, int experState) {
        return sampleService.getSamples(new Page(page, size),
                ConstantsData.getLoginUserId(), experState);
    }

    /**
     * 修改样本状态
     * 
     * @param sampleName
     * @param prevType
     * @param currentType
     * @return
     */
    private String changeType(String sampleName, int prevType,
            int currentType) {
        // 判断是否已经存在上一个状态的样本
        Sample sampling = sampleService.getByNameExperState(
                ConstantsData.getLoginUserId(), sampleName,
                prevType);
        if (sampling == null)
            return "0";
        // 判断是否已经存在一个相同的样本
        Sample scanStorage = sampleService.getByNameExperState(
                ConstantsData.getLoginUserId(), sampleName,
                currentType);
        if (scanStorage != null)
            return "-1";
        // 如果存在上一个状态并且不存在当前状态, 则更新状态
        sampleService.updateExperState(ConstantsData.getLoginUserId(),
                currentType, sampling.getSampleId());
        return DateUtil.getDateToString(sampling.getLogDate(),
                "yyyy-MM-dd HH:mm:ss");
    }

}

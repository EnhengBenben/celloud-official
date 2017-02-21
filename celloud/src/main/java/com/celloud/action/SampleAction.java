package com.celloud.action;

import java.io.File;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.constants.ConstantsData;
import com.celloud.constants.IconConstants;
import com.celloud.constants.SampleTypes;
import com.celloud.model.mysql.Patient;
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

    /**
     * 
     * @description 向用户所属的大客户发送订单
     * @author miaoqi
     * @date 2017年2月17日 上午10:11:57
     * @param orderId
     * @return
     */
    @ActionLog(value = "样本寄送订单", button = "样品")
    @RequestMapping("sendSampleInfoOrderInfo")
    public ResponseEntity<Void> sendSampleInfoOrderInfo(Integer orderId) {
        Integer userId = ConstantsData.getLoginUserId();
        Boolean flag = sampleService.sendOrderInfo(userId, orderId);
        if (flag) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

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
        ModelAndView mv = new ModelAndView("bsi/baozheng/sample_list");
        List<Sample> samples = sampleService
                .allUnaddSample(ConstantsData.getLoginUserId());
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


    @RequestMapping("commitSampleInfo")
    public ResponseEntity<Integer> commitSampleInfo() {
        Integer count = sampleService.commitSampleInfo(ConstantsData.getLoginUserId());
        if (count > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(count);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(count);
        }
    }

    @ActionLog(value = "获取样品订单信息", button = "提交订单")
    @RequestMapping("getSampleOrderInfo")
    @ResponseBody
    public Map<String, Object> getSampleOrderInfo(Integer orderId) {
        return sampleService.getSampleOrderInfo(ConstantsData.getLoginUserId(),
                orderId);
    }

    @ActionLog(value = "获取样品订单信息", button = "提交订单")
    @RequestMapping("getSampleInfoOrderInfo")
    @ResponseBody
    public Map<String, Object> getSampleInfoOrderInfo(Integer orderId) {
        return sampleService.getSampleInfoOrderInfo(ConstantsData.getLoginUserId(), orderId);
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

    @ActionLog(value = "新增样本信息", button = "扫码添加")
    @RequestMapping(value = "sampleInfos", method = RequestMethod.POST)
    public ResponseEntity<Void> sampleInfos(Patient patient, Sample sample) {
        if (sample.getSampleName() == null || sample.getTagId() == null || sample.getType() == null
                || patient == null) {
            logger.info("新增样本信息参数错误 sampleName = {}, tagId = {}, type = {}, patient = {}", sample.getSampleName(),
                    sample.getTagId(), sample.getType(),
                    patient);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Integer userId = ConstantsData.getLoginUserId();
        // 检验样本是否重复
        Boolean check = sampleService.checkSample(sample.getSampleName(), userId);
        if (check) {
            logger.info("用户 {} 下已经存在样本名为 {} 的样本", userId, sample.getSampleName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Boolean flag = sampleService.saveSampleInfoAndPatient(userId, sample, patient);
        if (!flag) {
            logger.info("用户 {} 增加样本和患者信息出错", userId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("用户 {} 增加样本和患者信息成功", userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ActionLog(value = "更新样本信息")
    @RequestMapping(value = "updateSampleInfos")
    public ResponseEntity<Void> updateSampleInfos(Patient patient, Sample sample, Integer oldTagId) {
        if (sample.getSampleId() == null || sample.getSampleName() == null || sample.getTagId() == null
                || sample.getType() == null
                || patient.getId() == null) {
            logger.info("更新样本信息参数错误 sampleName = {}, tagId = {}, type = {}, patient = {}", sample.getSampleName(),
                    sample.getTagId(), sample.getType(),
                    patient);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Integer userId = ConstantsData.getLoginUserId();
        Boolean flag = sampleService.updateSampleInfoAndPatient(patient, sample, oldTagId);
        if (!flag) {
            logger.info("用户 {} 更新样本和患者信息出错", userId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("用户 {} 更新样本和患者信息成功", userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping("removeSampleInfo")
    public ResponseEntity<Void> removeSampleInfo(Integer sampleId) {
        Integer uerId = ConstantsData.getLoginUserId();
        logger.info("用户 {} 删除 sampleId = {} 的样本信息", uerId, sampleId);
        Boolean flag = sampleService.removeSampleInfo(sampleId);
        if (flag) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            logger.info("用户 {} 删除 sampleid = {} 的样本信息失败", uerId, sampleId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "sampleInfos/{sampleId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String, String>> getSampleInfo(@PathVariable("sampleId") Integer sampleId) {
        Map<String, String> map = sampleService.getSampleAndPatient(ConstantsData.getLoginUserId(), sampleId);
        if (map != null && map.keySet().size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(map);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping("listSampleInfo")
    @ResponseBody
    public List<Map<String, String>> listSampleInfo() {
        return sampleService.listSampleAndPatient(ConstantsData.getLoginUserId(), SampleTypes.NOTADD, 0);
    }

    @ActionLog(value = "样本实验状态列表", button = "样本追踪")
    @RequestMapping("sampleTranking")
    @ResponseBody
    public PageList<Sample> sampleTranking(String sampleName,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return sampleService.getSamplesExperState(
                new Page(page, size),
                ConstantsData.getLoginUserId(), sampleName);
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
        return getSamples(page, size, SampleTypes.SCAN_STORAGE);
    }

    @ActionLog(value = "扫码样本入库", button = "扫码入库")
    @RequestMapping("toScanStorage")
    @ResponseBody
    public Map<String, String> toScanStorage(String sampleName,
            String orderNo) {
        Map<String, String> map = new HashMap<>();
        Sample s = sampleService.getSampleByNameAndOrderNo(orderNo, sampleName);
        if (s == null) {
            map.put("error", "该订单中无此样本信息");
            return map;
        }
        // 判断样本是否已采集
        Sample sampling = sampleService.getByNameExperState(orderNo, sampleName,
                SampleTypes.SAMPLING);
        if (sampling == null){
            map.put("error", "系统中无此样本信息，请确认是已采样样本！");
            return map;
        }
        // 判断样本是否已入库
        Sample scanStorage = sampleService.getByNameExperState(orderNo,
                sampleName,
                SampleTypes.SCAN_STORAGE);
        if (scanStorage != null){
            map.put("error", "此样品信息已经收集过，请核查或者采集下一管样品信息！");
            return map;
        }
        // 修改样本状态为入库
        map.put("experName",
                sampleService.updateExperState(ConstantsData.getLoginUserId(),
                        SampleTypes.SCAN_STORAGE, sampling.getSampleId()));
        map.put("date", DateUtil.getDateToString(sampling.getLogDate(),
                "yyyy-MM-dd HH:mm:ss"));
        return map;
    }

    @ActionLog(value = "获取提DNA的样本列表", button = "提DNA")
    @RequestMapping("getTokenDnaSamples")
    @ResponseBody
    public PageList<Sample> getTokenDnaSamples(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return getSamples(page, size, SampleTypes.TOKEN_DNA);
    }

    @ActionLog(value = "扫码提取DNA", button = "提取DNA")
    @RequestMapping("toTokenDNA")
    @ResponseBody
    public Map<String, String> toTokenDNA(String experSampleName) {
        Map<String, String> map = new HashMap<>();
        // 判断样本是否已入库
        Sample scanStorage = sampleService.getByExperNameExperState(
                experSampleName, SampleTypes.SCAN_STORAGE);
        if (scanStorage == null) {
            map.put("error", "此样本未入库");
            return map;
        }
        // 判断样本是否已提取DNA
        Sample tokenDNA = sampleService.getByExperNameExperState(
                experSampleName, SampleTypes.TOKEN_DNA);
        if (tokenDNA != null) {
            map.put("error", "此样品信息已经收集过，请核查或者采集下一管样品信息！");
            return map;
        }
        // 修改样本状态为提取DNA
        sampleService.updateExperState(ConstantsData.getLoginUserId(),
                SampleTypes.TOKEN_DNA, scanStorage.getSampleId());
        map.put("date", DateUtil.getDateToString(scanStorage.getLogDate(),
                "yyyy-MM-dd HH:mm:ss"));
        return map;
    }

    @ActionLog(value = "获取建库中的样本列表", button = "建库")
    @RequestMapping("getBuidLibrarySamples")
    @ResponseBody
	public Map<String, Object> getBuidLibrarySamples() {
        Map<String,Object> map = new HashMap<>();
        PageList<Sample> pageList = getSamples(1, SampleTypes.index.size(),
                SampleTypes.BUID_LIBRARY);
        Collections.reverse(pageList.getDatas());
        map.put("pageList", pageList);
        SecureRandom s = new SecureRandom();
        map.put("libraryName", DateUtil.getDateToString()
                + String.format("%02d", s.nextInt(99)));
		map.put("metaList", SampleTypes.libraryIndex);
        map.put("sampleIndex", SampleTypes.index);
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
    public Integer addSampleToLibrary(String experSampleName,
            String[] sindexs) {
        Integer userId = ConstantsData.getLoginUserId();
        Sample prevSamp = sampleService.getByExperNameExperState(
                experSampleName, SampleTypes.TOKEN_DNA);
        if (prevSamp == null)
            return 0;
        Sample currentSamp = sampleService.getByExperNameExperState(
                experSampleName, SampleTypes.BUID_LIBRARY);
        if (currentSamp != null)
            return -1;
        List<String> sindexList = sindexs == null || sindexs.length <= 0 ? null
                : Arrays.asList(sindexs);
        return sampleService.updateExperStateAndIndex(userId,
                SampleTypes.BUID_LIBRARY,
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
        List<String> header = Arrays.asList("文库编号", "文库index", "医院样品编号",
                "实验样品编号", "样品类型",
                "建库时间", "样本index");
        ExcelUtil.listToExcel(header,
                sampleService.sampleListInStorage(
                        ConstantsData.getLoginUserId(), ss.getId()),
                PropertiesUtil.experimentExcelPath + libraryName + ss.getId()
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

    @ActionLog(value = "修改文库上机状态", button = "修改上机状态")
    @RequestMapping("changeInMachine")
    @ResponseBody
    public Integer changeInMachine(int sampleStorageId) {
        return sampleService.updateSampleInMechine(ConstantsData.getLoginUserId(), sampleStorageId);
    }
}

package com.celloud.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celloud.alimail.AliEmailUtils;
import com.celloud.config.Config;
import com.celloud.constants.DataState;
import com.celloud.constants.FileFormat;
import com.celloud.constants.IconConstants;
import com.celloud.constants.SampleTypes;
import com.celloud.constants.TaskPeriod;
import com.celloud.mapper.PatientMapper;
import com.celloud.mapper.SampleLogMapper;
import com.celloud.mapper.SampleMapper;
import com.celloud.mapper.SampleOrderMapper;
import com.celloud.mapper.SampleStorageMapper;
import com.celloud.mapper.TaskMapper;
import com.celloud.model.mysql.CompanyEmail;
import com.celloud.model.mysql.Metadata;
import com.celloud.model.mysql.Patient;
import com.celloud.model.mysql.Sample;
import com.celloud.model.mysql.SampleLog;
import com.celloud.model.mysql.SampleOrder;
import com.celloud.model.mysql.SampleStorage;
import com.celloud.model.mysql.Task;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.CompanyEmailService;
import com.celloud.service.DataService;
import com.celloud.service.MetadataService;
import com.celloud.service.PatientService;
import com.celloud.service.SampleService;
import com.celloud.service.UserService;
import com.celloud.utils.DataUtil;
import com.celloud.utils.DateUtil;
import com.celloud.utils.QRCodeUtil;

/**
 * 样本收集管理接口实现
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2016年6月20日 上午11:22:09
 */
@Service("sampleService")
public class SampleServiceImple implements SampleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleServiceImple.class);
	@Resource
    private SampleMapper sampleMapper;
	@Resource
    private SampleOrderMapper sampleOrderMapper;
    @Resource
    private SampleLogMapper sampleLogMapper;
    @Resource
    private SampleStorageMapper sampleStorageMapper;
    @Resource
    private TaskMapper taskMapper;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyEmailService companyEmailService;
    @Autowired
    private Config config;
    @Autowired
    private AliEmailUtils aliEmailUtil;
    @Autowired
    private DataService dataService;
    @Autowired
    private MetadataService metadataService;

	@Override
	public Integer saveSample(String sampleName, Integer userId) {
		Sample s = new Sample();
		s.setSampleName(sampleName);
		s.setUserId(userId);
		return sampleMapper.insertSelective(s);
	}

	@Override
	public Integer commitSamples(List<Integer> sampleIds, Integer appId, Integer userId) {
		sampleIds = new ArrayList<>(new HashSet<>(sampleIds));
		Collections.sort(sampleIds);
        // 添加样本寄送订单
        SampleOrder so = new SampleOrder();
        so.setUserId(userId);
        // 样本编号规则：yyMMdd+ 4位userId不够补0
        String orderNo = DateUtil.getDateToString()
                + String.format("%04d", userId);
        so.setOrderNo(orderNo);
        sampleOrderMapper.insert(so);
        // 修改sample状态为已添加，并添加订单编号
        Integer result = sampleMapper.updateAddTypeById(sampleIds,
                SampleTypes.ISADD, so.getId());
        for (Integer sampleId : sampleIds) {
            Task task = new Task();
            task.setAppId(appId);
            task.setUserId(userId);
            task.setSampleId(sampleId);
            task.setPeriod(TaskPeriod.SAMPLING);
            task.setCreateDate(new Date());
            task.setUpdateDate(new Date());
            taskMapper.insertSelective(task);
        }
		return result;
	}

    @Override
    public Integer commitSamples(List<Integer> sampleIds, Integer userId) {
        Map<String, Map<String, String>> nameSeqMap = metadataService.getNameSeqMap(3);
        sampleIds = new ArrayList<>(new HashSet<>(sampleIds));
        Collections.sort(sampleIds);
        sampleIds = new ArrayList<>(new HashSet<>(sampleIds));
        Collections.sort(sampleIds);
        // 添加样本寄送订单
        SampleOrder so = new SampleOrder();
        so.setUserId(userId);
        sampleOrderMapper.insertSelective(so);
        so.setOrderNo(DataUtil.getSampleOrderNo(so.getId()));
        sampleOrderMapper.updateByPrimaryKeySelective(so);
		// 修改sample状态为已添加，并添加订单编号, 实验样本编号
        List<Sample> samples = sampleMapper.selectByIds(sampleIds);
        for(Sample sample : samples){
			sample.setIsAdd(true);
			sample.setOrderId(so.getId());
            sample.setExperSampleName(
                    DataUtil.getExperSampleNo(nameSeqMap.get(sample.getType()).get("seq"), sample.getSampleId()));
			sampleMapper.updateByPrimaryKeySelective(sample);
        }
        return so.getId();
    }

	@Override
	public List<Sample> allUnaddSample(Integer userId) {
        return sampleMapper.selectAllByUser(userId, SampleTypes.NOTADD,
                DataState.ACTIVE, SampleTypes.SAMPLING);
	}

	@Override
	public Boolean checkSample(String sampleName, Integer userId) {
		return sampleMapper.selectByName(userId, sampleName, DataState.ACTIVE) != null;
	}

    @Override
    public Boolean checkSample(Sample sample, Integer userId) {
        return sampleMapper.selectByNameAndNotIn(userId, sample, DataState.ACTIVE) != null;
    }

	@Override
	public Integer delete(Integer sampleId) {
        sampleLogMapper.deleteBySampling(sampleId);
		return sampleMapper.deleteByPrimaryKey(sampleId);
	}

	@Override
	public Integer deleteList(List<Integer> sampleIds) {
		return sampleMapper.deleteList(sampleIds);
	}

    @Override
    public PageList<Sample> getSamples(Page page, Integer userId,
            Integer experState) {
        List<Sample> list = sampleMapper.getSamples(page, userId, experState,
                DataState.ACTIVE);
        return new PageList<>(page, list);
    }

    @Override
    public Sample getByNameExperState(String orderNo, String sampleName,
            Integer experState) {
        return sampleMapper.getByNameExperState(orderNo, sampleName, experState,
                DataState.ACTIVE, SampleTypes.ISADD);
    }

    @Override
    public String updateExperState(Integer userId, Integer experState,
            Integer sampleId) {
        sampleLogMapper.deleteBySampleId(sampleId, DataState.DEELTED);
        SampleLog slog = new SampleLog();
        slog.setUserId(userId);
        slog.setSampleId(sampleId);
        slog.setExperState(experState);
        sampleLogMapper.insertSelective(slog);
        return "success";
    }

    @Override
    public Integer updateExperStateAndIndex(Integer userId, Integer experState,
            Integer sampleId, List<String> sindexList) {
        List<Metadata> sampleIndex = metadataService.getMetadata(118, 1);
		if (SampleTypes.indexString == null) {
			List<String> sampleList = new ArrayList<>();
            for (Metadata metadata : sampleIndex) {
				sampleList.add(metadata.getName() + ":" + metadata.getSeq());
			}
			SampleTypes.indexString = sampleList;
		}
        List<String> indexList = new ArrayList<>();
		indexList.addAll(SampleTypes.indexString);
        if (sindexList != null) {
            for (String s : sindexList) {
                indexList.remove(s);
            }
        }
        Sample s = new Sample();
        s.setSampleId(sampleId);
        s.setSindex(indexList.get(0));
        sampleMapper.updateByPrimaryKeySelective(s);

        sampleLogMapper.deleteBySampleId(sampleId, DataState.DEELTED);

        SampleLog slog = new SampleLog();
        slog.setUserId(userId);
        slog.setSampleId(sampleId);
        slog.setExperState(experState);
        return sampleLogMapper.insertSelective(slog);
    }

    @Override
    public Integer samplingAddSample(Integer userId, String sampleName,
            String type, Integer tagId) {
        Sample s = new Sample();
        s.setSampleName(sampleName);
        s.setType(type);
        s.setUserId(userId);
        sampleMapper.insertSelective(s);
        sampleMapper.addSampleTagRelat(s.getSampleId(), tagId);
        SampleLog slog = new SampleLog();
        slog.setUserId(userId);
        slog.setSampleId(s.getSampleId());
        return sampleLogMapper.insertSelective(slog);
    }

    @Override
    public Integer deleteSampleLog(Integer sampleLogId) {
        SampleLog sl = sampleLogMapper.selectByPrimaryKey(sampleLogId);
        Integer experState = sl.getExperState() - 1;
        sampleLogMapper.updateStateBySampleId(sl.getSampleId(),
                DataState.ACTIVE, experState);
        return sampleLogMapper.deleteByPrimaryKey(sampleLogId);
    }

    @Override
    public Integer editRemark(Integer sampleId, String remark) {
        Sample s = new Sample();
        s.setSampleId(sampleId);
        s.setRemark(remark);
        return sampleMapper.updateByPrimaryKeySelective(s);
    }

    @Override
    public SampleStorage addStorage(String name, String sindex,
            List<Integer> sampleIds, Integer userId) {
        SampleStorage ss = new SampleStorage();
        ss.setStorageName(name);
        ss.setSindex(sindex);
        ss.setSampleNum(sampleIds.size());
        ss.setUserId(userId);
        sampleStorageMapper.insertSelective(ss);

        for (Integer sampleId : sampleIds) {
            updateExperState(userId, SampleTypes.IN_LIBRARY, sampleId);
        }
        sampleStorageMapper.addSampleStorageRelat(ss.getId(), sampleIds);
        return ss;
    }

    @Override
    public PageList<SampleStorage> getSampleStorages(Page page,
            Integer userId) {
        List<SampleStorage> list = sampleStorageMapper.findAll(page, userId,
                DataState.ACTIVE);
        return new PageList<SampleStorage>(page, list);
    }

    @Override
    public List<Map<String, Object>> sampleListInStorage(Integer userId, Integer ssId) {
        return sampleStorageMapper.sampleListInStorage(userId, DataState.ACTIVE,
                ssId);
    }

    @Override
    public Map<String, Object> getSampleOrderInfo(Integer userId,
            Integer orderId) {
        Map<String, Object> map = new HashMap<>();
        SampleOrder so = sampleOrderMapper.selectByPrimaryKey(orderId, userId);
        List<Sample> samples = sampleMapper.getSamplesByOrder(userId, orderId,
                DataState.ACTIVE);
        map.put("sampleOrder", so);
        map.put("samples", samples);
        QRCodeUtil.createQRCode(so.getOrderNo(),
                IconConstants.getTempPath() + so.getOrderNo() + ".png");
        return map;
    }

    @Override
    public Sample getSampleByNameAndOrderNo(String orderNo, String sampleName) {
        return sampleMapper.getSampleByNameAndOrderNo(sampleName, orderNo,
                DataState.ACTIVE);
    }

    @Override
    public Sample getByExperNameExperState(String experSampleName,
            Integer experState) {
        return sampleMapper.getByExperNameExperState(experSampleName,
                experState, DataState.ACTIVE, SampleTypes.ISADD);
    }

    @Override
    public List<Sample> getSamplesByStorageName(String storageName) {
        return sampleMapper.getSamplesByStorageName(storageName,
                DataState.ACTIVE);
    }

    @Override
    public PageList<Sample> getSamplesExperState(Page page, Integer userId,
            String sampleName) {
        List<Sample> list = sampleMapper.getSamplesExperState(page, userId,
                sampleName, DataState.ACTIVE);
        return new PageList<Sample>(page, list);
    }

    @Override
    public Integer updateSampleInMechine(Integer userId, Integer sampleStorageId) {
        SampleStorage sampleStorage = new SampleStorage();
        sampleStorage.setId(sampleStorageId);
        sampleStorage.setInMachine(SampleTypes.SS_IN_MACHINE);

        List<Sample> list = sampleMapper.getSamplesByStorageId(sampleStorageId, DataState.ACTIVE);
        for (Sample s : list) {
            sampleLogMapper.deleteBySampleId(s.getSampleId(), DataState.DEELTED);

            SampleLog slog = new SampleLog();
            slog.setUserId(userId);
            slog.setSampleId(s.getSampleId());
            slog.setExperState(SampleTypes.IN_MACHINE);
            sampleLogMapper.insertSelective(slog);
        }
        return sampleStorageMapper.updateByPrimaryKeySelective(sampleStorage);
    }

	@Override
	public Sample findByPrimaryKey(Integer id) {
		return sampleMapper.selectByPrimaryKey(id);
	}

    @Override
    public Boolean saveSampleInfoAndPatient(Integer userId, Sample sample, Patient patient) {

        // 增加患者信息
        patient.setCreateDate(new Date());
        patient.setUpdateDate(patient.getCreateDate());
        patientService.save(patient);
        if (patient.getId() == null) {
            LOGGER.info("增加患者信息失败");
            return false;
        }
        // 增加样本信息
        sample.setUserId(userId);
        sample.setPatientId(patient.getId());
        sampleMapper.insertSelective(sample);
        sampleMapper.addSampleTagRelat(sample.getSampleId(), sample.getTagId());
        // 增加样本日志信息
        SampleLog sampleLog = new SampleLog();
        sampleLog.setUserId(userId);
        sampleLog.setSampleId(sample.getSampleId());
        sampleLogMapper.insertSelective(sampleLog);
        return true;
    }

    @Override
    public List<Map<String, String>> listSampleAndPatient(Integer userId, Integer isAdd, Integer orderId) {
        return sampleMapper.listSampleAndPatient(userId, orderId, isAdd, DataState.ACTIVE);
    }

    @Override
    public Boolean removeSampleInfo(Integer sampleId) {
        // 获取样本信息
        Sample sample = sampleMapper.selectByPrimaryKey(sampleId);
        if (sample == null) {
            return false;
        }
        Integer patientId = sample.getPatientId();
        Integer count1 = sampleMapper.deleteByPrimaryKey(sampleId);
        Integer count2 = patientMapper.deleteByPrimaryKey(patientId);
        return count1.intValue() == 1 && count2.intValue() == 1;
    }

    @Override
    public Map<String, String> getSampleAndPatient(Integer userId, Integer sampleId) {
        return sampleMapper.getSampleAndPatient(userId, sampleId, SampleTypes.NOTADD, DataState.ACTIVE);
    }

    @Override
    public Boolean updateSampleInfoAndPatient(Patient patient, Sample sample, Integer oldTagId) {
        // 更新患者信息
        patient.setCreateDate(null);
        patient.setUpdateDate(new Date());
        Integer count1 = patientMapper.updateByPrimaryKeySelective(patient);
        // 更新样本信息
        sample.setCreateDate(null);
        sample.setUpdateDate(patient.getUpdateDate());
        Integer count2 = sampleMapper.updateByPrimaryKeySelective(sample);
        // 查找样本和产品标签的关系
        Integer key = sampleMapper.getKeyBySampleIdAndTagId(sample.getSampleId(), oldTagId);
        // 更新样本标签关系
        Integer count3 = sampleMapper.updateSampleIdAndTagIdByKey(key, sample.getSampleId(), sample.getTagId());
        return count1.intValue() == 1 && count2.intValue() == 1 && count3.intValue() == 1;
    }

    @Override
    public Integer commitSampleInfo(Integer userId) {
        Map<String, Map<String, String>> nameSeqMap = metadataService.getNameSeqMap(3);
        List<Map<String, Object>> sampleInfos = sampleMapper.listSample(userId, SampleTypes.NOTADD,
                DataState.ACTIVE);
        if (sampleInfos == null || sampleInfos.size() <= 0) {
            return 0;
        }
        List<Integer> sampleIds = new ArrayList<>();
        for (Map<String, Object> sampleinfo : sampleInfos) {
            sampleIds.add(Integer.parseInt(sampleinfo.get("sampleId").toString()));
        }
        // 添加样本寄送订单
        SampleOrder so = new SampleOrder();
        so.setUserId(userId);
        sampleOrderMapper.insertSelective(so);
        so.setOrderNo(DataUtil.getSampleOrderNo(so.getId()));
        sampleOrderMapper.updateByPrimaryKeySelective(so);

        for (int i = 0; i < sampleInfos.size(); i++) {
            // 生成tb_file
            Integer dataId = dataService.addFileInfo(userId, "");
            // 文件datakey
            String fileDataKey = DataUtil.getNewDataKey(dataId);
            dataService.updateFileInfo(dataId, fileDataKey, null, "", FileFormat.NONE, null, null,
                    (Integer) sampleInfos.get(i).get("tagId"));
            // 生成tb_task
            Task task = new Task();
            task.setAppId(Integer.parseInt(sampleInfos.get(i).get("appId").toString()));
            task.setUserId(userId);
            task.setSampleId(Integer.parseInt(sampleInfos.get(i).get("sampleId").toString()));
            task.setPeriod(TaskPeriod.RUNNING);
            task.setCreateDate(new Date());
            task.setUpdateDate(task.getCreateDate());
            task.setDataKey(fileDataKey);
            taskMapper.insertSelective(task);
            // 生成tb_sample
            Sample sample = new Sample();
            sample.setSampleId(Integer.parseInt(sampleInfos.get(i).get("sampleId").toString()));
            sample.setIsAdd(true);
            sample.setOrderId(so.getId());
            sample.setExperSampleName(
                    DataUtil.getExperSampleNo(nameSeqMap.get(sampleInfos.get(i).get("type")).get("seq"),
                            Integer.parseInt(sampleInfos.get(i).get("sampleId").toString())));
            sampleMapper.updateByPrimaryKeySelective(sample);
        }
        return so.getId();
    }

    @Override
    public Map<String, Object> getSampleInfoOrderInfo(Integer userId, Integer orderId) {
        Map<String, Object> map = new HashMap<>();
        SampleOrder so = sampleOrderMapper.selectByPrimaryKey(orderId, userId);
        List<Map<String, String>> sampleInfos = listSampleAndPatient(userId, SampleTypes.ISADD, orderId);
        map.put("sampleOrder", so);
        map.put("sampleInfos", sampleInfos);
        QRCodeUtil.createQRCode(so.getOrderNo(), IconConstants.getTempPath() + so.getOrderNo() + ".png");
        return map;
    }

    @Override
    public Boolean sendOrderInfo(Integer userId, Integer orderId) {
        // 获取大客户id
        Integer companyId = userService.getCompanyIdByUserId(userId);
        // 获取大客户的email
        CompanyEmail queryCompanyEmail = new CompanyEmail();
        queryCompanyEmail.setCompanyId(companyId);
        // 判断当前的环境, 如果是正式环境, 获取正式人员邮箱
        if (config.getPro()) { // 当前是正式环境
            queryCompanyEmail.setStatus(1); // 正式人员
        } else {
            queryCompanyEmail.setStatus(0); // 测试人员
        }
        List<CompanyEmail> companyEmails = companyEmailService.selectBySelective(queryCompanyEmail);
        if (companyEmails != null && companyEmails.size() > 0) {
            // 根据orderId查询样本信息
            Map<String, Object> sampleInfoOrderInfo = getSampleInfoOrderInfo(userId, orderId);
            if (sampleInfoOrderInfo != null && sampleInfoOrderInfo.keySet().size() > 0) {
                List<Map<String, String>> gesampleInfos = (List<Map<String, String>>) sampleInfoOrderInfo
                        .get("sampleInfos");
                SampleOrder sampleOrder = (SampleOrder) sampleInfoOrderInfo.get("sampleOrder");
                String[] to = new String[companyEmails.size()];
                for (int i = 0; i < companyEmails.size(); i++) {
                    to[i] = companyEmails.get(i).getEmail();
                }
                String title = "样本寄送订单";
                StringBuilder context = new StringBuilder("");
                
                
                context.append("<html>");
                context.append("<div style=\"margin-bottom:-15px;\">");
                context.append(
                        "<h4>订单编号：<span style=\"font-weight: normal;\">" + sampleOrder.getOrderNo() + "</span></h4>");
                context.append("<h4>下单日期：<span style=\"font-weight: normal;\">"
                        + new DateTime(sampleOrder.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss") 
                        + "</span></h4>");
                context.append("</div>");
                context.append("<div style=\"border-top:2px solid #a0a0a0;width:800px;padding-top:10px;\">");
                context.append(
                        "<table style=\"width:800px;border-top: 1px solid #c8c8c8;border-left: 1px solid #c8c8c8;border-right: 1px solid #c8c8c8;padding:0px 10px;border-collapse: collapse;\">");
                context.append("<tr style=\"line-height:29px;\">");
                context.append("<th style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">序号</th>");
                context.append("<th style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">样本编号</th>");
                context.append("<th style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">姓名</th>");
                context.append("<th style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">性别</th>");
                context.append("<th style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">年龄</th>");
                context.append("<th style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">联系电话</th>");
                context.append("<th style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">身份证号</th>");
                context.append("<th style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">更新时间</th>");
                context.append("</tr>");
                
                for (int i = 0; i < gesampleInfos.size(); i++) {
                    context.append("<tr style=\"line-height:29px;\">");
                    context.append("<td style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">"
                            + (gesampleInfos.size() - i) + "</td>");
                    context.append("<td style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">"
                            + gesampleInfos.get(i).get("sampleName") + "</td>");
                    context.append("<td style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">"
                            + gesampleInfos.get(i).get("name") + "</td>");
                    context.append("<td style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">"
                            + ("0".equals(String.valueOf(gesampleInfos.get(i).get("gender"))) ? '女' : '男')
                            + "</td>");
                    context.append("<td style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">"
                            + String.valueOf(gesampleInfos.get(i).get("age")) + "</td>");
                    context.append("<td style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">"
                            + String.valueOf(gesampleInfos.get(i).get("tel")) + "</td>");
                    context.append("<td style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">"
                            + String.valueOf(gesampleInfos.get(i).get("idCard")) + "</td>");
                    context.append(
                            "<td style=\"text-align:center;border-bottom: 1px solid #c8c8c8;\">"
                                    + new DateTime(gesampleInfos.get(i).get("updateDate"))
                                    .toString("yyyy-MM-dd HH:mm:ss") + "</td>");
                    context.append("</tr>");
                }
                context.append("</table>");
                context.append("</div>");
                context.append("</html>");

                aliEmailUtil.simpleSend(title, context.toString(), to);
            }
        }
        return true;
    }

}

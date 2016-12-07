package com.celloud.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.constants.IconConstants;
import com.celloud.constants.SampleTypes;
import com.celloud.constants.TaskPeriod;
import com.celloud.mapper.SampleLogMapper;
import com.celloud.mapper.SampleMapper;
import com.celloud.mapper.SampleOrderMapper;
import com.celloud.mapper.SampleStorageMapper;
import com.celloud.mapper.TaskMapper;
import com.celloud.model.mysql.Metadata;
import com.celloud.model.mysql.Sample;
import com.celloud.model.mysql.SampleLog;
import com.celloud.model.mysql.SampleOrder;
import com.celloud.model.mysql.SampleStorage;
import com.celloud.model.mysql.Task;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.SampleService;
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
	@Resource
	SampleMapper sampleMapper;
	@Resource
    SampleOrderMapper sampleOrderMapper;
    @Resource
    SampleLogMapper sampleLogMapper;
    @Resource
    SampleStorageMapper sampleStorageMapper;
    @Resource
	TaskMapper taskMapper;

	@Override
	public Integer saveSample(String sampleName, Integer userId) {
		Sample s = new Sample();
		s.setSampleName(sampleName);
		s.setCreateDate(new Date());
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
        so.setCreateDate(new Date());
        // 样本编号规则：yyMMdd+ 4位userId不够补0
        String orderNo = DateUtil.getDateToString()
                + String.format("%04d", userId);
        so.setOrderNo(orderNo);
        sampleOrderMapper.insert(so);
        // 修改sample状态为已添加，并添加订单编号
        Integer result = sampleMapper.updateAddTypeById(sampleIds,
                SampleTypes.ISADD, so.getId(), new Date());
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
        sampleIds = new ArrayList<>(new HashSet<>(sampleIds));
        Collections.sort(sampleIds);
        sampleIds = new ArrayList<>(new HashSet<>(sampleIds));
        Collections.sort(sampleIds);
        // 添加样本寄送订单
        SampleOrder so = new SampleOrder();
        so.setUserId(userId);
        so.setCreateDate(new Date());
        sampleOrderMapper.insertSelective(so);
        so.setOrderNo(DataUtil.getSampleOrderNo(so.getId()));
        sampleOrderMapper.updateByPrimaryKeySelective(so);
        // 修改sample状态为已添加，并添加订单编号
        sampleMapper.updateAddTypeById(sampleIds, SampleTypes.ISADD,
                so.getId(), new Date());
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
	public Integer delete(Integer sampleId) {
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
    public Sample getByNameExperState(String sampleName,
            Integer experState) {
        return sampleMapper.getByNameExperState(sampleName, experState,
                DataState.ACTIVE, SampleTypes.ISADD);
    }

    @Override
    public String updateExperState(Integer userId, Integer experState,
            Integer sampleId) {
        sampleLogMapper.deleteBySampleId(sampleId, DataState.DEELTED, userId);
        SampleLog slog = new SampleLog();
        slog.setUserId(userId);
        slog.setSampleId(sampleId);
        slog.setCreateDate(new Date());
        slog.setExperState(experState);
        sampleLogMapper.insertSelective(slog);
        if (experState == SampleTypes.SCAN_STORAGE) {
            Sample s = sampleMapper.selectByPrimaryKey(sampleId);
            s.setExperSampleName(
                    DataUtil.getExperSampleNo(s.getType(), sampleId));
            sampleMapper.updateByPrimaryKeySelective(s);
            return s.getExperSampleName();
        }
        return "success";
    }

    @Override
    public Integer updateExperStateAndIndex(Integer userId, Integer experState,
            Integer sampleId, List<String> sindexList) {
		if (SampleTypes.indexString == null) {
			List<String> sampleList = new ArrayList<>();
			for (Metadata metadata : SampleTypes.index) {
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

        sampleLogMapper.deleteBySampleId(sampleId, DataState.DEELTED, userId);

        SampleLog slog = new SampleLog();
        slog.setUserId(userId);
        slog.setSampleId(sampleId);
        slog.setCreateDate(new Date());
        slog.setExperState(experState);
        return sampleLogMapper.insertSelective(slog);
    }

    @Override
    public Integer samplingAddSample(Integer userId, String sampleName,
            String type, Integer tagId) {
        Sample s = new Sample();
        s.setSampleName(sampleName);
        s.setCreateDate(new Date());
        s.setType(type);
        s.setUserId(userId);
        sampleMapper.insertSelective(s);
        sampleMapper.addSampleTagRelat(s.getSampleId(), tagId);
        SampleLog slog = new SampleLog();
        slog.setUserId(userId);
        slog.setSampleId(s.getSampleId());
        slog.setCreateDate(new Date());
        return sampleLogMapper.insertSelective(slog);
    }

    @Override
    public Integer deleteSampleLog(Integer sampleLogId) {
        SampleLog sl = sampleLogMapper.selectByPrimaryKey(sampleLogId);
        Integer experState = sl.getExperState() - 1;
        sampleLogMapper.updateStateBySampleId(sl.getSampleId(),
                DataState.ACTIVE, sl.getUserId(), experState);
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
        ss.setCreateDate(new Date());
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
    public List<Map<String, Object>> sampleListInStorage(Integer userId,
            Integer ssId) {
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
    public Sample getByExperNameExperState(
            String experSampleName, Integer experState) {
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
    public Integer updateSampleInMechine(Integer sampleStorageId) {
        SampleStorage sampleStorage = new SampleStorage();
        sampleStorage.setId(sampleStorageId);
        sampleStorage.setInMachine(SampleTypes.SS_IN_MACHINE);
        return sampleStorageMapper.updateByPrimaryKeySelective(sampleStorage);
    }
}

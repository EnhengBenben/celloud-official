package com.celloud.backstage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.mapper.TaskMapper;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.TaskService;
import com.celloud.backstage.utils.DateUtil;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Override
    public PageList<Map<String, String>> getRunningTimeByPage(Page page, String keyword) {
        List<Map<String, String>> list = taskMapper.getRunningTimeByPage(page, keyword);
        for (Map<String, String> map : list) {
            Object s = map.get("avgSecond");
            map.put("avgSecond", DateUtil.secondToTime(Long.parseLong(s.toString())));
        }
        return new PageList<Map<String, String>>(page, list);
    }

    @Override
    public Map<String, String> getQueuingTime() {
        Map<String, String> map = taskMapper.getQueuingTime();
        if (map != null) {
            Object totalSecond = map.get("totalSecond");
            Object avgSecond = map.get("avgSecond");
            map.put("totalSecond", DateUtil.secondToTime(Long.parseLong(totalSecond.toString())));
            map.put("avgSecond", DateUtil.secondToTime(Long.parseLong(avgSecond.toString())));
        }
        return map;
    }

    @Override
    public Map<String, Integer> getQuantityStatistics() {
        int totalRecordCount = taskMapper.getTotalRecordCount();
        int deleteCount = taskMapper.getCountByState(1);
        int failCount = taskMapper.getFailCount();
        Map<String, Integer> map = null;
        if (totalRecordCount > 0) {
            map = new HashMap<String, Integer>();
            map.put("totalRecordCount", totalRecordCount);
            map.put("deleteCount", deleteCount);
            map.put("failCount", failCount);
        }
        return map;
    }

}

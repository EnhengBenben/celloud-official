package com.celloud.backstage.service;

import java.util.Map;

import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;

public interface TaskService {

    public PageList<Map<String, String>> getRunningTimeByPage(Page page, String keyword);

    public Map<String, String> getQueuingTime();

    public Map<String, Integer> getQuantityStatistics();

    public int sendWeekStatistics(String colonyUsed);
}

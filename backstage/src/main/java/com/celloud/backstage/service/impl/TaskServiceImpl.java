package com.celloud.backstage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Service;

import com.celloud.backstage.mapper.TaskMapper;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.TaskService;
import com.celloud.backstage.utils.DateUtil;
import com.celloud.backstage.utils.POIWordUtil;
import com.celloud.backstage.utils.PropertiesUtil;

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

    @Override
    public void sendWeekStatistics() {
        String filePath = "G:\\weekstatistics\\mydata.docx";
        createWord(filePath);

    }

    private void createWord(String filePath) {
        XWPFDocument doc = new XWPFDocument();
        createTable1(doc);
        POIWordUtil.saveDocument(doc, filePath);
    }

    private void createTable1(XWPFDocument doc) {
        List<Map<String, Object>> lastWeekUserLogin = taskMapper.getLastWeekUserLogin(PropertiesUtil.testAccountIds);
        List<Map<String, Object>> lastWeekAppRun = taskMapper.getLastWeekAppRun(PropertiesUtil.testAccountIds);
        List<Map<String, Object>> lastWeekDataSize = taskMapper.getLastWeekDataSize(PropertiesUtil.testAccountIds);
        List<Map<String, Object>> lastWeekData = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> temp = new LinkedHashMap<String, Object>();
            if (lastWeekUserLogin.size() > i) {
                temp.put("logUsername", lastWeekUserLogin.get(i).get("username"));
                temp.put("logCount", lastWeekUserLogin.get(i).get("log_count"));
            } else {
                temp.put("logUsername", "无");
                temp.put("logCount", 0);
            }
            if(lastWeekAppRun.size()>i){
                temp.put("appName", lastWeekAppRun.get(i).get("app_name"));
                temp.put("appCount", lastWeekAppRun.get(i).get("app_count"));
            }else{
                temp.put("appName",  "无");
                temp.put("appCount", 0);
            }
            if (lastWeekDataSize.size() > i) {
                temp.put("sizeUsername", lastWeekDataSize.get(i).get("username"));
                Float sizeSum = Float.parseFloat(lastWeekDataSize.get(i).get("size_sum").toString());
                Object realSizeSum = 0;
                if (sizeSum > 1099511627776f) {
                    sizeSum = (sizeSum - sizeSum % 1099511627776f) / 1099511627776f
                            + sizeSum % 1099511627776f / 1099511627776f;
                    realSizeSum = String.format("%.2f", sizeSum) + "TB";
                }else if(sizeSum > 1073741824){
                    sizeSum = (sizeSum - sizeSum % 1073741824) / 1073741824 + sizeSum % 1073741824 / 1073741824;
                    realSizeSum = String.format("%.2f", sizeSum) + "GB";
                }else if(sizeSum > 1048576){
                    sizeSum = (sizeSum - sizeSum % 1048576) / 1048576 + sizeSum % 1048576 / 1048576;
                    realSizeSum = String.format("%.2f", sizeSum) + "MB";
                }else{
                    sizeSum = (sizeSum - sizeSum % 1024) / 1024 + sizeSum % 1024 / 1024;
                    realSizeSum = String.format("%.2f", sizeSum) + "KB";
                }
                temp.put("sizeSum", realSizeSum);
                temp.put("fileCount", lastWeekDataSize.get(i).get("file_count"));
            } else {
                temp.put("sizeUsername", "无");
                temp.put("sizeSum", 0);
                temp.put("fileCount", 0);
            }
            lastWeekData.add(i, temp);
        }
        
        POIWordUtil.insertText(doc, "周统计", ParagraphAlignment.CENTER, 22);
        POIWordUtil.insertTextLeftBold(doc, "1. 本周Top10统计");
        XWPFTable table = POIWordUtil.createTable(doc, 12, 7, "2200");
        POIWordUtil.mergeCellsHorizontal(table, 0, 0, 1);
        POIWordUtil.mergeCellsHorizontal(table, 0, 2, 3);
        POIWordUtil.mergeCellsHorizontal(table, 0, 4, 6);
        POIWordUtil.setCellTextBoldCenter(table, 0, 0, "前10活跃用户及登录次数");
        POIWordUtil.setCellTextBoldCenter(table, 0, 2, "前10活跃APP及运行次数");
        POIWordUtil.setCellTextBoldCenter(table, 0, 4, "数据量前10用户及数据大小");
        POIWordUtil.setCellTextBoldCenter(table, 1, 0, "username");
        POIWordUtil.setCellTextBoldCenter(table, 1, 1, "登陆次数");
        POIWordUtil.setCellTextBoldCenter(table, 1, 2, "APP");
        POIWordUtil.setCellTextBoldCenter(table, 1, 3, "运行次数");
        POIWordUtil.setCellTextBoldCenter(table, 1, 4, "username");
        POIWordUtil.setCellTextBoldCenter(table, 1, 5, "数据大小");
        POIWordUtil.setCellTextBoldCenter(table, 1, 6, "文件数量");
        POIWordUtil.fillTableByMap(table, 2, lastWeekData);

        Integer currentActiveUserCount = taskMapper.getActiveUserCount(PropertiesUtil.testAccountIds);
        Integer lastActiveUserCount = taskMapper.getLastActiveUserCount(PropertiesUtil.testAccountIds);
        String currentMonday = DateUtil.getCurrentMonday();
        String[] ymd = currentMonday.split("-");
        POIWordUtil.insertTextLeftBold(doc, "2. 用户数量统计：");
        POIWordUtil.insertTextLeft(doc,
                "截止" + ymd[0] + "年" + ymd[1] + "月" + ymd[2] + "日,平台用户总人数为" + currentActiveUserCount + "人。");
        POIWordUtil.insertTextLeft(doc, "新增用户:" + (currentActiveUserCount - lastActiveUserCount) + "。");
        POIWordUtil.insertTextLeftBold(doc, "3. 用户登录次数统计：");
        // 欠一张图
        POIWordUtil.insertTextLeftBold(doc, "4. APP总量统计：");
        POIWordUtil.insertTextLeft(doc, "目前平台可用APP数量为：" + taskMapper.getAppCount() + "个");
        POIWordUtil.insertTextLeft(doc, "本周新上线APP数量为：" + (taskMapper.getAppCount() - taskMapper.getLastAppCount()));
        POIWordUtil.insertTextLeftBold(doc, "5. App使用统计：");
        // 欠一张图
        POIWordUtil.insertTextLeftBold(doc, "6. 用户浏览器统计");
        // 欠一张图
        POIWordUtil.insertTextLeftBold(doc, "7. 数据量统计：");
        POIWordUtil.insertTextLeft(doc, "数据上传数量为：56849.57MB，共629个数据。");
    }


}

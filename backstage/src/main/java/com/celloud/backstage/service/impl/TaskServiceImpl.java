package com.celloud.backstage.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;

import com.celloud.backstage.mapper.TaskMapper;
import com.celloud.backstage.mapper.WeekMapper;
import com.celloud.backstage.model.Week;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.TaskService;
import com.celloud.backstage.utils.DateUtil;
import com.celloud.backstage.utils.EmailUtils;
import com.celloud.backstage.utils.FileTools;
import com.celloud.backstage.utils.JFreeChartUtil;
import com.celloud.backstage.utils.POIWordUtil;
import com.celloud.backstage.utils.PropertiesUtil;
import com.celloud.backstage.utils.SSHUtil;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private WeekMapper weekMapper;

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
    public int sendWeekStatistics(String colonyUsed) {
        // word保存路径
        String basePath = PropertiesUtil.weeklyReportPath + DateUtil.getDay(-1, Calendar.MONDAY).split("\\-")[0]
                + DateUtil.getDay(-1, Calendar.MONDAY).split("\\-")[1]
                + DateUtil.getDay(-1, Calendar.MONDAY).split("\\-")[2] + File.separator;
        // 首先检查上传图片资源是否够4个
        File file = new File(basePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] files = file.listFiles();
        int i = 0;
        for (File f : files) {
            if (f.getName().startsWith("u")) {
                i++;
            }
        }
        if (i < 4) {
            return -1;
        }

        try {
            // 插入上一周数据, 并发送word
            String day = DateUtil.getDay(-1, Calendar.MONDAY);
            Week week = weekMapper.findByDay(day);
            if (week == null) {
                week = new Week();
                // 获取上一周数据
                // 获取历史周统计
                Map<String, Object> lastWeekUserLogin = taskMapper.getLastWeekUserLogin(0,
                        PropertiesUtil.testAccountIds);
                Map<String, Object> lastWeekActiveUser = taskMapper.getLastWeekActiveUser(0,
                        PropertiesUtil.testAccountIds);
                Map<String, Object> lastWeekAppRun = taskMapper.getLastWeekAppRun(0, PropertiesUtil.testAccountIds);
                Map<String, Object> lastWeekAppActive = taskMapper.getLastWeekAppActive(0,
                        PropertiesUtil.testAccountIds);
                Map<String, Object> lastWeekDataSize = taskMapper.getLastWeekDataSize(0, PropertiesUtil.testAccountIds);
                String startDate = (String) lastWeekUserLogin.get("start_date");
                String endDate = (String) lastWeekUserLogin.get("end_date");
                Object logCount = lastWeekUserLogin.get("log_count");
                Object activeUser = lastWeekActiveUser.get("active_user");
                Object runApp = lastWeekAppRun.get("run_app");
                Object activeApp = lastWeekAppActive.get("active_app");
                Object sizeSum = lastWeekDataSize.get("size_sum");
                Object fileCount = lastWeekDataSize.get("file_count");

                week.setStartDate(startDate != null ? new SimpleDateFormat("yyyy-MM-dd").parse(startDate) : null);
                week.setEndDate(endDate != null ? new SimpleDateFormat("yyyy-MM-dd").parse(endDate.toString()) : null);
                week.setLoginCount(Integer.parseInt(
                        logCount != null ? logCount.toString() : "0"));
                week.setActiveUser(Integer.parseInt(
                        activeUser != null ? activeUser.toString() : "0"));
                week.setAppRun(Integer.parseInt(
                        runApp != null ? runApp.toString() : "0"));
                week.setActiveApp(Integer.parseInt(
                        activeApp != null ? activeApp.toString() : "0"));
                week.setDataSize(FileTools.formatFullFileSize(Float.parseFloat(
                        sizeSum != null ? sizeSum.toString() : "0")));
                week.setFileCount(Integer.parseInt(
                        fileCount != null ? fileCount.toString() : "0"));
                week.setColonyUsed(colonyUsed != null ? colonyUsed : "0%");
                weekMapper.insertSelective(week);
                // 创建word
                String filePath = basePath + "CelLoud数据统计" + DateUtil.getDay(-1, Calendar.MONDAY) + ".docx";
                createWord(basePath, filePath, colonyUsed);
                // 发送word
                sendWord(filePath);
                return 1;
            } else {
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 2;
    }

    private void sendWord(String filePath) {
        String title = "CelLoud数据统计" + DateUtil.getDay(-1, Calendar.MONDAY);
        Integer companyCount = taskMapper.findCompanyCount();
        String content = "各位领导：<br/>&nbsp;&nbsp;附件是" + DateUtil.getDay(-1, Calendar.MONDAY) + "至"
                + DateUtil.getDay(0, Calendar.SUNDAY)
                + ",CelLoud平台统计，请查收。<br/>" + "&nbsp;&nbsp;<font style='color:red;'>目前平台共有" + companyCount
                + "家医院在线</font>";
        // .addCc("lihuihuan@celloud.cn", "qiangyubiao@celloud.cn")
        EmailUtils.getInstance()
                .addTo(new String[] { "miaoqi@celloud.cn" })
                .setTitle(title).setContent(content).attach(filePath)
                .send();
    }

    private void createWord(String basePath, String filePath, String colonyUsed) {
        XWPFDocument doc = new XWPFDocument();
        List<Map<String, Object>> lastWeekUserLogin = taskMapper.getLastWeekUserLoginTop(PropertiesUtil.testAccountIds);
        List<Map<String, Object>> lastWeekAppRun = taskMapper.getLastWeekAppRunTop(PropertiesUtil.testAccountIds);
        List<Map<String, Object>> lastWeekDataSize = taskMapper.getLastWeekDataSizeTop(PropertiesUtil.testAccountIds);
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
                temp.put("sizeSum", FileTools.formatFullFileSize(sizeSum));
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
        // 欠2张图
        /*-------------------------图1-------------------------*/
        // 获取每天的登录次数
        List<Map<String, Integer>> loginDay = taskMapper.findLoginCountByDay(PropertiesUtil.testAccountIds);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map<String, Integer> map : loginDay) {
            dataset.setValue(map.get("login_count"), map.get("login_date"), map.get("login_date"));
        }
        String title = "登录次数";
        String categoryAxisLabel = "";
        String valueAxisLabel = "";
        String picPath = basePath + "1.jpeg";
        File file = new File(picPath);
        if (file.exists()) {
            file.delete();
        }
        int width = 1024;
        int height = 768;
        JFreeChartUtil.drawVerticalBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, picPath, width,
                height);
        POIWordUtil.insertPicture(doc, picPath, 450, 300);
        /*-------------------------图2-------------------------*/
        // 获取每个用户的登录次数
        List<Map<String, Integer>> loginUsername = taskMapper.findLoginCountByUsername(PropertiesUtil.testAccountIds);
        dataset = new DefaultCategoryDataset();
        for (Map<String, Integer> map : loginUsername) {
            dataset.setValue(map.get("login_count"), map.get("username"), map.get("username"));
        }
        title = "登录次数";
        categoryAxisLabel = "";
        valueAxisLabel = "";
        picPath = basePath + "2.jpeg";
        file = new File(picPath);
        if (file.exists()) {
            file.delete();
        }
        width = 768;
        height = 1024;
        JFreeChartUtil.drawHorizontalBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, picPath, width,
                height);
        POIWordUtil.insertPicture(doc, picPath, 450, 600);
        /*----------------------------------------------------*/
        POIWordUtil.insertTextLeftBold(doc, "4. APP总量统计：");
        POIWordUtil.insertTextLeft(doc, "目前平台可用APP数量为：" + taskMapper.getAppCount() + "个");
        POIWordUtil.insertTextLeft(doc, "本周新上线APP数量为：" + (taskMapper.getAppCount() - taskMapper.getLastAppCount()));
        POIWordUtil.insertTextLeftBold(doc, "5. App使用统计：");
        // 欠3张图
        /*-------------------------图3-------------------------*/
        // 获取每天的登录次数
        List<Map<String, Integer>> runDay = taskMapper.findAppRunCountByDay(PropertiesUtil.testAccountIds);
        dataset = new DefaultCategoryDataset();
        for (Map<String, Integer> map : runDay) {
            dataset.setValue(map.get("run_count"), map.get("run_date"), map.get("run_date"));
        }
        title = "运行次数";
        categoryAxisLabel = "";
        valueAxisLabel = "";
        picPath = basePath + "3.jpeg";
        file = new File(picPath);
        if (file.exists()) {
            file.delete();
        }
        width = 1024;
        height = 768;
        JFreeChartUtil.drawVerticalBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, picPath, width, height);
        POIWordUtil.insertPicture(doc, picPath, 450, 300);
        /*-------------------------图4-------------------------*/
        POIWordUtil.pageBreak(doc);
        // 获取每天的登录次数
        List<Map<String, Object>> appRunByUsername = taskMapper
                .findAppRunCountByUsername(PropertiesUtil.testAccountIds);
        dataset = new DefaultCategoryDataset();
        for (Map<String, Object> map : appRunByUsername) {
            dataset.setValue(Integer.parseInt(map.get("run_count").toString()),
                    map.get("user_app").toString().split("\\^")[0] + "    "
                            + map.get("user_app").toString().split("\\^")[1],
                    map.get("user_app").toString().split("\\^")[0] + "    "
                            + map.get("user_app").toString().split("\\^")[1]);
        }
        title = "运行次数, 按Username";
        categoryAxisLabel = "";
        valueAxisLabel = "";
        picPath = basePath + "4.jpeg";
        file = new File(picPath);
        if (file.exists()) {
            file.delete();
        }
        width = 768;
        height = 1024;
        JFreeChartUtil.drawHorizontalBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, picPath, width,
                height);
        POIWordUtil.insertPicture(doc, picPath, 450, 600);
        /*-------------------------图5-------------------------*/
        // 获取每个用户的登录次数
        List<Map<String, Integer>> appRunCount = taskMapper.findAppRunCount(PropertiesUtil.testAccountIds);
        dataset = new DefaultCategoryDataset();
        for (Map<String, Integer> map : appRunCount) {
            dataset.setValue(map.get("run_count"), map.get("app_name"), map.get("app_name"));
        }
        title = "运行次数";
        categoryAxisLabel = "";
        valueAxisLabel = "";
        picPath = basePath + "5.jpeg";
        file = new File(picPath);
        if (file.exists()) {
            file.delete();
        }
        width = 768;
        height = 1024;
        JFreeChartUtil.drawHorizontalBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, picPath, width,
                height);
        POIWordUtil.insertPicture(doc, picPath, 450, 600);
        POIWordUtil.insertTextLeftBold(doc, "6. 用户浏览器统计");
        // 欠1张图
        /*-------------------------图6-------------------------*/
        POIWordUtil.pageBreak(doc);
        // 获取每天的登录次数
        List<Map<String, Object>> osBrowser = taskMapper.findOsBrowser(PropertiesUtil.testAccountIds);
        dataset = new DefaultCategoryDataset();
        for (Map<String, Object> map : osBrowser) {
            dataset.setValue(Integer.parseInt(map.get("os_browser_count").toString()),
                    map.get("os_browser").toString().split("\\^")[0] + "    "
                            + map.get("os_browser").toString().split("\\^")[1],
                    map.get("os_browser").toString().split("\\^")[0] + "    "
                            + map.get("os_browser").toString().split("\\^")[1]);
        }
        title = "浏览器";
        categoryAxisLabel = "";
        valueAxisLabel = "";
        picPath = basePath + "6.jpeg";
        file = new File(picPath);
        if (file.exists()) {
            file.delete();
        }
        width = 768;
        height = 1024;
        JFreeChartUtil.drawHorizontalBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, picPath, width,
                height);
        POIWordUtil.insertPicture(doc, picPath, 450, 600);

        POIWordUtil.insertTextLeftBold(doc, "7. 数据量统计：");
        // 获取文件大小和文件数量
        Map<String, Object> map = taskMapper.getFileSizeAndCount(PropertiesUtil.testAccountIds);
        POIWordUtil.insertTextLeft(doc,
                "数据上传数量为：" + FileTools.formatMBFileSize(Float.parseFloat(map.get("fileSize").toString())) + "，共"
                        + map.get("fileCount") + "个数据。");
        // 欠3张图
        /*-------------------------图7-------------------------*/
        // 获取每个用户的登录次数
        List<Map<String, Object>> fileSize = taskMapper.findFileSizeByUsername(PropertiesUtil.testAccountIds);
        dataset = new DefaultCategoryDataset();
        for (Map<String, Object> fileSizeMap : fileSize) {
            dataset.setValue(
                    Float.parseFloat(
                            String.format("%.2f",
                                    Float.parseFloat(fileSizeMap.get("file_size").toString()) / (1024 * 1024))),
                    fileSizeMap.get("username").toString(),
                    fileSizeMap.get("username").toString());
        }
        title = "数据大小MB";
        categoryAxisLabel = "";
        valueAxisLabel = "";
        picPath = basePath + "7.jpeg";
        file = new File(picPath);
        if (file.exists()) {
            file.delete();
        }
        width = 768;
        height = 1024;
        JFreeChartUtil.drawHorizontalBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, picPath, width,
                height);
        POIWordUtil.insertPicture(doc, picPath, 450, 600);
        /*-------------------------图8-------------------------*/
        // 获取每个用户的登录次数
        List<Map<String, Object>> fileCount = taskMapper.findFileCountByUsername(PropertiesUtil.testAccountIds);
        dataset = new DefaultCategoryDataset();
        for (Map<String, Object> fileCountMap : fileCount) {
            dataset.setValue(Integer.parseInt(fileCountMap.get("file_count").toString()),
                    fileCountMap.get("username").toString(), fileCountMap.get("username").toString());
        }
        title = "数据个数";
        categoryAxisLabel = "";
        valueAxisLabel = "";
        picPath = basePath + "8.jpeg";
        file = new File(picPath);
        if (file.exists()) {
            file.delete();
        }
        width = 768;
        height = 1024;
        JFreeChartUtil.drawHorizontalBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, picPath, width,
                height);
        POIWordUtil.insertPicture(doc, picPath, 450, 600);
        /*-------------------------图9-------------------------*/
        // 获取每个用户的登录次数
        List<Map<String, Object>> fileSizeDay = taskMapper.findFileSizeByDay(PropertiesUtil.testAccountIds);
        dataset = new DefaultCategoryDataset();
        for (Map<String, Object> fileSizeDayMap : fileSizeDay) {
            dataset.setValue(
                    Float.parseFloat(String.format("%.2f",
                            Float.parseFloat(fileSizeDayMap.get("file_size").toString()) / (1024 * 1024 * 1024))),
                    fileSizeDayMap.get("file_date").toString(), fileSizeDayMap.get("file_date").toString());
        }
        title = "数据大小GB";
        categoryAxisLabel = "";
        valueAxisLabel = "";
        picPath = basePath + "9.jpeg";
        file = new File(picPath);
        if (file.exists()) {
            file.delete();
        }
        width = 1024;
        height = 768;
        JFreeChartUtil.drawVerticalBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, picPath, width,
                height);
        POIWordUtil.insertPicture(doc, picPath, 450, 300);

        POIWordUtil.insertTextLeftBold(doc, "8. 磁盘实际使用情况统计：");
        SSHUtil sshUtil = new SSHUtil("121.201.7.200", 6101, "celloud", "CelLoud1010");
        InputStream in = sshUtil.getResult("df -h");
        BufferedReader br = null;
        try {
            if (in != null) {
                br = new BufferedReader(new InputStreamReader(in));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.contains("Mounted") || line.contains("/share/data")) {
                        line = line.substring(line.indexOf("  ")).trim();
                        POIWordUtil.insertTextLeft(doc, line);
                    }
                }
            } else {
                POIWordUtil.insertTextLeft(doc, "暂无数据");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                br.close();
                in.close();
                sshUtil.release();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        POIWordUtil.insertTextLeftBold(doc, "9. 项目报告和数据报告数量");
        POIWordUtil.insertTextLeft(doc, "项目报告总数：" + taskMapper.getWeekReportCountByFlag(1) + "。");
        POIWordUtil.insertTextLeft(doc, "数据报告总数：" + taskMapper.getWeekReportCountByFlag(0) + "。");
        POIWordUtil.insertTextLeftBold(doc, "10. 客户端统计");
        POIWordUtil.insertTextLeftBold(doc, "  数据上传统计：", 10);
        // 欠1张图
        /*-------------------------图10-------------------------*/
        // 获取每个用户的登录次数
        List<Map<String, Object>> fileCountClient = taskMapper.findFileCountByClient(PropertiesUtil.testAccountIds);
        dataset = new DefaultCategoryDataset();
        for (Map<String, Object> fileCountClientMap : fileCountClient) {
            dataset.setValue(
                    Integer.parseInt(fileCountClientMap.get("file_count").toString()),
                    fileCountClientMap.get("username").toString(), fileCountClientMap.get("username").toString());
        }
        title = "数据个数";
        categoryAxisLabel = "";
        valueAxisLabel = "";
        picPath = basePath + "10.jpeg";
        file = new File(picPath);
        if (file.exists()) {
            file.delete();
        }
        width = 768;
        height = 1024;
        JFreeChartUtil.drawHorizontalBarChart(title, categoryAxisLabel, valueAxisLabel, dataset, picPath, width,
                height);
        POIWordUtil.insertPicture(doc, picPath, 450, 600);

        POIWordUtil.insertTextLeftBold(doc, "11. 集群使用统计");
        // 欠2张上传图
        POIWordUtil.insertPicture(doc, basePath + "u1.png", 150, 120);
        POIWordUtil.insertPicture(doc, basePath + "u2.png", 400, 180);
        POIWordUtil.insertTextLeftBold(doc, "存储IO情况", 10);
        // 欠2张上传图
        POIWordUtil.insertPicture(doc, basePath + "u3.png", 400, 80);
        POIWordUtil.insertPicture(doc, basePath + "u4.png", 400, 80);
        POIWordUtil.insertTextLeftBold(doc, "历史比较", 10);
        List<Week> weeks = weekMapper.findCurrentYearAll();
        XWPFTable historyTable = doc.createTable(weeks.size() + 1, 8);
        POIWordUtil.setCellTextBoldCenter(historyTable, 0, 0, "历史日期");
        POIWordUtil.setCellTextBoldCenter(historyTable, 0, 1, "登录次数");
        POIWordUtil.setCellTextBoldCenter(historyTable, 0, 2, "活跃用户数");
        POIWordUtil.setCellTextBoldCenter(historyTable, 0, 3, "APP运行次数");
        POIWordUtil.setCellTextBoldCenter(historyTable, 0, 4, "活跃APP");
        POIWordUtil.setCellTextBoldCenter(historyTable, 0, 5, "数据大小");
        POIWordUtil.setCellTextBoldCenter(historyTable, 0, 6, "数据个数");
        POIWordUtil.setCellTextBoldCenter(historyTable, 0, 7, "集群使用率");
        for (int i = 1; i <= weeks.size(); i++) {
            POIWordUtil.setCellTextCenter(historyTable, i, 0,
                    weeks.get(i - 1).getStartDate() != null
                            ? new SimpleDateFormat("yyyy-MM-dd").format(weeks.get(i - 1).getStartDate()) : "无");
            POIWordUtil.setCellTextCenter(historyTable, i, 1,
                    weeks.get(i - 1).getLoginCount() != null ? weeks.get(i - 1).getLoginCount().toString() : "0");
            POIWordUtil.setCellTextCenter(historyTable, i, 2,
                    weeks.get(i - 1).getActiveUser() != null ? weeks.get(i - 1).getActiveUser().toString() : "0");
            POIWordUtil.setCellTextCenter(historyTable, i, 3,
                    weeks.get(i - 1).getAppRun() != null ? weeks.get(i - 1).getAppRun().toString() : "0");
            POIWordUtil.setCellTextCenter(historyTable, i, 4,
                    weeks.get(i - 1).getActiveApp() != null ? weeks.get(i - 1).getActiveApp().toString() : "0");
            POIWordUtil.setCellTextCenter(historyTable, i, 5,
                    weeks.get(i - 1).getDataSize() != null ? weeks.get(i - 1).getDataSize().toString() : "0B");
            POIWordUtil.setCellTextCenter(historyTable, i, 6,
                    weeks.get(i - 1).getFileCount() != null ? weeks.get(i - 1).getFileCount().toString() : "0");
            POIWordUtil.setCellTextCenter(historyTable, i, 7,
                    weeks.get(i - 1).getColonyUsed() != null ? weeks.get(i - 1).getColonyUsed().toString() : "0%");
        }

        // List<Map<String, Object>> userLogin =
        // taskMapper.getUserLoginTop(PropertiesUtil.testAccountIds);
        // List<Map<String, Object>> appRun =
        // taskMapper.getAppRunTop(PropertiesUtil.testAccountIds);
        // List<Map<String, Object>> dataSize =
        // taskMapper.getDataSizeTop(PropertiesUtil.testAccountIds);
        // List<Map<String, Object>> data = new ArrayList<Map<String,
        // Object>>();
        // for (int i = 0; i < 10; i++) {
        // Map<String, Object> temp = new LinkedHashMap<String, Object>();
        // if (userLogin.size() > i) {
        // temp.put("logUsername", userLogin.get(i).get("username"));
        // temp.put("logCount", userLogin.get(i).get("log_count"));
        // } else {
        // temp.put("logUsername", "无");
        // temp.put("logCount", 0);
        // }
        // if (appRun.size() > i) {
        // temp.put("appName", appRun.get(i).get("app_name"));
        // temp.put("appCount", appRun.get(i).get("app_count"));
        // } else {
        // temp.put("appName", "无");
        // temp.put("appCount", 0);
        // }
        // if (dataSize.size() > i) {
        // temp.put("sizeUsername", dataSize.get(i).get("username"));
        // Float sizeSum =
        // Float.parseFloat(dataSize.get(i).get("size_sum").toString());
        // temp.put("sizeSum", FileTools.formatFileSize(sizeSum));
        // temp.put("fileCount", dataSize.get(i).get("file_count"));
        // } else {
        // temp.put("sizeUsername", "无");
        // temp.put("sizeSum", 0);
        // temp.put("fileCount", 0);
        // }
        // data.add(i, temp);
        // }

        // POIWordUtil.pageBreak(doc);
        // POIWordUtil.insertText(doc, "总量统计", ParagraphAlignment.CENTER, 22);
        // POIWordUtil.insertTextLeftBold(doc, "1. 平台Top10使用统计");
        // table = POIWordUtil.createTable(doc, 12, 7, "2200");
        // POIWordUtil.mergeCellsHorizontal(table, 0, 0, 1);
        // POIWordUtil.mergeCellsHorizontal(table, 0, 2, 3);
        // POIWordUtil.mergeCellsHorizontal(table, 0, 4, 6);
        // POIWordUtil.setCellTextBoldCenter(table, 0, 0, "前10活跃用户及登录次数");
        // POIWordUtil.setCellTextBoldCenter(table, 0, 2, "前10活跃APP及运行次数");
        // POIWordUtil.setCellTextBoldCenter(table, 0, 4, "数据量前10用户及数据大小");
        // POIWordUtil.setCellTextBoldCenter(table, 1, 0, "username");
        // POIWordUtil.setCellTextBoldCenter(table, 1, 1, "登陆次数");
        // POIWordUtil.setCellTextBoldCenter(table, 1, 2, "APP");
        // POIWordUtil.setCellTextBoldCenter(table, 1, 3, "运行次数");
        // POIWordUtil.setCellTextBoldCenter(table, 1, 4, "username");
        // POIWordUtil.setCellTextBoldCenter(table, 1, 5, "数据大小");
        // POIWordUtil.setCellTextBoldCenter(table, 1, 6, "文件数量");
        // POIWordUtil.fillTableByMap(table, 2, data);
        // Date date = new Date();
        POIWordUtil.saveDocument(doc, filePath);
    }

}

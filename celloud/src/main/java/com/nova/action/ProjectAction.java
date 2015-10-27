package com.nova.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.mongo.sdo.CmpGeneDetectionDetail;
import com.mongo.sdo.CmpGeneSnpResult;
import com.mongo.sdo.CmpReport;
import com.mongo.sdo.GeneDetectionResult;
import com.mongo.service.ReportService;
import com.mongo.service.ReportServiceImpl;
import com.nova.constants.Mod;
import com.nova.constants.SparkPro;
import com.nova.email.EmailProjectEnd;
import com.nova.email.EmailService;
import com.nova.itext.PGSProjectPDF;
import com.nova.pager.PageList;
import com.nova.portpool.PortPool;
import com.nova.queue.GlobalQueue;
import com.nova.sdo.Company;
import com.nova.sdo.Data;
import com.nova.sdo.DataType;
import com.nova.sdo.Dept;
import com.nova.sdo.Project;
import com.nova.sdo.ProjectParam;
import com.nova.sdo.Report;
import com.nova.sdo.Software;
import com.nova.sdo.User;
import com.nova.service.ICompanyService;
import com.nova.service.IDataService;
import com.nova.service.IDataTypeService;
import com.nova.service.IDeptService;
import com.nova.service.IProjectService;
import com.nova.service.IReportService;
import com.nova.service.ISoftwareService;
import com.nova.service.IUserService;
import com.nova.utils.Base64Util;
import com.nova.utils.DateUtil;
import com.nova.utils.FileTools;
import com.nova.utils.PerlUtils;
import com.nova.utils.PropertiesUtil;
import com.nova.utils.RemoteRequests;
import com.nova.utils.SQLUtils;
import com.nova.utils.SSHUtil;
import com.nova.utils.TemplateUtil;
import com.nova.utils.XmlUtil;

/**
 * 项目管理
 * 
 * @author <a href="mailto:liuqingxiao@novacloud.com">liuqx</a>
 * @date 2013-5-30上午08:54:48
 * @version Revision: 1.0
 */
@ParentPackage("celloud-default")
@Action("project")
@Results({
        @Result(name = "RunProject", type = "json", params = { "root", "error" }),
        @Result(name = "success", type = "json", params = { "root", "userNames" }),
        @Result(name = "returnBoolean", type = "json", params = { "root",
                "flag" }),
        @Result(name = "toSaveRunedCmp", type = "json", params = { "root",
                "flag" }) })
public class ProjectAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(ProjectAction.class);
    @Inject
    private IProjectService projectService;
    @Inject
    private IUserService userService;
    @Inject
    private IDataService dataService;
    @Inject
    private IDeptService deptService;
    @Inject
    private IReportService reportService;
    @Inject
    private IDataTypeService dataTypeService;
    @Inject
    private ISoftwareService softwareService;
    @Inject
    private ICompanyService companyService;
    private Integer userId;
    private int userid;
    private String userNames;
    private int shareUserId;
    private int dataSize;
    private Project project;
    private String projectName;
    private int dataId;
    private boolean flag;
    private int result;
    private String dataIds;
    private List<Project> proList;
    private Data data;
    // 共享项目时的提示内容
    private String errorMessage = "";
    private Map<String, List<Map<String, String>>> dataTypeMap;// 数据类型列表信息
    private List<Map<String, String>> strainMapList;
    private List<Map<String, String>> proMapList;
    private String appName;
    private String projectId;
    private ProjectParam proParam;
    private Map<String, Object> proParams;
    private String softwareId;
    private int error;
    private PageList<Project> myProNamePageList;
    private PageList<Project> sharedProNamePageList;
    private List<Data> proDataList;// 项目中的数据信息
    private String strain;
    private PageList<Project> privaProPageList;// 所有项目列表
    private PageList<Project> shareProPageList;// 共享给用户的项目列表
    private List<Project> proNameList;// 用户的项目名称列表
    private int sortByType;// 排序类型 1：按项目名称排序，2：按启动时间排序

    private static String dataPath = PropertiesUtil.bigFilePath;
    private static String datalist = PropertiesUtil.datalist;

    // TODO 需要投递到spark集群的app
    private static final List<String> apps = Arrays.asList("");
    // 初始化perl命令路径
    private static Map<String, String> perlMap = new HashMap<>();
    // 初始化 APP 项目报告的 Title
    private static Map<Integer, String> titleMap = new HashMap<>();
    static {
        SQLUtils sql = new SQLUtils();
        List<com.celloud.sdo.App> list = sql.getAllSoftware();
        for (com.celloud.sdo.App software : list) {
            perlMap.put("" + software.getSoftwareId(), software.getCommand());
        }
        // 81 | MDA |
         titleMap.put( 81, "dataName\tdataKey\tTotal_Reads\tDuplicate\tMap_Reads\tMap_Ratio(%)\twin_size\t\n");
         // 95 | NIPT |
         titleMap.put(95, "dataName\tdataKey\tAnotherName\tChr13\tChr18\tChr21\n");
         // 83 | gDNA |
         titleMap.put(83, "dataName\tdataKey\tTotal_Reads\tDuplicate\tMap_Reads\tMap_Ratio(%)\twin_size\t\n");
         // 92 | gDNA_mosaic |
         titleMap.put( 92, "dataName\tdataKey\tAnotherName\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\n");
         //101 | gDNA_mosaic_1 |
         titleMap.put( 101, "dataName\tdataKey\tAnotherName\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\n");
         // 93 | MDA_mosaic |
         titleMap.put( 93, "dataName\tdataKey\tAnotherName\tTotal_Reads\tMT_ratio\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\n");
         // 91 | MDA_HR |
         titleMap.put( 91, "dataName\tdataKey\tAnotherName\tTotal_Reads\tMT_ratio\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\n");
         // 94 | SurePlex |
         titleMap.put( 94, "dataName\tdataKey\tAnotherName\tTotal_Reads\tMT_ratio\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\n");
         // 104 | Sureplex_HR |
         titleMap.put( 104, "dataName\tdataKey\tAnotherName\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\n");
         // 87 | gDNA_MR |
         titleMap.put( 87, "dataName\tdataKey\tAnotherName\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\n");
         // 88 | MDA_MR |
         titleMap.put( 88, "dataName\tdataKey\tAnotherName\tTotal_Reads\tMT_ratio\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\t*SD\n");
         // 85 | MalBac |
         titleMap.put( 85, "dataName\tdataKey\tAnotherName\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate(%)\tGC_Count(%)\n");
         // 86 | gDNA_HR |
         titleMap.put( 86, "dataName\tdataKey\tAnotherName\tTotal_Reads\tMap_Reads\tMap_Ratio(%)\tDuplicate\tGC_Count(%)\n");
    }
    
    private static Map<String, Map<String, String>> machines = XmlUtil.machines;
    private static String sparkhost = machines.get("spark").get(Mod.HOST);
    private static String sparkpwd = machines.get("spark").get(Mod.PWD);
    private static String sparkuserName = machines.get("spark").get(
            Mod.USERNAME);
    private static String basePath = SparkPro.TOOLSPATH;

    public String toSaveRunedCmp() {
        String source = "/share/data/webapps/Tools/upload/88/110/";
        File upload = new File(source);
        File[] files = upload.listFiles();
        for (File f : files) {
            String data1 = f.getName();
            if (f.isDirectory() && data1.length() > 7) {
                System.out.println("--------------------" + data1
                        + "----------");
                String data2 = "";
                if (data1.equals("20150609090539")) {
                    data2 = "20150609309453";
                } else if (data1.equals("20150609648660")) {
                    data2 = "20150609886427";
                } else if (data1.equals("20150609918308")) {
                    data2 = "20150609127108";
                } else if (data1.equals("20150610114501")) {
                    data2 = "20150610888196";
                } else if (data1.equals("20150626658029")) {
                    data2 = "20150626745382";
                } else if (data1.equals("20150717570992")) {
                    data2 = "20150717944085";
                } else if (data1.equals("20150720053040")) {
                    data2 = "20150720461290";
                } else if (data1.equals("20150817490068")) {
                    data2 = "20150817188431";
                } else if (data1.equals("20150817593035")) {
                    data2 = "20150817496277";
                }

                List<Data> dataList = dataService.getDataByDataKeys(data1 + ","
                        + data2, 88);
                List<com.mongo.sdo.Data> dList = new ArrayList<com.mongo.sdo.Data>();
                for (Data d : dataList) {
                    com.mongo.sdo.Data d1 = new com.mongo.sdo.Data();
                    d1.setAnotherName(d.getAnotherName());
                    d1.setDataKey(d.getDataKey());
                    d1.setDataTags(d.getDataTags());
                    d1.setFileId(d.getFileId());
                    d1.setFileName(d.getFileName());
                    d1.setSample(d.getSample());
                    d1.setSize(d.getSize());
                    d1.setStrain(d.getStrain());
                    d1.setUserId(88);
                    dList.add(d1);

                }
                String finalPath = "/share/data/webapps/Tools/upload/88/110/"
                        + data1;

                // -----读取报告内容并保存到mongoDB------
                CmpReport cmpReport = new CmpReport();
                cmpReport.setDataKey(data1);
                cmpReport.setAppId(110);
                cmpReport.setAppName("CMP");
                cmpReport.setData(dList);
                Company c = companyService.getCompanyByUserId(88);
                cmpReport.setCompanyId(c.getCompanyId());
                cmpReport.setCompanyName(c.getCompanyName());
                cmpReport.setCompanyEngName(c.getEnglishName());
                cmpReport.setCompanyAddr(c.getAddress());
                cmpReport.setCompanyEnAddr(c.getEnglishName());
                cmpReport.setCompanyIcon(c.getCompanyIcon());
                cmpReport.setCompanyTel(c.getTel());
                cmpReport.setZipCode(c.getZipCode());
                User user = userService.getUserById(88);
                cmpReport.setUserId(user.getUserId());
                cmpReport.setUsername(user.getUsername());
                cmpReport.setEmail(user.getEmail());
                cmpReport.setDeptName("默认部门");
                cmpReport.setCreateDate(new Date());
                String logPath = finalPath + "/LOG.txt";
                String statisPath = finalPath + "/result/statistic.xls";
                String avgPath = finalPath + "/result/average.info";
                String snpPath = finalPath + "/result/snp_num.info";
                String[] snpArr = { "ABL1", "EGFR", "GNAQ", "KRAS", "PTPN11",
                        "AKT1", "ERBB2", "GNAS", "MET", "RB1", "ALK", "ERBB4",
                        "HNF1A", "MLH1", "RET", "APC", "EZH2", "HRAS", "MPL",
                        "SMAD4", "ATM", "FBXW7", "IDH1", "NOTCH1", "SMARCB1",
                        "BRAF", "FGFR1", "IDH2", "NPM1", "SMO", "CDH1",
                        "FGFR2", "JAK2", "NRAS", "SRC", "CDKN2A", "FGFR3",
                        "JAK3", "PDGFRA", "STK11", "CSF1R", "FLT3", "KDR",
                        "PIK3CA", "TP53", "CTNNB1", "GNA11", "KIT", "PTEN",
                        "VHL" };
                // 二、1 基因检测结果
                List<GeneDetectionResult> cmpGeneResult = new ArrayList<GeneDetectionResult>();
                if (new File(snpPath).exists()) {
                    List<String> list_ = new ArrayList<String>();
                    try {
                        list_ = FileUtils.readLines(new File(snpPath), "GBK");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (list_ != null) {
                        for (int z = 0; z < list_.size(); z++) {
                            String[] line_i = list_.get(z).split("\t");
                            GeneDetectionResult gdr = new GeneDetectionResult();
                            gdr.setGeneName(FileTools.getArray(line_i, 0));
                            gdr.setKnownMSNum(FileTools.getArray(line_i, 1));
                            gdr.setSequencingDepth(Integer.parseInt(FileTools
                                    .getArray(line_i, 2)));
                            cmpGeneResult.add(gdr);
                        }
                    }
                }
                cmpReport.setCmpGeneResult(cmpGeneResult);
                // 二、2
                if (new File(logPath).exists()) {
                    cmpReport.setRunDate(FileTools.getFirstLine(logPath)
                            .substring(1, 11));
                }
                if (new File(statisPath).exists()) {
                    List<String> list_ = new ArrayList<String>();
                    try {
                        list_ = FileUtils
                                .readLines(new File(statisPath), "GBK");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cmpReport.setAllFragment(FileTools.listIsNull(list_, 0));
                    cmpReport.setAvgQuality(FileTools.listIsNull(list_, 1));
                    cmpReport.setAvgGCContent(FileTools.listIsNull(list_, 2));
                    cmpReport.setUsableFragment(FileTools.listIsNull(list_, 3));
                    cmpReport.setNoDetectedGene(FileTools.listIsNull(list_, 4));
                    cmpReport.setDetectedGene(FileTools.listIsNull(list_, 5));
                }
                if (new File(avgPath).exists()) {
                    cmpReport.setAvgCoverage(FileTools.getFirstLine(avgPath));
                }
                // 五
                Map<String, CmpGeneDetectionDetail> geneDetectionDetail = new HashMap<String, CmpGeneDetectionDetail>();
                for (String snpName : snpArr) {
                    String spath = finalPath + "/result/" + snpName + ".snp";
                    List<String> list_ = new ArrayList<String>();
                    try {
                        if (new File(spath).exists()) {
                            list_ = FileUtils.readLines(new File(spath), "GBK");
                            CmpGeneDetectionDetail gdd = new CmpGeneDetectionDetail();
                            String avgSeqDepth = "";
                            List<CmpGeneSnpResult> result = new ArrayList<CmpGeneSnpResult>();
                            for (int z = 0; z < list_.size(); z++) {
                                if (z == 0) {
                                    avgSeqDepth = list_.get(z);
                                } else {
                                    String[] line_z = list_.get(z).split("\t");
                                    CmpGeneSnpResult gsr = new CmpGeneSnpResult();
                                    gsr.setGene(FileTools.getArray(line_z, 0));
                                    gsr.setRefBase(FileTools
                                            .getArray(line_z, 1));
                                    gsr.setMutBase(FileTools
                                            .getArray(line_z, 2));
                                    gsr.setDepth(FileTools.getArray(line_z, 3));
                                    gsr.setCdsMutSyntax(FileTools.getArray(
                                            line_z, 4));
                                    gsr.setAaMutSyntax(FileTools.getArray(
                                            line_z, 5));
                                    gsr.setMutationType(FileTools.getArray(
                                            line_z, 6));
                                    result.add(gsr);
                                }
                            }

                            gdd.setAvgCoverage(avgSeqDepth);
                            gdd.setResult(result);
                            geneDetectionDetail.put(snpName, gdd);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                cmpReport.setGeneDetectionDetail(geneDetectionDetail);

                String qualityPath1 = "http://www.celloud.org/Tools/upload/88/110/"
                        + data1
                        + "/QC/"
                        + data1
                        + "_fastqc/Images/per_base_quality.png";
                cmpReport.setQualityPath1(qualityPath1);
                String seqContentPath1 = "http://www.celloud.org/Tools/upload/88/110/"
                        + data1
                        + "/QC/"
                        + data1
                        + "_fastqc/Images/per_base_sequence_content.png";
                cmpReport.setSeqContentPath1(seqContentPath1);
                String fastqcDataPath = "/share/data/webapps/Tools/upload/88/110/"
                        + data1 + "/QC/" + data1 + "_fastqc/fastqc_data.txt";
                Map<String, String> basicStatistics1 = new HashMap<String, String>();
                if (new File(fastqcDataPath).exists()) {
                    List<String> list_ = FileTools.getLineByNum(fastqcDataPath,
                            4, 10);
                    System.out.println(fastqcDataPath);
                    if (list_.size() >= 7) {
                        String[] line_1 = list_.get(0).split("\t");
                        basicStatistics1.put("Filename",
                                FileTools.getArray(line_1, 1));
                        String[] line_2 = list_.get(1).split("\t");
                        basicStatistics1.put("FileType",
                                FileTools.getArray(line_2, 1));
                        String[] line_3 = list_.get(2).split("\t");
                        basicStatistics1.put("Encoding",
                                FileTools.getArray(line_3, 1));
                        String[] line_4 = list_.get(3).split("\t");
                        basicStatistics1.put("TotalSeq",
                                FileTools.getArray(line_4, 1));
                        String[] line_5 = list_.get(4).split("\t");
                        basicStatistics1.put("FilteredSeq",
                                FileTools.getArray(line_5, 1));
                        String[] line_6 = list_.get(5).split("\t");
                        basicStatistics1.put("SeqLength",
                                FileTools.getArray(line_6, 1));
                        String[] line_7 = list_.get(6).split("\t");
                        basicStatistics1.put("gc",
                                FileTools.getArray(line_7, 1));
                    }
                    cmpReport.setBasicStatistics1(basicStatistics1);
                }
                String qualityPath2 = "";
                String seqContentPath2 = "";
                Map<String, String> basicStatistics2 = new HashMap<String, String>();
                qualityPath2 = "http://www.celloud.org/Tools/upload/88/110/"
                        + data1 + "/QC/" + data2
                        + "_fastqc/Images/per_base_quality.png";
                seqContentPath2 = "http://www.celloud.org/Tools/upload/88/110/"
                        + data1 + "/QC/" + data2
                        + "_fastqc/Images/per_base_sequence_content.png";
                String f2 = "/share/data/webapps/Tools/upload/88/110/" + data1
                        + "/QC/" + data2 + "_fastqc/fastqc_data.txt";
                System.out.println(f2);
                List<String> list_2 = FileTools.getLineByNum(f2, 4, 10);
                if (list_2.size() >= 7) {
                    String[] line_1 = list_2.get(0).split("\t");
                    basicStatistics2.put("Filename",
                            FileTools.getArray(line_1, 1));
                    String[] line_2 = list_2.get(1).split("\t");
                    basicStatistics2.put("FileType",
                            FileTools.getArray(line_2, 1));
                    String[] line_3 = list_2.get(2).split("\t");
                    basicStatistics2.put("Encoding",
                            FileTools.getArray(line_3, 1));
                    String[] line_4 = list_2.get(3).split("\t");
                    basicStatistics2.put("TotalSeq",
                            FileTools.getArray(line_4, 1));
                    String[] line_5 = list_2.get(4).split("\t");
                    basicStatistics2.put("FilteredSeq",
                            FileTools.getArray(line_5, 1));
                    String[] line_6 = list_2.get(5).split("\t");
                    basicStatistics2.put("SeqLength",
                            FileTools.getArray(line_6, 1));
                    String[] line_7 = list_2.get(6).split("\t");
                    basicStatistics2.put("gc", FileTools.getArray(line_7, 1));
                }
                cmpReport.setQualityPath2(qualityPath2);
                cmpReport.setSeqContentPath2(seqContentPath2);
                cmpReport.setBasicStatistics2(basicStatistics2);
                ReportService reportService = new ReportServiceImpl();
                Data d = dataService.getDataByKey(data1);
                List<Integer> proIds = projectService.getProIdsByFileId(d
                        .getFileId());
                for (Integer proId : proIds) {
                    cmpReport.setProjectId(proId);
                    reportService.saveCmpReport(cmpReport);
                }
            }
        }
        return "toSaveRunedCmp";
    }

    /**
     * 下载项目pdf
     */
    public String downPdf() {
        StringBuffer sb = new StringBuffer();
        sb.append(userId).append(";").append(softwareId).append(";")
                .append(projectId).append(";");
        List<Data> list = projectService.getAllFileInProject(Integer
                .parseInt(projectId));
        for (Data data : list) {
            sb.append(data.getDataKey()).append(",");
        }
        RemoteRequests rr = new RemoteRequests();
        String requestUrl = PropertiesUtil.toolsPath
                + "Procedure!downPDF?dataKeyList=" + sb.toString();
        log.info("requestUrl:" + requestUrl);
        userNames = rr.pdf(requestUrl);
        return SUCCESS;
    }

    /**
     * 在app页面运行时调用的方法
     * 
     * @return
     */
    public String run() {
        log.info("----------------运行APP Begin----------");
        // 1.新建项目
        userId = (Integer) super.session.get("userId");
        projectName = new Date().getTime() + "";
        proList = projectService.getAllProNameList(userId);
        while (existsProName(projectName)) {
            projectName = new Date().getTime() + "";
        }
        project = new Project();
        project.setUserId(userId);
        project.setProjectName(projectName);
        // 根据softwareName获取对应的数据类型
        int dataFormat = dataService.getDataById(dataIds.split(",")[0])
                .getFileFormat();
        project.setDataFormat(dataFormat);
        flag = projectService.insertProject(project);
        if (!flag) {
            error = 1;
            log.error("创建项目失败");
            return "RunProject";
        }
        // 2.根据项目名称获取项目ID
        int proId = projectService.getProjectIdByName(projectName);
        // 3.新增数据项目关系
        int isError = dataService.allocateDatasToProject(dataIds, proId);
        if (isError == 0) {
            error = 2;
            log.error("创建项目数据关系失败");
            return "RunProject";
        }
        // 4.为该项目和app添加项目报告
        boolean hasReport = reportService.hasProReport(proId,
                Integer.parseInt(softwareId));
        if (!hasReport) {
            Report report = new Report();
            report.setProjectId(proId);
            report.setUserId(userId);
            report.setSoftwareId(Integer.parseInt(softwareId));
            report.setState(1);// 1：正在运行
            report.setFlag(1);// 1:项目报告
            reportService.addReportInfo(report);
        }
        // 5.根据 appIds 获取 datakeys
        StringBuffer dataResult = new StringBuffer();
        String[] dataIdArr = dataIds.split(",");
        if (dataIdArr.length > 0) {
            for (String dataId : dataIdArr) {
                if (StringUtils.isNotEmpty(dataId)) {
                    Data data = dataService.getDataById(dataId);
                    String filename = data.getFileName();
                    String datakey = data.getDataKey();
                    // int index = filename.lastIndexOf(".");
                    String ext = FileTools.getExtName(filename);
                    dataResult
                            .append(datakey)
                            .append(",")
                            .append(datakey)
                            .append(ext)
                            .append(",")
                            .append(filename)
                            .append(",")
                            .append(StringUtils.isEmpty(data.getAnotherName()) ? null
                                    : data.getAnotherName()).append(";");
                }
            }
        }
        Map<String, List<Data>> map = new HashMap<String, List<Data>>();
        if (Integer.parseInt(softwareId) == 110
                || Integer.parseInt(softwareId) == 111
                || Integer.parseInt(softwareId) == 112) {
            String dataDetails = FileTools.dataListSort(dataResult.toString());
            String dataArray[] = dataDetails.split(";");
            for (int i = 0; i < dataArray.length; i = i + 2) {
                String[] dataDetail = dataArray[i].split(",");
                String[] dataDetail1 = dataArray[i + 1].split(",");
                List<Data> dataList = dataService.getDataByDataKeys(
                        FileTools.getArray(dataDetail, 0) + ","
                                + FileTools.getArray(dataDetail1, 0), userId);
                map.put(FileTools.getArray(dataDetail, 0), dataList);
            }
        }
        Company com = companyService.getCompanyByUserId(userId);
        User user = userService.getUserById(userId);
        Dept dept = deptService.getDeptByUser(userId);
        // 6.根据用户ID获取用户邮箱
        String email = userService.getEmailBySessionUserId(userId);
        // 7.根据软件id获取软件名称
        Software soft = softwareService.getSoftware(Integer
                .parseInt(softwareId));
        String dataKeyList = dataResult.toString();
        if (apps.contains(softwareId)) {// 判断是否需要进队列
            String select = apps.toString().substring(1,
                    apps.toString().length() - 1);
            int running = dataService.dataRunning(select);
            log.info("页面运行任务，此时正在运行的任务数：" + running);
            String appPath = basePath + userId + "/" + softwareId + "/";
            if (SparkPro.NODES >= running) {
                log.info("资源满足需求，投递任务");
                submit(appPath, proId + "", dataKeyList, appName,
                        perlMap.get(softwareId));
            } else {
                log.info("资源不满足需求，进入队列等待");
                GlobalQueue.offer(appPath + "--" + proId + "--" + dataKeyList
                        + "--" + appName + "--" + softwareId);
            }
        } else {
            String newPath = PropertiesUtil.toolsOutPath
                    + "Procedure!runApp?userId=" + userId + "&appId="
                    + softwareId + "&appName=" + soft.getSoftwareName()
                    + "&projectName=" + projectName + "&email=" + email
                    + "&dataKeyList=" + dataKeyList + "&projectId=" + proId
                    + "&dataInfos="
                    + Base64Util.encrypt(JSONObject.toJSONString(map))
                    + "&company="
                    + Base64Util.encrypt(JSONObject.toJSONString(com))
                    + "&user="
                    + Base64Util.encrypt(JSONObject.toJSONString(user))
                    + "&dept="
                    + Base64Util.encrypt(JSONObject.toJSONString(dept));
            RemoteRequests rr = new RemoteRequests();
            rr.run(newPath);
        }
        error = 0;
        return "RunProject";
    }

    public String runQueue() {
        log.info("手动释放项目所占用的端口，ProjectId:" + projectId);
        runQueue(projectId);
        flag = true;
        return "returnBoolean";
    }

    /**
     * 运行队列里的命令
     */
    private void runQueue(String projectId) {
        log.info(projectId + "运行结束，释放端口");
        PortPool.setPort(projectId);
        while (true) {
            if (GlobalQueue.isEmpty()) {
                log.info("任务队列为空");
                break;
            }
            String command = GlobalQueue.peek();
            if (command != null) {
                String[] infos = command.split("--");
                int need = infos[2].split(";").length;
                log.info("需要节点 " + need + "，可使用节点 " + PortPool.getSize());
                if (PortPool.getSize() < need) {
                    log.info("节点不满足需要，暂缓投递任务");
                    break;
                }
                log.info("满足需要，投递任务");
                submit(infos[0], infos[1], infos[2], infos[3],
                        perlMap.get(infos[4]));
                GlobalQueue.poll();
            }
        }
    }

    private void submit(String basePath, String projectId, String dataKeyList,
            String appName, String perl) {
        // 创建要运行的文件列表文件
        String dataListFile = dealDataKeyListContainFileName(projectId,
                dataKeyList);
        // TODO
        String command = "nohup perl  /share/biosoft/perl/wangzhen/PGS/bin/moniter_qsub_url.pl perl "
                + " "
                + perl
                + " "
                + dataListFile
                + " "
                + basePath
                + " ProjectID"
                + projectId
                + " >"
                + basePath
                + "ProjectID"
                + projectId + ".log &";
        log.info("运行命令：" + command);
        SSHUtil ssh = new SSHUtil(sparkhost, sparkuserName, sparkpwd);
        ssh.sshSubmit(command, false);
    }

    /**
     * 项目运行结束之后
     */
    public String projectRunOver() {
        log.info("项目运行结束，id:" + projectId);
        // 1.利用 python 生成数据 pdf，并将数据报告插入 mongodb
        String command = "python " + SparkPro.PYTHONPATH + " "
                + SparkPro.TOOLSPATH + " " + projectId;
        PerlUtils.executeGadgetsPerl(command);

        int proId = Integer.parseInt(projectId);
        project = projectService.getProjectById(proId);
        userId = project.getUserId();
        String email = userService.getEmailByUserId(userId);
        int appId = reportService.getSoftwareIdByProjectId(proId);
        proDataList = projectService.getAllFileInProject(proId);
        appName = softwareService.getSoftware(appId).getSoftwareName();
        System.out.println(projectId);
        String appPath = basePath + userId + "/" + appId + "/";
        // 创建项目结果文件
        String projectFile = appPath + projectId + "/" + projectId + ".txt";
        FileTools.createFile(projectFile);
        // 追加表头
        StringBuffer resultArray = new StringBuffer();
        // boolean isFirst = true;
        resultArray.append(titleMap.get(appId));
        StringBuffer sb = new StringBuffer();
        for (Data d : proDataList) {
            String filename = d.getFileName();
            String datakey = d.getDataKey();
            String anotherName = StringUtils.isEmpty(d.getAnotherName()) ? null
                    : d.getAnotherName();
            String finalPath = appPath + datakey;
            sb.append(datakey).append(",").append(getBarcode(filename))
                    .append(",").append(anotherName).append(";");
            String fileName = FileTools.fileExist(finalPath, datakey + ".xls",
                    "endsWith");
            if (fileName.equals("")) {
                fileName = FileTools.fileExist(finalPath,
                        "no_enough_reads.xls", "endsWith");
            }
            String result[] = null;
            // String title[] = null;
            try {
                String r[] = FileUtils.readFileToString(
                        new File(finalPath + "/" + fileName)).split("\n");
                if (r.length > 2) {
                    // title = getArray(r, 1).split("\t");
                    result = getArray(r, 2).split("\t");
                } else {
                    result = FileTools.getLastLine(finalPath + "/" + fileName)
                            .split("\t");
                }
                // if (isFirst) {
                // System.out.println(title==null);
                //
                // for (int i = 0; i < title.length; i++) {
                // resultArray.append(getArray(title, i));
                // if (i == title.length - 1) {
                // resultArray.append("\n");
                // } else {
                // resultArray.append("\t");
                // }
                // }
                // isFirst = false;
                // }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result.length == 1) {
                resultArray.append(filename + "\t" + datakey + "\t"
                        + anotherName + "\t" + getArray(result, 0) + "\n");
            } else {
                if(titleMap.get(appId).split("\t").length==9){
                    resultArray.append(filename + "\t" + datakey + "\t"
                            + anotherName + "\t" + getArray(result, 0) + "\t"
                            + getArray(result, 1) + "\t" + getArray(result, 2)
                            + "\t" + getArray(result, 3) + "\t"
                            + getArray(result, 4) + "\t" + getArray(result, 5)
                            + "\n");
                }else if (titleMap.get(appId).split("\t").length==8){
                    resultArray.append(filename + "\t" + datakey + "\t"
                            + anotherName + "\t" + getArray(result, 0) + "\t"
                            + getArray(result, 1) + "\t" + getArray(result, 2)
                            + "\t" + getArray(result, 3) + "\t"
                            + getArray(result, 4)+ "\n");
                }
            }
        }
        // 2.生成项目pdf
        try {
            PGSProjectPDF.createPDF(appPath, appName, 220, 800, sb.toString(),
                    projectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileTools.appendWrite(projectFile, resultArray.toString());
        String xml = null;
        if (new File(projectFile).exists()) {
            xml = XmlUtil.writeXML(projectFile);
        }
        // 3. 项目报告插入mysql并修改项目运行状态
        reportService
                .updateReportStateByProSoftId(userId, proId, appId, 3, xml);

        // TODO 4.发送邮件
        String param = "fileName=" + null + "&userId=" + userId + "&appId="
                + appId + "&dataKey=" + null + "&projectId=" + projectId
                + "&sampleList=" + null;
        projectName = projectService.getProjectNameById(proId);
        EmailProjectEnd.sendEndEmail(projectName, "" + appId,
                DateUtil.nowCarefulTimeToString(project.getCreateDate()),
                email, param, true);
        runQueue(projectId);
        flag = true;
        return "returnBoolean";
    }

    /**
     * 通过文件名称获取Barcode
     * 
     * @param fileName
     * @return
     */
    private static String getBarcode(String fileName) {
        String[] s = fileName.split("_");
        if (s.length > 2) {
            fileName = s[0] + "_" + s[1];
        }
        return fileName;
    }

    /**
     * 将 dataKeyList 封装成 dataListFile 文件，包含文件名
     * 
     * @param dataKeyList
     * @return
     */
    private String dealDataKeyListContainFileName(String projectId,
            String dataKeyList) {
        StringBuffer sb = new StringBuffer();
        String dataListFile = datalist + new Date().getTime() + ".txt";
        FileTools.createFile(dataListFile);
        String dataArray[] = dataKeyList.split(";");
        List<String> ports = PortPool.getPorts(dataArray.length, projectId);
        for (int i = 0; i < dataArray.length; i++) {
            String[] dataDetail = dataArray[i].split(",");
            sb.append(dataPath + getArray(dataDetail, 1) + "\t"
                    + getArray(dataDetail, 2) + "\t" + ports.get(i) + "\n");
        }
        FileTools.appendWrite(dataListFile, sb.toString());
        return dataListFile;
    }

    private static String getArray(String[] n, int num) {
        return n == null ? null : (n.length > num ? n[num] : null);
    }

    public String updateReportReadStateByPro() {
        result = reportService.updateReportReadStateByPro(
                Integer.parseInt(projectId), Integer.parseInt(softwareId));
        return SUCCESS;
    }

    /**
     * 获取某个项目的项目名、物种、数据类型信息
     * 
     * @return
     */
    public String getProjectById() {
        project = projectService.getProjectById(project.getProjectId());
        return SUCCESS;
    }

    /**
     * 获取所有项目列表
     * 
     * @return
     */
    public String getAllProMapListByUserId() {
        userId = (Integer) super.session.get("userId");
        proList = projectService.getAllProNameList(userId);
        proMapList = new ArrayList<Map<String, String>>();
        // 添加数据的所有物种类型
        for (Project pro : proList) {
            Map<String, String> one = new HashMap<String, String>();
            one.put("id", pro.getProjectId() + "");
            one.put("text", pro.getProjectName());
            proMapList.add(one);
        }
        return SUCCESS;
    }

    /**
     * 项目删除
     * 
     * @return
     */
    public String deleteProject() {
        result = projectService.deleteProject(Integer.parseInt(projectId));
        return "deleteProject";
    }

    /**
     * 修改项目文件格式为初始状态0
     * 
     * @return
     */
    public String updateProDataFormat() {
        result = projectService.updateDataFormatById(project.getProjectId(),
                project.getDataFormat());
        return "updateProDataFormat";
    }

    /**
     * 获取项目下文件个数
     * 
     * @return
     */
    public String getDataSizeInProject() {
        dataSize = dataService.getDataListByProjectId(project.getProjectId())
                .size();
        return "getDataSizeInProject";
    }

    /**
     * 检查项目名是否重复
     * 
     * @return
     */
    public String checkProjectName() {
        userId = (Integer) super.session.get("userId");
        int proId = projectService.checkProjectName(project.getProjectId(),
                project.getProjectName(), userId);
        flag = (proId != 0);
        return "checkProjectName";
    }

    /**
     * 根据项目名获取项目编号
     * 
     * @return
     */
    public String getProjectIdByName() {
        projectId = projectService.getProjectIdByName(projectName) + "";
        return SUCCESS;
    }

    /**
     * 新建SNP项目
     * 
     * @return
     */
    private String softwareName;

    public String createSnpTreeProject() {
        userId = (Integer) super.session.get("userId");
        projectName = new Date().getTime() + "";
        proList = projectService.getAllProNameList(userId);
        while (existsProName(projectName)) {
            projectName = new Date().getTime() + "";
        }
        project = new Project();
        project.setUserId(userId);
        project.setProjectName(projectName);
        // 根据softwareName获取对应的数据类型
        int dataFormat = dataService.getDataById(dataId + "").getFileFormat();
        project.setDataFormat(dataFormat);
        flag = projectService.insertProject(project);
        int result = flag ? 1 : 0;
        projectName = result + "," + projectName;
        return SUCCESS;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    /**
     * 判断项目名是否重复
     * 
     * @param proName
     * @return
     */
    public boolean existsProName(String proName) {
        for (Project pro : proList) {
            if (pro.getProjectName().equals(projectName)) {
                projectName = new Date().getTime() + "";
                return true;
            }
        }
        return false;
    }

    public String getUserIdByProjectId() {
        shareUserId = projectService.getUserIdByProjectId(Integer
                .parseInt(projectId));
        return SUCCESS;
    }

    public String getAllProStrainList() {
        userId = (Integer) super.session.get("userId");
        List<Map<String, String>> proStrainList = projectService
                .getProStrainItem(userId);
        List<Map<String, String>> dataStrainList = dataService
                .getStrainItem(userId);
        strainMapList = new ArrayList<Map<String, String>>();
        proStrainList.addAll(dataStrainList);
        HashSet<Map<String, String>> h = new HashSet<Map<String, String>>(
                proStrainList);
        proStrainList.clear();
        proStrainList.addAll(h);
        // 添加数据的所有物种类型
        for (Map<String, String> map : proStrainList) {
            if (!"".equals(map.get("strain")) && map.get("strain") != null) {
                Map<String, String> one = new HashMap<String, String>();
                one.put("id", map.get("strain"));
                one.put("text", map.get("strain"));
                strainMapList.add(one);
            }
        }
        return "strainMapList";
    }

    public String getAllDataTypeList() {
        dataTypeMap = new HashMap<String, List<Map<String, String>>>();
        List<DataType> typeList = dataTypeService.getDataTypeList();
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int i = 0; i < typeList.size(); i++) {
            Map<String, String> one = new HashMap<String, String>();
            one.put("id", String.valueOf(typeList.get(i).getTypeId()));
            one.put("text", typeList.get(i).getTypeDesc());
            listMap.add(one);
        }
        dataTypeMap.put("tags", listMap);
        return "dataTypeList";
    }

    public String removeData() {
        flag = projectService.removeData(projectId, dataId);
        return SUCCESS;
    }

    public String addDataForPro() {
        result = projectService.addDataToPro(project.getProjectId(), dataId);
        // int fileType = dataService.getFileTypeById(dataId);
        // projectService.addProDataType(project.getProjectId(),
        // String.valueOf(fileType));
        return "addDataForPro";
    }

    /**
     * 数据分配项目，获取项目列表
     * 
     * @return
     */
    public String getProListForSel() {
        log.info("查询项目名称列表");
        getUserId();
        proNameList = projectService.getAllProNameList(userId);
        dataTypeMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (Project pro : proNameList) {
            Map<String, String> proMap = new HashMap<String, String>();
            proMap.put("id", pro.getProjectId() + "");
            proMap.put("text", pro.getProjectName());
            listMap.add(proMap);
        }
        dataTypeMap.put("tags", listMap);
        return SUCCESS;
    }

    public String getProListForUpload() {
        proNameList = projectService.getAllProNameList(userid);
        return "getProListForUpload";
    }

    private int fileId;

    public String getProListByFileId() {
        proNameList = projectService.getAllProNameListByFileId(fileId);
        dataTypeMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (Project pro : proNameList) {
            Map<String, String> proMap = new HashMap<String, String>();
            proMap.put("id", pro.getProjectId() + "");
            proMap.put("text", pro.getProjectName());
            listMap.add(proMap);
        }
        dataTypeMap.put("tags", listMap);
        return SUCCESS;
    }

    public String getProListForData() {
        getUserId();
        proNameList = projectService.getProListForData(userId,
                data.getFileFormat());
        dataTypeMap = new HashMap<String, List<Map<String, String>>>();
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (Project pro : proNameList) {
            Map<String, String> proMap = new HashMap<String, String>();
            proMap.put("id", pro.getProjectId() + "");
            proMap.put("text", pro.getProjectName());
            listMap.add(proMap);
        }
        dataTypeMap.put("tags", listMap);
        return "getProListForData";
    }

    public String getAllDataInPro() {
        proDataList = projectService
                .getAllFileInProject(project.getProjectId());
        return "getDataInProSucc";
    }

    public String getDataSizeInPro() {
        proDataList = projectService
                .getAllFileInProject(project.getProjectId());
        dataSize = proDataList.size();
        return SUCCESS;
    }

    public String getStrainByProId() {
        log.info("获取指定项目的物种信息");
        strain = projectService.getStrainByProId(project.getProjectId());
        return "getStrainByProId";
    }

    private String proManageInfo; // 添加项目的提示
    private String userIds = "";

    public String addProject() {
        getUserId();
        String proName = project.getProjectName();
        project.setUserId(userId);
        List<Project> proNameList = projectService.getAllProNameList(userId);
        flag = true;
        for (int i = 0; i < proNameList.size(); i++) {
            if (proName.equals(proNameList.get(i).getProjectName())) {
                flag = false;
                proManageInfo = "项目名称已存在，建议使用" + proName + "_1";
            }
        }
        if (flag == true) {
            flag = projectService.insertProject(project);
            if (flag == true) {
                proManageInfo = "succ";
            }
        }
        return SUCCESS;
    }

    /**
     * 取消项目共享
     * 
     * @return
     */
    public String cancelProjectShare() {
        flag = projectService.deleteSharedPro(userId, project.getProjectId());
        return "returnBoolean";
    }

    public String saveProjectShare() {
        errorMessage = "";
        getUserId();
        if ((null != userNames) && (!userNames.equals(""))) {
            String username[] = userNames.split(",");
            for (String uname : username) {
                int userid = userService.getUserIdByName(uname);
                if (userid == 0) {
                    errorMessage += uname + ",";
                } else {
                    userIds += "" + userid + ",";
                }
            }
        }
        if (!errorMessage.equals("")) {
            errorMessage = "用户"
                    + errorMessage.substring(0, errorMessage.length() - 1)
                    + "不存在！";
            return "saveProjectShare";
        }
        if (!"".equals(userIds)) {
            projectService.deleteSharedPro(userId, project.getProjectId());
            errorMessage = projectService.insertShareProject(
                    userIds.substring(0, userIds.length() - 1),
                    project.getProjectId(), userId)
                    + "";
            Map<String, Integer> map = projectService.getAllErrorSharedPro(
                    userId, project.getProjectId());
            if (map != null) {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    projectService.deleteSharedProById(entry.getValue());
                }
            }
        } else {
            projectService.deleteSharedPro(userId, project.getProjectId());
            Map<String, Integer> map = projectService.getAllErrorSharedPro(
                    userId, project.getProjectId());
            if (map != null) {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    projectService.deleteSharedProById(entry.getValue());
                }
            }
            errorMessage = "2";
        }
        return "saveProjectShare";
    }

    public String sendProShareEmail() {
        if (!userNames.equals("")) {
            result = 1;
            for (String uName : userNames.split(",")) {
                String emailTemplate = TemplateUtil.readTemplate(0);
                String sendMsg = emailTemplate
                        .replaceAll("#shareType", "项目")
                        .replaceAll("#userName", uName)
                        .replaceAll("#shareUserName",
                                (String) super.session.get("userName"))
                        .replaceAll(
                                "#dataName",
                                projectService.getProjectNameById(project
                                        .getProjectId()))
                        .replaceAll("#dataKey", project.getProjectId() + "");
                String email = userService.getEmailByUserName(uName);
                // EmailUtil.sendMail(email,
                // "项目共享编号为：" + project.getProjectId());
                // EmailUtil.sendMail(email,sendMsg,true);
                EmailService.send(email, sendMsg, true);
            }
        }
        return "sendProShareEmail";
    }

    public String addProStrain() {
        String strain = project.getStrain();
        int proectid = project.getProjectId();
        flag = projectService.addProStrain(proectid, strain);
        return "addProStrain";
    }

    public String addProDataType() {
        String dataType = String.valueOf(project.getProDataType());
        flag = projectService.addProDataType(project.getProjectId(), dataType);
        return "addProDataType";
    }

    public String getProjectNameById() {
        projectName = projectService.getProjectNameById(project.getProjectId());
        return SUCCESS;
    }

    public String updateProjectNameById() {
        log.info("根据项目编号修改项目名称");
        flag = projectService.updateProName(project.getProjectId(),
                project.getProjectName());
        return "updateProjectNameById";
    }

    public String getDataTypeById() {
        log.info("根据项目编号获取数据类型");
        String dataType = projectService
                .getDataTypeById(project.getProjectId());
        project.setProDataType(Integer.parseInt(dataType));
        return "getDataTypeById";
    }

    public int getUserId() {
        userId = (Integer) super.session.get("userId");
        return userId;
    }

    public PageList<Project> getShareProPageList() {
        return shareProPageList;
    }

    public void setShareProPageList(PageList<Project> shareProPageList) {
        this.shareProPageList = shareProPageList;
    }

    public PageList<Project> getMyProNamePageList() {
        return myProNamePageList;
    }

    public void setMyProNamePageList(PageList<Project> myProNamePageList) {
        this.myProNamePageList = myProNamePageList;
    }

    public PageList<Project> getSharedProNamePageList() {
        return sharedProNamePageList;
    }

    public void setSharedProNamePageList(PageList<Project> sharedProNamePageList) {
        this.sharedProNamePageList = sharedProNamePageList;
    }

    public List<Data> getProDataList() {
        return proDataList;
    }

    public void setProDataList(List<Data> proDataList) {
        this.proDataList = proDataList;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getProNameList() {
        return proNameList;
    }

    public void setProNameList(List<Project> proNameList) {
        this.proNameList = proNameList;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getProManageInfo() {
        return proManageInfo;
    }

    public void setProManageInfo(String proManageInfo) {
        this.proManageInfo = proManageInfo;
    }

    public PageList<Project> getPrivaProPageList() {
        return privaProPageList;
    }

    public void setPrivaProPageList(PageList<Project> privaProPageList) {
        this.privaProPageList = privaProPageList;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public int getSortByType() {
        return sortByType;
    }

    public void setSortByType(int sortByType) {
        this.sortByType = sortByType;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Map<String, List<Map<String, String>>> getDataTypeMap() {
        return dataTypeMap;
    }

    public void setDataTypeMap(
            Map<String, List<Map<String, String>>> dataTypeMap) {
        this.dataTypeMap = dataTypeMap;
    }

    public List<Map<String, String>> getStrainMapList() {
        return strainMapList;
    }

    public void setStrainMapList(List<Map<String, String>> strainMapList) {
        this.strainMapList = strainMapList;
    }

    public int getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(int shareUserId) {
        this.shareUserId = shareUserId;
    }

    public String getDataIds() {
        return dataIds;
    }

    public void setDataIds(String dataIds) {
        this.dataIds = dataIds;
    }

    public List<Project> getProList() {
        return proList;
    }

    public void setProList(List<Project> proList) {
        this.proList = proList;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public ProjectParam getProParam() {
        return proParam;
    }

    public void setProParam(ProjectParam proParam) {
        this.proParam = proParam;
    }

    public Map<String, Object> getProParams() {
        return proParams;
    }

    public void setProParams(Map<String, Object> proParams) {
        this.proParams = proParams;
    }

    public List<Map<String, String>> getProMapList() {
        return proMapList;
    }

    public void setProMapList(List<Map<String, String>> proMapList) {
        this.proMapList = proMapList;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

}

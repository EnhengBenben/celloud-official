package com.nova.action;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.sdo._16s.Compare;
import com.nova.sdo._16s.SystemRecommend;
import com.nova.service.IReportService;
import com.nova.utils.PerlUtils;
import com.nova.utils.PropertiesUtil;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("celloud-default")
@Action("16s")
@Results({
	@Result(name = "commonMatch", location = "/pages/app/16s/result.jsp"),
	@Result(name = "resultSearch", location = "/pages/app/16s/svgAjax.jsp") })
public class MatchAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(MatchAction.class);
    @Inject
    private IReportService reportService;
    private String svgXml;
    private String svgFileName;
    private List<Compare> list;
    private List<SystemRecommend> sysList;
    private String sequence;
    private String flag;
    private String queryId;
    private String path;
    private String fileName;

    public String fileId;

    /**
     * @Title: commonMatch
     * @Description: (用户序列提交,对序列传送到服务器端计算)
     * @param @return
     * @return String
     * @throws
     */
    public String commonMatch() {
	// 下面两行用于使用本地调用perl程序
	if (fileId != null && !fileId.trim().equals("")) {
	    path = this.reportService.getFilePath(Integer.parseInt(fileId));
	    fileName = this.productfileName(path);

	} else {
	    fileName = getTimestamp() + "aa.fa";
	    path = getPath(sequence, fileName);
	}
	String result = PerlUtils.getResult(path);

	sysList = getResultList(result);
	return "commonMatch";
    }

    private String productfileName(String path) {
	String filename = "";
	if (path != null) {
	    int i = path.lastIndexOf("/");
	    filename = path.substring(i + 1, path.length());
	}
	return filename;
    }

    public List<SystemRecommend> getResultList(String result) {
	List<SystemRecommend> list = new ArrayList<SystemRecommend>();
	String separator = System.getProperty("line.separator");
	String[] str = result.split(separator);
	for (int i = 1; i < str.length; i++) {
	    SystemRecommend sr = new SystemRecommend();
	    if (str[i].equals("")) {
		break;
	    } else {
		String[] line = str[i].split(",");
		if (line != null) {
		    sr.setQueryId(line[0]);
		    sr.setSubjectId(line[1]);
		    sr.setIdentity(line[2]);
		    sr.setScore(line[4]);
		    sr.setSubjectAnnotation(line[5]);
		    list.add(sr);
		}
	    }
	}
	return list;
    }

    /**
     * @Title: getPath
     * @Description: (把用户输入的序列生成本地文件)
     * @param @param sequence
     * @param @return
     * @return String
     * @throws
     */
    public String getPath(String sequence, String fileName) {
	String path = PropertiesUtil.bigFilePath;
	File file = new File(path);
	if (!file.exists()) {
	    file.mkdirs();
	}
	FileWriter writer = null;
	try {
	    writer = new FileWriter(path + fileName);
	    writer.write(sequence);
	    writer.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (writer != null) {
		try {
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	return path + fileName;
    }

    /**
     * @Title: resultSearch
     * @Description: (用户点击单个结果查看svg和data)
     * @param @return
     * @return String
     * @throws
     */
    public String resultSearch() {
	// String stdout = RometoPerl.getSvgAndData();

	String stdout = PerlUtils.getSvgAndData(path, fileName, queryId);
	if (stdout.indexOf("<svg") == -1) {
	    flag = "failure";
	} else {
	    list = getCompareList(stdout);
	    String strainName = list.get(0).getQueryName();
	    svgXml = getSvg(stdout, strainName);
	    svgFileName = "file/" + getTimestamp() + ".svg";
	    writeFile(svgXml, svgFileName);
	    // 用于解析svg
	    flag = "success";
	}
	return "resultSearch";
    }

    /**
     * @Title: getSvg
     * @Description: (从返回的字符流中获取svgXml)
     * @param @param stdout
     * @param @return
     * @return String
     * @throws
     */
    public String getSvg(String stdout, String strainName) {
	String result = stdout.substring(stdout.indexOf("<?xml"),
		stdout.indexOf("</svg>") + 6);
	String result1 = result.substring(0, result.indexOf(strainName));
	String result2 = result.substring(result.indexOf(strainName),
		result.length());
	int i = result1.lastIndexOf("#000000");
	String temp0 = result1.substring(0, i);
	String temp1 = result1.substring(i, result1.length()).replace(
		"#000000", "#FF0000");
	result = temp0 + temp1 + result2;
	return result;
    }

    /**
     * @Title: writeFile
     * @Description: (把svgXml写入文件,主要是考虑IE8浏览器的情况)
     * @param @param svgXml
     * @return void
     * @throws
     */
    public void writeFile(String svgXml, String svgFileName) {
	FileWriter writer = null;
	try {
	    writer = new FileWriter(ServletActionContext.getServletContext()
		    .getRealPath("/") + svgFileName);
	    writer.write(svgXml);
	    writer.flush();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (writer != null) {
		try {
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    /**
     * @Title: getCompareList
     * @Description: (获取比对结果最接近的20条序列的信息)
     * @param @param stdout
     * @param @return
     * @return List<Compare>
     * @throws
     */
    public List<Compare> getCompareList(String stdout) {
	List<Compare> list1 = new ArrayList<Compare>();
	Compare c = null;
	String result = stdout.substring(0, stdout.indexOf("#"));
	String[] str = result.split("\n");
	if (str.length > 0 || str != null) {
	    for (int i = 0; i < str.length; i++) {
		c = new Compare();
		String[] line = str[i].trim().split(";");
		if (line != null) {
		    c.setQueryName(line[0]);
		    c.setSubjectName(line[1]);
		    c.setQueryLength(line[2]);
		    c.setQueryBegin(line[3]);
		    c.setQueryEnd(line[4]);
		    c.setSubjectBegin(line[5]);
		    c.setSubjectEnd(line[6]);
		    c.setSubjectLength(line[7]);
		    c.setIdentidy(line[10]);
		    list1.add(c);
		}
	    }
	}
	return list1;
    }

    /**
     * 获取当前的时间戳，返回14位数字
     * 
     * @return
     */
    public static String getTimestamp() {

	String timestamp = "";
	Calendar c = Calendar.getInstance();

	int yyyy = c.get(Calendar.YEAR);
	int mm = c.get(Calendar.MONTH) + 1;
	int dd = c.get(Calendar.DAY_OF_MONTH);
	int hh = c.get(Calendar.HOUR) + 8;
	int MM = c.get(Calendar.MINUTE);
	int SS = c.get(Calendar.SECOND);

	String sMonth = mm < 10 ? "0" + mm : "" + mm;
	String sDay = dd < 10 ? "0" + dd : "" + dd;
	String sHour = hh < 10 ? "0" + hh : "" + hh;
	String sMinute = MM < 10 ? "0" + MM : "" + MM;
	String sSecond = SS < 10 ? "0" + SS : "" + SS;

	timestamp = yyyy + sMonth + sDay + sHour + sMinute + sSecond;

	return timestamp;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public String getQueryId() {
	return queryId;
    }

    public void setQueryId(String queryId) {
	this.queryId = queryId;
    }

    public List<SystemRecommend> getSysList() {
	return sysList;
    }

    public void setSysList(List<SystemRecommend> sysList) {
	this.sysList = sysList;
    }

    public String getFlag() {
	return flag;
    }

    public void setFlag(String flag) {
	this.flag = flag;
    }

    public String getSequence() {
	return sequence;
    }

    public void setSequence(String sequence) {
	this.sequence = sequence;
    }

    public String getSvgXml() {
	return svgXml;
    }

    public void setSvgXml(String svgXml) {
	this.svgXml = svgXml;
    }

    public List<Compare> getList() {
	return list;
    }

    public void setList(List<Compare> list) {
	this.list = list;
    }

    public String getSvgFileName() {
	return svgFileName;
    }

    public void setSvgFileName(String svgFileName) {
	this.svgFileName = svgFileName;
    }

    public String getFileId() {
	return fileId;
    }

    public void setFileId(String fileId) {
	this.fileId = fileId;
    }

    public IReportService getReportService() {
	return reportService;
    }

    public void setReportService(IReportService reportService) {
	this.reportService = reportService;
    }

}

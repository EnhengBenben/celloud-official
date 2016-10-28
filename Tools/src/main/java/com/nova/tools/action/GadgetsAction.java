package com.nova.tools.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.nova.tools.utils.FileTools;
import com.nova.tools.utils.PerlUtils;
import com.nova.tools.utils.PropertiesUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @Description:小工具action
 * @author lin
 * @date 2013-8-5 上午11:05:58
 */
@ParentPackage("json-default")
@Action("Gadgets")
@Results({ @Result(name = "success", type = "json", params = { "root", "result" }) })
public class GadgetsAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private String context;// 前台传来的文本内容
    private String result;// 执行结果
    private String start;
    private String end;
    // 获取celloud端当前用户的id
    private Integer userId;

    private String basePath = ServletActionContext.getServletContext()
	    .getRealPath(PropertiesUtils.dataPath);
    private String perlPath = ServletActionContext.getServletContext()
	    .getRealPath("/resource/perl/");
    private Logger log = Logger.getLogger(GadgetsAction.class);

    /**
     * 序列翻译
     * 
     * @return
     */
    public String translate() {
	String dataKey = getDateKey();
	String command = "perl " + perlPath + "/cds2aa.pl  " + mkFile(dataKey);
	result = PerlUtils.executeGadgetsPerl(command);
	saveFileInfo(dataKey, userId);
	return SUCCESS;
    }

    private String getDateKey() {
	HttpURLConnection conn = null;
	BufferedReader br = null;
	String dataKey = "";
	String url = "http://"
		+ ServletActionContext.getRequest().getLocalAddr() + ":"
		+ ServletActionContext.getRequest().getLocalPort()
		+ "/celloud/data!getUniqueDataKey";
	try {
	    log.info("url:" + url);
	    conn = (HttpURLConnection) new URL(url).openConnection();
	    conn.setRequestMethod("GET");
	    conn.setDoOutput(true);
	    conn.setConnectTimeout(120000);
	    conn.setReadTimeout(40000);
	    conn.connect();
	    br = new BufferedReader(
		    new InputStreamReader(conn.getInputStream()));
	    dataKey = br.readLine().toString().replaceAll("\"", "");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null)
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	return dataKey;
    }

    private void saveFileInfo(String dataKey, Integer userId) {
	HttpURLConnection conn = null;
	BufferedReader br = null;
	double size = context.length() / 1024.0;
	String url = "http://"
		+ ServletActionContext.getRequest().getLocalAddr() + ":"
		+ ServletActionContext.getRequest().getLocalPort()
		+ "celloud/data!saveFileInfo?data.dataKey=" + dataKey
		+ "&data.fileName=" + dataKey
		+ ".fasta&data.fileFormat=3&userId=" + userId + "&data.size="
		+ size;
	try {
	    log.info("url:" + url);
	    conn = (HttpURLConnection) new URL(url).openConnection();
	    conn.setRequestMethod("GET");
	    conn.setDoOutput(true);
	    conn.setConnectTimeout(120000);
	    conn.setReadTimeout(40000);
	    conn.connect();
	    br = new BufferedReader(
		    new InputStreamReader(conn.getInputStream()));
	    br.readLine().toString();
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null)
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
    }

    /**
     * 序列截取
     * 
     * @return
     */
    public String seqsub() {
	log.info("序列截取");
	String command = "perl " + perlPath + "/cut_seq.pl  " + mkFile() + " "
		+ start + " " + end;
	result = PerlUtils.executeGadgetsPerl(command);
	return SUCCESS;
    }

    /**
     * 序列反转
     * 
     * @return
     */
    public String reversal() {
	log.info("序列反转");
	String command = "perl " + perlPath + "/reverse_seq.pl  " + mkFile();
	result = PerlUtils.executeGadgetsPerl(command);
	return SUCCESS;
    }

    /**
     * 将前台传来的字符串封装成文件
     * 
     * @return
     */
    private String mkFile() {
	String filePath = basePath + "/" + new Date().getTime() + ".fasta";
	System.out.println("filePath=" + filePath);
	FileTools.createFile(filePath);
	FileTools.appendWrite(filePath, context);
	return filePath;
    }

    /**
     * 将前台传来的字符串封装成文件
     * 
     * @return
     */
    private String mkFile(String dataKey) {
	String filePath = PropertiesUtils.dataPath + "/" + dataKey + ".fasta";
	FileTools.createFile(filePath);
	FileTools.appendWrite(filePath, context);
	return filePath;
    }

    public String getContext() {
	return context;
    }

    public void setContext(String context) {
	this.context = context;
    }

    public String getResult() {
	return result;
    }

    public void setResult(String result) {
	this.result = result;
    }

    public String getStart() {
	return start;
    }

    public void setStart(String start) {
	this.start = start;
    }

    public String getEnd() {
	return end;
    }

    public void setEnd(String end) {
	this.end = end;
    }

    public Integer getUserId() {
	return userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }
}
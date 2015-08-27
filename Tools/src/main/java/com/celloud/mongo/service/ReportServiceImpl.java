package com.celloud.mongo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.celloud.mongo.core.SystemContext;
import com.celloud.mongo.dao.ReportDAO;
import com.celloud.mongo.sdo.CmpFilling;
import com.celloud.mongo.sdo.CmpReport;
import com.celloud.mongo.sdo.GddDiseaseDict;

/**
 * 
 * @author <a href="mailto:liuqingxiao@celloud.cn">liuqx</a>
 * @date 2015-7-13下午4:40:46
 * @version Revision: 1.0
 */
public class ReportServiceImpl implements ReportService {
    ReportDAO reportDao = SystemContext.getReportDAO();

    @Override
    public void saveCmpReport(CmpReport cmpReport) {
	reportDao.saveCmpReport(cmpReport);
    }

    @Override
    public void editCmpFilling(Object id, CmpFilling cmpFill) {
	reportDao.editCmpFilling(id, cmpFill);
    }

    @Override
    public CmpReport getCmpReport(String dataKey, Integer userId) {
	return reportDao.getCmpReport(dataKey, userId);
    }

    @Override
    public void deleteCmpReport(String dataKey, Integer userId) {
	reportDao.deleteCmpReport(dataKey, userId);
    }

    @Override
    public CmpReport getSimpleCmp(String dataKey, Integer userId) {
	return reportDao.getCmpReport(dataKey, userId);
    }

    @Override
    public void saveGddDiseaseDict(GddDiseaseDict gddDisease) {
	reportDao.saveGddDiseaseDict(gddDisease);
    }

    @Override
    public GddDiseaseDict getGddDiseaseDict(String name) {
	return reportDao.getGddDiseaseDict(name);
    }

    public static void main(String[] args) {
	File file = new File("E:\\CelLoud\\燕达项目\\归档\\spark-15824.txt");
	BufferedReader reader;
	try {
	    reader = new BufferedReader(new InputStreamReader(
		    new FileInputStream(file), "gbk"));
	    String tmpStr = null;
	    ReportService rService = new ReportServiceImpl();
	    int i = 0;
	    while ((tmpStr = reader.readLine()) != null) {
		String[] tmpArry = tmpStr.split("=");
		for (String s : tmpArry) {
		    System.out.print(s + "---");
		}
		System.out.println(tmpArry.length);
		GddDiseaseDict gddDisease = new GddDiseaseDict();
		gddDisease.setType(tmpArry[0]);
		gddDisease.setEngName(tmpArry[1]);
		gddDisease.setName(tmpArry[2]);
		gddDisease.setGene(tmpArry[3]);
		rService.saveGddDiseaseDict(gddDisease);
		System.out.println(i++);
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}

package com.celloud.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.AppDataListType;
import com.celloud.dao.ReportDao;
import com.celloud.model.mongo.FSocg;
import com.celloud.model.mongo.FSocgDB;
import com.celloud.model.mysql.App;
import com.celloud.model.mysql.Company;
import com.celloud.model.mysql.DataFile;
import com.celloud.service.AppService;
import com.celloud.service.CompanyService;
import com.celloud.service.DataService;
import com.celloud.service.DirectReportService;
import com.celloud.service.TaskService;
import com.celloud.service.UserService;
import com.celloud.utils.FileTools;

@Service("directReportService")
public class DirectReportServiceImpl implements DirectReportService {

	@Resource
	private ReportDao reportDao;
	@Resource
	private TaskService taskService;
	@Resource
	private AppService appService;
	@Resource
	private DataService dataService;
	@Resource
	private UserService userService;
	@Resource
	private CompanyService companyService;

	@Override
	public void fsocg(Integer userId, Integer appId, String list, String path, Integer projectId) {
		String input = FileTools.getFirstLine(list);
		String dnaPath = input.split("\t")[0];
		String dnaKey = getDataKey(dnaPath);
		String result = path + dnaKey;
		// DNA
		List<String> dnaList = FileTools.filetoList(new File(dnaPath));
		List<String> dnaFrequency = new ArrayList<>();
		for (int i = 1; i < dnaList.size(); i++) {
			String line = dnaList.get(i);
			String[] dna = line.split("\t");
			if (dna.length < 12)
				continue;
			if (Float.valueOf(dna[6]) <= 1)
				continue;
			if ("No Call".equals(dna[4]))
				continue;
			if (isIn(dna[11])) {
				dnaFrequency.add(line);
			}
		}
		App app = appService.selectByPrimaryKey(appId);
		String title = app.getTitle();
		StringBuffer sbDNA = new StringBuffer(title + "\n");
		String[] titleArray = title.split("\t");
		List<String> titleList = new ArrayList<>();
		for (String t : titleArray) {
			titleList.add(t);
		}
		List<List<String>> dna = new ArrayList<>();
		dna.add(titleList);
		for (String string : dnaFrequency) {
			String str[] = string.split("\t");
			List<String> chr = new ArrayList<>();
			chr.add(str[0] + ":" + str[1]);
			chr.add(str[2]);
			chr.add(str[3]);
			chr.add(str[4]);
			chr.add(str[6]);
			chr.add(str[7]);
			chr.add(str[9]);
			chr.add(str[10]);
			chr.add(str[11]);
			chr.add(str[12]);
			chr.add(str[13]);
			dna.add(chr);
			sbDNA.append(str[0]).append(":").append(str[1]).append("\t").append(str[2]).append("\t").append(str[3])
					.append("\t").append(str[4]).append("\t").append(str[6]).append("\t").append(str[7]).append("\t")
					.append(str[9]).append("\t").append(str[10]).append("\t").append(str[11]).append("\t")
					.append(str[12]).append("\t").append(str[13]).append("\n");
		}
		String dnaResult = result + File.separatorChar + "DNA_result.txt";
		if (FileTools.checkPath(dnaResult)) {
			new File(dnaResult).delete();
		}
		FileTools.appendWrite(dnaResult, sbDNA.toString());
		// RNA
		String rnaPath = input.split("\t")[1];
		String rnaKey = getDataKey(rnaPath);
		List<String> rnaList = FileTools.filetoList(new File(rnaPath));
		Set<String> rnaContigId = new HashSet<>();
		for (int i = 1; i < rnaList.size(); i++) {
			String line = rnaList.get(i);
			String[] rna = line.split("\t");
			if(rna.length<10)
				continue;
			if (Integer.valueOf(rna[7]) <= 0||Integer.valueOf(rna[8]) <= 0||Integer.valueOf(rna[9]) <= 200)
				continue;
			String contigId = rna[0];
			if (AppDataListType.FSOCG_noId.contains(contigId))
				continue;
			rnaContigId.add(contigId);
		}
		StringBuffer sbRna = new StringBuffer("contig_id\n");
		List<String> rna = new ArrayList<>();
		rna.add("contig_id\n");
		for (String string : rnaContigId) {
			rna.add(string);
			sbRna.append(string).append("\n");
		}
		String rnaResult = result + File.separatorChar + "RNA_result.txt";
		if (FileTools.checkPath(rnaResult)) {
			new File(rnaResult).delete();
		}
		FileTools.appendWrite(rnaResult, sbRna.toString());
		FSocg fsocg = new FSocg();
		fsocg.setDna(dna);
		fsocg.setRna(rna);
		fsocg.setAppId(appId);
		fsocg.setAppName(app.getAppName());
		fsocg.setProjectId(projectId);
		fsocg.setDataKey(dnaKey);
		List<DataFile> dataList = dataService.getDatasInProject(projectId);
		fsocg.setData(dataList);
		Integer companyId = userService.getCompanyIdByUserId(userId);
		Company company = companyService.getCompanyById(companyId);
		fsocg.setCompanyAddr(company.getAddress());
		fsocg.setCompanyEnAddr(company.getAddressEn());
		fsocg.setCompanyEngName(company.getEnglishName());
		fsocg.setCompanyIcon(company.getCompanyIcon());
		fsocg.setCompanyId(company.getCompanyId());
		fsocg.setCompanyName(company.getCompanyName());
		fsocg.setCompanyTel(company.getTel());
		fsocg.setUserId(userId);
		reportDao.saveData(fsocg);
		taskService.updateToDone(appId, projectId, dnaKey, dnaKey + "," + rnaKey, null);
	}

	private boolean isIn(String param) {
		List<FSocgDB> database = reportDao.getAllByClass(FSocgDB.class);
		for (FSocgDB fSocg : database) {
			if (fSocg.getAlleleName().equals(param)) {
				return true;
			}
		}
		return false;
	}

	private String getDataKey(String path) {
		String[] paths = path.split("/");
		String fileName = paths[paths.length - 1];
		return fileName.split("\\.")[0];
	}

}

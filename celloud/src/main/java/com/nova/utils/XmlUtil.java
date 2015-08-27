package com.nova.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.BaseElement;

import com.nova.sdo.ProSample;
import com.nova.sdo.ProjectParam;

public class XmlUtil {
    private static Logger log = Logger.getLogger(XmlUtil.class);

    public static String writeXML(String path) {
	String context = null;
	try {
	    context = FileUtils.readFileToString(new File(path));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	if (context == null) {
	    return null;
	}
	StringBuffer sb = new StringBuffer("<table>");
	String lines[] = context.split("\n");
	for (String line : lines) {
	    sb.append("<tr>");
	    String datas[] = line.split("\t");
	    for (String data : datas) {
		sb.append("<td>").append(data).append("</td>");
	    }
	    sb.append("</tr>");
	}
	sb.append("</table>");
	return sb.toString();
    }

    /**
     * 将数据库中的xml格式的项目报告解析成List<List<String>>
     * 
     * @param context
     * @return
     */
    public static List<List<String>> readXML(String context) {
	List<List<String>> listXML = new ArrayList<List<String>>();
	Document doc = null;
	try {
	    // 将字符串转为XML
	    doc = DocumentHelper.parseText(context);
	    // 获取根节点
	    Element root = doc.getRootElement();
	    // 获取根节点下的子节点data
	    Iterator<?> dataList = root.elementIterator("data");
	    // 遍历data节点
	    while (dataList.hasNext()) {
		List<String> listData = new ArrayList<String>();
		Element data = (Element) dataList.next();
		// 拿到data节点下的子节点val值
		Iterator<?> valList = data.elementIterator("val");
		// 遍历val节点值
		while (valList.hasNext()) {
		    Element val = (Element) valList.next();
		    listData.add(val.getTextTrim());
		}
		listXML.add(listData);
	    }
	} catch (DocumentException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return listXML;
    }

    /**
     * 保存项目参数值
     */
    public static int createDocument(ProjectParam proParam,
	    String projectFileDir) {
	int result = 1;
	// 首先检查是保存全部参数，还是保存某个app的参数
	int paramFlag = proParam.getFlag();
	int projectId = proParam.getProjectId();
	String proRealPath = projectFileDir + "/" + projectId + ".xml";
	if (paramFlag == 0) {// 全部参数保存
	    // 1、首先需要判断对应项目参数xml文件是否存在，如果存在，删除，重新创建
	    File proFile = new File(proRealPath);
	    if (proFile.exists()) {
		boolean isSuc = proFile.delete();
		if (isSuc) {
		    log.info("xml文件删除成功");
		} else {
		    log.error("xml文件删除失败");
		}
	    }
	    // 2、新建文件
	    Document doc = DocumentHelper.createDocument();
	    // 添加根节点
	    Element root = doc.addElement("params");
	    root.addComment("params for project " + projectId);
	    // 添加物种字节点
	    String strain = proParam.getStrain();
	    if (null != strain && !"".equals(strain)) {
		Element strainNode = new BaseElement("strain");
		strainNode.setText(strain);
		root.add(strainNode);
	    }
	    // 添加adaptor3信息
	    String ada3 = proParam.getAdaptor3();
	    if (null != ada3 && !"".equals(ada3)) {
		Element ada3Node = new BaseElement("adaptor3");
		ada3Node.setText(ada3);
		root.add(ada3Node);
	    }
	    // 添加adaptor5信息
	    String ada5 = proParam.getAdaptor5();
	    if (null != ada5 && !"".equals(ada5)) {
		Element ada5Node = new BaseElement("adaptor5");
		ada5Node.setText(ada5);
		root.add(ada5Node);
	    }
	    // 添加sampleList信息
	    String sampleList = proParam.getSampleList();
	    if (null != sampleList && !"".equals(sampleList)) {
		Element sampleNode = new BaseElement("sampleList");
		root.add(sampleNode);
		for (String subSample : sampleList.split(";")) {
		    String[] samContent = subSample.split(":");

		    Element sampleSubNode = new BaseElement("sample");
		    sampleSubNode.addAttribute("name", samContent[0]);
		    sampleNode.add(sampleSubNode);

		    for (String data : samContent[1].split(",")) {
			Element dataNode = new BaseElement("data");
			dataNode.setText(data);
			sampleSubNode.add(dataNode);
		    }
		}
	    }
	    // 添加diffList参数信息
	    String diffList = proParam.getDiffList();
	    if (null != diffList && !"".equals(diffList)) {
		Element diffNode = new BaseElement("diffList");
		root.add(diffNode);
		for (String diff : diffList.split(";")) {
		    String[] diffContent = diff.split(":");
		    Element diffSubNode = new BaseElement("diff");
		    diffSubNode.addAttribute("name", diffContent[0]);
		    diffNode.add(diffSubNode);

		    String diffTrCon = diffContent[1];
		    String[] trco = diffTrCon.split("#");
		    String treatment = trco[0];
		    int leftqIndex = treatment.indexOf("[");
		    int rightIndex = treatment.indexOf("]");
		    String trname = treatment.substring(0, leftqIndex);
		    Element treatmentNode = new BaseElement("treatment");
		    treatmentNode.addAttribute("name", trname);
		    diffSubNode.add(treatmentNode);
		    for (String sample : treatment.substring(leftqIndex + 1,
			    rightIndex).split(",")) {
			Element sampleNode = new BaseElement("trsample");
			sampleNode.setText(sample);
			treatmentNode.add(sampleNode);
		    }

		    String control = trco[1];
		    int leftqIndex1 = control.indexOf("[");
		    int rightIndex1 = control.indexOf("]");
		    String conname = control.substring(0, leftqIndex1);
		    Element controlNode = new BaseElement("control");
		    controlNode.addAttribute("name", conname);
		    diffSubNode.add(controlNode);
		    for (String sample : control.substring(leftqIndex1 + 1,
			    rightIndex1).split(",")) {
			Element sampleNode = new BaseElement("consample");
			sampleNode.setText(sample);
			controlNode.add(sampleNode);
		    }
		}
	    }
	    // 添加denovo
	    String denovo = proParam.getDenovo();
	    if (null != denovo && !"".equals(denovo)) {
		Element denovoNode = new BaseElement("denovo");
		denovoNode.setText(denovo);
		root.add(denovoNode);
	    }
	    doc.asXML();
	    try {
		XMLWriter output = new XMLWriter(new FileWriter(new File(
			proRealPath)));
		output.write(doc);
		output.close();
	    } catch (IOException e) {
		e.printStackTrace();
		result = 0;
	    }
	} else {// 某个app参数保存
	    File proFile = new File(proRealPath);
	    // 分两种情况，1、xml文件已存在 2、xml文件不存在
	    int softwareId = proParam.getSoftwareId();
	    if (!proFile.exists()) {// 文件不存在，新建文档
		Document doc = DocumentHelper.createDocument();
		// 添加根节点
		Element root = doc.addElement("params");
		// 添加sampleList信息
		String sampleList = proParam.getSampleList();
		if (null != sampleList && !"".equals(sampleList)) {
		    Element sampleNode = new BaseElement("sampleList");
		    root.add(sampleNode);
		    for (String subSample : sampleList.split(";")) {
			String[] samContent = subSample.split(":");

			Element sampleSubNode = new BaseElement("sample");
			sampleSubNode.addAttribute("name", samContent[0]);
			sampleNode.add(sampleSubNode);

			for (String data : samContent[1].split(",")) {
			    Element dataNode = new BaseElement("data");
			    dataNode.setText(data);
			    sampleSubNode.add(dataNode);
			}
		    }
		}
		if (softwareId == 74) {// 外显子，只需要保存样本信息

		} else if (softwareId == 76) {// minRNA_MG,需要保存样本、接口序列、物种信息
		    // 添加物种字节点
		    String strain = proParam.getStrain();
		    if (null != strain && !"".equals(strain)) {
			Element strainNode = new BaseElement("strain");
			strainNode.setText(strain);
			root.add(strainNode);
		    }
		    // 添加adaptor3信息
		    String ada3 = proParam.getAdaptor3();
		    if (null != ada3 && !"".equals(ada3)) {
			Element ada3Node = new BaseElement("adaptor3");
			ada3Node.setText(ada3);
			root.add(ada3Node);
		    }
		    // 添加adaptor5信息
		    String ada5 = proParam.getAdaptor5();
		    if (null != ada5 && !"".equals(ada5)) {
			Element ada5Node = new BaseElement("adaptor5");
			ada5Node.setText(ada5);
			root.add(ada5Node);
		    }
		} else if (softwareId == 77) {// minRNA_MG,需要保存样本、物种信息、差异设置信息
		    // 添加物种字节点
		    String strain = proParam.getStrain();
		    if (null != strain && !"".equals(strain)) {
			Element strainNode = new BaseElement("strain");
			strainNode.setText(strain);
			root.add(strainNode);
		    }
		    // 添加diffList参数信息
		    String diffList = proParam.getDiffList();
		    if (null != diffList && !"".equals(diffList)) {
			Element diffNode = new BaseElement("diffList");
			root.add(diffNode);
			for (String diff : diffList.split(";")) {
			    String[] diffContent = diff.split(":");
			    Element diffSubNode = new BaseElement("diff");
			    diffSubNode.addAttribute("name", diffContent[0]);
			    diffNode.add(diffSubNode);

			    String diffTrCon = diffContent[1];
			    String[] trco = diffTrCon.split("#");
			    String treatment = trco[0];
			    int leftqIndex = treatment.indexOf("[");
			    int rightIndex = treatment.indexOf("]");
			    String trname = treatment.substring(0, leftqIndex);
			    Element treatmentNode = new BaseElement("treatment");
			    treatmentNode.addAttribute("name", trname);
			    diffSubNode.add(treatmentNode);
			    for (String sample : treatment.substring(
				    leftqIndex + 1, rightIndex).split(",")) {
				Element sampleNode = new BaseElement("trsample");
				sampleNode.setText(sample);
				treatmentNode.add(sampleNode);
			    }

			    String control = trco[1];
			    int leftqIndex1 = control.indexOf("[");
			    int rightIndex1 = control.indexOf("]");
			    String conname = control.substring(0, leftqIndex1);
			    Element controlNode = new BaseElement("control");
			    controlNode.addAttribute("name", conname);
			    diffSubNode.add(controlNode);
			    for (String sample : control.substring(
				    leftqIndex1 + 1, rightIndex1).split(",")) {
				Element sampleNode = new BaseElement(
					"consample");
				sampleNode.setText(sample);
				controlNode.add(sampleNode);
			    }
			}
		    }
		} else if (softwareId == 79) {// dn_diff:样本、差异、denovo结果文件
		    // 添加diffList参数信息
		    String diffList = proParam.getDiffList();
		    if (null != diffList && !"".equals(diffList)) {
			Element diffNode = new BaseElement("diffList");
			root.add(diffNode);
			for (String diff : diffList.split(";")) {
			    String[] diffContent = diff.split(":");
			    Element diffSubNode = new BaseElement("diff");
			    diffSubNode.addAttribute("name", diffContent[0]);
			    diffNode.add(diffSubNode);

			    String diffTrCon = diffContent[1];
			    String[] trco = diffTrCon.split("#");
			    String treatment = trco[0];
			    int leftqIndex = treatment.indexOf("[");
			    int rightIndex = treatment.indexOf("]");
			    String trname = treatment.substring(0, leftqIndex);
			    Element treatmentNode = new BaseElement("treatment");
			    treatmentNode.addAttribute("name", trname);
			    diffSubNode.add(treatmentNode);
			    for (String sample : treatment.substring(
				    leftqIndex + 1, rightIndex).split(",")) {
				Element sampleNode = new BaseElement("trsample");
				sampleNode.setText(sample);
				treatmentNode.add(sampleNode);
			    }

			    String control = trco[1];
			    int leftqIndex1 = control.indexOf("[");
			    int rightIndex1 = control.indexOf("]");
			    String conname = control.substring(0, leftqIndex1);
			    Element controlNode = new BaseElement("control");
			    controlNode.addAttribute("name", conname);
			    diffSubNode.add(controlNode);
			    for (String sample : control.substring(
				    leftqIndex1 + 1, rightIndex1).split(",")) {
				Element sampleNode = new BaseElement(
					"consample");
				sampleNode.setText(sample);
				controlNode.add(sampleNode);
			    }
			}
		    }
		    // 添加denovo
		    String denovo = proParam.getDenovo();
		    if (null != denovo && !"".equals(denovo)) {
			Element denovoNode = new BaseElement("denovo");
			denovoNode.setText(denovo);
			root.add(denovoNode);
		    }
		}
		try {
		    XMLWriter output = new XMLWriter(new FileWriter(new File(
			    proRealPath)));
		    output.write(doc);
		    output.close();
		} catch (IOException e) {
		    e.printStackTrace();
		    result = 0;
		}
	    } else {// 如果文件已经存在，则修改相应节点的信息
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
		    doc = reader.read(new File(proRealPath));
		} catch (DocumentException e) {
		    result = 0;
		    e.printStackTrace();
		    return result;
		}
		Element rootNode = (Element) doc.selectSingleNode("//params");
		if (softwareId == 74) {// 外显子，只需要修改样本信息
		    Element samNode = (Element) doc
			    .selectSingleNode("//sampleList");
		    if (null != samNode) {
			rootNode.remove(samNode);
		    }
		    // 保存样本信息
		    // 保存样本信息
		    String sampleList = proParam.getSampleList();
		    if (null != sampleList && !"".equals(sampleList)) {
			Element sampleNode = new BaseElement("sampleList");
			rootNode.add(sampleNode);
			for (String subSample : sampleList.split(";")) {
			    String[] samContent = subSample.split(":");

			    Element sampleSubNode = new BaseElement("sample");
			    sampleSubNode.addAttribute("name", samContent[0]);
			    sampleNode.add(sampleSubNode);

			    for (String data : samContent[1].split(",")) {
				Element dataNode = new BaseElement("data");
				dataNode.setText(data);
				sampleSubNode.add(dataNode);
			    }
			}
		    }
		} else if (softwareId == 76) {// minRNA_MG,需要修改样本、接口序列、物种信息
		    Element samNode = (Element) doc
			    .selectSingleNode("//sampleList");
		    if (null != samNode) {
			rootNode.remove(samNode);
		    }
		    Element strainNode = (Element) doc
			    .selectSingleNode("//strain");
		    if (null != strainNode) {
			rootNode.remove(strainNode);
		    }
		    Element ada3Node = (Element) doc
			    .selectSingleNode("//adaptor3");
		    if (null != ada3Node) {
			rootNode.remove(ada3Node);
		    }
		    Element ada5Node = (Element) doc
			    .selectSingleNode("//adaptor5");
		    if (null != ada5Node) {
			rootNode.remove(ada5Node);
		    }
		    // 保存样本信息
		    String sampleList = proParam.getSampleList();
		    if (null != sampleList && !"".equals(sampleList)) {
			Element sampleNode = new BaseElement("sampleList");
			rootNode.add(sampleNode);
			for (String subSample : sampleList.split(";")) {
			    String[] samContent = subSample.split(":");

			    Element sampleSubNode = new BaseElement("sample");
			    sampleSubNode.addAttribute("name", samContent[0]);
			    sampleNode.add(sampleSubNode);

			    for (String data : samContent[1].split(",")) {
				Element dataNode = new BaseElement("data");
				dataNode.setText(data);
				sampleSubNode.add(dataNode);
			    }
			}
		    }
		    // 添加物种字节点
		    String strain = proParam.getStrain();
		    if (null != strain && !"".equals(strain)) {
			Element strainNode1 = new BaseElement("strain");
			strainNode1.setText(strain);
			rootNode.add(strainNode1);
		    }
		    // 添加adaptor3信息
		    String ada3 = proParam.getAdaptor3();
		    if (null != ada3 && !"".equals(ada3)) {
			Element ada3Node1 = new BaseElement("adaptor3");
			ada3Node1.setText(ada3);
			rootNode.add(ada3Node1);
		    }
		    // 添加adaptor5信息
		    String ada5 = proParam.getAdaptor5();
		    if (null != ada5 && !"".equals(ada5)) {
			Element ada5Node1 = new BaseElement("adaptor5");
			ada5Node1.setText(ada5);
			rootNode.add(ada5Node1);
		    }
		} else if (softwareId == 77) {// minRNA_MG,需要修改样本、物种信息、差异设置信息
		    Element samNode = (Element) doc
			    .selectSingleNode("//sampleList");
		    if (null != samNode) {
			rootNode.remove(samNode);
		    }
		    // 保存样本信息
		    String sampleList = proParam.getSampleList();
		    if (null != sampleList && !"".equals(sampleList)) {
			Element sampleNode = new BaseElement("sampleList");
			rootNode.add(sampleNode);
			for (String subSample : sampleList.split(";")) {
			    String[] samContent = subSample.split(":");

			    Element sampleSubNode = new BaseElement("sample");
			    sampleSubNode.addAttribute("name", samContent[0]);
			    sampleNode.add(sampleSubNode);

			    for (String data : samContent[1].split(",")) {
				Element dataNode = new BaseElement("data");
				dataNode.setText(data);
				sampleSubNode.add(dataNode);
			    }
			}
		    }
		    Element strainNode = (Element) doc
			    .selectSingleNode("//strain");
		    if (null != strainNode) {
			rootNode.remove(strainNode);
		    }
		    // 添加物种字节点
		    String strain = proParam.getStrain();
		    if (null != strain && !"".equals(strain)) {
			Element strainNode1 = new BaseElement("strain");
			strainNode1.setText(strain);
			rootNode.add(strainNode1);
		    }
		    Element diffNode = (Element) doc
			    .selectSingleNode("//diffList");
		    if (null != diffNode) {
			rootNode.remove(diffNode);
		    }
		    // 添加diffList参数信息
		    String diffList = proParam.getDiffList();
		    if (null != diffList && !"".equals(diffList)) {
			Element diffNode1 = new BaseElement("diffList");
			rootNode.add(diffNode1);
			for (String diff : diffList.split(";")) {
			    String[] diffContent = diff.split(":");
			    Element diffSubNode = new BaseElement("diff");
			    diffSubNode.addAttribute("name", diffContent[0]);
			    diffNode1.add(diffSubNode);

			    String diffTrCon = diffContent[1];
			    String[] trco = diffTrCon.split("#");
			    String treatment = trco[0];
			    int leftqIndex = treatment.indexOf("[");
			    int rightIndex = treatment.indexOf("]");
			    String trname = treatment.substring(0, leftqIndex);
			    Element treatmentNode = new BaseElement("treatment");
			    treatmentNode.addAttribute("name", trname);
			    diffSubNode.add(treatmentNode);
			    for (String sample : treatment.substring(
				    leftqIndex + 1, rightIndex).split(",")) {
				Element sampleNode = new BaseElement("trsample");
				sampleNode.setText(sample);
				treatmentNode.add(sampleNode);
			    }

			    String control = trco[1];
			    int leftqIndex1 = control.indexOf("[");
			    int rightIndex1 = control.indexOf("]");
			    String conname = control.substring(0, leftqIndex1);
			    Element controlNode = new BaseElement("control");
			    controlNode.addAttribute("name", conname);
			    diffSubNode.add(controlNode);
			    for (String sample : control.substring(
				    leftqIndex1 + 1, rightIndex1).split(",")) {
				Element sampleNode = new BaseElement(
					"consample");
				sampleNode.setText(sample);
				controlNode.add(sampleNode);
			    }
			}
		    }
		} else if (softwareId == 79) {
		    Element samNode = (Element) doc
			    .selectSingleNode("//sampleList");
		    if (null != samNode) {
			rootNode.remove(samNode);
		    }
		    // 保存样本信息
		    String sampleList = proParam.getSampleList();
		    if (null != sampleList && !"".equals(sampleList)) {
			Element sampleNode = new BaseElement("sampleList");
			rootNode.add(sampleNode);
			for (String subSample : sampleList.split(";")) {
			    String[] samContent = subSample.split(":");

			    Element sampleSubNode = new BaseElement("sample");
			    sampleSubNode.addAttribute("name", samContent[0]);
			    sampleNode.add(sampleSubNode);

			    for (String data : samContent[1].split(",")) {
				Element dataNode = new BaseElement("data");
				dataNode.setText(data);
				sampleSubNode.add(dataNode);
			    }
			}
		    }
		    Element diffNode = (Element) doc
			    .selectSingleNode("//diffList");
		    if (null != diffNode) {
			rootNode.remove(diffNode);
		    }
		    // 添加diffList参数信息
		    String diffList = proParam.getDiffList();
		    if (null != diffList && !"".equals(diffList)) {
			Element diffNode1 = new BaseElement("diffList");
			rootNode.add(diffNode1);
			for (String diff : diffList.split(";")) {
			    String[] diffContent = diff.split(":");
			    Element diffSubNode = new BaseElement("diff");
			    diffSubNode.addAttribute("name", diffContent[0]);
			    diffNode1.add(diffSubNode);

			    String diffTrCon = diffContent[1];
			    String[] trco = diffTrCon.split("#");
			    String treatment = trco[0];
			    int leftqIndex = treatment.indexOf("[");
			    int rightIndex = treatment.indexOf("]");
			    String trname = treatment.substring(0, leftqIndex);
			    Element treatmentNode = new BaseElement("treatment");
			    treatmentNode.addAttribute("name", trname);
			    diffSubNode.add(treatmentNode);
			    for (String sample : treatment.substring(
				    leftqIndex + 1, rightIndex).split(",")) {
				Element sampleNode = new BaseElement("trsample");
				sampleNode.setText(sample);
				treatmentNode.add(sampleNode);
			    }

			    String control = trco[1];
			    int leftqIndex1 = control.indexOf("[");
			    int rightIndex1 = control.indexOf("]");
			    String conname = control.substring(0, leftqIndex1);
			    Element controlNode = new BaseElement("control");
			    controlNode.addAttribute("name", conname);
			    diffSubNode.add(controlNode);
			    for (String sample : control.substring(
				    leftqIndex1 + 1, rightIndex1).split(",")) {
				Element sampleNode = new BaseElement(
					"consample");
				sampleNode.setText(sample);
				controlNode.add(sampleNode);
			    }
			}
		    }
		    Element denovoNode = (Element) doc
			    .selectSingleNode("//denovo");
		    if (null != denovoNode) {
			rootNode.remove(denovoNode);
		    }
		    // 保存denovo信息
		    String denovo = proParam.getDenovo();
		    if (null != denovo && !"".equals(denovo)) {
			Element denovoNode1 = new BaseElement("denovo");
			denovoNode1.setText(denovo);
			rootNode.add(denovoNode1);
		    }
		}
		try {
		    XMLWriter output = new XMLWriter(new FileWriter(new File(
			    proRealPath)));
		    output.write(doc);
		    output.close();
		} catch (IOException e) {
		    result = 0;
		    e.printStackTrace();
		}
	    }
	}
	return result;
    }

    /**
     * 获取项目参数
     * 
     * @param projectId
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getProjectParams(int projectId,
	    String proFileDir) {
	Map<String, Object> proMap = null;
	String newPath = proFileDir + "/" + projectId + ".xml";
	File file = new File(newPath);
	if (!file.exists()) {
	    return proMap;
	} else {
	    proMap = new HashMap<String, Object>();
	    SAXReader reader = new SAXReader();
	    Document doc = null;
	    try {
		doc = reader.read(new File(newPath));
	    } catch (DocumentException e) {
		e.printStackTrace();
	    }
	    String strain = "";
	    String ada3 = "";
	    String ada5 = "";
	    if (doc != null) {
		Element strainNode = (Element) doc.selectSingleNode("//strain");
		if (null != strainNode) {
		    strain = strainNode.getText();
		}
		Element ada3Node = (Element) doc.selectSingleNode("//adaptor3");
		if (null != ada3Node) {
		    ada3 = ada3Node.getText();
		}
		Element ada5Node = (Element) doc.selectSingleNode("//adaptor5");
		if (null != ada5Node) {
		    ada5 = ada5Node.getText();
		}
		// sampleList
		List<ProSample> samList = null;
		Element sampleListNode = (Element) doc
			.selectSingleNode("//sampleList");
		if (null != sampleListNode) {
		    samList = new ArrayList<ProSample>();
		    ProSample proSample = null;
		    List<Element> samNodeList = sampleListNode.elements();
		    Iterator<Element> it = samNodeList.iterator();
		    while (it.hasNext()) {
			proSample = new ProSample();
			Element samNode = (Element) it.next();
			proSample.setName(samNode.attributeValue("name"));
			List<String> samDataList = new ArrayList<String>();
			List<Element> samSubNodeList = samNode.elements();
			Iterator<Element> subit = samSubNodeList.iterator();
			while (subit.hasNext()) {
			    Element samSubNode = (Element) subit.next();
			    samDataList.add(samSubNode.getText());
			}
			proSample.setDataList(samDataList);
			samList.add(proSample);
		    }
		}
		// diffList
		Map<String, Map<String, List<String>>> diffListMap = null;
		Element diffListNode = (Element) doc
			.selectSingleNode("//diffList");
		if (null != diffListNode) {
		    diffListMap = new TreeMap<String, Map<String, List<String>>>();
		    List<Element> diffNodeList = diffListNode.elements();
		    Iterator<Element> it = diffNodeList.iterator();
		    while (it.hasNext()) {
			Element diffNode = (Element) it.next();
			String diffName = diffNode.attributeValue("name");
			// 获取treatment和contrl
			Map<String, List<String>> diffMap = new HashMap<String, List<String>>();
			List<Element> diffSubNodes = diffNode.elements();
			Iterator<Element> subit = diffSubNodes.iterator();
			while (subit.hasNext()) {
			    Element diffSubNode = (Element) subit.next();
			    String nodeName = diffSubNode.getName();
			    String diffSubName = diffSubNode
				    .attributeValue("name");
			    if (nodeName.equals("treatment")) {
				List<String> subTrSampleList = new ArrayList<String>();
				List<Element> diffSubSampleNodes = diffSubNode
					.elements();
				Iterator<Element> diffsubit = diffSubSampleNodes
					.iterator();
				while (diffsubit.hasNext()) {
				    Element diffSubSampleNode = (Element) diffsubit
					    .next();
				    subTrSampleList.add(diffSubSampleNode
					    .getText());
				}
				diffMap.put(diffSubName, subTrSampleList);
			    } else if (nodeName.equals("control")) {
				List<String> subConSampleList = new ArrayList<String>();
				List<Element> diffSubSampleNodes = diffSubNode
					.elements();
				Iterator<Element> diffsubit = diffSubSampleNodes
					.iterator();
				while (diffsubit.hasNext()) {
				    Element diffSubSampleNode = (Element) diffsubit
					    .next();
				    subConSampleList.add(diffSubSampleNode
					    .getText());
				}
				diffMap.put(diffSubName, subConSampleList);
			    }
			}
			diffListMap.put(diffName, diffMap);
		    }
		}
		// denovo
		String denovo = "";
		Element denovoNode = (Element) doc.selectSingleNode("//denovo");
		if (null != denovoNode) {
		    denovo = denovoNode.getText();
		}
		proMap.put("strain", strain);
		proMap.put("ada3", ada3);
		proMap.put("ada5", ada5);
		proMap.put("sampleList", samList);
		proMap.put("diffList", diffListMap);
		proMap.put("denovo", denovo);
	    }
	}
	return proMap;
    }

    /**
     * 删除项目的参数
     * 
     * @param projectId
     * @param softwareId
     * @param xmlPath
     */
    public static void deleteProParams(int projectId, int softwareId,
	    String xmlPath) {

    }
}

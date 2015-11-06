package com.nova.tools.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.nova.tools.constant.AppNameIDConstant;
import com.nova.tools.constant.ReportStateConstant;
import com.nova.tools.service.impl.ChangeStateServiceImpl;
import com.nova.tools.service.impl.RunAppServiceImpl;
import com.nova.tools.utils.DateUtil;
import com.nova.tools.utils.EmailUtil;
import com.nova.tools.utils.FileTools;
import com.nova.tools.utils.XMLUtil;

/**
 * @Description: App 运行 service
 * @author lin
 * @date 2013-7-29 下午8:51:35
 */
public class RunAppService {
	//TODO 不需要在Tools端进行后续处理的需要在这里配置下
    private static final List<String> apps = Arrays.asList("81","82", "83", "85",
            "86", "87", "88", "91", "92", "93", "94", "104");

    /**
     * 运行项目
     * 
     * @param basePath
     * @param userId
     * @param appId
     * @param appName
     * @param projectId
     * @param dataKeyList
     * @param email
     * @param projectName
     * @param sampleList
     * @param ada3
     * @param ada5
     * @param sp
     * @param cpu
     * @param diffList
     * @param fileName
     * @return
     */
    public void runProject(String basePath, String userId, String appId,
            String appName, String projectId, String dataKey,
            String dataKeyList, String email, String projectName,
            String sampleList, String ada3, String ada5, String sp, String cpu,
            String diffList, String fileName, String dataInfos, String company,
            String user, String dept) {
        // 创建项目文件夹
        String projectPath = basePath + "/" + userId + "/" + appId + "/"
                + projectId;
        FileTools.createDir(projectPath);
        String param = "fileName=" + fileName + "&userId=" + userId + "&appId="
                + appId + "&dataKey=" + dataKeyList.split(",")[0]
                + "&projectId=" + projectId + "&sampleList=" + sampleList;
        String appPath = basePath + "/" + userId + "/" + appId;
        RunAppServiceImpl runApp = new RunAppServiceImpl();
        String start = DateUtil.formatNowDate();

        // MIB
        if (AppNameIDConstant.MIB.equals(appId)) {
            runApp.MIB(appPath, projectId, dataKey, fileName, dataKeyList,
                    appId, appName, userId, dataInfos, company, user, dept);
        }

        // split
        if (AppNameIDConstant.split.equals(appId)) {
            runApp.split(appPath, projectId, dataKeyList, appId, appName,
                    userId, dataInfos, company, user, dept);
        }

        // CMP
        if (AppNameIDConstant.CMP.equals(appId)
                || AppNameIDConstant.CMP_199.equals(appId)
                || AppNameIDConstant.GDD.equals(appId)) {
            runApp.runCMP(appPath, projectId, dataKeyList, appId, appName,
                    userId, dataInfos, company, user, dept);
        }

        // VSP
        if (AppNameIDConstant.VSP.equals(appId)) {
            runApp.VSP(appPath, projectId, dataKeyList);
        }

        // 16S
        if (AppNameIDConstant._16S.equals(appId)) {
            runApp._16S(appPath, projectId, dataKeyList);
        }

        // 序列翻译
        if (AppNameIDConstant.TRANSLATE.equals(appId)) {
            runApp.translate(appPath, projectId, dataKeyList);
        }

        // HCV
        if (AppNameIDConstant.HCV.equals(appId)) {
            runApp.runHCV(appPath, projectId, dataKeyList);
        }

        // tree
        if (AppNameIDConstant.Tree.equals(appId)) {
            runApp.runTree(basePath, userId, appId, projectId, dataKeyList);
        }

        // EGFR
        if (AppNameIDConstant.EGFR.equals(appId)) {
            runApp.runEGFRProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }

        // TB
        if (AppNameIDConstant.TB.equals(appId)) {
            runApp.runTBProject(appPath + "/", projectId, dataKeyList, appName);
        }

        // TB-INH
        if (AppNameIDConstant.TB_INH.equals(appId)) {
            runApp.runTBINHProject(appPath + "/", projectId, dataKeyList);
        }

        // KRAS
        if (AppNameIDConstant.KRAS.equals(appId)) {
            runApp.runKRASProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }

        // NIPT
        if (AppNameIDConstant.NIPT.equals(appId)) {
            runApp.runNIPTProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }

        // SNP
        if (AppNameIDConstant.SNP.equals(appId)) {
            runApp.runSNPProject(appPath + "/", projectId, dataKeyList);
        }

        // HBV_SNP2
        if (AppNameIDConstant.HBV_SNP2.equals(appId)) {
            runApp.runHBV_SNP2Project(appPath + "/", projectId, dataKeyList,
                    basePath);
        }

        // DPD
        if (AppNameIDConstant.DPD.equals(appId)) {
            runApp.runDPDProject(appPath + "/", projectId, dataKeyList,
                    basePath);
        }

        // BRAF
        if (AppNameIDConstant.BRAF.equals(appId)) {
            runApp.runBRAFProject(appPath + "/", projectId, dataKeyList,
                    basePath);
        }

        // UGT
        if (AppNameIDConstant.UGT.equals(appId)) {
            runApp.runUGTProject(appPath + "/", projectId, dataKeyList,
                    basePath);
        }

        // SurePlex
        if (AppNameIDConstant.SurePlex.equals(appId)) {
            runApp.runSurePlexProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }

        // SurePlex_HR
        if (AppNameIDConstant.Sureplex_HR.equals(appId)) {
            runApp.runSurePlex_HRProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }

        // PGS
        if (AppNameIDConstant.PGS.equals(appId)) {
            runApp.runPGSProject(appPath + "/", projectId, dataKeyList, appName);
        }

        // gDNA
        if (AppNameIDConstant.gDNA.equals(appId)) {
            runApp.rungDNAProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }
        if (AppNameIDConstant.gDNA_Chimeric.equals(appId)) {
            runApp.rungDNA_ChimericProject(appPath + "/", projectId,
                    dataKeyList, appName);
        }
        if (AppNameIDConstant.MDA_Chimeric.equals(appId)) {
            runApp.runMDA_ChimericProject(appPath + "/", projectId,
                    dataKeyList, appName);
        }
        if (AppNameIDConstant.MDA_HR.equals(appId)) {
            runApp.runMDA_HRProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }

        // gDNA_MR
        if (AppNameIDConstant.gDNA_MR.equals(appId)) {
            runApp.rungDNA_MRProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }
        // gDNA_MR_v1
        if (AppNameIDConstant.gDNA_MR_v1.equals(appId)) {
            runApp.rungDNA_MR_v1Project(appPath + "/", projectId, dataKeyList,
                    appName);
        }
        // MDA_MR_v1
        if (AppNameIDConstant.MDA_MR_v1.equals(appId)) {
            runApp.runMDA_MR_v1Project(appPath + "/", projectId, dataKeyList,
                    appName);
        }
        // MDA_HR_v1
        if (AppNameIDConstant.MDA_HR_v1.equals(appId)) {
            runApp.runMDA_HR_v1Project(appPath + "/", projectId, dataKeyList,
                    appName);
        }
        // gDNA_HR_v1
        if (AppNameIDConstant.gDNA_HR_v1.equals(appId)) {
            runApp.rungDNA_HR_v1Project(appPath + "/", projectId, dataKeyList,
                    appName);
        }
        // MDA_Chimeric_v1
        if (AppNameIDConstant.MDA_Chimeric_v1.equals(appId)) {
            runApp.runMDA_Chimeric_v1Project(appPath + "/", projectId,
                    dataKeyList, appName);
        }
        // gDNA_Chimeric_v1
        if (AppNameIDConstant.gDNA_Chimeric_v1.equals(appId)) {
            runApp.rungDNA_Chimeric_v1Project(appPath + "/", projectId,
                    dataKeyList, appName);
        }
        // SurePlex_v1
        if (AppNameIDConstant.SurePlex_v1.equals(appId)) {
            runApp.runSurePlex_v1Project(appPath + "/", projectId, dataKeyList,
                    appName);
        }
        // MalBac_v1
        if (AppNameIDConstant.MalBac_v1.equals(appId)) {
            runApp.runMalBac_v1Project(appPath + "/", projectId, dataKeyList,
                    appName);
        }

        // MDA_MR
        if (AppNameIDConstant.MDA_MR.equals(appId)) {
            runApp.runMDA_MRProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }

        // MalBac
        if (AppNameIDConstant.MalBac.equals(appId)) {
            runApp.runMalBacProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }

        // gDNA_HR
        if (AppNameIDConstant.gDNA_HR.equals(appId)) {
            runApp.rungDNA_HRProject(appPath + "/", projectId, dataKeyList,
                    appName);
        }
        if (!apps.contains(appId)) {
            String txt = projectPath + "/" + projectId + ".txt";
            String xml = null;
            if (new File(txt).exists()) {
                xml = XMLUtil.writeXML(txt);
            }
            if (AppNameIDConstant.Tree.equals(appId)) {
                String dataList[] = dataKeyList.split(";");
                StringBuffer sb = new StringBuffer("<table>");
                sb.append("<tr>");
                sb.append("<td>DataKey</td>");
                sb.append("<td>FileName</td>");
                sb.append("</tr>");
                for (String data : dataList) {
                    sb.append("<tr>");
                    String d[] = data.split(",");
                    sb.append("<td>").append(d[0]).append("</td>");
                    sb.append("<td>").append(d[2]).append("</td>");
                    sb.append("</tr>");
                }
                sb.append("</table>");
                xml = sb.toString();
            }
            ChangeStateServiceImpl.changeState(appId, appName, projectId, null,
                    ReportStateConstant.FINISH, userId, xml);
            EmailUtil.sendEndEmail(projectName, appId, start, email, param,
                    true);
        }
    }
}
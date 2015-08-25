package com.celloud.mongo.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.celloud.mongo.sdo.PGSFilling;
import com.celloud.mongo.service.ReportService;
import com.google.inject.Inject;
import com.nova.action.BaseAction;
import com.nova.utils.ConnectManager;

@ParentPackage("celloud-default")
@Action("pgsprint")
public class PGSPrintAction  extends BaseAction{
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(PGSPrintAction.class);
	
	@Inject
	private ReportService reportService;
	
	public void pgs() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		String sql = "select r.*,f.data_key from tb_report r,tb_file f where print_context is not null and print_context!='' and f.file_id = r.file_id and r.user_id = 15";
		try {
			conn = ConnectManager.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("userId", rs.getString("user_id"));
				map.put("appId", rs.getString("software_id"));
				map.put("projectId", rs.getString("project_id"));
				map.put("fileId", rs.getString("file_id"));
				map.put("datakey", rs.getString("data_key"));
				map.put("context", rs.getString("print_context"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectManager.free(conn, ps, rs);
		}
//		for (int i = 0; i < list.size(); i++) {
			Map<String, String> m = list.get(list.size()-1);
			int userId = Integer.parseInt(m.get("userId"));
			int appId = Integer.parseInt(m.get("appId"));
			int dataKey = Integer.parseInt(m.get("datakey"));
			Document doc = Jsoup.parse(m.get("context"));
			Elements divs = doc.getElementsByTag("div");
			PGSFilling  pgs = new PGSFilling();
			for (Element div : divs) {
				String id = div.attr("id");
				if("des".equals(id)){
					pgs.setDescription(div.text());
				}
			}
			Elements inputs = doc.getElementsByTag("input");
			for (int i = 0; i < inputs.size(); i++) {
				String val = inputs.get(i).attr("value");
				if (i == 0)
					pgs.setMedicalNumber(val);
				if (i == 1)
					pgs.setNumber(val);
				if (i == 2)
					pgs.setPatientName(val);
				if (i == 3)
					pgs.setPatientSex(val);
				if (i == 4)
					pgs.setPatientAge(val);
				if (i == 5)
					pgs.setSampleType(val);
				if (i == 6)
					pgs.setApplyDate(val);
				if (i == 7)
					pgs.setReceiveDate(val);
				if (i == 8)
					pgs.setSampleStatus(val);
				if (i == 9)
					pgs.setDetection(val);
				if (i == 10)
					pgs.setReview(val);
				if (i == 11)
					pgs.setAuditor(val);
				if (i == 12)
					pgs.setYear(val);
				if (i == 13)
					pgs.setMonth(val);
				if (i == 14)
					pgs.setDay(val);
			}
			reportService.editPGSFilling(userId, appId, dataKey, pgs);
//			System.out.println(pgs.getMedicalNumber());
//			System.out.println(pgs.getNumber());
//			System.out.println(pgs.getPatientName());
//			System.out.println(pgs.getPatientSex());
//			System.out.println(pgs.getPatientAge());
//			System.out.println(pgs.getSampleType());
//			System.out.println(pgs.getApplyDate());
//			System.out.println(pgs.getReceiveDate());
//			System.out.println(pgs.getSampleStatus());
//			System.out.println(pgs.getDescription());
//			System.out.println(pgs.getDetection());
//			System.out.println(pgs.getReview());
//			System.out.println(pgs.getAuditor());
//			System.out.println(pgs.getYear());
//			System.out.println(pgs.getMonth());
//			System.out.println(pgs.getDay());
//		}
	}

}

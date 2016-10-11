package com.sms.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sms.constant.GadgetsConstants;
import com.sms.utils.FileTools;
import com.sms.utils.PerlUtils;

@WebServlet("/RunServlet")
public class RunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RunServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		String context = request.getParameter("context");
		String filePath = GadgetsConstants.FILEPATH + "/" + new Date().getTime() + ".fasta";
		FileTools.createFile(filePath);
		FileTools.appendWrite(filePath, context);
		String perlPath = request.getSession().getServletContext().getRealPath("/resource/perl/");
		String result = null;
		String command = null;
		if ("reversal".equals(type)) {
			command = "perl " + perlPath + "/reverse_seq.pl  " + filePath;
			result = PerlUtils.executeGadgetsPerl(command);
		} else if ("seqsub".equals(type)) {
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			command = "perl " + perlPath + "/cut_seq.pl  " + filePath + " " + start + " " + end;
			result = PerlUtils.executeGadgetsPerl(command);
		} else {
			result = "不能识别的运行参数，请检查后再提交！";
		}
		result = PerlUtils.executeGadgetsPerl(command);
		response.getWriter().append(result);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

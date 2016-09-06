package com.celloud.box.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorHandleController implements ErrorController {
	private static final String ERROR_PATH = "/error";

	@RequestMapping(ERROR_PATH)
	@ResponseBody
	public String errorHandle(HttpServletResponse response) {
		return "error";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}

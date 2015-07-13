package com.nova.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.sdo.Behavior;
import com.nova.service.IBehaviorService;

@ParentPackage("celloud-default")
@Action("behavior")
@Results({ @Result(name = "getList", type = "json", params = { "root", "date" }) })
public class BehaviorAction extends BaseAction{
	private static final long serialVersionUID = 1L;

	private String date;
	private List<Behavior> list;
	@Inject
	private IBehaviorService ibs;

	public String getBehaviorList() {
		list = ibs.getBehaviorList(date);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		String address = null;
		for (int i = 0; i < list.size(); i++) {
			address = list.get(i).getAddress();
			sb.append("{address:" + address.substring(11, 13) + ",userName:"
					+ list.get(i).getUserName() + ",time:'" + address + "'},");
		}
		date = sb.toString();
		date = date.substring(0, date.length() - 1) + "]";
		return "getList";
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<Behavior> getList() {
		return list;
	}

	public void setList(List<Behavior> list) {
		this.list = list;
	}
}

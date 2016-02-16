package com.celloud.utils;

import java.util.ArrayList;
import java.util.List;

import com.celloud.sdo.DataFile;

public class EntryUtil {
	/**
	 * 补齐时间现在只能是降序
	 * @param list
	 * @return
	 */
	public static List<DataFile> toInsert(List<DataFile> list){
		if(list==null||list.size()<=2)
			return list;
		 List<DataFile> res = new ArrayList<DataFile>(list.size());
		 for(int i=0;i<list.size()-1;i++){
			 res.addAll(toCreateList(list.get(i), list.get(i+1)));
		 }
		 res.add(list.get(list.size()-1));
		 return res;
	}
	
	public static List<DataFile> toCreateList(DataFile start,DataFile end){
		 List<DataFile> res = new ArrayList<DataFile>();
		return createOneData(res,start,end);
	}
	public static  List<DataFile> createOneData(List<DataFile>res,DataFile start,DataFile end){
		if(start==null)
			return res;
		else if (start!=null&&end==null){
			res.add(start);
			return res;
		}
		res.add(start);
		String nextMonth = DateUtil.nextMonth(start.getYearMonth(),start.getYearMonth(),end.getYearMonth());
		if(nextMonth.equals(end.getYearMonth())){
			return res;	
		}else{
			DataFile obj=null;
			try {
				obj = (DataFile) start.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			} 
			obj.setYearMonth(nextMonth);
			obj.setFileNum(0);
			obj.setSize(0l);
			createOneData(res,obj,end);
		}
		return res;
	}
public static void main(String[] args) {
	List<DataFile>list = new ArrayList<>();
	DataFile d1 = new DataFile();
	d1.setYearMonth("2014-07");
	d1.setFileNum(5);
	list.add(d1);
//	Data d2 = new Data();
//	d2.setYearMonth("2014-11");
//	d2.setFileNum(15);
//	list.add(d2);
//	
//	Data d3 = new Data();
//	d3.setYearMonth("2015-02");
//	d3.setFileNum(15);
//	list.add(d3);

	List<DataFile> l = toInsert(list);
	for (DataFile data : l) {
		System.out.println(data);
	}
}
}

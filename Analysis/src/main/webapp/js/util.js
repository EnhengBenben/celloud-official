// //Date类扩展
// ---------------------------------------------------
// 日期格式化
// 格式 YYYY/yyyy/YY/yy 表示年份
// MM/M 月份
// W/w 星期
// dd/DD/d/D 日期
// hh/HH/h/H 时间
// mm/m 分钟
// ss/SS/s/S 秒
// ---------------------------------------------------
Date.prototype.format = function(formatStr) {
	var str = formatStr;
	var Week = [ '日', '一', '二', '三', '四', '五', '六' ];
	
	str = str.replace(/yyyy|YYYY/, this.getFullYear());
	str = str.replace(/yy|YY/, (this.getYear() % 100) > 9 ? (this.getYear() % 100).toString() : '0' + (this.getYear() % 100));
	var m = this.getMonth() + 1;
	str = str.replace(/MM/, m > 9 ? m.toString() : '0' + m);
	str = str.replace(/M/g, this.getMonth());
	
	str = str.replace(/w|W/g, Week[this.getDay()]);
	
	str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : '0' + this.getDate());
	str = str.replace(/d|D/g, this.getDate());
	
	str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString() : '0' + this.getHours());
	str = str.replace(/h|H/g, this.getHours());
	str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes().toString() : '0' + this.getMinutes());
	str = str.replace(/m/g, this.getMinutes());
	
	str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds().toString() : '0' + this.getSeconds());
	str = str.replace(/s|S/g, this.getSeconds());
	
	return str;
}

function showWeekFirstDay() {
	var Nowdate = new Date();
	var WeekFirstDay = new Date(Nowdate - (Nowdate.getDay() - 1) * 86400000);
	return WeekFirstDay.format('yyyy-MM-dd')
}
/**
 * 本周最后一天
 */
function showWeekLastDay() {
	var Nowdate = new Date();
	var WeekFirstDay = new Date(Nowdate - (Nowdate.getDay() - 1) * 86400000);
	var WeekLastDay = new Date((WeekFirstDay / 1000 + 6 * 86400) * 1000);
	return WeekLastDay.format('yyyy-MM-dd')
}
/**
 * 本月第一天
 */
function showMonthFirstDay() {
	var Nowdate = new Date();
	var MonthFirstDay = new Date(Nowdate.getFullYear(), Nowdate.getMonth(), 1);
	return MonthFirstDay.format('yyyy-MM-dd')
}
/**
 * 本月最后一天
 */
function showMonthLastDay() {
	var Nowdate = new Date();
	var MonthNextFirstDay = new Date(Nowdate.getFullYear(), Nowdate.getMonth() + 1, 1);
	var MonthLastDay = new Date(MonthNextFirstDay - 86400000);
	return MonthLastDay.format('yyyy-MM-dd')
}

Date.prototype.yearmonth = function() {
	var m = this.getMonth() + 1;
	var mm = m < 10 ? '0' + m : m;
	return this.getFullYear() + '-' + mm;
}
// 周日
Date.prototype.sunday = function() {
	var l = this.getTime();
	// 周日是每一天
	var n = this.getDay();
	if (n == 0) {// 周日
		return new Date(l);
	} else {
		var d = 7 - n;
		return new Date(l + d * 24 * 60 * 60 * 1000);
	}
}

Date.prototype.Monday = function() {
	var l = this.getTime();
	// 周日是每一天
	var d = this.getDay();
	if (d == 0) {
		d = 13;
	} else
		d = d + 7
	return new Date(l - d * 24 * 60 * 60 * 1000);
}

Date.prototype.lastWeekMonday = function(var_date) {
	var l = var_date.getTime();
	var d = var_date.getDay();
	if (d == 0) {
		d = 6 + 7
	} else {
		d = 7 + d;
	}
	return new Date(this.getTime() - d * 24 * 60 * 60 * 1000);
}

Date.prototype.lastWeekSunday = function(var_date) {
	var l = var_date.getTime();
	var d = var_date.getDay();
	if (d == 0) {
		d = 7
	} else {
		d = 1 + d;
	}
	return new Date(this.getTime() - d * 24 * 60 * 60 * 1000);
}
Date.prototype.localMonDay = function(var_date) {
	var l = var_date.getTime();
	var d = var_date.getDay();
	if (d == 0) {
		d = -6
	} else {
		d = d - 1;
	}
	return new Date(this.getTime() - d * 24 * 60 * 60 * 1000);
}

Date.prototype.monthStart = function() {
	var l = this.getTime();
	var d = this.getDate() - 1;
	return new Date(l - d * 24 * 60 * 60 * 1000).format('yyyy-MM-dd');
}
Date.prototype.monthEnd = function() {
	var da = new Date();
	return da.getFullYear() + "-" + da.getMonth() + "-" + da.MaxDayOfDate;
}
/*******************************************************************************
 * 保留每月每周前几条的数据记录
 * 
 * @param list
 * @param top
 * @param poperty
 * @returns {Array}
 */
function doTopN(list, top, poperty) {
	var lt = [];
	if (list == null || list.length < 1)
		return [];
	if (poperty == "yearMonth") {
		var n = 1;// topN
		var k = 0;// 数组下标
		var ym = list[0].yearMonth;
		lt[0] = list[0]
		for (var i = 1; i < list.length; i++) {
			if (ym == list[i].yearMonth && n <= top) {
				lt[k] = list[i];
				k++;
				n++;
			} else if (ym != list[i].yearMonth) {
				ym = list[i].yearMonth;
				n = 1;
				lt[k] = list[i];
			}
		}
	} else if (poperty == "weekDate") {
		var n = 1;// topN
		var k = 0;// 数组下标
		var ym = list[0].weekDate;
		for (var i = 0; i < list.length; i++) {
			if (ym == list[i].weekDate && n <= top) {
				lt[k] = list[i];
				k++;
				n++;
			} else if (ym != list[i].weekDate) {
				ym = list[i].weekDate;
				n = 1;
				lt[k] = list[i];
			}
		}
	}
	return lt;
}
/** * */
function ListAvg(list) {
	var count = 0;
	if (list.length > 1) {
		count = list[0];
		for (var i = 1; i < list.length; i++) {
			count = count + list[i];
		}
		return count / list.length;
	} else if (list.length == 1) {
		return list[0];
	}
	
}

jQuery.fn.dataTableExt.oSort['filesize-desc'] = function(a, b) {
	if (a == null || b == null)
		return 0;
	var alen = a.substring(a.length - 2, a.lenght);
	var blen = b.substring(a.length - 2, a.lenght);
	
	if (alen == blen) {// 单位一样比较数字
		var av = parseFloat(a);
		var bv = parseFloat(b);
		return av > bv ? 0 : 1;
	} else {
		if (alen == "GB" || alen == "gb") {
			return 0;
		} else if (alen == "KB" || alen == "kb") {
			return 1;
		} else {
			if (blen == "GB" || blen == "gb") {
				return 1;
			} else {
				return 0;
			}
		}
	}
};

jQuery.fn.dataTableExt.oSort['filesize-asc'] = function(a, b) {
	if (a == null || b == null)
		return 0;
	var alen = a.substring(a.length - 2, a.lenght);
	var blen = b.substring(a.length - 2, a.lenght);
	
	if (alen == blen) {// 单位一样比较数字
		var av = parseFloat(a);
		var bv = parseFloat(b);
		return av < bv ? 0 : 1;
	} else {
		if (alen == "GB" || alen == "gb") {
			return 1;
		} else if (alen == "KB" || alen == "kb") {
			return 0;
		} else {
			if (blen == "GB" || blen == "gb") {
				return 0;
			} else {
				return 1;
			}
		}
	}
};

/** ** */
var Env = {
	"dev_mode" : true,
}
function logReq(url, msg) {
	if (Env.dev_mode) {
		console.log("URL:" + url)
		console.log(msg);
	}
}
function logRes(msg) {
	if (Env.dev_mode) {
		console.log(msg);
	}
}

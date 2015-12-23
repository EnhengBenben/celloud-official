//Date类扩展
Date.prototype.format = function() {
	var m = this.getMonth() + 1;
	var day = this.getDate();
	var dd = day < 10 ? '0' + day : day
	return this.getFullYear() + '/' + m + '/' + dd;
}
Date.prototype.yearmonth = function() {
	var m = this.getMonth() + 1;
	var mm = m < 10 ? '0' + m : m;
	return this.getFullYear() + '/' + mm;
}
// 周日
Date.prototype.sunday = function() {
	var l = this.getTime();
	// 周日是每一天
	var n = this.getDay();
	if (n == 0) {// 周日
		return new Date(l).format();
	} else {
		var d = 7 - n;
		return new Date(l + d * 24 * 60 * 60 * 1000).format();
	}
}

Date.prototype.Monday = function() {
	var l = this.getTime();
	// 周日是每一天
	var d = this.getDay();
	if (d == 0) {
		d = 13;
	} else {
		d = d + 7
	}
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

Date.prototype.month = function() {
	var l = this.getTime();
	// 周月是每一天
	var d = this.getDate();
	return new Date(this.getTime() + d * 24 * 60 * 60 * 1000).format();
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

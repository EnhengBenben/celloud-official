/**
 * 判断Cmp文件是否配对
 * @param fileNames
 * @returns {Boolean}
 */
function checkCmpData(fileNames){
	flag = 0;
	var isTrue = true;
	var tempArr = new Array();
	var total = "";
	for (i=0;i < fileNames.length; i++) {
		var fileName = fileNames[i];
		var dot = fileName.lastIndexOf('.');
		var newName = fileName.substring(0, dot);
		length = newName.length;
		if (newName.endsWith("1") || newName.endsWith("2")) {
			tempArr.push(newName.substring(0, length - 1));
			total+= newName+",";
		} else {
			isTrue = false;
		}
	}
	for (i=0;i<tempArr.length;i++) {
		var count = 0;
		var temp = total.split(tempArr[i]);
		count = temp.length;
		if (count < 3) {
			isTrue = false;
			break;
		}
	}
	return isTrue;
}

//Written by Paul Stothard, University of Alberta, Canada

function dnaPattern (theDocument) {	
	var result = "";
	var newDna = "";
	var maxInput = 500000;
	if (testScript() == false) {
		return false;
	}
	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (checkSequenceLength(theDocument.forms[0].elements[0].value, maxInput) == false) || (checkFormElement (theDocument.forms[0].elements[1]) == false))	{
		return false;
	}
	var re = "/" + theDocument.forms[0].elements[1].value.replace(/\//g,"") + "/gi";
	re = removeWhiteSpace(re);
	try {
		re = eval(re);
		var testString = "teststring";
		testString = testString.replace(re, "");
	}
	catch(e) {
		alert ("The regular expression is not formatted correctly.");
		return false;
	}
	var arrayOfFasta = getArrayOfFasta (theDocument.forms[0].elements[0].value);
	for (var i = 0; i < arrayOfFasta.length; i++)	{
		newDna = getSequenceFromFasta (arrayOfFasta[i]);
		title = getTitleFromFasta (arrayOfFasta[i]);
		newDna = removeNonDna(newDna);
		result += getInfoFromTitleAndSequence(title, newDna);
		result += writeDnaPattern(newDna, re);
		result += '<br/><br/>';
	}
	return result;
}

function writeDnaPattern (dnaSequence, re)	{
	var result = "";
	var matchArray;
	var matchCount = 0;
	var length = dnaSequence.length;
        var simplePattern = re.toString();
        simplePattern = simplePattern.replace(/\/gi$|\/ig$|^\//gi, "");
	while (matchArray = re.exec(dnaSequence)) {
	    matchCount++;
        var match_end = re.lastIndex;
        var match_start = match_end - RegExp.lastMatch.length + 1;
        result += "&gt;match number " + matchCount + " to \"" + simplePattern + "\" start=" + match_start + " end=" + match_end + " on the direct strand<br/>" + addReturns(matchArray[0]) + "<br/><br/>";
		re.lastIndex = re.lastIndex - RegExp.lastMatch.length + 1;
	}
	//reset search to start of sequence
	re.lastIndex = 0;
	//now search the reverse-complement
	dnaSequence = reverse(complement(dnaSequence));
	while (matchArray = re.exec(dnaSequence)) {
        matchCount++;
        var match_start = (length - re.lastIndex + 1);
        var match_end = match_start + RegExp.lastMatch.length - 1;	
        result += "&gt;match number " + matchCount + " to \"" + simplePattern + "\" start=" + match_start + " end=" + match_end + " on the reverse strand<br/>" + addReturns(matchArray[0]) + "<br/><br/>";
		re.lastIndex = re.lastIndex - RegExp.lastMatch.length + 1;			
	}		
	if (!(matchCount > 0)) {
		result += "no matches found for this sequence.<br/><br/>";
	}
	return result;
}


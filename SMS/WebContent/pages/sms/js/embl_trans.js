//Written by Paul Stothard, University of Alberta, Canada

function emblTrans (theDocument) {	
	var maxInput = 200000;
	if (testScript() == false) {
		return false;
	}
	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (verifyEmblFeat (theDocument.forms[0].elements[0].value) == false) || (checkTextLength(theDocument.forms[0].elements[0].value, maxInput) == false))	{
		return false;
	}
	return emblTransExtract(theDocument.forms[0].elements[0].value);	
}
function emblTransExtract (emblFile)	{
	var result = "";
	var title;
	emblFile = "_" + emblFile + "_";
	var recordArray = emblFile.split(/ID\s\s\s[^\f\n\r]*/);
	for (var i=1; i < recordArray.length; i++) {
		var mainArray = emblFile.split (/[\f\n\r]\s*FH   Key             Location\/Qualifiers[\f\n\r]+\s*FH|[\f\n\r]\s*XX[\s]*[\f\n\r]\s*SQ[^\f\n\r]*/);
		if ((mainArray[0].search(/[\f\n\r]\s*DE[^\f\n\r]+/) != -1)) {
			title = ((mainArray[0].match(/[\f\n\r]\s*DE[^\f\n\r]+/)).toString()).replace(/[\f\n\r]\s*DE\s*/, "");
		}
		else {
			title = "Untitled";
		}
		title = filterFastaTitle(title.replace(/[\f\n\r\t]+$/g, "")) + "<br/>";
		var dnaSequenceArray = mainArray[2].split (/\/{2}/);
		result+=title + "\n";		
		if (dnaSequenceArray.length == 1) {
			alert ("The entire EMBL file may not have been processed.");
		}
		var featureArray = mainArray[1].split(/[\f\n\r]FT {3,12}\b/);
		result+=showFeatureTrans (featureArray);
	}
	return result;
}

function showFeatureTrans (arrayOfFeatures)	{
	var result ="";
	var featureTitle = "";
	var theTitle = "";
	var firstQualifier = "";
	var translation = "";
	var translationFound = false;
	for (var i=1; i < arrayOfFeatures.length; i++) {
		if (arrayOfFeatures[i].search(/\/translation/) != -1)	{
			arrayOfFeatures[i] = arrayOfFeatures[i].replace(/[\[\]\*]/g,"");
			featureTitle = (arrayOfFeatures[i].match(/[^ \f\n\r\t\v]+ /)).toString(); 
			theTitle = new RegExp (featureTitle);
			removedTitle = arrayOfFeatures[i].replace(theTitle,"");
			firstQualifier = (arrayOfFeatures[i].match(/\/[^\f\n\r]+/)).toString();
			result+='&gt;' + filterFastaTitle(featureTitle) + filterFastaTitle(firstQualifier) + "\n";
			translation = arrayOfFeatures[i].match(/\/translation="[^"]+"/);
			translation = translation.toString();
			translation = translation.replace(/\/translation/,"");
                        translation = translation.replace(/^FT\s+/gm,"");
			translation = removeNonProtein(translation);
			translation = addReturns(translation);
			result+=translation;
			translationFound = true;
			result+='\n\n';
		}
	}
	if (translationFound == false)	{
		result+='No translations were found.\n';
	}
	return result;
}
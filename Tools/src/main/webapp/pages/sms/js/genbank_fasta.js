//Written by Paul Stothard, University of Alberta, Canada

function genbankFasta (theDocument) {
    var maxInput = 200000;
	if (testScript() == false) {
		return false;
	}
	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (verifyGenBank (theDocument.forms[0].elements[0].value) == false) || (checkTextLength(theDocument.forms[0].elements[0].value, maxInput) == false))	{
		return false;
	}
	return genbankToFasta(theDocument.forms[0].elements[0].value);
}

function genbankToFasta (genBankFile)	{
	var result = "";
	genBankFile = "_" + genBankFile + "_";
	var recordArray = genBankFile.split(/LOCUS\s\s\s[^\f\n\r]*/m);
	for (var i=1; i < recordArray.length; i++) {	
		var mainArray = recordArray[i].split(/DEFINITION|ACCESSION|ORIGIN[^\f\n\r]*/);
		var title = filterFastaTitle(mainArray[1].replace(/[\f\n\r\t]+$/g, "")) + "<br/>";
		var dnaSequenceArray = mainArray[3].split (/\/{2}/);
		if (dnaSequenceArray.length == 1) {
			alert ("The entire GenBank file may not have been processed.");
		}
		var dnaSequence = removeNonDna(dnaSequenceArray[0]);		
		result +="&gt;" + title + addReturns(dnaSequence) + "<br/><br/>";
	}
	return result;
}


function groupDna (theDocument) {	
	var result = "";
	var newDna = "";
	var title = "";
	var maxInput = 100000;
	var adjustedStart;

	if (testScript() == false) {
		return false;
	}

	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (checkSequenceLength(theDocument.forms[0].elements[0].value, maxInput) == false))	{
		return false;
	}

	adjustedStart = (theDocument.forms[0].elements[8].value).replace(/[^\d\-]/g,"");
	if ((checkFormElement (theDocument.forms[0].elements[8]) == false) || (verifyMaxDigits (adjustedStart, 9999999999) == false))	{
		return false;
	}
	adjustedStart = parseInt(adjustedStart) - 1;
	var arrayOfFasta = getArrayOfFasta (theDocument.forms[0].elements[0].value);

	for (var i = 0; i < arrayOfFasta.length; i++)	{
		newDna = getSequenceFromFasta (arrayOfFasta[i]);
		title = getTitleFromFasta (arrayOfFasta[i]);

		newDna = removeNonDna(newDna);

		result += getInfoFromTitleAndSequence(title, newDna);

		result += writeGroupNumDnaSetStart(newDna, "", theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value, theDocument.forms[0].elements[5].options[theDocument.forms[0].elements[5].selectedIndex].value, 0, newDna.length, theDocument.forms[0].elements[6].options[theDocument.forms[0].elements[6].selectedIndex].value, theDocument.forms[0].elements[7].options[theDocument.forms[0].elements[7].selectedIndex].value, adjustedStart);
		result +='<br/></br>';
	}
	return result;
}

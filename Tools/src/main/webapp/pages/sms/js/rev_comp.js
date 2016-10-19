//Written by Paul Stothard, University of Alberta, Canada

function revComp (theDocument) {
	var result = "";
	var newDna = "";
	var title = "";
	var maxInput = 100000;
	if (testScript() == false) {
		return false;
	}
	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (checkSequenceLength(theDocument.forms[0].elements[0].value, maxInput) == false))	{
		return false;
	}
	var arrayOfFasta = getArrayOfFasta (theDocument.forms[0].elements[0].value);
	for (var i = 0; i < arrayOfFasta.length; i++)	{
		newDna = getSequenceFromFasta (arrayOfFasta[i]);
		title = getTitleFromFasta (arrayOfFasta[i]);
		newDna = removeNonDna(newDna);
		if (theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value == "reverse-complement")	{
	        result += ">" + title + " reverse complement<br/>";
			newDna = reverse(complement(newDna));
		}
		else if (theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value == "reverse")	{
			result += ">" + title + " reverse<br/>";
			newDna = reverse(newDna);
		}
		else if (theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value == "complement")	{
			result += ">" + title + " complement<br/>";
			newDna = complement(newDna);
		}
		result += addReturns(newDna) + '<br/><br/>';
	}
	return result;
}


//Written by Paul Stothard, University of Alberta, Canada

function groupProtein (theDocument) {	
	var result = "";
	var newProtein = "";
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
		newProtein = getSequenceFromFasta (arrayOfFasta[i]);
		title = getTitleFromFasta (arrayOfFasta[i]);

		newProtein = removeNonProtein(newProtein);

		result += getInfoFromTitleAndSequence(title, newProtein);

		result += writeGroupNumProtein(newProtein, "", theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value, theDocument.forms[0].elements[5].options[theDocument.forms[0].elements[5].selectedIndex].value, 0, newProtein.length, theDocument.forms[0].elements[6].options[theDocument.forms[0].elements[6].selectedIndex].value);
		result += '<br/><br/>';
	}
	return result;
}

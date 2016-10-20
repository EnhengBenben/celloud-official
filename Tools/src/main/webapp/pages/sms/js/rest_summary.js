//Written by Paul Stothard, University of Alberta, Canada

function restSummary (theDocument) {
	var result = "";
	var newDna = "";
	var title = "";
	var maxInput = 100000;
	var restrictionSites = getRestrictionSiteString("standard");

	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (checkSequenceLength(theDocument.forms[0].elements[0].value, maxInput) == false))	{
		return false;
	}

	itemsToCheck = restrictionSites.split(/,/);
	if (checkRestPatterns(itemsToCheck) == false)	{
		return false;
	}

	//openPre();		
	result += "<span class=\"one\">" + "cuts once" + "</span><br /><br/>";
	result += "<span class=\"two\">" + "cuts twice" + "</span><br /><br/>";
	result += "<br/>";
	var arrayOfFasta = getArrayOfFasta (theDocument.forms[0].elements[0].value);

	for (var i = 0; i < arrayOfFasta.length; i++)	{
		newDna = getSequenceFromFasta (arrayOfFasta[i]);
		title = getTitleFromFasta(arrayOfFasta[i]);

		newDna = removeNonDna(newDna);

		result += getInfoFromTitleAndSequenceAndTopology(title, newDna, theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value);

		result += writeRestrictionSites (newDna, itemsToCheck, theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value);
		
		result += '<br /><br/><br /><br/>';
	}
	return result;
}

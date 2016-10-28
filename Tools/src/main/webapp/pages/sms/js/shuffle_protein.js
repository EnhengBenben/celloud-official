//Written by Paul Stothard, University of Alberta, Canada

function shuffleProtein (theDocument) {	
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

		newProtein = removeNonProteinAllowDegen(newProtein);

		result += getFastaTitleFromTitleAndSequence(title, newProtein);

		result += writeShuffledSequence(newProtein);
		
		result += '<br/><br/>';
	}
	return result;
}	

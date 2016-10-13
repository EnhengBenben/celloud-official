//Written by Paul Stothard, University of Alberta, Canada

function shuffleDna (theDocument) {	
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

		result += getFastaTitleFromTitleAndSequence(title, newDna);

		result += writeShuffledSequence(newDna);
		
		result += '<br/><br/>';
	}
	return result;
}	
	



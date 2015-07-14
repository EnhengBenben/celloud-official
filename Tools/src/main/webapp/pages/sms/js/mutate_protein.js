//Written by Paul Stothard, University of Alberta, Canada

function mutateProtein (theDocument) {	
	var result = "";
	var newProtein = "";
	var title = "";
	var maxInput = 100000;
	var maxDigitsInput = 10000;
	if (testScript() == false) {
		return false;
	}
	var enteredNumber = (theDocument.forms[0].elements[4].value).replace(/[^\d]/g,"");

	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (checkSequenceLength(theDocument.forms[0].elements[0].value, maxInput) == false) || (verifyMaxDigits (enteredNumber, maxDigitsInput) == false))	{
		return false;
	}
	var arrayOfFasta = getArrayOfFasta (theDocument.forms[0].elements[0].value);

	for (var i = 0; i < arrayOfFasta.length; i++)	{
		newProtein = getSequenceFromFasta (arrayOfFasta[i]);
		title = getTitleFromFasta (arrayOfFasta[i]);

		newProtein = removeNonProteinAllowDegen(newProtein);

		result += getFastaTitleFromTitleAndSequence(title, newProtein);
		result += writeMutatedSequence(newProtein, ["A","C","D","E","F","G","H","I","K","L","M","N","P","Q","R","S","T","V","W","Y"], enteredNumber, theDocument.forms[0].elements[5].options[theDocument.forms[0].elements[5].selectedIndex].value, newProtein.length - 1);
		
		result += '<br/><br/>';
	}
	return result;
}	
	



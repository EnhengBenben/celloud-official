//Written by Paul Stothard, University of Alberta, Canada

function mutateDna (theDocument) {	
	var result="";
	var newDna = "";
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
		newDna = getSequenceFromFasta (arrayOfFasta[i]);
		title = getTitleFromFasta (arrayOfFasta[i]);
		newDna = removeNonDna(newDna);
		result += getFastaTitleFromTitleAndSequence(title, newDna);
		result += writeMutatedSequence(newDna, ["g", "a", "c", "t"], enteredNumber, theDocument.forms[0].elements[5].options[theDocument.forms[0].elements[5].selectedIndex].value, newDna.length - theDocument.forms[0].elements[6].options[theDocument.forms[0].elements[6].selectedIndex].value - 1);
		result += '<br/><br/>';
	}
	return result;
}	
	



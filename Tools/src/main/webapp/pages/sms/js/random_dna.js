//Written by Paul Stothard, University of Alberta, Canada

function randomDna (theDocument) {
	var result = "";
	var maxInput = 10000;

	if (testScript() == false) {
		return false;
	}
	
	var enteredNumber = (theDocument.forms[0].elements[0].value).replace(/[^\d]/g,"");
	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (verifyMaxDigits (enteredNumber, maxInput) == false))	{
		return false;
	}

	var seqNum = parseInt(theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value);
	for (var i=1; i <= seqNum; i++) {
		result += '&gt;' + 'random sequence ' + i + ' consisting of ' + enteredNumber + ' bases.<br/>';
		result += writeRandomSequence(["g", "a", "c", "t"], enteredNumber);
		result += "<br/>";
	}
	return result;
}

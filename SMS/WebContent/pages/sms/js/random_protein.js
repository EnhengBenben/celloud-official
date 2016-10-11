//Written by Paul Stothard, University of Alberta, Canada

function randomProtein (theDocument) {
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
		result += '&gt;' + 'random sequence ' + i + ' consisting of ' + enteredNumber + ' residues.<br/>';
		result += writeRandomSequence(["A","C","D","E","F","G","H","I","K","L","M","N","P","Q","R","S","T","V","W","Y"], enteredNumber);
		result += "<br/>";
	}
	return result;
}

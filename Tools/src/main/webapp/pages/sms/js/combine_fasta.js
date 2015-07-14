//Written by Paul Stothard, University of Alberta, Canada

function combineFasta (theDocument) {
	var result = "";
	var maxInput = 500000;
	var sequences = new Array();
	if (testScript() == false) {
		return false;
	}
	if ((checkFormElement (theDocument.getElementById("sequence")) == false) || (checkTextLength(theDocument.getElementById("sequence").value, maxInput) == false))	{
		return false;
	}
	var arrayOfFasta = getArrayOfFasta (theDocument.getElementById("sequence").value);
	for (var i = 0; i < arrayOfFasta.length; i++)	{
		sequences.push(removeNonLetters(getSequenceFromFasta (arrayOfFasta[i])));
	}
	var sequence = sequences.join("");
	if (sequences.length == 1) {
		result = "&gt;results for " + sequence.length + " residue sequence made from " + sequences.length + " records, starting \"" + sequence.substring(0,10) + "\"<br/>";
	}
	else if (sequences.length > 1) {
		result = "&gt;results for " + sequence.length + " residue sequence made from " + sequences.length + " records, starting \"" + sequence.substring(0,10) + "\"<br/>";
	}
	else {
		result = "<div class=\"info\">No sequence records were read</div><br/>";
	}
	result+=addReturns(sequence) + "<br/><br/>";
	return result;
}	


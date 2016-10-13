//Written by Paul Stothard, University of Alberta, Canada

function splitFasta (theDocument) {	
	var result = "";
	var maxInput = 500000;
	if (testScript() == false) {
		return false;
	}
	var newLength = (theDocument.forms[0].elements[1].value).replace(/[^\d]/g,"");
	var overlap = (theDocument.forms[0].elements[2].value).replace(/[^\d]/g,"");
	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (checkTextLength(theDocument.forms[0].elements[0].value, maxInput) == false) || (verifyMaxDigits (newLength, maxInput) == false) || (verifyMaxDigits (overlap, maxInput) == false))	{
		return false;
	}
	newLength = parseInt(newLength);
	overlap = parseInt(overlap);
	var arrayOfFasta = getArrayOfFasta (theDocument.forms[0].elements[0].value);
	for (var i = 0; i < arrayOfFasta.length; i++)	{
		var sequence = getSequenceFromFasta (arrayOfFasta[i]);
		sequence = removeNonLetters(sequence);
		var title = getTitleFromFasta (arrayOfFasta[i]);
		var length = sequence.length;
		var seqCount = 1;
		for (var j = 0; j < length; j = j + newLength) {
    			//if using overlap adjust j
        	    	if (j > overlap) {
        			j = j - overlap;
    			}
    			var subseq = sequence.substring(j, j + newLength);
    			var subseq_length = subseq.length;
			var start = j + 1;
    			var end = start + subseq_length - 1;
			result+='>fragment_' + seqCount + ';' + title + '_start=' + start + ';end=' + end + ';length=' + subseq_length + ';source_length=' + length + '<br/>' + addReturns(subseq) + '<br/><br/>';
			seqCount++;
		}
	}
	return result;
}	


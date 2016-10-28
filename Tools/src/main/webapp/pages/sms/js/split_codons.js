//Written by Paul Stothard, University of Alberta, Canada

function splitCodons (theDocument) {	
	var result = "";
	var maxInput = 500000;
	if (testScript() == false) {
		return false;
	}
	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (checkTextLength(theDocument.forms[0].elements[0].value, maxInput) == false))	{
		return false;
	}
	var arrayOfFasta = getArrayOfFasta (theDocument.forms[0].elements[0].value);
	for (var i = 0; i < arrayOfFasta.length; i++)	{
		var sequence = getSequenceFromFasta (arrayOfFasta[i]);
		sequence = removeFormatting(sequence);
		var title = getTitleFromFasta (arrayOfFasta[i]);
		
		if (sequence.length % 3 != 0) {
			alert("Sequence '" + title + "' ends in a partial codon that will be removed.")
		}
		var length = sequence.length;
		var seqCount = 1;		
	
		var position1 = getBasesBasedOnCodonPosition(sequence, 1);
		result += '>' + title + ';codon_positon_1_bases;length=' + position1.length + ';source_length=' + length + '<br/>' + addReturns(position1) + '<br/><br/>';
		var position2 = getBasesBasedOnCodonPosition(sequence, 2);
		result += '>' + title + ';codon_positon_2_bases;length=' + position2.length + ';source_length=' + length + '<br/>' + addReturns(position2) + '<br/><br/>';
		var position3 = getBasesBasedOnCodonPosition(sequence, 3);
		result += '>' + title + ';codon_positon_3_bases;length=' + position3.length + ';source_length=' + length + '<br/>' + addReturns(position3) + '<br/><br/>';
		seqCount++;
	}
	return result;
}	

function getBasesBasedOnCodonPosition (sequence, position) {
	var re;
	if (position == 1) {
		re = "((.)..)";
	}
	else if (position == 2) {
		re = "(.(.).)";
	}
	else if (position == 3) {
		re = "(..(.))";		
	}
	//remove partial codon from the end
	var partial_codon_length = sequence.length % 3;
	sequence = sequence.replace(new RegExp(".{" + partial_codon_length + "}$"), "");
	return sequence.replace(new RegExp(re, "g"), 
		function (str, p1, p2, offset, s) {
			return p2;
		}
	);
}
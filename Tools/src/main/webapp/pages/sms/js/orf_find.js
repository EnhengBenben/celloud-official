//Written by Paul Stothard, University of Alberta, Canada

function orfFind (theDocument) {	
	var result = "";
	var newDna = "";
	var title = "";
	var maxInput = 100000;
	if (testScript() == false) {
		return false;
	}
	var geneticCode = getGeneticCodeString(theDocument.forms[0].elements[8].options[theDocument.forms[0].elements[8].selectedIndex].value);
	geneticCode = geneticCode.split(/,/);
	var enteredNumber = (theDocument.forms[0].elements[7].value).replace(/[^\d]/g,"");
	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (checkFormElement (theDocument.forms[0].elements[7]) == false) || (verifyDigits(enteredNumber) == false) || (checkSequenceLength(theDocument.forms[0].elements[0].value, maxInput) == false))	{
		return false;
	}
	if (checkGeneticCode(geneticCode) == false)	{
		return false;
	}
	newDna = getSequenceFromFasta (theDocument.forms[0].elements[0].value);
	title = getTitleFromFasta (theDocument.forms[0].elements[0].value);
	verifyDna (newDna);
	newDna = removeNonDna(newDna);
	result += getInfoFromTitleAndSequence(title, newDna);
	if (theDocument.forms[0].elements[5].options[theDocument.forms[0].elements[5].selectedIndex].value == 'all') {
		//rf 1
		result += writeOrfs(newDna, geneticCode, theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value, 0, theDocument.forms[0].elements[6].options[theDocument.forms[0].elements[6].selectedIndex].value, enteredNumber);
		//rf 2
		result += writeOrfs(newDna, geneticCode, theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value, 1, theDocument.forms[0].elements[6].options[theDocument.forms[0].elements[6].selectedIndex].value, enteredNumber);
		//rf 3
		result += writeOrfs(newDna, geneticCode, theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value, 2, theDocument.forms[0].elements[6].options[theDocument.forms[0].elements[6].selectedIndex].value, enteredNumber);
	}
	else {
		result += writeOrfs(newDna, geneticCode, theDocument.forms[0].elements[4].options[theDocument.forms[0].elements[4].selectedIndex].value, theDocument.forms[0].elements[5].options[theDocument.forms[0].elements[5].selectedIndex].value, theDocument.forms[0].elements[6].options[theDocument.forms[0].elements[6].selectedIndex].value, enteredNumber);
	}
	return result;
}

function writeOrfs (dnaSequence, geneticCode, startCodons, startPos, strand, theLength)	{
	var result = "";
	var i = 0;
	var k = 0;
	var codon = "";
	var foundStart = false;
	var geneticCodeMatchExp = getGeneticCodeMatchExp (geneticCode);
	var geneticCodeMatchResult = getGeneticCodeMatchResult (geneticCode);
	var proteinLength = 0;
	var foundStop = false;

	var geneticCodeMatchExpStop;
	for (var j = 0; j < geneticCodeMatchExp.length; j++)	{
		if (geneticCodeMatchResult[j] == "*")	{
			geneticCodeMatchExpStop = geneticCodeMatchExp[j];
			break;
		}
	}

	var startRe = new RegExp (startCodons, "i");
	var sequenceToTranslate;

	startPos = parseInt(startPos);
	var rf = startPos + 1;
	theLength = parseInt(theLength);

	if (strand == "reverse")	{
		dnaSequence = reverse(complement(dnaSequence));
	}

	while (i <= dnaSequence.length - 3)	{
		for (var i = startPos; i <= dnaSequence.length - 3; i = i + 3)	{
			codon = dnaSequence.substring(i,(i+3));
			if ((startCodons != "any") && (foundStart == false) && (codon.search(startRe) == -1))	{
				break;
			}
			foundStart = true;
			if (codon.search(geneticCodeMatchExpStop) != -1) {
				foundStop = true;
			}
			proteinLength++;
			if ((foundStop) && (proteinLength < theLength))	{
				break;
			}
			if (((foundStop) && (proteinLength >= theLength)) || ((i >= dnaSequence.length - 5) && (proteinLength >= theLength)))	{
				sequenceToTranslate = dnaSequence.substring((startPos),i+3);			
				result += '&gt;ORF number ' + (k+1) + ' in reading frame ' + rf + ' on the ' + strand + ' strand extends from base ' + (startPos + 1) + ' to base ' + (i + 3) + '.<br/>';
				result += addReturns(sequenceToTranslate) + '<br/><br/>';
				result += '&gt;Translation of ORF number ' + (k+1) + ' in reading frame ' + rf + ' on the ' + strand + ' strand.<br/>';				
				sequenceToTranslate = sequenceToTranslate.replace(/(...)/g, 
                    			function (str, p1, offset, s) {
                      				return " " + p1 + " ";
                   			}
                		);
				for (var m = 0; m < geneticCodeMatchExp.length; m++)	{
					sequenceToTranslate = sequenceToTranslate.replace(geneticCodeMatchExp[m], geneticCodeMatchResult[m]);
				}
				sequenceToTranslate = sequenceToTranslate.replace(/\S{3}/g, "X");
				sequenceToTranslate = sequenceToTranslate.replace(/\s/g, "");
				sequenceToTranslate = sequenceToTranslate.replace(/[a-z]/g, "");
				result += addReturns(sequenceToTranslate) + '<br/><br/>';	
				k = k + 1;
				break;
			}
		}
		startPos = i + 3;
		i = startPos;
		foundStart = false;
	  	foundStop = false;
		proteinLength = 0;
	}
	if (k == 0)	{
		result += 'No ORFs were found in reading frame ' + rf + '.\n\n';
	}
	return result;
}

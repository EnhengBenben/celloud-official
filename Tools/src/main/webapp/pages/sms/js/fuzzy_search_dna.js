
function fuzzySearchDna (theDocument) {	
	var result = "";
	var targetSequence = "";
	var targetTitle = "";
	var querySequence = "";
	var queryTitle = "";
	var maxTarget = 2000;
	var maxQuery = 30;
	if (testScript() == false) {
		return false;
	}
	if ((checkFormElement (theDocument.forms[0].elements[0]) == false) || (checkSequenceLength(theDocument.forms[0].elements[0].value, maxTarget) == false) || (checkFormElement (theDocument.forms[0].elements[1]) == false) || (checkSequenceLength(theDocument.forms[0].elements[1].value, maxQuery) == false))	{
		return false;
	}
	var MATCH_SCORE = parseInt(theDocument.forms[0].elements[5].options[theDocument.forms[0].elements[5].selectedIndex].value);
	var MISMATCH_SCORE = parseInt(theDocument.forms[0].elements[6].options[theDocument.forms[0].elements[6].selectedIndex].value);
	var GAP_PENALTY = parseInt(theDocument.forms[0].elements[7].options[theDocument.forms[0].elements[7].selectedIndex].value);
	var HITS = parseInt(theDocument.forms[0].elements[8].options[theDocument.forms[0].elements[8].selectedIndex].value);
	targetSequence = getSequenceFromFasta(theDocument.forms[0].elements[0].value);
	targetSequence = removeNonDna(targetSequence);
	targetTitle = getTitleFromFasta(theDocument.forms[0].elements[0].value);
	querySequence = getSequenceFromFasta(theDocument.forms[0].elements[1].value);
	querySequence = removeNonDna(querySequence);
	queryTitle = "query";
	result += getFuzzySearchTitle(targetTitle, targetSequence, queryTitle, querySequence);
	if (targetSequence.search(/./) != -1)	{
		targetSequence = targetSequence.match(/./g);
	}
	if (querySequence.search(/./) != -1)	{
		querySequence = querySequence.match(/./g);
	}
	if (targetSequence.length == 0) {
		alert("The sequence contains no DNA bases.");
		return false;
	}
	if (querySequence.length == 0) {
		alert("The query sequence contains no DNA bases.");
		return false;
	}
	result += _fuzzySearchDna (queryTitle, querySequence, targetTitle, targetSequence, MATCH_SCORE, MISMATCH_SCORE, GAP_PENALTY, HITS);
	return result;
}	

function _fuzzySearchDna (queryTitle, querySequence, targetTitle, targetSequence, matchScore, mismatchScore, gapPenalty, hits)	{
	var result ="";
	var matrix = new Identity();
	matrix.setMatch(matchScore);
	matrix.setMismatch(mismatchScore);
	var scoreSet = new ScoreSet();
	scoreSet.setScoreSetParam(matrix, gapPenalty, hits);
	var fuzzySearch = new FuzzySearch();
	fuzzySearch.initializeMatrix(querySequence, targetSequence, scoreSet);
	fuzzySearch.search();
	var hits = fuzzySearch.getHits();
	if (hits.length > 0) {
		for (var i = 0; i < hits.length; i++) {
			result += ">" + queryTitle + " from " + hits[i].startM + " to " + hits[i].endM + "<br/>";
			result += hits[i].sequenceM + "<br/>";
			result += ">" + targetTitle + " from " + hits[i].startN + " to " + hits[i].endN + "<br/>";
			result += hits[i].sequenceN + "<br/>";			
			result += "Score: " + hits[i].score + "<br/><br/>";			
		}
	}
	else {
		result += "No hits were obtained.<br/><br/>";
	}
	return result;
}
//------------------------------------ ScoreSet class
//ScoreSet getScore
function getScore (r1, r2) {
	return this.scoringMatrix.scoringMatrix_getScore(r1, r2);	
}

//ScoreSet setScoreSetParam
function setScoreSetParam (scoringMatrix, gapPenalty, hits) {
	this.scoringMatrix = scoringMatrix;
	this.gap = gapPenalty;
	this.hits = hits;
}

//ScoreSet class
function ScoreSet () {
	this.scoringMatrix;
	this.gap;
}

//create and throw away a prototype object
new ScoreSet();

//define object methods
ScoreSet.prototype.getScore = getScore;
ScoreSet.prototype.setScoreSetParam = setScoreSetParam;

//------------------------------------


//------------------------------------ ScoringMatrix abstract class
//ScoringMatrix getScore method
function scoringMatrix_getScore(r1, r2) {
	r1 = r1.toLowerCase();
	r2 = r2.toLowerCase();
	if (r1 == r2) {
		return this.match;
	}
	else {
		return this.mismatch;
	}
}

//ScoringMatrix class
function ScoringMatrix() {
	this.mismatch;
	this.match;
}

//create and throw away a prototype object
new ScoringMatrix();

//define object methods
ScoringMatrix.prototype.scoringMatrix_getScore = scoringMatrix_getScore;

//------------------------------------ Identity class extends ScoringMatrix Class
//Identity class setMismatch method
function setMismatch(mismatchScore) {
	this.mismatch = mismatchScore;
}

//Identity class setMatch method
function setMatch(matchScore) {
	this.match = matchScore;
}

//Identity class
function Identity () {
}

Identity.prototype = new ScoringMatrix();
Identity.prototype.setMismatch = setMismatch;
Identity.prototype.setMatch = setMatch;

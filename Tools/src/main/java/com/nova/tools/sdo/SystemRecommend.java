package com.nova.tools.sdo;

public class SystemRecommend {

    private String queryId;
    private String subjectId;
    private String identity;
    private String subjectAnnotation;
    private String score;

    public String getQueryId() {
	return queryId;
    }

    public void setQueryId(String queryId) {
	this.queryId = queryId;
    }

    public String getSubjectId() {
	return subjectId;
    }

    public void setSubjectId(String subjectId) {
	this.subjectId = subjectId;
    }

    public String getIdentity() {
	return identity;
    }

    public void setIdentity(String identity) {
	this.identity = identity;
    }

    public String getSubjectAnnotation() {
	return subjectAnnotation;
    }

    public void setSubjectAnnotation(String subjectAnnotation) {
	this.subjectAnnotation = subjectAnnotation;
    }

    public String getScore() {
	return score;
    }

    public void setScore(String score) {
	this.score = score;
    }
}

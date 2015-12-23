package com.celloud.model;

public class ReportWithBLOBs extends Report {
    private String printContext;

    private String context;

    public String getPrintContext() {
        return printContext;
    }

    public void setPrintContext(String printContext) {
        this.printContext = printContext == null ? null : printContext.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }
}
package com.it.utils.pdfUtil;


public class LogTableData {

    private String  tuFileName;

    private String logFileName;

    private String isEffective;

    private String reason;

    private String description;

    public static LogTableData build(){
        return new LogTableData();
    }

    public String getTuFileName() {
        return tuFileName;
    }

    public LogTableData setTuFileName(String tuFileName) {
        this.tuFileName = tuFileName;
        return this;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public LogTableData setLogFileName(String logFileName) {
        this.logFileName = logFileName;
        return this;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public LogTableData setIsEffective(String isEffective) {
        this.isEffective = isEffective;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public LogTableData setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LogTableData setDescription(String description) {
        this.description = description;
        return this;
    }
}

package com.it.utils.pdfUtil;

import java.util.List;

public class BugData {

    private String bugId;

    private List<String> baseDataList;

    private List<LogTableData> resultTable;

    public static BugData build(){
        return new BugData();
    }

    public String getBugId() {
        return bugId;
    }

    public BugData setBugId(String bugId) {
        this.bugId = bugId;
        return this;
    }

    public List<String> getBaseDataList() {
        return baseDataList;
    }

    public BugData setBaseDataList(List<String> baseDataList) {
        this.baseDataList = baseDataList;
        return this;
    }

    public List<LogTableData> getResultTable() {
        return resultTable;
    }

    public BugData setResultTable(List<LogTableData> resultTable) {
        this.resultTable = resultTable;
        return this;
    }
}

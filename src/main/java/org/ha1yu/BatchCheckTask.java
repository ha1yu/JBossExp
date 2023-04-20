package org.ha1yu;

import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

public class BatchCheckTask extends Task<Integer> {
    private int index;
    private String url;
    private int vulID;
    private SimpleStringProperty result = new SimpleStringProperty();

    protected Integer call() throws Exception {
        String checkResult = UtilMethod.vulCheck(this.vulID, this.url);
        this.setResult(checkResult);
        return null;
    }

    public BatchCheckTask(int index, String url) {
        this.url = url;
        this.index = index;
    }

    public BatchCheckTask(int index, int vulID, String url) {
        this.vulID = vulID;
        this.url = url;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResult() {
        return this.result.get();
    }

    public SimpleStringProperty resultProperty() {
        return this.result;
    }

    public void setResult(String result) {
        this.result.set(result);
    }
}


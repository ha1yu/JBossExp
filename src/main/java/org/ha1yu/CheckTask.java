package org.ha1yu;

import org.ha1yu.UtilMethod;
import javafx.concurrent.Task;

public class CheckTask extends Task<Void> {
    private String targetUrl;
    private int index;
    private String result;

    public CheckTask(int index, String targetUrl) {
        this.targetUrl = targetUrl;
        this.index = index;
    }

    protected Void call() throws Exception {
        String result = UtilMethod.vulCheck(this.index, this.targetUrl);
        this.updateMessage(result);
        this.setResult(result);
        return null;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

package org.ha1yu;

import org.ha1yu.UtilMethod;
import javafx.concurrent.Task;

public class UploadTask extends Task<Void> {
    private int index;
    private String url;
    private String filePath;
    private String fileContent;

    public UploadTask(int index, String url, String filePath, String fileContent) {
        this.index = index;
        this.url = url;
        this.filePath = filePath;
        this.fileContent = fileContent;
    }

    protected Void call() throws Exception {
        String result = UtilMethod.uploadFile(this.index, this.url, this.filePath, this.fileContent);
        this.updateMessage(result);
        return null;
    }
}

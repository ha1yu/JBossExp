package org.ha1yu;

import org.ha1yu.UtilMethod;
import javafx.concurrent.Task;

public class CommandTask extends Task<Void> {
    private int index;
    private String url;
    private String cmd;
    private boolean flag;

    public CommandTask(int index, String url, String cmd, boolean flag) {
        this.index = index;
        this.url = url;
        this.cmd = cmd;
        this.flag = flag;
    }

    protected Void call() throws Exception {
        String result = UtilMethod.commandExploit(this.index, this.url, this.cmd, this.flag);
        this.updateMessage(result);
        return null;
    }
}
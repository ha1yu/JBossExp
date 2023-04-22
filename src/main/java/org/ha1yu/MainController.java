package org.ha1yu;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainController {
    @FXML
    private TextField url_textField;
    @FXML
    private ChoiceBox bug_choiceBox;
    private int currentVulIndex;
    @FXML
    private Tab target_Tab;
    @FXML
    private Tab check_Tab;
    @FXML
    private Tab command_Tab;
    @FXML
    private Tab upload_Tab;
    @FXML
    private Tab config_Tab;
    @FXML
    private Tab about_Tab;
    private boolean commandFlag;
    @FXML
    private TabPane tabPane;
    @FXML
    private CheckBox target_UA_checkBox;
    @FXML
    private Button target_Start_Btn;
    @FXML
    private Button target_clear_Btn;
    @FXML
    private TextArea target_Result_textArea;
    @FXML
    private TextField batch_path;
    @FXML
    private Button batch_importBtn;
    @FXML
    private Button batch_startBtn;
    @FXML
    private Button batch_stopBtn;
    @FXML
    private TableView<BatchCheckTask> batch_tableView;
    @FXML
    private TableColumn<BatchCheckTask, Integer> batch_tableColumnIndex;
    @FXML
    private TableColumn<BatchCheckTask, String> batch_tableColumnUrl;
    @FXML
    private TableColumn<BatchCheckTask, String> batch_tableColumnResult;
    @FXML
    private Button batch_saveBtn;
    @FXML
    private Button batch_clearBtn;
    private List<String> urlList;
    private int batch_job_count;
    @FXML
    private TextField command_TextField;
    private String command;
    @FXML
    private Button command_startBtn;
    @FXML
    private TextArea command_result_textArea;
    private boolean uploadJarFlag;
    private String upload_webRootPath;
    @FXML
    private TextField upload_filePath_textField;
    private String upload_filePath;
    @FXML
    private TextField upload_fileName_textField;
    private String upload_fileName;
    @FXML
    private Button upload_webRootPath_btn;
    @FXML
    private Button upload_givenPath_btn;
    @FXML
    private TextArea upload_content_textArea;
    private String upload_content;
    @FXML
    private TextArea upload_result_textArea;
    private String upload_result;
    private Config config;
    @FXML
    private CheckBox config_isProxy_checkBox;
    @FXML
    private ComboBox config_proxyType_comboBox;
    private boolean config_isProxy = false;
    @FXML
    private TextField config_proxyIP_textFiled;
    private String config_proxyIP;
    @FXML
    private TextField config_proxyPort_textFiled;
    private int config_proxyPort;
    @FXML
    private CheckBox config_isAuth_checkBox;
    private boolean config_isAuth = false;
    @FXML
    private TextField config_authUserName_textFiled;
    private String config_authUserName;
    @FXML
    private TextField config_authPwd_textFiled;
    private String config_authPwd;
    @FXML
    private CheckBox config_isUA_checkBox;
    private boolean config_isUA;
    @FXML
    private TextField config_ua_TextFiled;
    private String config_ua;
    @FXML
    private Button config_saveBtn;
    @FXML
    private TextArea about_textArea;
    private String url_mainTab;

    public MainController() {
    }

    @FXML
    public void initialize() {
        this.config = Config.getInstance("config.properties");
        this.initComponents();
        this.initConfig();
    }

    public void initConfig() {
        this.config_authUserName_textFiled.setText(this.config.getStringValue("username"));
        this.config_authPwd_textFiled.setText(this.config.getStringValue("password"));
        this.config_isAuth = this.config.getBooleanValue("isAuth");
        this.config_isProxy = this.config.getBooleanValue("isProxy");
        this.config_isUA = this.config.getBooleanValue("isUA");
        if (this.config_isAuth) {
            this.config_authUserName_textFiled.setEditable(true);
            this.config_authPwd_textFiled.setEditable(true);
            this.config_isAuth_checkBox.setSelected(true);
        }

        if (this.config_isProxy) {
            this.config_proxyIP_textFiled.setEditable(true);
            this.config_proxyPort_textFiled.setEditable(true);
            this.config_isProxy_checkBox.setSelected(true);
        }

        if (this.config_isUA) {
            this.config_isUA_checkBox.setSelected(true);
            this.config_ua_TextFiled.setEditable(true);
        }

        this.config_ua_TextFiled.setText(this.config.getStringValue("UA") != null ? this.config.getStringValue("UA") : "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
        this.config_proxyIP_textFiled.setText(this.config.getStringValue("proxyIP"));
        this.config_proxyPort_textFiled.setText(this.config.getStringValue("proxyPort"));
        this.config_isAuth_checkBox.setOnAction((event) -> {
            this.config_isAuth = false;
            this.config_authUserName_textFiled.setEditable(false);
            this.config_authPwd_textFiled.setEditable(false);
            if (this.config_isAuth_checkBox.isSelected()) {
                this.config_isAuth = true;
                this.config_authUserName_textFiled.setEditable(true);
                this.config_authPwd_textFiled.setEditable(true);
            }

        });
        this.config_isProxy_checkBox.setOnAction((event) -> {
            this.config_isProxy = false;
            this.config_proxyIP_textFiled.setEditable(false);
            this.config_proxyPort_textFiled.setEditable(false);
            if (this.config_isProxy_checkBox.isSelected()) {
                this.config_isProxy = true;
                this.config_proxyIP_textFiled.setEditable(true);
                this.config_proxyPort_textFiled.setEditable(true);
            }

        });
        this.config_isUA_checkBox.setOnAction((event) -> {
            this.config_isUA = false;
            this.config_ua_TextFiled.setEditable(false);
            if (this.config_isUA_checkBox.isSelected()) {
                this.config_isUA = true;
                this.config_ua_TextFiled.setEditable(true);
            }

        });
        this.config_saveBtn.setOnAction((event) -> {
            try {
                if (this.config_isAuth) {
                    this.config_authUserName = this.config_authUserName_textFiled.getText();
                    this.config_authPwd = this.config_authPwd_textFiled.getText();
                    this.config.setProperty("username", this.config_authUserName);
                    this.config.setProperty("password", this.config_authPwd);
                }

                if (this.config_isProxy) {
                    this.config_proxyPort = Integer.parseInt(this.config_proxyPort_textFiled.getText());
                    this.config_proxyIP = this.config_proxyIP_textFiled.getText();
                    this.config.setProperty("proxyIP", this.config_proxyIP);
                    this.config.setProperty("proxyPort", this.config_proxyPort);
                }

                if (this.config_isUA) {
                    this.config_ua = this.config_ua_TextFiled.getText();
                    this.config.setProperty("UA", this.config_ua.replace(",", "\\,"));
                }

                this.config.setProperty("isAuth", this.config_isAuth);
                this.config.setProperty("isUA", this.config_isUA);
                this.config.setProperty("isProxy", this.config_isProxy);
                this.showAlert(AlertType.INFORMATION, "提示", "保存成功！");
            } catch (Exception var3) {
                this.showAlert(AlertType.WARNING, "警告", "保存失败，请检查填写是否正确!");
            }

        });
        this.about_textArea.setText("\t\t\t\t\t\t\t\t\t\t\tJBoss漏洞验证工具\n\n\n\n1.支持单一URL漏洞验证和单一漏洞批量扫描。单一漏洞批量验证时，双击URL，URL填写到地址栏中，右击复制URL到剪贴板。\n\n2.支持CVE-2006-5750,CVE-2007-1036,CVE-2010-0738,CVE-2010-1871,CVE-2013-4810,CVE-2015-7501,CVE-2017-7504,CVE-2017-12149等漏洞的验证。\n\n3.支持CVE-2010-1871,CVE-2013-4810,CVE-2015-7501,CVE-2017-7504,CVE-2017-12149等漏洞的命令执行。\n\n");
    }

    public void initComponents() {
        this.batch_importBtn.setDisable(false);
        this.batch_startBtn.setDisable(true);
        this.batch_stopBtn.setDisable(true);
        this.batch_saveBtn.setDisable(true);
        this.batch_clearBtn.setDisable(true);
        this.target_Result_textArea.setWrapText(true);
        this.target_Result_textArea.setEditable(false);
        this.command_result_textArea.setWrapText(true);
        this.command_result_textArea.setEditable(false);
        this.upload_content_textArea.setWrapText(true);
        this.upload_result_textArea.setWrapText(true);
        this.upload_result_textArea.setEditable(false);
        this.upload_content_textArea.setText("<%out.println(\"hello world!!!\");%>");
        this.uploadJarFlag = false;
        this.bug_choiceBox.setItems(FXCollections.observableArrayList(Utils.bugs_mainTab));
        this.bug_choiceBox.getSelectionModel().selectFirst();
        this.currentVulIndex = 0;
        this.command_startBtn.setDisable(true);
        this.bug_choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            this.currentVulIndex = newValue.intValue();
            this.uploadJarFlag = false;
            this.upload_result_textArea.clear();
            this.command_startBtn.setDisable(false);
            if (this.currentVulIndex < 3) {
                this.command_startBtn.setDisable(true);
            }

        });
        this.target_Start_Btn.setOnAction((event) -> {
            if (this.url_textField.getText().trim().equals("")) {
                this.showAlert(AlertType.WARNING, "警告", "检测网址不能为空!");
            } else {
                String targetUrl = this.url_textField.getText().trim();
                int bugSize = this.bug_choiceBox.getItems().size();
                this.target_Result_textArea.appendText("=======================================================================================\n");
                this.target_Result_textArea.appendText(targetUrl + "的检测结果如下：\n");

                for (int i = 0; i < bugSize; ++i) {
                    CheckTask checkTask = new CheckTask(i, targetUrl);
                    checkTask.messageProperty().addListener((observable, oldValue, newValue) -> {
                        this.target_Result_textArea.appendText(newValue + "\n");
                    });
                    (new Thread(checkTask)).start();
                }

            }
        });
        this.target_clear_Btn.setOnAction((event) -> {
            this.target_Result_textArea.clear();
        });
        this.batch_tableColumnIndex.setCellValueFactory(new PropertyValueFactory("index"));
        this.batch_tableColumnUrl.setCellValueFactory(new PropertyValueFactory("url"));
        this.batch_tableColumnResult.setCellValueFactory(new PropertyValueFactory("result"));
        this.batch_tableView.setRowFactory((param) -> {
            TableRow<BatchCheckTask> row = new TableRow();
            row.setOnMouseClicked((event) -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    this.url_textField.setText(((BatchCheckTask) row.getItem()).getUrl());
                    this.uploadJarFlag = false;
                } else if (!row.isEmpty() && event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 1) {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(((BatchCheckTask) row.getItem()).getUrl());
                    clipboard.setContent(content);
                }

            });
            return row;
        });
        this.config_isAuth = false;
        this.config_authUserName_textFiled.setEditable(false);
        this.config_authPwd_textFiled.setEditable(false);
        this.config_isProxy = false;
        this.config_proxyIP_textFiled.setEditable(false);
        this.config_proxyPort_textFiled.setEditable(false);
        String[] config_proxyTypes = new String[]{"HTTP"};
        this.config_proxyType_comboBox.setItems(FXCollections.observableArrayList(config_proxyTypes));
        this.config_isUA = false;
        this.config_ua_TextFiled.setEditable(false);
    }

    @FXML
    private void batchBtnimportAction() {
        String cwd = System.getProperty("user.dir");
        File file = new File(cwd);
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(file);
        chooser.setTitle("选择");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt文本文件", "*.txt"));
        File chosenDir = chooser.showOpenDialog((Window) null);
        if (chosenDir != null) {
            String filePath = chosenDir.getAbsolutePath();
            this.batch_path.setText(filePath);
            this.urlList = MainMethod.readFile(filePath);
            ObservableList<BatchCheckTask> list = FXCollections.observableArrayList();
            if (this.urlList.size() > 0) {
                this.batch_startBtn.setDisable(false);
                this.batch_stopBtn.setDisable(true);
                this.batch_saveBtn.setDisable(true);
                this.batch_clearBtn.setDisable(true);
            }

            for (int i = 0; i < this.urlList.size(); ++i) {
                BatchCheckTask batchCheckTask = new BatchCheckTask(i + 1, (String) this.urlList.get(i));
                list.add(batchCheckTask);
            }

            this.batch_tableView.setItems(list);
        }

    }

    @FXML
    private void batchBtnStartAction(ActionEvent event) {
        this.batch_job_count = 0;
        int tableSize = this.batch_tableView.getItems().size();
        if (this.urlList.size() > 0) {
            this.batch_tableView.getItems().clear();
            this.batch_clearBtn.setDisable(true);
            this.batch_saveBtn.setDisable(true);
            this.batch_importBtn.setDisable(true);
            this.batch_startBtn.setDisable(true);
            this.batch_stopBtn.setDisable(false);
            Executor exec = Executors.newFixedThreadPool(10, (r) -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });

            for (int i = 0; i < this.urlList.size(); ++i) {
                String url = this.urlList.get(i);
                // Check one
                //BatchCheckTask batchCheckTask = new BatchCheckTask(i + 1, this.currentVulIndex, url);

                // check all vuls
                List<BatchCheckTask> que = new ArrayList<>();
                BatchCheckTask batchCheckTask1 = new BatchCheckTask(i + 1, 0, url);
                BatchCheckTask batchCheckTask2 = new BatchCheckTask(i + 1, 1, url);
                BatchCheckTask batchCheckTask3 = new BatchCheckTask(i + 1, 2, url);
                BatchCheckTask batchCheckTask4 = new BatchCheckTask(i + 1, 3, url);
                BatchCheckTask batchCheckTask5 = new BatchCheckTask(i + 1, 4, url);
                BatchCheckTask batchCheckTask6 = new BatchCheckTask(i + 1, 5, url);
                BatchCheckTask batchCheckTask7 = new BatchCheckTask(i + 1, 6, url);
                BatchCheckTask batchCheckTask8 = new BatchCheckTask(i + 1, 7, url);
                que.add(batchCheckTask1);
                que.add(batchCheckTask2);
                que.add(batchCheckTask3);
                que.add(batchCheckTask4);
                que.add(batchCheckTask5);
                que.add(batchCheckTask6);
                que.add(batchCheckTask7);
                que.add(batchCheckTask8);
                for (int y = 0; y < que.size(); ++y) {
                    BatchCheckTask batchCheckTask = que.get(y);
                    exec.execute(batchCheckTask);
                    this.batch_tableView.getItems().addAll(batchCheckTask);
                    batchCheckTask.setOnSucceeded((event1) -> {
                        ++this.batch_job_count;
                        if (this.batch_job_count == this.urlList.size()) {
                            this.jobDone();
                        }

                    });
                    batchCheckTask.setOnCancelled((event12) -> {
                        ++this.batch_job_count;
                        if (this.batch_job_count == this.urlList.size()) {
                            this.jobDone();
                        }

                    });
                    batchCheckTask.setOnFailed((event13) -> {
                        ++this.batch_job_count;
                        if (this.batch_job_count == this.urlList.size()) {
                            this.jobDone();
                        }

                    });
                }
            }
        }

    }

    @FXML
    private void batchBtnStopAction(ActionEvent event) {
        this.batch_clearBtn.setDisable(false);
        this.batch_saveBtn.setDisable(false);
        this.batch_importBtn.setDisable(false);
        this.batch_startBtn.setDisable(false);
        this.batch_stopBtn.setDisable(true);
        this.batch_tableView.getItems().forEach((batchCheckTask) -> {
            batchCheckTask.cancel();
        });
    }

    @FXML
    private void batchBtnSaveAction(ActionEvent event) {
        if (this.batch_tableView.getItems().size() == 0) {
            this.showAlert(AlertType.INFORMATION, "提示", "没有可以保存的数据");
        }

        String batch_filePath = this.batch_path.getText().trim();
        StringBuffer saveNameSB = new StringBuffer();
        if (!batch_filePath.equals("")) {
            File f = new File(batch_filePath);
            saveNameSB.append(f.getName().substring(0, f.getName().lastIndexOf(".")));
        }

        saveNameSB.append(UtilMethod.getFDate());
        saveNameSB.append(".txt");
        String path = saveNameSB.toString();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < this.batch_tableView.getItems().size(); ++i) {
            BatchCheckTask batchCheckTask = (BatchCheckTask) this.batch_tableView.getItems().get(i);
            sb.append(batchCheckTask.getIndex());
            sb.append("\t\t");
            sb.append(batchCheckTask.getUrl());
            sb.append("\t\t");
            if (batchCheckTask.getResult() == null) {
                sb.append("暂未检测");
            } else {
                sb.append(batchCheckTask.getResult());
            }

            sb.append("\r\n");
        }

        UtilMethod.writeResult(path, sb.toString(), true);
    }

    @FXML
    private void batchBtnClearAction(ActionEvent event) {
        this.batch_tableView.getItems().clear();
        this.batch_clearBtn.setDisable(false);
        this.batch_saveBtn.setDisable(true);
        this.batch_importBtn.setDisable(false);
        this.batch_startBtn.setDisable(false);
        this.batch_stopBtn.setDisable(true);
    }

    @FXML
    private void commandBtnStart() {
//        System.out.println("执行命令->执行按钮");
        String url = this.url_textField.getText().trim();
        String cmd = this.command_TextField.getText().trim();
//        System.out.println("url:" + url);
//        System.out.println("cmd:" + cmd);
//        System.out.println("this.currentVulIndex:" + this.currentVulIndex);
//        System.out.println("this.uploadJarFlag:" + this.uploadJarFlag);
        CommandTask commandTask = new CommandTask(this.currentVulIndex, url, cmd, this.uploadJarFlag);
        this.uploadJarFlag = true;
        commandTask.messageProperty().addListener((observable, oldValue, newValue) -> {
            this.command_result_textArea.appendText("【" + cmd + "】");
            this.command_result_textArea.appendText("\n");
            this.command_result_textArea.appendText(newValue + "\n");
            this.command_result_textArea.appendText("-------------------------------------\n");
        });
        (new Thread(commandTask)).start();
    }

    @FXML
    private void commandBtnClear() {
        this.command_result_textArea.clear();
    }

    @FXML
    private void uploadBtnAction() {
        this.upload_result_textArea.clear();
        String url = this.url_textField.getText().trim();
        String filePath = this.upload_filePath_textField.getText().trim();
        if (filePath.equals("")) {
            this.upload_result_textArea.setText("上传路径不能为空");
        } else {
            String fileContent = this.upload_content_textArea.getText().trim();
            UploadTask uploadTask = new UploadTask(this.currentVulIndex, url, filePath, fileContent);
            uploadTask.messageProperty().addListener((observable, oldValue, newValue) -> {
                this.upload_result_textArea.setText(newValue + "\n");
            });
            (new Thread(uploadTask)).start();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText((String) null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void jobDone() {
        this.showAlert(AlertType.INFORMATION, "提示", "任务完成");
        this.batch_clearBtn.setDisable(false);
        this.batch_saveBtn.setDisable(false);
        this.batch_importBtn.setDisable(false);
        this.batch_startBtn.setDisable(false);
        this.batch_stopBtn.setDisable(true);
        if (this.batch_tableView.getItems().size() == 0) {
            this.batch_saveBtn.setDisable(true);
        }

    }
}

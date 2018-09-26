package com.d1m.Entity;


import java.util.List;
import java.util.Map;

/**
 * @Auther: Leo.hu
 * @Date: 2018/8/22 15:50
 * @Description:
 */
public class CaseDetailsEntity {

    private String id ;

    private String isRun ;

    private String weChatId ;

    private String dbUrl ;

    private String dbUserName ;

    private String dbPassword ;

    private String sshName ;

    private String sshPassword ;

    private String sshHost ;

    private String sshPort ;

    private String host ;

    private String path ;

    private String requestMethod ;

    private String head ;

    private String requestParam ;

    private List<String> initSql ;

    private String relatedApi ;

    private String postProcessor ;

    private String expectResponse ;

    private List<String> querySql ;

    private String expectSqlResult ;

    private List<String> clearSql ;

    private String[] headDetails ;

    private Map<String, String> requestParamMap ;

    private String Comment ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsRun() {
        return isRun;
    }

    public void setIsRun(String isRun) {
        this.isRun = isRun;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getSshName() {
        return sshName;
    }

    public void setSshName(String sshName) {
        this.sshName = sshName;
    }

    public String getSshPassword() {
        return sshPassword;
    }

    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
    }

    public String getSshHost() {
        return sshHost;
    }

    public void setSshHost(String sshHost) {
        this.sshHost = sshHost;
    }

    public String getSshPort() {
        return sshPort;
    }

    public void setSshPort(String sshPort) {
        this.sshPort = sshPort;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getRelatedApi() {
        return relatedApi;
    }

    public void setRelatedApi(String relatedApi) {
        this.relatedApi = relatedApi;
    }

    public String getPostProcessor() {
        return postProcessor;
    }

    public void setPostProcessor(String postProcessor) {
        this.postProcessor = postProcessor;
    }

    public String getExpectResponse() {
        return expectResponse;
    }

    public void setExpectResponse(String expectResponse) {
        this.expectResponse = expectResponse;
    }

    public String getExpectSqlResult() {
        return expectSqlResult;
    }

    public void setExpectSqlResult(String expectSqlResult) {
        this.expectSqlResult = expectSqlResult;
    }

    public List<String> getInitSql() {
        return initSql;
    }

    public void setInitSql(List<String> initSql) {
        this.initSql = initSql;
    }

    public List<String> getQuerySql() {
        return querySql;
    }

    public void setQuerySql(List<String> querySql) {
        this.querySql = querySql;
    }

    public List<String> getClearSql() {
        return clearSql;
    }

    public void setClearSql(List<String> clearSql) {
        this.clearSql = clearSql;
    }

    public String[] getHeadDetails() {
        return headDetails;
    }

    public void setHeadDetails(String[] headDetails) {
        this.headDetails = headDetails;
    }

    public Map<String, String> getRequestParamMap() {
        return requestParamMap;
    }

    public void setRequestParamMap(Map<String, String> requestParamMap) {
        this.requestParamMap = requestParamMap;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}

package com.d1m.Entity;

import com.d1m.utils.CellMapping;

/**
 * @Auther: Leo.hu
 * @Date: 2018/8/22 15:49
 * @Description:
 */
public class CaseEntity {

    @CellMapping(cellName = "ID")
    public String id ;

    @CellMapping(cellName = "IsRun")
    public String isRun ;

    @CellMapping(cellName = "WeChatId")
    public String weChatId ;

    @CellMapping(cellName = "Path")
    public String path ;

    @CellMapping(cellName = "RequestMethod")
    public String requestMethod ;

    @CellMapping(cellName = "Head")
    public String head ;

    @CellMapping(cellName = "RequestParam")
    public String requestParam ;

    @CellMapping(cellName = "InitSql")
    public String initSql ;

    @CellMapping(cellName = "RelatedApi")
    public String relatedApi ;

    @CellMapping(cellName = "PostProcessor")
    public String postProcessor ;

    @CellMapping(cellName = "ExpectResponse")
    public String expectResponse ;

    @CellMapping(cellName = "QuerySql")
    public String querySql ;

    @CellMapping(cellName = "ExpectSqlResult")
    public String expectSqlResult ;

    @CellMapping(cellName = "ClearSql")
    public String clearSql ;

    @CellMapping(cellName = "Comment")
    public String comment ;

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

    public String getInitSql() {
        return initSql;
    }

    public void setInitSql(String initSql) {
        this.initSql = initSql;
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

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public String getExpectSqlResult() {
        return expectSqlResult;
    }

    public void setExpectSqlResult(String expectSqlResult) {
        this.expectSqlResult = expectSqlResult;
    }

    public String getClearSql() {
        return clearSql;
    }

    public void setClearSql(String clearSql) {
        this.clearSql = clearSql;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

package com.d1m.excute;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.d1m.Entity.CaseDetailsEntity;
import com.d1m.manage.CaseDetailsEntityManager;
import com.d1m.utils.*;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.sql.*;
import java.util.*;

import static com.d1m.utils.Constants.CASEENTITY;
import static com.d1m.utils.Constants.CONFIGENTITY;
import static com.d1m.utils.Constants.DATAENTITY;

/**
 * @Auther: Leo.hu
 * @Date: 2018/8/23 15:04
 * @Description:
 */
public class ExcuteAllCase {

    List<CaseDetailsEntity> caseDetailsEntityList;

    CaseDetailsEntityManager caseDetailsEntityManager;

    CaseDetailsEntity initCaseDetailsEntity;

    /**
     * 存储请求参数信息
     */
    Map<String, String> dataMap;

    /**
     * 接口返回结果
     */
    String result;

    /**
     * 接口后置处理器接收的数据
     */
    static Map<String, Map<String, String>> processorMap;

    /**
     * 数据库连接地址、用户名、密码、驱动
     */
    private static String url;

    private static String userName;

    private static String password;

    private static String driver;

    private  static String[] dbHost ;

    /**
     * QuerySql返回结果
     */
    private static Map resultMap;

    private SoftAssert softAssert;

    @Test(dataProvider = "createTestData")
    public void excute(String Id, CaseDetailsEntity caseDetailsEntity) {
        Reporter.log("当前执行的CaseId：" + Id);
        dataMap = new HashMap<>();
        processorMap = new HashMap<>();
        softAssert = new SoftAssert();
        excuteSingleAPI(caseDetailsEntity);
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] createTestData() {
        caseDetailsEntityManager = new CaseDetailsEntityManager();
        caseDetailsEntityList = caseDetailsEntityManager.createCaseDeatilsEntity(CASEENTITY, CONFIGENTITY, DATAENTITY);
        Object dateMap[][] = new Object[caseDetailsEntityList.size()][2];
        for (int i = 0; i < caseDetailsEntityList.size(); i++) {
            dateMap[i][0] = caseDetailsEntityList.get(i).getId();
            dateMap[i][1] = caseDetailsEntityList.get(i);
        }
        return dateMap;
    }

    /**
     * 功能描述: 执行单个的接口请求
     *
     * @param: * @param caseDetailsEntity
     * @auther: Leo.hu
     * @date: 2018/9/5 16:20
     */

    public void excuteSingleAPI(CaseDetailsEntity caseDetailsEntity) {

        /**
         *  判断关联的API，执行当前case之前需要先执行关联的API
         */
        String[] relatedAPI = null;
        if (!StringTools.isNullOrEmpty(caseDetailsEntity.getRelatedApi())) {
            Reporter.log("当前接口关联的API为： " + caseDetailsEntity.getRelatedApi());
            //  通过“.”来分隔关联的API
            relatedAPI = caseDetailsEntity.getRelatedApi().split("\\&");
            for (int i = 0; i < relatedAPI.length; i++) {
                //  执行前置接口，并获 取返回值
                initCaseDetailsEntity = CaseDetailsEntityManager.getCaseDetailsEntity(caseDetailsEntityList, relatedAPI[i]);
                excuteSingleAPI(initCaseDetailsEntity);
            }
        }

        /**
         *  判断初始化sql是否为空，不为空的话则先执行初始化sql
         *  初始化sql中可包含引用参数，引用的参数为关联接口中后置处理器返回的值
         *  参数引用统一使用“${}”,查询sql和清除sql相同
         */
        if (caseDetailsEntity.getInitSql().size() > 0) {
            String initSqlParam = null;
            //替换sql中可能存在的引用参数
            for (int i = 0; i < caseDetailsEntity.getInitSql().size(); i++) {
                List initSqlList = RegExp.getKeywords(caseDetailsEntity.getInitSql().get(i));
                for (int j = 0; j < initSqlList.size(); j++) {
                    //  参数替换
                    for (int k = 0; k < relatedAPI.length; k++) {
                        if (processorMap.get(relatedAPI[k]).containsKey(initSqlList.get(j))) {
                            initSqlParam = processorMap.get(relatedAPI[k]).get(initSqlList.get(j));
                            break;
                        }
                    }
                    caseDetailsEntity.getInitSql().set(i, caseDetailsEntity.getInitSql().get(i).replace("${" + initSqlList.get(i).toString() + "}", initSqlParam));
                }
            }
            //执行sql操作
            for (int i = 0; i < caseDetailsEntity.getInitSql().size(); i++) {
                try {
                    excuteUpdateSql(caseDetailsEntity, caseDetailsEntity.getInitSql().get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         *  设置head 头的信息，并替换里面的引用的参数
         *  head 头中可能包含引用的参数，用“${}”来引用，其参数来源于依赖接口的返回值
         */
        String[] head = caseDetailsEntity.getHead().split("\\&");
        String headParam = null;
        for (int i = 0; i < head.length; i++) {
            //  如果大于0，则表示head中有引入参数，需要替换该参数
            List headParamList = RegExp.getKeywords(head[i]);
            for (int j = 0; j < headParamList.size(); j++) {
                //  将其中的参数替换为真正的值，并更新caseDetailsEntity
                for (int k = 0; k < relatedAPI.length; k++) {
                    if (processorMap.get(relatedAPI[k]).containsKey(headParamList.get(j))) {
                        headParam = processorMap.get(relatedAPI[k]).get(headParamList.get(j));
                        break;
                    }
                }
                head[i] = head[i].replace("${" + headParamList.get(j).toString() + "}", headParam);
            }
        }
        caseDetailsEntity.setHeadDetails(head);
        Reporter.log("请求的接口头信息为： " + caseDetailsEntity.getHead());

        /**
         *  RequestParam 可能存在引用参数，需要替换
         */
        if (!StringTools.isNullOrEmpty(caseDetailsEntity.getRequestParam())) {
            List requestParamList = RegExp.getKeywords(caseDetailsEntity.getRequestParam());
            String requestParam = null;
            for (int i = 0; i < requestParamList.size(); i++) {
                //  参数替换
                for (int k = 0; k < relatedAPI.length; k++) {
                    if (processorMap.get(relatedAPI[k]).containsKey(requestParamList.get(i))) {
                        requestParam = processorMap.get(relatedAPI[k]).get(requestParamList.get(i));
                        break;
                    }
                }
                caseDetailsEntity.setRequestParam(caseDetailsEntity.getRequestParam().replace("${" + requestParamList.get(i).toString() + "}", requestParam));
            }
        }

        /**
         *  判断具体的请求方式及请求参数的类型
         *  执行具体的请求操作
         */
        if (caseDetailsEntity.getRequestMethod().toUpperCase().trim().equals("GET")) {
            result = HttpClientUtil.sendHttpGet(caseDetailsEntity);
        }
        if (caseDetailsEntity.getRequestMethod().toUpperCase().trim().equals("POST")) {
            //  判断head中的请求参数类型，使用不同的请求方式
            if (caseDetailsEntity.getHead().toUpperCase().contains("APPLICATION/JSON")) {
                result = HttpClientUtil.sendHttpPostByJson(caseDetailsEntity);
            }
            if (caseDetailsEntity.getHead().toUpperCase().contains("FORM")) {
                //  处理form-data形式的参数转为map或list
                String[] formdata = caseDetailsEntity.getRequestParam().replaceAll("\n", "").split("\\&");
                for (int i = 0; i < formdata.length; i++) {
                    String[] dataDetails = formdata[i].split("\\:");
                    dataMap.put(dataDetails[0], dataDetails[1]);
                }
                caseDetailsEntity.setRequestParamMap(dataMap);
                result = HttpClientUtil.sendHttpPostByFormData(caseDetailsEntity);
            }
        }
        Reporter.log("接口返回的Response为： " + JSONObject.parse(result));

        /**
         *  判断后置处理器，不为空则执行完当前Case之后存储该处理器中所需要保存的数据
         *  将所有的数据存入processorMap中，供关联接口调用
         */
        if (!StringTools.isNullOrEmpty(caseDetailsEntity.getPostProcessor())) {
            String[] processor = caseDetailsEntity.getPostProcessor().split("\\&");
            Map<String, String> processorDeatilsMap = new HashMap<>();
            for (int i = 0; i < processor.length; i++) {
                //  将所有的后置参数全部存储到processorMap中，方便后期调用,只取第一个值
                List<Object> list = (List<Object>) JSONPath.eval(JSONObject.parse(result), "$.." + processor[i]);
                if (list.size() > 0) {
                    processorDeatilsMap.put(processor[i], list.get(0).toString());
                }
            }
            processorMap.put(caseDetailsEntity.getId(), processorDeatilsMap);
        }

        /**
         *  判断是否有查询的sql，并且ExpectSqlResult不为空的话，则执行该操作
         *  sql中可能存在引用参数，需先替换
         *  判断sql返回值与预期结果是否相同
         */
        if (caseDetailsEntity.getQuerySql().size() > 0 && !StringTools.isNullOrEmpty(caseDetailsEntity.getExpectSqlResult())) {
            //替换sql中可能存在的引用参数
            String queryParam = null;
            for (int i = 0; i < caseDetailsEntity.getQuerySql().size(); i++) {
                List querySqlList = RegExp.getKeywords(caseDetailsEntity.getQuerySql().get(i));
                for (int j = 0; j < querySqlList.size(); j++) {
                    //  参数替换
                    for (int k = 0; k < relatedAPI.length; k++) {
                        if (processorMap.get(relatedAPI[k]).containsKey(querySqlList.get(j))) {
                            queryParam = processorMap.get(relatedAPI[k]).get(querySqlList.get(j));
                            break;
                        }
                    }
                    caseDetailsEntity.getQuerySql().set(i, caseDetailsEntity.getQuerySql().get(i).replace("${" + querySqlList.get(i).toString() + "}", queryParam));
                }
            }
            //  获取sql返回值
            resultMap = new LinkedHashMap();
            for (int i = 0; i < caseDetailsEntity.getQuerySql().size(); i++) {
                resultMap.putAll(excuteQuerySql(caseDetailsEntity, caseDetailsEntity.getQuerySql().get(i)));
            }
            //将预期的sql返回值转换成Map存储
            Map<String, String> expectSqlResultMap = new HashMap();
            //  去除所有的换行符，并以&分隔
            String[] expectSqlResult = caseDetailsEntity.getExpectSqlResult().replaceAll("\n", "").split("\\&");
            for (int i = 0; i < expectSqlResult.length; i++) {
                String[] expectSqlResultDetails = expectSqlResult[i].split("\\:");
                expectSqlResultMap.put(expectSqlResultDetails[0], expectSqlResultDetails[1]);
            }
            //  比对sql返回值与预期结果是否相同
            Iterator<Map.Entry<String, String>> it = expectSqlResultMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                //  如果存在相同的Key，则比较值是否相等，若expectSqlResultMap中的key不存在于resultMap中，则失败
                if (resultMap.containsKey(entry.getKey())) {
                    Reporter.log("Key:" + entry.getKey() + "预期的Value为：" + entry.getValue() + "，实际结果为：" + resultMap.get(entry.getKey()));
                    softAssert.assertEquals(resultMap.get(entry.getKey()), entry.getValue());
                } else {
                    Reporter.log("预期的Key：" + entry.getKey() + "未查询到");
                    softAssert.assertFalse(false);
                }
            }
        }

        /**
         *  返回值断言，判断接口返回值与预期结果是否相同，
         *  支持多值校验，中间以&符分隔
         */
        if (!StringTools.isNullOrEmpty(caseDetailsEntity.getExpectResponse())) {
            String[] expectResponse = caseDetailsEntity.getExpectResponse().replaceAll("\n", "").split("\\&");
            Map<String, String> expectResponseMap = new LinkedHashMap();
            for (int i = 0; i < expectResponse.length; i++) {
                String[] expectResponseDeatails = expectResponse[i].split("\\:");
                expectResponseMap.put(expectResponseDeatails[0], expectResponseDeatails[1]);
            }
            //  判断期望的值与实际返回的值是否相等
            Iterator<Map.Entry<String, String>> it = expectResponseMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                List<Object> list = (List<Object>) JSONPath.eval(JSONObject.parse(result), "$.." + entry.getKey());
                if (list.size() > 0) {
                    Reporter.log("Key：" + entry.getKey() + ",预期的结果为：" + entry.getValue() + ",实际结果为：" + list.get(0).toString());
                    softAssert.assertEquals(list.get(0).toString(), entry.getValue());
                } else {
                    softAssert.assertFalse(false, "根据Key：" + entry.getKey() + "未获取到实际的值");
                }
            }
        }

        /**
         *  执行清除的sql
         */
        if (caseDetailsEntity.getClearSql().size() > 0) {
            //替换sql中可能存在的引用参数
            String clearParam = null;
            for (int i = 0; i < caseDetailsEntity.getClearSql().size(); i++) {
                List clearSqlList = RegExp.getKeywords(caseDetailsEntity.getClearSql().get(i));
                for (int j = 0; j < clearSqlList.size(); j++) {
                    //  参数替换
                    for (int k = 0; k < relatedAPI.length; k++) {
                        if (processorMap.get(relatedAPI[k]).containsKey(clearSqlList.get(j))) {
                            clearParam = processorMap.get(relatedAPI[k]).get(clearSqlList.get(j));
                            break;
                        }
                    }
                    caseDetailsEntity.getClearSql().set(i, caseDetailsEntity.getClearSql().get(i).replace("${" + clearSqlList.get(i).toString() + "}", clearParam));
                }
            }

            for (int i = 0; i < caseDetailsEntity.getClearSql().size(); i++) {
                try {
                    excuteUpdateSql(caseDetailsEntity, caseDetailsEntity.getClearSql().get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 功能描述: 执行具体的sql
     *
     * @param: * @param caseDetailsEntity、sql
     * @return: Map
     * @auther: Leo.hu
     * @date: 2018/9/5 16:20
     */

    public Map excuteQuerySql(CaseDetailsEntity caseDetailsEntity, String sql) {
        //  执行具体的sql语句
        jdbcConfig(caseDetailsEntity);
        Reporter.log("当前执行的SQL为： " + sql);
        try {
            return JdbcHelper.query(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行update，增删改操作
     *
     * @param caseDetailsEntity 执行的具体的case信息
     * @param sql               需要执行的sql语句
     */
    private void excuteUpdateSql(CaseDetailsEntity caseDetailsEntity, String sql) {
        jdbcConfig(caseDetailsEntity);
        Reporter.log("当前执行的SQL为： " + sql);
        try {
            JdbcHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 装载驱动
     */
    private void jdbcConfig(CaseDetailsEntity caseDetailsEntity) {
        dbHost = caseDetailsEntity.getDbUrl().split("\\/");
        url = "jdbc:mysql://localhost:3307/" + dbHost[1];
        userName = caseDetailsEntity.getDbUserName();
        password = caseDetailsEntity.getDbPassword();
        driver = "com.mysql.jdbc.Driver";
        connectSession(caseDetailsEntity);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 建立数据库连接
     *
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, userName, password);
        return conn;
    }

    /**
     * 释放连接
     *
     * @param conn
     */
    private static void freeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放statement
     *
     * @param statement
     */
    private static void freeStatement(Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放resultset
     *
     * @param rs
     */
    private static void freeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     *
     * @param conn
     * @param statement
     * @param rs
     */
    public static void free(Connection conn, Statement statement, ResultSet rs) {
        if (rs != null) {
            freeResultSet(rs);
        }
        if (statement != null) {
            freeStatement(statement);
        }
        if (conn != null) {
            freeConnection(conn);
        }
    }

    /**
     *  创建SSH 连接
     * @param caseDetailsEntity
     */
    public static void connectSession(CaseDetailsEntity caseDetailsEntity) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(caseDetailsEntity.getSshName(), caseDetailsEntity.getSshHost(), Integer.valueOf(caseDetailsEntity.getSshPort()));
            session.setPassword(caseDetailsEntity.getSshPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            //需要根据url获取host
            int assinged_port=session.setPortForwardingL(3307, dbHost[0], 3306);
            System.out.println("localhost:"+assinged_port+" -> "+dbHost[0]+":"+ 3306);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

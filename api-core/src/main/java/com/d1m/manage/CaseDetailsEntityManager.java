package com.d1m.manage;

import com.d1m.Entity.CaseDetailsEntity;
import com.d1m.Entity.CaseEntity;
import com.d1m.Entity.ConfigEntity;
import com.d1m.Entity.DataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Leo.hu
 * @Date: 2018/8/22 16:33
 * @Description:
 */
public class CaseDetailsEntityManager {

    private List<CaseEntity> caseEntityList;

    private List<ConfigEntity> configEntityList;

    private List<DataEntity> dataEntityList;

    CaseEntity caseEntity;

    ConfigEntity configEntity;

    List<String> initSqlDataEntityList, querySqlDataEntityList, clearSqlDataEntityList;

    CaseDetailsEntity caseDetailsEntity;

    /**
     * 创建CaseDetailsEntity对象
     * @param caseEntityPath
     * @param configEntityPath
     * @param dataEntityPath
     * @return
     */
    public List<CaseDetailsEntity> createCaseDeatilsEntity(String caseEntityPath, String configEntityPath, String dataEntityPath) {
        List<CaseDetailsEntity> caseDetailsEntityList = new ArrayList<>();

        //获取CaseEntity表中的数据
        caseEntityList = CaseEntityManager.getCaseEntityList(caseEntityPath);
        //获取ConfigEntity表中的数据
        configEntityList = ConfigEntityManager.getConfigEntityList(configEntityPath);
        //获取DataEntity表中的数据
        dataEntityList = DataEntityManager.getDataEntityList(dataEntityPath);
        //遍历caseEntity表中的数据，将关联的ID转化为实际数据
        for (int i = 0; i < caseEntityList.size(); i++) {
            //判断isrun的状态，为1时才会执行
            if (caseEntityList.get(i).getIsRun().equals("1")) {
                caseDetailsEntity = new CaseDetailsEntity();
                //获取对应的configEntity表中的数据
                configEntity = ConfigEntityManager.getConfigEntityByWeChatId(configEntityList, caseEntityList.get(i).getWeChatId());
                initSqlDataEntityList = DataEntityManager.getDataByDataId(dataEntityList, caseEntityList.get(i).getInitSql());
                querySqlDataEntityList = DataEntityManager.getDataByDataId(dataEntityList, caseEntityList.get(i).getQuerySql());
                clearSqlDataEntityList = DataEntityManager.getDataByDataId(dataEntityList, caseEntityList.get(i).getClearSql());
                caseDetailsEntity.setId(caseEntityList.get(i).getId());
                caseDetailsEntity.setIsRun(caseEntityList.get(i).getIsRun());
                caseDetailsEntity.setWeChatId(caseEntityList.get(i).getWeChatId());
                caseDetailsEntity.setDbUrl(configEntity.getUrl());
                caseDetailsEntity.setDbUserName(configEntity.getUserName());
                caseDetailsEntity.setDbPassword(configEntity.getPassword());
                caseDetailsEntity.setHost(configEntity.getHost());
                caseDetailsEntity.setPath(caseEntityList.get(i).getPath());
                caseDetailsEntity.setRequestMethod(caseEntityList.get(i).getRequestMethod());
                caseDetailsEntity.setHead(caseEntityList.get(i).getHead());
                caseDetailsEntity.setRequestParam(caseEntityList.get(i).getRequestParam());
                caseDetailsEntity.setInitSql(initSqlDataEntityList);
                caseDetailsEntity.setRelatedApi(caseEntityList.get(i).getRelatedApi());
                caseDetailsEntity.setPostProcessor(caseEntityList.get(i).getPostProcessor());
                caseDetailsEntity.setExpectResponse(caseEntityList.get(i).getExpectResponse());
                caseDetailsEntity.setQuerySql(querySqlDataEntityList);
                caseDetailsEntity.setExpectSqlResult(caseEntityList.get(i).getExpectSqlResult());
                caseDetailsEntity.setClearSql(clearSqlDataEntityList);
                caseDetailsEntityList.add(caseDetailsEntity);
            }
        }
        return caseDetailsEntityList;
    }

    /**
     *
     * 功能描述: 通过ID查找对应的接口
     *
     * @param:  * @param caseDetailsEntityList id
     * @return:
     * @auther: Leo.hu
     * @date: 2018/9/5 16:20
     */
    public static  CaseDetailsEntity getCaseDetailsEntity(List<CaseDetailsEntity> caseDetailsEntityList, String id){
        CaseDetailsEntity caseDetailsEntity = null ;
        for (int i = 0; i < caseDetailsEntityList.size(); i++) {
            if (caseDetailsEntityList.get(i).getId().equals(id)){
                caseDetailsEntity = caseDetailsEntityList.get(i);
                break;
            }
        }
        return caseDetailsEntity ;
    }
}

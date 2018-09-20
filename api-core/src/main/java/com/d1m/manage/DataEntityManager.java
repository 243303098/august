package com.d1m.manage;

import com.d1m.Entity.DataEntity;
import com.d1m.utils.FastExcel;
import com.d1m.utils.StringTools;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Leo.hu
 * @Date: 2018/8/22 16:33
 * @Description:
 */
public class DataEntityManager {

    /**
     *
     * 功能描述: 获取DataEntity的对象并存储到List中
     *
     * @param:  * @param null
     * @return:
     * @auther: Leo.hu
     * @date: 2018/8/22 16:30
     */
    public static List<DataEntity> getDataEntityList(String dataEntityPath){
        List<DataEntity> dataEntityList = null;
        InputStream is = DataEntityManager.class.getClassLoader().getResourceAsStream(dataEntityPath);
        try {
            FastExcel fastExcel = new FastExcel(is);
            dataEntityList = fastExcel.praseExcel(DataEntity.class);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
        return dataEntityList;
    }

    /**
     *
     * 功能描述: 根据dataId 获取实际的数据
     *
     * @param:  * @param dataEntityList， dataKey
     * @return:
     * @auther: Leo.hu
     * @date: 2018/8/22 16:30
     */
    public static List<String> getDataByDataId(List<DataEntity> dataEntityList, String dataId) {
        List<String> dataList = new ArrayList<>();
        if (!StringTools.isNullOrEmpty(dataId)){
            //dataId支持多个，用英文“,”隔开
            String[] dataIdArray = dataId.split("\\&");
            for (int i = 0; i < dataEntityList.size(); i++) {
                for (int j = 0; j < dataIdArray.length; j++) {
                    if (dataEntityList.get(i).getId().equals(dataIdArray[j])){
                        dataList.add(dataEntityList.get(i).getData());
                        break;
                    }
                }
            }
        }
        return dataList;
    }

}

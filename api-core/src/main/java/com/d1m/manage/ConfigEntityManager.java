package com.d1m.manage;

import com.d1m.Entity.ConfigEntity;
import com.d1m.Entity.DataEntity;
import com.d1m.utils.FastExcel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Auther: Leo.hu
 * @Date: 2018/8/22 16:33
 * @Description:
 */
public class ConfigEntityManager {

    /**
     *
     * 功能描述: 获取DataEntity的对象并存储到List中
     *
     * @param:  * @param null
     * @return:
     * @auther: Leo.hu
     * @date: 2018/8/22 16:30
     */
    public static List<ConfigEntity> getConfigEntityList(String configEntityPath){
        List<ConfigEntity> configEntityList = null;
        InputStream is = DataEntityManager.class.getClassLoader().getResourceAsStream(configEntityPath);
        try {
            FastExcel fastExcel = new FastExcel(is);
            configEntityList = fastExcel.praseExcel(ConfigEntity.class);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configEntityList;
    }

    /**
     *
     * 功能描述: 根据wechatId 获取configEntity的数据
     *
     * @param:
     * @return:
     * @auther: Leo.hu
     * @date: 2018/8/22 16:30
     */
    public static ConfigEntity getConfigEntityByWeChatId(List<ConfigEntity> configEntityList, String weChatId) {
        ConfigEntity configEntity = null;
        for (int i = 0; i < configEntityList.size(); i++) {
            //将IsUsed状态为1的并且WeChatId相同的信息加入到
            if (configEntityList.get(i).getWeChatId().equals(weChatId) && configEntityList.get(i).getIsUesd().equals("1")){
                configEntity = configEntityList.get(i);
                break;
            }
        }
        return configEntity;
    }

}

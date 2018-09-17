package com.d1m.Entity;

import com.d1m.utils.CellMapping;

/**
 * @Auther: Leo.hu
 * @Date: 2018/8/22 15:50
 * @Description:
 */
public class DataEntity {

    @CellMapping(cellName = "Id")
    public String id ;

    @CellMapping(cellName = "Data")
    public String data ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

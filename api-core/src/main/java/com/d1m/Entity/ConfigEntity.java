package com.d1m.Entity;

import com.d1m.utils.CellMapping;

/**
 * @Auther: Leo.hu
 * @Date: 2018/8/22 15:49
 * @Description:
 */
public class ConfigEntity {

    @CellMapping(cellName = "WeChatId")
    public String weChatId ;

    @CellMapping(cellName = "Url")
    public String url ;

    @CellMapping(cellName = "UserName")
    public String userName ;

    @CellMapping(cellName = "Password")
    public String password ;

    @CellMapping(cellName = "Host")
    public String host ;

    @CellMapping(cellName = "SSHName")
    public String sshName ;

    @CellMapping(cellName = "SSHPassword")
    public String sshPassword ;

    @CellMapping(cellName = "SSHHost")
    public String sshHost ;

    @CellMapping(cellName = "SSHPort")
    public String sshPort ;

    @CellMapping(cellName = "IsUsed")
    public String isUesd ;

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIsUesd() {
        return isUesd;
    }

    public void setIsUesd(String isUesd) {
        this.isUesd = isUesd;
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
}

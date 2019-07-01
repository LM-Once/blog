package com.it.device;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.hibernate.validator.constraints.Length;

/**
 * 硬件实体
 */
public class Node {

    /**
     * remark: mac地址
     * table : t_node
     * field : mac_address
     */
    //@ApiModelProperty(value = "mac地址", example = "30-9c-23-ef-38-b7")
    private String macAddress;


    /**
     * remark: ip地址
     * table : t_node
     * field : ip_address
     */
    //@ApiModelProperty(value = "ip地址", example = "172.17.172.234")
    private String ipAddress;

    /**
     * remark: agent服务端口号
     * table : t_node
     * field : port
     */
    //@ApiModelProperty(value = "PC节点服务端口号", example = "10200")
    private Integer port;

    /**
     * remark:
     * table : t_node
     * field : version
     */
    //@ApiModelProperty(value = "PC节点服务版本号", example = "v1")
    private String version;

    /**
     * remark:
     * table : t_node
     * field : version
     */
    //@ApiModelProperty(value = "用户id", example = "80213472")
    private String userName;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Node{" +
                "macAddress='" + macAddress + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", port=" + port +
                ", version='" + version + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Node node = new Node();
        node.setMacAddress("1");
        node.setIpAddress("2");
        node.setPort(222);
        node.setVersion("222");
        node.setUserName("255");

        System.out.println("1:"+node.toString());
        System.out.println("2:"+JSONObject.fromObject(node).toString());
        System.out.println("3:"+JSON.toJSONString(node));
    }
}

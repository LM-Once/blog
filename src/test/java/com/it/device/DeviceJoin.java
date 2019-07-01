package com.it.device;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class DeviceJoin {

    /**
     *开发环境
     */
    private final static  String DEVLOCATION = "http://127.0.0.1:9014";

    /**
     * 节点地址
     */
    private final static String NODEPATH = "/node/join";

    /**
     * 设备地址
     */
    private final static  String DEVICEPATH = "/device/join";
    /**
     * 节点注册
     * @param macAddress
     * @param ipAddress
     * @param port
     * @param version
     */
    public String nodeJoin (String macAddress , String ipAddress,
                          Integer port , String version, String userName){

        Node node = new Node();
        node.setMacAddress(macAddress);
        node.setIpAddress(ipAddress);
        node.setPort(port);
        node.setVersion(version);
        node.setUserName(userName);

        String result = HttpRequest.httpPostRaw(DEVLOCATION+NODEPATH ,JSONObject.fromObject(node).toString(),null,"UTF-8");
        return result;
    }

    /**
     * 设备注册
     * @param devices
     * @param macAddress
     * 说明：需在心跳节点正常状态下才能进行注册
     * @return
     */
    public String deviceJoin (List<Device> devices,String macAddress){
        DeviceJoinModel deviceJoinModel = new DeviceJoinModel();
        deviceJoinModel.setDevices(devices);
        deviceJoinModel.setMacAddress(macAddress);
        String result = HttpRequest.httpPostRaw(DEVLOCATION + DEVICEPATH ,JSON.toJSONString(deviceJoinModel),null,"UTF-8");
        return result;
    }

    public static void main(String[] args) {

        DeviceJoin deviceJoin = new DeviceJoin();

        String result = deviceJoin.nodeJoin("30-9C-23-EE-08-D3","172.17.195.217", 95272,"v12","80189527");
        /*String macAddress = "30-9C-23-EE-08-D3";
        List<Device> devices = new ArrayList<>();
        Device device1 = new Device();
        device1.setSn("WallAutoTest700011");
        device1.setDeviceName("WallAutoTestDevice");
        device1.setProductCode("70001");
        device1.setProductModel("device70001");
        device1.setConnectionStatus(2);
        devices.add(device1);

        Device device2 = new Device();
        device2.setSn("WLANRVR700021");
        device2.setDeviceName("WLANRVR");
        device2.setProductCode("70002");
        device2.setProductModel("device70002");
        device2.setConnectionStatus(2);
        devices.add(device2);

        String result = deviceJoin.deviceJoin(devices,macAddress);*/
        System.out.println("响应结果result："+result);
    }
}

package com.it.device;


import java.util.List;

/**
 * 设备注册实体
 */
public class DeviceJoinModel extends Node {

    /**
     * 设备列表
     */
    private List<Device> devices;

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}

package com.it.device;

import org.hibernate.validator.constraints.Length;

/**
 * 设备基础信息
 */
public class Device {

    /**
     * remark: 产品序列号，如：49c9cac5、JZAI7L85KZJJWKDY
     * table : t_device
     * field : sn
     */
    private String sn;

    /**
     * remark: 设备名称(可自定义)，如：OPPO R11s Plus、PACM00
     * table : t_device
     * field : device_name
     */
    private String deviceName;

    /**
     * remark: 产品代号，如：18120,18130
     * table : t_device
     * field : product_code
     */
    private String productCode;

    /**
     * remark: 产品型号，如：OPPO A73、OPPO R11 Plus
     * table : t_device
     * field : product_model
     */
    private String productModel;
    /**
     * remark: 连接状态，0为bootloader， 1为offline， 2为device， 3为recovery， 4为sideload， 5为unauthorized， 6为disconnected
     * table : t_device
     * field : connection_status
     */
    private Integer connectionStatus;


    public Device() {

    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(Integer connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }
}

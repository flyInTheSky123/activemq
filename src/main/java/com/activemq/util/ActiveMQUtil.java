package com.activemq.util;

import cn.hutool.core.util.NetUtil;

import javax.swing.*;

public class ActiveMQUtil {


    public static void main(String[] args) {
        //MQ 的端口
        checkServer();

    }

    public static void checkServer() {
//        NetUtil.isUsableLocalPort(port) 用于检测本地端口是否可用
        if (NetUtil.isUsableLocalPort(8161)) {
            JOptionPane.showMessageDialog(null, "ActiveMQ 服务器没有启动");
            System.exit(1);
        }

    }


}

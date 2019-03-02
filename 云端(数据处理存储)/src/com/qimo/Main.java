package com.qimo;

import cn.usr.UsrCloudMqttCallbackAdapter;
import cn.usr.UsrCloudMqttClientAdapter;
import cn.usr.client.UsrCloudMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;


/**
 * @Package: PACKAGE_NAME
 * @Description: 主方法，此Demo 仅仅演示SDK使用方式适用于服务端开发，未详细考虑高并发问题，请使用者自行参考。
 * @author: Rock 【shizhiyuan@usr.cn】
 * @Date: 2018-08-25 15:55
 */
public class Main extends UsrCloudMqttCallbackAdapter {


    /**
     * 声明客户端句柄
     */
    private static UsrCloudMqttClient cloudMqttClient = null;

    /**
     * 启动类
     *
     * @param args
     */
    public static void main(String[] args) throws MqttException {

        // 给客户端句柄赋值
        cloudMqttClient = new UsrCloudMqttClientAdapter();

        // 设置客户端的回调类为此类；（demo为省事不是很建议这样用）
        cloudMqttClient.setUsrCloudMqttCallback(new Main());

        // 调用客户端的连接函数
        cloudMqttClient.Connect("qingchun", "lan07715273883");

    }


    /**
     * 客户端的连接回调;
     *
     * @param returnCode
     * @param description
     * @Description 由于上面已经设置了回调函数，所以客户端与服务器的交互状态会再本回调函数中回显。
     */
    @Override
    public void onConnectAck(int returnCode, String description) {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.append(String.format("返回标识符为:%d ，返回描述为：%s", returnCode, description)));

        // 注意！！！！ 根据文档说明此处会回调两次

        switch (returnCode) {
            case 0:
                // 收到回调returnCode为0，代表客户端与服务器取得联系。但并不表示已经连接成功。
                break;
            case 1:
                // 收到回调returnCode为1，为连接失败通知。
                break;
            case 2:
                // 收到回调returnCode为2，代表客户端与服务器取得联系，并且已经连接成功此时可以做订阅或者发布处理。
                try {
                    // 订阅账号下面的全部设备的原始数据流
                    cloudMqttClient.SubscribeForUsername();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                // 收到回调returnCode为3，为已经和服务器已经断开连接，此处可以做判断进行业务逻辑。
                // 注意: SDK 源代码中设置了客户端为自动重连，所以客户端和服务器断开连接后会自动再连接，就会按上面的逻辑方式循环操作。
                break;
            default:
                break;
        }

    }

    /**
     * 客户端订阅回调
     *
     * @param messageId
     * @param clientId
     * @param topics
     * @param returnCode
     */
    @Override
    public void onSubscribeAck(int messageId, String clientId, String topics, int returnCode) {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(
                stringBuilder.append(
                        String.format("收到消息的messageID为 :%d ，客户端ID为：%s ，订阅主题为：%s ，订阅结果为 ：%s", messageId, clientId, topics, (returnCode == 0 ? "成功" : "失败"))));

    }

    /**
     * 客户端收到消息后的回调
     *
     * @param messageId
     * @param topic
     * @param data
     */
    @Override
    public void onReceiveEvent(int messageId, String topic, byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.append(String.format("收到消息的messageID为 :%d ，来自主题为：%s ，转换为16进制字符串为 %s：", messageId, topic, bytesToHexString(data))));
        String str = bytesToHexString(data);
        int val = 0;
        float num = 0;
        System.out.println(str);
        for(int i=0;i<5;i++) {
        	val = Integer.parseInt(str.substring(2*i,2*i+2));
        	num+=val*Math.pow(10,2-i);
        }
        System.out.println((long)System.currentTimeMillis() / 1000);
        
        Data myData = new Data();
        myData.insert(num,System.currentTimeMillis() / 1000);
        
        

    }

    /**
     * 字节数组转十六进制字符传
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}

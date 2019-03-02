package com.qimo;

import cn.usr.UsrCloudMqttCallbackAdapter;
import cn.usr.UsrCloudMqttClientAdapter;
import cn.usr.client.UsrCloudMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;


/**
 * @Package: PACKAGE_NAME
 * @Description: ����������Demo ������ʾSDKʹ�÷�ʽ�����ڷ���˿�����δ��ϸ���Ǹ߲������⣬��ʹ�������вο���
 * @author: Rock ��shizhiyuan@usr.cn��
 * @Date: 2018-08-25 15:55
 */
public class Main extends UsrCloudMqttCallbackAdapter {


    /**
     * �����ͻ��˾��
     */
    private static UsrCloudMqttClient cloudMqttClient = null;

    /**
     * ������
     *
     * @param args
     */
    public static void main(String[] args) throws MqttException {

        // ���ͻ��˾����ֵ
        cloudMqttClient = new UsrCloudMqttClientAdapter();

        // ���ÿͻ��˵Ļص���Ϊ���ࣻ��demoΪʡ�²��Ǻܽ��������ã�
        cloudMqttClient.setUsrCloudMqttCallback(new Main());

        // ���ÿͻ��˵����Ӻ���
        cloudMqttClient.Connect("qingchun", "lan07715273883");

    }


    /**
     * �ͻ��˵����ӻص�;
     *
     * @param returnCode
     * @param description
     * @Description ���������Ѿ������˻ص����������Կͻ�����������Ľ���״̬���ٱ��ص������л��ԡ�
     */
    @Override
    public void onConnectAck(int returnCode, String description) {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.append(String.format("���ر�ʶ��Ϊ:%d ����������Ϊ��%s", returnCode, description)));

        // ע�⣡������ �����ĵ�˵���˴���ص�����

        switch (returnCode) {
            case 0:
                // �յ��ص�returnCodeΪ0������ͻ����������ȡ����ϵ����������ʾ�Ѿ����ӳɹ���
                break;
            case 1:
                // �յ��ص�returnCodeΪ1��Ϊ����ʧ��֪ͨ��
                break;
            case 2:
                // �յ��ص�returnCodeΪ2������ͻ����������ȡ����ϵ�������Ѿ����ӳɹ���ʱ���������Ļ��߷�������
                try {
                    // �����˺������ȫ���豸��ԭʼ������
                    cloudMqttClient.SubscribeForUsername();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                // �յ��ص�returnCodeΪ3��Ϊ�Ѿ��ͷ������Ѿ��Ͽ����ӣ��˴��������жϽ���ҵ���߼���
                // ע��: SDK Դ�����������˿ͻ���Ϊ�Զ����������Կͻ��˺ͷ������Ͽ����Ӻ���Զ������ӣ��ͻᰴ������߼���ʽѭ��������
                break;
            default:
                break;
        }

    }

    /**
     * �ͻ��˶��Ļص�
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
                        String.format("�յ���Ϣ��messageIDΪ :%d ���ͻ���IDΪ��%s ����������Ϊ��%s �����Ľ��Ϊ ��%s", messageId, clientId, topics, (returnCode == 0 ? "�ɹ�" : "ʧ��"))));

    }

    /**
     * �ͻ����յ���Ϣ��Ļص�
     *
     * @param messageId
     * @param topic
     * @param data
     */
    @Override
    public void onReceiveEvent(int messageId, String topic, byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.append(String.format("�յ���Ϣ��messageIDΪ :%d ����������Ϊ��%s ��ת��Ϊ16�����ַ���Ϊ %s��", messageId, topic, bytesToHexString(data))));
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
     * �ֽ�����תʮ�������ַ���
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

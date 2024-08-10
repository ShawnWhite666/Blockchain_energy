package com.blockchainenergy.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blockchainenergy.mqtt.server.MqttParam;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @descriptions: 实现阿里云接收订阅消息的类
 */
@Component
@Slf4j
public class IoTDemoPubSubDemo implements CommandLineRunner {
    //  接收到的电表信息存储在这里 - 单例模式
    private static Map<String, MqttParam> mqttMap = new HashMap<>();

    public static Map<String, MqttParam> getMqttMap() {
        return mqttMap;
    }

    //  需要修改的数据
    public static String productKey = "hwvo1sfD0nE";
    public static String deviceName = "mqttjava";
    public static String deviceSecret = "3f091d56401d9f7a4d9c29f3be864848";
    public static String regionId = "cn-shanghai";

    // 本地publish的topic
    private static String pubTopic = "/" + productKey + "/" + deviceName + "/user/update";
    // 接收的topic
    private static String subTopic = "/" + productKey + "/" + deviceName + "/user/get";

    private static MqttClient mqttClient;

    //    开启项目就开始运行
    @Override
    public void run(String... args) {
        initAliyunIoTClient();
        // 汇报属性
        // postDeviceProperties();
        try {
            mqttClient.subscribe(subTopic); // 订阅Topic
        } catch (MqttException e) {
            log.info("error:" + e.getMessage());
            e.printStackTrace();
        }

        // 设置订阅监听
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                log.info("connection Lost");
                long reconnectTimes = 1;
                while (true) {
                    try {
                        if (mqttClient.isConnected()) {
                            log.info("mqtt reconnect success end");
                            break;
                        }
                        if (reconnectTimes == 10) {
                            //当重连次数达到10次时，就抛出异常，不在重连
                            log.info("mqtt reconnect error");
                            return;
                        }
                        log.info("mqtt reconnect times = " + reconnectTimes++ + " try again...");
                        mqttClient.reconnect();
                    } catch (MqttException e) {
                        log.info("error:" + e.getMessage());
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                // 存入服务端
                getJsonList(new String(mqttMessage.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
    }

    /**
     * 初始化 Client 对象
     */
    private static void initAliyunIoTClient() {

        try {
            // 构造连接需要的参数
            String clientId = "java" + System.currentTimeMillis();
            Map<String, String> params = new HashMap<>(16);
            params.put("productKey", productKey);
            params.put("deviceName", deviceName);
            params.put("clientId", clientId);
            String timestamp = String.valueOf(System.currentTimeMillis());
            params.put("timestamp", timestamp);
            // cn-shanghai
            String targetServer = "tcp://" + productKey + ".iot-as-mqtt." + regionId + ".aliyuncs.com:1883";

            String mqttclientId = clientId + "|securemode=3,signmethod=hmacsha1,timestamp=" + timestamp + "|";
            String mqttUsername = deviceName + "&" + productKey;
            String mqttPassword = AliyunIoTSignUtil.sign(params, deviceSecret, "hmacsha1");

            connectMqtt(targetServer, mqttclientId, mqttUsername, mqttPassword);
            log.info("连接MQTT服务器成功！");
        } catch (Exception e) {
            log.info("initAliyunIoTClient error " + e.getMessage());
        }
    }

    public static void connectMqtt(String url, String clientId, String mqttUsername, String mqttPassword) throws Exception {

        MemoryPersistence persistence = new MemoryPersistence();
        mqttClient = new MqttClient(url, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        // MQTT 3.1.1
        connOpts.setMqttVersion(4);
        connOpts.setAutomaticReconnect(false);
        connOpts.setCleanSession(true);

        connOpts.setUserName(mqttUsername);
        connOpts.setPassword(mqttPassword.toCharArray());
        connOpts.setKeepAliveInterval(60);

        mqttClient.connect(connOpts);

    }

    /**
     * 汇报属性
     */
    public static void changeST(int isSplit, String meterId) {
        try {
            //上报数据
            //高级版 物模型-属性上报payload
            String payloadJson = "{\"items\":{\"method\":\"thing.service.property.set\",\"id\":\"133792044\",\"params\":{\"SWITCH\":" + isSplit + ",\"ID\":\"" + meterId + "\"}}}";
            MqttMessage message = new MqttMessage(payloadJson.getBytes());
            message.setQos(1);
            mqttClient.publish(pubTopic, message);
            log.info("发送消息：" + payloadJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @Description 把输入的String转换为json存储在map中
     * @Param [json]
     **/
    public static void getJsonList(String json) {
        try {
            //String转json
            JSONObject rootObject = JSON.parseObject(json);
            MqttParam mqttParam = new MqttParam();

            JSONObject itemsObject = rootObject.getJSONObject("items");
            mqttParam.setElec_meter_id(itemsObject.getJSONObject("ID").getString("value"));
            mqttParam.setEA(itemsObject.getJSONObject("EA").getDouble("value"));
            mqttParam.setEF(itemsObject.getJSONObject("EF").getDouble("value"));
            mqttParam.setER(itemsObject.getJSONObject("ER").getDouble("value"));
            mqttParam.setA(itemsObject.getJSONObject("A").getDouble("value"));
            mqttParam.setV(itemsObject.getJSONObject("V").getDouble("value"));
            mqttParam.setPIA(itemsObject.getJSONObject("PIA").getDouble("value"));
            mqttParam.setPIR(itemsObject.getJSONObject("PIR").getDouble("value"));
            mqttParam.setPI(itemsObject.getJSONObject("PI").getDouble("value"));
            mqttParam.setPF(itemsObject.getJSONObject("PF").getDouble("value"));
            mqttParam.setST(itemsObject.getJSONObject("SWITCH").getInteger("value"));

            log.info(String.valueOf(mqttParam));
            mqttMap.put(mqttParam.getElec_meter_id(), mqttParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}

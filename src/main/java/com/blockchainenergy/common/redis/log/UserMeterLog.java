package com.blockchainenergy.common.redis.log;

import com.blockchainenergy.mqtt.server.MqttParam;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

@Data
public class UserMeterLog implements Serializable {
    private int n = 60;// 容量
    private String meterId;
    private Integer st;
    private LinkedList<Double> EAs;
    private LinkedList<Double> ERs;
    private LinkedList<Double> EFs;
    private LinkedList<Double> Vs;
    private LinkedList<Double> As;
    private LinkedList<Double> PIAs;
    private LinkedList<Double> PIRs;
    private LinkedList<Double> PIs;
    private LinkedList<Double> PFs;


    public UserMeterLog(String meterId) {
        this.meterId = meterId;

        EAs = new LinkedList<>();
        ERs = new LinkedList<>();
        EFs = new LinkedList<>();
        Vs = new LinkedList<>();
        As = new LinkedList<>();
        PIAs = new LinkedList<>();
        PIRs = new LinkedList<>();
        PIs = new LinkedList<>();
        PFs = new LinkedList<>();
    }

    public int getSize() {
        return Vs.size();
    }

    public void add(MqttParam mqttParam) {
        st = mqttParam.getST();
        EAs.add(mqttParam.getEA());
        ERs.add(mqttParam.getER());
        EFs.add(mqttParam.getEF());
        Vs.add(mqttParam.getV());
        As.add(mqttParam.getA());
        PIAs.add(mqttParam.getPIA());
        PIRs.add(mqttParam.getPIR());
        PIs.add(mqttParam.getPI());
        PFs.add(mqttParam.getPF());

        if (Vs.size() > n) {
            EAs.removeFirst();
            ERs.removeFirst();
            EFs.removeFirst();
            Vs.removeFirst();
            As.removeFirst();
            PIAs.removeFirst();
            PIRs.removeFirst();
            PIs.removeFirst();
            PFs.removeFirst();
        }
    }
}

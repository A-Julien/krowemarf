package com.prckt.krowemarf.struct.client;

import com.prckt.krowemarf.struct._Runnable;
import com.prckt.krowemarf.struct.server._Info;

import java.rmi.Naming;
import java.rmi.Remote;

public class Client implements _Runnable {

    public String url;

    public Client(String url){
        this.url = url;
    }

    @Override
    public int run() {
        return 0;
    }

    @Override
    public int stop() {
        return 0;
    }
}

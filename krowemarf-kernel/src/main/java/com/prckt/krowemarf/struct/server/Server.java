package com.prckt.krowemarf.struct.server;

import com.prckt.krowemarf.struct._Runnable;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server implements _Runnable {

    public String url;
    public int port;

    public Server(String url, int port){
        this.url = url;
        this.port = port;
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

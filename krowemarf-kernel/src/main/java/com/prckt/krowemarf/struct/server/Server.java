package com.prckt.krowemarf.struct.server;

import com.prckt.krowemarf.struct._Runnable;

public class Server implements _Runnable {
    private int port;
    private String adresse;

    public Server(int port, String adresse) {
        this.port = port;
        this.adresse = adresse;
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

package com.prckt.krowemarf.struct.client;

import com.prckt.krowemarf.struct._Runnable;
import com.prckt.krowemarf.struct.server._Info;

import java.rmi.Naming;
import java.rmi.Remote;

public class Client implements _Runnable {

    public String url;

    public Client(String url, int port){
        this.url = url;
    }

    @Override
    public int run() {
        try {
            Remote r = Naming.lookup(url);
            System.out.println(r);
            System.out.println(r instanceof com.prckt.krowemarf.struct.server._Info);
            if(r instanceof _Info){
                String s = ((_Info)r).getInfo();
                System.out.println("Chaine renvoyée : " + s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Fin du client");
        return 0;
    }

    @Override
    public int stop() {
        return 0;
    }
}

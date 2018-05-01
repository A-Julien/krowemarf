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
        try{
            LocateRegistry.createRegistry(port);
            Info info = new Info();
            System.out.println("Objet enregistré à l'url : " + url);
            Naming.rebind(url,info);
            System.out.println("Serveur lancé");

        }catch (RemoteException e){
            e.printStackTrace();
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int stop() {
        return 0;
    }
}

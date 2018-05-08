package com.prckt.krowemarf.struct;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface _Runnable {

    /**
     * Permert de demmarrer les serveur ou le client
     * @return return 0 si le démmarage a été éxécuter sans problemes, 1 si problème
     * @throws RemoteException
     */
    public int  run() throws Exception;
    public abstract void stop() throws RemoteException, NotBoundException;
    final String componentManagerName = "componentManager";
    final String userManagerName = "userManager";
    final String clientListenerManagerName = "clientListnerManager";

    default String buildRmiAddr(String name, String adresse){
        return "rmi://" + adresse + "/"+ name;
    }
}

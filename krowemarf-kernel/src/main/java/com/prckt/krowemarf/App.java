package com.prckt.krowemarf;

import com.prckt.krowemarf.components.*;
import com.prckt.krowemarf.components.Messenger.Messenger;
import com.prckt.krowemarf.components.Messenger.MessengerClient;
import com.prckt.krowemarf.components.Messenger._Messenger;
import com.prckt.krowemarf.services._ComponentManager;
import com.prckt.krowemarf.struct.client.Client;
import com.prckt.krowemarf.struct.server.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class App
{
    public static void main( String[] args ) throws RemoteException {
        Server server = new Server(1099,"127.0.0.1");
        server.run();

        _Component messaging = new Messenger("chat");
        System.out.println(messaging.getName());
        server.bindComponent(messaging);
    }
}


class clientTest
{
    public static void main( String[] args ) throws RemoteException, NotBoundException {
        Client client = new Client(1099, "127.0.0.1");
        client.run();
        _ComponentManager cmp = client.getComponentManager();

        _Component messaging = cmp.getComponantByName("chat");
        _Messenger chat = (_Messenger) messaging;

        chat.subscribe(new MessengerClient("sx"), "Boby");

        chat.postMessage("Boby", "nick ta mere la pute");

        chat.unsubscribe("Boby");
        //System.out.println("PUTE");
        //client.stop();
    }
}

class clientTest2
{
    public static void main( String[] args ) throws RemoteException, NotBoundException {
        Client client = new Client(1099, "127.0.0.1");
        client.run();
        _ComponentManager cmp = client.getComponentManager();

        _Component messaging = cmp.getComponantByName("chat");
        _Messenger chat = (_Messenger) messaging;

        chat.subscribe(new MessengerClient("sx"), "Jo");
    }
}


class clientTest3
{
    public static void main( String[] args ) throws RemoteException, NotBoundException {
        Client client = new Client(1099, "127.0.0.1");
        client.run();
        _ComponentManager cmp = client.getComponentManager();

        _Component messaging = cmp.getComponantByName("chat");
        _Messenger chat = (_Messenger) messaging;

        chat.subscribe(new MessengerClient("s"), "Jolo");

        chat.postMessage("Jolo", "mdr ta mere est conne");
    }
}
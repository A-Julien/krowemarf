package com.prckt.krowemarf;

import com.prckt.krowemarf.components.*;
import com.prckt.krowemarf.components.Messenger.Messenger;
import com.prckt.krowemarf.components.Messenger.MessengerClient;
import com.prckt.krowemarf.components.Messenger._Messenger;
import com.prckt.krowemarf.components.Posts.Posts;
import com.prckt.krowemarf.components.Posts._Posts;
import com.prckt.krowemarf.services.DbConnectionManager;
import com.prckt.krowemarf.services._ComponentManager;
import com.prckt.krowemarf.struct.client.Client;
import com.prckt.krowemarf.struct.server.Server;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;


public class App
{
    public static void main( String[] args ) throws RemoteException {
        Server server = new Server(1099,"127.0.0.1");
        server.run();

        _Component messaging = new Messenger("chat");
        _Component posts =  new Posts("commentaires");
        server.bindComponent(messaging);
        server.bindComponent(posts);
    }
}

class clientTestCommentaire
{
    public static void main( String[] args ) throws RemoteException, NotBoundException {
        Client client = new Client(1099, "127.0.0.1");
        client.run();
        _ComponentManager cmp = client.getComponentManager();

        _Component posts = cmp.getComponantByName("commentaires");
        _Posts post = (_Posts) posts;
        _DefaultMessage t = new DefaultMessage("ni","coucou","moi",new GregorianCalendar());
        post.addPost(new testMsg("coucou","moi",3));
        post.addPost(t);
        ArrayList<_DefaultMessage> arrayList =  post.loadPost();
        for (_DefaultMessage e :arrayList) {
            System.out.println(e.toStrings());
        }
        //System.out.println("PUTE");
        //client.stop();
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

class db
{
    public static void main( String[] args ) throws RemoteException, NotBoundException, SQLException {
        DbConnectionManager db = new DbConnectionManager();
        PreparedStatement p = db.connect().prepareStatement("SELECT * FROM TEST");
    }
}
package com.prckt.krowemarf;

import com.prckt.krowemarf.components.DefaultMessage;
import com.prckt.krowemarf.components.DocumentLibrary.DocumentLibrary;
import com.prckt.krowemarf.components.DocumentLibrary.MetaDataDocument;
import com.prckt.krowemarf.components.DocumentLibrary._DocumentLibrary;
import com.prckt.krowemarf.components.DocumentLibrary._MetaDataDocument;
import com.prckt.krowemarf.components.Messenger.Messenger;
import com.prckt.krowemarf.components.Messenger.MessengerClient;
import com.prckt.krowemarf.components.Messenger._Messenger;
import com.prckt.krowemarf.components.Posts.Posts;
import com.prckt.krowemarf.components.Posts._Posts;
import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.components.testMsg;
import com.prckt.krowemarf.services.DbConnectionManager;
import com.prckt.krowemarf.services._ComponentManager;
import com.prckt.krowemarf.struct.client.Client;
import com.prckt.krowemarf.struct.server.Server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;


public class App
{
    public static void main( String[] args ) throws RemoteException {
        Server server = new Server(1099,"127.0.0.1");
        server.run();

        _Component messaging = new Messenger("chat");

        _Component posts =  new Posts("commentaires");

        _Component googleDrive = new DocumentLibrary("drive","/Users/julien/Desktop/");
        _Component Drive = new DocumentLibrary("drive2","/Users/julien/Desktop/");

        server.bindComponent(messaging);
        server.bindComponent(posts);
        server.bindComponent(googleDrive);

    }
}

class clientTestDrive
{
    public static void main( String[] args ) throws Exception {
        Client client = new Client(1099, "127.0.0.1");
        client.run();

        _ComponentManager cmp = client.getComponentManager();

        _DocumentLibrary drive = (_DocumentLibrary) cmp.getComponantByName("drive");

        File file = new File("/Users/julien/Downloads/TestRMI-2.rar");
        byte buffer[] = new byte[(int)file.length()];
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("/Users/julien/Downloads/TestRMI-2.rar"));
        inputStream.read(buffer,0,buffer.length);
        inputStream.close();

        LinkedList<_MetaDataDocument> metaDataDocuments = drive.getall();

        //drive.uploadFile("jojo", buffer, new MetaDataDocument("TestRMI-2" ,"zip",file.length(),""));
        drive.uploadFile("jojo",DocumentLibrary.fileToBytes(file),
                new MetaDataDocument("TestRMI-2" ,"zip",file.length(),""));

        DocumentLibrary.writeFile(
                drive.downloadFile(new MetaDataDocument("TestRMI-2" ,"zip",file.length(),"/Users/julien/Desktop/")),
                "/Users/julien/Desktop/test/" + "TestRMI-2.zip"
        );


        System.out.println("PUTE");
        //client.stop();
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

        _DefaultMessage t = new DefaultMessage("coucou","moi", new GregorianCalendar());

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

        chat.subscribe(new MessengerClient() {
            @Override
            public void onReceive(_DefaultMessage message) throws RemoteException {
                System.out.println(message.toStrings());
            }
        }, "Boby");

        chat.postMessage("Boby", new DefaultMessage("nicke ta mere","Boby",new GregorianCalendar()));

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

        chat.subscribe(new MessengerClient() {
            @Override
            public void onReceive(_DefaultMessage message) throws RemoteException {
                System.out.println(message.toStrings());
            }

        }, "Jo");
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

        chat.subscribe(new MessengerClient() {
            @Override
            public void onReceive(_DefaultMessage message) throws RemoteException {
                System.out.println(message.toStrings());
            }
        }, "Jolo");

        chat.postMessage("Jolo",
                new DefaultMessage("nicke ta mere","Jolo",new GregorianCalendar()));
    }
}

class db
{
    public static void main( String[] args ) throws RemoteException, NotBoundException, SQLException {
        DbConnectionManager db = new DbConnectionManager();
        PreparedStatement p = db.connect().prepareStatement("SELECT * FROM TEST");
    }
}
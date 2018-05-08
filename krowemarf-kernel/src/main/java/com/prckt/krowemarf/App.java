package com.prckt.krowemarf;

import com.prckt.krowemarf.components.DefaultMessage;
import com.prckt.krowemarf.components.DocumentLibrary.DocumentLibrary;
import com.prckt.krowemarf.components.DocumentLibrary.MetaDataDocument;
import com.prckt.krowemarf.components.DocumentLibrary._DocumentLibrary;
import com.prckt.krowemarf.components.DocumentLibrary._MetaDataDocument;
import com.prckt.krowemarf.components.Messenger.Messenger;
import com.prckt.krowemarf.components.Messenger._Messenger;
import com.prckt.krowemarf.components.Messenger.£MessengerClient;
import com.prckt.krowemarf.components.Posts.Posts;
import com.prckt.krowemarf.components.Posts._Posts;
import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.components.testMsg;
import com.prckt.krowemarf.services.ClientListenerManagerServices.£ClientListener;
import com.prckt.krowemarf.services.ComponentManagerSevices._ComponentManager;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices._User;
import com.prckt.krowemarf.struct.Client;
import com.prckt.krowemarf.struct.Server;

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

//TODO CLient listner pour actions ascynchrones,
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
        Client client = new Client(1099, "127.0.0.1", "Seb", "mdp");
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
        Client client = new Client(1099, "127.0.0.1", "ddff","dsf");
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


class clientTest1
{
    public static void main( String[] args ) throws RemoteException {
        Client client = new Client(1099, "127.0.0.1","Jean","mdp");
        client.run();

        _ComponentManager cmp = client.getComponentManager();

        client.initClientListner(new £ClientListener() {
            @Override
            public void onNewPrivateMessenger(String composenteName) throws RemoteException {

                //System.out.println(cmp.getComponantByName(composenteName));
                ((_Messenger)cmp.getComponantByName(composenteName)).subscribe(new £MessengerClient() {
                    @Override
                    public void onReceive(_DefaultMessage message) throws RemoteException {
                        System.out.println("message : " + message.toStrings());
                    }

                    @Override
                    public void onLeave(_User user) throws RemoteException {
                        System.out.println(user.getLogin() + " Leave ");
                    }
                },client.getUser());
            }
        });

        _Messenger m = (_Messenger) cmp.getComponantByName("chat");

        m.subscribe(new £MessengerClient() {
            @Override
            public void onReceive(_DefaultMessage message) throws RemoteException {
                System.out.println("coucou");
            }

            @Override
            public void onLeave(_User user) throws RemoteException {
                System.out.println("leave");
            }
        }, client.getUser());



        //_Component messaging = cmp.getComponantByName("chat");
        //_Messenger chat = (_Messenger) messaging;

        /*chat.subscribe(new £MessengerClient() {
            @Override
            public void onNewPrivateMessenger(_DefaultMessage message) throws RemoteException {
                System.out.println(message.toStrings());
            }
        }, "Boby");*/


        //chat.postMessage(client.getUser(),
    //            new DefaultMessage("nicke ta mere","Boby",new GregorianCalendar()));
//
  //      chat.unsubscribe("Boby");

        //System.out.println("PUTE");
        //client.stop();
    }
}
//TODO call methode creatmp return id, userManager-> id, ArrayList<User>
//TODO userManager avec listUserCo hashtable(User-Listner);
//TODO parcour list, si user corrspond on appelle methode listner (id)
class clientTest
{
    public static void main( String[] args ) throws RemoteException, NotBoundException, InterruptedException {
        Client client = new Client(1099, "127.0.0.1","Seb","mdp");

        client.run();

        _ComponentManager cmp = client.getComponentManager();


        client.initClientListner(new £ClientListener() {
            @Override
            public void onNewPrivateMessenger(String composenteName) throws RemoteException {

                ((_Messenger)cmp.getComponantByName(composenteName)).subscribe(new £MessengerClient() {
                    @Override
                    public void onReceive(_DefaultMessage message) throws RemoteException {
                        System.out.println(message.toStrings());
                    }

                    @Override
                    public void onLeave(_User user) throws RemoteException {
                        System.out.println(user.getLogin() + " Leave ");
                    }
                }, client.getUser());
                System.out.println("hello");

                ((_Messenger)cmp.getComponantByName(composenteName)).postMessage(client.getUser(),new DefaultMessage("ccoucou hibou",client.getUser().getLogin(),new GregorianCalendar()));

            }
        });
        ArrayList<_User> u = new ArrayList<>();
        for (_User ut:
             client.getAllUsersConnected()) {
            if(ut.getLogin().equals("Jean") || ut.getLogin().equals("Seb")){
                u.add(ut);
            }
        }
        client.newPrivateMessenger(u);



       /* _Component messaging = cmp.getComponantByName("chat");
        _Messenger chat = (_Messenger) messaging;

        chat.subscribe(new £MessengerClient() {
            @Override
            public void onNewPrivateMessenger(_DefaultMessage message) throws RemoteException {
                System.out.println(message.toStrings());
            }
        }, "Jo");*/
        //client.stop();
    }
    

}


class clientTest3
{
    public static void main( String[] args ) throws RemoteException {
        Client client = new Client(1099, "127.0.0.1","Philippe","mdp");
        if(client.run() == 1){ System.out.println("error"); System.exit(1);}
        _ComponentManager cmp = client.getComponentManager();

        _Component messaging = cmp.getComponantByName("chat");
        _Messenger chat = (_Messenger) messaging;

        chat.subscribe(new £MessengerClient() {
            @Override
            public void onReceive(_DefaultMessage message) throws RemoteException {
                System.out.println("ntmntmntmtnl");
            }

            @Override
            public void onLeave(_User user) throws RemoteException {
                System.out.println(user.getLogin() + " Leave ");
            }
        }, client.getUser());

        chat.unsubscribe(client.getUser());
        client.stop();
    }
}

class db
{
    public static void main( String[] args ) throws RemoteException, NotBoundException, SQLException {
        DbConnectionManager db = new DbConnectionManager();
        PreparedStatement p = db.connect().prepareStatement("SELECT * FROM TEST");
    }
}
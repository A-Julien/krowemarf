package com.prckt.krowemarf;

import com.prckt.krowemarf.components.DocumentLibrary.DocumentLibrary;
import com.prckt.krowemarf.components.DocumentLibrary._DocumentLibrary;
import com.prckt.krowemarf.components.DocumentLibrary._MetaDataDocument;
import com.prckt.krowemarf.components.Messenger.Messenger;
import com.prckt.krowemarf.components.Messenger._Messenger;
import com.prckt.krowemarf.components.Messenger.£MessengerClient;
import com.prckt.krowemarf.components.Posts.Posts;
import com.prckt.krowemarf.components.Posts._Posts;
import com.prckt.krowemarf.components.TypeMessage;
import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.services.ClientListenerManagerServices.£ClientListener;
import com.prckt.krowemarf.services.ComponentManagerSevices._ComponentManager;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices._User;
import com.prckt.krowemarf.struct.Client.Client;
import com.prckt.krowemarf.struct.Server.Server;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class App
{
    public static void main( String[] args ) throws IOException, SQLException, ClassNotFoundException {
        Server server = new Server(1099,"127.0.0.1");
        server.run();

        _Component messaging = new Messenger("chat");

        _Component posts =  new Posts("commentaires");

        _Component googleDrive = new DocumentLibrary("drive","/Users/julien/Downloads");

        server.bindComponent(posts);
        server.bindComponent(messaging);
        server.bindComponent(googleDrive);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(200);
                    System.out.println("Shouting down ...");
                    server.stop();

                } catch (InterruptedException | RemoteException | NotBoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

class clientTestDrive
{
    public static void main( String[] args ) throws Exception {
        Client client = new Client(1099, "127.0.0.1");
        client.setCredential("Seb","mdp");
        client.run();

        _ComponentManager cmp = client.getComponentManager();

        _DocumentLibrary drive = (_DocumentLibrary) cmp.getComponantByName("drive");

        File file = new File("/Users/julien/Downloads/TestRMI-2.zip");
        /*byte buffer[] = new byte[(int)file.length()];
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream("/Users/julien/Downloads/TestRMI-2.rar"));
        inputStream.read(buffer,0,buffer.length);
        inputStream.close();*/

       // drive.uploadFile("jojo",DocumentLibrary.fileToBytes(file), new MetaDataDocument("TestRMI-2" ,"zip",file.length(),""));
        ArrayList<_MetaDataDocument> metaDataDocuments = drive.getall();


        for (_MetaDataDocument m : metaDataDocuments) {
            System.out.println("meta -> " + m.getName());
        }

        //drive.uploadFile(client.getUser(),DocumentLibrary.fileToBytes(file), new MetaDataDocument(client.getUser(),"TestRMI-2" ,"zip",file.length(),""));

        drive.remove(metaDataDocuments.get(0));

        /*DocumentLibrary.writeFile(
                drive.downloadFile(metaDataDocuments.get(0)),//new MetaDataDocument(client.getUser(),"TestRMI-2" ,"zip",file.length(),"/Users/julien/Desktop/")),
                "/Users/julien/Desktop/", metaDataDocuments.get(0)
        );*/


        client.stop();
    }
}

class clientTestCommentaire
{
    public static void main( String[] args ) throws Exception {
        Client client = new Client(1099, "127.0.0.1");
        client.setCredential("Seb","mdp");
        if(client.run() == 1){ System.out.println("error"); System.exit(1);}


        _ComponentManager cmp = client.getComponentManager();
        
        _Posts post = (_Posts) cmp.getComponantByName("commentaires");

        //_Component posts = cmp.getComponantByName("commentaires");
       // _Posts post = (_Posts) posts;

        //_DefaultMessage t = new £DefaultMessage("coucou","moi", new GregorianCalendar());

        //post.savePost(SerializationUtils.serialize(new TypeMessage("CONTENU",client.getUser().getLogin())));
        //post.savePost(SerializationUtils.serialize(new TypeMessage("CONTENU",client.getUser().getLogin())));

        //post.savePost(S

        //post.addPost(t);




       _DefaultMessage t  = new TypeMessage("CONTENU",client.getUser(), new Date(96, 1, 14));
       System.out.println("On enregistre ça : " + t.toStrings());

       post.addMessage(SerializationUtils.serialize(t));

        HashMap<Integer,_DefaultMessage> hm =  post.loadPost();
        for (_DefaultMessage messages: hm.values()) {

        }
//        System.out.println("On load ça : " + hm.get(15).toStrings());
 //       post.removePost(15);

      /* for (_DefaultMessage e :arrayList) {
           System.out.println("On load ça : " + e.toStrings());
           System.out.println("On delete ça : " + t.toStrings());
           post.removePost(SerializationUtils.serialize(t));
        }*/


        //System.out.println("PUTE");
        client.stop();
    }
}


class clientTest1
{
    public static void main( String[] args ) throws Exception {
        Client client = new Client(1099, "127.0.0.1");
        client.setCredential("Jean","mdp");
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
                System.out.println(message.toStrings());
            }

            @Override
            public void onLeave(_User user) throws RemoteException {
                System.out.println(user.getLogin() +  " leave");
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
    //            new £DefaultMessage("nicke ta mere","Boby",new GregorianCalendar()));
  //      chat.unsubscribe("Boby");

        //System.out.println("PUTE");
        //client.stop();
    }
}
/** call methode creatmp return id, userManager-> id, ArrayList<User>
 userManager avec listUserCo hashtable(User-Listner);
 parcour list, si user corrspond on appelle methode listner (id)*/
class clientTest
{
    public static void main( String[] args ) throws Exception {
        Client client = new Client(1099, "127.0.0.1");
        client.setCredential("Seb","mdps");
        if(client.run() == 1 ){
            client.setCredential("Seb","mdp");
            client.run();
        }
        _ComponentManager cmp = client.getComponentManager();


        client.initClientListner(new £ClientListener() {
            @Override
            public void onNewPrivateMessenger(String composenteName) throws RemoteException, SQLException {

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

                ((_Messenger)cmp.getComponantByName(composenteName)).postMessage(client.getUser(),new TypeMessage("ccoucou hibou",client.getUser()));

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
        client.stop();

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

//TODO sans client.close gros bug lors de la reco
class clientTest3
{
    public static void main( String[] args ) throws Exception {
        Client client = new Client(1099, "127.0.0.1");
        client.setCredential("Philippe","mdp");
        if(client.run() == 1){ System.out.println("error"); System.exit(1);}
        _ComponentManager cmp = client.getComponentManager();

        _Component messaging = cmp.getComponantByName("chat");
        _Messenger chat = (_Messenger) messaging;

        chat.subscribe(new £MessengerClient() {
            @Override
            public void onReceive(_DefaultMessage message) throws RemoteException {
                System.out.println(message.toStrings());
            }

            @Override
            public void onLeave(_User user) throws RemoteException {
                System.out.println(user.getLogin() + " Leave ");
            }
        }, client.getUser());

        TypeMessage typeMessage = new TypeMessage("CONTENU",client.getUser());

        //chat.postMessage(client.getUser(), typeMessage);

        //chat.saveMessage(SerializationUtils.serialize(typeMessage));

        chat.reLoadMessage(client.getUser());


        chat.unsubscribe(client.getUser());

        client.stop();
    }
}

class db
{
    public static void main( String[] args ) throws RemoteException, NotBoundException, SQLException {
        DbConnectionManager db = new DbConnectionManager();
        PreparedStatement p = db.connect("tt").prepareStatement("SELECT * FROM TEST");
    }
}


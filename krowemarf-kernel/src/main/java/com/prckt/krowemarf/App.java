package com.prckt.krowemarf;

import com.prckt.krowemarf.components.DocumentLibrary.DocumentLibrary;
import com.prckt.krowemarf.components.DocumentLibrary.MetaDataDocument;
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
import com.prckt.krowemarf.services.Access;
import com.prckt.krowemarf.services.ClientListenerManagerServices.£ClientListener;
import com.prckt.krowemarf.services.ComponentManagerSevices._ComponentManager;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices.User;
import com.prckt.krowemarf.services.UserManagerServices._User;
import com.prckt.krowemarf.struct.Client;
import com.prckt.krowemarf.struct.Server;
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

        _Component googleDrive = new DocumentLibrary("drive","C:\\Users\\Clément HERESAZ\\Downloads");

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




class clientTestBd
{
    public static void main( String[] args ) throws Exception {
        Client client = new Client(1099, "127.0.0.1");
        client.setCredential("Seb","mdp");
        if(client.run() == 1){ System.out.println("error"); System.exit(1);}

        Posts p = new Posts("test");
        User user1 = new User("Seb","mdp");
        User user2 = new User("Jean","mdp");
        Access access1 = new Access(user1, "admin");
        ArrayList<MetaDataDocument> m;

        DocumentLibrary lib = new DocumentLibrary("musique", "/Users/toto/IdeaProjects");
        MetaDataDocument doc1 = new MetaDataDocument(user1 , "livre", "txt", (float) 13.4, "/Users/toto/IdeaProjects");
        MetaDataDocument doc2 = new MetaDataDocument(user1, "jeu", "txt", (float) 18.9, "/Users/toto/IdeaProjects");

        //add
        lib.add(doc1);
        lib.add(doc2);

        //get
        MetaDataDocument doc3 = lib.get("livre", "/Users/toto/IdeaProjects", "txt");
        System.out.println("name : " + doc3.getName() + "; extension : " + doc3.getExtension() + "; size : " + doc3.getSize() + "; path : " + doc3.getPath() + "; type : " + doc3.getType());

        //getall
        m = lib.getall();
        for (int i = 0; i<m.size(); i++) {
            System.out.println("name : " + m.get(i).getName() + "; extension : " + m.get(i).getExtension() + "; size : " + m.get(i).getSize() + "; path : " + m.get(i).getPath() + "; type : " + m.get(i).getType());
        }

        //filterByName
        m.clear();
        m = lib.filterByName("jeu");
        for (int i = 0; i<m.size(); i++) {
            System.out.println("name : " + m.get(i).getName() + "; extension : " + m.get(i).getExtension() + "; size : " + m.get(i).getSize() + "; path : " + m.get(i).getPath() + "; type : " + m.get(i).getType());
        }

        //filterByExtension
        m.clear();
        m = lib.filterByExtension("txt");
        for (int i = 0; i<m.size(); i++) {
            System.out.println("name : " + m.get(i).getName() + "; extension : " + m.get(i).getExtension() + "; size : " + m.get(i).getSize() + "; path : " + m.get(i).getPath() + "; type : " + m.get(i).getType());
        }

        //filterByPath
        m.clear();
        m = lib.filterByPath("/Users/toto/IdeaProjects");
        for (int i = 0; i<m.size(); i++) {
            System.out.println("name : " + m.get(i).getName() + "; extension : " + m.get(i).getExtension() + "; size : " + m.get(i).getSize() + "; path : " + m.get(i).getPath() + "; type : " + m.get(i).getType());
        }

        //filterBySizeSup
        m.clear();
        m = lib.filterBySizeSup(15);
        for (int i = 0; i<m.size(); i++) {
            System.out.println("name : " + m.get(i).getName() + "; extension : " + m.get(i).getExtension() + "; size : " + m.get(i).getSize() + "; path : " + m.get(i).getPath() + "; type : " + m.get(i).getType());
        }

        //filterBySizeInf
        m.clear();
        m = lib.filterBySizeInf(15);
        for (int i = 0; i<m.size(); i++) {
            System.out.println("name : " + m.get(i).getName() + "; extension : " + m.get(i).getExtension() + "; size : " + m.get(i).getSize() + "; path : " + m.get(i).getPath() + "; type : " + m.get(i).getType());
        }

        //filterBySizeInterval
        m.clear();
        m = lib.filterBySizeInterval(15, 20);
        for (int i = 0; i<m.size(); i++) {
            System.out.println("name : " + m.get(i).getName() + "; extension : " + m.get(i).getExtension() + "; size : " + m.get(i).getSize() + "; path : " + m.get(i).getPath() + "; type : " + m.get(i).getType());
        }

        //filterByType
        m.clear();
        m = lib.filterByType("Text");
        for (int i = 0; i<m.size(); i++) {
            System.out.println("name : " + m.get(i).getName() + "; extension : " + m.get(i).getExtension() + "; size : " + m.get(i).getSize() + "; path : " + m.get(i).getPath() + "; type : " + m.get(i).getType());
        }

        // remove
        lib.remove(doc1);
        lib.remove(doc2);


        //addDbComponent
        p.addDbComponent();

        //addAccess
        p.addAccess(access1);
        p.addAccess(user2,"user");

        //isPermission
        String r = p.isPermission(user1);
        System.out.println("L'utilisateur Seb est " + r + " sur ce composant.");

        //isAdmin
        ArrayList<String> admin = p.isAdmin();

        System.out.println("Les administrateurs sont: ");
        for (int i = 0; i<admin.size(); i++) {
            System.out.println( admin.get(i) + " ");
        }

        //isUser
        ArrayList<String> user = p.isUser();

        System.out.println("Les utilisateur sont: ");
        for (int i = 0; i<user.size(); i++) {
            System.out.println( user.get(i) + " ");
        }

        //removeAccess
        p.removeAccess(access1);
        p.removeAccess(user2);

        //removeDbComponent
        p.removeDbComponent();

        client.stop();
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
        ArrayList<MetaDataDocument> metaDataDocuments = drive.getall();


        for (MetaDataDocument m : metaDataDocuments) {
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

       post.addPost(SerializationUtils.serialize(t));

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


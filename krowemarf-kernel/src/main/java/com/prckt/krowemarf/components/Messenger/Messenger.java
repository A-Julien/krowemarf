package com.prckt.krowemarf.components.Messenger;

import com.prckt.krowemarf.components.TypeMessage;
import com.prckt.krowemarf.components._Component;
import com.prckt.krowemarf.components._DefaultMessage;
import com.prckt.krowemarf.components.£DefaultMessage;
import com.prckt.krowemarf.services.DbConnectionServices.DbConnectionManager;
import com.prckt.krowemarf.services.DbConnectionServices._DbConnectionManager;
import com.prckt.krowemarf.services.UserManagerServices.User;
import com.prckt.krowemarf.services.UserManagerServices._User;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Messenger component allow client to send instant _DefaultMessage
 * to other client
 *
 */
public class Messenger extends UnicastRemoteObject implements _Messenger{
    private Hashtable<_User, _MessengerClient> users;

    public String name;
    private  Connection dbConnection;


    /**
     * Constructor of the component
     * @param name of the component
     * @throws RemoteException
     */
    public Messenger(String name) throws RemoteException {
        super();
        this.name = name;
        this.users = new Hashtable<>();
        this.dbConnection = new DbConnectionManager().connect(this.getName());
    }

    /**
     * This method allow client subscribe to the component.
     * When subscribe, client give to him the two callback (onReceive and onLeave)
     *
     * @param messengerClient Callback given by client
     * @param user user
     * @throws RemoteException
     */
    @Override
    public void subscribe(_MessengerClient messengerClient, _User user) throws RemoteException {
        if(!this.users.containsKey(user)){
            this.users.put(user, messengerClient);
            Logger.getGlobal().log(Level.INFO,user.getLogin() +  " connected to chat : " + this.getName());
        }
    }

    /**
     * Unsubscribe a user to this component
     * @param user user to unsubscribe
     * @throws RemoteException
     */
    @Override
    public void unsubscribe(_User user) throws RemoteException {
        if(this.users.containsKey(user)){
            this.users.remove(user);
            Logger.getGlobal().log(Level.INFO,user.getLogin() +  " unsubscribe to chat : " + this.getName());
            Enumeration<_MessengerClient> e = this.users.elements();
            while (e.hasMoreElements()){
                _MessengerClient clients = e.nextElement();
                clients.onLeave(user);
            }
        }
    }

    /**unsubscribe
     * This method allow client to post message on the component
     * When a user post a message, the server call all callback onRecieve of
     * each client who subscribed of this component
     *
     * @param user user who post the message
     * @param message the message
     * @throws RemoteException
     */
    @Override
    public void postMessage(_User user, _DefaultMessage message) throws RemoteException {
        Logger.getGlobal().log(Level.INFO,user.getLogin() +  " send message : " + message.toStrings());
        Enumeration<_MessengerClient> e = this.users.elements();
        while (e.hasMoreElements()){
            _MessengerClient clients = e.nextElement();
            clients.onReceive(message);
        }


    }

    /**
     * This method allow client to save conversation to the bd.
     * To pass the message in RMI the message need to be serialized
     * @param message serialized message
     * @throws RemoteException
     */
    @Override
    public void saveMessage(byte[] message) throws RemoteException{
        if(SerializationUtils.deserialize(message) instanceof _DefaultMessage){
            try {
                _DbConnectionManager.serializeJavaObjectToDB(this.dbConnection, message, this.getName(), _Component.messengerTableName);
            } catch (SQLException e1) {
                Logger.getGlobal().log(Level.INFO, "Erreur lors de la sauvegarde du message");
                e1.printStackTrace();
            }
        }else{
            Logger.getGlobal().log(Level.INFO, "Impossible de sauvegarder le message, n'est pas un _DefaultMessage");
        }

    }


    /**
     * This method allow client when he started to use a compenent that he use in the past to reload
     * all history of conversation
     *
     * @param user user who want reload history of the messenger component
     * @throws IOException
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws RemoteException
     */
    @Override
    public void reLoadMessage(_User user) throws IOException, SQLException, ClassNotFoundException, RemoteException {
        ArrayList<Object> messages = _DbConnectionManager.deSerializeJavaObjectFromDB(this.dbConnection, _Component.messengerTableName, this.getName());
        //Enumeration<_MessengerClient> e = this.users.elements();

        for (Object o: messages) {
            this.users.get(user).onReceive((£DefaultMessage)o);
        }
        Logger.getGlobal().log(Level.INFO, user.getLogin() + " demande un reload du composant " + this.name);
    }


    /**
     * Return the name of the component
     * @return String name
     * @throws RemoteException
     */
    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    /**
     * Stop the component.
     * Warns all subscriber that the component will shutdown
     *
     * @throws SQLException
     * @throws RemoteException
     */
    @Override
    public void stop() throws SQLException, RemoteException {
        Logger.getGlobal().log(Level.INFO,"Arrêt du component : " + this.getName());
        _User messaging = new User(this.getName());
        this.postMessage(messaging, new TypeMessage("Your message" + this.getName() +" will be close in few second", messaging));
        this.dbConnection.close();
    }
}

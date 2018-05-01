package com.prckt.krowemarf;

import com.prckt.krowemarf.struct.server.Server;

public class ServerTest {

    public static void main( String[] args )
    {
        System.out.println( "Démarrage du serveur" );
        Server server = new Server("rmi://localhost/TestRMI", 1099);
        server.run();
    }
}

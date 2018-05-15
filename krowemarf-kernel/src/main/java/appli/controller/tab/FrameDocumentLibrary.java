package appli.controller.tab;

import com.prckt.krowemarf.components.DocumentLibrary.DocumentLibrary;
import com.prckt.krowemarf.components.DocumentLibrary.MetaDataDocument;
import com.prckt.krowemarf.components.DocumentLibrary._MetaDataDocument;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class FrameDocumentLibrary extends JFrame {
    JPanel jPanelDL = null;
    JButton jButtonLoad = null;
    JButton jButtonSend = null;
    JFileChooser jFileChooserDL = null;
    ConnexionController connexionController;
    DocumentLibrary dL;


    public FrameDocumentLibrary(ConnexionController _connexionController){
        this.connexionController = _connexionController;
        initComponents();
    }

    public void initComponents(){
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(400, 240);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        this.setLocation((int) rect.getMaxX() - this.getWidth(), 0);
        this.setVisible(true);
        this.setTitle("Krowemarf");

        this.jButtonLoad = new JButton("Télécharger un fichier");
        this.jButtonLoad.setBounds(0,0,400,100);
        this.jButtonLoad.setFont(jButtonLoad.getFont().deriveFont(Font.BOLD, 16f));
/*        this.jButtonLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    jButtonLoadActionPerformed(e);
                } catch (RemoteException exc) {
                    exc.printStackTrace();
                }
            }
        });*/

        this.jButtonSend = new JButton("Envoyer un fichier");
        this.jButtonSend.setBounds(0,100,400,100);
        this.jButtonSend.setFont(jButtonSend.getFont().deriveFont(Font.BOLD, 16f));
        this.jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    jButtonSendActionPerformed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        this.add(jButtonLoad);
        this.add(jButtonSend);

        this.repaint();
    }

    private void jButtonLoadActionPerformed(ActionEvent e) throws RemoteException {
        System.out.println("dadzdaz");
        JList<String> metaDataList = new JList<String>();
        metaDataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane jScrollList;

        DefaultListModel modelDL = new DefaultListModel();

        this.dL = (DocumentLibrary) this.connexionController.client.getComponentManager().getComponantByName("DocumentLibrary");

        URI pathFile = null;

        ArrayList alDL;
        alDL = dL.getall();

        _MetaDataDocument metaDataDocument;
        for(int i=0; i<alDL.size(); i++){
            metaDataDocument = (MetaDataDocument) alDL.get(i);
            metaDataList.add(metaDataDocument.getName(), (Component) metaDataDocument);
        }

        FrameListDocument frameListDocument = new FrameListDocument();

        jScrollList = new JScrollPane();
        jScrollList.setBounds(20, 120,220, 80);
        jScrollList.setViewportView(metaDataList);

        metaDataList.getListSelectionListeners();

        this.dL.downloadFile(new MetaDataDocument(new File(pathFile)));
    }

    private void jButtonSendActionPerformed(ActionEvent e) throws IOException {
        this.jFileChooserDL = new JFileChooser();
        this.jFileChooserDL.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (JFileChooser.CANCEL_OPTION != this.jFileChooserDL.showSaveDialog(this)) {
            File fileChosen = this.jFileChooserDL.getSelectedFile();

            this.dL = (DocumentLibrary) this.connexionController.client.getComponentManager().getComponantByName("DocumentLibrary");
            this.dL.uploadFile(this.connexionController.client.getUser(),
                                new byte[]{},
                                new MetaDataDocument(fileChosen));
        }
    }
}
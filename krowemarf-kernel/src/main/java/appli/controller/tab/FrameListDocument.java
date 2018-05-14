package appli.controller.tab;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class FrameListDocument extends JFrame implements ActionListener {
    private Container container;
    private JButton download;
    private JLabel jLabel;
    private JTextField jTextField;
    private JList jList;
    private DefaultListModel model;
    private JScrollPane scrollList;

    public FrameListDocument(){
        this.initComponents();
        this.setTitle("CoDejaVu : JList");
        this.setSize(280,330);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void initComponents() {
        container = getContentPane();
        container.setLayout(null);
        jTextField= new JTextField();
        jTextField.setBounds(20, 80, 135, 23);
        
        download= new JButton();
        download.setText("Télécharger");
        download.setBounds(20, 210, 80, 23);
        download.addActionListener(this);

        jLabel= new JLabel();
        jLabel.setFont(new java.awt.Font("Tahoma", 0, 28));
        jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel.setText("JList");
        jLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jLabel.setBounds(40, 20, 180, 43);

        jList = new JList();
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );

        model = new DefaultListModel();

        scrollList = new JScrollPane();
        scrollList.setBounds(20, 120,220, 80);
        scrollList.setViewportView(jList);

        container.add(jLabel);
        container.add(jTextField);
        container.add(download);
        container.add(scrollList);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==download)
        {
            //downloadAction();
        }
    }
}

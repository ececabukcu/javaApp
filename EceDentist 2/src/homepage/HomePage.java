package homepage;
import appointmentH.AppointmentH;
import medicineH.MedicineH;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends JFrame {

    private JPanel homepagePanel;
    private JButton appointmentButton;
    private JTree contactTree;
    private JButton medicineButton;
    private JLabel icon;
    private JPanel MainP;
    DefaultTreeModel model;
    DefaultMutableTreeNode contact = new DefaultMutableTreeNode("Contact Us");

    public HomePage() {
        init();
    }

    private void init() {
        setContentPane(homepagePanel);
        setTitle("Home Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        TreeForm();

        appointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AppointmentH().setVisible(true);
            }
        });

        medicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MedicineH().setVisible(true);
            }
        });
        contactTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) contactTree.getLastSelectedPathComponent();
                    if (selectedNode != null && selectedNode.isLeaf()) {
                        showInfoDialog(selectedNode.getUserObject().toString());
                    }
                }
            }
        });

    }

    public void Load() {
        DefaultMutableTreeNode adressNode = new DefaultMutableTreeNode("Our Address");
        adressNode.add(new DefaultMutableTreeNode("Saray Mah. Site Yolu Cad. No:13-15A 34768 Ümraniye/İstanbul"));

        DefaultMutableTreeNode phoneNode = new DefaultMutableTreeNode("Phone Number");
        phoneNode.add(new DefaultMutableTreeNode("05556785434"));

        DefaultMutableTreeNode mailNode = new DefaultMutableTreeNode("Mail Adress");
        mailNode.add(new DefaultMutableTreeNode("ecedentist@gmail.com"));


        contact.add(adressNode);
        contact.add(phoneNode);
        contact.add(mailNode);

        model = (DefaultTreeModel) contactTree.getModel();
        model.setRoot(contact);
        contactTree.setModel(model);
    }
    private void showInfoDialog(String info) {
        JOptionPane.showMessageDialog(this, info, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    public void centerWindow() {
        setContentPane(homepagePanel);
        setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = getSize();
        int x = (screenSize.width - windowSize.width) / 2;
        int y = (screenSize.height - windowSize.height) / 2;
        setLocation(x, y);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }

    public void TreeForm() {
        centerWindow();
        Load();
        contactTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
    }
}



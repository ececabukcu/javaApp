package buyNowH;

import appointmentH.AppointmentH;
import utils.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BuyNowH extends JFrame {

    private JPanel creditCardPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton BUYButton;


    public BuyNowH(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        //setLocationRelativeTo(this);
        setContentPane(creditCardPanel);
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Credit Card Panel");
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        BUYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(true);
                JOptionPane.showMessageDialog(rootPane, "Shopping completed");
            }
        });

    }


    }


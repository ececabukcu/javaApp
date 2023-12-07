package adminpage;
import adminButtons.MedicineP;
import adminButtons.TreatmentP.TreatmentP;
import doctorProcedures.DoctorProceduresEdit;
import doctorProcedures.DoctorProceduresView;
import patientProcedues.PatientPocedures;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AdminPage extends JFrame {
    private JPanel adminPagePanel;
    private JPanel apanel;
    private JLabel dpPanel;
    private JButton medicineButton;
    private JButton treatmentButton;
    private JMenuBar menuBar;
    private JMenu menu1 = new JMenu("Doctor Procedures");
    private JMenuItem menu1Item1 = new JMenuItem("Edit");
    private JMenuItem menu1Item2 = new JMenuItem("View");
    private JMenu menu2 = new JMenu("Patient Procedures");
    private JMenuItem menu2Item2 = new JMenuItem("View");
    public AdminPage() {
        init();
    }
    private void init(){
        add(adminPagePanel);
        setTitle("Admin Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        menuBar = new JMenuBar();
        menuBar.add(menu1);
        menuBar.add(menu2);
        menu1.add(menu1Item1);
        menu1.add(menu1Item2);
        menu2.add(menu2Item2);
        setJMenuBar(menuBar);
        menu1Item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorProceduresEdit().setVisible(true);
            }
        });
        menu1Item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorProceduresView().setVisible(true);
            }
        });
        menu2Item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { new PatientPocedures().setVisible(true);}
        });
        medicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MedicineP().setVisible(true);
            }
        });

        treatmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { new TreatmentP().setVisible(true);}
        });
    }


}





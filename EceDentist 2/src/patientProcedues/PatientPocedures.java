package patientProcedues;
import entity.Appointment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class PatientPocedures extends JFrame {
    private JPanel viewPanel;
    private JTable table1;
    private JScrollPane vtablescroll;
    private JLabel patients;
    String[] columnNames = {"NAME", "GENDER", "PHONE", "TREATMENT", "DOCTOR"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    public PatientPocedures() {
        setSize(900, 600);
        setLocationRelativeTo(this);
        setContentPane(viewPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Patient Procedures");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        showPatients();}
    private List<Appointment> getAll() {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        List<Appointment> patientList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM appointments");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                patientList.add(new Appointment(
                        resultSet.getString("name"),
                        resultSet.getString("gender"),
                        resultSet.getInt("phone"),
                        resultSet.getString("treatment"),
                        resultSet.getString("doctor")));}
            return patientList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;}}
    public void showPatients() {
        List<Appointment> patientList = getAll();
        DefaultTableModel tbl = (DefaultTableModel) table1.getModel();
        tbl.setRowCount(0);
        for (Appointment appointment : patientList) {
            Object[] rowData = new Object[5];
            rowData[0] = appointment.getName();
            rowData[1] = appointment.getGender();
            rowData[2] = appointment.getPhone();
            rowData[3] = appointment.getTreatment();
            rowData[4] = appointment.getDoctor();
            tableModel.addRow(rowData);
        }
        table1.setModel(tableModel);}}

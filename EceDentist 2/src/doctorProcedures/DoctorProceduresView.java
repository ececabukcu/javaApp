package doctorProcedures;
import entity.Doctor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class DoctorProceduresView extends JFrame {
    private JPanel viewPanel;
    private JTable viewTable;
    private JScrollPane vtablescroll;
    private JLabel doctors;
    String[] columnNames = {"ID", "NAME", "PHONE", "GENDER", "DOCTOR SPECIALTY"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    public DoctorProceduresView() {
        setSize(900, 600);
        setLocationRelativeTo(this);
        setContentPane(viewPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Doctor Procedures");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        showDoctors();}
        private List<Doctor> getAll() {
            final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
            final String USERNAME = "root";
            final String PASSWORD = "";
            List<Doctor> doctorList = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM doctors");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    doctorList.add(new Doctor(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("phone"),
                            resultSet.getString("gender"),
                            resultSet.getString("doctor_specialty")));}
                return doctorList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;}}
        public void showDoctors() {
            List<Doctor> doctorList = getAll();
            DefaultTableModel tbl = (DefaultTableModel) viewTable.getModel();
            tbl.setRowCount(0);
            for (Doctor doctor : doctorList) {
                Object[] rowData = new Object[5];
                rowData[0] = doctor.getId();
                rowData[1] = doctor.getName();
                rowData[2] = doctor.getPhone();
                rowData[3] = doctor.getGender();
                rowData[4] = doctor.getDoctorSpecialty();
                tableModel.addRow(rowData);
            }
             viewTable.setModel(tableModel);}}


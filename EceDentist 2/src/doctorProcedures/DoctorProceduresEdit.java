package doctorProcedures;
import entity.Doctor;
import utils.DoctorUtils;
import utils.StringUtils;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
public class DoctorProceduresEdit extends JFrame {
    private JPanel dpedit1;
    private JPanel panel3;
    private JPanel panel4;
    private JTextField tfName;
    private JTextField tfPhone;
    private JRadioButton femaleRadioButton;
    private JRadioButton maleRadioButton;
    private JComboBox comboBox1;
    private JButton ADDButton;
    private JButton CLEARButton;
    private JButton UPDATEButton;
    private JButton SEARCHButton;
    private JTextField tfSearch;
    private JButton DELETEButton;
    private JTextField tfDelete;
    private JTable table1;
    private JComboBox searchCombo;
    private DoctorUtils doctorUtils = new DoctorUtils();
    private ButtonGroup buttonGroup = new ButtonGroup();
    String[] columnNames = {"id", "name", "phone", "gender", "doctorSpecialty"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    public DoctorProceduresEdit() {
        setSize(1000, 700);
        setLocationRelativeTo(this);
        setContentPane(dpedit1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Doctor Procedures");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buttonGroup.add(femaleRadioButton);
        buttonGroup.add(maleRadioButton);
        ADDButtonClick();
        showDoctors();
        DELETEButtonClick();
        UPDATEButtonClick();
        CLEARButtonClick();
        onTableSelect();
        SearchButtonClick();
    }
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
    private void ADDButtonClick() {
        ADDButton.addActionListener(e -> {
            saveDoctorList(new Doctor(
                    tfName.getText(),
                    doctorUtils.strToInteger(tfPhone.getText()),
                    getSelectedButtonText(buttonGroup),
                    comboBox1.getSelectedItem().toString()
            ));
            JOptionPane.showMessageDialog(rootPane, "inserted OK");
            showDoctors();});}
    private void DELETEButtonClick() {
        DELETEButton.addActionListener(e -> {
            int id = doctorUtils.strToInteger(tfDelete.getText());
            delete(id);
            JOptionPane.showMessageDialog(rootPane, "Delete is  OK");
            showDoctors();});}
    private void UPDATEButtonClick() {
        UPDATEButton.addActionListener(e -> {
            update(new Doctor(
                    doctorUtils.strToInteger(tfDelete.getText()),
                    tfName.getText(),
                    doctorUtils.strToInteger(tfPhone.getText()),
                    getSelectedButtonText(buttonGroup),
                    comboBox1.getSelectedItem().toString()
            ));});}
    public void CLEARButtonClick() {
        CLEARButton.addActionListener(e -> {
            clear();
            showDoctors();
        });}
    private void SearchButtonClick() {
        SEARCHButton.addActionListener(e -> {
            String selectedSearchCombo = searchCombo.getSelectedItem().toString();
            if(!StringUtils.hasText(tfSearch.getText())){
                showDoctors();
                return;}
            if(selectedSearchCombo.isEmpty()){
                JOptionPane.showMessageDialog(rootPane, "Please select search filter");
                return;}
            List<Doctor> doctorList = search(selectedSearchCombo, tfSearch.getText());
            if(doctorList == null){
                return;}
            DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
            tableModel.setRowCount(0);
            for (Doctor doctor : doctorList) {
                Object[] rowData = new Object[5];
                rowData[0] = doctor.getId();
                rowData[1] = doctor.getName();
                rowData[2] = doctor.getPhone();
                rowData[3] = doctor.getGender();
                rowData[4] = doctor.getDoctorSpecialty();
                tableModel.addRow(rowData);
            }
            table1.setModel(tableModel);});}
    private void saveDoctorList(Doctor doctor) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO dentistsystem.doctors (name,phone,gender,doctor_specialty) values (?,?,?,?)");
            preparedStatement.setString(1, doctor.getName());
            preparedStatement.setInt(2, doctor.getPhone());
            preparedStatement.setString(3, doctor.getGender());
            preparedStatement.setString(4, doctor.getDoctorSpecialty());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();}}
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
    private void delete(int id) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM doctors WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();}}
    private void update(Doctor doctor) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        boolean hasError = false;
        if(!StringUtils.hasText(doctor.getName())){
            JOptionPane.showMessageDialog(rootPane, "Doctor name cannot be empty");
            hasError = true;
        }else if(!StringUtils.hasText(doctor.getPhone()== null? "" : doctor.getPhone().toString())){
            JOptionPane.showMessageDialog(rootPane, "Doctor phone cannot be empty");
            hasError = true;
        }else if(!StringUtils.hasText(doctor.getGender())){
            JOptionPane.showMessageDialog(rootPane, "Doctor gender cannot be empty");
            hasError = true;
        }else if(!StringUtils.hasText(doctor.getDoctorSpecialty())){
            JOptionPane.showMessageDialog(rootPane, "Doctor Specialty cannot be empty");
            hasError = true;
        }
        if (hasError) {
            return;}
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE doctors SET name=?, phone=?, gender=?, doctor_specialty=? where id=?");
            preparedStatement.setString(1, doctor.getName());
            preparedStatement.setInt(2, doctor.getPhone() == null ? 0 : doctor.getPhone());
            preparedStatement.setString(3, doctor.getGender());
            preparedStatement.setString(4, doctor.getDoctorSpecialty());
            preparedStatement.setInt(5, doctor.getId());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(rootPane, "Update OK");
            showDoctors();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
            e.printStackTrace();}}
    private void clear() {
        tfName.setText("");
        tfPhone.setText("");
        buttonGroup.clearSelection();
        comboBox1.setSelectedIndex(0);
    }
    private List<Doctor> search(String selectedSearchCombo, String searchText) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        String query = "";
        if(selectedSearchCombo.equals("gender") || selectedSearchCombo.equals("id")) {
            query = "SELECT * FROM doctors WHERE " + selectedSearchCombo + " = '" + searchText + "'";
        } else {
            query = "SELECT * FROM doctors WHERE " + selectedSearchCombo
                    + " LIKE '%" + searchText + "%'";}
        List<Doctor> doctorList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
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
        DefaultTableModel tbl = (DefaultTableModel) table1.getModel();
        tbl.setRowCount(0);
            for (Doctor doctor : doctorList) {
                Object[] rowData = new Object[5];
                rowData[0] = doctor.getId();
                rowData[1] = doctor.getName();
                rowData[2] = doctor.getPhone();
                rowData[3] = doctor.getGender();
                rowData[4] = doctor.getDoctorSpecialty();
                tableModel.addRow(rowData);}
            table1.setModel(tableModel);}
        private void onTableSelect() {
            table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = table1.getSelectedRow();
                        if (selectedRow != -1) {
                            String id = table1.getValueAt(selectedRow, 0).toString();
                            String name =  table1.getValueAt(selectedRow, 1).toString();
                            String phone =  table1.getValueAt(selectedRow, 2).toString();
                            String gender = table1.getValueAt(selectedRow, 3).toString();
                            String doctorSpecialty = table1.getValueAt(selectedRow, 4).toString();
                            tfDelete.setText(id);
                            tfName.setText(name);
                            tfPhone.setText(phone);
                            if(gender.equals("Male")) {
                                maleRadioButton.setSelected(true);
                            } else {
                                femaleRadioButton.setSelected(true);}
                            comboBox1.setSelectedItem(doctorSpecialty);}}}});}}

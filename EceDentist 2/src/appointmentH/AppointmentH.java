package appointmentH;
import entity.Appointment;
import entity.Doctor;
import entity.Treatment;
import utils.DoctorUtils;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
public class AppointmentH extends JFrame {
    private JPanel appointmentPanel;
    private JTextField nameField;
    private JComboBox treatmentComboBox;
    private JComboBox doctorComboBox;
    private JButton saveAppointmentButton;
    private JRadioButton femaleRadioButton;
    private JRadioButton maleRadioButton;
    private javax.swing.JLabel treatmentPriceLabel;
    private JTextField tfPhone;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private DoctorUtils doctorUtils = new DoctorUtils();
    public AppointmentH() {
        setSize(700, 700);
        setLocationRelativeTo(this);
        setContentPane(appointmentPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("AppointmentH");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        buttonGroup.add(femaleRadioButton);
        buttonGroup.add(maleRadioButton);
        treatmentComboBox.addActionListener(e -> {
            fillDoctorComboBox();
            showTreatmentPrice();});
        fillDoctorComboBox();
        fillTreatmentComboBox();
        saveAppointmentClick();}
    private void showTreatmentPrice() {
        String selectedTreatment = treatmentComboBox.getSelectedItem().toString();
        int treatmentPrice = getTreatmentPrice(selectedTreatment);
        treatmentPriceLabel.setText(treatmentPrice + " $");}
    private int getTreatmentPrice(String treatmentName) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT treatment_price FROM treatments WHERE treatment_name = ?");
            preparedStatement.setString(1, treatmentName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("treatment_price");}
        } catch (Exception e) {
            e.printStackTrace();}
        return 0;}
    private void fillTreatmentComboBox() {
        List<Treatment> treatmentList = getTreatments();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Treatment treatment : treatmentList) {
            model.addElement(treatment.getTreatmentName());}
        treatmentComboBox.setModel(model);}
    private List<Treatment> getTreatments() {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        List<Treatment> treatmentList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM treatments");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                treatmentList.add(new Treatment(
                        resultSet.getString("treatment_name"),
                        resultSet.getInt("treatment_price")));}
            return treatmentList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;}}
    private void fillDoctorComboBox() {
        if (treatmentComboBox.getSelectedItem() != null) {
            String selectedTreatment = treatmentComboBox.getSelectedItem().toString();
            List<Doctor> doctorList = getDoctorsBySpecialty(selectedTreatment);
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (Doctor doctor : doctorList) {
                model.addElement(doctor.getName());}
            doctorComboBox.setModel(model);}}
    private List<Doctor> getDoctorsBySpecialty(String specialty) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        List<Doctor> doctorList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM doctors WHERE doctor_specialty = ?");
            preparedStatement.setString(1, specialty);
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
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();}}
        return null;}
    private void saveAppointmentClick() {
        saveAppointmentButton.addActionListener(e -> {
            saveAppointmentList(new Appointment(
                    nameField.getText(),
                    getSelectedButtonText(buttonGroup),
                    doctorUtils.strToInteger(tfPhone.getText()),
                    treatmentComboBox.getSelectedItem().toString(),
                    doctorComboBox.getSelectedItem().toString()
            ));
            JOptionPane.showMessageDialog(rootPane, "Your appointment has been saved. We will call you back to set the date and time.");});}
    private void saveAppointmentList(Appointment appointment) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO dentistsystem.appointments (name,gender,phone,treatment,doctor) values (?,?,?,?,?)");
            preparedStatement.setString(1, appointment.getName());
            preparedStatement.setString(2, appointment.getGender());
            preparedStatement.setInt(3, appointment.getPhone());
            preparedStatement.setString(4, appointment.getTreatment());
            preparedStatement.setString(5, appointment.getDoctor());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();}}}





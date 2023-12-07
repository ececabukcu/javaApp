package adminButtons.TreatmentP;
import entity.Treatment;
import utils.DoctorUtils;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class TreatmentP extends JFrame{
    private JTable treatmentTable;
    private JTextField tfChangePrice;
    private JButton changeButton;
    private JPanel treatP;
    private JComboBox comboBoxT;
    private DoctorUtils doctorUtils = new DoctorUtils();
    String[] columnNames = {"TREATMENT NAME", "TREATMENT PRICE"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    public TreatmentP(){

        setSize(1000, 700);
        setLocationRelativeTo(this);
        setContentPane(treatP);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Treatments");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        showTreatments();
        CHANGEButtonClick();
        onTableSelect();
    }
    private void CHANGEButtonClick() {
        changeButton.addActionListener(e -> update(new Treatment(
                comboBoxT.getSelectedItem().toString(),
                doctorUtils.strToInteger(tfChangePrice.getText()))));}

    private List<Treatment> getAll() {
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
                        resultSet.getInt("treatment_price")
                ));
            }
            return treatmentList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showTreatments() {
        List<Treatment> treatmentList = getAll();
        DefaultTableModel tbl = (DefaultTableModel) treatmentTable.getModel();
        tbl.setRowCount(0);
        for (Treatment treatment : treatmentList) {
            Object[] rowData = new Object[2];
            rowData[0] = treatment.getTreatmentName();
            rowData[1] = treatment.getTreatmentPrice();
            tableModel.addRow(rowData);}
        treatmentTable.setModel(tableModel);}
    private void update(Treatment treatment) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE treatments SET treatment_price=? WHERE treatment_name=?");
            preparedStatement.setString(1, treatment.getTreatmentName());
            preparedStatement.setInt(2, treatment.getTreatmentPrice() == null ? 0 : treatment.getTreatmentPrice());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Change OK");
            showTreatments();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
            e.printStackTrace();}}
    private void onTableSelect() {
        treatmentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = treatmentTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String medicineName = treatmentTable.getValueAt(selectedRow, 0).toString();
                        String medicinePrice =  treatmentTable.getValueAt(selectedRow, 1).toString();
                        comboBoxT.setSelectedItem(medicineName);
                        tfChangePrice.setText(medicinePrice);}}}});}
}



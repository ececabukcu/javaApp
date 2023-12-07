package medicineH;
import buyNowH.BuyNowH;
import entity.Medicine;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
public class MedicineH extends JFrame {
    private JTable medcinebuyTable;
    private JTextField tfmedicineHName;
    private JTextField tfMedicineHPrice;
    private JButton BUYNOWButton;
    private JPanel medicineHPanel;
    String[] columnNames = {"MEDICINE NAME", "MEDICINE PRICE"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    public MedicineH(){
        setSize(700, 700);
        setLocationRelativeTo(this);
        setContentPane(medicineHPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Medicines");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        showMedicines();
        onTableSelect();
        BUYNOWButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BuyNowH().setVisible(true);
            }});}
    private List<Medicine> getAll() {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        List<Medicine> medicinePList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM medicines");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                medicinePList.add(new Medicine(
                        resultSet.getString("medicine"),
                        resultSet.getInt("price")));}
            return medicinePList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;}}
    public void showMedicines() {
        List<Medicine> medicinePList = getAll();
        DefaultTableModel tbl = (DefaultTableModel) medcinebuyTable.getModel();
        tbl.setRowCount(0);
        for (Medicine medicine : medicinePList) {
            Object[] rowData = new Object[2];
            rowData[0] = medicine.getMedicine();
            rowData[1] = medicine.getPrice();
            tableModel.addRow(rowData);}
        medcinebuyTable.setModel(tableModel);}
    private void onTableSelect() {
        medcinebuyTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = medcinebuyTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String medicineName = medcinebuyTable.getValueAt(selectedRow, 0).toString();
                        String medicinePrice =  medcinebuyTable.getValueAt(selectedRow, 1).toString();
                        tfmedicineHName.setText(medicineName);
                        tfMedicineHPrice.setText(medicinePrice);}}}});}}



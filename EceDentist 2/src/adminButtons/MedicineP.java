package adminButtons;
import entity.Medicine;
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
import java.util.List;
public class MedicineP extends JFrame {
    private JPanel medicinePanel;
    private JTable medicineTable;
    private JButton addMedicineButton;
    private JButton deleteMedicineButton;
    private JTextField tfMPrice;
    private JTextField tfMName;
    private JLabel medicinePrice;
    private JLabel medicineName;
    private JPanel panel2;
    private JPanel panel1;
    private JTable medicinesTable;
    private JLabel medicines;
    private DoctorUtils doctorUtils = new DoctorUtils();
    String[] columnNames = {"MEDICINE NAME", "MEDICINE PRICE"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    public MedicineP(){
        setSize(700, 700);
        setLocationRelativeTo(this);
        setContentPane(medicinePanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Medicines");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        showMedicines();
        AddMedicineButtonClick();
        deleteMedicineButtonClick();
        onTableSelect();}
    private void AddMedicineButtonClick() {
        addMedicineButton.addActionListener(e -> {
            addMedicine(new Medicine(
                    tfMName.getText(),
                    doctorUtils.strToInteger(tfMPrice.getText())
            ));
            showMedicines();});}
    private void deleteMedicineButtonClick() {
        deleteMedicineButton.addActionListener(e -> {
            deleteMedicine(tfMName.getText());
            showMedicines();});}
    private void addMedicine(Medicine medicine) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        boolean hasError = false;
        if(!StringUtils.hasText(medicine.getMedicine())){
            JOptionPane.showMessageDialog(rootPane, "Medicine name cannot be empty");
            hasError = true;
        }else if(!StringUtils.hasText(medicine.getPrice()== null? "" : medicine.getPrice().toString())) {
            JOptionPane.showMessageDialog(rootPane, "Medicine price cannot be empty");
            hasError = true;
        }if (hasError) {
            return;}
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO dentistsystem.medicines (medicine,price) values (?,?)");
            preparedStatement.setString(1, medicine.getMedicine());
            preparedStatement.setInt(2, medicine.getPrice());
            preparedStatement.execute();
            JOptionPane.showMessageDialog(rootPane, "inserted OK");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "This medicine is registered in the system!");
            e.printStackTrace();}}
    private void deleteMedicine(String medicine) {
        final String DB_URL = "jdbc:mysql://localhost:3306/dentistsystem";
        final String USERNAME = "root";
        final String PASSWORD = "";
        boolean hasError = false;
        if(!StringUtils.hasText(medicine.intern())){
            JOptionPane.showMessageDialog(rootPane, "Medicine name cannot be empty");
            hasError = true;
        }else if(hasError){
            JOptionPane.showMessageDialog(rootPane, "Delete is  OK");
        return;}
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM medicines WHERE medicine = ?");
            preparedStatement.setString(1, medicine);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();}}
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
        DefaultTableModel tbl = (DefaultTableModel) medicinesTable.getModel();
        tbl.setRowCount(0);
        for (Medicine medicine : medicinePList) {
            Object[] rowData = new Object[2];
            rowData[0] = medicine.getMedicine();
            rowData[1] = medicine.getPrice();
            tableModel.addRow(rowData);}
        medicinesTable.setModel(tableModel);}
    private void onTableSelect() {
        medicinesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = medicinesTable.getSelectedRow();

                    if (selectedRow != -1) {
                        String medicineName = medicinesTable.getValueAt(selectedRow, 0).toString();
                        String medicinePrice =  medicinesTable.getValueAt(selectedRow, 1).toString();
                        tfMName.setText(medicineName);
                        tfMPrice.setText(medicinePrice);}}}});}}



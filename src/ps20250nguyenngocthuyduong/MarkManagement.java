package ps20250nguyenngocthuyduong;

import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import ps20250nguyenngocthuyduong.customcomponent.MyDialog;
import ps20250nguyenngocthuyduong.dao.GradeDAO;
import ps20250nguyenngocthuyduong.dao.GradeDAOImpl;
import ps20250nguyenngocthuyduong.dao.StudentDAO;
import ps20250nguyenngocthuyduong.dao.StudentDAOImpl;
import ps20250nguyenngocthuyduong.dao.TestDAO;
import ps20250nguyenngocthuyduong.dao.TestDAOImpl;
import ps20250nguyenngocthuyduong.models.Grade;
import ps20250nguyenngocthuyduong.utils.AddPlaceholder;
import ps20250nguyenngocthuyduong.models.LoggedInUser;
import ps20250nguyenngocthuyduong.models.Test;
import ps20250nguyenngocthuyduong.utils.ClearComponent;
import ps20250nguyenngocthuyduong.utils.Export;
import ps20250nguyenngocthuyduong.utils.ImageResizing;
import ps20250nguyenngocthuyduong.utils.Validator;

public class MarkManagement extends javax.swing.JFrame {

    /**
    * The list of Grade objects currently displayed in the table.
    */
    ArrayList<Grade> lGrade = new ArrayList<>(); //List Grade
    
    /**
    * The table model used to display the Grade objects.
    */
    DefaultTableModel model;
    
    /**
    * The data access object for managing Grade objects.
    */
    GradeDAO gradeDAO = new GradeDAOImpl();
    
    /**
    * The data access object for managing Student objects.
    */
    StudentDAO studentDAO = new StudentDAOImpl();
    
    /**
    * The data access object for managing Test objects.
    */
    TestDAO testDAO = new TestDAOImpl();
    
    /**
    * The index of the selected row in the table.
    */
    int index = -1;
    
    
    
    public MarkManagement() {
        initComponents();
        setLocationRelativeTo(null);

        //chi dong frame nay chu khong dong frame chinh
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //add placeholder
        AddPlaceholder.addPlaceholderText(txtTop, "Top...");
        AddPlaceholder.addPlaceholderText(txtSearch, "Tìm...");

        //table
        //tao model
        model = new DefaultTableModel();

        // Set the table model to the tblMark table
        tblMark.setModel(model);
        
        //disable table editing
        tblMark.setDefaultEditor(Object.class, null); 
        
        //table header
        String [] colNames = {"Mã SV", "Họ Tên", "Mã Test", "Tiếng Anh", "Tin học", "GDTC", "Điểm TB"};
        model.setColumnIdentifiers(colNames);

        //column width
        tblMark.getColumnModel().getColumn(0).setPreferredWidth(90);
        tblMark.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblMark.getColumnModel().getColumn(2).setPreferredWidth(90);
        tblMark.getColumnModel().getColumn(3).setPreferredWidth(120);
        tblMark.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblMark.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblMark.getColumnModel().getColumn(6).setPreferredWidth(100);

        //table data
        fillToTable();

        //combobox MaTest
        cboTest.removeAllItems();
        cboTest.addItem("View all");
        for(Test t : (ArrayList<Test>)testDAO.getAll()) {
            cboTest.addItem(t.getTestID());
        }
        
        tblMark.requestFocus();
    }
    
    
    
    //XAY DUNG CAC HAM
    
    /**
    * Selects a row in the table with the given index i and shows its detail in the corresponding text fields.
    * If i is negative or the table model is empty, this method has no effect.
    * 
    * @param i the index of the row to be selected
    */
    public void selectRow(int i) {
        if(i >= 0) {
            index = i;
            tblMark.setRowSelectionInterval(index, index);
            showDetail();
            //scroll toi dong duoc chon
            tblMark.scrollRectToVisible(new Rectangle(tblMark.getCellRect(index, 0, true))); 
        }
    } 
        

    
    /**
    * Fills the table with all grades in the database.
    * Clears the existing rows in the table and adds each grade as a new row.
    */
    public void fillToTable() {
        lGrade = (ArrayList<Grade>) gradeDAO.getAll();
        
        model.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(lGrade != null) {
            for(Grade g : (ArrayList<Grade>) gradeDAO.getAll()) {
                model.addRow(new Object[] {
                    g.getStudentID(), 
                    studentDAO.getByID(g.getStudentID()).getStudentName(), 
                    g.getTestID(), 
                    g.getTAGrade() == null ? "" : String.format("%.2f", g.getTAGrade()), 
                    g.getTinHocGrade() == null ? "" : String.format("%.2f", g.getTinHocGrade()), 
                    g.getGDTCGrade() == null ? "" : String.format("%.2f", g.getGDTCGrade()),
                    String.format("%.2f", g.getDTB())});
            }
        }
    }
    
    
    
    /**
    * This method fills data from ArrayList lGrade into the table tblMark.
    * @param list the ArrayList of Grade objects to fill the table with
    */
    public void fillToTable(List<Grade> list) {
        model.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(list != null) {
            for(Grade g : list) {
                model.addRow(new Object[] {
                    g.getStudentID(), 
                    studentDAO.getByID(g.getStudentID()).getStudentName(), 
                    g.getTestID(), 
                    g.getTAGrade() != null ? String.format("%.2f", g.getTAGrade()) : "" , 
                    g.getTinHocGrade()!= null ? String.format("%.2f", g.getTinHocGrade()) : "" , 
                    g.getGDTCGrade() != null ? String.format("%.2f", g.getGDTCGrade()) : "" ,
                    String.format("%.2f", g.getDTB())});
            }
        }
    }
    
    
    
    /**
    * This method returns the index of the Grade object with the given testID and studentID in the lGrade ArrayList.
    * @param testID the test ID to search for
    * @param studentID the student ID to search for
    * @return the index of the matching Grade object in lGrade, or -1 if no match is found
    */
    public Integer findGradeIndex(String testID, String studentID) {
        for(Grade g : lGrade) {
            if(g.getTestID().equalsIgnoreCase(testID) && g.getStudentID().equalsIgnoreCase(studentID)) {
                return lGrade.indexOf(g);
            }
        }
        return -1;
    }
    
    /**
    * Gets the top grades containing the specified test ID and search text,
    * limited by the specified number of results.
    * 
    * @see #getTopTestIDContain()
    */
    public void showDetail() {
        Grade g = new Grade();
        
        //lay maSV va maTest cua hang duoc chon
        String maTest = tblMark.getValueAt(tblMark.getSelectedRow(), 2).toString();
        String maSV = tblMark.getValueAt(tblMark.getSelectedRow(), 0).toString();
        
        //tim grade co maSV va maTest trong lGrade
        g = lGrade.get(findGradeIndex(maTest, maSV));
        
        //do du lieu tu Grade g len form
        lblStudentName.setText(studentDAO.getByID(g.getStudentID()).getStudentName());
        lblStudentID.setText(g.getStudentID());
        txtTAGrade.setText(g.getTAGrade() != null ? String.format("%.2f", g.getTAGrade()) : "");
        txtTinHocGrade.setText(g.getTinHocGrade()!= null ? String.format("%.2f", g.getTinHocGrade()) : "");
        txtGDTCGrade.setText(g.getGDTCGrade()!= null ? String.format("%.2f", g.getGDTCGrade()) : "");
        lblDTB.setText(String.format("%.2f", g.getDTB()));
        
        lblAnh.setIcon(ImageResizing.resizing("students/" + g.getStudentID() + ".png", lblAnh.getWidth(), lblAnh.getHeight()));
    }
    
    
    
    /**
    * This method clears the form by resetting the selected index in the table and clearing the input fields.
    */
    public void clearForm() {
        index = -1;
        tblMark.getSelectionModel().clearSelection(); //bo chon tren table
        ClearComponent.clear(txtTAGrade, txtTinHocGrade, txtGDTCGrade, lblDTB);
    }
    
    
    
    /**
    * Retrieves a list of Grade objects from the database with a specified maximum number of test IDs
    * and an optional search text, based on the values entered in the text fields and combo boxes on the form.
    * The list is then assigned to the lGrade attribute of the class.
    */
    public void getTopTestIDContain() {
        String topText = txtTop.getText();
        Integer top = -1;

        if (!topText.isEmpty() && !topText.equalsIgnoreCase("Top...")) {
            try {
                top = Integer.parseInt(topText);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        String searchText = (!txtSearch.getText().isEmpty() && !txtSearch.getText().equalsIgnoreCase("Tìm...")) ? txtSearch.getText() : null;
        String selectedTestID = (cboTest.getSelectedItem().toString().equals("View all")) ? null : cboTest.getSelectedItem().toString();

        // call the method with the top parameter
        lGrade = (ArrayList<Grade>) gradeDAO.getTopTestIDContain(top, selectedTestID, searchText);
    }
    
    
    
    /**
    * Refreshes the contents of the table based on the current filter settings.
    * This method first calls the getTopTestIDContain() method to retrieve the list of Grade objects,
    * and then passes this list to the fillToTable() method to update the contents of the table.
    */
    public void stateChangedFilter() {
        getTopTestIDContain();
        fillToTable(lGrade);
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        pnlTitleBar = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        lblResize = new javax.swing.JLabel();
        lblMinimize = new javax.swing.JLabel();
        lblTitleBarName = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblFirst = new javax.swing.JLabel();
        lblPrevious = new javax.swing.JLabel();
        lblNext = new javax.swing.JLabel();
        lblLast = new javax.swing.JLabel();
        txtTop = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        txtSearch = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        btnSave = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnNew = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnUpdate = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnDelete = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMark = new ps20250nguyenngocthuyduong.customcomponent.MyTable();
        pnlDetail = new javax.swing.JPanel();
        lblHoTenSV2 = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblStudentName = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblStudentID = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblMaSV2 = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblTiengAnh = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblTinHoc = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblGDTC = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblDTB = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblDiemTB2 = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblAnh = new javax.swing.JLabel();
        txtTAGrade = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        txtTinHocGrade = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        txtGDTCGrade = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        lblTitleTab1 = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblMaTest = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        cboTest = new ps20250nguyenngocthuyduong.customcomponent.MyComboBox();
        btnManagementTest = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnPrintTable = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnPrintStudent = new ps20250nguyenngocthuyduong.customcomponent.MyButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlMain.setBackground(new java.awt.Color(255, 255, 255));
        pnlMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(155, 155, 155), 2));

        pnlTitleBar.setBackground(new java.awt.Color(235, 235, 235));
        pnlTitleBar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlTitleBar.setPreferredSize(new java.awt.Dimension(898, 40));

        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ps20250nguyenngocthuyduong/resources/images/icons/close.png"))); // NOI18N
        lblClose.setToolTipText("Close");
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblCloseMousePressed(evt);
            }
        });

        lblResize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ps20250nguyenngocthuyduong/resources/images/icons/resize.png"))); // NOI18N
        lblResize.setToolTipText("Resize");
        lblResize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblResize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblResizeMouseClicked(evt);
            }
        });

        lblMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ps20250nguyenngocthuyduong/resources/images/icons/minimize.png"))); // NOI18N
        lblMinimize.setToolTipText("Minimize");
        lblMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizeMouseClicked(evt);
            }
        });

        lblTitleBarName.setText("Mark Management");
        lblTitleBarName.setFont(new java.awt.Font("Source Code Pro Medium", 1, 14)); // NOI18N

        lblFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ps20250nguyenngocthuyduong/resources/images/icons/first.png"))); // NOI18N
        lblFirst.setToolTipText("First");
        lblFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFirst.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblFirstMousePressed(evt);
            }
        });

        lblPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ps20250nguyenngocthuyduong/resources/images/icons/prev.png"))); // NOI18N
        lblPrevious.setToolTipText("Previous");
        lblPrevious.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblPrevious.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblPreviousMousePressed(evt);
            }
        });

        lblNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ps20250nguyenngocthuyduong/resources/images/icons/next.png"))); // NOI18N
        lblNext.setToolTipText("Next");
        lblNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblNextMousePressed(evt);
            }
        });

        lblLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ps20250nguyenngocthuyduong/resources/images/icons/last.png"))); // NOI18N
        lblLast.setToolTipText("Last");
        lblLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLast.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblLastMousePressed(evt);
            }
        });

        txtTop.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtTop.setToolTipText("Chỉ được nhập số");
        txtTop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTopKeyReleased(evt);
            }
        });

        txtSearch.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlTitleBarLayout = new javax.swing.GroupLayout(pnlTitleBar);
        pnlTitleBar.setLayout(pnlTitleBarLayout);
        pnlTitleBarLayout.setHorizontalGroup(
            pnlTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblNext, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblLast, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTop, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
                .addComponent(lblTitleBarName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lblMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblResize, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        pnlTitleBarLayout.setVerticalGroup(
            pnlTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleBarLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLast, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNext, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblResize, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTitleBarName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        btnSave.setBackground(new java.awt.Color(17, 164, 40));
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(12, 109, 26)));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnNew.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNew.setText("Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnUpdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(208, 23, 45));
        btnDelete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 17, 33)));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tblMark.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblMarkMousePressed(evt);
            }
        });
        tblMark.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblMarkKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblMark);

        pnlDetail.setBackground(new java.awt.Color(255, 255, 255));
        pnlDetail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)));

        lblHoTenSV2.setForeground(new java.awt.Color(141, 141, 141));
        lblHoTenSV2.setText("Họ tên SV:");

        lblStudentName.setText("Nguyễn Văn Anh");

        lblStudentID.setText("PS001");

        lblMaSV2.setForeground(new java.awt.Color(141, 141, 141));
        lblMaSV2.setText("Mã SV:");

        lblTiengAnh.setForeground(new java.awt.Color(141, 141, 141));
        lblTiengAnh.setText("Tiếng Anh");

        lblTinHoc.setForeground(new java.awt.Color(141, 141, 141));
        lblTinHoc.setText("Tin Học");

        lblGDTC.setForeground(new java.awt.Color(141, 141, 141));
        lblGDTC.setText("GDTC");

        lblDTB.setText("8.2");

        lblDiemTB2.setForeground(new java.awt.Color(141, 141, 141));
        lblDiemTB2.setText("Điểm TB:");

        lblAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ps20250nguyenngocthuyduong/resources/images/students/st001.png"))); // NOI18N
        lblAnh.setToolTipText("Ảnh");
        lblAnh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtTAGrade.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtTAGrade.setText("8.5");
        txtTAGrade.setNextFocusableComponent(txtTinHocGrade);

        txtTinHocGrade.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtTinHocGrade.setText("7.0");
        txtTinHocGrade.setNextFocusableComponent(txtGDTCGrade);

        txtGDTCGrade.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtGDTCGrade.setText("9.0");

        javax.swing.GroupLayout pnlDetailLayout = new javax.swing.GroupLayout(pnlDetail);
        pnlDetail.setLayout(pnlDetailLayout);
        pnlDetailLayout.setHorizontalGroup(
            pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetailLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTinHoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGDTC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiemTB2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblHoTenSV2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTiengAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMaSV2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlDetailLayout.createSequentialGroup()
                        .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDetailLayout.createSequentialGroup()
                                .addComponent(lblStudentID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(30, 30, 30))
                            .addGroup(pnlDetailLayout.createSequentialGroup()
                                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblDTB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTinHocGrade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(txtGDTCGrade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetailLayout.createSequentialGroup()
                                .addComponent(txtTAGrade, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                .addGap(20, 20, 20)))
                        .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblStudentName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        pnlDetailLayout.setVerticalGroup(
            pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetailLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHoTenSV2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlDetailLayout.createSequentialGroup()
                        .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaSV2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTiengAnh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTAGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTinHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTinHocGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGDTC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGDTCGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(pnlDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDiemTB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDTB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        lblTitleTab1.setText("Details");
        lblTitleTab1.setFont(new java.awt.Font("Source Code Pro Medium", 1, 14)); // NOI18N

        lblMaTest.setText("Mã Test:");

        cboTest.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 2, 1, 2)));
        cboTest.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTestItemStateChanged(evt);
            }
        });

        btnManagementTest.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnManagementTest.setText("Management Test");
        btnManagementTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManagementTestActionPerformed(evt);
            }
        });

        btnPrintTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPrintTable.setText("Print Table");
        btnPrintTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintTableActionPerformed(evt);
            }
        });

        btnPrintStudent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPrintStudent.setText("Print this Student");
        btnPrintStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintStudentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1016, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblTitleTab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pnlDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                                .addComponent(btnPrintStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblMaTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cboTest, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnManagementTest, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnPrintTable, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTest, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnManagementTest, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(lblTitleTab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrintStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addComponent(btnPrintTable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCloseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMousePressed
        dispose();
    }//GEN-LAST:event_lblCloseMousePressed

    private void lblResizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblResizeMouseClicked
        if (getExtendedState() == MAXIMIZED_BOTH) setExtendedState(NORMAL) ;
        else setExtendedState(MAXIMIZED_BOTH);
    }//GEN-LAST:event_lblResizeMouseClicked

    private void lblMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMouseClicked
        setState(ICONIFIED);
    }//GEN-LAST:event_lblMinimizeMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        selectRow(0);
    }//GEN-LAST:event_formWindowOpened

    private void tblMarkMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMarkMousePressed
        selectRow(tblMark.getSelectedRow());
    }//GEN-LAST:event_tblMarkMousePressed

    private void lblFirstMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFirstMousePressed
        if(model.getRowCount() > 0) {
            selectRow(0);
        }
    }//GEN-LAST:event_lblFirstMousePressed

    private void lblPreviousMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPreviousMousePressed
        if(index > 0) {
            selectRow(--index);
        }
    }//GEN-LAST:event_lblPreviousMousePressed

    private void lblNextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNextMousePressed
        if(index < model.getRowCount() - 1) {
            selectRow(++index);
        }
    }//GEN-LAST:event_lblNextMousePressed

    private void lblLastMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLastMousePressed
        if(model.getRowCount() > 0) {
            selectRow(model.getRowCount() - 1);
        }
    }//GEN-LAST:event_lblLastMousePressed

    private void tblMarkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblMarkKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_UP) {
            evt.consume();
            
            if(index > 0) {
                selectRow(--index);
            }
        }
        else if(evt.getKeyCode() == KeyEvent.VK_DOWN) {
            evt.consume();
            
            if(index < model.getRowCount() - 1) {
                selectRow(++index);
            }
        }
    }//GEN-LAST:event_tblMarkKeyPressed

    private void txtTopKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTopKeyReleased
        stateChangedFilter();
        if(model.getRowCount() > 0) selectRow(0);
    }//GEN-LAST:event_txtTopKeyReleased

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        stateChangedFilter();
        if(model.getRowCount() > 0) selectRow(0);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
        txtTAGrade.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void cboTestItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTestItemStateChanged
        //vi khi chuyen tu TE001 sang TE002 se bao gom SELECTED va DESELECTED
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            stateChangedFilter();
            if(model.getRowCount() > 0) selectRow(0);
        }
    }//GEN-LAST:event_cboTestItemStateChanged

    private void btnManagementTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManagementTestActionPerformed
        new TestManagement().setVisible(true);
    }//GEN-LAST:event_btnManagementTestActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String testID = tblMark.getValueAt(tblMark.getSelectedRow(), 2).toString();
        String studentID = lblStudentID.getText();
        String tAGrade = txtTAGrade.getText();
        String tinHocGrade = txtTinHocGrade.getText();
        String gDTCGrade = txtGDTCGrade.getText();
        
        //validate
        ArrayList<String> errors = new ArrayList<>();

//        if(!Validator.isNotNull(null, testID, "View all")) errors.add("Vui lòng chọn Mã Test\n");
        errors.add(Validator.DoubleGrade0To10((JTextField)txtGDTCGrade, "Điểm GDTC", gDTCGrade, true, null));
        errors.add(Validator.DoubleGrade0To10((JTextField)txtTinHocGrade, "Điểm Tin Học", tinHocGrade, true, null));
        errors.add(Validator.DoubleGrade0To10((JTextField)txtTAGrade, "Điểm Tiếng Anh", tAGrade, true, null));
        
        Collections.reverse(errors);
        String e = "";
        for(String s : errors) e += s;
        
        //co loi
        if(!e.isEmpty()) {
            MyDialog.display(1, e);
            return;
        }
        
        //pre-add (update)
        if(gradeDAO.getByID(testID, studentID) == null) {
            //record chua ton tai
            MyDialog.display(1, "SV với mã này không tham gia bài Test.");
            return;
        }
          
        //add (update)
        int rowAffected = gradeDAO.updateByID(new Grade(testID, 
                                                        studentID, 
                                                        tAGrade != null && !tAGrade.isEmpty() ? Double.parseDouble(tAGrade) : null, 
                                                        tinHocGrade != null && !tinHocGrade.isEmpty() ? Double.parseDouble(tinHocGrade) : null, 
                                                        gDTCGrade != null && !gDTCGrade.isEmpty() ? Double.parseDouble(gDTCGrade) : null));
        
        if(rowAffected > 0) {
            //update thanh cong
//            fillToTable();
            stateChangedFilter();
            selectRow(findGradeIndex(testID, studentID));
        }
        else {
            //update that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String testID = tblMark.getValueAt(tblMark.getSelectedRow(), 2).toString();
        String studentID = lblStudentID.getText();
        String tAGrade = txtTAGrade.getText();
        String tinHocGrade = txtTinHocGrade.getText();
        String gDTCGrade = txtGDTCGrade.getText();
        
        //validate
        ArrayList<String> errors = new ArrayList<>();

//        if(!Validator.isNotNull(null, testID, "View all")) errors.add("Vui lòng chọn Mã Test\n");
        errors.add(Validator.DoubleGrade0To10((JTextField)txtGDTCGrade, "Điểm GDTC", gDTCGrade, true, null));
        errors.add(Validator.DoubleGrade0To10((JTextField)txtTinHocGrade, "Điểm Tin Học", tinHocGrade, true, null));
        errors.add(Validator.DoubleGrade0To10((JTextField)txtTAGrade, "Điểm Tiếng Anh", tAGrade, true, null));
        
        Collections.reverse(errors);
        String e = "";
        for(String s : errors) e += s;
        
        //co loi
        if(!e.isEmpty()) {
            MyDialog.display(1, e);
            return;
        }
        
        //pre-add (update)
        if(gradeDAO.getByID(testID, studentID) == null) {
            //record chua ton tai
            MyDialog.display(1, "SV với mã này không tham gia bài Test.");
            return;
        }
          
        //add (update)
        int rowAffected = gradeDAO.updateByID(new Grade(testID, 
                                                        studentID, 
                                                        tAGrade != null && !tAGrade.isEmpty() ? Double.parseDouble(tAGrade) : null, 
                                                        tinHocGrade != null && !tinHocGrade.isEmpty() ? Double.parseDouble(tinHocGrade) : null, 
                                                        gDTCGrade != null && !gDTCGrade.isEmpty() ? Double.parseDouble(gDTCGrade) : null));
        
        if(rowAffected > 0) {
            //update thanh cong
//            fillToTable();
            stateChangedFilter();
            selectRow(findGradeIndex(testID, studentID));
        }
        else {
            //update that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String testID = tblMark.getValueAt(tblMark.getSelectedRow(), 2).toString();
        String studentID = lblStudentID.getText();
        String tAGrade = txtTAGrade.getText();
        String tinHocGrade = txtTinHocGrade.getText();
        String gDTCGrade = txtGDTCGrade.getText();
        
        //pre-delete
        Grade gradeDB = gradeDAO.getByID(testID, studentID);
        String taDB = gradeDB.getTAGrade() == null ? "" : gradeDB.getTAGrade() + "";
        String thDB = gradeDB.getTinHocGrade()== null ? "" : gradeDB.getTinHocGrade()+ "";
        String gdtcDB = gradeDB.getGDTCGrade()== null ? "" : gradeDB.getGDTCGrade()+ "";
 
        if(gradeDAO.getByID(testID, studentID) == null) {
            MyDialog.display(1, "SV với bài Test này không tồn tại trong danh sách.");
            return;
        }
        else if(!taDB.equals(tAGrade) ||
                !thDB.equals(tinHocGrade) ||
                !gdtcDB.equals(gDTCGrade)) {
            MyDialog.display(1, "Thông tin của sinh viên trên form không khớp với CSDL.");
            return;
        }
          
        //delete
        int rowAffected = gradeDAO.deleteByID(testID, studentID);
        
        if(rowAffected > 0) {
            //delete thanh cong
//            fillToTable();
            stateChangedFilter();
            
            //xoa 1 dong cuoi
            if(lGrade.size() == 0) clearForm();
            //xoa dong cuoi
            else if(index == lGrade.size()) selectRow(index - 1);
            else selectRow(index);
        }
        else {
            //delete that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnPrintTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintTableActionPerformed
        Export.tableToPDF(tblMark, "DanhSachDiemCuaSinhVien");
    }//GEN-LAST:event_btnPrintTableActionPerformed

    private void btnPrintStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintStudentActionPerformed
        Export.panelToImage(pnlDetail, lblStudentID.getText() + "-" + cboTest.getSelectedItem().toString());
    }//GEN-LAST:event_btnPrintStudentActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MarkManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MarkManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MarkManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MarkManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Check login
                if (!LoggedInUser.isGiangVien()) {
                    new Login().setVisible(true);
                    MyDialog.display(1, "Bạn phải đăng nhập đúng Role để có thể truy cập chức năng này.");
                }
                else {
                    new MarkManagement().setVisible(true);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnDelete;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnManagementTest;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnNew;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnPrintStudent;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnPrintTable;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnSave;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnUpdate;
    private ps20250nguyenngocthuyduong.customcomponent.MyComboBox cboTest;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblClose;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblDTB;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblDiemTB2;
    private javax.swing.JLabel lblFirst;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblGDTC;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblHoTenSV2;
    private javax.swing.JLabel lblLast;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblMaSV2;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblMaTest;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JLabel lblNext;
    private javax.swing.JLabel lblPrevious;
    private javax.swing.JLabel lblResize;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblStudentID;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblStudentName;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTiengAnh;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTinHoc;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTitleBarName;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTitleTab1;
    private javax.swing.JPanel pnlDetail;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlTitleBar;
    private ps20250nguyenngocthuyduong.customcomponent.MyTable tblMark;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtGDTCGrade;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtSearch;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtTAGrade;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtTinHocGrade;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtTop;
    // End of variables declaration//GEN-END:variables
}

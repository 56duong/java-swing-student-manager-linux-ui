package ps20250nguyenngocthuyduong;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import ps20250nguyenngocthuyduong.customcomponent.MyDialog;
import ps20250nguyenngocthuyduong.dao.StudentDAO;
import ps20250nguyenngocthuyduong.dao.StudentDAOImpl;
import ps20250nguyenngocthuyduong.utils.AddPlaceholder;
import ps20250nguyenngocthuyduong.models.LoggedInUser;
import ps20250nguyenngocthuyduong.models.Student;
import ps20250nguyenngocthuyduong.utils.ClearComponent;
import ps20250nguyenngocthuyduong.utils.Export;
import ps20250nguyenngocthuyduong.utils.ImageResizing;
import ps20250nguyenngocthuyduong.utils.Validator;

public class StudentManagement extends javax.swing.JFrame {

    /**
    * The list of Student objects currently displayed in the table.
    */
    ArrayList<Student> lStudent = new ArrayList<>(); //List Student
    
    /**
    * The table model used to display the Student objects.
    */
    DefaultTableModel model;
    
    /**
    * The data access object for managing Student objects.
    */
    StudentDAO studentDAO = new StudentDAOImpl();
    
    /**
    * The index of the selected row in the table.
    */
    int index = -1;
    
    /**
    * The image of the currently selected student.
    */
    BufferedImage studentImage;
    
    
    
    public StudentManagement() {
        initComponents();
        setLocationRelativeTo(null);
        //chi dong frame nay chu khong dong frame chinh
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        //add placeholder
        AddPlaceholder.addPlaceholderText(txtSearch, "Tìm...");
        
        //table
        //tao model
        model = new DefaultTableModel();

        // Set the table model to the tblStudent table
        tblStudent.setModel(model);
        
        //disable table editing
        tblStudent.setDefaultEditor(Object.class, null); 
        
        //table header
        String [] colNames = {"Mã SV", "Họ Tên", "Email", "SDT", "Giới Tính", "Địa chỉ"};
        model.setColumnIdentifiers(colNames);

        //column width
        tblStudent.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblStudent.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblStudent.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblStudent.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblStudent.getColumnModel().getColumn(4).setPreferredWidth(30);
        tblStudent.getColumnModel().getColumn(5).setPreferredWidth(300);
        
        //table data
        fillToTable();
        
        tblStudent.requestFocus();
    }
    
    
    
    //XAY DUNG CAC HAM
    
    /**
    * Selects a row in the table with the given index i and shows its detail in the corresponding text fields.
    * If i is negative or the table model is empty, this method has no effect.
    * 
    * @param i the index of the row to be selected
    */
    public void selectRow(int i) {
        index = i;
        tblStudent.setRowSelectionInterval(index, index);
        showDetail();
        //scroll toi dong duoc chon
        tblStudent.scrollRectToVisible(new Rectangle(tblStudent.getCellRect(index, 0, true)));
        studentImage = null;
    } 
        

    
    /**
    * Fills the table with all students in the database.
    * Clears the existing rows in the table and adds each student as a new row.
    */
    public void fillToTable() {
        lStudent = (ArrayList<Student>) studentDAO.getAllContain(Validator.isNotNull(null, txtSearch.getText(), "Tìm...") ? txtSearch.getText() : null);
        
        model.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(lStudent != null) {
            for(Student s : lStudent) {
                model.addRow(new Object[] {s.getStudentID(), s.getStudentName(), s.getEmail(), s.getPhoneNumber(), s.getSex().equals("1") ? "Nam" : "Nữ", s.getAddress()});
            }
        }
    }
    
    
    
    //tim index cua Student trong lStudent bang studentID
    /**
     * Returns the index of the student with the given student ID in the lStudent ArrayList.
     * @param studentID the student ID to search for
     * @return the index of the matching student object in lStudent, or -1 if no match is found
     */
    public Integer findStudentIndex(String studentID) {
        for(Student s : lStudent) {
            if(s.getStudentID().equalsIgnoreCase(studentID)) {
                return lStudent.indexOf(s);
            }
        }
        return -1;
    }
    
    /**
     * Displays the details of the selected student in the form.
     */
    public void showDetail() {
        Student s = new Student();
        
        //lay MaSV trong cot dau tien cua hang duoc chon
        String maSV = tblStudent.getValueAt(tblStudent.getSelectedRow(), 0).toString();
        
        //tim sinh vien co maSV trong lStudent
        s = lStudent.get(findStudentIndex(maSV));
        
        //do du lieu tu Student s len form
        txtStudentID.setText(s.getStudentID());
        txtStudentName.setText(s.getStudentName());
        txtEmail.setText(s.getEmail());
        txtPhoneNumber.setText(s.getPhoneNumber());
        if(s.getSex().equals("1")) rdoMale.setSelected(true);
        else rdoFemale.setSelected(true);
        txtAddress.setText(s.getAddress());
        
        lblAnh.setIcon(ImageResizing.resizingStudentImage("students/" + s.getStudentID().toLowerCase() + ".png", lblAnh.getWidth() - 1, lblAnh.getHeight()));
    }
    
    
    
    /**
    * This method clears the form by resetting the selected index in the table and clearing the input fields, student image.
    */
    public void clearForm() {
        index = -1;
        tblStudent.getSelectionModel().clearSelection(); //bo chon tren table
        ClearComponent.clear(lblAnh, txtStudentID, txtStudentName, txtEmail, txtPhoneNumber, btgGioiTinh, txtAddress);
        studentImage = null;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgGioiTinh = new javax.swing.ButtonGroup();
        pnlMain = new javax.swing.JPanel();
        pnlTitleBar = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        lblResize = new javax.swing.JLabel();
        lblMinimize = new javax.swing.JLabel();
        lblFirst = new javax.swing.JLabel();
        lblPrevious = new javax.swing.JLabel();
        lblNext = new javax.swing.JLabel();
        lblLast = new javax.swing.JLabel();
        lblTitleBarName = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        txtSearch = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudent = new ps20250nguyenngocthuyduong.customcomponent.MyTable();
        btnSave = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnNew = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnUpdate = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnDelete = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        pnlStudentDetail = new javax.swing.JPanel();
        lblMaSV = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblHoTen = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblEmail = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblSDT = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblGioiTinh = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblDiaChi = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        txtStudentID = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        txtStudentName = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        txtEmail = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        txtPhoneNumber = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        rdoMale = new ps20250nguyenngocthuyduong.customcomponent.MyRadioButton();
        rdoFemale = new ps20250nguyenngocthuyduong.customcomponent.MyRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new ps20250nguyenngocthuyduong.customcomponent.MyTextArea();
        lblAnh = new javax.swing.JLabel();
        btnPrintStudent = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnPrintTable = new ps20250nguyenngocthuyduong.customcomponent.MyButton();

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

        lblTitleBarName.setText("Student Management");
        lblTitleBarName.setFont(new java.awt.Font("Nunito Sans SemiBold", 1, 14)); // NOI18N

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
                .addComponent(lblTitleBarName, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
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
                    .addComponent(lblFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTitleBarName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tblStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblStudentMousePressed(evt);
            }
        });
        tblStudent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblStudentKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblStudent);

        btnSave.setBackground(new java.awt.Color(17, 164, 40));
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(12, 109, 26)));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Lưu");
        btnSave.setNextFocusableComponent(btnNew);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnNew.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnNew.setText("Mới");
        btnNew.setNextFocusableComponent(btnUpdate);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnUpdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnUpdate.setText("Cập nhật");
        btnUpdate.setNextFocusableComponent(btnDelete);
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

        pnlStudentDetail.setBackground(new java.awt.Color(255, 255, 255));

        lblMaSV.setForeground(new java.awt.Color(141, 141, 141));
        lblMaSV.setText("Mã SV");

        lblHoTen.setForeground(new java.awt.Color(141, 141, 141));
        lblHoTen.setText("Họ tên");

        lblEmail.setForeground(new java.awt.Color(141, 141, 141));
        lblEmail.setText("Email");

        lblSDT.setForeground(new java.awt.Color(141, 141, 141));
        lblSDT.setText("SDT");

        lblGioiTinh.setForeground(new java.awt.Color(141, 141, 141));
        lblGioiTinh.setText("Giới tính");

        lblDiaChi.setForeground(new java.awt.Color(141, 141, 141));
        lblDiaChi.setText("Địa chỉ");

        txtStudentID.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtStudentID.setText("PS001");
        txtStudentID.setNextFocusableComponent(txtStudentName);

        txtStudentName.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtStudentName.setText("Trần Văn Anh");
        txtStudentName.setNextFocusableComponent(txtEmail);

        txtEmail.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtEmail.setText("vananh@gmail.com");
        txtEmail.setNextFocusableComponent(txtPhoneNumber);

        txtPhoneNumber.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtPhoneNumber.setText("0987654321");
        txtPhoneNumber.setNextFocusableComponent(txtAddress);

        btgGioiTinh.add(rdoMale);
        rdoMale.setSelected(true);
        rdoMale.setText("Nam");
        rdoMale.setNextFocusableComponent(rdoFemale);

        btgGioiTinh.add(rdoFemale);
        rdoFemale.setText("Nữ");
        rdoFemale.setNextFocusableComponent(txtAddress);

        txtAddress.setColumns(20);
        txtAddress.setLineWrap(true);
        txtAddress.setRows(5);
        txtAddress.setText("TPHCM");
        txtAddress.setWrapStyleWord(true);
        txtAddress.setNextFocusableComponent(btnSave);
        jScrollPane2.setViewportView(txtAddress);

        lblAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ps20250nguyenngocthuyduong/resources/images/students/st001.png"))); // NOI18N
        lblAnh.setToolTipText("Click to upload Image");
        lblAnh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        lblAnh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAnh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblAnhMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pnlStudentDetailLayout = new javax.swing.GroupLayout(pnlStudentDetail);
        pnlStudentDetail.setLayout(pnlStudentDetailLayout);
        pnlStudentDetailLayout.setHorizontalGroup(
            pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 583, Short.MAX_VALUE)
            .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlStudentDetailLayout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                    .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblSDT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblGioiTinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDiaChi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMaSV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblHoTen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlStudentDetailLayout.createSequentialGroup()
                            .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlStudentDetailLayout.createSequentialGroup()
                                        .addComponent(rdoMale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rdoFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(100, 100, 100))
                        .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(txtStudentName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        pnlStudentDetailLayout.setVerticalGroup(
            pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlStudentDetailLayout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlStudentDetailLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblMaSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtStudentName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rdoMale, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rdoFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(15, 15, 15)
                            .addGroup(pnlStudentDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        btnPrintStudent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPrintStudent.setText("Print this Student");
        btnPrintStudent.setNextFocusableComponent(btnDelete);
        btnPrintStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintStudentActionPerformed(evt);
            }
        });

        btnPrintTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPrintTable.setText("Print Table");
        btnPrintTable.setNextFocusableComponent(btnDelete);
        btnPrintTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlStudentDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMainLayout.createSequentialGroup()
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnPrintStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMainLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrintTable, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlStudentDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrintStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrintTable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))
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

    private void tblStudentMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentMousePressed
        selectRow(tblStudent.getSelectedRow());
    }//GEN-LAST:event_tblStudentMousePressed

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

    private void tblStudentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblStudentKeyPressed
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
    }//GEN-LAST:event_tblStudentKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        //xoa trang table
        model.setRowCount(0);

        for(Student s : lStudent) {
            String row = s.getStudentID() + " " + s.getStudentName() + " " + s.getEmail() + " " + s.getPhoneNumber() + " " + (s.getSex().equals("1") ? "Nam" : "Nữ") + " " + s.getAddress();
            
            if(row.toLowerCase().contains(txtSearch.getText().trim().toLowerCase())){
                model.addRow(new Object[] {s.getStudentID(), s.getStudentName(), s.getEmail(), s.getPhoneNumber(), s.getSex().equals("1") ? "Nam" : "Nữ", s.getAddress()});
            }
        }
        
        if(model.getRowCount() > 0) {
            selectRow(0);
        }
        else {
            //khong tim thay
            clearForm();
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
        txtStudentID.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String studentID = txtStudentID.getText().toUpperCase();
        String studentName = txtStudentName.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String sex = rdoMale.isSelected() ? "1" : rdoFemale.isSelected() ? "2" : "";
        String address = txtAddress.getText();
        
        //validate
        ArrayList<String> errors = new ArrayList<>();

//        errors += Validator.allowVietnamese((JTextArea)txtAddress, "Địa chỉ", address, true, null);
        errors.add(Validator.allowNumber(null, "Giới tính", sex, false, null));
        errors.add(Validator.allowNumber((JTextField)txtPhoneNumber, "SDT", phoneNumber, true, null));
        errors.add(Validator.email((JTextField)txtEmail, "Email", email, true, null));
        errors.add(Validator.allowVietnameseSpace((JTextField)txtStudentName, "Họ tên SV", studentName, false, null));
        errors.add(studentID.length() > 5 ? "Mã SV chỉ được chứa tối đa 5 kí tự\n" : "");
        errors.add(Validator.allowNumberText((JTextField)txtStudentID, "Mã SV", studentID, false, null));

        Collections.reverse(errors);
        String e = "";
        for(String s : errors) e += s;
        
        //co loi
        if(!e.isEmpty()) {
            MyDialog.display(1, e);
            return;
        }
        
        //pre-add
        if(studentDAO.getByID(studentID) != null) {
            //StudentID da ton tai
            MyDialog.display(1, "Mã SV này đã tồn tại.");
            txtStudentID.requestFocus();
            return;
        }
          
        //add
        // save image to file
        if(studentImage != null) {
            try {
            // Get the base directory of the application
            String baseDir = System.getProperty("user.dir");
            // Construct the path to the resources directory
            String resourcesPath = baseDir + "/resources/images/students/";

            File destDir = new File(resourcesPath);
            if (!destDir.exists()) {
                destDir.mkdirs(); // Create directories if they don't exist
            }

            // Tao va Luu anh
            File destFile = new File(destDir, studentID.toLowerCase() + ".png");
            ImageIO.write(studentImage, "png", destFile);
            } catch (Exception ex) {
                ex.printStackTrace();
                //them that bai
                MyDialog.display(1, "Có lỗi xảy ra.");
                return;
            }
        }
        
        int rowAffected = studentDAO.add(new Student(studentID, studentName, email, phoneNumber, sex, address));
        
        if(rowAffected > 0) {
            //them thanh cong
            fillToTable();
            selectRow(findStudentIndex(studentID));
        }
        else {
            //them that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String studentID = txtStudentID.getText().toUpperCase();
        String studentName = txtStudentName.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String sex = rdoMale.isSelected() ? "1" : rdoFemale.isSelected() ? "2" : "";
        String address = txtAddress.getText();
        
        //validate
        ArrayList<String> errors = new ArrayList<>();

//        errors += Validator.allowVietnamese((JTextArea)txtAddress, "Địa chỉ", address, true, null);
        errors.add(Validator.allowNumber(null, "Giới tính", sex, false, null));
        errors.add(Validator.allowNumber((JTextField)txtPhoneNumber, "SDT", phoneNumber, true, null));
        errors.add(Validator.email((JTextField)txtEmail, "Email", email, true, null));
        errors.add(Validator.allowVietnameseSpace((JTextField)txtStudentName, "Họ tên SV", studentName, false, null));
        errors.add(studentID.length() > 5 ? "Mã SV chỉ được chứa tối đa 5 kí tự\n" : "");
        errors.add(Validator.allowNumberText((JTextField)txtStudentID, "Mã SV", studentID, false, null));

        Collections.reverse(errors);
        String e = "";
        for(String s : errors) e += s;
        
        //co loi
        if(!e.isEmpty()) {
            MyDialog.display(1, e);
            return;
        }
        
        //pre-update
        if(studentDAO.getByID(studentID) == null) {
            //StudentID khong ton tai
            MyDialog.display(1, "Mã SV này không tồn tại để cập nhật.");
            txtStudentID.requestFocus();
            return;
        }
        
        //update
        if(studentImage != null && lblAnh.getIcon() != null) {
            try {
                // Get the base directory of the application
                String baseDir = System.getProperty("user.dir");
                // Construct the path to the resources directory
                String resourcesPath = baseDir + "/resources/images/students/" + studentID + ".png";

                File destFile = new File(resourcesPath);

                // Luu anh
                ImageIO.write(studentImage, "png", destFile);
            
//                File destFile = new File("src/ps20250nguyenngocthuyduong/resources/images/students/" + studentID + ".png");
//                ImageIO.write(studentImage, "png", destFile);
            } catch (IOException ex) {
                //update that bai
                MyDialog.display(1, "Có lỗi xảy ra.");
                return;
            }
        }
        
        int rowAffected = studentDAO.updateByID(new Student(studentID, studentName, email, phoneNumber, sex, address));
        
        if(rowAffected > 0) {
            //update thanh cong
            fillToTable();
            selectRow(findStudentIndex(studentID));
        }
        else {
            //update that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String studentID = txtStudentID.getText().toUpperCase();
        String studentName = txtStudentName.getText();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String sex = rdoMale.isSelected() ? "1" : rdoFemale.isSelected() ? "2" : "";
        String address = txtAddress.getText();
        
        //pre-delete
        Student studentDB = studentDAO.getByID(studentID);
        
        if(!Validator.isNotNull(null, studentID, null)) {
            MyDialog.display(1, "Vui lòng nhập Mã SV.");
            txtStudentID.requestFocus();
            return;
        }
        else if(studentDAO.getByID(studentID) == null) {
            //UserID khong ton tai
            MyDialog.display(1, "Mã SV này không tồn tại để xóa.");
            txtStudentID.requestFocus();
            return;
        }
        else if(!studentDB.getStudentName().equals(studentName) ||
                !studentDB.getEmail().equals(email) ||
                !studentDB.getPhoneNumber().equals(phoneNumber) ||
                !studentDB.getSex().equals(sex) ||
                !studentDB.getAddress().equals(address)) {
            MyDialog.display(1, "Thông tin của sinh viên trên form không khớp với CSDL.");
            return;
        }
          
        //delete
        //delete image
        // Get the base directory of the application
        String baseDir = System.getProperty("user.dir");
        // Construct the path to the resources directory
        String resourcesPath = baseDir + "/resources/images/students/" + studentID + ".png";
        File imageFile = new File(resourcesPath);
        if (imageFile.exists()) {
            if (!imageFile.delete()) {
                //delete that bai
                MyDialog.display(1, "Có lỗi xảy ra.");
                return;
            }
        }
        
        int rowAffected = studentDAO.deleteByID(studentID);
        
        if(rowAffected > 0) {
            //delete thanh cong
            fillToTable();
            
            //xoa 1 dong cuoi
            if(lStudent.size() == 0) clearForm();
            //xoa dong cuoi
            else if(index == lStudent.size()) selectRow(index - 1);
            else selectRow(index);
        }
        else {
            //delete that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void lblAnhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMousePressed
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showDialog(null, "Open");
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                studentImage = ImageIO.read(selectedFile);
                lblAnh.setIcon(ImageResizing.resizing(studentImage, lblAnh.getWidth() - 1, lblAnh.getHeight()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_lblAnhMousePressed

    private void btnPrintStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintStudentActionPerformed
        Export.panelToImage(pnlStudentDetail, txtStudentID.getText());
    }//GEN-LAST:event_btnPrintStudentActionPerformed

    private void btnPrintTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintTableActionPerformed
        Export.tableToPDF(tblStudent, "DanhSachSinhVien");
    }//GEN-LAST:event_btnPrintTableActionPerformed

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
            java.util.logging.Logger.getLogger(StudentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Check login
                if (!LoggedInUser.isCBDT()) {
                    new Login().setVisible(true);
                    MyDialog.display(1, "Bạn phải đăng nhập đúng Role để có thể truy cập chức năng này.");
                }
                else {
                    new StudentManagement().setVisible(true);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgGioiTinh;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnDelete;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnNew;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnPrintStudent;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnPrintTable;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnSave;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblClose;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblDiaChi;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblEmail;
    private javax.swing.JLabel lblFirst;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblGioiTinh;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblHoTen;
    private javax.swing.JLabel lblLast;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblMaSV;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JLabel lblNext;
    private javax.swing.JLabel lblPrevious;
    private javax.swing.JLabel lblResize;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblSDT;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTitleBarName;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlStudentDetail;
    private javax.swing.JPanel pnlTitleBar;
    private ps20250nguyenngocthuyduong.customcomponent.MyRadioButton rdoFemale;
    private ps20250nguyenngocthuyduong.customcomponent.MyRadioButton rdoMale;
    private ps20250nguyenngocthuyduong.customcomponent.MyTable tblStudent;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextArea txtAddress;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtEmail;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtPhoneNumber;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtSearch;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtStudentID;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtStudentName;
    // End of variables declaration//GEN-END:variables
}

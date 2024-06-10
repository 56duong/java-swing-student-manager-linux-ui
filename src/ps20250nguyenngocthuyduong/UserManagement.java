package ps20250nguyenngocthuyduong;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import ps20250nguyenngocthuyduong.customcomponent.MyDialog;
import ps20250nguyenngocthuyduong.dao.UserDAO;
import ps20250nguyenngocthuyduong.dao.UserDAOImpl;
import ps20250nguyenngocthuyduong.utils.AddPlaceholder;
import ps20250nguyenngocthuyduong.models.LoggedInUser;
import ps20250nguyenngocthuyduong.models.User;
import ps20250nguyenngocthuyduong.utils.ClearComponent;
import ps20250nguyenngocthuyduong.utils.Validator;

public class UserManagement extends javax.swing.JFrame {

    /**
    * The list of User objects currently displayed in the table.
    */
    ArrayList<User> lUser = new ArrayList<>(); //List User
    
    /**
    * The table model used to display the Student objects.
    */
    DefaultTableModel model;
    
    /**
    * The data access object for managing User objects.
    */
    UserDAO userDAO = new UserDAOImpl();
    
    /**
    * The index of the selected row in the table.
    */
    int index = -1;
    
    
    
    public UserManagement() {
        initComponents();
        
        setLocationRelativeTo(null);
        
        //chi dong frame nay chu khong dong frame chinh
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        //add placeholder
        AddPlaceholder.addPlaceholderText(txtSearch, "Tìm...");
        
        //table
        //tao model
        model = new DefaultTableModel();

        // Set the table model to the tblUser table
        tblUser.setModel(model);
        
        //disable table editing
        tblUser.setDefaultEditor(Object.class, null); 
        
        //table header
        String [] colNames = {"Mã ND", "Tên ND", "Mật khẩu", "Role"};
        model.setColumnIdentifiers(colNames);

        //column width
        tblUser.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblUser.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblUser.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblUser.getColumnModel().getColumn(3).setPreferredWidth(80);
        
        //table data
        fillToTable();
        
        tblUser.requestFocus();
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
        tblUser.setRowSelectionInterval(index, index);
        showDetail();
        //scroll toi dong duoc chon
        tblUser.scrollRectToVisible(new Rectangle(tblUser.getCellRect(index, 0, true))); 
    } 
        

    
    /**
    * Fills the table with all users in the database.
    * Clears the existing rows in the table and adds each users as a new row.
    */
    public void fillToTable() {
        lUser = (ArrayList<User>) userDAO.getAllContain(Validator.isNotNull(null, txtSearch.getText(), "Tìm...") ? txtSearch.getText() : null);
        
        model.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(lUser != null) {
            for(User u : lUser) {
                model.addRow(new Object[] {u.getUserID(), u.getUserName(), u.getPassword(), u.getRole().equals("1") ? "Giảng viên" : u.getRole().equals("2") ? "Cán bộ ĐT" : "Admin"});
            }
        }
    }
    
    
    
    //tim index cua User trong lUser bang userID
    /**
    * This method returns the index of the User object with the given userID in the lUser ArrayList.
    * @param userID the user ID to search for
    * @return the index of the matching User object in lUser, or -1 if no match is found
    */
    public Integer findUserIndex(String userID) {
        for(User u : lUser) {
            if(u.getUserID().equalsIgnoreCase(userID)) {
                return lUser.indexOf(u);
            }
        }
        return -1;
    }
    
    /**
     * Displays the details of the selected student in the form.
     */
    public void showDetail() {
        User u = new User();
        
        //lay MaND trong cot dau tien cua hang duoc chon
        String maSV = tblUser.getValueAt(tblUser.getSelectedRow(), 0).toString();
        
        //tim nguoi dung co maND trong lUser
        u = lUser.get(findUserIndex(maSV));
        
        //do du lieu tu User u len form
        txtUserID.setText(u.getUserID());
        txtUserName.setText(u.getUserName());
        txtPassword.setText(u.getPassword());
        if(u.getRole().equals("1")) rdoGiangVien.setSelected(true);
        else if(u.getRole().equals("2")) rdoCanBoDT.setSelected(true);
        else rdoAdmin.setSelected(true);
    }
    
    
    
    /**
    * This method clears the form by resetting the selected index in the table and clearing the input fields.
    */
    public void clearForm() {
        index = -1;
        tblUser.getSelectionModel().clearSelection(); //bo chon tren table
        ClearComponent.clear(txtUserID, txtUserName, txtPassword, btgRole);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgRole = new javax.swing.ButtonGroup();
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
        tblUser = new ps20250nguyenngocthuyduong.customcomponent.MyTable();
        lblMaND = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblTenND = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblMatKhau = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblRole = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        txtUserID = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        txtUserName = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        txtPassword = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        rdoAdmin = new ps20250nguyenngocthuyduong.customcomponent.MyRadioButton();
        rdoGiangVien = new ps20250nguyenngocthuyduong.customcomponent.MyRadioButton();
        btnSave = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnNew = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnUpdate = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnDelete = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        rdoCanBoDT = new ps20250nguyenngocthuyduong.customcomponent.MyRadioButton();

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
        lblResize.setToolTipText("Minimize");
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

        lblTitleBarName.setText("User Management");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 283, Short.MAX_VALUE)
                .addComponent(lblTitleBarName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
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

        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblUserMousePressed(evt);
            }
        });
        tblUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblUserKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);

        lblMaND.setForeground(new java.awt.Color(141, 141, 141));
        lblMaND.setText("Mã ND");

        lblTenND.setForeground(new java.awt.Color(141, 141, 141));
        lblTenND.setText("Tên ND");

        lblMatKhau.setForeground(new java.awt.Color(141, 141, 141));
        lblMatKhau.setText("Mật khẩu");

        lblRole.setForeground(new java.awt.Color(141, 141, 141));
        lblRole.setText("Role");

        txtUserID.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtUserID.setText("US006");
        txtUserID.setNextFocusableComponent(txtUserName);

        txtUserName.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtUserName.setText("admin");
        txtUserName.setNextFocusableComponent(txtPassword);

        txtPassword.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtPassword.setText("admin");
        txtPassword.setNextFocusableComponent(rdoAdmin);

        btgRole.add(rdoAdmin);
        rdoAdmin.setSelected(true);
        rdoAdmin.setText("Admin");

        btgRole.add(rdoGiangVien);
        rdoGiangVien.setText("Giảng viên");
        rdoGiangVien.setNextFocusableComponent(rdoGiangVien);

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

        btgRole.add(rdoCanBoDT);
        rdoCanBoDT.setText("Cán bộ ĐT");
        rdoCanBoDT.setNextFocusableComponent(rdoCanBoDT);

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRole, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaND, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenND, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMatKhau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addComponent(rdoAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(rdoGiangVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(rdoCanBoDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUserID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenND, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdoAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdoGiangVien, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rdoCanBoDT, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void tblUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMousePressed
        selectRow(tblUser.getSelectedRow());
    }//GEN-LAST:event_tblUserMousePressed

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

    private void tblUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblUserKeyPressed
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
    }//GEN-LAST:event_tblUserKeyPressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        //xoa trang table
        model.setRowCount(0);

        for(User u : lUser) {
            String row = u.getUserID() + " " + u.getUserName() + " " + u.getPassword() + " " + (u.getRole().equals("1") ? "Giảng viên" : u.getRole().equals("2") ? "Cán bộ ĐT" : "Admin");
            
            if(row.toLowerCase().contains(txtSearch.getText().trim().toLowerCase())){
                model.addRow(new Object[] {u.getUserID(), u.getUserName(), u.getPassword(), u.getRole().equals("1") ? "Giảng viên" : u.getRole().equals("2") ? "Cán bộ ĐT" : "Admin"});
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
        txtUserID.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String userID = txtUserID.getText().toUpperCase();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = rdoGiangVien.isSelected() ? "1" : rdoCanBoDT.isSelected() ? "2" : rdoAdmin.isSelected() ? "3" : "";
        
        //validate
        String errors = "";

        errors += Validator.allowNumberText((JTextField)txtUserID, "Mã ND", userID, false, null);
        errors += userID.length() > 5 ? "Mã ND chỉ được chứa tối đa 5 kí tự\n" : "";
        errors += Validator.allowNumberText((JTextField)txtUserName, "Tên ND", userName, false, null);
        errors += Validator.allowNumberText((JTextField)txtPassword, "Mật khẩu", password, false, null);
        errors += Validator.allowNumber(null, "Role", role, false, null);

        //co loi
        if(Validator.isNotNull(null, errors, null)) {
            MyDialog.display(1, errors);
            return;
        }
        
        //pre-add
        if(userDAO.getByID(userID) != null) {
            //UserID da ton tai
            MyDialog.display(1, "Mã ND này đã tồn tại.");
            txtUserID.requestFocus();
            return;
        }
        else if(userDAO.getByName(userName) != null) {
            //UserName da ton tai
            MyDialog.display(1, "Tên ND này đã tồn tại.");
            txtUserName.requestFocus();
            return;
        }
          
        //add
        int rowAffected = userDAO.add(new User(userID, userName, password, role));
        
        if(rowAffected > 0) {
            //them thanh cong
            fillToTable();
            selectRow(findUserIndex(userID));
        }
        else {
            //them that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String userID = txtUserID.getText().toUpperCase();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = rdoGiangVien.isSelected() ? "1" : rdoCanBoDT.isSelected() ? "2" : rdoAdmin.isSelected() ? "3" : "";
        
        //validate
        String errors = "";

        errors += Validator.allowNumberText((JTextField)txtUserID, "Mã ND", userID, false, null);
        if(userID.length() > 5) {
            errors += "Mã ND chỉ được chứa tối đa 5 kí tự\n";
            txtUserID.requestFocus();
        }
        errors += Validator.allowNumberText((JTextField)txtUserName, "Tên ND", userName, false, null);
        errors += Validator.allowNumberText((JTextField)txtPassword, "Mật khẩu", password, false, null);
        errors += Validator.allowNumber(null, "Role", role, false, null);

        //co loi
        if(Validator.isNotNull(null, errors, null)) {
            MyDialog.display(1, errors);
            return;
        }
        
        //pre-update
        if(userDAO.getByID(userID) == null) {
            //UserID khong ton tai
            MyDialog.display(1, "Mã ND này không tồn tại để cập nhật.");
            txtUserID.requestFocus();
            return;
        }
          
        //update
        int rowAffected = userDAO.updateByID(new User(userID, userName, password, role));
        
        if(rowAffected > 0) {
            //update thanh cong
            fillToTable();
            selectRow(findUserIndex(userID));
        }
        else {
            //update that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String userID = txtUserID.getText().toUpperCase();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = rdoGiangVien.isSelected() ? "1" : rdoCanBoDT.isSelected() ? "2" : rdoAdmin.isSelected() ? "3" : "";
        
        //pre-delete
        User userDB = userDAO.getByID(userID);
        
        if(!Validator.isNotNull(null, userID, null)) {
            MyDialog.display(1, "Vui lòng nhập Mã ND.");
            txtUserID.requestFocus();
            return;
        }
        else if(userDAO.getByID(userID) == null) {
            //UserID khong ton tai
            MyDialog.display(1, "Mã ND này không tồn tại để xóa.");
            txtUserID.requestFocus();
            return;
        }
        else if(!userDB.getUserName().equals(userName) ||
                !userDB.getPassword().equals(password) ||
                !userDB.getRole().equals(role)) {
            MyDialog.display(1, "Thông tin của người dùng trên form không khớp với CSDL.");
            return;
        }
          
        //delete
        int rowAffected = userDAO.deleteByID(userID);
        
        if(rowAffected > 0) {
            //delete thanh cong
            fillToTable();
            
            //xoa 1 dong cuoi
            if(lUser.size() == 0) clearForm();
            //xoa dong cuoi
            else if(index == lUser.size()) selectRow(index - 1);
            else selectRow(index);
        }
        else {
            //delete that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(UserManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Check login
                if (!LoggedInUser.isAdmin()) {
                    new Login().setVisible(true);
                    MyDialog.display(1, "Bạn phải đăng nhập đúng Role để có thể truy cập chức năng này.");
                }
                else {
                    new UserManagement().setVisible(true);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgRole;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnDelete;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnNew;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnSave;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblFirst;
    private javax.swing.JLabel lblLast;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblMaND;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblMatKhau;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JLabel lblNext;
    private javax.swing.JLabel lblPrevious;
    private javax.swing.JLabel lblResize;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblRole;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTenND;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTitleBarName;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlTitleBar;
    private ps20250nguyenngocthuyduong.customcomponent.MyRadioButton rdoAdmin;
    private ps20250nguyenngocthuyduong.customcomponent.MyRadioButton rdoCanBoDT;
    private ps20250nguyenngocthuyduong.customcomponent.MyRadioButton rdoGiangVien;
    private ps20250nguyenngocthuyduong.customcomponent.MyTable tblUser;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtPassword;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtSearch;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtUserID;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}

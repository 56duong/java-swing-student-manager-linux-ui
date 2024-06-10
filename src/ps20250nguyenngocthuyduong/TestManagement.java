package ps20250nguyenngocthuyduong;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import ps20250nguyenngocthuyduong.customcomponent.MyDialog;
import ps20250nguyenngocthuyduong.dao.GradeDAO;
import ps20250nguyenngocthuyduong.dao.GradeDAOImpl;
import ps20250nguyenngocthuyduong.dao.TestDAO;
import ps20250nguyenngocthuyduong.dao.TestDAOImpl;
import ps20250nguyenngocthuyduong.models.LoggedInUser;
import ps20250nguyenngocthuyduong.models.Test;
import ps20250nguyenngocthuyduong.utils.Chart;
import ps20250nguyenngocthuyduong.utils.ClearComponent;
import ps20250nguyenngocthuyduong.utils.Export;
import ps20250nguyenngocthuyduong.utils.Validator;

public class TestManagement extends javax.swing.JFrame {

    /**
    * The list of Test objects currently displayed in the table.
    */
    ArrayList<Test> lTest = new ArrayList<>(); //List Test
    
    /**
    * The table model used to display the Student objects.
    */
    DefaultTableModel model;
    
    /**
    * The data access object for managing Test objects.
    */
    TestDAO testDAO = new TestDAOImpl();
    
    /**
    * The data access object for managing Grade objects.
    */
    GradeDAO gradeDAO = new GradeDAOImpl();
    
    /**
    * The index of the selected row in the table.
    */
    int index = -1;
    
    
    
    public TestManagement() {
        initComponents();
        
        setLocationRelativeTo(null);
        
        //chi dong frame nay chu khong dong frame chinh
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        //table
        //tao model
        model = new DefaultTableModel();

        // Set the table model to the tblTest table
        tblTest.setModel(model);
        
        //disable table editing
        tblTest.setDefaultEditor(Object.class, null); 
        
        //table header
        String [] colNames = {"Mã Test", "Tên Test", "Ngày Test"};
        model.setColumnIdentifiers(colNames);

        //column width
        tblTest.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblTest.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblTest.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        //table data
        fillToTable();
        
        tblTest.requestFocus();
    }
    
    
    
    //XAY DUNG CAC HAM
    /**
    * Displays a bar chart of the average grades for each subject of the selected test.
    * Sets the title of the chart to include the number of the selected test.
    * Clears the content of the pnlChart panel and replaces it with the new chart.
    * Uses the Chart class to create and display the chart.
    * @see Chart#createChart(ArrayList, String[], JPanel)
    */
    public void displayChart() {
        lblChartTitle.setText("Điểm TB các môn của bài " + model.getValueAt(index, 1).toString());
        pnlChart.removeAll();
        
        JPanel pnl = new JPanel();
        pnl.setPreferredSize(new Dimension(0, pnlChart.getPreferredSize().height));

        //chart
        ArrayList<Double> lCol = gradeDAO.getAVGByTestID(model.getValueAt(index, 0).toString());
        
        Chart.createChart(lCol, new String[] {"TA", "TinHoc", "GDTC"}, pnl);
        
        pnlChart.add(pnl, BorderLayout.CENTER);
        pnlChart.revalidate();
        pnlChart.repaint();
    }
    
    
    
    /**
    * Selects a row in the table with the given index i and shows its detail in the corresponding text fields.
    * If i is negative or the table model is empty, this method has no effect.
    * 
    * @param i the index of the row to be selected
    */
    public void selectRow(int i) {
        index = i;
        tblTest.setRowSelectionInterval(index, index);
        showDetail();
        //scroll toi dong duoc chon
        tblTest.scrollRectToVisible(new Rectangle(tblTest.getCellRect(index, 0, true))); 
        displayChart();
    } 
        

    
    /**
    * Fills the table with all tests in the database.
    * Clears the existing rows in the table and adds each test as a new row.
    */
    public void fillToTable() {
        lTest = (ArrayList<Test>) testDAO.getAll();
        
        model.setRowCount(0); //clear rows in the table
        
        //them tung dong vao
        if(lTest != null) {
            for(Test t : lTest) {
                model.addRow(new Object[] {t.getTestID(), t.getTestName(), t.getTestDate()});
            }
        }
    }
    
    
    
    //tim index cua Test trong lTest bang testID
    /**
     * Returns the index of the test with the given test ID in the lTest ArrayList.
     * @param testID the test ID to search for
     * @return the index of the matching student object in lTest, or -1 if no match is found
     */
    public Integer findTestIndex(String testID) {
        for(Test t : lTest) {
            if(t.getTestID().equalsIgnoreCase(testID)) {
                return lTest.indexOf(t);
            }
        }
        return -1;
    }
    
    /**
     * Displays the details of the selected test in the form.
     */
    public void showDetail() {
        Test t = new Test();
        
        //lay MaT trong cot dau tien cua hang duoc chon
        String maT = tblTest.getValueAt(tblTest.getSelectedRow(), 0).toString();
        
        //tim test co maT trong lTest
        t = lTest.get(findTestIndex(maT));
        
        //do du lieu tu Test t len form
        txtTestID.setText(t.getTestID());
        txtTestName.setText(t.getTestName());
        txtTestDate.setText(t.getTestDate());
    }
    
    
    
    /**
    * This method clears the form by resetting the selected index in the table and clearing the input fields.
    */
    public void clearForm() {
        index = -1;
        tblTest.getSelectionModel().clearSelection(); //bo chon tren table
        ClearComponent.clear(txtTestID, txtTestName, txtTestDate);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTest = new ps20250nguyenngocthuyduong.customcomponent.MyTable();
        lblTestID = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblTestName = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        lblTestDate = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        txtTestID = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        txtTestName = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        txtTestDate = new ps20250nguyenngocthuyduong.customcomponent.MyTextField();
        btnSave = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnNew = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnUpdate = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        btnDelete = new ps20250nguyenngocthuyduong.customcomponent.MyButton();
        pnlChartContainer = new javax.swing.JPanel();
        pnlChart = new javax.swing.JPanel();
        lblChartTitle = new ps20250nguyenngocthuyduong.customcomponent.MyLabel();
        btnPrintChart = new ps20250nguyenngocthuyduong.customcomponent.MyButton();

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

        lblTitleBarName.setText("Test Management");
        lblTitleBarName.setFont(new java.awt.Font("Nunito Sans SemiBold", 1, 14)); // NOI18N

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
                .addGap(285, 285, 285)
                .addComponent(lblTitleBarName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(lblTitleBarName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tblTest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblTestMousePressed(evt);
            }
        });
        tblTest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblTestKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblTest);

        lblTestID.setForeground(new java.awt.Color(141, 141, 141));
        lblTestID.setText("Mã Test");

        lblTestName.setForeground(new java.awt.Color(141, 141, 141));
        lblTestName.setText("Tên Test");

        lblTestDate.setForeground(new java.awt.Color(141, 141, 141));
        lblTestDate.setText("Ngày Test");

        txtTestID.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtTestID.setText("US006");
        txtTestID.setNextFocusableComponent(txtTestName);

        txtTestName.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtTestName.setText("admin");
        txtTestName.setNextFocusableComponent(txtTestDate);

        txtTestDate.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(1, 3, 1, 3)));
        txtTestDate.setText("admin");
        txtTestDate.setNextFocusableComponent(btnSave);

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

        pnlChartContainer.setBackground(new java.awt.Color(255, 255, 255));

        pnlChart.setBackground(new java.awt.Color(240, 240, 240));
        pnlChart.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(199, 199, 199)), javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 20)));
        pnlChart.setMinimumSize(new java.awt.Dimension(412, 264));
        pnlChart.setPreferredSize(new java.awt.Dimension(412, 190));
        pnlChart.setLayout(new java.awt.BorderLayout());

        lblChartTitle.setBackground(new java.awt.Color(255, 255, 255));
        lblChartTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChartTitle.setText("Điểm TB các môn của bài Test 1");

        javax.swing.GroupLayout pnlChartContainerLayout = new javax.swing.GroupLayout(pnlChartContainer);
        pnlChartContainer.setLayout(pnlChartContainerLayout);
        pnlChartContainerLayout.setHorizontalGroup(
            pnlChartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChartContainerLayout.createSequentialGroup()
                .addGroup(pnlChartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlChart, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblChartTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlChartContainerLayout.setVerticalGroup(
            pnlChartContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChartContainerLayout.createSequentialGroup()
                .addComponent(pnlChart, javax.swing.GroupLayout.PREFERRED_SIZE, 206, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblChartTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnPrintChart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnPrintChart.setText("Print");
        btnPrintChart.setNextFocusableComponent(btnUpdate);
        btnPrintChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlChartContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTestID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTestName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTestDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTestID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTestName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTestDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnPrintChart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addComponent(pnlTitleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(pnlMainLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTestID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTestID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTestName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTestName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTestDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTestDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlChartContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrintChart, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))))
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

    private void tblTestMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTestMousePressed
        selectRow(tblTest.getSelectedRow());
    }//GEN-LAST:event_tblTestMousePressed

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

    private void tblTestKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblTestKeyPressed
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
    }//GEN-LAST:event_tblTestKeyPressed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
        txtTestID.requestFocus();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        String testID = txtTestID.getText().toUpperCase();
        String testName = txtTestName.getText();
        String testDate = txtTestDate.getText().isEmpty() ? dateFormat.format(date) : txtTestDate.getText();
        
        //validate
        ArrayList<String> errors = new ArrayList<>();

        errors.add(Validator.date((JTextField)txtTestDate, "Ngày Test", testDate, true, null));
        errors.add(Validator.allowNumberTextSpace((JTextField)txtTestName, "Tên Test", testName, true, null));
        errors.add(testID.length() > 5 ? "Mã Test chỉ được chứa tối đa 5 kí tự\n" : "");
        errors.add(Validator.allowNumberText((JTextField)txtTestID, "Mã Test", testID, false, null));

        Collections.reverse(errors);
        String e = "";
        for(String s : errors) e += s;
        
        //co loi
        if(!e.isEmpty()) {
            MyDialog.display(1, e);
            return;
        }
        
        //pre-add
        if(testDAO.getByID(testID) != null) {
            //TestID da ton tai
            MyDialog.display(1, "Mã Test này đã tồn tại.");
            txtTestID.requestFocus();
            return;
        }

        //add
        int rowAffected = testDAO.add(new Test(testID, testName, testDate));
        
        if(rowAffected > 0) {
            //them thanh cong
            fillToTable();
            selectRow(findTestIndex(testID));
            
            displayChart();
        }
        else {
            //them that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        String testID = txtTestID.getText().toUpperCase();
        String testName = txtTestName.getText();
        String testDate = txtTestDate.getText();
        
        //validate
        ArrayList<String> errors = new ArrayList<>();

        errors.add(Validator.date((JTextField)txtTestDate, "Ngày Test", testDate, true, null));
        errors.add(Validator.allowNumberTextSpace((JTextField)txtTestName, "Tên Test", testName, true, null));
        errors.add(testID.length() > 5 ? "Mã Test chỉ được chứa tối đa 5 kí tự\n" : "");
        errors.add(Validator.allowNumberText((JTextField)txtTestID, "Mã Test", testID, false, null));

        Collections.reverse(errors);
        String e = "";
        for(String s : errors) e += s;
        
        //co loi
        if(!e.isEmpty()) {
            MyDialog.display(1, e);
            return;
        }
        
        //pre-update
        if(testDAO.getByID(testID) == null) {
            //TestID khong ton tai
            MyDialog.display(1, "Mã Test này không tồn tại để cập nhật.");
            txtTestID.requestFocus();
            return;
        }
          
        //update
        int rowAffected = testDAO.updateByID(new Test(testID, testName, testDate));
        
        if(rowAffected > 0) {
            //update thanh cong
            fillToTable();
            selectRow(findTestIndex(testID));
            
            displayChart();
        }
        else {
            //update that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String testID = txtTestID.getText().toUpperCase();
        String testName = txtTestName.getText();
        String testDate = txtTestDate.getText().isEmpty() ? null : txtTestDate.getText();
        
        //pre-delete
        Test testDB = testDAO.getByID(testID);     
        if(!Validator.isNotNull(null, testID, null)) {
            MyDialog.display(1, "Vui lòng nhập Mã Test.");
            txtTestID.requestFocus();
            return;
        }
        else if(testDAO.getByID(testID) == null) {
            //TestID khong ton tai
            MyDialog.display(1, "Mã Test này không tồn tại để xóa.");
            txtTestID.requestFocus();
            return;
        }
        else if(!testDB.getTestName().equals(testName) ||
                !Objects.equals(testDB.getTestDate(), testDate)) {
            MyDialog.display(1, "Thông tin của Test trên form không khớp với CSDL.");
            return;
        }
          
        //delete
        int rowAffected = testDAO.deleteByID(testID);
        
        if(rowAffected > 0) {
            //delete thanh cong
            fillToTable();
            
            //xoa 1 dong cuoi
            if(lTest.size() == 0) clearForm();
            //xoa dong cuoi
            else if(index == lTest.size()) selectRow(index - 1);
            else selectRow(index);
            
            displayChart();
        }
        else {
            //delete that bai
            MyDialog.display(1, "Có lỗi xảy ra.");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnPrintChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintChartActionPerformed
        Export.panelToImage(pnlChartContainer, lblChartTitle.getText().replaceAll(" ", "-"));
    }//GEN-LAST:event_btnPrintChartActionPerformed

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
            java.util.logging.Logger.getLogger(TestManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Check login
                if (!LoggedInUser.isAdmin() || !LoggedInUser.isGiangVien()) {
                    new Login().setVisible(true);
                    MyDialog.display(1, "Bạn phải đăng nhập đúng Role để có thể truy cập chức năng này.");
                }
                else {
                    new TestManagement().setVisible(true);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgRole;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnDelete;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnNew;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnPrintChart;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnSave;
    private ps20250nguyenngocthuyduong.customcomponent.MyButton btnUpdate;
    private javax.swing.JScrollPane jScrollPane1;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblChartTitle;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblFirst;
    private javax.swing.JLabel lblLast;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JLabel lblNext;
    private javax.swing.JLabel lblPrevious;
    private javax.swing.JLabel lblResize;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTestDate;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTestID;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTestName;
    private ps20250nguyenngocthuyduong.customcomponent.MyLabel lblTitleBarName;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JPanel pnlChartContainer;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlTitleBar;
    private ps20250nguyenngocthuyduong.customcomponent.MyTable tblTest;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtTestDate;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtTestID;
    private ps20250nguyenngocthuyduong.customcomponent.MyTextField txtTestName;
    // End of variables declaration//GEN-END:variables
}

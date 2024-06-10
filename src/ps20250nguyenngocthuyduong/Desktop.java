package ps20250nguyenngocthuyduong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import ps20250nguyenngocthuyduong.customcomponent.MyDialog;
import ps20250nguyenngocthuyduong.customcomponent.MyImagePanel;
import ps20250nguyenngocthuyduong.customcomponent.MyTextArea;
import ps20250nguyenngocthuyduong.models.LoggedInUser;
import ps20250nguyenngocthuyduong.models.MyFont;
import ps20250nguyenngocthuyduong.utils.ImageResizing;
import ps20250nguyenngocthuyduong.utils.ImageUtil;

public class Desktop {
    /**
    * Represents the width of the frame.
    */
    public final int FRAME_WIDTH = 1280;
    
    /**
    * Represents the height of the frame.
    */
    public final int FRAME_HEIGHT = 720;
    
    /**
    * Represents the clock label on the desktop.
    */
    JLabel lblClock;
    
    /**
    Represents the panel containing all the desktop applications.
    */
    MyImagePanel pnlApps;
    
    /**
    * Represents a list of all the desktop applications.
    */
    ArrayList<String> iconApp = new ArrayList<>(); //tat ca app tren desktop (khong bao gom cua iconDock)
    
    /**
    * This method opens the application associated with the specified label.
    * 
    * @param appName The name of the application to open.
    */
    MouseAdapter openApp; //open tat ca app tren desktop (bao gom cua iconDock)
    
    /**
    * This variable holds a mapping of file names to their corresponding text content.
    */
    HashMap<String, String> newTextDocumentString = new HashMap<>();
    
    /**
    * The main panel of the desktop application, which contains all other panels.
    */
    JPanel pnlMain;
    
    /**
    * The left panel of the desktop application, which contains the list of applications.
    */
    JPanel pnlLeft;
    
    /**
    * The top panel of the main panel, which contains the clock and the search bar.
    */
    JPanel pnlMainTop;
    
    /**
    * An array of strings representing the icons currently displayed on the dock.
    * The order of the icons in the array determines their position on the dock.
    */
    String [] iconDock;
    
    /**
     * Constructs a new Desktop object.
     */
    public Desktop() {
        // frame
        JFrame frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Check login
        if (!LoggedInUser.isLoggedIn()) {
            //chua dang nhap
            new Login().setVisible(true);
            MyDialog.display(1, "Bạn phải đăng nhập trước khi truy cập vào Desktop.");
            
            return;
        }
        //check role
        else if (LoggedInUser.isGiangVien()) {
            iconApp.add("MarkManagement");
            iconApp.add("TestManagement");
        } else if (LoggedInUser.isCBDT()) {
            iconApp.add("StudentManagement");
        } else if (LoggedInUser.isAdmin()) {
            iconApp.add("StudentManagement");
            iconApp.add("MarkManagement");
            iconApp.add("UserManagement");
            iconApp.add("TestManagement");
        } else {
            JOptionPane.showMessageDialog(null, "Unknown user role.");
            return;
        }
        
        
        
        //event open app
        openApp = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //get app name
                JLabel l = (JLabel) e.getSource();     
                String appName = l.getName();
                
                if (e.isPopupTrigger() && appName.toLowerCase().matches("^newtextdocument\\d*\\.txt$")) {
                    JPopupMenu popupMenuDelete = new JPopupMenu();
                    JMenuItem mniDelete = new JMenuItem("Delete");
                    mniDelete.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            iconApp.remove(appName);
                            // Update desktop
                            //display all app
                            pnlApps.removeAll();
                            for(int i = 0; i < iconApp.size(); i++) {
                                createApp(iconApp.get(i));
                            }
                            
                            pnlApps.revalidate();
                            pnlApps.repaint();
                        }
                    });
                    popupMenuDelete.add(mniDelete);
                    popupMenuDelete.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            
            public void mouseEntered(MouseEvent e) {
                //hover in
                JLabel l = (JLabel) e.getSource();
                l.setOpaque(true);
                l.repaint();
                l.setBackground(new Color(86, 47, 73));
            }
            
            public void mouseExited(MouseEvent e) {
                //hover out
                JLabel l = (JLabel) e.getSource();
                l.setOpaque(false);
                l.repaint();
            }
            
            public void mouseClicked(MouseEvent e) {
                //get app name
                JLabel l = (JLabel) e.getSource();     
                String appName = l.getName().toLowerCase();

                //double click on app
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    
                    if(appName.equals("markmanagement")) {
                        new MarkManagement().setVisible(true);
                    }
                    else if(appName.equals("studentmanagement")) {
                        new StudentManagement().setVisible(true);
                    }
                    else if(appName.equals("usermanagement")) {
                        new UserManagement().setVisible(true);
                    }
                    else if(appName.equals("testmanagement")) {
                        new TestManagement().setVisible(true);
                    }
                    else if(appName.equals("firefox")) {
                        String search = (String) JOptionPane.showInputDialog(null, "Search with Google", "Firefox", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ImageUtil.getResource("icons/firefox.png")), null, null);
                        if(search != null && !search.isEmpty()) {
                            String url = "https://www.google.com/search?q=" + search.replaceAll(" ", "+");
                            try {
                                java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                    else if(appName.equals("filemanager")) {
                        //create tree
                        DefaultMutableTreeNode desktop = new DefaultMutableTreeNode("Desktop");  
                        
                        for(String s : iconApp) {
                            DefaultMutableTreeNode tn = new DefaultMutableTreeNode(s);  
                            desktop.add(tn);  
                        }

                        JTree jt = new JTree(desktop);  

                        //open form tree
                        jt.addTreeSelectionListener(new TreeSelectionListener() {
                            @Override
                            public void valueChanged(TreeSelectionEvent e) {
                                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jt.getLastSelectedPathComponent();
                                if (selectedNode.toString().equalsIgnoreCase("studentmanagement")) {
                                    new StudentManagement().setVisible(true);
                                }
                                else if (selectedNode.toString().equalsIgnoreCase("markmanagement")) {
                                    new MarkManagement().setVisible(true);
                                }
                                else if (selectedNode.toString().equalsIgnoreCase("usermanagement")) {
                                    new UserManagement().setVisible(true);
                                }
                                else if (selectedNode.toString().equalsIgnoreCase("testmanagement")) {
                                    new TestManagement().setVisible(true);
                                }
                                for (String i : newTextDocumentString.keySet()) {
                                    if (selectedNode.toString().equalsIgnoreCase(i)) {
                                        openTxtApp(i);
                                    }
                                }
                            }
                        });

                        //filemanager frame
                        JFrame jf = new JFrame("File Manager");
                        jf.setIconImage(new ImageIcon(ImageUtil.getResource("icons/filemanager.png")).getImage());
                        JScrollPane sp = new JScrollPane(jt);
                        jf.add(sp);
                        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        jf.setSize(400, 300);
                        jf.setLocationRelativeTo(null);
                        jf.setVisible(true);

                    }
                    else if(appName.toLowerCase().matches("^newtextdocument\\d*\\.txt$")) {
                        openTxtApp(appName);
                    }
                }
            }
        };
        
        
        
        // pnlLeft
        pnlLeft = new JPanel(new GridLayout(11, 1, 10, 10));
        pnlLeft.setBorder(new EmptyBorder(20, 10, 10, 10));
        pnlLeft.setPreferredSize(new Dimension(72, FRAME_HEIGHT));
        pnlLeft.setBounds(0, 0, 72, FRAME_HEIGHT);
        pnlLeft.setBackground(new Color(30, 17, 24));

        //iconDock
        iconDock = new String[] {"firefox", "filemanager"};
        for(String i : iconDock) {
            JLabel lbl = new JLabel(new ImageIcon(ImageUtil.getResource("icons/" + i + ".png")), JLabel.CENTER);
            lbl.setName(i);
            lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lbl.addMouseListener(openApp);
            
            pnlLeft.add(lbl);
        }
        
        
        
        //pnlMain
        pnlMain = new JPanel();
        pnlMain.setLayout(new BorderLayout());
        
        //pnlMainTop
        pnlMainTop = new JPanel(new BorderLayout());
        pnlMainTop.setBorder(new EmptyBorder(3, 10, 3, 10));
        pnlMainTop.setPreferredSize(new Dimension(FRAME_WIDTH, 25));
        pnlMainTop.setBackground(new Color(19, 19, 19));
        
        //lblClock
        lblClock = new JLabel("", JLabel.CENTER);
        lblClock.setForeground(Color.WHITE);
        lblClock.setFont(new MyFont().font);
        showClock(lblClock);
            
        //lblLogout va lblPoweroff
        JLabel lblLogout = new JLabel("Log Out", new ImageIcon(ImageUtil.getResource("icons/logout.png")), JLabel.RIGHT);
        lblLogout.setForeground(Color.WHITE);
        lblLogout.setFont(new MyFont().font);
        lblLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //closing all
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    window.dispose();
                }
                
                //logout
                LoggedInUser.logOut();
                
                //open login
                new Login().setVisible(true);
            }
        });
        
        JLabel lblPoweroff = new JLabel("Power Off", new ImageIcon(ImageUtil.getResource("icons/poweroff.png")), JLabel.RIGHT);
        lblPoweroff.setForeground(Color.WHITE);
        lblPoweroff.setFont(new MyFont().font);
        lblPoweroff.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblPoweroff.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        
        JPanel pnlPoweroffLogout = new JPanel(new GridLayout(1, 2, 20, 0));
        pnlPoweroffLogout.setOpaque(false);
        pnlPoweroffLogout.add(lblLogout);
        pnlPoweroffLogout.add(lblPoweroff);
        
        pnlMainTop.add(lblClock, BorderLayout.CENTER);
        pnlMainTop.add(pnlPoweroffLogout, BorderLayout.EAST);
        JPanel pnlNull = new JPanel();
        pnlNull.setOpaque(false);
        pnlNull.setPreferredSize(new Dimension(pnlPoweroffLogout.getPreferredSize().width, pnlPoweroffLogout.getPreferredSize().height));
        pnlMainTop.add(pnlNull, BorderLayout.WEST);
        
        pnlMain.add(pnlMainTop, BorderLayout.NORTH);
        

        
        //pnlApps
        pnlApps = new MyImagePanel("backgrounds/warty-final-ubuntu.png", FRAME_WIDTH, FRAME_HEIGHT);
        pnlApps.setBorder(new EmptyBorder(10, 72, 10, 10));
        pnlApps.setLayout(new FlowLayout(FlowLayout.LEFT, 40, 10));

        //display all app
        for(int i = 0; i < iconApp.size(); i++) {
            createApp(iconApp.get(i));
        }
        pnlMain.add(pnlApps, BorderLayout.CENTER);
        
        //popup menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem mniNewText = new JMenuItem("New Text Document...");
        popupMenu.add(mniNewText);
        JMenuItem mniChangeBackground = new JMenuItem("Change Background...");
        popupMenu.add(mniChangeBackground);
        
        pnlMain.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()) {
                    //neu nhan chuot phai
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        
        mniNewText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iconApp.add("NewTextDocument" + newTextDocumentString.size() + ".txt");                
                createApp("NewTextDocument" + newTextDocumentString.size() + ".txt");

                // Update desktop
                pnlApps.revalidate();
                pnlApps.repaint();
                
                openTxtApp("");
            }
        });
        
        mniChangeBackground.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int r = fc.showOpenDialog(null);

                if(r == JFileChooser.APPROVE_OPTION) {
                    try {
                        Image img = new ImageIcon(fc.getSelectedFile().getPath()).getImage();   
                        pnlApps.setImg(img);
                        
                        pnlMain.revalidate();
                        pnlMain.repaint();
                        pnlLeft.revalidate();
                        pnlLeft.repaint();
                    } catch (Exception ex) {
                        System.out.println("Error");
                    }
                }
            }
        });
        
        
        
        //add to frame
        frame.add(pnlLeft);
        frame.add(pnlMain);

        frame.setUndecorated(true);
        frame.setVisible(true); 
        showClock(lblClock);
    }
  
  
    
    /**
     * This method shows a clock with the current time on the specified label.
     * The clock updates every minute.
     * 
     * @param lbl The label to show the clock on.
     */
    public void showClock(JLabel lbl) {
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("LLL dd - hh:mm");
        
        //get curent time
        Date now = new Date();
        String time = df.format(now);
        lbl.setText(time);
        
        Runnable rClock = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    //get curent time
                    Date now = new Date();
                    String time = df.format(now);

                    lbl.setText(time);
                    
                    try {
                        //wait 1 minute
                        Thread.sleep(60000);
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        };
        
        Thread tClock = new Thread(rClock);
        tClock.start();
    }
    
    
    
    /**
     * This method creates an application icon on the Applications panel.
     * 
     * @param appName The name of the application to create an icon for.
     */
    public void createApp(String appName) {
        //hien thi app len desktop (khong bao gom dock)
        String appNameIcon = appName.toLowerCase();
        if(appName.toLowerCase().matches("^newtextdocument\\d*\\.txt$")) {
            appNameIcon = "newtextdocument";
        }

        int appSize = 48;
        JLabel lbl = new JLabel(String.format("<html><p style='word-wrap: break-word; width: 48px; text-align: center'>" + appName + "</p></html>"), ImageResizing.resizing("icons/" + appNameIcon.toLowerCase() + ".png", appSize, appSize), JLabel.CENTER);
        lbl.setVerticalTextPosition(JLabel.BOTTOM);//chu doc
        lbl.setHorizontalTextPosition(JLabel.CENTER); //chu ngang
        lbl.setVerticalAlignment(JLabel.TOP); //doc
        lbl.setName(appName);
        lbl.setPreferredSize(new Dimension(60, 110));
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new MyFont().font);
        lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl.addMouseListener(openApp);

        pnlApps.add(lbl);
        lbl.addMouseListener(openApp);
    }
    
    
    
    /**
     * This method opens a new text document with the specified file name and
     * displays its content.
     * 
     * @param fn The name of the text document to open.
     */
    public void openTxtApp(String fn) {
        //mo NewTextDocument.txt va hien thi noi dung dua tren appName
        if(fn.equals("")) {
            fn = "NewTextDocument" + newTextDocumentString.size() + ".txt";
        }
        String fn2 = fn;   
    
        JFrame newTextDocument = new JFrame(fn);
        newTextDocument.setIconImage(new ImageIcon(ImageUtil.getResource("icons/newtextdocument.png")).getImage());
        newTextDocument.setSize(800, 450);
        newTextDocument.setLocationRelativeTo(null);
        newTextDocument.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextArea newTextArea = new MyTextArea();
        newTextArea.setText(newTextDocumentString.get(fn2));
        JScrollPane newScrollPane = new JScrollPane(newTextArea);
        newTextDocument.getContentPane().add(newScrollPane, BorderLayout.CENTER);

        newTextDocument.setVisible(true);
        
        newTextDocument.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                newTextDocumentString.put(fn2.toLowerCase(), newTextArea.getText());
            }
        });
    }
    
    
    
    public static void main(String[] args) {
        //Look and feel
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
        }
        
        Desktop desktop = new Desktop();
    }
}

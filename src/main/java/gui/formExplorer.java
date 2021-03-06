/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import adb.DataReciever;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import structure.Config;
import structure.Explorer;
import structure.IconController;
import structure.ModelSPFolders;
import adb.Logger;


public class formExplorer extends javax.swing.JFrame implements TableModelListener {

    private Explorer exp;
    private DataReciever adb;
    private int mouseClickCount = 0;
    private String startupPath;
    private static boolean needEdit = false;

    /**
     * Creates new form formExplorer
     */
    public formExplorer() {
        initComponents();
        
        Logger.setLogControl(jTextAreaLog);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jButton1 = new javax.swing.JButton();
        jTextFieldPC = new javax.swing.JTextField();
        jTextFieldSP = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPaneDevice = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTablePC = new CustomTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSP = new CustomTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jPopupMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPopupMenu2MouseReleased(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AExplorer");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("/gfx/arrow_refresh.png"))); // NOI18N
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/arrow_refresh.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTextPaneDevice.setEditable(false);
        jScrollPane3.setViewportView(jTextPaneDevice);

        jTablePC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTablePC.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTablePCMouseMoved(evt);
            }
        });
        jTablePC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTablePCMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePCMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTablePC);

        jTableSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableSP.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTableSPMouseMoved(evt);
            }
        });
        jTableSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableSPMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSPMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSP);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/application_double.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/image_add.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextAreaLog.setEditable(false);
        jTextAreaLog.setColumns(20);
        jTextAreaLog.setLineWrap(true);
        jTextAreaLog.setRows(5);
        jTextAreaLog.setToolTipText("");
        jScrollPane2.setViewportView(jTextAreaLog);

        jMenu1.setText("Actions");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/image_add.png"))); // NOI18N
        jMenuItem2.setText("Screenshot");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/application_double.png"))); // NOI18N
        jMenuItem3.setText("Packages");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(jButton3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButton2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 218, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jTextFieldSP)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jTextFieldPC)
                            .add(layout.createSequentialGroup()
                                .add(jButton7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(0, 0, Short.MAX_VALUE))
                            .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton1)
                    .add(jButton7)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jButton3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jButton2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFieldPC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldSP, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                    .add(jScrollPane4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            Config.init(this);

            adb = new DataReciever(this);

            ArrayList<String> arrDevices = adb.getDevices(false);
            if (arrDevices.size() == 0) {
                throw new Exception("No device.");
            }

            String tmpDeviceName = arrDevices.get(0);

            // if ADB service does not executed at current time
            if (tmpDeviceName.equals("* daemon started successfully *")) {
                tmpDeviceName = adb.getDevices(false).get(0);
            }

            DataReciever.selectedDevice = tmpDeviceName;

            startupPath = new File("").getAbsolutePath();
            String path = System.getProperty("user.dir");
            exp = new Explorer(path);

            jTextFieldPC.setText(path);
            jTextFieldSP.setText("/");

            jTextPaneDevice.setText(tmpDeviceName);

            jTablePC.setModel(exp.setPath(), null);
            adb.getDirContent(jTextFieldSP.getText());
        } catch (IOException e) {
            Logger.writeToLog(e);
            // Попадаем сюда только если что-то не так с settings.ini
        } catch (Exception e) {
            Logger.writeToLog(e);
            //System.exit(1);
        }
    }//GEN-LAST:event_formWindowOpened


    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            jTextFieldPC.setText(exp.getPath());
            jTablePC.setModel(exp.setPath(), null);

        } catch (Exception ex) {
            Logger.writeToLog(ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTablePCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePCMouseClicked
        //mouseClickCount++;
        if (++mouseClickCount == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            try {
                mouseClickCount = 0;
                int selectedRow = jTablePC.getSelectedRow();
                //int selectedCol = jTablePC.getSelectedColumn();
                String file = (String) jTablePC.getValueAt(selectedRow, 1);
                if (file.equals("..")) {
                    jTablePC.setModel(exp.getUpperDirectory(), null);
                    jTextFieldPC.setText(exp.getPath());
                } else if (exp.getType(file) == Explorer.DIRECTORY_ITEM_FOLDER) {
                    jTablePC.setModel(exp.setPath(), null);
                    jTextFieldPC.setText(exp.getPath());
                }
            } catch (Exception ex) {
                Logger.writeToLog(ex);
            }

        } else if (evt.getButton() == MouseEvent.BUTTON3) {
        }
    }//GEN-LAST:event_jTablePCMouseClicked

    private void jTablePCMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePCMouseMoved
        mouseClickCount = 0;
    }//GEN-LAST:event_jTablePCMouseMoved

    private void jTableSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSPMouseClicked
        if (++mouseClickCount == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            mouseClickCount = 0;
            ModelSPFolders model = null;
            String resultPath = null;

            try {
                int selectedRow = jTableSP.getSelectedRow();
                //int selectedCol = jTableSP.getSelectedColumn();
                String file = (String) jTableSP.getValueAt(selectedRow, 1);
                int i = 0;

                // checking directory type              
                if (file.equals("..")) // upper directory
                {
                    String currentPath = jTextFieldSP.getText();
                    i = currentPath.lastIndexOf("/");

                    if (i != 0) {
                        resultPath = currentPath.substring(0, i);
                    } else {
                        resultPath = "/";
                    }
                } else if ((i = file.indexOf(" -> ")) > 0) // link
                {
                    file = file.substring(i + 4, file.length());
                    if (file.indexOf("/") != 0) {
                        return;
                    }

                    // парсим Long из столбца чтобы проверить, файл или папка
                    // для папки значение столбца null
                    try {
                        Long.parseLong((String) jTableSP.getValueAt(selectedRow, 5));
                    } catch (NumberFormatException ex) {
                        resultPath = file;
                        //Logger.writeToLog(ex);
                    }
                } else // unner directory
                {
                    try {
                        Long.parseLong((String) jTableSP.getValueAt(selectedRow, 5));
                    } catch (NumberFormatException ex) {
                        //Logger.writeToLog(ex);
                        if (!jTextFieldSP.getText().equals("/")) {
                            resultPath = jTextFieldSP.getText() + "/" + (String) jTableSP.getValueAt(selectedRow, 1);
                        } else {
                            resultPath = "/" + (String) jTableSP.getValueAt(selectedRow, 1);
                        }
                    }
                }
                adb.getDirContent(resultPath);
                jTextFieldSP.setText(resultPath);
            } catch (Exception ex) {
                Logger.writeToLog(ex);
            }
        }

    }//GEN-LAST:event_jTableSPMouseClicked

    private void jTableSPMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSPMouseMoved
        mouseClickCount = 0;
    }//GEN-LAST:event_jTableSPMouseMoved

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            adb.getDirContent(jTextFieldSP.getText());
        }

    }//GEN-LAST:event_jButton1MouseClicked

    private void jTableSPMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSPMouseReleased
        final int selRows[] = jTableSP.getSelectedRows();
        if (evt.getButton() == MouseEvent.BUTTON3 && selRows.length > 0) {
            jPopupMenu1.removeAll();
            JMenuItem miDelete, miView, miRename, miTransfer;
            miDelete = new JMenuItem("Delete", IconController.mDelete);
            miView = new JMenuItem("View as Text", IconController.mView);
            miRename = new JMenuItem("Rename", IconController.mRename);
            miTransfer = new JMenuItem("Send to PC", IconController.mTransferPC);
            final TableModelListener obj = this;
            miDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < selRows.length; i++) {
                        String ext = (String) jTableSP.getValueAt(selRows[i], 2);
                        String path = jTextFieldSP.getText() + "/" + jTableSP.getValueAt(selRows[i], 1);
                        if (ext != null) {
                            path += ext;
                        }
                        adb.deleteFile(jTextFieldSP.getText(), path);
                    }
                }
            });
            miTransfer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        for (int i = 0; i < selRows.length; i++) {
                            String Spath = jTextFieldSP.getText()
                                    + "/"
                                    + jTableSP.getValueAt(selRows[i], 1)
                                    + jTableSP.getValueAt(selRows[i], 2);
                            String DPath = jTextFieldPC.getText()
                                    + File.separator
                                    + jTableSP.getValueAt(selRows[i], 1)
                                    + jTableSP.getValueAt(selRows[i], 2);
                            adb.pullFile(Spath, DPath);
                        }
                        jTablePC.setModel(exp.setPath(), null);
                    } catch (Exception ex) {
                        Logger.writeToLog(ex);
                    }
                }
            });

            try {
                final int i = jTableSP.getSelectedRow();
                Object tmp = jTableSP.getValueAt(i, 5);
                formExplorer exp = this;
                if (selRows.length == 1 && (tmp != null && tmp.toString().length() > 0)) {
                    miView.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            final String Spath = jTextFieldSP.getText() + "/" + jTableSP.getValueAt(i, 1) + jTableSP.getValueAt(i, 2);
                            final String DPath = startupPath + File.separator + "tmp" + File.separator + jTableSP.getValueAt(i, 1) + jTableSP.getValueAt(i, 2);
                            adb.pullFile(Spath, DPath);

                            java.awt.EventQueue.invokeLater(new Runnable() {
                                public void run() {
                                    new formWatcher(exp, DPath, Spath, true).setVisible(true);
                                }
                            });
                        }
                    });

                    miRename.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Rename

                        }
                    });

                    jPopupMenu1.add(miView);
                    jPopupMenu1.add(miRename);
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                Logger.writeToLog(ex);
            }

            jPopupMenu1.add(miDelete);
            jPopupMenu1.add(miTransfer);
            jPopupMenu1.show(jTableSP, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTableSPMouseReleased

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MousePressed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            adb.makeScreenshot(jTextFieldSP.getText());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.writeToLog(e);
        }
        // показать уведомление о том, что скриншот сохранен
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jPopupMenu2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPopupMenu2MouseReleased
    }//GEN-LAST:event_jPopupMenu2MouseReleased

    private void jTablePCMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePCMouseReleased
        final int selRows[] = jTablePC.getSelectedRows();
        if (evt.getButton() == MouseEvent.BUTTON3 && selRows.length > 0) {
            jPopupMenu2.removeAll();
            JMenuItem miView, miDelete, miTransfer;
            miDelete = new JMenuItem("Delete", IconController.mDelete);
            miView = new JMenuItem("View as Text", IconController.mView);
            miTransfer = new JMenuItem("Send to device", IconController.mTransferSP);

            miDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        for (int i = 0; i < selRows.length; i++) {
                            String ext = (String) jTablePC.getValueAt(selRows[i], 2);
                            String Spath = jTextFieldPC.getText() + File.separator + jTablePC.getValueAt(selRows[i], 1);
                            File f = new File(Spath + ext);

                            if (f.isFile()) {
                                f.delete();
                            } else {
                                Explorer.deleteDir(f);
                            }
                        }
                        jTablePC.setModel(exp.setPath(), null);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Logger.writeToLog(ex);
                        //Logger.getLogger(formExplorer.class.getName()).strLog(Level.SEVERE, null, ex);
                    }
                }
            });
            final TableModelListener obj = this;
            miTransfer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < selRows.length; i++) {
                        String Spath = jTextFieldSP.getText() + "/" + jTablePC.getValueAt(selRows[i], 1) + jTablePC.getValueAt(selRows[i], 2);
                        String pPath = jTextFieldPC.getText() + File.separator + jTablePC.getValueAt(selRows[i], 1) + jTablePC.getValueAt(selRows[i], 2);
                        adb.pushFile(pPath, Spath);
                    }
                    adb.getDirContent(jTextFieldSP.getText());
                }
            });

            if (selRows.length == 1 && jTablePC.getValueAt(jTablePC.getSelectedRow(), 3) != null) {
                formExplorer exp = this;
                miView.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int i = jTablePC.getSelectedRow();
                        final String Spath = jTextFieldPC.getText() + File.separator + jTablePC.getValueAt(i, 1) + jTablePC.getValueAt(i, 2);
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                new formWatcher(exp, Spath, null, false).setVisible(true);
                            }
                        });
                    }
                });

                jPopupMenu2.add(miView);
            }

            jPopupMenu2.add(miDelete);
            jPopupMenu2.add(miTransfer);

            jPopupMenu2.show(jTablePC, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTablePCMouseReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            adb.makeScreenshot(jTextFieldSP.getText());
        } catch (Exception e) {
            Logger.writeToLog(e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.showPackages();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        this.showPackages();
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    
    private void showPackages()
    {
        adb.getPackages();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private CustomTable jTablePC;
    private CustomTable jTableSP;
    private javax.swing.JTextArea jTextAreaLog;
    private javax.swing.JTextField jTextFieldPC;
    private javax.swing.JTextField jTextFieldSP;
    private javax.swing.JTextPane jTextPaneDevice;
    // End of variables declaration//GEN-END:variables

    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {

            ModelSPFolders model = (ModelSPFolders) e.getSource();
            if (model != null) {
                if (model.getValueAt(model.getCellOldRow(), 1).toString().equals(model.getCellOldValue())) {
                    return;
                }

                String dir = jTextFieldSP.getText();
                if (dir.length() != 1) {
                    dir += "/";
                }

                String ext = model.getValueAt(model.getCellOldRow(), 2).toString();
                String newValue = dir
                        + model.getValueAt(model.getCellOldRow(), 1).toString()
                        + ext;
                String oldValue = dir + model.getCellOldValue() + ext;

                adb.asyncRenameFile(oldValue, newValue, model);
            }
        }

    }

    public void updateTableModelSP(ModelSPFolders model)
    {
        final TableModelListener obj = this;
        jTableSP.setModel(model, obj);
    }

    public void refreshAfterRename(ModelSPFolders model)
    {
        jTableSP.setValueAt(model.getCellOldValue(),
                model.getCellOldRow(),
                model.getCellOldCol());
    }

    public void refreshTablePC()
    {
        try {
            jTablePC.setModel(exp.setPath(), null);
        } catch (Exception e) {
            Logger.writeToLog(e);
            e.printStackTrace();
        }
    }
}

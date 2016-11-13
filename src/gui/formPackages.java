package gui;

import adb.DataReciever;
import structure.BasicModel;
import structure.ModelPackage;

public class formPackages extends javax.swing.JFrame {

    /**
     * Creates new form formPackages
     *
     * @param mp
     */
    public formPackages(ModelPackage mp) {
        initComponents();
        try {
            jTablePackages.setModel(mp, null);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void checkTabeSelection() {
        if (jTablePackages.getSelectedColumn() > 0) {
            jButton2.setEnabled(true);
        } else {
            jButton2.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePackages = new CustomTable();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });


        jTablePackages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTablePackagesMouseReleased(evt);
            }
        });
        jTablePackages.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablePackagesKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePackages);

        jButton2.setText("Uninstall");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablePackagesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablePackagesKeyReleased
        checkTabeSelection();
    }//GEN-LAST:event_jTablePackagesKeyReleased

    private void jTablePackagesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePackagesMouseReleased
        checkTabeSelection();
    }//GEN-LAST:event_jTablePackagesMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Uninstall selected programs
        try {

            String[] args = {DataReciever.adbPath, "-s", DataReciever.selectedDevice, "uninstall", ""};
            int[] rows = jTablePackages.getSelectedRows();
            for (int i = 0; i < rows.length; i++) {
                args[args.length - 1] = jTablePackages.getValueAt(rows[i], 1).toString();
                String report = DataReciever.executeCommand(args);
                if (report.indexOf("Success") == 0) {
                    ((BasicModel) jTablePackages.getModel()).removeRow(rows[i]);
                }
                //jTablePackages.setModel(DataReciever.getPackages(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private CustomTable jTablePackages;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameinterface;

import javax.swing.JFrame;

/**
 *
 * @author Jonas Lauwers <jonas.lauwers AT gmail.org>
 */
public class DifficultyMenu extends Menu {

    /**
     * Creates new form DifficultyMenu
     */
    public DifficultyMenu(Gui frame) {
        super(frame);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        infoButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        easyButton = new javax.swing.JButton();
        mediumButton = new javax.swing.JButton();
        hardButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1024, 768));
        setMinimumSize(new java.awt.Dimension(1024, 768));
        setPreferredSize(new java.awt.Dimension(1024, 768));

        jPanel2.setMaximumSize(new java.awt.Dimension(120, 70));
        jPanel2.setMinimumSize(new java.awt.Dimension(120, 70));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(120, 70));

        infoButton.setBackground(new java.awt.Color(26, 35, 50));
        infoButton.setForeground(new java.awt.Color(26, 35, 50));
        infoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-Help-1.png"))); // NOI18N
        infoButton.setBorderPainted(false);
        infoButton.setContentAreaFilled(false);
        infoButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        infoButton.setMaximumSize(new java.awt.Dimension(45, 45));
        infoButton.setMinimumSize(new java.awt.Dimension(45, 45));
        infoButton.setPreferredSize(new java.awt.Dimension(45, 45));
        infoButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-Help-Pressed-1.png"))); // NOI18N
        infoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoButtonActionPerformed(evt);
            }
        });

        settingsButton.setBackground(new java.awt.Color(26, 35, 50));
        settingsButton.setForeground(new java.awt.Color(26, 35, 50));
        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-Settings-1.png"))); // NOI18N
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        settingsButton.setMaximumSize(new java.awt.Dimension(45, 45));
        settingsButton.setMinimumSize(new java.awt.Dimension(45, 45));
        settingsButton.setPreferredSize(new java.awt.Dimension(45, 45));
        settingsButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-Settings-Pressed-1.png"))); // NOI18N
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(infoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(settingsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.setMaximumSize(new java.awt.Dimension(400, 650));
        jPanel1.setMinimumSize(new java.awt.Dimension(400, 650));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 650));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Mainframe/Logo-1.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setMaximumSize(new java.awt.Dimension(350, 205));
        jLabel1.setMinimumSize(new java.awt.Dimension(350, 205));
        jLabel1.setPreferredSize(new java.awt.Dimension(350, 205));

        easyButton.setBackground(new java.awt.Color(26, 35, 50));
        easyButton.setFont(getFont());
        easyButton.setForeground(new java.awt.Color(26, 35, 50));
        easyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-2.png"))); // NOI18N
        easyButton.setText("Easy");
        easyButton.setToolTipText("");
        easyButton.setBorderPainted(false);
        easyButton.setContentAreaFilled(false);
        easyButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        easyButton.setIconTextGap(0);
        easyButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        easyButton.setMaximumSize(new java.awt.Dimension(300, 77));
        easyButton.setMinimumSize(new java.awt.Dimension(300, 77));
        easyButton.setPreferredSize(new java.awt.Dimension(300, 77));
        easyButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-Pressed-2.png"))); // NOI18N
        easyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                easyButtonActionPerformed(evt);
            }
        });

        mediumButton.setBackground(new java.awt.Color(26, 35, 50));
        mediumButton.setFont(getFont());
        mediumButton.setForeground(new java.awt.Color(26, 35, 50));
        mediumButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-1.png"))); // NOI18N
        mediumButton.setText("Medium");
        mediumButton.setToolTipText("");
        mediumButton.setBorderPainted(false);
        mediumButton.setContentAreaFilled(false);
        mediumButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mediumButton.setIconTextGap(0);
        mediumButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        mediumButton.setMaximumSize(new java.awt.Dimension(300, 77));
        mediumButton.setMinimumSize(new java.awt.Dimension(300, 77));
        mediumButton.setPreferredSize(new java.awt.Dimension(300, 77));
        mediumButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-Pressed-1.png"))); // NOI18N
        mediumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediumButtonActionPerformed(evt);
            }
        });

        hardButton.setBackground(new java.awt.Color(26, 35, 50));
        hardButton.setFont(getFont());
        hardButton.setForeground(new java.awt.Color(26, 35, 50));
        hardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-3.png"))); // NOI18N
        hardButton.setText("Hard");
        hardButton.setToolTipText("");
        hardButton.setBorderPainted(false);
        hardButton.setContentAreaFilled(false);
        hardButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        hardButton.setIconTextGap(0);
        hardButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        hardButton.setMaximumSize(new java.awt.Dimension(300, 77));
        hardButton.setMinimumSize(new java.awt.Dimension(300, 77));
        hardButton.setPreferredSize(new java.awt.Dimension(300, 77));
        hardButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-Pressed-3.png"))); // NOI18N
        hardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hardButtonActionPerformed(evt);
            }
        });

        backButton.setBackground(new java.awt.Color(26, 35, 50));
        backButton.setFont(getFont());
        backButton.setForeground(new java.awt.Color(26, 35, 50));
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-2.png"))); // NOI18N
        backButton.setText("Back");
        backButton.setToolTipText("");
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        backButton.setIconTextGap(0);
        backButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        backButton.setMaximumSize(new java.awt.Dimension(300, 77));
        backButton.setMinimumSize(new java.awt.Dimension(300, 77));
        backButton.setPreferredSize(new java.awt.Dimension(300, 77));
        backButton.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Buttons/Button-Pressed-2.png"))); // NOI18N
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(easyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mediumButton, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(easyButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(mediumButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(hardButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(312, 312, 312)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(312, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void easyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_easyButtonActionPerformed
        frame.setDifficulty("easy");
        frame.start();
        frame.switchMenu("game");
    }//GEN-LAST:event_easyButtonActionPerformed

    private void mediumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mediumButtonActionPerformed
        frame.setDifficulty("medium");
        frame.start();
        frame.switchMenu("game");
    }//GEN-LAST:event_mediumButtonActionPerformed

    private void hardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hardButtonActionPerformed
        frame.setDifficulty("hard");
        frame.start();
        frame.switchMenu("game");
    }//GEN-LAST:event_hardButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        frame.switchMenu("main");
    }//GEN-LAST:event_backButtonActionPerformed

    private void infoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoButtonActionPerformed
        frame.switchMenu("info");
    }//GEN-LAST:event_infoButtonActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        frame.switchMenu("settings");
    }//GEN-LAST:event_settingsButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton easyButton;
    private javax.swing.JButton hardButton;
    private javax.swing.JButton infoButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton mediumButton;
    private javax.swing.JButton settingsButton;
    // End of variables declaration//GEN-END:variables
}

/*
 * Autopsy Forensic Browser
 *
 * Copyright 2011 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.casemodule;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * The "Add Image" wizard panel 2. Provides checkbox to enable indexing, button
 * to start process, and progress bar.
 */
final class AddImageVisualPanel2 extends JPanel {

    private AddImageLoadingPanel loadingPanel;
    private AddImageDonePanel donePanel;

    /**
     * Creates new form AddImageVisualPanel2
     */
    AddImageVisualPanel2() {
        loadingPanel = new AddImageLoadingPanel();
        donePanel = new AddImageDonePanel();
        initComponents();
        customizeComponents();
    }

    private void customizeComponents() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.removeAll();
        mainPanel.add(loadingPanel, BorderLayout.CENTER);
        mainPanel.validate();
        mainPanel.repaint();
    }
    
    AddImageLoadingPanel getLoadingPanel() {
        return loadingPanel;
    }
    
    AddImageDonePanel getDonePanel() {
        return donePanel;
    }
    
    void done() {
        mainPanel.removeAll();
        mainPanel.add(donePanel, BorderLayout.CENTER);
        mainPanel.validate();
        mainPanel.repaint();
    }

    void resetInfoPanel() {
        loadingPanel.resetInfoPanel();
    }

    /**
     * Returns the name of the this panel. This name will be shown on the left
     * panel of the "Add Image" wizard panel.
     *
     * @return name the name of this panel
     */
    @Override
    public String getName() {
        return "Add Data Source";
    }

    public JProgressBar getCrDbProgressBar() {
        return loadingPanel.getCrDbProgressBar();
    }

    public JLabel getProgressLabel() {
        return loadingPanel.getProgressLabel();
    }

    /**
     * Changes the progress bar text and color.
     *
     * @param text the text to be shown
     * @param value the current value of the progress bar
     * @param color the color of the progress bar text
     */
    public void changeProgressBarTextAndColor(String text, int value, Color color) {
        loadingPanel.changeProgressBarTextAndColor(text, value, color);
    }
    
    /**
     * append progress text to progress label
     * @param text 
     */
    public void appendProgressText(String text) {
        loadingPanel.appendProgressText(text);
    }
    
    /**
     * Updates the currently processing directory
     * @param dir the text to update with
     */
    public void changeCurrentDir(String dir){
        loadingPanel.changeCurrentDir(dir);
    }
    
    /**
     * Sets the CurrentlyProcessing tag and text area to be invisible
     */
    public void setProcessInvis(){
        loadingPanel.setProcessInvis();
    }

    void setErrors(final String errors, boolean critical) {
        loadingPanel.setErrors(errors, critical);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udpserver;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author ZENBOOK
 */
public class UDPui extends javax.swing.JFrame {

    private Thread backgroundProcess;

//    private Boolean running = false;
    private Runnable background;
    
    private DatagramSocket serverSocket;
    private Boolean available = true;
    private XYSeries series;

    /**
     * Creates new form UDPui
     */
    public UDPui() {
        // <editor-fold defaultstate="collapsed" desc="Graph">
        series = new XYSeries("ECG Reading");
        series.setMaximumItemCount(50);
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart("ECG Reading", "Time (seconds)", "Voltage (volt)", dataset);
        
        final XYPlot plot = chart.getXYPlot();
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        
        
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.setVisible(true);
        jPanel1.setSize(600, 500);
        jPanel1.add(new ChartPanel(chart),BorderLayout.CENTER);
        jPanel1.validate();
        add(jPanel1);
        // </editor-fold>
        initComponents();
        
        background = new Runnable(){
            public void run(){
                try {
                        serverSocket = new DatagramSocket(9876);
                    } catch (SocketException ex) {
                        Logger.getLogger(UDPui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                while(true){
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    try {
                        serverSocket.receive(receivePacket);
                    } catch (IOException ex) {
                        Logger.getLogger(UDPui.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            available = false;
                            System.out.println("Finish Timer");
                        }
                    }, 65 * 1000);

                    if (!new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength()).equals("")) {
                        int count = 1;
                        while (true) {
                            try {
                                receiveData = new byte[1024];
                                byte[] sendData = new byte[1024];
                                receivePacket = new DatagramPacket(receiveData, receiveData.length);

                                serverSocket.receive(receivePacket);
                                String message = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());

                                if (message.equals("end")) {
                                    valuePane.setCaretPosition(valuePane.getDocument().getLength());
                                    //send back to mobile
                                    InetAddress IPAddress = receivePacket.getAddress();
                                    int port = receivePacket.getPort();
                                    String capitalizedSentence = count + "";
                                    sendData = capitalizedSentence.getBytes();
                                    DatagramPacket sendPacket
                                            = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                                    serverSocket.send(sendPacket);
                                    //end send back to mobile
                                    count = 1;
                                    break;
                                } else if (available) {
                                    
                                    series.add(count, Double.parseDouble(message));
                                    
                                    
                                    valuePane.setText(valuePane.getText().toString() + count + ":" + message + "\n");
//                                    valuePane.setCaretPosition(valuePane.getDocument().getLength());
                                    count++;
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(UDPui.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        };
        backgroundProcess = new Thread(background);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stBT = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        valuePane = new javax.swing.JTextArea();
        graphPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        stBT.setText("START");
        stBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stBTActionPerformed(evt);
            }
        });

        valuePane.setColumns(20);
        valuePane.setRows(5);
        valuePane.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                valuePaneInputMethodTextChanged(evt);
            }
        });
        jScrollPane1.setViewportView(valuePane);

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(stBT)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(stBT)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stBTActionPerformed
        // TODO add your handling code here:
//        if(stBT.getText().toString().equals("START")){
//            stBT.setText("STOP");
        valuePane.setText("");
        series.clear();
        available = true;
        if (!backgroundProcess.isAlive()) {
            stBT.setText("CLEAR");
            backgroundProcess.start();
        }

//        }else{
//            stBT.setText("START");
//        }
    }//GEN-LAST:event_stBTActionPerformed

    private void valuePaneInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_valuePaneInputMethodTextChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_valuePaneInputMethodTextChanged

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UDPui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UDPui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UDPui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UDPui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UDPui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel graphPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton stBT;
    private javax.swing.JTextArea valuePane;
    // End of variables declaration//GEN-END:variables
}

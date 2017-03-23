/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arm;

import arm.SIMDExt.BancoVectores;
import arm.assembler.Decodificacion;
import arm.compiler.BancoInstrucciones;
import arm.compiler.ManejadorErrores;
import arm.compiler.Principal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import arm.help.Converter;
import arm.scalar.BancoBanderas;
import arm.scalar.BancoBranch;
import arm.scalar.BancoRegistros;
import arm.scalar.Memoria;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;

public class MainFrame extends javax.swing.JFrame {

    private CustomDocumentFilter docFilter;

    private ArrayList<JTextPane> paneList;
    private int numScripts;
    private int numTabs;

    private Path pathDeArchivo;
    private String retornoArchivo;

    private boolean binFlag;
    private boolean decFlag;
    private boolean hexFlag;
    public static boolean modified;

    public static String regMod;

    public static String performanceData;
    public static boolean modifiedPerformance;

    private String stepCode;
    private boolean stepFlag;

    private Scanner stepScanner;

    BancoRegistros bancoRegistros = new BancoRegistros();
    BancoVectores bancoVectores = new BancoVectores();
    BancoBanderas bancoBanderas = new BancoBanderas();
    BancoBranch bancoBranch = new BancoBranch();
    Decodificacion ensamblador = new Decodificacion();
    Memoria memoria = new Memoria();

    //public static boolean tomasuloUpdate = false;
    public MainFrame() throws InterruptedException, IOException {
        initComponents();
        iniciarComponentes();
        this.setName("ARM-SIM");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);
        jPanel1.setBackground(Color.LIGHT_GRAY);
        jPanel2.setBackground(Color.LIGHT_GRAY);

        this.paneList = new ArrayList<>();
        paneList.add(mainTextPane);
        numScripts = 1;
        numTabs = 0;

        jTabbedPane1.setToolTipTextAt(0, "Script 1");
        stepCode = "";
        stepFlag = false;

        TextLineNumber lineNum = new TextLineNumber(mainTextPane);
        jScrollPane3.setRowHeaderView(lineNum);

        docFilter = new CustomDocumentFilter(mainTextPane);
        docFilter.setPane();

        decFlag = false;
        binFlag = false;
        hexFlag = true;

    }

    private void updateRegisters(ArrayList<Long> pRegisters) {
        for (int i = 0; i < pRegisters.size(); i++) {
            long tempVal = 0;
            tempVal = pRegisters.get(i);

            String stringRegVal = "";

            if (hexFlag) {
                stringRegVal = Long.toHexString(tempVal);
                stringRegVal = "0x" + stringRegVal.toUpperCase();
            } else if (binFlag) {
                stringRegVal = Long.toBinaryString(tempVal);
                stringRegVal = stringRegVal.toUpperCase();
            }
            switch (i) {
                case 0:
                    r0Tv.setText(stringRegVal);
                    break;
                case 1:
                    r1Tv.setText(stringRegVal);
                    break;
                case 2:
                    r2Tv.setText(stringRegVal);
                    break;
                case 3:
                    r3Tv.setText(stringRegVal);
                    break;
                case 4:
                    r4Tv.setText(stringRegVal);
                    break;
                case 5:
                    r5Tv.setText(stringRegVal);
                    break;
                case 6:
                    r6Tv.setText(stringRegVal);
                    break;
                case 7:
                    r7Tv.setText(stringRegVal);
                    break;
                case 8:
                    r8Tv.setText(stringRegVal);
                    break;
                case 9:
                    r9Tv.setText(stringRegVal);
                    break;
                case 10:
                    r10Tv.setText(stringRegVal);
                    break;
                case 11:
                    r11Tv.setText(stringRegVal);
                    break;
                case 12:
                    r12Tv.setText(stringRegVal);
                    break;
                case 13:
                    r13Tv.setText(stringRegVal);
                    break;
                case 14:
                    r14Tv.setText(stringRegVal);
                    break;
                case 15:
                    r15Tv.setText(stringRegVal);
                    break;
            }
        }
    }

    public void setFlags(ArrayList<String> pFlags) {
        for (int i = 0; i < pFlags.size(); i++) {
            String tempVal = pFlags.get(i);
            if (i == 0) {
                nTv.setText(tempVal);
            } else if (i == 1) {
                zTv.setText(tempVal);
            } else if (i == 2) {
                cTv.setText(tempVal);
            } else {
                vTv.setText(tempVal);
            }
        }
    }

    public void iniciarComponentes() throws IOException {
        ArrayList<Long> registros = new ArrayList<>();
        try {
            registros = bancoRegistros.obtenerRegistrosIniciales();
        } catch (IOException ex) {
            System.out.println("Error obteniendo los registros a imprimir");
        }
        //updateRegisters(registros);

        ArrayList<String> banderasString = bancoBanderas.obtenerBanderasIniciales();
        setFlags(banderasString);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        newBtn = new javax.swing.JButton();
        openBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        runBtn = new javax.swing.JButton();
        resBtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        mainTextPane = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        r0Tv = new javax.swing.JTextField();
        r1Tv = new javax.swing.JTextField();
        r2Tv = new javax.swing.JTextField();
        r3Tv = new javax.swing.JTextField();
        r4Tv = new javax.swing.JTextField();
        r5Tv = new javax.swing.JTextField();
        r6Tv = new javax.swing.JTextField();
        r7Tv = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        r8Tv = new javax.swing.JTextField();
        r9Tv = new javax.swing.JTextField();
        r10Tv = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        r11Tv = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        r12Tv = new javax.swing.JTextField();
        r13Tv = new javax.swing.JTextField();
        r14Tv = new javax.swing.JTextField();
        r15Tv = new javax.swing.JTextField();
        decBtn = new javax.swing.JButton();
        binBtn = new javax.swing.JButton();
        hexBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        stepFBtn = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        nTv = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        zTv = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        cTv = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        vTv = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        exeTimeTv = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenu = new javax.swing.JMenuItem();
        openMenu = new javax.swing.JMenuItem();
        saveMenu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenu = new javax.swing.JMenuItem();
        runningMenu = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        maCodeMenu = new javax.swing.JMenuItem();
        meMapMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        newBtn.setText("New");
        newBtn.setToolTipText("New Script");
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        openBtn.setText("Open");
        openBtn.setToolTipText("Open Script");
        openBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openBtnActionPerformed(evt);
            }
        });

        saveBtn.setText("Save");
        saveBtn.setToolTipText("Save Script");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        runBtn.setText("Run");
        runBtn.setToolTipText("Run actual Script");
        runBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runBtnActionPerformed(evt);
            }
        });

        resBtn.setText("Reset");
        resBtn.setToolTipText("Reset");
        resBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resBtnActionPerformed(evt);
            }
        });

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setAutoscrolls(true);

        jScrollPane3.setViewportView(mainTextPane);

        jTabbedPane1.addTab("Script 1", jScrollPane3);
        jScrollPane3.getAccessibleContext().setAccessibleParent(jTabbedPane1);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setAutoscrolls(true);

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel2.setText("R0");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel3.setText("R1");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel4.setText("R2");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel5.setText("R3");

        jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel6.setText("R4");

        jLabel7.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel7.setText("R5");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel8.setText("R6");

        jLabel9.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel9.setText("R7");

        r0Tv.setEditable(false);
        r0Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r0Tv.setText("0x0");
        r0Tv.setToolTipText("");

        r1Tv.setEditable(false);
        r1Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r1Tv.setText("0x0");

        r2Tv.setEditable(false);
        r2Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r2Tv.setText("0x0");

        r3Tv.setEditable(false);
        r3Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r3Tv.setText("0x0");

        r4Tv.setEditable(false);
        r4Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r4Tv.setText("0x0");

        r5Tv.setEditable(false);
        r5Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r5Tv.setText("0x0");

        r6Tv.setEditable(false);
        r6Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r6Tv.setText("0x0");
        r6Tv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r6TvActionPerformed(evt);
            }
        });

        r7Tv.setEditable(false);
        r7Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r7Tv.setText("0x0");

        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel10.setText("R8");

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel11.setText("R9");

        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel12.setText("R10");

        r8Tv.setEditable(false);
        r8Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r8Tv.setText("0x0");

        r9Tv.setEditable(false);
        r9Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r9Tv.setText("0x0");

        r10Tv.setEditable(false);
        r10Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r10Tv.setText("0x0");

        jLabel13.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel13.setText("R11");

        r11Tv.setEditable(false);
        r11Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r11Tv.setText("0x0");

        jLabel14.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel14.setText("R12");

        jLabel15.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel15.setText("R13");

        jLabel16.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel16.setText("R14");

        jLabel17.setFont(new java.awt.Font("Ubuntu", 1, 16)); // NOI18N
        jLabel17.setText("R15");

        r12Tv.setEditable(false);
        r12Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r12Tv.setText("0x0");

        r13Tv.setEditable(false);
        r13Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r13Tv.setText("0x0");

        r14Tv.setEditable(false);
        r14Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r14Tv.setText("0x0");

        r15Tv.setEditable(false);
        r15Tv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r15Tv.setText("0x0");
        r15Tv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r15TvActionPerformed(evt);
            }
        });

        decBtn.setText("Dec");
        decBtn.setToolTipText("Decimal");
        decBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decBtnActionPerformed(evt);
            }
        });

        binBtn.setText("Bin");
        binBtn.setToolTipText("Binary");
        binBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                binBtnActionPerformed(evt);
            }
        });

        hexBtn.setText("Hex");
        hexBtn.setToolTipText("Hexadecimal");
        hexBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hexBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setText("REGISTERS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(r15Tv, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(r14Tv))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(r13Tv))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(r12Tv))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(r11Tv))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(r10Tv))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(27, 27, 27)
                                .addComponent(r9Tv))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(27, 27, 27)
                                .addComponent(r8Tv))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(27, 27, 27)
                                .addComponent(r7Tv))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(r5Tv, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(r4Tv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                    .addComponent(r6Tv)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(r2Tv, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(r1Tv, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(r0Tv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                    .addComponent(r3Tv)))
                            .addComponent(jLabel6))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(decBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(binBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hexBtn)
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(decBtn)
                    .addComponent(binBtn)
                    .addComponent(hexBtn)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(r0Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(r1Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(r2Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(r3Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(r4Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(r5Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(r6Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(r7Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(r8Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(r9Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(r10Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(r11Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(r12Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(r13Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(r14Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(r15Tv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        jScrollPane2.setViewportView(jPanel1);

        jLabel18.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel18.setText("ARM-SIM");

        stepFBtn.setText("Step-Forward");
        stepFBtn.setToolTipText("Step Forward");
        stepFBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepFBtnActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel19.setText("N:");

        nTv.setEditable(false);
        nTv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nTvActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel20.setText("Z:");

        zTv.setEditable(false);

        jLabel21.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel21.setText("C:");

        cTv.setEditable(false);

        jLabel22.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel22.setText("V:");

        vTv.setEditable(false);

        jLabel23.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel23.setText("Execution Time:");

        exeTimeTv.setEditable(false);
        exeTimeTv.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel24.setText("V REGISTERS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addContainerGap(567, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        fileMenu.setText("File");

        newMenu.setText("New Script");
        newMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuActionPerformed(evt);
            }
        });
        fileMenu.add(newMenu);

        openMenu.setText("Open Script");
        openMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuActionPerformed(evt);
            }
        });
        fileMenu.add(openMenu);

        saveMenu.setText("Save Script");
        saveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenu);
        fileMenu.add(jSeparator1);

        exitMenu.setText("Exit");
        exitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenu);

        jMenuBar1.add(fileMenu);

        runningMenu.setText("Running");

        jMenuItem6.setText("Run");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        runningMenu.add(jMenuItem6);

        jMenuItem7.setText("Reset");
        runningMenu.add(jMenuItem7);

        jMenuBar1.add(runningMenu);

        toolsMenu.setText("Tools");

        maCodeMenu.setText("View Machine Code");
        maCodeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maCodeMenuActionPerformed(evt);
            }
        });
        toolsMenu.add(maCodeMenu);

        meMapMenu.setText("View Memory Map");
        meMapMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meMapMenuActionPerformed(evt);
            }
        });
        toolsMenu.add(meMapMenu);

        jMenuBar1.add(toolsMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(runBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stepFBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(3, 3, 3)
                        .addComponent(nTv, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addGap(3, 3, 3)
                        .addComponent(zTv, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addGap(3, 3, 3)
                        .addComponent(cTv, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addGap(3, 3, 3)
                        .addComponent(vTv, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(newBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resBtn))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 86, Short.MAX_VALUE)
                        .addComponent(jLabel23)
                        .addGap(2, 2, 2)
                        .addComponent(exeTimeTv, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel18))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newBtn)
                            .addComponent(openBtn)
                            .addComponent(saveBtn)
                            .addComponent(runBtn)
                            .addComponent(stepFBtn)
                            .addComponent(resBtn)
                            .addComponent(jLabel23)
                            .addComponent(exeTimeTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(nTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(zTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(cTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(vTv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        // TODO add your handling code here:
        newScript("");
    }//GEN-LAST:event_newBtnActionPerformed

    private void r6TvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r6TvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r6TvActionPerformed

    private void newMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuActionPerformed
        // TODO add your handling code here:
        newScript("");
    }//GEN-LAST:event_newMenuActionPerformed

    private void openBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openBtnActionPerformed
        // TODO add your handling code here:
        openScript();
    }//GEN-LAST:event_openBtnActionPerformed

    private void openMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuActionPerformed
        // TODO add your handling code here:
        openScript();
    }//GEN-LAST:event_openMenuActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        // TODO add your handling code here:
        saveScript();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
        // TODO add your handling code here:
        saveScript();
    }//GEN-LAST:event_saveMenuActionPerformed

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuActionPerformed
        // TODO add your handling code here:
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_exitMenuActionPerformed

    private void decBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decBtnActionPerformed
        // TODO add your handling code here:
        this.decFlag = true;
        convertion(0);
    }//GEN-LAST:event_decBtnActionPerformed

    private void binBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_binBtnActionPerformed
        // TODO add your handling code here:
        this.binFlag = true;
        convertion(1);
    }//GEN-LAST:event_binBtnActionPerformed

    private void hexBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hexBtnActionPerformed
        // TODO add your handling code here:
        this.hexFlag = true;
        convertion(2);
    }//GEN-LAST:event_hexBtnActionPerformed

    private void runBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runBtnActionPerformed
        // TODO add your handling code here:
        runScript();
    }//GEN-LAST:event_runBtnActionPerformed

    private void meMapMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meMapMenuActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            MemoryMap window;

            public void run() {
                window = new MemoryMap();
                window.setVisible(true);
                window.updateMap();
            }
        });
    }//GEN-LAST:event_meMapMenuActionPerformed

    private void stepFBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepFBtnActionPerformed
        // TODO add your handling code here:
        runStepScript();
    }//GEN-LAST:event_stepFBtnActionPerformed

    private void resBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resBtnActionPerformed
        try {
            // TODO add your handling code here:
            resetFunction();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_resBtnActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        runScript();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void r15TvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_r15TvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_r15TvActionPerformed

    private void nTvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nTvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nTvActionPerformed

    private void maCodeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maCodeMenuActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            AssembleFrame window;

            public void run() {
                window = new AssembleFrame();
                window.setVisible(true);
            }
        });
    }//GEN-LAST:event_maCodeMenuActionPerformed

    private void resetFunction() throws IOException {

        ensamblador.borrarEnsamblaje();
        memoria.inicia_memoria();
        //Borrar el contenido actual de las instrucciones, banderas, ensamblaje, memoria, branch y registros
        //bancoInstrucciones.borrarInstrucciones();
        bancoBanderas.borrarBanderas();

        bancoRegistros.borrarRegistros();
        //moduloErrores.borrarErrores();
        bancoBranch.borrarBranch();

        this.stepFlag = false;

        if (this.hexFlag) {
            r1Tv.setText("0x");
            r2Tv.setText("0x");
            r3Tv.setText("0x");
            r4Tv.setText("0x");
            r5Tv.setText("0x");
            r6Tv.setText("0x");
            r7Tv.setText("0x");
            r8Tv.setText("0x");
            r9Tv.setText("0x");
            r10Tv.setText("0x");
            r11Tv.setText("0x");
            r12Tv.setText("0x");
            r13Tv.setText("0x");
            r14Tv.setText("0x");
            r15Tv.setText("0x");
            r0Tv.setText("0x");
        }
        if (this.decFlag || this.binFlag) {
            r1Tv.setText("0");
            r2Tv.setText("0");
            r3Tv.setText("0");
            r4Tv.setText("0");
            r5Tv.setText("0");
            r6Tv.setText("0");
            r7Tv.setText("0");
            r8Tv.setText("0");
            r9Tv.setText("0");
            r10Tv.setText("0");
            r11Tv.setText("0");
            r12Tv.setText("0");
            r13Tv.setText("0");
            r14Tv.setText("0");
            r15Tv.setText("0");
            r0Tv.setText("0");
        }
    }
    
    private int stepIndex = -1;
    int limit = 5;
    
    Principal compiladorStep = new Principal();
            BancoInstrucciones bancoInstruccionesStep = new BancoInstrucciones();
            ManejadorErrores moduloErroresStep = new ManejadorErrores();

    private void runStepScript() {
        //System.out.println("Missing!!!");
        this.stepFlag = true;
        stepIndex += 1;
        
    try {
        if(stepIndex < limit) {
            
            
            if(stepIndex == 0) {
                ensamblador.borrarEnsamblaje();
                memoria.inicia_memoria();
            }
            
            //bancoBranch.borrarBranch();

            String source = "";
            JTextPane tempPane = null;
            int textPaneSelected = jTabbedPane1.getSelectedIndex();

            if (textPaneSelected != -1) {
                tempPane = paneList.get(textPaneSelected);
            }

            if (tempPane != null) {
                Element root = tempPane.getDocument().getDefaultRootElement();
                Element child = root.getElement(stepIndex);
                
                int start = child.getStartOffset();
                int end = child.getEndOffset();
                int length = end - start;
                source = child.getDocument().getText(start, length);
                
               /* if(source.equals("\n")) {
                    stepIndex +=1;
                    child = root.getElement(stepIndex);
                    source = child.getDocument().getText(start, length);
                }*/
                
                limit = root.getElementCount();

                System.out.println("Source:"+source+"*--");
                
            }

            String instruccionesString = source + "\n" + "\n" + "ADD R0, R0, #0";
            //System.out.println(instruccionesString);
            try {
                compiladorStep.principal(instruccionesString);
            } catch (IOException ex) {
                System.out.println("Error en el boton");
            }
            String vectorsText = "Vectors" + "\n";
            vectorsText += bancoVectores.obtenerVectores();
            jLabel24.setText("<html>" + vectorsText.replaceAll("\n", "<br/>") + "</html>");

            //Impresion de los registros en pantalla
            ArrayList<Long> registros = new ArrayList<>();
            try {
                registros = bancoRegistros.obtenerRegistros();
            } catch (IOException ex) {
                System.out.println("Error obteniendo los registros a imprimir");
            }
            updateRegisters(registros);

            //Impresion de las banderas en pantalla
            ArrayList<String> banderasString = bancoBanderas.obtenerBanderas();
            setFlags(banderasString);

            //Hacer el aproximado de ejecucion
            int cantidadInstrucciones = bancoInstruccionesStep.cantidadInstrucciones();
            double tiempoEjecucion = 0.01 * cantidadInstrucciones;
            String tiempoEjecucionString = tiempoEjecucion + " ms";
            exeTimeTv.setText(tiempoEjecucionString);

            //Borrar el contenido actual de las instrucciones, banderas, ensamblaje, memoria, branch y registros
         //   bancoInstrucciones.borrarInstrucciones();
         //   bancoBanderas.borrarBanderas();

           // bancoRegistros.borrarRegistros();
           // moduloErrores.borrarErrores();
          //  bancoBranch.borrarBranch();
        }
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadLocationException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void runScript() {

        this.stepFlag = false;

        try {
            Principal compilador = new Principal();
            BancoInstrucciones bancoInstrucciones = new BancoInstrucciones();
            ManejadorErrores moduloErrores = new ManejadorErrores();
            ensamblador.borrarEnsamblaje();
            memoria.inicia_memoria();
            //bancoBranch.borrarBranch();

            String source = "";
            JTextPane tempPane = null;
            int textPaneSelected = jTabbedPane1.getSelectedIndex();

            if (textPaneSelected != -1) {
                tempPane = paneList.get(textPaneSelected);
            }

            if (tempPane != null) {
                source = tempPane.getText();
            }

            String instruccionesString = source + "\n" + "\n" + "ADD R0, R0, #0";
            System.out.println(instruccionesString);
            try {
                compilador.principal(instruccionesString);
            } catch (IOException ex) {
                System.out.println("Error en el boton");
            }
            String vectorsText = "Vectors" + "\n";
            vectorsText += bancoVectores.obtenerVectores();
            jLabel24.setText("<html>" + vectorsText.replaceAll("\n", "<br/>") + "</html>");

            //Impresion de los registros en pantalla
            ArrayList<Long> registros = new ArrayList<>();
            try {
                registros = bancoRegistros.obtenerRegistros();
            } catch (IOException ex) {
                System.out.println("Error obteniendo los registros a imprimir");
            }
            updateRegisters(registros);

            //Impresion de las banderas en pantalla
            ArrayList<String> banderasString = bancoBanderas.obtenerBanderas();
            setFlags(banderasString);

            //Hacer el aproximado de ejecucion
            int cantidadInstrucciones = bancoInstrucciones.cantidadInstrucciones();
            double tiempoEjecucion = 0.01 * cantidadInstrucciones;
            String tiempoEjecucionString = tiempoEjecucion + " ms";
            exeTimeTv.setText(tiempoEjecucionString);

            //Borrar el contenido actual de las instrucciones, banderas, ensamblaje, memoria, branch y registros
            bancoInstrucciones.borrarInstrucciones();
            bancoBanderas.borrarBanderas();

            bancoRegistros.borrarRegistros();
            moduloErrores.borrarErrores();
            bancoBranch.borrarBranch();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void convertion(int pType) {
        if (hexFlag && (pType == 0)) {
            this.hexFlag = false;
            convertionAux(0);
        }
        if (binFlag && (pType == 0)) {
            this.binFlag = false;
            convertionAux(1);
        }
        if (decFlag && (pType == 1)) {
            this.decFlag = false;
            convertionAux(2);
        }
        if (hexFlag && (pType == 1)) {
            this.hexFlag = false;
            convertionAux(3);
        }
        if (decFlag && (pType == 2)) {
            this.decFlag = false;
            convertionAux(4);
        }
        if (binFlag && (pType == 2)) {
            this.binFlag = false;
            convertionAux(5);
        }
    }

    private void convertionAux(int pOption) {
        Converter conv = new Converter();

        ArrayList<JTextField> list = doTvList();

        for (int i = 0; i < list.size(); i++) {
            switch (pOption) {
                case 0:
                    list.get(i).setText(conv.hexToDec(list.get(i).getText()));
                    break;
                case 1:
                    list.get(i).setText(conv.binToDec(list.get(i).getText()));
                    break;
                case 2:
                    list.get(i).setText(conv.decToBin(list.get(i).getText()));
                    break;
                case 3:
                    list.get(i).setText(conv.hexToBin(list.get(i).getText()));
                    break;
                case 4:
                    list.get(i).setText(conv.decToHex(list.get(i).getText()));
                    break;
                case 5:
                    list.get(i).setText(conv.binToHex(list.get(i).getText()));
                    break;
            }
        }
    }

    private ArrayList doTvList() {
        ArrayList<JTextField> list = new ArrayList<>();
        list.add(r0Tv);
        list.add(r1Tv);
        list.add(r2Tv);
        list.add(r3Tv);
        list.add(r4Tv);
        list.add(r5Tv);
        list.add(r6Tv);
        list.add(r7Tv);
        list.add(r8Tv);
        list.add(r9Tv);
        list.add(r10Tv);
        list.add(r11Tv);
        list.add(r12Tv);
        list.add(r13Tv);
        list.add(r14Tv);
        list.add(r15Tv);

        return list;
    }

    private void newScript(String pContent) {
        this.stepFlag = false;

        JTextPane newScriptPane = new JTextPane();
        newScriptPane.setLayout(new BorderLayout());
        newScriptPane.setText(pContent);

        CustomDocumentFilter tempDocFil = new CustomDocumentFilter(newScriptPane);
        tempDocFil.setPane();

        JScrollPane sp = new JScrollPane();
        sp.setAutoscrolls(true);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setViewportView(newScriptPane);

        TextLineNumber lineNum = new TextLineNumber(newScriptPane);
        sp.setRowHeaderView(lineNum);

        jTabbedPane1.addTab("Script " + Integer.toString(numScripts += 1), sp);
        jTabbedPane1.setSelectedIndex(numTabs += 1);
        jTabbedPane1.setToolTipTextAt(numTabs, "Script " + Integer.toString(numScripts));

        paneList.add(newScriptPane);

    }

    private void openScript() {
        this.stepFlag = false;

        JFileChooser Buscador = new JFileChooser();
        Buscador.setAcceptAllFileFilterUsed(false);
        Buscador.setMultiSelectionEnabled(false);
        Buscador.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
        Buscador.showOpenDialog(Buscador);
        File file = Buscador.getSelectedFile();
        try {
            pathDeArchivo = Paths.get(file.getAbsolutePath());
            retornoArchivo = new String(Files.readAllBytes(pathDeArchivo));
            newScript(retornoArchivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR, we can't load the file");
        }
    }

    private void saveScript() {
        JFileChooser saveFile = new JFileChooser();
        //saveFile.showSaveDialog(null);
        int returnVal = saveFile.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter(saveFile.getSelectedFile().getPath() + ".txt"));
                JTextPane tempPane = null;
                int textPaneSelected = jTabbedPane1.getSelectedIndex();
                if (textPaneSelected != -1) {
                    tempPane = paneList.get(textPaneSelected);
                }
                if (tempPane != null) {
                    out.write(tempPane.getText());
                }
            } catch (IOException ex) {
                System.out.println("ERROR, with saving file");
            } finally {
                try {
                    out.close();

                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton binBtn;
    private javax.swing.JTextField cTv;
    private javax.swing.JButton decBtn;
    private javax.swing.JTextField exeTimeTv;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton hexBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuItem maCodeMenu;
    private javax.swing.JTextPane mainTextPane;
    private javax.swing.JMenuItem meMapMenu;
    private javax.swing.JTextField nTv;
    private javax.swing.JButton newBtn;
    private javax.swing.JMenuItem newMenu;
    private javax.swing.JButton openBtn;
    private javax.swing.JMenuItem openMenu;
    private javax.swing.JTextField r0Tv;
    private javax.swing.JTextField r10Tv;
    private javax.swing.JTextField r11Tv;
    private javax.swing.JTextField r12Tv;
    private javax.swing.JTextField r13Tv;
    private javax.swing.JTextField r14Tv;
    private javax.swing.JTextField r15Tv;
    private javax.swing.JTextField r1Tv;
    private javax.swing.JTextField r2Tv;
    private javax.swing.JTextField r3Tv;
    private javax.swing.JTextField r4Tv;
    private javax.swing.JTextField r5Tv;
    private javax.swing.JTextField r6Tv;
    private javax.swing.JTextField r7Tv;
    private javax.swing.JTextField r8Tv;
    private javax.swing.JTextField r9Tv;
    private javax.swing.JButton resBtn;
    private javax.swing.JButton runBtn;
    private javax.swing.JMenu runningMenu;
    private javax.swing.JButton saveBtn;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JButton stepFBtn;
    private javax.swing.JMenu toolsMenu;
    private javax.swing.JTextField vTv;
    private javax.swing.JTextField zTv;
    // End of variables declaration//GEN-END:variables

}

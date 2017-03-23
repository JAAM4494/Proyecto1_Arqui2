/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import arm.MemoryMap;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import static java.lang.Thread.sleep;

/**
 *
 * @author jaam
 */
public class AppMainFrame extends javax.swing.JFrame {

    private Path pathDeArchivo;
    private String pathRelativo;
    private String retornoArchivo;

    private ArrayList<ArrayList<ColorModel>> mainMatrix;
    
    private ImageManager manager;

    /**
     * Creates new form AppMainFrame
     */
    public AppMainFrame() {
        initComponents();

        this.setName("Encrypt-App");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);
        jPanel1.setBackground(Color.LIGHT_GRAY);
        jPanel2.setBackground(Color.LIGHT_GRAY);
        jPanel3.setBackground(Color.LIGHT_GRAY);
        jPanel5.setBackground(Color.LIGHT_GRAY);

        //originalImage.setIcon(new ImageIcon(getClass().getResource("/application/images/firefox.jpg")));
        //encryptedImage.setIcon(new ImageIcon(getClass().getResource("/application/images/firefox.jpg")));
        //desencryptedImage.setIcon(new ImageIcon(getClass().getResource("/application/images/firefox.jpg")));
    }
   

    private void encryptFunction() throws IOException {

        ImageProcessing process = new ImageProcessing();

        int index = algorithmsBox.getSelectedIndex();

        String key = new String(privateKeyEntry.getPassword());
        int shiftIndex = shiftNumberBox.getSelectedIndex();
        String shiftSelection = shiftNumberBox.getItemAt(shiftIndex);

        String redColor = new String(redColorEntry.getPassword());
        String greenColor = new String(greenColorEntry.getPassword());
        String blueColor = new String(blueColorEntry.getPassword());
        String alphaColor = new String(alphaValueEntry.getPassword());

        ColorModel keyVector = new ColorModel();
        keyVector.setRed(Integer.parseInt(redColor));
        keyVector.setGreen(Integer.parseInt(greenColor));
        keyVector.setBlue(Integer.parseInt(blueColor));
        keyVector.setAlpha(Integer.parseInt(alphaColor));

        switch (index) {
            case 0:
                process.encryptImage(mainMatrix, "xor", Integer.parseInt(key),
                        Integer.parseInt(shiftSelection), keyVector);
                break;
            case 1:
                process.encryptImage(mainMatrix, "shift-s", Integer.parseInt(key),
                        Integer.parseInt(shiftSelection), keyVector);
                break;
            case 2:
                process.encryptImage(mainMatrix, "shift-c", Integer.parseInt(key),
                        Integer.parseInt(shiftSelection), keyVector);
                break;
            default:
                process.encryptImage(mainMatrix, "add", Integer.parseInt(key),
                        Integer.parseInt(shiftSelection), keyVector);
                break;
        }
        manager.buildImage(process.getEncryptedImageArray(),"encrypted");
    }
    
    

    private void desencryptFunction() throws IOException {
        
        ImageProcessing process = new ImageProcessing();

        int index = algorithmsBox.getSelectedIndex();

        String key = new String(privateKeyEntry.getPassword());
        int shiftIndex = shiftNumberBox.getSelectedIndex();
        String shiftSelection = shiftNumberBox.getItemAt(shiftIndex);

        String redColor = new String(redColorEntry.getPassword());
        String greenColor = new String(greenColorEntry.getPassword());
        String blueColor = new String(blueColorEntry.getPassword());
        String alphaColor = new String(alphaValueEntry.getPassword());

        ColorModel keyVector = new ColorModel();
        keyVector.setRed(Integer.parseInt(redColor));
        keyVector.setGreen(Integer.parseInt(greenColor));
        keyVector.setBlue(Integer.parseInt(blueColor));
        keyVector.setAlpha(Integer.parseInt(alphaColor));
        
        Path parent = pathDeArchivo.getParent();
        String stringPath = parent.toString() + "/encrypted.jpg";
        System.out.println("stringPath:"+stringPath);
        Path encryptedPath = Paths.get(stringPath);
        
        manager = new ImageManager(encryptedPath);
        mainMatrix = manager.getImagePixels();

        switch (index) {
            case 0:
                process.desencryptImage(mainMatrix, "xor", Integer.parseInt(key),
                        Integer.parseInt(shiftSelection), keyVector);
                break;
            case 1:
                process.desencryptImage(mainMatrix, "shift-s", Integer.parseInt(key),
                        Integer.parseInt(shiftSelection), keyVector);
                break;
            case 2:
                process.desencryptImage(mainMatrix, "shift-c", Integer.parseInt(key),
                        Integer.parseInt(shiftSelection), keyVector);
                break;
            default:
                process.desencryptImage(mainMatrix, "add", Integer.parseInt(key),
                        Integer.parseInt(shiftSelection), keyVector);
                break;
        }
        manager.buildImage(process.getDesencryptedImageArray(),"desencrypted");
    }

    private void openImages() {
        JFileChooser Buscador = new JFileChooser();
        Buscador.setAcceptAllFileFilterUsed(false);
        Buscador.setMultiSelectionEnabled(false);
        //Buscador.setFileFilter(new FileNameExtensionFilter(".jpg", "image"));
        Buscador.showOpenDialog(Buscador);
        File file = Buscador.getSelectedFile();
        try {
            pathDeArchivo = Paths.get(file.getAbsolutePath());
            retornoArchivo = new String(Files.readAllBytes(pathDeArchivo));
            //
            pathRelativo = "/" + pathDeArchivo.getParent().getParent().getFileName()
                    + "/" + pathDeArchivo.getParent().getFileName() + "/" + pathDeArchivo.getFileName();
            
            System.out.println("relativo: "+pathRelativo);
            System.out.println("pathArchivo1: "+pathDeArchivo.getParent().getParent().getFileName());
            System.out.println("pathArchivo2: "+pathDeArchivo.getParent().getFileName());
            System.out.println("fileName: "+pathDeArchivo.getFileName());
            

            originalImage.setIcon(new ImageIcon(getClass().getResource(pathRelativo)));

            // pass image to grayScale and set matrix
            manager = new ImageManager(pathDeArchivo);
            manager.imageToGrayScale();
            mainMatrix = manager.getImageGrayPixels();

            //newScript(retornoArchivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR, we can't load the file");
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

        jPanel1 = new javax.swing.JPanel();
        selectImageBtn = new javax.swing.JButton();
        desencryptBtn = new javax.swing.JButton();
        encryptBtn = new javax.swing.JButton();
        algorithmsBox = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        privateKeyEntry = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        shiftNumberBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        redColorEntry = new javax.swing.JPasswordField();
        greenColorEntry = new javax.swing.JPasswordField();
        blueColorEntry = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        alphaValueEntry = new javax.swing.JPasswordField();
        jLabel12 = new javax.swing.JLabel();
        originalImage = new javax.swing.JLabel();
        encryptedImage = new javax.swing.JLabel();
        desencryptedImage = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        displayEncrypted = new javax.swing.JButton();
        displayDesencryptedBtn = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        selectIamgeMenu = new javax.swing.JMenuItem();
        viewMemoryMenu = new javax.swing.JMenuItem();
        exitMenu = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        encryptMenu = new javax.swing.JMenuItem();
        desencryptMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        selectImageBtn.setText("Select Image");
        selectImageBtn.setToolTipText("Select Image");
        selectImageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectImageBtnActionPerformed(evt);
            }
        });

        desencryptBtn.setText("Desencrypt");
        desencryptBtn.setToolTipText("Desencrypt");
        desencryptBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desencryptBtnActionPerformed(evt);
            }
        });

        encryptBtn.setText("Encrypt");
        encryptBtn.setToolTipText("Encrypt");
        encryptBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encryptBtnActionPerformed(evt);
            }
        });

        algorithmsBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "XOR", "Single Shift", "Circular Shift", "Single Addition" }));
        algorithmsBox.setToolTipText("Select Algorithm");

        jLabel14.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel14.setText("Setup");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(encryptBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(desencryptBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(selectImageBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(algorithmsBox, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel14))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(0, 9, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(algorithmsBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectImageBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encryptBtn)
                    .addComponent(desencryptBtn))
                .addGap(43, 43, 43))
        );

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setText("XOR");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        jLabel2.setText("Set Private Key:");

        privateKeyEntry.setText("000");
        privateKeyEntry.setToolTipText("Private Key");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
            .addComponent(privateKeyEntry)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(privateKeyEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setText("Single Shift | Circular Shift");

        shiftNumberBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7" }));
        shiftNumberBox.setToolTipText("Shift Number");

        jLabel6.setText("Shift Number:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shiftNumberBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel3))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(shiftNumberBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("Vector Key Values:");

        redColorEntry.setText("000");
        redColorEntry.setToolTipText("Red Value");
        redColorEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redColorEntryActionPerformed(evt);
            }
        });

        greenColorEntry.setText("000");
        greenColorEntry.setToolTipText("Green Value");

        blueColorEntry.setText("000");
        blueColorEntry.setToolTipText("Blue Value");

        jLabel7.setText("R:");

        jLabel8.setText("G:");

        jLabel9.setText("B:");

        jLabel5.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel5.setText("Single Addition");

        jLabel10.setText("A:");

        alphaValueEntry.setText("000");
        alphaValueEntry.setToolTipText("Alpha Value");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(redColorEntry, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(greenColorEntry)
                            .addComponent(blueColorEntry)
                            .addComponent(alphaValueEntry))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(redColorEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(greenColorEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(blueColorEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(alphaValueEntry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel12.setText("Encrypted:");

        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel11.setText("Original:");

        jLabel13.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel13.setText("Desencrypted:");

        displayEncrypted.setText("Display");
        displayEncrypted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayEncryptedActionPerformed(evt);
            }
        });

        displayDesencryptedBtn.setText("Display");
        displayDesencryptedBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayDesencryptedBtnActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        selectIamgeMenu.setText("Select Image");
        selectIamgeMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectIamgeMenuActionPerformed(evt);
            }
        });
        jMenu1.add(selectIamgeMenu);

        viewMemoryMenu.setText("View Memory");
        viewMemoryMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewMemoryMenuActionPerformed(evt);
            }
        });
        jMenu1.add(viewMemoryMenu);

        exitMenu.setText("Exit");
        exitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt);
            }
        });
        jMenu1.add(exitMenu);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Action");

        encryptMenu.setText("Encrypt");
        encryptMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encryptMenuActionPerformed(evt);
            }
        });
        jMenu3.add(encryptMenu);

        desencryptMenu.setText("Desencrypt");
        desencryptMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desencryptMenuActionPerformed(evt);
            }
        });
        jMenu3.add(desencryptMenu);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(originalImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(displayEncrypted))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(displayDesencryptedBtn)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(encryptedImage, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(desencryptedImage, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 31, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(displayEncrypted)
                    .addComponent(displayDesencryptedBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(encryptedImage, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(originalImage, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(desencryptedImage, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectImageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectImageBtnActionPerformed
        // TODO add your handling code here:
        openImages();
    }//GEN-LAST:event_selectImageBtnActionPerformed

    private void encryptBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encryptBtnActionPerformed
        try {
            // TODO add your handling code here:
            encryptFunction();
            //buildImage();
            //saveEncryptData();
        } catch (IOException ex) {
            Logger.getLogger(AppMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_encryptBtnActionPerformed

    private void desencryptBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desencryptBtnActionPerformed
        try {
            // TODO add your handling code here:
            System.out.println("///////////////////////////////////////////////////////"
                    + "\n ///////////////////////////////////////////////////////////////"
                    + "\n ///////////////////////////////////////////////////////////////"
                    + "\n ///////////////////////////////////////////////////////////////"
                    + "\n ///////////////////////////////////////////////////////////////"
                    + "\n ////////////////////////////////////////////////////////////////");
            desencryptFunction();
        } catch (IOException ex) {
            Logger.getLogger(AppMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_desencryptBtnActionPerformed

    private void selectIamgeMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectIamgeMenuActionPerformed
        // TODO add your handling code here:
        openImages();
    }//GEN-LAST:event_selectIamgeMenuActionPerformed

    private void encryptMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encryptMenuActionPerformed
        try {
            // TODO add your handling code here:
            encryptFunction();
        } catch (IOException ex) {
            Logger.getLogger(AppMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_encryptMenuActionPerformed

    private void desencryptMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desencryptMenuActionPerformed
        try {
            // TODO add your handling code here:
            
            desencryptFunction();
            
        } catch (IOException ex) {
            Logger.getLogger(AppMainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_desencryptMenuActionPerformed

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuActionPerformed
        // TODO add your handling code here:
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_exitMenuActionPerformed

    private void redColorEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redColorEntryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_redColorEntryActionPerformed

    private void displayEncryptedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayEncryptedActionPerformed
        // TODO add your handling code here:
        pathRelativo = "/" + pathDeArchivo.getParent().getParent().getFileName()
                    + "/" + pathDeArchivo.getParent().getFileName() + "/encrypted.jpg";
        
        encryptedImage.setIcon(new ImageIcon(getClass().getResource(pathRelativo)));
    }//GEN-LAST:event_displayEncryptedActionPerformed

    private void displayDesencryptedBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayDesencryptedBtnActionPerformed
        // TODO add your handling code here:
        pathRelativo = "/" + pathDeArchivo.getParent().getParent().getFileName()
                    + "/" + pathDeArchivo.getParent().getFileName() + "/desencrypted.jpg";
        
        desencryptedImage.setIcon(new ImageIcon(getClass().getResource(pathRelativo)));
    }//GEN-LAST:event_displayDesencryptedBtnActionPerformed

    private void viewMemoryMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewMemoryMenuActionPerformed
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
            java.util.logging.Logger.getLogger(AppMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    }//GEN-LAST:event_viewMemoryMenuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> algorithmsBox;
    private javax.swing.JPasswordField alphaValueEntry;
    private javax.swing.JPasswordField blueColorEntry;
    private javax.swing.JButton desencryptBtn;
    private javax.swing.JMenuItem desencryptMenu;
    private javax.swing.JLabel desencryptedImage;
    private javax.swing.JButton displayDesencryptedBtn;
    private javax.swing.JButton displayEncrypted;
    private javax.swing.JButton encryptBtn;
    private javax.swing.JMenuItem encryptMenu;
    private javax.swing.JLabel encryptedImage;
    private javax.swing.JMenuItem exitMenu;
    private javax.swing.JPasswordField greenColorEntry;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel originalImage;
    private javax.swing.JPasswordField privateKeyEntry;
    private javax.swing.JPasswordField redColorEntry;
    private javax.swing.JMenuItem selectIamgeMenu;
    private javax.swing.JButton selectImageBtn;
    private javax.swing.JComboBox<String> shiftNumberBox;
    private javax.swing.JMenuItem viewMemoryMenu;
    // End of variables declaration//GEN-END:variables
}

package waterflow;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

public class UI extends JFrame {

    //DATA---------------------
    private Data data = null;
    Algorithm algorithm = null;

    //panel
    private JPanel jContentPane = null;
    private JPanel jPanelNorth = null;
    private JTabbedPane jTabbedPane = null;

    //komponen di jPanelNorth
    private JLabel jLabelFileDEM = null;
    private JTextField jTextFieldFileDEM = null;
    private JButton jButtonBrowse = null;
    private JButton jButtonRunD8 = null;
    private JButton jButtonRunD16 = null;
    private JButton jButtonRunD24 = null;
    private JButton jButtonRunD32 = null;
    private JToggleButton jToggleButtonMap = null;
    private JToggleButton jToggleButtonPoints = null;
    private JToggleButton jToggleButtonWater = null;
    private JToggleButton jToggleButtonGraph = null;
    private JButton jButtonClear = null;
    private JButton jButtonClose = null;

    //komponen di jTabbedPane
    private JPanel jPanelDataDEM = null;
    private JPanel jPanelResult = null;
    private Canvas canvas = null;
    private JScrollPane jScrollPaneDataDEM = null;
    private JScrollPane jScrollPaneResult = null;
    private JTextArea jTextAreaDataDEM = null;
    private JTextArea jTextAreaResult = null;

    //method constructor--------------------------------------------------------
    public UI() {
        init();
    }

    private void init() {
        /*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         */

 /*
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
        try {
            UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel());
            // set UI manager properties here that affect Quaqua
        } catch (Exception e) {
            // take an appropriate action here
        }

        setContentPane(getJContentPane());
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        setTitle("Water Flow Direction");
        setSize(1000, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    //method-method untuk inisialisasi komponen swing---------------------------
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanelNorth(), BorderLayout.NORTH);
            jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    private JPanel getJPanelNorth() {
        if (jPanelNorth == null) {
            jPanelNorth = new JPanel();
            jPanelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
            jPanelNorth.add(getJLabelFileDEM());
            jPanelNorth.add(getJTextFieldFileDEM());
            jPanelNorth.add(getJButtonBrowse());
            jPanelNorth.add(getJButtonRunD8());
            jPanelNorth.add(getJButtonRunD16());
            jPanelNorth.add(getJButtonRunD24());
            jPanelNorth.add(getJButtonRunD32());
            jPanelNorth.add(getJToggleButtonMap());
            jPanelNorth.add(getJToggleButtonPoints());
            jPanelNorth.add(getJToggleButtonWater());
            jPanelNorth.add(getJToggleButtonGraph());
            jPanelNorth.add(getJButtonClear());
            jPanelNorth.add(getJButtonClose());
        }
        return jPanelNorth;
    }

    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.addTab("Data DEM", getJPanelDataDEM());
            jTabbedPane.addTab("Result", getJScrollPaneResult());
            jTabbedPane.addTab("Flow Direction", getCanvas());
            jTabbedPane.setTabPlacement(JTabbedPane.LEFT);
            jTabbedPane.setSelectedIndex(2);
        }
        return jTabbedPane;
    }

    //method untuk inisialisasi komponen di jPanelNorth-------------------------
    private JLabel getJLabelFileDEM() {
        if (jLabelFileDEM == null) {
            jLabelFileDEM = new JLabel("File DEM");
        }
        return jLabelFileDEM;
    }

    private JTextField getJTextFieldFileDEM() {
        if (jTextFieldFileDEM == null) {
            jTextFieldFileDEM = new JTextField();
            //jTextFieldFileDEM.setEditable(false);
            //jTextFieldFileDEM.setEnabled(false);            
            jTextFieldFileDEM.setRequestFocusEnabled(false);
            jTextFieldFileDEM.setPreferredSize(new Dimension(200, 30));
        }
        return jTextFieldFileDEM;
    }

    private JButton getJButtonBrowse() {
        if (jButtonBrowse == null) {
            jButtonBrowse = new JButton("Browse");
            jButtonBrowse.setPreferredSize(new Dimension(80, 30));
            jButtonBrowse.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Browse");
                    JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    if (jfc.showOpenDialog(jContentPane) == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = jfc.getSelectedFile();
                        jTextFieldFileDEM.setText(selectedFile.getAbsolutePath());
                        data = new Data(selectedFile);
                        canvas.setData(data);
                        jTextAreaDataDEM.setText(data.getStringDataElevasi());
                        jTextAreaResult.setText(data.getStringDataResult());
                        canvas.requestFocus();
                    }
                }
            });
        }
        return jButtonBrowse;
    }

    private JButton getJButtonRunD8() {
        if (jButtonRunD8 == null) {
            jButtonRunD8 = new JButton("RunD8");
            jButtonRunD8.setPreferredSize(new Dimension(80, 30));
            jButtonRunD8.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("RunD8");
                    algorithm = new AlgoritmaD8(data);
                    canvas.setData(algorithm.getData());
                    jTextAreaResult.setText(algorithm.getData().getStringDataResult());                    
                    canvas.requestFocus();
                }
            });
        }
        return jButtonRunD8;
    }

    private JButton getJButtonRunD16() {
        if (jButtonRunD16 == null) {
            jButtonRunD16 = new JButton("RunD16");
            jButtonRunD16.setPreferredSize(new Dimension(80, 30));
            jButtonRunD16.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("RunD16");
                    algorithm = new AlgoritmaD16(data);
                    canvas.setData(algorithm.getData());
                    jTextAreaResult.setText(algorithm.getData().getStringDataResult());  
                    canvas.requestFocus();
                }
            });
        }
        return jButtonRunD16;
    }

    private JButton getJButtonRunD24() {
        if (jButtonRunD24 == null) {
            jButtonRunD24 = new JButton("RunD24");
            jButtonRunD24.setPreferredSize(new Dimension(80, 30));
            jButtonRunD24.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("RunD24");
                    algorithm = new AlgoritmaD24(data);
                    canvas.setData(algorithm.getData());
                    jTextAreaResult.setText(algorithm.getData().getStringDataResult());  
                    canvas.requestFocus();
                }
            });
        }
        return jButtonRunD24;
    }

    private JButton getJButtonRunD32() {
        if (jButtonRunD32 == null) {
            jButtonRunD32 = new JButton("RunD32");
            jButtonRunD32.setPreferredSize(new Dimension(80, 30));
            jButtonRunD32.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("RunD32");
                    canvas.setData(new AlgoritmaD32(data).getData());
                    canvas.requestFocus();
                }
            });
        }
        return jButtonRunD32;
    }

    private JToggleButton getJToggleButtonMap() {
        if (jToggleButtonMap == null) {
            jToggleButtonMap = new JToggleButton("Map");
            jToggleButtonMap.setPreferredSize(new Dimension(80, 30));
            jToggleButtonMap.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Map");
                }
            });
        }
        return jToggleButtonMap;
    }

    private JToggleButton getJToggleButtonPoints() {
        if (jToggleButtonPoints == null) {
            jToggleButtonPoints = new JToggleButton("Points");
            jToggleButtonPoints.setPreferredSize(new Dimension(80, 30));
            jToggleButtonPoints.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Points");
                }
            });
        }
        return jToggleButtonPoints;
    }

    private JToggleButton getJToggleButtonWater() {
        if (jToggleButtonWater == null) {
            jToggleButtonWater = new JToggleButton("Water");
            jToggleButtonWater.setPreferredSize(new Dimension(80, 30));
            jToggleButtonWater.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Water");
                }
            });
        }
        return jToggleButtonWater;
    }

    private JToggleButton getJToggleButtonGraph() {
        if (jToggleButtonGraph == null) {
            jToggleButtonGraph = new JToggleButton("Graph");
            jToggleButtonGraph.setPreferredSize(new Dimension(80, 30));
            jToggleButtonGraph.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Graph");
                }
            });
        }
        return jToggleButtonGraph;
    }

    private JButton getJButtonClear() {
        if (jButtonClear == null) {
            jButtonClear = new JButton("Clear");
            jButtonClear.setPreferredSize(new Dimension(80, 30));
            jButtonClear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Clear");
                }
            });
        }
        return jButtonClear;
    }

    private JButton getJButtonClose() {
        if (jButtonClose == null) {
            jButtonClose = new JButton("Close");
            jButtonClose.setPreferredSize(new Dimension(80, 30));
            jButtonClose.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Close");
                    System.exit(0);
                }
            });
        }
        return jButtonClose;
    }

    //method-method untuk inisialisasi komponen di jTabbedpane------------------
    private JPanel getJPanelDataDEM() {
        if (jPanelDataDEM == null) {
            jPanelDataDEM = new JPanel();
            jPanelDataDEM.setLayout(new BorderLayout());
            jPanelDataDEM.add(getJScrollPaneDataDEM(), BorderLayout.CENTER);
        }
        return jPanelDataDEM;
    }

    private JPanel getJPanelResult() {
        if (jPanelResult == null) {
            jPanelResult = new JPanel();
            jPanelResult.setLayout(new BorderLayout());
            jPanelResult.add(getJScrollPaneResult(), BorderLayout.CENTER);
        }
        return jPanelResult;
    }

    private Canvas getCanvas() {
        if (canvas == null) {
            canvas = new Canvas(this);
        }
        return canvas;
    }

    private JScrollPane getJScrollPaneDataDEM() {
        if (jScrollPaneDataDEM == null) {
            jScrollPaneDataDEM = new JScrollPane();
            jScrollPaneDataDEM.setViewportView(getJTextAreaDataDEM());
        }
        return jScrollPaneDataDEM;
    }

    private JScrollPane getJScrollPaneResult() {
        if (jScrollPaneResult == null) {
            jScrollPaneResult = new JScrollPane();
            jScrollPaneResult.setViewportView(getJTextAreaResult());
        }
        return jScrollPaneResult;
    }

    private JTextArea getJTextAreaDataDEM() {
        if (jTextAreaDataDEM == null) {
            jTextAreaDataDEM = new JTextArea();
        }
        return jTextAreaDataDEM;
    }

    private JTextArea getJTextAreaResult() {
        if (jTextAreaResult == null) {
            jTextAreaResult = new JTextArea();
        }
        return jTextAreaResult;
    }

    //--------------------------------------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UI ui = new UI();
            }
        });
    }//end of main()

}//end of class UI

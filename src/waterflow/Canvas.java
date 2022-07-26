package waterflow;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Canvas extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private JFrame jFrame = null;
    private int lastOffsetX;
    private int lastOffsetY;
    final private double MIN_SCALE = 0.05;
    final private double MAX_SCALE = 100;

    //add points----------------------------------------------------------------    
    private boolean addPoint = false;
    private boolean addPoints = false;
    private boolean first = true;
    private int i1, j1, i2, j2;

    //--------------------------------------------------------------------------    
    private int cellSize = 20;
    private double translateX;
    private double translateY;
    private double scale;

    //Data----------------------------------------------------------------------
    private Data data = null;

    //visibilitas---------------------------------------------------------------
    private boolean showMap = true;
    private boolean showPoints = true;
    private boolean showResult = true;
    private boolean showGraph = true;

    public Canvas(JFrame jFrame) {
        this.jFrame = jFrame;
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.translateX = 0;
        this.translateY = 0;
        scale = 1;
        setOpaque(false);
        setDoubleBuffered(true);
    }

    //method getter & setter untuk data-----------------------------------------
    public void setData(Data data) {
        this.data = data;
        repaint();
    }

    //method getter & setter untuk translasi dan skala--------------------------
    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public double getTranslateX() {
        return translateX;
    }

    public void setTranslateX(double translateX) {
        this.translateX = translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public void setTranslateY(double translateY) {
        this.translateY = translateY;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    //method getter & setter untuk visibilitas komponen di paint----------------
    public boolean isShowMap() {
        return showMap;
    }

    public void setShowMap(boolean showMap) {
        this.showMap = showMap;
        repaint();
    }

    public boolean isShowPoints() {
        return showPoints;
    }

    public void setShowPoints(boolean showPoints) {
        this.showPoints = showPoints;
        repaint();
    }

    public boolean isShowResult() {
        return showResult;
    }

    public void setShowResult(boolean showResult) {
        this.showResult = showResult;
        repaint();
    }

    public boolean isShowGraph() {
        return showGraph;
    }

    public void setShowGraph(boolean showGraph) {
        this.showGraph = showGraph;
        repaint();
    }

    //method untuk add multiple points------------------------------------------
    public boolean isAddPoints() {
        return addPoints;
    }

    public void setAddPoints(boolean addPoints) {
        this.addPoints = addPoints;
    }

    //override method paint()---------------------------------------------------
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        //set translasi---------------------------------------------------------
        AffineTransform tx = new AffineTransform();
        tx.translate(translateX, translateY);
        tx.scale(scale, scale);
        g2d.setTransform(tx);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //draw data-------------------------------------------------------------
        if (data != null) {
            //baca field-field yang ada di object data
            double[][] dataElevasi = data.getElevasi();
            int rows = data.getRows();
            int cols = data.getCols();
            ArrayList<Titik> listTitikPusat = data.getListTitikPusat();
            int[][] result = data.getResult();
            ArrayList<Edge> graph = data.getGraph();
            double MIN = data.getMIN();
            double MAX = data.getMAX();
            Color[] colors = data.getColors();

            //draw elevasi------------------------------------------------------            
            double range = Math.abs(MAX - MIN);
            double step = range / colors.length;
            if (showMap
                    && dataElevasi != null
                    && colors != null
                    && rows == dataElevasi.length
                    && cols == dataElevasi[0].length) {

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        double value = dataElevasi[i][j];
                        //pilih warna                        
                        int iColor = (int) Math.floor((value - MIN) / step);
                        if (iColor < 0) {
                            iColor = 0;
                        }
                        if (iColor >= colors.length) {
                            iColor = colors.length - 1;
                        }
                        Color c = colors[iColor];
                        g2d.setColor(c);
                        //g2d.setStroke(new BasicStroke(2));
                        //g2d.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    }
                }
            }//end of draw elevasi

            //draw result-------------------------------------------------------
            if (showResult && result != null) {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        int value = result[i][j];
                        if (value >= 0) {
                            g2d.setColor(Color.decode("#3498db"));
                            //g2d.setStroke(new BasicStroke(2));
                            //g2d.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                            g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        }
                    }
                }
            }

            //draw titik hujan--------------------------------------------------
            if (showPoints && listTitikPusat != null && listTitikPusat.size() > 0) {
                g2d.setColor(Color.decode("#2980b9"));
                for (Titik titik : listTitikPusat) {
                    int i = titik.getX();
                    int j = titik.getY();
                    g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }

            //draw graph--------------------------------------------------------
            if (showGraph && graph != null && graph.size() > 0) {
                for (Edge edge : graph) {
                    Titik origin = edge.getOrigin();
                    Titik destination = edge.getDestination();
                    double radian = edge.getRadian();

                    int originCY = origin.getY() * cellSize + (int) (cellSize * 0.5);
                    int originCX = origin.getX() * cellSize + (int) (cellSize * 0.5);

                    int destinationCY = destination.getY() * cellSize + (int) (cellSize * 0.5);
                    int destinationCX = destination.getX() * cellSize + (int) (cellSize * 0.5);

                    g2d.setComposite(AlphaComposite.SrcOver.derive(1.0F));
                    //draw line
                    Color lineColor = Color.decode("#e67e22");
                    g2d.setColor(lineColor);
                    //g2d.setStroke(new BasicStroke(2));
                    Stroke stroke = new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
                    g2d.setStroke(stroke);
                    g2d.drawLine(originCY, originCX, destinationCY, destinationCX);
                }
            }

            //draw addPoints----------------------------------------------------
            if (addPoints && !first) {
                int min_i = Math.min(i1, i2);
                int min_j = Math.min(j1, j2);
                int size_i = Math.abs(i2 - i1);
                int size_j = Math.abs(j2 - j1);
                Color lineColor = Color.decode("#e67e22");
                g2d.setColor(lineColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRect(min_j * cellSize, min_i * cellSize, (1 + size_j) * cellSize, (1 + size_i) * cellSize);
            }

        }//end of if(data!=null)
        //dispose---------------------------------------------------------------
        g2d.dispose();
    }//end of paint()

    //Method untuk D&D, scale, key event, etc-----------------------------------
    @Override
    public void keyTyped(KeyEvent e) {
        ///
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        //System.out.println("press: "+i);
        if (i == 18) {
            addPoint = true;
            addPoints = false;
        } else if (i == 17) {
            addPoint = false;
            addPoints = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        //System.out.println("release: "+i);
        addPoint = false;
        addPoints = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (addPoint) {
            double x1 = e.getX() - translateX;
            double y1 = e.getY() - translateY;

            double cellSizeScale = cellSize * scale;
            double x = Math.floor(x1 / cellSizeScale);
            double y = Math.floor(y1 / cellSizeScale);

            int i = (int) y;//i,j | x, y
            int j = (int) x;

            if (data != null) {
                double[][] dataElevasi = data.getElevasi();
                if (dataElevasi != null && i >= 0 && i < dataElevasi.length && j >= 0 && j < dataElevasi[i].length) {
                    Titik titik = new Titik(i, j);
                    data.insertTitikPusat(titik);
                    repaint();
                }
            }
            first = true;
        }
    }//end of  mouseClicked(MouseEvent e

    @Override
    public void mousePressed(MouseEvent e) {
        if (addPoints) {
            if (first) {
                double x1 = e.getX() - translateX;
                double y1 = e.getY() - translateY;

                double cellSizeScale = cellSize * scale;
                double x = Math.floor(x1 / cellSizeScale);
                double y = Math.floor(y1 / cellSizeScale);

                int i = (int) y;//i,j | x, y
                int j = (int) x;

                if (data != null) {
                    double[][] dataElevasi = data.getElevasi();
                    if (dataElevasi != null && i >= 0 && i < dataElevasi.length && j >= 0 && j < dataElevasi[i].length) {
                        first = false;
                        i1 = i;
                        j1 = j;
                    }
                }
            }
        } else {
            //capture titik start x dan start untuk memulai drag and drop
            lastOffsetX = e.getX();
            lastOffsetY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (first == false) {
            first = true;
            //System.out.println("(" + i1 + "," + j1 + ") - (" + i2 + "," + j2 + ") ");
            int size = (Math.abs(i2 - i1) + 1) * (Math.abs(j2 - j1) + 1);
            Titik[] titik = new Titik[size];
            int maxi = Math.max(i1, i2);
            int mini = Math.min(i1, i2);
            int maxj = Math.max(j1, j2);
            int minj = Math.min(j1, j2);

            int k = 0;
            for (int a = mini; a <= maxi; a++) {
                for (int b = minj; b <= maxj; b++) {
                    titik[k] = new Titik(a, b);
                    k++;
                }
            }
            data.insertTitikPusat(titik);
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (addPoints) {
            if (!first) {
                double x1 = e.getX() - translateX;
                double y1 = e.getY() - translateY;

                double cellSizeScale = cellSize * scale;
                double x = Math.floor(x1 / cellSizeScale);
                double y = Math.floor(y1 / cellSizeScale);

                int i = (int) y;//i,j | x, y
                int j = (int) x;

                if (data != null) {
                    double[][] dataElevasi = data.getElevasi();
                    if (dataElevasi != null && i >= 0 && i < dataElevasi.length && j >= 0 && j < dataElevasi[i].length) {
                        i2 = i;
                        j2 = j;
                        repaint();
                    }
                }
            }
        } else {
            //capture titik x y yang baru dan hitung translasinya
            int newX = e.getX() - lastOffsetX;
            int newY = e.getY() - lastOffsetY;

            //tambahkan newX dan newY ke lastOffset
            lastOffsetX += newX;
            lastOffsetY += newY;

            //update posisi canvas
            translateX = translateX + newX;
            translateY = translateY + newY;

            //repaint canvas
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
            if (scale >= MIN_SCALE && scale <= MAX_SCALE) {
                double oldScale = scale;
                double newScale = 0.1 * e.getWheelRotation();
                newScale += oldScale;
                newScale = Math.max(0.00001, newScale);

                //cek min max
                if (newScale < MIN_SCALE) {
                    newScale = MIN_SCALE;
                }
                if (newScale > MAX_SCALE) {
                    newScale = MAX_SCALE;
                }

                double x1 = e.getX() - translateX;
                double y1 = e.getY() - translateY;

                double x2 = (x1 * newScale) / oldScale;
                double y2 = (y1 * newScale) / oldScale;

                double newTranslateX = translateX + x1 - x2;
                double newTranslateY = translateY + y1 - y2;

                //set new value
                translateX = newTranslateX;
                translateY = newTranslateY;
                scale = newScale;
                repaint();
            }
        }
    }

}//end of class Canvas

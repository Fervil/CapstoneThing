import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
 
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
  
public class MainView extends JFrame implements MouseMotionListener,MouseListener,KeyListener{
      
    /**
     * FourteenFourHundred Studios (c)
     * Mod
     */
    private static final long serialVersionUID = 1L;
      
    private Image dbImage; 
    private Graphics dbg;
      
    public static int xCam=-500;
    public static int yCam=-300;
      
    public int size=10;
      
    public int ogxCam=0;
    public int ogyCam=0;
      
    public int cmX=0;
    public int cmY=0;
      
    public static Color map[][]=new Color[1000][1000];
      
    public MainView(){
        super("MOD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500,500);
        makeMap();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        (new repaint()).start();
         sheep john = new sheep(70,66);
    }
      
    public void makeMap(){
        try{
            boolean crater=false;
            int craterWidth=0;
            int craterSlope=0;
            for(int x=0;x<1000;x++){
                int sky=0;
                if(crater){
                    sky=r(68,70);
                }else{
                    if(x<=craterWidth/2){
                        craterSlope--;
                    }
                     
                    else if(x>craterWidth/2){
                        System.out.println(craterSlope);
                        craterSlope++;
                    }
                    sky=r(68+craterSlope,70+craterSlope);
                     
                    //craterSlope++;
                }
                int dirt=sky+r(50,60);
                for(int y=0;y<1000;y++){
                     
                    if(y<sky){
                         
                         
                            if(x>craterWidth && crater){
                               // crater=false;
                                System.out.println("NO");
                            }
                         
                         
                        map[y][x]=Color.CYAN;
                        if(r(0,1000)==1){
                            //System.out.println("crater");
                            crater=true;
                            craterWidth=x+r(10,40);
                            craterSlope=0;
                        }
                         
                        if(y==sky-1)
                            map[y][x]=Color.GREEN;
                    }else if(y<dirt){
                        map[y][x]=Color.GRAY;   
                        if(r(0,15000)==1000 && y>sky+10){
                            map[y][x]=Color.MAGENTA;
                        }
                    }else{
                        map[y][x]=Color.DARK_GRAY;
                    }
 
                }
            }
 
            int mountains=r(5,10);
            int trees=0;
            System.out.println("mountains: "+mountains);
            System.out.println("trees: "+trees);
            
            for(int x=0;x<trees;x++){
                 int distance=r(0,1000);
                 int height=r(10,20);
                 for(int i=68-height;i<68;i++){
                     map[i][distance]=Color.RED;
                 }
 
            }
             
            for(int x=0;x<mountains;x++){
                int start=78;
                int size=r(100,150);
                int p=0;
                int distance=r(0,1000);
                for(int j=start;j>start-size;j--){
                    for(int i=p+distance;i<distance+size-p;i++){
                        map[j][i]=Color.GRAY;
                    }
                    p++;
                }
            }
 
        }catch(Exception e){
 
        }
 
 
    }
 
    class repaint extends Thread{
        public void run(){
            while(true){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                repaint();
            }
        }
    }
  
    public void paint(Graphics g){
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, null);
    }
  
    public int r(int min, int max){
         return min + (new Random()).nextInt((max - min) + 1);
    }
      
    public Color getShading(int xx,int yy){
        Color c = map[yy][xx]; 
          
        try{
            int i=0;
              
            while(!(map[yy-i][xx].equals(Color.CYAN))){
                i++;
            }
            int factor=i*10;
            if(c.equals(Color.GREEN)){
                return new Color(c.getRed(),c.getGreen()-factor,c.getBlue());
            }else if(c.equals(Color.RED)){
                return new Color(c.getRed()-factor,c.getGreen(),c.getBlue());
            }else if(c.equals(Color.BLUE)){
                return new Color(c.getRed(),c.getGreen(),c.getBlue()-factor);
            }
              
            c=new Color(c.getRed()-factor,c.getGreen()-factor,c.getBlue()-factor);
              
        }catch(Exception e){
            //e.printStackTrace();
            return new Color(0,0,0);
        }
        return c;
    }
      
    public void paintComponent(Graphics g){
        super.paint(g);
        for(int x=0;x<1000;x++){
            for(int y=0;y<1000;y++){
                //
                if( ( ((y*size)+yCam>0)&&((x*size)+xCam>0) ) ){
                    g.setColor(getShading(x,y)); 
                   // g.setColor(map[y][x]);
                    g.fillRect((x*size)+xCam, (y*size)+yCam, size, size);
                }
                 
                if((y*size)+yCam>this.getHeight())
                    break;
            }
            if((x*size)+xCam>this.getWidth())
                break;
        }
        g.setColor(Color.BLACK);
    //  g.drawString("xCam: "+xCam, 30, 50);
    //  g.drawString("xMouse: "+roundUp(cmX,size), 30, 70);
        g.drawRect(roundUp(cmX-size,size), roundUp(cmY-size,size), size, size);
        //miniMap(g);
       // xCam-=size;
    }
      
    int roundUp(int n,int place) {
        return (n + (place-1)) / place * place;
    }
      
      
      
    public void miniMap(Graphics g){
        //working on this
        for(int x=0;x<700;x++){
            for(int y=0;y<700;y++){
                  
                g.setColor(map[y][x]);
                  
                g.fillRect((x*1)+xCam, (y*1)+yCam, 1, 1);
                if((y*1)+yCam>50)
                    break;
            }
            if((x*1)+xCam>50)
                break;
        }
    }
  
    public void mouseDragged(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)){
            cmX=e.getX();
            cmY=e.getY();
            xCam=roundUp((-1*(ogxCam-e.getX())),size);
            yCam=roundUp((-1*(ogyCam-e.getY())),size);
            if(xCam>0)
                xCam=0;
            if (yCam>0){
                yCam=0;
            }
        }
    }
  
    public void mouseMoved(MouseEvent e) {
        cmX=e.getX();
        cmY=e.getY();
    }
  
    public static void main(String args[]){
        new MainView();
    }
  
    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
          
    }
  
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
          
    }
  
    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
          
    }
  
    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println(Color.GRAY.getRed()+"=="+getShading(cmX/size,cmY/size).getRed());
        if(e.getButton()==3){
            if(!( map[(e.getY()-yCam)/size][(e.getX()-xCam)/size].equals(Color.CYAN))){
                map[(e.getY()-yCam)/size][(e.getX()-xCam)/size]=Color.CYAN;
            }else{
                map[(e.getY()-yCam)/size][(e.getX()-xCam)/size]=Color.GRAY;
 
            }
        }
        ogxCam=e.getX()-xCam;
        ogyCam=e.getY()-yCam;
  
    }
 
    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
          
    }
  
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==e.VK_Z){
            size+=5;
            xCam=-1*(cmX-xCam);
            yCam=-1*(cmY-yCam);
            //xCam=-1*(cmY-xCam);
        }else if(e.getKeyCode()==e.VK_X){
            size-=5;
            //xCam=-1*(cmX-xCam);
        //  yCam=-1*(cmY-yCam);
        }else if(e.getKeyCode()==e.VK_SPACE){
        }
          
    }
  
    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
          
    }
  
    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
          
    }
  
  
}
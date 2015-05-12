import java.awt.Color;
 
 
public class mob {
 
    public String[][][] frames={};
    public int frame=0;
    public int x=0;
    public int y=0;
    public int speed=0;
     
    public mob(int x,int y,int speed){
        this.x=x;
        this.y=y;
        this.speed=speed;
        (new motion()).start();
    }
     
    public void clear(){
        String[][] cFrame=frames[frame];
        for(int i=0;i<cFrame.length;i++){
            for(int j=0;j<cFrame[i].length;j++){             
                MainView.map[i+y][j+x]=Color.CYAN;          
            }
        }
    }
     
    public void paint(){
        String[][] cFrame=frames[frame];
        for(int i=0;i<cFrame.length;i++){
            for(int j=0;j<cFrame[i].length;j++){             
                if(cFrame[i][j].equals("#")){
                    MainView.map[i+y][j+x]=Color.RED;
                }else if(cFrame[i][j].equals(" ")){
                }               
            }
        }
    }
     
    public boolean isCollide(){
    //  String[][] cFrame=frames[frame];
        //if(cFrame[cFrame.length-1][cFrame[cFrame.length-1].length-1].equals(Color.CYAN)){
        //  return false;
        //}else{
        //  return true;
        //}
        return true;
    }
     
    public void behavior(){
         
    }
     
    class motion extends Thread{
        public void run(){
            while(true){
                behavior();
                paint();
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                }
                clear();
                frame++;
                if(frame==frames.length){
                    behavior();
                    frame=0;
                }
                 
            }
        }
    }
     
}
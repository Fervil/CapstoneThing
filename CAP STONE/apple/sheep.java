public class sheep extends mob{
 
    public sheep(int x,int y){
        super(x,y,1000);
        frames=new String[][][]{
            {
                {" "," ","#"},
                {"#","#","#"},
                {"#"," ","#"},
            },
            {
                {" "," "," ","#"},
                {" ","#","#","#"},
                {"#"," "," "," ","#"},
            },
        };
    }
     
    public void behavior(){
            x++;
        
    }
     
}
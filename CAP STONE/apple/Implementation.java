
public class Implementation extends WSS{
    
    public Implementation(){
        (new server()).start();
    }
    
    public static void main(String[] args){
        new Implementation();
    }
    
    public String testApple(){
        return "WSS Apple works! [part 1] (called from \"Implementation\")";
    }
    

    
}

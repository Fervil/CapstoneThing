
import java.net.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.lang.reflect.Method;
import sun.misc.*;

public class WSS{

    public WSS(){

    }

    class server extends Thread{
        public void run(){

            while(true){
                try{
                    ServerSocket ss = new ServerSocket(5555);
                    Socket sock=ss.accept();
                    BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    String request=br.readLine();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                    bw.write(wssCompile(parseRequest(request)));
                    bw.flush();
                    bw.close();
                }catch(Exception e){
                }
            }

        }
    }

    public String wssCompile(String arg){
        String[] parts =arg.replace(">","<").split("<");
        for(int i=0;i<parts.length;i++){
            System.out.println(parts[i]);
            if(parts[i].equals("wss")){
                System.out.println("GOT EEM");
                try{
                    Class cls = Class.forName(parts[i+1].split(":")[0]);
                    System.out.println(parts[i+1].split(":")[0]);
                    Object instance = cls.newInstance();
                    Method method = getClass().getMethod(parts[i+1].split(":")[1]); 
                    Object returnObject = method.invoke(instance);
                    arg=arg.replace(parts[i+1],(String)returnObject);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return arg;
    }

    public String parseRequest(String request){
        String str="";
        String requestType=request.split(" ")[0];
        String requestPage=request.split(" ")[1];
        if(requestType.equals("GET") ){
            if(!(requestPage.endsWith(".gif"))){
                str="HTTP/1.1 200 OK\r\n\r\n"+readFile(requestPage.substring(1));
            }else{
                
                // read "any" type of image (in this case a jpg file)
                try{
                    long bytes=(new File(requestPage.substring(1))).length();
                    FileInputStream fin = new FileInputStream(requestPage.substring(1));
                    int i;
                    String f="";
                    int z=0;
                    byte[] byteArray = new byte[(int)bytes];
                    fin.read(byteArray);
                    fin.close();
                    
                    sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
                    f=encoder.encode(byteArray);
                    
                    //end while
                    str="HTTP/1.1 200 OK\r\nContent-Transfer-Encoding: BASE64\r\nContent-Type: image/jpg\r\nContent-Length: "+bytes+"\r\n\r\n"+f;
                    //System.out.println("IMAGE "+requestPage.substring(1));
                   // System.out.println("IMAGE: "+"HTTP/1.1 200 OK\r\nContent-Type: image/jpg\r\nContent-Length: "+bytes+"\r\n\r\n"+f);
               }catch(Exception e){
                   e.printStackTrace();
               }
            }
        }
        return str;
    }

    public String readFile(String str){
        String ret="";
        try{           
            BufferedReader br = new BufferedReader(new FileReader(str));
            String s;
            while ((s = br.readLine()) != null) {
                ret+=(s);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }

}

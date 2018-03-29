
package teema2;

import java.io.PrintWriter;
import java.io.StringWriter;


public class Teema2 {

    public static void main(String[] args) {
        try{
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            
            League l = new League("Constraints.txt");
            l.countErrors();
            
            for(int i = 0; i < 10000; i++){
                if(i%1000 == 0) l.printErrors();
                l.swapRandomGame();
                for(int j = 0; j < 5; j++){
                    l.swapFromWorst();
                }
                if(l.getErrors() == 0) break;
            }
            
            l.print();
            writer.println(l.getErrors() + " 0 " + l.getErrors());
            writer.println("0 0 0 0");
            writer.println(l.getOutput());
            writer.close();
            
        }catch(Exception e){
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter( writer );
            e.printStackTrace( printWriter );
            printWriter.flush();

            String stackTrace = writer.toString();
            System.out.println(stackTrace);
        }
        
    }
    
}

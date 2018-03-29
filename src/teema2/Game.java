
package teema2;


public class Game {
    private final int home;
    private final int visitor;
    private int locked;
    private int aError;  //Errors for breaking away block
    private int gError; //Errors for breaking  > 1 games
    private int hError; //Errors for breaking home block
    private int lError;  //Errors for being on wrong round for boundgame
    
    public Game(int h, int v) {
        locked = -1;
        home = h;
        visitor = v;
        gError = 0;
        aError = 0;
        hError = 0;
        lError = 0;
    }
    
    public Game(int h, int v, int l) {
        locked = l;
        home = h;
        visitor = v;
        gError = 0;
        aError = 0;
        hError = 0;
        lError = 0;
    }
   
    void addGError(int v) {
        gError += v - 1;
    }
    
    void addHError(int i) {
        hError += i;
    }

    void addAError(int i) {
        aError += i;
    }

    void addLError(int i) {
        lError += i;
    }
    
    void print() {
        System.out.print("[" + (home + 1) + " " + (visitor + 1) + " "+ Integer.toString(gError) +" "+ Integer.toString(hError) +" "+ Integer.toString(aError) +" "+ Integer.toString(lError) +"]");
    }
    
    String output() {
        String ret;
        ret = home + " " + visitor + " " + (locked>=0?"joo":"ei") + "\n";
        return ret;
    }

    public int getVisitor() {
        return visitor;
    }

    public int getHome() {
        return home;
    }
    
    public int getErrors(){
        return gError + hError + aError + lError;
    }

    void setErrors(int i) {
        gError = i;
        hError = i;
        aError = i;
        lError = i;
    }

    int getLocked() {
        return locked;
    }

    void setLocked(int roundNumber) {
        if(roundNumber >= 0) {
            locked = roundNumber;
        }
    }

}
    


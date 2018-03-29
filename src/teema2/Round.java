
package teema2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Round {
    List <Game> games;
    int error;
    private final int t;
    ArrayList<Integer> homeBlocks;
    ArrayList<Integer> awayBlocks;
    private final int roundNumber;
    
    Round(int teams, int rNumber, ArrayList<Integer> hBlocks, ArrayList<Integer> aBlocks) {
        roundNumber = rNumber;
        t = teams;
        error = -1;
        games = new ArrayList<>();
        homeBlocks = hBlocks;
        awayBlocks = aBlocks;
    }
    
    public int addGame(Game g) {
        if(error>=0){
            int change = error;
            games.add(g);
            change -= countErrors();
            return change;
        }else{
            games.add(g);
            return 0;
        }
    }
    
    Game removeGame(int gameIndex) {
        Game g = games.remove(gameIndex);
        countErrors();
        return g;
    }
    
    int testAdd(Game randGame) {
        addGame(randGame);
        int change = error;
        removeGame(games.size()-1);
        change -= error;
        return change;
    }

    public int countErrors() {
        int[] gamesByTeam = new int[t];
        Arrays.fill(gamesByTeam, 0);  //initialize to -1 so we can use the value as errorvalue
        error = 0;

        for(Game g : games){
            gamesByTeam[g.getHome()]++;
            gamesByTeam[g.getVisitor()]++;
        }
        
        for(Game g : games){
            g.setErrors(0);
            if(gamesByTeam[g.getHome()] > 1) g.addGError(gamesByTeam[g.getHome()]);
            if(gamesByTeam[g.getVisitor()] > 1) g.addGError(gamesByTeam[g.getVisitor()]);
            if(homeBlocks.contains(g.getHome())) g.addHError(1);
            if(awayBlocks.contains(g.getVisitor())) g.addAError(1);
            if(g.getLocked() >= 0 && g.getLocked() != roundNumber) g.addLError(100);
            error += g.getErrors();
        }
        
        return error;
    }

    public void print() {
        games.forEach((game) -> {
            game.print();
            System.out.print(" ");
        });
        System.out.println(Integer.toString(error));
    } 
    
    public String getOutput(){
        String output = "";
        
        for(Game g : games){
            output += (roundNumber + 1) + " " + g.output();
        }
        
        return output;
    }
    
    public List<Game> getGames() {
        return games;
    }

    int getError() {
        return error;
    }

    Game getGame(int i) {
        return games.get(i);
    }

    int getRoundNumber() {
        return roundNumber;
    }
}

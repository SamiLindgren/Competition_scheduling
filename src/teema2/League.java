
package teema2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class League {
    
    List<Round> rounds;
    private int error;
    private final int t;
    private final int r;
    private final String leagueName;
    private final ArrayList<String> teamNames;
    private final Random randomGenerator;
    
    public League(String constraintFile) {
        
        Constraints c = new Constraints(constraintFile);
        
        leagueName = c.getLeagueName();
        teamNames = c.getTeamNames();
        t = c.getTeamNumber();
        r = c.getRoundNumber();
        
        error = -1;
        randomGenerator = new Random();
        
        rounds = createRounds(c.getBlockedHome(), c.getBlockedAway());
        addGames(c.getExtraGames(), c.getPreAssigned());
        
    }  
  
    private List<Round> createRounds(ArrayList<ArrayList<Integer>> blockedHome, ArrayList<ArrayList<Integer>> blockedAway) {
        List<Round> ret = new ArrayList<>();
        for(int i = 0; i < r; i++){
            ret.add(new Round(t, i, blockedHome.get(i), blockedAway.get(i))); //Make all rounds with rounds blocked info
        }
        return ret;
    }
    
    private void addGames(ArrayList<Game> extraGames, ArrayList<String> preAssigned) { //Add all normal games and extragames to rounds
        List<Game> games = new ArrayList<>();
        games.addAll(extraGames);
        
        for(int home = 0; home < t; home++){ //Create normal games
            for(int visitor = 0; visitor < t; visitor++){
                if(home == visitor) continue;
                games.add(new Game(home, visitor));
            }
        }
        
        games.forEach((game) -> {  //Loop trough games and mark bound ones as locked to round
            for(String pAssigment : preAssigned){
                if(pAssigment.startsWith(game.getHome() + " " + game.getVisitor() + " ")){
                    String[] tmp = pAssigment.split(" ");
                    int roundNumber = Integer.parseInt(tmp[2]);
                    game.setLocked(roundNumber);
                    preAssigned.remove(preAssigned.indexOf(pAssigment));
                    break;
                }
            }
        });
        
        Collections.shuffle(games);
        games.forEach((game) -> {  //Move games to random rounds
            Round randomRound = rounds.get(randomGenerator.nextInt(rounds.size()));
            randomRound.addGame(game);
        });
    }

    void swapFromWorst() {
        int maxError = -1;
        ArrayList<Round> worstRounds = new ArrayList<>();
        ArrayList<Integer> worstGameIndexes = new ArrayList<>();
        Round worstRound;
        int worstGameIndex;
        
        for(Round round : rounds){
            if(maxError < round.getError()) maxError = round.getError();
        }
        
        for(Round round: rounds) {
            if(round.getError() == maxError) worstRounds.add(round);
        }
        
        worstRound = worstRounds.get(randomGenerator.nextInt(worstRounds.size())); //Pick random round out of worst rounds
        maxError = -1;
        
        for(Game game : worstRound.getGames()){
            if(game.getErrors() > maxError) maxError = game.getErrors();
        }
        
        for(int i = 0; i < worstRound.getGames().size(); i++){
            if(worstRound.getGame(i).getErrors() == maxError) worstGameIndexes.add(i);
        }
        
        worstGameIndex = worstGameIndexes.get(randomGenerator.nextInt(worstGameIndexes.size()));
        
        swapGame(worstRound.getRoundNumber(), worstGameIndex);
        
    }   
    
    int swapRandomGame() {
        int roundIndex = selectRandomRound(1);
        int gameIndex = selectRandomGame(rounds.get(roundIndex));
        return swapGame(roundIndex, gameIndex);
    }
    
    private int selectRandomRound(int minGames) { //Select round with atleast minGames games
        int totalGames = 0;
        int roundIndex = -1;
        while(totalGames < minGames){
            roundIndex = randomGenerator.nextInt(rounds.size());
            totalGames = rounds.get(roundIndex).getGames().size();
        }
        
        return roundIndex;
    }
    
    private int selectRandomGame(Round round) {
        return randomGenerator.nextInt(round.getGames().size());
    }
    
    int swapGame(int roundIndex, int gameIndex) {
        Round startRound = rounds.get(roundIndex);
        int startErrChange = startRound.getError();
        Game randGame = startRound.removeGame(gameIndex);
        startErrChange -= startRound.getError();
        ArrayList<Integer> swapValues = new ArrayList<>();
        
        error -= startErrChange;
        
        for(Round current : rounds){
            int change = current.testAdd(randGame) - startErrChange;
            swapValues.add(change);
        }
        
        
        int minVal = Collections.min(swapValues);
        List<Integer> bestSwapIndexes = new ArrayList<>();
        
        int index = 0;
        for(int swapValue : swapValues){
            if(swapValue == minVal) bestSwapIndexes.add(index);
            index++;
        }
        
        if(bestSwapIndexes.size() >= 2) { //If more than 1 choice take random round that is not starting round
            int swapIndex;
            do{
                swapIndex = bestSwapIndexes.get(randomGenerator.nextInt(bestSwapIndexes.size()));
            }while(swapIndex == roundIndex);
            error -= rounds.get(swapIndex).addGame(randGame);
            return swapIndex;
        }else{
            error -= rounds.get(bestSwapIndexes.get(0)).addGame(randGame);
            return bestSwapIndexes.get(0);
        }
    }
    
    public int countErrors() {
        error = 0;
        rounds.forEach((round) -> {
            addError(round.countErrors());
        });
       
        return error;
    }
    
    private void addError(int e){
        if(error < 0) error = 0;
        error += e;
    }

    void printErrors() {
        System.out.println(error);
    }
    
    public void print() {
        int i = 1;
        for(Round round : rounds){
            System.out.print(i + ". ");
            round.print();
            i++;
        }
        System.out.println("Total error: " + (Integer.toString(error)));
    }
    
    String getOutput() { //Build string for output.txt
        String output = "";
        for(Round r : rounds){
            output += r.getOutput();
        }
        return output;
    }
    
    int getErrors() {
        return error;
    }

}

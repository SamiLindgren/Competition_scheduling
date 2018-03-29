package teema2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Constraints {

    private String leagueName;
    private int t;
    private int rr;
    private int r;
    private ArrayList<String> teamNames;
    private ArrayList<Game> extraGames;
    private ArrayList<String> roundDays;
    private ArrayList<ArrayList<Integer>> blockedHome;
    private ArrayList<ArrayList<Integer>> blockedAway;
    private ArrayList<String> preAssigned;
    
    public Constraints(String constraintFile){
        try{
                //Initialize objects
                BufferedReader bReader = new BufferedReader(new FileReader(constraintFile));
                String line = bReader.readLine();
                teamNames = new ArrayList<>();
                extraGames = new ArrayList<>();
                roundDays = new ArrayList<>();
                blockedHome = new ArrayList<>();
                blockedAway = new ArrayList<>();
                preAssigned = new ArrayList<>();
                
                
                //Helpers
                String[] temp;
                int roundNumber;
                int teamNumber;
                int home;
                int away;
                int category = 0;
                
                while (line != null) {     
                    if(line.startsWith("#")){ //New category
                        category++;
                    } else { //Data input
                        switch(category){
                            case 1: // Leaguename
                                leagueName = line;
                                break;
                            case 2: //Number of teams
                                t = Integer.parseInt(line);
                                break;
                            case 3:  //Number of roundrobins
                                rr = Integer.parseInt(line);
                                break;
                            case 4: //number of rounds
                                r = Integer.parseInt(line);
                                initLists();
                                break;
                            case 5: //team names
                                teamNames.add(line);
                                break;
                            case 6: //additional games
                                temp = line.split(" ");
                                home = Integer.parseInt(temp[0]);
                                away = Integer.parseInt(temp[1]);
                                extraGames.add(new Game(home-1,away-1));
                                break;
                            case 7: //weekdays for rounds
                                roundDays.add(line);
                                break;
                            case 8: //team cannot play at home on a certain day (team-number round-number)
                                temp = line.split(" ");
                                roundNumber = Integer.parseInt(temp[1]) - 1;
                                teamNumber = Integer.parseInt(temp[0]) - 1;
                                blockedHome.get(roundNumber).add(teamNumber);
                                break;
                            case 9: //team cannot play away on a certain day (team-number round-number) 
                                temp = line.split(" ");
                                roundNumber = Integer.parseInt(temp[1]) - 1;
                                teamNumber = Integer.parseInt(temp[0]) - 1;
                                blockedAway.get(roundNumber).add(teamNumber);
                                break;
                            case 10: //game must be preassigned on certain round (team-number team-number round-number)
                                temp = line.split(" ");
                                home = Integer.parseInt(temp[0]) - 1;
                                away = Integer.parseInt(temp[1]) - 1;
                                roundNumber = Integer.parseInt(temp[2]) - 1;
                                preAssigned.add(home + " " + away + " " + roundNumber);
                                break;
                            default:
                                System.out.println("Incorrect format of Constraints.txt");

                        }
                    }
                    line = bReader.readLine();
                }

                bReader.close();
            }catch(Exception e){
                System.out.println("file not found");
            }
    }

    private void initLists() {
        for(int i = 0; i < r; i++){
            getBlockedHome().add(new ArrayList<>());
            getBlockedAway().add(new ArrayList<>());
        }
        System.out.println();
    }

    /**
     * @return the leagueName
     */
    public String getLeagueName() {
        return leagueName;
    }

    /**
     * @return the t
     */
    public int getTeamNumber() {
        return t;
    }

    /**
     * @return the r
     */
    public int getRoundNumber() {
        return r;
    }

    /**
     * @return the teamNames
     */
    public ArrayList<String> getTeamNames() {
        return teamNames;
    }

    /**
     * @return the extraGames
     */
    public ArrayList<Game> getExtraGames() {
        return extraGames;
    }

    /**
     * @return the roundDays
     */
    public ArrayList<String> getRoundDays() {
        return roundDays;
    }

    /**
     * @return the blockedHome
     */
    public ArrayList<ArrayList<Integer>> getBlockedHome() {
        return blockedHome;
    }

    /**
     * @return the blockedAway
     */
    public ArrayList<ArrayList<Integer>> getBlockedAway() {
        return blockedAway;
    }

    /**
     * @return the preAssigned
     */
    public ArrayList<String> getPreAssigned() {
        return preAssigned;
    }
}

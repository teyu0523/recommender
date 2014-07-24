/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gamerecommender;

import java.io.*;
import java.util.*;

/**
 *
 * @author Owner
 */
public class GameRecommender {
    
    double overallAverage;
    double lambda = 0.02;
    double roe = 0.005;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Read in CSV file
        //for every empty rating for a user
            //create a random bu and bi for that position
            //calculate the random basline: baseRating
            //minimizeError(baseRating, bu, bi);
        GameRecommender obj = new GameRecommender();
        List<String[]> users = obj.readCSV();
        obj.getAverageRating(users);
    }
    
    public void getAverageRating(List<String[]> users){
        /*
         * Adding all value together and then diving the total number of value exists
         * 
         * All zeros are considered to be not rated
         */
        int i, j, total=0, count=0;
        int user_size = users.size();
        int rating_size = users.get(0).length;
        for(i=1; i<user_size; i++){
            for(j=1; j<rating_size; j++){
                if(users.get(i)[j]!=null && !users.get(i)[j].isEmpty() && Integer.valueOf(users.get(i)[j])!=0){
                    System.out.println(users.get(i)[j]);
                    total += Integer.valueOf(users.get(i)[j]);
                    count++; 
                }
            }
        }
        if(total!=0){
            overallAverage = (double)total/(double)count;
            System.out.println(overallAverage);
        }
    }

    public List<String[]> readCSV(){
        
        BufferedReader br;
        String line;
        String semicolon = ";";
        String[] parsedline;
        List<String[]> users = new LinkedList<>();
        String[] ratings = null;
        try{
            br = new BufferedReader(new FileReader("data.csv"));
            
            //grabs the first row that contains the 
            while((line = br.readLine())!=null){
                ratings = line.split(",");
                users.add(ratings);
            }
            return(users);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return(null);
    }
    
    /**
     * Used to calculate the predicted rating of given user and video game
     * @param bu
     * @param bi
     * @return 
     */
    public double baseline(double BuBi[]){
        
        return (overallAverage + BuBi[0] + BuBi[1]);
        
    }
    
    /**
     * calculates the mean error of given rating and predicted rating 
     * 
     * @param oldValue
     * @param bu
     * @param bi
     * @return 
     */
    public double meanError(double oldValue, double BuBi[]){
        return (oldValue - overallAverage - BuBi[0] - BuBi[1]);
    }
    
    /**
     * Used to refine bu and bi over 30 iterations
     */
    public double[] minimizeError(double baseRating, double BuBi[]){
        
        
        int i;
        //iterate 30 times to minimize bu and bi
        for(i = 0; i < 30; i++){
           BuBi = StochasticGradientDescent(baseRating, BuBi);
           baseRating = baseline(BuBi);
        }
        
        return BuBi;
    }
    
    /**
     * returns the more accurate bu and bi using the derived equations 
     * @param oldValue
     * @param BuBi
     * @return 
     */
    public double[] StochasticGradientDescent(double oldValue, double BuBi[]){
        BuBi[0] = BuBi[0] + roe*(meanError(oldValue, BuBi) - lambda*BuBi[0]);
        BuBi[1] = BuBi[1] + roe*(meanError(oldValue, BuBi) - lambda*BuBi[1]);
        
        return BuBi;
    }
    
}

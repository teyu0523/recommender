/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gamerecommender;

import java.io.*;
import java.util.ArrayList;

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
        
        
        
    }
    
    public void readCSV(){
        
        BufferedReader br;
        String line;
        String semicolon = ";";
        String[] parsedline;
        
        try{
            br = new BufferedReader(new FileReader("Rankings.csv"));
            
            //grabs the first row that contains the 
            line = br.readLine();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
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

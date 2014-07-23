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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Read in CSV file
        
        
        
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
    public double baseline(double bu, double bi){
        
        return (overallAverage + bu + bi);
        
    }
    
    /**
     * calculates the mean error of given rating and predicted rating 
     * 
     * @param oldValue
     * @param bu
     * @param bi
     * @return 
     */
    public double meanError(double oldValue, double bu, double bi){
        return (Math.pow(oldValue - overallAverage - bu - bi, 2.0) + 
                lambda*Math.pow(bu, 2.0) + lambda*Math.pow(bi, 2.0));
    }
    
    /**
     * Used to refine bu and bi over 30 interations
     */
    public void minimizeError(){
        
        int i;
        
        //iterate 30 times to minimize bu and bi
        for(i = 0; i < 30; i++){
            //Some logic here
        }
        
    }
    
    /**
     * the set of derived equations used to find better bu and bi
     */
    public void gradientDescent(){
        
    }
    
}

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
    
    float overallAverage;

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
    
    
    public float baseline(float bu, float bi){
        
        return (overallAverage + bu + bi);
        
    }
    
    public void minimizeError(){
        
    }
    
    public void gradientDescent(){
        
    }
    
}

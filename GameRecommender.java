/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.util.*;

/**
 *
 * @author Owner
 */
public class GameRecommender {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameRecommender objGR = new GameRecommender();
        List<double[]> users = objGR.readCSV();
        Pearson objPearson = new Pearson();
        if(users == null){
            return;
        }
        List<double[]> usersFilled = objPearson.populateMissing(users);
        Baseline objBaseline = new Baseline();
        objBaseline.populateMissing(users, usersFilled);
    }
    
    public List<double[]> readCSV(){
        
        BufferedReader br;
        String line;
        String semicolon = ";";
        String[] parsedline;
        List<double[]> users = new LinkedList<>();
        double[] ratings = null;
        String[] temp = null;
        try{
            br = new BufferedReader(new FileReader("data.csv"));
            br.readLine();
            while((line = br.readLine())!=null){
                temp = line.split(",");
                ratings = new double[temp.length - 1];
                for(int i = 1; i<temp.length; i++){
                    ratings[i-1] = Double.parseDouble(temp[i]);
                }
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
        
    public void printTable(List<double[]> users){
        int userSize = users.size();
        int itemSize = users.get(1).length;
        for(int i = 0; i<userSize; i++){
            System.out.print("\n");
            for(int j=0; j<itemSize; j++){
                System.out.print(users.get(i)[j] + " ");
            }
            System.out.print("\n");
        }
    }

}

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
        Data dataBase = new Data();
        String filename = "data.csv";
        String requestedUser;
        int method = 0;

        Scanner in = new Scanner(System.in);

        //Ask User which method they want to use 
        // System.out.println("Please select which method GameRecommender will use.\n1.) Pearson/Baseline\n2.) Baseline/Pearson");
        // while(method != 1 || method != 2){
        //     method = Integer.parseInt(in.nextLine());
        //     if(method != 1 || method != 2){
        //         System.out.println("Invalid input. Try again");                
        //     }

        // }


        System.out.println("Enter a User you like to recommend games for.");

        //String[] userList = objGR.getUsers(filename);
        // String[] itemList = objGR.getItems(filename);
        objGR.readCSV(filename, dataBase);
        Pearson objPearson = new Pearson();
        List<double[]> usersFilled = objPearson.populateMissing(dataBase.getUsers());
        Baseline objBaseline = new Baseline();
        objBaseline.populateMissing(dataBase.getUsers(), usersFilled);
    }

    public String[] getItems(String filename){
        BufferedReader br;
        String[] itemList = null;
        try{
            br = new BufferedReader(new FileReader(filename));
            itemList = (br.readLine()).split(",");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return(null);
    }


    public void readCSV(String filename, Data dataBase){
        
        BufferedReader br;
        String line;
        String semicolon = ";";
        String[] parsedline;

        String[] itemList = null;
        List<String> userList = new LinkedList<String>();
        List<double[]> users = new LinkedList<double[]>();
        
        double[] ratings = null;
        String[] temp = null;
        try{
            br = new BufferedReader(new FileReader(filename));
            
            itemList = (br.readLine()).split(",");
            dataBase.setItemList(itemList);
            //br.readLine();
            while((line = br.readLine())!=null){
                temp = line.split(",");
                ratings = new double[temp.length - 1];
                for(int i = 0; i<temp.length; i++){
                    if(i == 0){
                        userList.add(temp[i]);
                    }
                    else{
                        ratings[i-1] = Double.parseDouble(temp[i]);  
                    }
                    
                }
                users.add(ratings);
            }
            dataBase.setUserList(userList);
            dataBase.setUsers(users);
            //return(users);
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        //return(null);
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

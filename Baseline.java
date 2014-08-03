import java.util.*;
import java.io.*;

public class Baseline{
    
    double overallAverage;
    double lambda = 0.02;
    double roe = 0.005;
    public void populateMissing(List<double[]> users, List<double[]> usersFilled){
        //for every empty rating for a user
            //create a random bu and bi for that position
            //calculate the random basline: baseRating
            //minimizeError(baseRating, bu, bi);
        double BuBi[] = new double[2];
        int userSize = users.size();
        int itemSize = users.get(0).length;
        double baseRating;

        getAverageRating(users);

        for(int i=0; i<userSize; i++){
            for(int j=0; j<itemSize; j++){
                if(users.get(i)[j] == 0){
                    BuBi = getBuBi(i, j, userSize, itemSize, usersFilled);
                    System.out.println("Initital Bu: " + BuBi[0] + " Bi: " + BuBi[1]);
                    baseRating = baseline(BuBi);
                    System.out.println("base rating: " + usersFilled.get(i)[j]);
                    BuBi = minimizeError(usersFilled.get(i)[j], BuBi);
                    System.out.println("New Bu: " + BuBi[0] + " Bi: " + BuBi[1]);
                    System.out.println("New rating after minimizeError: " + baseline(BuBi) + "\n");  
                }
            }
        }

    }
    
    public double[] getBuBi(int i_missing, int j_missing, int userSize, int itemSize, List<double[]> usersFilled){
        double BuBi[] = new double[2];
        double totalRating = 0, totalCount = 0;
        for(int j = 0; j<itemSize; j++){
            if(j != j_missing){
                totalRating += usersFilled.get(i_missing)[j];
                totalCount++;
            }
        }
        BuBi[0] =  totalRating/totalCount - overallAverage;
        totalRating = 0;
        totalCount = 0;
        for(int i = 0; i<userSize; i++){
            if(i != i_missing){
                totalRating += usersFilled.get(i)[j_missing];
                totalCount++;
            }
        }
        BuBi[1] = totalRating/totalCount - overallAverage;
        return(BuBi);
    }

    public void getAverageRating(List<double[]> users){
        /*
         * Adding all value together and then diving the total number of value exists
         * 
         * All zeros are considered to be not rated
         */
        int i, j;
        double userSize = users.size(), total = 0, count = 0;
        double ratingSize = users.get(0).length;
        for(i=1; i<userSize; i++){
            for(j=1; j<ratingSize; j++){
                if(users.get(i)[j]!=0){
                    total += users.get(i)[j];
                    count++; 
                }
            }
        }
        if(total!=0){
            overallAverage = total/count;
            System.out.println("overallAverage: " + overallAverage);
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
    public double meanError(double baseRating, double BuBi[]){
        // r - mu - bu - bi
        return (baseRating - overallAverage - BuBi[0] - BuBi[1]);
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
        //bu <-- bu + roe ( eui - lamda1 * bu)
        //bi <-- bi + roe ( eui - lamda2 * bi)
        BuBi[0] = BuBi[0] + roe*(meanError(oldValue, BuBi) - lambda*BuBi[0]);
        BuBi[1] = BuBi[1] + roe*(meanError(oldValue, BuBi) - lambda*BuBi[1]);
        
        return BuBi;
    }
}
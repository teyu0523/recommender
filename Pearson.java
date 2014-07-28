import java.util.*;
import java.io.*;

public class Pearson{

	public static void main(String[] args){
		Pearson obj = new Pearson();
		List<double[]> users = obj.readCSV();
		if(users == null){
			return;
		}
		obj.populateMissing(users);
	}

	public List<double[]> populateMissing(List<double[]> users){
		int i, j;
		int userSize = users.size();
		int itemSize = users.get(0).length;
		double temp;
		List<double[]> usersFilled = new LinkedList<double[]>();
		for(i=0; i<userSize; i++){
			usersFilled.add(users.get(i).clone());
		}
		for(i=0; i<userSize; i++){
			for(j=0; j<itemSize; j++){
				if(users.get(i)[j] == 0){
					temp = pearsonAlgo(i, j, users);
					if(temp != -1){
						usersFilled.get(i)[j] = temp;
					}
				}
			}
		}
		printTable(usersFilled);
		return(usersFilled);
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

	public double pearsonAlgo(int i_missing, int j_missing, List<double[]> users){
		int i, j;
		int userSize = users.size();
		int itemSize = users.get(0).length;
		double itemAverageCount = 0, itemTotalValue = 0;
		double totalRatedItemB = 0, totalRatedItemA = 0, totalRatedItemCountA = 0, totalRatedItemCountB = 0;
		double averageA, averageB;
		double simDenomeratorB = 0, simDenomeratorA = 0, simNumerator = 0;
		double sim;
		for(i=0; i<userSize; i++){
			if(i != i_missing && users.get(i)[j_missing] != 0){
				for(j=0; j<itemSize; j++) {
					if(j != j_missing && users.get(i)[j] != 0 && users.get(i_missing)[j] != 0){
						totalRatedItemCountA++;
						totalRatedItemCountB++;
						totalRatedItemA += users.get(i_missing)[j];
						totalRatedItemB += users.get(i)[j];
					}
				}
				if(totalRatedItemCountA != 0){
					averageA = totalRatedItemA/totalRatedItemCountA;
					averageB = totalRatedItemB/totalRatedItemCountB;
					for(j=0; j<itemSize; j++) {
						if(j != j_missing && users.get(i)[j] != 0 && users.get(i_missing)[j] != 0) {
							System.out.println("rap: " + users.get(i_missing)[j] + " avA: " + averageA + " rbp: " + users.get(i)[j] +  " avB: " + averageB);
							simNumerator += (users.get(i_missing)[j] - averageA) * (users.get(i)[j] - averageB);
							simDenomeratorB += Math.pow((users.get(i_missing)[j] - averageA), 2);
							simDenomeratorA += Math.pow((users.get(i)[j] - averageB), 2);
						}
					}
					sim = simNumerator/(Math.sqrt(simDenomeratorA)*Math.sqrt(simDenomeratorB));
					itemTotalValue += sim*users.get(i)[j_missing];
					itemAverageCount++;
					System.out.println("sim value: " + sim + "i: " + i);
				}
				totalRatedItemA = 0;
				totalRatedItemB = 0;
				simDenomeratorB = 0;
				simDenomeratorA = 0;
				totalRatedItemCountA = 0;
				totalRatedItemCountB = 0;
				simNumerator = 0;
			}
		}
		if(itemAverageCount != 0){
			return(itemTotalValue/itemAverageCount);
		}
		return(-1);
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
}
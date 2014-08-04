import java.util.*;
import java.io.*;

public class Pearson{

	public List<double[]> populateMissing(List<double[]> users, List<double[]> usersFilled){
		int i, j;
		int userSize = users.size();
		int itemSize = users.get(0).length;
		List<double[]> usersFilledNew = new LinkedList<double[]>();
		double temp;

		if(usersFilled.isEmpty()){
			System.out.println("Starting pearson first to fillout missing values.");
			for(i=0; i<userSize; i++){
				usersFilledNew.add(users.get(i).clone());
			}
			for(i=0; i<userSize; i++){
				for(j=0; j<itemSize; j++){
					if(users.get(i)[j] == 0){
						temp = pearsonAlgo(i, j, users);
						System.out.println("Average Sim value: " + temp + "\n");
						if(temp != -1){
							usersFilledNew.get(i)[j] = temp;
						}
					}
				}
			}
		} else {
			System.out.println("Adding pearson on top of previous algorithm.");
			for(i=0; i<userSize; i++){
				usersFilledNew.add(usersFilled.get(i).clone());
			}
			for(i=0; i<userSize; i++){
				for(j=0; j<itemSize; j++){
					if(users.get(i)[j] == 0){
						temp = pearsonAlgo(i, j, usersFilled);
						System.out.println("Average Sim value: " + temp + "\n");
						if(temp != -1){
							usersFilledNew.get(i)[j] = temp;
						}
					}
				}
			}
		}

		return(usersFilledNew);
	}


	public double pearsonAlgo(int iMissing, int jMissing, List<double[]> users){
		int i, j;
		int userSize = users.size();
		int itemSize = users.get(0).length;
		double itemWeightCount = 0, itemTotalValue = 0;
		double totalRatedItemB = 0, totalRatedItemA = 0, totalRatedItemCountA = 0, totalRatedItemCountB = 0;
		double averageA, averageB;
		double simDenomeratorB = 0, simDenomeratorA = 0, simNumerator = 0;
		double sim, simDenomeratorSqrt;

		for(i=0; i<userSize; i++){
			if(i != iMissing && users.get(i)[jMissing] != 0){
				for(j=0; j<itemSize; j++) {
					if(j != jMissing && users.get(i)[j] != 0 && users.get(iMissing)[j] != 0){
						totalRatedItemCountA++;
						totalRatedItemCountB++;
						totalRatedItemA += users.get(iMissing)[j];
						totalRatedItemB += users.get(i)[j];
					}
				}
				if(totalRatedItemCountA != 0){
					averageA = totalRatedItemA/totalRatedItemCountA;
					averageB = totalRatedItemB/totalRatedItemCountB;
					for(j=0; j<itemSize; j++) {
						if(j != jMissing && users.get(i)[j] != 0 && users.get(iMissing)[j] != 0) {
							//System.out.println("rap: " + users.get(iMissing)[j] + " avA: " + averageA + " rbp: " + users.get(i)[j] +  " avB: " + averageB);
							simNumerator += (users.get(iMissing)[j] - averageA) * (users.get(i)[j] - averageB);
							simDenomeratorA += Math.pow((users.get(iMissing)[j] - averageA), 2);
							simDenomeratorB += Math.pow((users.get(i)[j] - averageB), 2);
						}
					}
					simDenomeratorSqrt = (Math.sqrt(simDenomeratorA)*Math.sqrt(simDenomeratorB));
					if(simDenomeratorSqrt != 0){
						sim = simNumerator/simDenomeratorSqrt;
						itemTotalValue += sim*users.get(i)[jMissing];
						itemWeightCount += (+sim);
						System.out.println(" i: " + i + " sim value: " + sim + " avgNum: " + itemTotalValue + " avgDen " + itemWeightCount);
					}
					
				}
				totalRatedItemA = 0;
				totalRatedItemB = 0;
				simDenomeratorB = 0;
				simDenomeratorA = 0;
				totalRatedItemCountA = 0;
				totalRatedItemCountB = 0;
				simNumerator = 0;
				sim = 0;
			}
		}
		if(itemWeightCount != 0){
			System.out.println(itemTotalValue + "   " + itemWeightCount);
			return(itemTotalValue/itemWeightCount);
		}
		return(-1);
	}
}
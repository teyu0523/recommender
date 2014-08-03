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
			for(i=0; i<userSize; i++){
				usersFilledNew.add(users.get(i).clone());
			}
			for(i=0; i<userSize; i++){
				for(j=0; j<itemSize; j++){
					if(users.get(i)[j] == 0){
						temp = pearsonAlgo(i, j, users);
						System.out.println("Average Sim value: "+temp);
						if(temp != -1){
							usersFilledNew.get(i)[j] = temp;
						}
					}
				}
			}
		} else {
			for(i=0; i<userSize; i++){
				usersFilledNew.add(usersFilled.get(i).clone());
			}
			for(i=0; i<userSize; i++){
				for(j=0; j<itemSize; j++){
					if(users.get(i)[j] == 0){
						temp = pearsonAlgo(i, j, usersFilled);
						System.out.println("Average Sim value: "+temp);
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
		double itemAverageCount = 0, itemTotalValue = 0;
		double totalRatedItemB = 0, totalRatedItemA = 0, totalRatedItemCountA = 0, totalRatedItemCountB = 0;
		double averageA, averageB;
		double simDenomeratorB = 0, simDenomeratorA = 0, simNumerator = 0;
		double sim;
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
							simDenomeratorB += Math.pow((users.get(iMissing)[j] - averageA), 2);
							simDenomeratorA += Math.pow((users.get(i)[j] - averageB), 2);
						}
					}
					sim = simNumerator/(Math.sqrt(simDenomeratorA)*Math.sqrt(simDenomeratorB));
					itemTotalValue += sim*users.get(i)[jMissing];
					itemAverageCount += sim;
					System.out.println("sim value: " + sim + " i: " + i);
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
}
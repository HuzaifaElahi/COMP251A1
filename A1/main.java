package A1;

import A1.Chaining.*;
import A1.Open_Addressing.*;
import java.io.*;
import java.util.*;

public class main {

	/**
	 * Calculate 2^w
	 */
	public static int power2(int w) {
		return (int) Math.pow(2, w);
	}

	/**
	 * Uniformly generate a random integer between min and max, excluding both
	 */
	public static int generateRandom(int min, int max, int seed) {
		Random generator = new Random();
		//if the seed is equal or above 0, we use the input seed, otherwise not.
		if (seed >= 0) {
			generator.setSeed(seed);
		}
		int i = generator.nextInt(max - min - 1);
		return i + min + 1;
	}

	/**
	 * export CSV file
	 */
	public static void generateCSVOutputFile(String filePathName, ArrayList<Double> alphaList, ArrayList<Double> avColListChain, ArrayList<Double> avColListProbe) {
		File file = new File(filePathName);
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			int len = alphaList.size();
			fw.append("Alpha");
			for (int i = 0; i < len; i++) {
				fw.append("," + alphaList.get(i));
			}
			fw.append('\n');
			fw.append("Chain");
			for (int i = 0; i < len; i++) {
				fw.append("," + avColListChain.get(i));
			}
			fw.append('\n');
			fw.append("Open Addressing");
			for (int i = 0; i < len; i++) {
				fw.append(", " + avColListProbe.get(i));
			}
			fw.append('\n');
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		/*===========PART 1 : Experimenting with n===================*/
		//Initializing the three arraylists that will go into the output 
		ArrayList<Double> alphaList = new ArrayList<Double>();
		ArrayList<Double> avColListChain = new ArrayList<Double>();
		ArrayList<Double> avColListProbe = new ArrayList<Double>();

		//Keys to insert into both hash tables
		int[] keysToInsert = {164, 127, 481, 132, 467, 160, 205, 186, 107, 179,
				955, 533, 858, 906, 207, 810, 110, 159, 484, 62, 387, 436, 761, 507,
				832, 881, 181, 784, 84, 133, 458, 36};

		//values of n to test for in the experiment
		int[] nList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
		//value of w to use for the experiment on n
		int w = 10;

		for (int n : nList) {

			//initializing two hash tables with a seed
			Chaining MyChainTable = new Chaining(w, 137);
			Open_Addressing MyProbeTable = new Open_Addressing(w, 137);

			/*Use the hash tables to compute the average number of 
                        collisions over keys keysToInsert, for each value of n. 
                        The format of the three arraylists to fillis as follows:

                        alphaList = arraylist of all tested alphas 
                                   (corresponding to each tested n)
                        avColListChain = average number of collisions for each
                                         Chain experiment 
                                         (make sure the order matches alphaList)
                        avColListProbe =  average number of collisions for each
                                         Linear Probe experiment
                                           (make sure the order matches)
                        The CSV file will output the result which you can visualize
			 */
			int chainCollision = 0;
			int probeCollision = 0;
			for(int index = 0 ; index < n ; index++) {
				chainCollision += MyChainTable.insertKey(keysToInsert[index]);
				probeCollision += MyProbeTable.insertKey(keysToInsert[index]);
			}
			alphaList.add((n/((double)MyChainTable.m)));
			avColListChain.add((chainCollision/((double)n)));  
			avColListProbe.add((probeCollision/((double)n)));  


			//ADD YOUR CODE HERE
		}

		generateCSVOutputFile("n_comparison.csv", alphaList, avColListChain, avColListProbe);

		/*===========    PART 2 : Test removeKey  ===================*/
		/* In this exercise, you apply your removeKey method on an example.
        Make sure you use the same seed, 137, as you did in part 1. You will
        be penalized if you don't use the same seed.
		 */
		//Please not the output CSV will be slightly wrong; ignore the labels.
		ArrayList<Double> removeCollisions = new ArrayList<Double>();
		ArrayList<Double> removeIndex = new ArrayList<Double>();
		int[] keysToRemove = {6, 8, 164, 180, 127, 3, 481, 132, 4, 467, 5, 160,
				205, 186, 107, 179};

		//ADD YOUR CODE HERE

		Open_Addressing MyProbeTableRemove = new Open_Addressing(w, 137);
		double removeCollisionsVal = 0;
		for(int insertIndex = 0; insertIndex < 16 ; insertIndex++) {
			MyProbeTableRemove.insertKey(keysToInsert[insertIndex]);
		}
		for(int i = 0; i < keysToRemove.length ; i++) {
			int key = keysToRemove[i];
			removeCollisionsVal = MyProbeTableRemove.removeKey(key);
			removeCollisions.add(removeCollisionsVal);
			removeIndex.add((double)i);
		}


		generateCSVOutputFile("remove_collisions.csv", removeIndex, removeCollisions, removeCollisions);

		/*===========PART 3 : Experimenting with w===================*/

		/*In this exercise, the hash tables are random with no seed. You choose 
                values for the constant, then vary w and observe your results.
		 */
		//generating random hash tables with no seed can be done by sending -1
		//as the seed. You can read the generateRandom method for detail.
		//int randomNumber = generateRandom(0,55,-1);
		//Chaining MyChainTableTask3 = new Chaining(8, -1);
		//Open_Addressing MyProbeTableTask3 = new Open_Addressing(8, -1);
		//Lists to fill for the output CSV, exactly the same as in Task 1.
		ArrayList<Double> alphaList2 = new ArrayList<Double>();
		ArrayList<Double> avColListChain2 = new ArrayList<Double>();
		ArrayList<Double> avColListProbe2 = new ArrayList<Double>();

		int[] wList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
		int[] nListRand = new int [32];

		for(int i = 0 ; i < 32 ; i++) {
			nListRand[i] = generateRandom(0,55,-1);
		}

		for(int wTest : wList) {

			Chaining MyChainTableTask3 = new Chaining(wTest, -1);
			Open_Addressing MyProbeTableTask3 = new Open_Addressing(wTest, -1);

			int chainCollision = 0;
			int probeCollision = 0;
			for(int indexTask3 = 0 ; indexTask3 < nListRand.length ; indexTask3++) {
				chainCollision += MyChainTableTask3.insertKey(nListRand[indexTask3]);
				probeCollision += MyProbeTableTask3.insertKey(nListRand[indexTask3]);
			}
			alphaList2.add((nListRand.length/((double)MyChainTableTask3.m)));
			avColListChain2.add((chainCollision/((double)nListRand.length)));  
			avColListProbe2.add((probeCollision/((double)nListRand.length)));  
		}
		//ADD YOUR CODE HERE
		generateCSVOutputFile("w_comparison.csv", alphaList2, avColListChain2, avColListProbe2);
	}
}

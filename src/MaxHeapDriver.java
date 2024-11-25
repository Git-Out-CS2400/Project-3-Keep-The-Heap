import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class MaxHeapDriver {

	public static void main(String[] args) throws IOException {
		String filename;
		Scanner scan = new Scanner(System.in); //scanner input
		System.out.println("Enter the filename:"); 
		filename=scan.next(); //get the filename
		ArrayList<Integer> numbers = new ArrayList<>(); //Create an arrayList that will hold the numbers from the file
		
		//File object to read the file
		File file = new File(filename);
		
		//Scanner to read file contents
		Scanner inputfile=new Scanner(file);
		//Read contents from the file
		while(inputfile.hasNext()) {
			String num = inputfile.nextLine().trim(); //use trim method to cut out empty space
			if(!num.isEmpty())
				numbers.add(Integer.parseInt(num)); //convert to integer and add to the arrayList
		}
		
		//Turn the arrayList into an array 
		Integer[] heapArray = numbers.toArray(new Integer[numbers.size()]); 
		
		
		MaxHeap<Integer> sequentialHeap = new MaxHeap<>();
		sequentialHeap.sequentialBuild(heapArray);

		//Create a new MaxHeapObject and pass the heapArray as an argument to build using the optimal method
		MaxHeap<Integer> optimalHeap = new MaxHeap<>();
		optimalHeap.optimalBuild(heapArray);
		
		System.out.println("Done!");
		System.out.println("Both the sequential and optimal method's output will be in outputfile.txt.");

		FileWriter fw = new FileWriter("outputfile.txt");
		PrintWriter outfile = new PrintWriter(fw);
		outfile.println("=====================================================================");
		outfile.println("Here's the first 10 items of the heap (made by the sequential method): ");
		for (int i = 1; i <= 10; i++) {
            outfile.print(sequentialHeap.getItem(i) + " ");
		}
		//Print the number of swaps
		outfile.println("\nHere's the number of swaps with the sequential method: " + sequentialHeap.getSwaps());
		//call removeMax 10 times
		for (int i= 1; i<=10;i++) {
			sequentialHeap.removeMax();
		}
		//Print the new heap's first 10 items after the removals
		outfile.println("Here's the first 10 items of the heap after 10 removals: ");
		for (int i = 1; i <= 10; i++) {
            outfile.print(sequentialHeap.getItem(i) + " ");
		}
		//Print number of swaps
		outfile.println("\nHere's the number of swaps after the removals: " + sequentialHeap.getSwaps());


		
		
		outfile.println("\n\nHere's the first 10 items of the heap (made by the optimal method): ");
		for (int i = 1; i <= 10; i++) {
            outfile.print(optimalHeap.getItem(i) + " ");
		}
		//Print the number of swaps
		outfile.println("\nHere's the number of swaps with the optimal method: " + optimalHeap.getSwaps());
		//call removeMax 10 times
		for (int i= 1; i<=10;i++) {
			optimalHeap.removeMax();
		}
		//Print the new heap's first 10 items after the removals
		outfile.println("Here's the first 10 items of the heap after 10 removals: ");
		for (int i = 1; i <= 10; i++) {
            outfile.print(optimalHeap.getItem(i) + " ");
		}
		//Print number of swaps
		outfile.println("\nHere's the number of swaps after the removals: " + optimalHeap.getSwaps());
		outfile.println("=====================================================================");
		inputfile.close(); //close the scanner
		outfile.close();  //close the file
	}

}

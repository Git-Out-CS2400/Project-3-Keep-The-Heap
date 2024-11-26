import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MaxHeapDriver {

    public static void main(String[] args) throws IOException {
        // Get the filename from the user
        String filename = getFilenameFromUser();

        // Read numbers from the file
        Integer[] heapArray = readNumbersFromFile(filename);

        // Build heaps using sequential and optimal methods
        MaxHeap<Integer> sequentialHeap = new MaxHeap<>();
        sequentialHeap.sequentialBuild(heapArray);

        MaxHeap<Integer> optimalHeap = new MaxHeap<>();
        optimalHeap.optimalBuild(heapArray);

        System.out.println("Done! Both the sequential and optimal method's output will be in outputfile.txt.");

        // Write the results to the output file
        writeResultsToFile(sequentialHeap, optimalHeap);
    }

    // Get the filename from the user
    private static String getFilenameFromUser() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the filename:");
        String filename = scan.next();
        scan.close();
        return filename;
    }

    // Read numbers from file and return them as an array
    private static Integer[] readNumbersFromFile(String filename) throws IOException {
        ArrayList<Integer> numbers = new ArrayList<>();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);

        while (inputFile.hasNext()) {
            String num = inputFile.nextLine().trim();
            if (!num.isEmpty()) {
                numbers.add(Integer.parseInt(num));
            }
        }
        inputFile.close();
        return numbers.toArray(new Integer[0]);
    }

    // Write the heap results to the output file
    private static void writeResultsToFile(MaxHeap<Integer> sequentialHeap, MaxHeap<Integer> optimalHeap) throws IOException {
        try (PrintWriter outfile = new PrintWriter(new FileWriter("outputfile.txt"))) {
            outfile.println("=====================================================================");

            // Sequential method
            writeHeapResults(outfile, "Heap built using sequential insertions", sequentialHeap);
            outfile.println("Number of swaps in the heap creation: " + sequentialHeap.getSwaps());
            performRemovals(sequentialHeap, 10);
            writeHeapResults(outfile, "Heap after 10 removals", sequentialHeap);

            // Optimal method
            outfile.println();
            writeHeapResults(outfile, "Heap built using optimal method", optimalHeap);
            outfile.println("Number of swaps in the heap creation: " + optimalHeap.getSwaps());
            performRemovals(optimalHeap, 10);
            writeHeapResults(outfile, "Heap after 10 removals", optimalHeap);
            
            outfile.println();
            outfile.println("Github link: https://github.com/Git-Out-CS2400/Project-3.git");

            outfile.println("=====================================================================");
        }
    }

    // Method to write results to output file
    private static void writeHeapResults(PrintWriter outfile, String label, MaxHeap<Integer> heap) {
        outfile.print(label + ": ");
        outfile.println(formatHeap(heap, 10));
    }

    // Method to perform heap removals
    private static void performRemovals(MaxHeap<Integer> heap, int count) {
        for (int i = 0; i < count && !heap.isEmpty(); i++) {
            heap.removeMax();
        }
    }

    // Method to handle formatting of output
    private static String formatHeap(MaxHeap<Integer> heap, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= count && i <= heap.getSize(); i++) {
            sb.append(heap.getItem(i));
            if (i < count && i < heap.getSize()) {
                sb.append(",");
            }
        }
        sb.append(",...");
        return sb.toString();
    }
}

import java.util.Arrays;
public final class MaxHeap<T extends Comparable<? super T>>
             implements MaxHeapInterface<T>
{
   private T[] heap;      // Array of heap entries; ignore heap[0]
   private int lastIndex; // Index of last entry and number of entries
   private boolean integrityOK = false;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;
	private int swaps=0;
   
   public MaxHeap()
   {
      this(DEFAULT_CAPACITY); // Call next constructor
   } // end default constructor
   
   public MaxHeap(int initialCapacity)
   {
      // Is initialCapacity too small?
      if (initialCapacity < DEFAULT_CAPACITY)
         initialCapacity = DEFAULT_CAPACITY;
      else // Is initialCapacity too big?
         checkCapacity(initialCapacity);
      
      // The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      T[] tempHeap = (T[])new Comparable[initialCapacity + 1];
      heap = tempHeap;
      lastIndex = 0;
      integrityOK = true;
   } // end constructor
   public MaxHeap(T[] entries) {
	   this(entries.length);
	   lastIndex=entries.length;
	   swaps=0;
	   for(int index=0; index<entries.length;index++)
		   heap[index+1]=entries[index];
	   for(int rootIndex=lastIndex/2;rootIndex>0;rootIndex--)
		   reheap(rootIndex);
   }

   public void sequentialBuild(T[]entries){
      
      checkCapacity(entries.length);
      int tempSwaps = 0;

      for(int index = 0; index < entries.length; index++){
         tempSwaps = this.add(entries[index]);
      }

      swaps = tempSwaps;
   }

   public void optimalBuild(T[] entries) {
	    lastIndex = entries.length; // Set lastIndex based on the input array's length
	    swaps = 0; // Reset swap count
	    // Initialize heap array, assuming it's large enough
	    for (int index = 0; index < entries.length; index++) {
	    	ensureCapacity();
	        heap[index + 1] = entries[index]; // Fill the heap starting from index 1
	    }
	    // Start heapifying from the last non-leaf node
	    for (int rootIndex = lastIndex / 2; rootIndex > 0; rootIndex--) {
	        reheap(rootIndex); // Reheapify the tree
	    }
	}



   public T getItem(int index) {
       if (index < 1 || index > lastIndex) {
           throw new IndexOutOfBoundsException("Invalid index.");
       }
       return heap[index];  // Return the element at the specified index
   }
   public int getSwaps() {
	   return swaps;
   }


   // build a heap from an array optimal
   public int add(T newEntry)
   {
       checkIntegrity();
       int newIndex = lastIndex + 1;
       int parentIndex = newIndex / 2;
       while ((parentIndex > 0) && newEntry.compareTo(heap[parentIndex]) > 0)
       {
           heap[newIndex] = heap[parentIndex];
           newIndex = parentIndex;
           parentIndex = newIndex / 2;
           swaps++;
       }
       heap[newIndex] = newEntry;
       lastIndex++;
       ensureCapacity();

       return swaps;
   } // end add

   public T removeMax()
   {
	   checkIntegrity();
	   T root=null;
	   if(!isEmpty()) {
		   root=heap[1];
		   heap[1]=heap[lastIndex];
		   lastIndex--;
		   reheap(1);
	   }
	   return root;
   } // end removeMax

   public T getMax()
   {
		checkIntegrity();
      T root = null;
      if (!isEmpty())
         root = heap[1];
      return root;
   } // end getMax

   public boolean isEmpty()
   {
      return lastIndex < 1;
   } // end isEmpty

   public int getSize()
   {
      return lastIndex;
   } // end getSize

   public void clear()
   {
		checkIntegrity();
      while (lastIndex > -1)
      {
         heap[lastIndex] = null;
         lastIndex--;
      } // end while
      lastIndex = 0;
   } // end clear
   
   //Private methods
   private void reheap(int rootIndex)
   {
       boolean done = false;
       T orphan = heap[rootIndex];
       int leftChildIndex = 2 * rootIndex;
       while (!done && (leftChildIndex <= lastIndex))
       {
           int largerChildIndex = leftChildIndex;
           int rightChildIndex = leftChildIndex + 1;
           if ((rightChildIndex <= lastIndex) && heap[rightChildIndex].compareTo(heap[largerChildIndex]) > 0)
           {
               largerChildIndex = rightChildIndex;
           }
           if (orphan.compareTo(heap[largerChildIndex]) < 0)
           {
               heap[rootIndex] = heap[largerChildIndex];
               rootIndex = largerChildIndex;
               leftChildIndex = 2 * rootIndex;
               swaps++; //add 1 to swap count
           }
           else
               done = true;
       }
       heap[rootIndex] = orphan;
   }
   private void checkIntegrity()
   {
      if (!integrityOK)
         throw new SecurityException ("MaxHeap object is corrupt.");
   } // end checkintegrity
   private void checkCapacity(int capacity)
   {
      if (capacity > MAX_CAPACITY)
         throw new IllegalStateException("Attempt to create a bag whose capacity exceeds " +
                                         "allowed maximum of " + MAX_CAPACITY);
   } // end checkCapacity
   private void ensureCapacity()
   {
       if (lastIndex >= heap.length - 1)  // Check if array is full
       {
           int newCapacity = 2 * heap.length;
           checkCapacity(newCapacity);  // Ensure the new capacity does not exceed maximum
           heap = Arrays.copyOf(heap, newCapacity);
       }
   }

} // end MaxHeap

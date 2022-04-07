package cmsc256;

public class MinHeap <E extends Comparable<? super E>>{
    private E[] heap;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 8;

    //constructor to allow clients of the class to set the initial capacity of the heap
    @SuppressWarnings("unchecked")
    public MinHeap(int capacity){
        if(capacity > 0){ //make sure array capacity is greater than 0 (not empty)
            Object[] temp; //create Object array
            temp = new Comparable[capacity];
            heap = (E[]) temp;
        }
        else{
            throw new IllegalArgumentException("Array size must be greater than 0.");
        }
    }

    //constructor that uses the default capacity
    public MinHeap(){
        this(DEFAULT_CAPACITY);
    }

    //accessor method for the private size instance variable
    public int size(){
        return size;
    }

    //method to determine if the heap is empty
    public boolean isEmpty(){
        return size == 0;
    }

    //method to expand the size of the internal array
    private void expand(){
        @SuppressWarnings("unchecked")
                E[] newHeap = (E[]) new Comparable[heap.length*2];
        for(int i = 0; i < size(); i++){
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    //method that swap two elements of the array given two indices
    private void swapElements(int index1, int index2){
        E temp = heap[index1];
        heap[index1] = heap[index2];
    }

    //method that gets the index od a node's parent node in the array given the index of the child
    private int getParentIndex(int childIndex){
        //if odd, child is a left node
        if(childIndex % 2 != 0){
            return childIndex / 2;
        }
        //if even, child is a right node
        else{
            return childIndex / 2 - 1;
        }
    }

    //method to determine the smaller child index (if position has two children)
    private int smallerChildIndex(int parentIndex){
        int smaller = parentIndex;
        //get left child index by calculation based on parent index
        int leftChild = 2 * smaller;
        //if the left child index is in bounds of the heap...
        if(leftChild < size() - 1){
            //set smaller to left if left is smaller than parent
            if(heap[leftChild].compareTo(heap[smaller]) < 0)
                smaller = leftChild;
            //get right child index by calculation based on parent index
            int rightChild = leftChild + 1;
            //if the right child index is in bounds of the heap...
            if(rightChild < size() - 1){
                //set smaller to right if right is smaller than parent
                if(heap[rightChild].compareTo(heap[smaller]) < 0)
                    smaller = rightChild;
            }
        }
        return smaller;
    }

    //method to insert an element into the next available location of the heap
    public void insert(E element){
        int position = size(); //index that the node will be inserted
        //check the array length and expand if needed
        if(position == heap.length){
            this.expand();
        }
        size++; //increment size of the heap
        heap[position] = element; //add element at the last index
        int parentIndex = getParentIndex(position);
        while(position > 0 && heap[position].compareTo(heap[parentIndex]) < 0){
            //if parent is greater, swap parent and node
            swapElements(parentIndex, position);
            //update position of the new element and find next parent up
            position = parentIndex;
            parentIndex = getParentIndex(parentIndex+1);
        }
    }

    public static void main(String[] args){
        MinHeap<Integer> mh = new MinHeap<>();

        mh.insert(2);
        mh.insert(4);
        mh.insert(1);
        mh.insert(10);
        mh.insert(3);
        mh.insert(6);
        mh.insert(15);
        mh.insert(12);
        mh.insert(16);
        mh.insert(5);

        //remove all of the nodes from heap
        while(!mh.isEmpty()){
            System.out.println(mh.remove());
        }
        System.out.println();
    }

    //method that removes the minimum node from the tree (root)
    public E remove(){
        if (isEmpty()){ //if heap is empty, return null
            return null;
        }
        //take out root and place last node at root position
        E min = heap[0];
        heap[0] = heap[size() - 1];
        heap[size() - 1] = null; //set the removed location to null
        size--; //decrement the size of heap
        //position of new root and its smaller child
        int position = 0;
        int smallerIndex = smallerChildIndex(position);
        //while there is a smaller child, swap parent and child
        while(smallerIndex != position) {
            swapElements(position, smallerIndex);
            //update position of node and get new smaller child
            position = smallerIndex;
            smallerIndex = smallerChildIndex(position);
        }
        return min;
    }

}

























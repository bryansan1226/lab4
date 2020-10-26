package lab4;
import java.util.Scanner;

import assignmentThree.LinkedList;
import assignmentThree.LinkedList.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class lab4 {

	public static void main(String[] args)throws Exception {
		LinkedList playlist=new LinkedList();
		LinkedList playedSongs=new LinkedList();
		System.out.println("Populating playlist...");
		String[] myFiles= {"week1.csv","week2.csv","week3.csv","week4.csv","week5.csv","week6.csv",
		         "week7.csv","week8.csv","week9.csv","week10.csv","week11.csv","week12.csv"}; //Adding all the files to an array to extract data with a single loop
		for(int i=0;i<myFiles.length;i++)
		{
			Scanner scanner=new Scanner(new File(myFiles[i]),"UTF-8");
			scanner.useDelimiter(",");
			scanner.nextLine();
			for(int j=0;j<200;j++)
			{
				scanner.nextLine();
				scanner.next();
				String name=scanner.next().replaceAll("^\"|\"$", "");//Removes the quotation marks on some names in preparation for sorting
				if(!playlist.foundInList(playlist, name))	//The loop will only add the name to the list if it cannot already find the name 
					playlist=playlist.insert(playlist, name);
			}
			scanner.close();
		}
		System.out.println("Sorting list...");
		playlist.sort(playlist, playlist.getSize(playlist));//function that sorts the populated playlist with a bubble sort
		System.out.println("Sort complete");
		int choice=-1;
		Scanner myChoice= new Scanner(System.in);
		System.out.println("MAIN MENU\n(1) Play next song\n(2) Play previous song\n(3)Show songs in queue"
				+ "\n(4) Show recently played songs\nEnter 0 to exit");
		while(choice!=0) {
			System.out.println("Enter your selection: ");
			choice=myChoice.nextInt();
			if(choice==0) break;
			//If the choice is 1, the program will display the song at the head of the list while removing it from the list and adding it to a playedsongs list
			if(choice==1) {
			String name=playlist.first(playlist);
			playedSongs=playedSongs.insert(playedSongs, name);
			playlist=playlist.deleteFirst(playlist);
			System.out.println("Playing next song: "+name);	
			}
			else if(choice==2) {
			//If the choice is 2, the program will display the song last added to PlayedSongs, adds it back to the head of the playlist, and removes it from playedSongs
			String name=playedSongs.last(playedSongs);
			playlist=playlist.addAtStart(playlist, name);
			playedSongs=playedSongs.deleteLast(playedSongs);
			System.out.println("Playing previous song: "+name);	
			}
			else if(choice==3) {
			//Prints the current playlist
			System.out.println("Current queue: ");
			playlist.printList(playlist);
			}
			else if(choice==4) {
			//Prints the current playedSongs list
			System.out.println("Previously played songs: ");
			playlist.printList(playedSongs);
			}
			else System.out.println("Invalid choice");
		}

	}

}
class LinkedList{
	Node head;
	static class Node
	{
		String data;
		Node next;
		Node(String d)
		{
			data=d;
			next=null;
		}
	}
	public static LinkedList insert(LinkedList list, String data) 
	{
		Node newNode= new Node(data);
		newNode.next=null;
		if (list.head==null)
			list.head=newNode;
		else
		{
			Node last=list.head;
			while (last.next!=null)
				last=last.next;
			last.next=newNode;
		}
		return list;
	}
	//addAtStart adds a node to the beginning of a list by creating a temporary node for the first item and making the new Node to be added point to it next. 
	public static LinkedList addAtStart(LinkedList list, String data)
	{
		Node newNode= new Node(data);
		if(list.head==null)
			list.head=newNode;
		else {
			Node temp=list.head;
			list.head=newNode;
			list.head.next=temp;
		}
		return list;
	}
	/*
	 * Method for printing the list, it visits every node in the list and prints the data
	 */
	public static void printList(LinkedList list)
	{
		Node currNode=list.head;
		while (currNode !=null) 
		{
			System.out.println(currNode.data+" ");
			currNode=currNode.next;			
		}
		System.out.println();
	}
	/*
	 * Method for checking whether a string is already found in the list. It uses the list and a string as input then
	 * it visits every node and returns true if it finds a match. 
	 */
	public static String first(LinkedList list) {
		Node currNode=list.head;
		return currNode.data;
	}
	public static String last(LinkedList list) {
		Node currNode=list.head;
		while (currNode.next !=null)
			currNode=currNode.next;
		return currNode.data;
	}
	public static LinkedList deleteFirst(LinkedList list)
	{
		Node currNode=list.head;
		if(currNode!=null)
		{
			list.head=currNode.next;
			return list;
		}
		else
		{
			System.out.println("List is empty");
			return list;
		}
	}
	public static LinkedList deleteLast(LinkedList list) {
		Node currNode=list.head;
		if(currNode==null)
			return list;
		else
		{
			while(currNode.next.next!=null)
				currNode=currNode.next;
		}
		currNode.next=null;
		return list;
	}
	public static boolean foundInList(LinkedList list, String name) {
		boolean isFound=false;
		Node currNode=list.head;
		while (currNode!=null)
		{
			if(name.equals(currNode.data))
				{isFound=true;
				break;}
			else
				currNode=currNode.next;
		}
		return isFound;
	}
	/*
	 * Method for getting the size of a linked list. It takes a list as in input and visits every node, increasing a counter
	 * by one until it reaches the end of the list. It then returns the counter's value as an int.
	 */
	public static int getSize(LinkedList list) {
		Node currNode=list.head;
		int counter=0;
		if(currNode==null)
				return 0;
		else {
			while(currNode!=null) {
				counter++;
//				System.out.println(currNode.data+counter);
				currNode=currNode.next;
			}
		}
		return counter;
	}
	/*
	 * This method sorts all the contents in the list by using a simple bubble sort on the first letter of each name
	 * If it finds that a swap must be made, a temp string variable is used and the method swaps the data in the two nodes
	 */
	public static void sort(LinkedList list, int size) {
		if (size>1) {
			for (int i=0;i<size;i++) {
				Node currNode=list.head;
				Node nextNode=list.head.next;
				for(int j=0;j<size;j++) {
					if(nextNode.next!=null) {
					if(currNode.data.charAt(0)>nextNode.data.charAt(0)) {
						String temp=currNode.data;
						currNode.data=nextNode.data;
						nextNode.data=temp;
					}
					currNode=nextNode;
					nextNode=nextNode.next;}
				}
			}
		}
	}
}
import java.lang.Math;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;


class Node
{
	public boolean visited = false;
	public int [] neighbors = new int[0];
	public int node_num;
	public int degree;
	boolean taken = false;
}

class Project4
{
public static HashSet<Integer> independent_set = new HashSet<Integer>();;

public static void main(String []args)
{
	int num1;
	int num2;
	int first_node = 0;
	int counter = 0;
	Node[] nodes = new Node[100001];
    try
    {
		
//Read in file name and load data from file into an array of Node classes		
		
Scanner scanner = new Scanner(System.in);
System.out.println("Please enter a file name with verticies listed in tab delimited form: ");
String file_str = scanner.nextLine();
File file = new File(file_str);
Scanner reader = new Scanner(file);
String line;
   while (reader.hasNextLine()) {
	   line = reader.nextLine();
	   if (line.contains("#") == false) {
		   List<Integer> single = new ArrayList<Integer>();
		   String[] nums = line.split("\t");
		   num1 = Integer.parseInt(nums[0]);
		   num2 = Integer.parseInt(nums[1]);
		   if (counter == 0)
		   {
			   first_node = num1;
		   }
		   if (nodes[num1] != null)
		   {
			   int [] temp_array = Arrays.copyOf(nodes[num1].neighbors, nodes[num1].neighbors.length);
			   nodes[num1].neighbors = Arrays.copyOf(temp_array, temp_array.length + 1);
			   //Redeclares array with more memory each time node is loaded to conserve memory
		   }
		   else {
			   nodes[num1] = new Node();
			   nodes[num1].node_num = num1;
			   nodes[num1].neighbors = new int[1];
		   }
		   nodes[num1].neighbors[nodes[num1].neighbors.length - 1] = num2;
		   //Prints to show program progress in loading data
		   System.out.println(num1 + " : " + Arrays.toString(nodes[num1].neighbors));
		   counter++;
			}
            }
    reader.close();
    }
    catch (FileNotFoundException e) {

}
	
	
//Begin Algorithm to find articulation points
	
		double begin = System.nanoTime();
		nodes[first_node].visited = true;
	    int k = 0;
	int j = 0;
        Stack<Node> alist = new Stack<Node>();
	    Node node = nodes[first_node];
	    mis(node, nodes);
	System.out.println("Independent Set: " + Arrays.toString(independent_set.toArray()));
	double time_taken = System.nanoTime()-begin;
	System.out.println(time_taken);
}
	
	public static void check_degree(Node node, Node[] nodes)
	{
		for (int m=0; m < node.neighbors.length; m++)
		{
			Node neighbor = nodes[node.neighbors[m]];
			if (neighbor.visited == false)
			{
				node.degree = node.degree + 1;
			}
		}
	}
	
	public static void mis(Node node, Node[] nodes)
	{
		check_degree(node, nodes);
		if (node.degree == 0)
		{
			independent_set.add(node.node_num); //always take the node to the independent set
			node.taken = true;
			return;
		}
		else{
		if (node.degree == 1)
		{ 
			independent_set.add(node.node_num); //take the node, ignore the neighbors, and then run mis on the grandchildren
			node.taken = true;
			for (int i=0; i < node.neighbors.length; i++)
			{
			    Node neighbor = nodes[node.neighbors[i]];
				check_degree(neighbor, nodes);
				if (neighbor.visited == false)
				{
					check_degree(neighbor, nodes);
					if (neighbor.degree > 0)
					{	
						neighbor.visited = true;
						for (int h=0; h < neighbor.neighbors.length; h++)
						{
						if (nodes[neighbor.neighbors[h]].visited == false)
						{
							nodes[neighbor.neighbors[h]].visited = true;
							mis(nodes[neighbor.neighbors[h]], nodes);
						}
						}
					}
					}
				}
			}
			else
			{
					for (int m=0; m < node.neighbors.length; m++)
					{
					Node neighbor = nodes[node.neighbors[m]]; //run mis on the neighbors and only take the node if no neighbors are taken
					if (neighbor.visited == false)
					{
				neighbor.visited = true;
				mis(neighbor, nodes);
					}
				}
				boolean taken = false;
				for (int m=0; m < node.neighbors.length; m++)
				{
					Node neighbor = nodes[node.neighbors[m]];
					if (neighbor.taken == true)
					{
						taken = true;
					}
				}
				if (taken == false)
				{
					independent_set.add(node.node_num);
				}
			}
		}
	}

}

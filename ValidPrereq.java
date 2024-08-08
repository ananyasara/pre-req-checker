package prereqchecker;
import java.util.*;
/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    public static void main(String[] args) {

        if ( args.length < 3 ) 
        {
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
            return;
        }
	// WRITE YOUR CODE HERE
    ArrayList<ArrayList<String>> adjList = new ArrayList<ArrayList<String>>();

    StdIn.setFile(args[0]);
    String [] name = StdIn.readAllStrings();
    int a = Integer.parseInt(name[0]);
    int b = Integer.parseInt(name[a+1]);

    for(int i = 1; i < a + 1; i++){
        ArrayList<String> adj = new ArrayList<String>();
        adj.add(name[i]);
        adjList.add(adj);
    }

    for(int i = a + 2; i < name.length; i+=2)
    {
        for(ArrayList<String> list : adjList)
        {
            if(name[i].equals(list.get(0)))
            {
                list.add(name[i+1]);
            }
        }
    }
    StdIn.setFile(args[1]);
    String bing = StdIn.readString();
    String bong = StdIn.readString();
    boolean x = false;
    boolean y = false;
    boolean z = false;
    for(ArrayList<String> li : adjList)
    {
        if(li.get(0).contains(bing))
        {
            x = true;
        }
        if(li.get(0).contains(bong))
        {
            y = true;
        }
    }
    if(x && y)
    {
        z = true;
    }
    z = valid(bong, bing, adjList, z);
    StdOut.setFile(args[2]);
    if(z){
        StdOut.println("YES");
    }
    else{
        StdOut.println("NO");
    }
}

    private static boolean valid(String a, String b, ArrayList<ArrayList<String>> adjList, boolean ret){
        Stack <String> s = new Stack<String>();
        s.push(a);
        while(!s.isEmpty()){
        for(ArrayList<String> li : adjList)
        {
          if(li.get(0).equals(s.peek()))
          {
               s.pop();
               if(li.contains(b))
               {
                  ret = false;
                  return ret;
                }
            else{
                for(int i = 1; i<li.size(); i++)
                {
                    s.push(li.get(i));
                }
            }
            break;
            }
         }
        }
        return ret;
    }
}
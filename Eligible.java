package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    public static void main(String[] args) {

        if ( args.length < 3 ) 
        {
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
            return;
        }


    ArrayList<ArrayList<String>> AdjList = new ArrayList<ArrayList<String>>();
    StdIn.setFile(args[0]);
    String [] name = StdIn.readAllStrings();
    int a = Integer.parseInt(name[0]);
    int b = Integer.parseInt(name[a+1]);
   
    for(int i = 1; i < a + 1; i++)
    {
        ArrayList<String> course = new ArrayList<String>();
        course.add(name[i]);
        AdjList.add(course);
    }
    for(int i = a + 2; i < name.length; i+=2)
    {
        for(ArrayList<String> j : AdjList)
        {
            if(name[i].equals(j.get(0)))
            {
                j.add(name[i+1]);
            }
        }
    }

    StdIn.setFile(args[1]);
    int c = StdIn.readInt();
    String [] nme1 = StdIn.readAllStrings();
    ArrayList<String> taken = new ArrayList<String>();
    for(String course1 : nme1)
    {
        taken.add(course1);
    }
    for(int i = 0; i<AdjList.size(); i++)
    {
        if(taken.contains(AdjList.get(i).get(0)))
        {
            for(String course1 : AdjList.get(i))
            {
                if(!taken.contains(course1))
                {
                    taken.add(course1);
                    i=0;
                }
            }
        }
    }
    
    StdOut.setFile(args[2]);
    for(ArrayList<String> j : AdjList)
    {
        boolean result = true;
        if(taken.contains(j.get(0)))
        {
            result = false;
        }
        else{
            for(int i = 1; i < j.size(); i++)
            {
                if(!taken.contains(j.get(i)))
                {
                    result = false;
                }
            }
        }
        if(result)
        {
            StdOut.println(j.get(0));
        }
    }
}
}

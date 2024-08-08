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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        if ( args.length < 3 ) 
        {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }

        StdIn.setFile(args[0]);
        HashMap<String,ArrayList<String>> adjacency = new HashMap<String,ArrayList<String>>();
        HashMap<String, Boolean> marking1 = new HashMap<String, Boolean>();
        HashMap<String, Boolean> marking2 = new HashMap<String, Boolean>();
        HashMap<String, Boolean> temp1 = new HashMap<String, Boolean>();
        ArrayList<String> canTake = new ArrayList<String>();
        int vertices = StdIn.readInt();
        StdIn.readLine();
        
        int count = 0; 
        String finalPrerequisites = new String();
        ArrayList<String> allCourses = new ArrayList<String>();
        while (count < vertices){
            String key = StdIn.readLine();
            allCourses.add(key);
            canTake.add(key);
            ArrayList<String> connects = new ArrayList<String>();
            adjacency.put(key, connects);
            marking1.put(key, false);
            marking2.put(key, false);
            count++;
        }
       
        int edges = StdIn.readInt();
        StdIn.readLine();
        count = 0;
        int breakPoint = 0;
        while (count < edges)
        {
            String fullKey = StdIn.readLine();
            for (int i = 0; i < fullKey.length(); i++)
            {
                if (fullKey.charAt(i) == (' '))
                {
                    breakPoint = i;
                }
            }
            String[] temp2 = fullKey.split(" ");
            String key1 = temp2[0];
            String key2 = temp2[1]; 
            ArrayList<String> connections = adjacency.get(key1);
            connections.add(key2);
            adjacency.put(key1, connections);
            count++;
        }
       
        StdIn.setFile(args[1]);
        String targetCourse = StdIn.readLine();
        marking1 = markDFS(marking1, adjacency, targetCourse);
        ArrayList<String> coursesNeeded = new ArrayList<String>();
        ArrayList<String> coursesTaken = new ArrayList<String>();
        ArrayList<String> takenCourses = new ArrayList<String>();
        for (int i = 0; i < vertices; i++){
            if (marking1.get(allCourses.get(i))){
                coursesNeeded.add(allCourses.get(i));
            }
        }
        
        int courseNumbers = StdIn.readInt();
        count = 0;
        StdIn.readLine();
        ArrayList<String> taken = new ArrayList<String>();
        while (count < courseNumbers){
            String courseID = StdIn.readLine();
            taken.add(courseID);
            marking2 = markDFS(marking2, adjacency, courseID);
            count++;
        }
        for (int i = 0; i < vertices; i++)
        {
            if (marking2.get(allCourses.get(i)))
            {
                coursesTaken.add(allCourses.get(i));
            }
        }
        ArrayList<String> finalCourses = new ArrayList<String>();
        ArrayList<String> removableCourses = new ArrayList<String>();
        for (int i = 0; i < coursesNeeded.size(); i++){
            for (int n = 0; n < coursesTaken.size(); n++){
                if (coursesNeeded.get(i).equals(coursesTaken.get(n)))
                {
                    removableCourses.add(coursesNeeded.get(i));
                }
            }
        }
        for (int i = 0; i < removableCourses.size(); i++)
        {
            coursesNeeded.remove(removableCourses.get(i));
        }
        for (int i = 0; i < coursesNeeded.size(); i++)
        {
            if (coursesNeeded.get(i).equals(targetCourse))
            {
                coursesNeeded.remove(i);
            }
        }
        
        Boolean eligible = true;
        Boolean temporary = false;
        String finalString = new String();
        int tempCheck = 0;
        while (!coursesNeeded.isEmpty()){
            for (int i = 0; i < allCourses.size(); i++)
            {
                marking2.put(allCourses.get(i), false);
            }
            for (int i = 0; i < taken.size(); i++)
            {
                marking2 = markDFS(marking2, adjacency, taken.get(i));
            }
            for (int i = 0; i < vertices; i++)
            {
                String truecourses = allCourses.get(i);
                if(marking2.get(truecourses))
                {
                    takenCourses.add(truecourses);
                }
            }
            for (int i = 0; i < allCourses.size(); i++)
            {
                if (!(marking2.get(allCourses.get(i))))
                {
                    ArrayList<String> courses = adjacency.get(allCourses.get(i));
                    ArrayList<Boolean> courseboolean = new ArrayList<Boolean>();
                    for (int n = 0; n < courses.size(); n++)
                    {
                        for (int j = 0; j < takenCourses.size(); j++)
                        {
                            if (courses.get(n).equals(takenCourses.get(j)))
                            {
                                courseboolean.add(true);
                                temporary = true;
                                break;
                            }
                        }
                        if (temporary == false)
                        {
                            courseboolean.add(false);
                        }
                        temporary = false;
                    }
                    for (int x = 0; x < courseboolean.size(); x++)
                    {
                        if (courseboolean.get(x) == false)
                        {
                            eligible = false;
                        }
                    }
                    if (eligible == true)
                    {
                        finalCourses.add(allCourses.get(i));
                    }
                    eligible = true;
                }
            }
            for (int i = 0; i < finalCourses.size(); i++)
            {
                for (int j = 0; j < coursesNeeded.size(); j++)
                {
                    if (finalCourses.get(i).equals(coursesNeeded.get(j)))
                    {
                        finalString += coursesNeeded.get(j) + " ";
                        taken.add(finalCourses.get(i));
                        coursesNeeded.remove(finalCourses.get(i));
                    }
                }
            }
            finalString += "\n";
            tempCheck++;
        }
        StdOut.setFile(args[2]);
        String actualFinal = new String();
        actualFinal += tempCheck + "\n";
        actualFinal += finalString;
        StdOut.print(actualFinal);
	// WRITE YOUR CODE HERE
    }
    private static HashMap<String, Boolean> markDFS(HashMap<String, Boolean> mark, HashMap<String, ArrayList<String>> adjacent, String markcheck)
    {
        HashMap<String, Boolean> marking = mark;
        if (!(marking.get(markcheck)))
        {
            marking.put(markcheck, true);
        }
        HashMap<String, ArrayList<String>> adjacency = adjacent;
        ArrayList<String> temp = adjacency.get(markcheck);
        for (int i = 0; i < temp.size(); i++)
        {
            markDFS(marking, adjacent, temp.get(i));
        }
        return marking;
    }
}

package HW8;


/******************************************************************
 *
 *   ADD YOUR NAME / SECTION NUMBER HERE
 *   Yash Patel - 001
 *   This java file contains the problem solutions of canFinish and
 *   numGroups methods.
 *
 ********************************************************************/

import java.util.*;

class ProblemSolutions {

    /**
     * Method canFinish
     * <p>
     * You are building a course curriculum along with required intermediate
     * exams certifications that must be taken by programmers in order to obtain
     * a new certification called 'master programmer'. In doing so, you are placing
     * prerequisites on intermediate exam certifications that must be taken first.
     * You are allowing the flexibility of taking the exams in any order as long as
     * any exam prerequisites are satisfied.
     * <p>
     * Unfortunately, in the past, your predecessors have accidentally published
     * curriculums and exam schedules that were not possible to complete due to cycles
     * in prerequisites. You want to avoid this embarrassment by making sure you define
     * a curriculum and exam schedule that can be completed.
     * <p>
     * You goal is to ensure that any student pursuing the certificate of 'master
     * programmer', can complete 'n' certification exams, each being specific to a
     * topic. Some exams have prerequisites of needing to take and pass earlier
     * certificate exams. You do not want to force any order of taking the exams, but
     * you want to make sure that at least one order is possible.
     * <p>
     * This method will save your embarrassment by returning true or false if
     * there is at least one order that can taken of exams.
     * <p>
     * You wrote this method, and in doing so, you represent these 'n' exams as
     * nodes in a graph, numbered from 0 to n-1. And you represent the prerequisite
     * between taking exams as directed edges between two nodes which represent
     * those two exams.
     * <p>
     * Your method expects a 2-dimensional array of exam prerequisites, where
     * prerequisites[i] = [ai, bi] indicating that you must take exam 'bi' first
     * if you want to take exam 'ai'. For example, the pair [0, 1], indicates that
     * to take exam certification '0', you have to first have the certification for
     * exam '1'.
     * <p>
     * The method will return true if you can finish all certification exams.
     * Otherwise, return false (e.g., meaning it is a cyclic or cycle graph).
     * <p>
     * Example 1:
     * Input: numExams = 2, prerequisites = [[1,0]]
     * Output: true
     * Explanation: There are a total of 2 exams to take.
     * To take exam 1 you should have completed the
     * certification of exam 0. So, it is possible (no
     * cyclic or cycle graph of prereqs).
     * <p>
     * <p>
     * Example 2:
     * Input: numExams = 2, prerequisites = [[1,0],[0,1]]
     * Output: false
     * Explanation: There are a total of 2 exams to take.
     * To take exam 1 you should have completed the
     * certification of exam 0, and to take exams 0 you
     * should also have completed the certification of exam
     * 1. So, it is impossible (it is a cycle graph).
     *
     * @param numExams      - number of exams, which will produce a graph of n nodes
     * @param prerequisites - 2-dim array of directed edges.
     * @return boolean          - True if all exams can be taken, else false.
     */
    public boolean canFinish(int numExams, int[][] prerequisites) {
        // Build directed graph's adjacency list
        ArrayList<Integer>[] adj = getAdjList(numExams, prerequisites);

        // Array to track the in-degree of each node
        int[] inDegree = new int[numExams];
        for (int[] pair : prerequisites) {
            inDegree[pair[0]]++;
        }

        // Create a queue for nodes with no prerequisites
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numExams; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // Process the queue
        int finishedCourses = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            finishedCourses++;

            for (int neighbor : adj[course]) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Return true if all courses are finished
        return finishedCourses == numExams;
    }

    /**
     * Method getAdjList
     *
     * Building an Adjacency List for the directed graph based on number of nodes
     * and passed in directed edges.
     *
     * @param numNodes      - number of nodes in graph (labeled 0 through n-1) for n nodes
     * @param edges         - 2-dim array of directed edges
     * @return ArrayList<Integer>[]  - An adjacency list representing the provided graph.
     */
    private ArrayList<Integer>[] getAdjList(int numNodes, int[][] edges) {
        ArrayList<Integer>[] adj = new ArrayList[numNodes]; // Create an array of ArrayList ADT

        for (int node = 0; node < numNodes; node++) {
            adj[node] = new ArrayList<>(); // Allocate empty ArrayList per node
        }
        for (int[] edge : edges) {
            adj[edge[1]].add(edge[0]); // Add connected node edge [1] for node [0]
        }
        return adj;
    }

    /*
     * Assignment Graphing - Number of groups.
     *
     * There are n people. Some of them are connected
     * as friends forming a group. If person 'a' is
     * connected to person 'b', and person 'b' is
     * connected to person 'c', they form a connected
     * group.
     *
     * Not all groups are interconnected, meaning there
     * can be 1 or more groups depending on how people
     * are connected.
     *
     * This example can be viewed as a graph problem,
     * where people are represented as nodes, and
     * edges between them represent people being
     * connected. In this problem, we are representing
     * this graph externally as a non-directed
     * Adjacency Matrix. And the graph itself may not
     * be fully connected, it can have 1 or more
     * non-connected components (sub graphs).
     */
    public int numGroups(int[][] adjMatrix) {
        int numNodes = adjMatrix.length; // Total number of nodes
        boolean[] visited = new boolean[numNodes]; // Track visited nodes
        int groupCount = 0; // Count of connected components (groups)

        for (int i = 0; i < numNodes; i++) {
            if (!visited[i]) {
                groupCount++; // Found a new group

                exploreGroup(adjMatrix, visited, i); // Explore all nodes in this group
            }
        }


        return groupCount;
    }

    private void exploreGroup(int[][] adjMatrix, boolean[] visited, int node) {
        visited[node] = true; // Mark the current node as visited

        // Traverse all neighbors of the current node
        for (int neighbor = 0; neighbor < adjMatrix.length; neighbor++) {
            if (adjMatrix[node][neighbor] == 1 && !visited[neighbor]) {
                exploreGroup(adjMatrix, visited, neighbor); // Recursively visit the neighbor
            }
        }
    }
}

import java.util.Scanner; 
import java.io.*; 
import java.util.*; 
class Graph 
{ 
public int N; 
// Nodes in the graph public LinkedList<Integer> adj[]; //Adjacency Lists 
// Constructor
 public Graph(int n) { N = n; adj = new LinkedList[n]; for (int i=0; i<n; ++i) adj[i] = new LinkedList(); } public Graph(Graph g){ this.N=g.N; adj=new 
LinkedList[g.adj.length]; for(int i=0;i<g.adj.length;i++){ adj[i]=new LinkedList(); this.adj[i].addAll(g.adj[i]); } } // Function to add an edge into the graph void 
addEdge(int v,int w) { adj[v].add(w); } // Function to remove an edge from the graph 
void remove(int v, int w){ for(int i=0;i<adj[v].size();i++){ 
if(adj[v].get(i)==w){ adj[v].remove(i); } }
for(int i=0;i<adj[w].size();i++){ if(adj[w].get(i)==v){ adj[w].remove(i); } } } // using BFS traversal from a given source s to know UP/DOWN states 
boolean BFS(int s) 
{ // Mark all the vertices as not visited(By default set as false)
 boolean visited[] = new boolean[N]; // Create a queue for BFS LinkedList<Integer> queue = new 
LinkedList<Integer>(); // Mark the current node as visited and enqueue it 
visited[s]=true; queue.add(s); while (queue.size() != 0) { 
// Dequeue a vertex from queue print it 
s = queue.poll(); 
Iterator<Integer> i = adj[s].listIterator(); 
while (i.hasNext()) 
{ int n = i.next(); if (!visited[n]) { visited[n] = true; 
queue.add(n); } } } // Counting the visited nodes int countVisited=0; for(int i=0;i<N;i++){ if(visited[i]==true){ countVisited++; }
} // Verification and returning the decision if(countVisited==N){ return true; }else{ return false; } } } public class Project2 { public ArrayList<int[]> 
combinationList=new ArrayList<>(); // Combination Utility recursive function void combinationUtil(int arr[], int data[], int start, int end, int index, int r) { if 
(index == r) { int temp[]=new int[r]; for (int j=0; j<r; j++){ temp[j]=data[j]; } this.combinationList.add(temp); return; } for (int i=start; i<=end && end-i+1 >= 
r-index; i++) { data[index] = arr[i]; combinationUtil(arr, data, i+1, end, index+1, r); } } // Parent function that calls the combination utility function void 
printCombination(int arr[], int n, int r) { int data[]=new int[r]; combinationUtil(arr, data, 0, n-1, 0, r); } // Graph generator function with reliability 
calculations public double generateGraphs(double[] p,int E, int factor){
double totalReliability = 0; // Creating a base graph with 10 edges Graph g = new Graph(5); g.addEdge(0, 1); g.addEdge(0, 2); g.addEdge(0, 3); g.addEdge(0, 4); 
g.addEdge(1, 0); g.addEdge(1, 2); g.addEdge(1, 3); g.addEdge(1, 4); g.addEdge(2, 0); g.addEdge(2, 1); g.addEdge(2, 3); g.addEdge(2, 4); g.addEdge(3, 0); g.addEdge(3, 
2); g.addEdge(3, 1); g.addEdge(3, 4); g.addEdge(4, 0); g.addEdge(4, 2); g.addEdge(4, 3); g.addEdge(4, 1); //Temporary graph class object with same edges as the base 
graph Graph defGraph = new Graph(g); int arr[]={0,1,2,3,4,5,6,7,8,9}; int mediator[][]={{0,1},{0,2},{0,3},{0,4},{1,2},{1,3},{1,4},{2,3},{2,4},{3,4}}; for(int 
i=1;i<=E;i++) this.printCombination(arr, E, i); double reliable[]=new double[1024]; double state[]=new double[1024]; double test=1;
for(int i=0;i<p.length;i++){ test=test*p[i]; } for(int i=0;i<this.combinationList.size();i++){ double reliability=1; int arrEdge[]=this.combinationList.get(i); 
for(int j=0;j<arrEdge.length;j++){ g.remove(mediator[arrEdge[j]][0], mediator[arrEdge[j]][1]); } // Reliability calculation for each combination for(int 
ko=0;ko<10;ko++){ reliability=reliability*p[ko]; } for(int ko=0;ko<arrEdge.length;ko++){ reliability=(reliability/p[arrEdge[ko]])*(1-p[arrEdge[ko]]); } // BFS 
function called to find UP/DOWN based on that assigning the respective reliabilities and respective state if(g.BFS(0)){ reliable[i]=reliability; state[i]=1; }else{ 
state[i]=0; reliable[i]=reliability; } g=new Graph(defGraph); } double testReliable = 0; // Using random number generator for K values (K – factor variable) Random 
ran = new Random(); for(;factor!=0;factor--){ int x = ran.nextInt(1024); if(state[x]==0){ state[x]=1; }else{ state[x]=0; } }
// Calculating final reliability and returning the results for(int boo=0;boo<1024;boo++){ if(state[boo]==1){ testReliable=testReliable+reliable[boo]; } } return 
testReliable; } public static void main(String args[]){ System.out.println("Enter UTD ID: "); Scanner s=new Scanner(System.in); String d=s.nextLine(); double 
p[][]=new double[20][10]; // Calculating link probability or p value for(int i=0;i<20;i++){ for(int j=0;j<10;j++){ 
p[i][j]=Math.pow((0.05+(i*0.05)),(Math.ceil(Integer.parseInt(d.charAt(j)+"")/3))); } } int reliable[]=new int[1024]; // Calling generateGraph method to get Total 
reliability for [0.05,1] for(int i=0;i<=20;i++){ Project2 proj=new Project2(); System.out.println("For p= "+String.format("%.2f",(0.05*i))+" reliability 
="+proj.generateGraphs(p[19-i],10,0)); } // Calling generateGraph method to get Total reliability for p=0.9 for(int factor=0;factor<=20;factor++){ 
System.out.print("For p= 0.9 and k = "+factor+" reliability ="); double sum=0; for(int rot=0;rot<10;rot++) { Project2 proj=new Project2(); 
sum=sum+proj.generateGraphs(p[1],10,factor); } System.out.println(sum/10); }
} }

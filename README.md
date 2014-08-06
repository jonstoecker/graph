Graph (Scala)
====
A framework for building graph-based data models in Scala.

In this model, the graph itself only maintains a set of nodes. Each node maintains its own adjacency list, and
common searches and traversals are explicitly exposed to the user.

Public API
====

The following methods are exposed to the user:

Constructors
--
() :: default; initializes an empty graph

(HashSet) :: initializes a graph from an existing set of nodes. As nodes maintain their own adjacency lists, edges 
will be retained if present.

Accessors
--
edges -> int :: number of edges contained within the graph
vertices -> int :: number of vertices contained within the graph
degree -> int :: degree of the graph
maxDegree -> int :: largest degree of any vertex within the graph
avgDegree -> int :: average degree of all vertices within the graph
contains(data) ->  option(node) :: presence of given data value and associated reference  

Mutators
--
addNode(Node) :: adds a node to the graph
addEdge(From, To) :: creates an edge between the two given nodes
removeEdge(From, To) :: removes an edge between two given nodes

Traversals and algorithms
--
bfs(target_val) :: breadth-first search; randomized start
bfs(start, target_val) :: bfs to find a given data value from a given start point
dfs(target) :: depth-first search to find a given data value; randomized start
dfs(start, target_val) :: depth-first search to find a given data value from a given start point
iddfs(target_val) :: iterative deepening depth-first search
iddfs(start, target_val) :: iddfs from a given start point

Utility methods
--
toString :: pretty print string representation of the graph

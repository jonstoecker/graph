package stoecker

import scala.collection.mutable

/**
 * Created by jstoecker on 7/25/14.
 * @author jstoecker
 */
class Graph[E](nodesIn: mutable.HashSet[Node[E]]) {

  def this() = this(new mutable.HashSet[Node[E]])

  private val nodes: mutable.HashSet[Node[E]] = nodesIn
  private var numEdges: Int = sumEdges(nodes.toList)
  private val DEBUG: Boolean = true

  private def sumEdges(set: List[Node[E]]): Int = set.foldLeft(0)((sum, curr) => sum + curr.adjacent.size)

  /**
   * Adds the provided node to the graph
   * @param node node to add the graph
   */
  def addNode(node: Node[E]) {

    // TODO add proper case handling for nodes with pre-existing edges
    // 1.) Check for adjacent nodes
    // If true:
    // 2.) Traverse the new graph component and construct a set
    // 3.) Replace the existing node set with the union of the two sets
    // 4.) Recalculate the sum of edges
    if (node.adjacent.size == 0) {
      nodes.add(node)
    } else {
      // TODO
    }
  }

  /**
   * Adds a two-way edge to the graph between the two specified nodes
   * Currently undirected
   * @param n "from" node
   * @param m "to" node
   */
  def addEdge(n: Node[E], m: Node[E]) {
    // TODO replace assumed undirected graph with digraph functionality
    nodes.add(n)
    nodes.add(m)
    n.adjacent.append(m)
    m.adjacent.append(n)
    numEdges += 2
  }

  /**
   * Removes a two-way edge from the graph between the two specified nodes
   * Currently undirected
   * @param n "from" node
   * @param m "to" node
   */
  def removeEdge(n: Node[E], m: Node[E]) {
    // TODO replace assumed undirected graph with digraph functionality
    if (nodes.remove(n)) numEdges -= 1
    if (nodes.remove(m)) numEdges -= 1
  }

  /**
   * Number of edges contained within the graph
   * @return number of edges contained within the graph
   */
  // TODO replace assumed undirected graph with digraph functionality
  def edges = numEdges / 2

  /**
   * Number of vertices contained within the graph
   * @return number of vertices contained within the graph
   */
  def vertices = nodes.size

  /**
   * Returns the total degree of the graph
   * @return total degree of the graph
   */
  def degree = numEdges

  /**
   * Finds and returns the largest degree value of any single node contained within the graph
   * @return max degree found within the graph
   */
  def maxDegree = {
    if (nodes.isEmpty) 0
    nodes.reduceLeft((a, b) => if (a.adjacent.size > b.adjacent.size) a else b).adjacent.size
  }

  /**
   * Returns the average degree value of all nodes in the graph
   * @return average degree value of all nodes in the graph
   */
  def avgDegree = 2 * numEdges / nodes.size

  /**
   * Accessor for the set of all nodes in the graph
   * @return set of all nodes in the graph
   */
  def getNodes = nodes

  /**
   * Searches the graph for a specified data value; uses a depth-first algorithm by default
   * Running time: as DFS, O(|Edges|)
   * @param target data point to search for within the graph
   * @return reference to the node containing; null if non-existent
   */
  // TODO replace with iterative deepening depth-first search
  def find(target: E): Node[E] = dfs(nodes.head, target)

  /**
   * Searches the graph for a specified data value; depth-first algorithm
   * Running time: O(|Edges|)
   * @param n starting point (node/vertex) for the search
   * @param target data point to locate within the graph
   * @return reference to containing node; null if not found
   */
  def dfs(n: Node[E], target: E): Node[E] = {
    // Recursive step
    def dfs(n: Node[E], target: E, visited: mutable.HashSet[Node[E]]): Node[E] = {
      if (DEBUG) println("Visiting " + n.data + " at " + n.toString)
      visited.add(n)
      var result: Node[E] = null

      if (n.data.equals(target)) return n

      for (node <- n.adjacent if !visited.contains(node) && result == null) {
        result = dfs(node, target, visited)
      }
      result
    }

    dfs(n, target, new mutable.HashSet[Node[E]])
  }

  /**
   * Searches the graph for a specified data value; breadth-first algorithm
   * Running time: O(|Edges|)
   * @param n starting point (node/vertex) for the search
   * @param target data point to locate within the graph
   * @return reference to containing node; null if not found
   */
  def bfs(n: Node[E], target: E): Node[E] = {

    val queue: mutable.ListBuffer[Node[E]] = new mutable.ListBuffer[Node[E]]
    val visited: mutable.HashSet[Node[E]] = new mutable.HashSet[Node[E]]
    queue.append(n)
    visited.add(n)

    while (queue.nonEmpty) {

      val current: Node[E] = queue.remove(0)
      if (DEBUG) println("Found " + current.data + " while visiting " + current.toString)
      if (current.data.equals(target)) return current

      for (node <- current.adjacent if !visited.contains(node)) {
        visited.add(node)
        queue.append(node)
      }
    }
    null
  }

  /**
   * Generates a string representation of the graph
   * @return string representation of the graph
   */
  override def toString: String = {
    // TODO
    "todo"
  }
}

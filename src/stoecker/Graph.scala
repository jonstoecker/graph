package stoecker

import scala.collection.mutable
import java.util.PriorityQueue

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
   * @param from "from" node
   * @param to "to" node
   */
  def addEdge(from: Node[E], to: Node[E]) {
    // TODO replace assumed undirected graph with digraph functionality
    nodes.add(from)
    nodes.add(to)
    from.adjacent.append(to)
    to.adjacent.append(from)
    numEdges += 2
  }

  /**
   * Removes a single edge between the two specified nodes
   * Currently undirected
   * @param from "from" node
   * @param to "to" node
   */
  def removeEdge(from: Node[E], to: Node[E]) = {
    // TODO replace assumed undirected graph with digraph functionality
    def getEdge(from: Node[E], to: Node[E]) =
      from.adjacent.find((node: Node[E]) => node == to) match {
        case Some(node) => node
        case None => null
      }
    from.adjacent -= getEdge(from, to)
    to.adjacent -= getEdge(to, from)
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
   * Searches for a specified data value (currently iterative)
   * Running time: O(n)
   * @param target data point to search for within the graph
   * @return option containing the appropriate node(s)
   */
  def contains(target: E) = nodes.find((node: Node[E]) => node.data.equals(target))

  /**
   * Recursive step of the DFS/IDDFS
   * @param n current node
   * @param target value to search for
   * @param depth current depth
   * @param maxDepth maximum depth (for iterative deepening DFS)
   * @param visited hash of visited nodes for cycle detection
   * @param isIterativeDeepening flag for identifying IDDFS
   * @return reference to containing node; null if not found
   */
  private def dfs(n: Node[E], target: E, depth: Int, maxDepth: Int,
                  visited: mutable.HashSet[Node[E]], isIterativeDeepening: Boolean): Node[E] = {
    if (DEBUG) println("Visiting " + n.data + " at " + n.toString + " with depth " + depth + " and max depth " + maxDepth)
    visited.add(n)

    if (n.data.equals(target)) return n
    if (isIterativeDeepening && depth == maxDepth) return null

    var result: Node[E] = null
    for (node <- n.adjacent if result == null && !visited.contains(node)) {
      result = dfs(node, target, depth + 1, maxDepth, visited, isIterativeDeepening)
    }
    result
  }

  /**
   * Searches the graph for a specified data value; depth-first algorithm; random starting point
   * Running time: O(|Edges|)
   * @param target data point to locate within the graph
   * @return reference to containing node; null if not found
   */
  def dfs(target: E): Node[E] = dfs(nodes.head, target)

  /**
   * Searches the graph for a specified data value; depth-first algorithm; user-provided starting point
   * @param start starting point
   * @param target data point to locate within the graph
   * @return reference to containing node; null if not found
   */
  def dfs(start: Node[E], target: E): Node[E] = dfs(start, target, 0, 0,
    new mutable.HashSet[Node[E]], isIterativeDeepening = false)

  /**
   * Iterative deepening depth-first search
   * @param n starting node
   * @param target value to search for
   * @return reference to the containing node; null if not found
   */
  def iddfs(n: Node[E], target: E): Node[E] = {
    def deepeningSearch(maxDepth: Int): Node[E] = {
      val visited = new mutable.HashSet[Node[E]]
      val result = dfs(n, target, 0, maxDepth, visited, isIterativeDeepening = true)
      // TODO address discontinuous component case for terminating iterative deepening search
      // depth is arbitrarily limited to signed int maximum (i.e. 2^31 - 1)
      if (result == null && visited.size < nodes.size && maxDepth < Integer.MAX_VALUE) return deepeningSearch(maxDepth + 1)
      result
    }
    deepeningSearch(maxDepth = 0)
  }

  /**
   * Iterative deepening depth-first search; random start position
   * @param target value to search for
   * @return reference to containing node; null if not found
   */
  def iddfs(target: E): Node[E] = iddfs(nodes.head, target)

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
   * Searches the graph for a specified data value; breadth-first algorithm; random starting point
   * Running time: O(|Edges|)
   * @param target data point to locate within the graph
   * @return reference to containing node; null if not found
   */
  def bfs(target: E): Node[E] = bfs(nodes.head, target)

  /**
   * Finds the shortest path between two nodes
   * @param source starting point/source
   * @param target destination
   * @return list containing the shortest path between two nodes
   */
  def findPath(source: Node[E], target: Node[E]): List[Node[E]] = {
    val path: mutable.ListBuffer[Node[E]] = new mutable.ListBuffer[Node[E]]
    val previous: mutable.HashMap[Node[E], Node[E]] = new mutable.HashMap[Node[E], Node[E]]

    def dijkstra() = {
      val queue: java.util.PriorityQueue[Node[E]] = new java.util.PriorityQueue[Node[E]]()
      source.id = 0 // Initial distance from origin to origin is 0
      queue.add(source)

      while (!queue.isEmpty) {
        val current: Node[E] = queue.poll()

        for (adjacent <- current.adjacent) {
          val distance: Int = current.id + 1

          if (distance < adjacent.id) {
            queue.remove(adjacent)
            adjacent.id = distance
            previous.put(adjacent, current)
            queue.add(adjacent)
          }
        }
      }
    }

    // Initialize distance and previous node hashes
    for (node <- nodes) {
      node.id = Integer.MAX_VALUE
      previous.put(node, null)
    }

    // Run Dijkstra's algorithm to find the shortest path to each node in the set
    dijkstra()

    var node = previous.getOrElse(target, null)
    while(node != null) {
      path.prepend(node)
      node = previous.getOrElse(node, null)
    }
    path.toList
  }

  /**
   * Generates a string representation of the graph
   * @return string representation of the graph
   */
  override def toString: String = {
    val graphStr: mutable.StringBuilder = new mutable.StringBuilder()
    graphStr.append("Graph information\n--\nVertices: ")
      .append(nodes.size)
      .append("\nEdges: ")
      .append(edges / 2)
      .append("\nAdjacencies: \n")
    for (node <- nodes) {
      graphStr.append(node.data).append(": ")
      for (adj <- node.adjacent) {
        graphStr.append(adj.data).append(" ")
      }
      graphStr.append("\n")
    }
    graphStr.toString()
  }

  /**
   * Generates a string representation of the graph including each node's id value
   * @return string representation of the graph
   */
  def toStringWithIds: String = {
    val graphStr: mutable.StringBuilder = new mutable.StringBuilder()
    graphStr.append("Graph information\n--\nVertices: ")
      .append(nodes.size)
      .append("\nEdges: ")
      .append(edges / 2)
      .append("\nAdjacencies: \n")
    for (node <- nodes) {
      graphStr.append(node.data).append(" (").append(node.id).append("): ")
      for (adj <- node.adjacent) {
        graphStr.append(adj.data).append(" (").append(adj.id).append(") ")
      }
      graphStr.append("\n")
    }
    graphStr.toString()
  }

  /**
   * Generates a string representation of the graph including each node's feature value
   * @return string representation of the graph
   */
  def toStringWithFeatures: String = {
    val graphStr: mutable.StringBuilder = new mutable.StringBuilder()
    graphStr.append("Graph information\n--\nVertices: ")
      .append(nodes.size)
      .append("\nEdges: ")
      .append(edges / 2)
      .append("\nAdjacencies: \n")
    for (node <- nodes) {
      graphStr.append(node.data).append(" (").append(node.feature).append("): ")
      for (adj <- node.adjacent) {
        graphStr.append(adj.data).append(" (").append(adj.feature).append(") ")
      }
      graphStr.append("\n")
    }
    graphStr.toString()
  }
}

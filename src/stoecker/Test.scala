package stoecker

/**
 * Created by jstoecker on 7/25/14.
 * @author jstoecker
 */
object Test {

  // TODO clean up test cases/unit tests
  def main(args: Array[String]) {
//    testNode()
//    testAddNodeAndEdge()
//    testIDDFS()
//    testContains()
//    testRemoveEdge()
//    testToString()
    testFindPath()
  }

  def generateGraph(): Graph[String] = {
    val testGraph: Graph[String] = new Graph[String]
    val nodeA = new Node[String]("A")
    val nodeB = new Node[String]("B")
    val nodeC = new Node[String]("C")
    testGraph.addNode(nodeA)
    testGraph.addNode(nodeB)
    testGraph.addNode(nodeC)
    testGraph.addEdge(nodeA, nodeB)
    testGraph.addEdge(nodeA, nodeC)

    testGraph
  }

  /*
  Adding a node to an existing graph; testing "vertices" and "edges" accessors
   */
  def testAddNodeAndEdge() = {

    val testGraph: Graph[String] = new Graph[String]

    println("Testing AddNode\n--")
    println("Vertices: " + testGraph.vertices + " Edges: " + testGraph.edges)
    println("Adding A")
    val nodeA = new Node[String]("A")
    testGraph.addNode(nodeA)
    println("Vertices: " + testGraph.vertices + " Edges: " + testGraph.edges)
    println("Adding B")
    val nodeB = new Node[String]("B")
    testGraph.addNode(nodeB)
    println("Vertices: " + testGraph.vertices + " Edges: " + testGraph.edges)
    println("Adding C")
    val nodeC = new Node[String]("C")
    testGraph.addNode(nodeC)
    println("Vertices: " + testGraph.vertices + " Edges: " + testGraph.edges)

    println("Adding edge from A to B")
    testGraph.addEdge(nodeA, nodeB)
    println("Vertices: " + testGraph.vertices + " Edges: " + testGraph.edges)
    println("Adding edge from A to C")
    testGraph.addEdge(nodeA, nodeC)
    println("Vertices: " + testGraph.vertices + " Edges: " + testGraph.edges)
  }

  /*
  Test the Node class and associated variables/methods
   */
  def testNode() = {
    val testNode = new Node[String]("TestNode")

    def testNodeValues(id: Int, data: String, feature: String) = {
      println("Current (id): " + testNode.id)
      println("Current (data): " + testNode.data)
      println("Current (feature): " + testNode.feature)
      println("Expected (id): " + id)
      println("Expected (data): " + data)
      println("Expected (feature): " + feature)
      testNode.id = id
      testNode.data = data
      testNode.feature = feature
      println("Actual (id): " + testNode.id)
      println("Actual (data): " + testNode.data)
      println("Actual (feature): " + testNode.feature)
      if (testNode.id != id || testNode.data != data || testNode.feature != feature) println("Fail")
      else println("\nSuccess\n")
    }

    testNodeValues(-1, "TestNodeChange", "f1")
    testNodeValues(15, "TestNodeChange2", "f2")
  }

  /*
  Test edge removal
   */
  def testRemoveEdge() = {
    val testGraph = generateGraph()
    def testRemoveEdge(n: Node[String], m: Node[String]) = {
      println("Current")
      println(n.data + "(" + n + ")" + " adjacent:")
      for (node <- n.adjacent) println(node.data)
      println()
      println(m.data + "(" + m + ")" + " adjacent:")
      for (node <- n.adjacent) println(node.data)
      println("\n")

      println("After removal: ")
      testGraph.removeEdge(n, m)
      println(n.data + "(" + n + ")" + " adjacent:")
      for (node <- n.adjacent) println(node.data)
      println()
      println(m.data + "(" + m + ")" + " adjacent:")
      for (node <- n.adjacent) println(node.data)
      println("\n")
    }

    val nodeA: Node[String] = testGraph.contains("A").head
    val nodeB: Node[String] = testGraph.contains("B").head
    testRemoveEdge(nodeA, nodeB)
    testRemoveEdge(nodeA, nodeB)
    testRemoveEdge(nodeA, nodeB)
    testRemoveEdge(nodeA, nodeB)
  }

  /*
  Test depth-first search
   */
  def testBFS() = {
    // TODO
  }

  /*
  Test breadth-first search
   */
  def testDFS() = {
    // TODO
  }

  /*
  Test the iterative deepening depth-first search method
   */
  def testIDDFS() = {

    val testGraph = generateGraph()

    // Should return a reference to nodeC
    var testString: String = "C"
    println("Looking for " + testString)
    var result = testGraph.iddfs(testString)
    if (result != null)
      println("Found " + testString + " at " + result.toString)
    else
      println("Did not find " + testString)

    // Should return null
    testString = "D"
    result = testGraph.iddfs(testString)
    if (result != null)
      println("Found " + testString + " at " + result.toString)
    else
      println("Did not find " + testString)
  }

  def testContains() = {

    val testGraph = generateGraph()

    // Case 1: single known value; should be "some" + reference
    var testString = new String("C")
    println("Graph contains " + testString + " : " + testGraph.contains(testString))
    testString = "A"
    println("Graph contains " + testString + " : " + testGraph.contains(testString))

    // Case 2: non-existent value; Should be "none"
    testString = "D"
    println("Graph contains " + testString + " : " + testGraph.contains(testString))
    testString = "E"
    println("Graph contains " + testString + " : " + testGraph.contains(testString))

    // Case 3: Multiple values; should be "some" + first reference found
    val testNode = new Node[String]("D")
    val testNode2 = new Node[String]("D")
    testGraph.addNode(testNode)
    testString = "D"
    println("Graph contains " + testString + " : " + testGraph.contains(testString))
    testGraph.addNode(testNode2)
    println("Graph contains " + testString + " : " + testGraph.contains(testString))
  }

  def testFindPath() = {
    val testGraph = generateGraph()

    val nodeA: Node[String] = testGraph.contains("A").head
    val nodeB: Node[String] = testGraph.contains("B").head
    val nodeC: Node[String] = testGraph.contains("C").head

    for(node <- testGraph.findPath(nodeB, nodeC)) {
      print(node.data + " -> ")
    }
    println(nodeC.data)
  }

  def testToString() = {
    val testGraph = generateGraph()
    println(testGraph.toString)
    println(testGraph.toStringWithIds)
    println(testGraph.toStringWithFeatures)
  }

}

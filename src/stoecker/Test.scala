package stoecker

/**
 * Created by jstoecker on 7/25/14.
 * @author jstoecker
 */
object Test {

  val testGraph: Graph[String] = new Graph[String]

  // TODO clean up test cases/unit tests
  def main(args: Array[String]) {

    generateGraph()
    testIDDFS()
    testContains()
  }

  def generateGraph() = {
    val nodeA = new Node[String]("A")
    val nodeB = new Node[String]("B")
    val nodeC = new Node[String]("C")
    testGraph.addNode(nodeA)
    testGraph.addNode(nodeB)
    testGraph.addNode(nodeC)
    testGraph.addEdge(nodeA, nodeB)
    testGraph.addEdge(nodeA, nodeC)
  }

  /*
  Adding a node to an existing graph
   */
  def testAddNode() = {
    println("Edges : " + testGraph.edges)
    println("Vertices : " + testGraph.vertices)
    println("Edges : " + testGraph.edges)
    println("Vertices : " + testGraph.vertices)
  }

  /*
  Test the Node class and associated variables/methods
   */
  def testNode() = {
    val testNode = new Node[String]("TestNode")

    // Should show "-1" (default value for a node id)
    println(testNode.toString + " id : " + testNode.id)

    // Should show "TestNode"
    println(testNode.toString + " data: " + testNode.data)
  }

  /*
  Test depth-first search
   */
  def testBFS() = {

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

    // Should return a reference to nodeC
    val testString: String = "C"
    println("Looking for " + testString)
    var result = testGraph.iddfs(testString)
    if (result != null)
      println("Found " + testString + " at " + result.toString)
    else
      println("Did not find " + testString)

    // Should return a reference to node containing "C" and display progressively deeper search patterns as follows:
    //
    result = testGraph.iddfs(null, testString)
    if (result != null)
      println("Found " + testString + " at " + result.toString)
    else
      println("Did not find " + testString)
  }

  def testContains() = {

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
    println("Graph contains " + testString + " : " + testGraph.contains(testString))
    println("Graph contains " + testString + " : " + testGraph.contains(testString))
  }

}

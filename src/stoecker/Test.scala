package stoecker

/**
 * Created by jstoecker on 7/25/14.
 * @author jstoecker
 */
object Test {

  // TODO clean up test cases/unit tests
  def main(args: Array[String]) {
    println("Test")
    val testNode = new Node[String]("TestNode")
    val nodeA = new Node[String]("A")
    val nodeB = new Node[String]("B")
    val nodeC = new Node[String]("C")
    println(testNode.toString + " id : " + testNode.id)
    println(testNode.toString + " data: " + testNode.data)
    val testGraph = new Graph[String]
    println("Edges : " + testGraph.edges)
    println("Vertices : " + testGraph.vertices)
    testGraph.addNode(nodeA)
    println("Edges : " + testGraph.edges)
    println("Vertices : " + testGraph.vertices)
    testGraph.addNode(nodeB)
    testGraph.addNode(nodeC)
    println("Edges : " + testGraph.edges)
    println("Vertices : " + testGraph.vertices)
    testGraph.addEdge(nodeA, nodeB)
    testGraph.addEdge(nodeA, nodeC)
    println("Edges : " + testGraph.edges)
    val testString: String = "C"
    println("Max degree : " + testGraph.maxDegree)
    println("NodeC address : " + nodeC.toString)
    println("Looking for " + testString)
    val result: Node[String] = testGraph.iddfs(nodeB, testString)
    if (result != null)
      println("Found " + testString + " at " + result.toString)
    else
      println("Did not find " + testString)
    testContains(testGraph)
  }

  def testContains(testGraph: Graph[String]) = {

    var testString: String = "C"

    // Should be "some" + reference
    println("Graph contains " + testString + " : " + testGraph.contains(testString))
    testString = "A"
    println("Graph contains " + testString + " : " + testGraph.contains(testString))

    // Should be "none"
    testString = "D"
    println("Graph contains " + testString + " : " + testGraph.contains(testString))
  }

}

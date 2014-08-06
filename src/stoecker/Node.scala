package stoecker

import scala.collection.mutable.ListBuffer

/**
 * Created by jstoecker on 7/24/14.
 * @author jstoecker
 */
class Node[E] (dataIn: E, edgesIn: ListBuffer[Node[E]]) extends Ordered[Node[E]] {

  def this(dataIn: E) = this(dataIn, new ListBuffer[Node[E]])

  var adjacent: ListBuffer[Node[E]] = edgesIn
  var data: E = dataIn
  var id: Int = -1            // <volatile> secondary characteristic e.g. color, distance, etc.
  var feature: String = ""    // tertiary feature e.g. resource, color, name. Not volatile.

  def compare(that: Node[E]) = this.id - that.id
}

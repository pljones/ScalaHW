package scala.swing

import info.drealm.scala.{SeqSort,MergeSort}
import operator.Functional._

class SortedListView[T <% Ordered[T]] extends ListView[T] with MergeSort[T] {
    def this(items: Traversable[T]) = {
        this()
        listData = sort(items)
    }
    
    def this(comparator: (T, T) => Int)(items: Traversable[T]) {
        this()
        listData = items |> sortBy(comparator)
    }
}

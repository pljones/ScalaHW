package scala.swing

import info.drealm.scala.{SeqSort,MergeSort}
import operator.Functional._

class SortedListView[T <% Ordered[T]] extends ListView[T] with SeqSort[T] {
    def this(items: Traversable[T]) = {
        this()
        listData = items |> sort()
    }
    
    def this(orderBy: (T, T) => Int, items: Traversable[T]) {
        this()
        listData = items |> sortBy(orderBy)
    }
}
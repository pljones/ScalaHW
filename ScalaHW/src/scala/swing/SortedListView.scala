package scala.swing

import info.drealm.scala.SeqSort

class SortedListView[T <% Ordered[T]] extends ListView[T] with SeqSort[T] {
    def this(items: Traversable[T]) = {
        this()
        listData = doSort(items)
        //println(items + " => " + listData)
    }
}

package info.drealm.scala

import operator.Functional._

trait SeqSort[+T] {

    // The method has a constraint that items can be ordered and 
    // therefore have a compare function.
    def doSort[T <% Ordered[T]]()(xs: Traversable[T]): Seq[T] = {
        xs |> doSort((x, y) => x.compare(y))
    }

    // This is the unconstrained version where it is up to the
    // caller to explain how to sequence the incoming traversable.
    // The comparator function passed must, however, work like Ordered.compare.
    def doSort[T](comparator: (T, T) => Int)(xs: Traversable[T]): Seq[T] = {
        xs match {
            case Nil => Nil
            case head :: Nil => Seq(head)
            case pivot :: rest =>
                doSort(comparator)(rest filter (comparator(pivot,_) > 0)) ++:
                    Seq(pivot) ++:
                    doSort(comparator)(rest filterNot (comparator(pivot,_) > 0))
        }
    }
}

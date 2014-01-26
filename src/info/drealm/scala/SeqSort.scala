package info.drealm.scala

import operator.Functional._

trait SeqSort[+T] {

    // The method has a constraint that items can be ordered and 
    // therefore have a compare function.
    def sort[T <% Ordered[T]]()(xs: Traversable[T]): Seq[T] = xs |> sortBy(_.compare(_))

    // This is the unconstrained version where it is up to the
    // caller to explain how to sequence the incoming traversable.
    // The comparator function passed must, however, work like Ordered.compare.
    def sortBy[T](comparator: (T, T) => Int)(xs: Traversable[T]): Seq[T] = {
        xs match {
            case Nil => Nil
            case _ :: Nil => xs.toSeq
            case pivot :: rest =>
                val (lower, higher) = rest partition (comparator(pivot, _) > 0)
                (lower |> sortBy(comparator)) ++:
                    Seq(pivot) ++:
                    (higher |> sortBy(comparator))
        }
    }
}

trait MergeSort[+T] {

    // The method has a constraint that items can be ordered and 
    // therefore have a compare function.
    def sort[T <% Ordered[T]]()(xs: Traversable[T]): Seq[T] = xs |> sortBy(_.compare(_))

    // This is the unconstrained version where it is up to the
    // caller to explain how to sequence the incoming traversable.
    // The comparator function passed must, however, work like Ordered.compare.
    def sortBy[T](comparator: (T, T) => Int)(xs: Traversable[T]): Seq[T] = {

        def merge(xs: Seq[T], ys: Seq[T]): Seq[T] =
            (xs, ys) match {
                case (Nil, _) => ys
                case (_, Nil) => xs
                case (xh :: xt, yh :: yt) =>
                    if (comparator(xh, yh) < 0)
                        Seq(xh) ++: merge(xt, ys)
                    else
                        Seq(yh) ++: merge(xs, yt)
            }

        xs match {
            case Nil => Nil
            case _ :: Nil => xs.toSeq
            case _ =>
                val (left, right) = xs splitAt xs.toSeq.length / 2
                merge(sortBy(comparator)(left), sortBy(comparator)(right))
        }
    }
}

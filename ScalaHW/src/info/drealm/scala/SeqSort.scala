package info.drealm.scala

trait SeqSort[+T] {

    // This function is a member of a trait and, therefore, a method.
    // The method has a constraint that items can be ordered and are
    // supplied in a traversable form.  (Might only need traversableonce?)
    // It returns them sequentially by their ordering.
    def doSort[T <% Ordered[T]]()(xs: Traversable[T]): Seq[T] = {
        xs match {
            case Nil => Nil
            case head :: Nil => Seq(head)
            case pivot :: rest =>
                doSort()(rest filter (pivot >)) ++:
                    Seq(pivot) ++:
                    doSort()(rest filterNot (pivot >))
        }
    }
}

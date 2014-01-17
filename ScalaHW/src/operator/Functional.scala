/**
 * Taken from
 * http://stevegilham.blogspot.co.uk/2009/01/pipe-operator-in-scala.html
 */
package operator

object Functional {
    class PipedObject[+T] private[Functional] (value:T)
    {
        def |>[R] (f : T => R) = f(this.value)
    }
    implicit def toPiped[T] (value:T) = new PipedObject[T](value)
}
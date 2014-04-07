/**
 * Taken from
 * http://stevegilham.blogspot.co.uk/2009/01/pipe-operator-in-scala.html
 */
package operator

import scala.language.implicitConversions

object Functional {
    class PipedObject[+T] private[Functional] (value:T)
    {
        def |>[R] (f : T => R) = f(this.value)
    }
    implicit def toPiped[T] (value:T) = new PipedObject[T](value)
}
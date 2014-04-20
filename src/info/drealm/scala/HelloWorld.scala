package info.drealm.scala

import swing._
import swing.event._

object helloWorld extends SimpleSwingApplication {

    def top(): Frame = new MainFrame {

        case class GoodbyeButtonClicked(override val source: Button) extends ActionEvent(source)
        case class DefaultButtonChanged(override val source: Button) extends ComponentEvent()

        private[this] val migPanel = new MigPanel("", "[grow]", "[grow]") {

            private[this] def sortedListViewSelectionChanged[T](sortedListView: SortedListView[T]) = {
                // Fires on mouseDown and mouseUp..!
                for (x <- sortedListView.selection.items)
                    println(x)
            }

            private[this] val integerListView = new SortedListView(Seq(17, 32, 17, 3, 12, 15, 3)) { name = "Integers" }

            private[this] val wordListView = new SortedListView(Seq("Borrower", "Woodshed", "Horse", "Soldier", "Bookend", "Woodshed", "Tomato")) {
                name = "Words"
            }

            // Ugh, compile-order dependent code.  Must define goodbyeButton before
            // using its preferredSize in helloButton.  It beats defining an extra
            // pretend-goodbyeButton in helloButton, though.
            private[this] val goodbyeButton = new Button {
                text = "Goodbye"
                mnemonic = Key.E
            }

            private[this] val helloButton = new Button {
                text = "Hello"
                mnemonic = Key.E
                preferredSize = new Dimension(goodbyeButton.preferredSize.width, preferredSize.height)
            }

            def defaultButton: Button = {
                if (contents.contains(helloButton)) helloButton else goodbyeButton
            }

            contents += integerListView
            contents += wordListView
            contents += helloButton

            listenTo(integerListView.selection)
            listenTo(wordListView.selection)
            listenTo(helloButton)
            listenTo(goodbyeButton)
            reactions += {
                case SelectionChanged(`integerListView`) => sortedListViewSelectionChanged(integerListView)
                case SelectionChanged(`wordListView`)    => sortedListViewSelectionChanged(wordListView)
                case ButtonClicked(`helloButton`) => {
                    helloButton.visible = false
                    contents -= helloButton
                    contents += goodbyeButton
                    publish(new DefaultButtonChanged(defaultButton))
                }
                case ButtonClicked(`goodbyeButton`) => publish(new GoodbyeButtonClicked(goodbyeButton))
            }
        }

        title = "Hello, world!"
        minimumSize = new Dimension(228, 40)
        contents = migPanel
        defaultButton = migPanel.defaultButton
        centerOnScreen

        listenTo(migPanel)
        reactions += {
            case DefaultButtonChanged(x) => defaultButton = x
            case GoodbyeButtonClicked(_) => quit()
        }
    }
}

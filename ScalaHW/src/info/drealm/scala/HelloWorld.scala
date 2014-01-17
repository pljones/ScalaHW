package info.drealm.scala

import swing._
import swing.event._

object helloWorld extends SimpleSwingApplication {

    private[this] val integerListView = new SortedListView(List(17, 32, 17, 3, 12, 15, 3)) { name = "Integers" }

    private[this] val wordListView = new SortedListView(List("Borrower", "Woodshed", "Horse", "Soldier", "Bookend", "Woodshed", "Tomato")) {
        name = "Words"
    }

    private[this] val helloButton = new Button {
        text = "Hello"
        mnemonic = Key.E
        preferredSize = new Dimension((new Button("Goodbye")).preferredSize.width, preferredSize.height)
    }

    private[this] val goodbyeButton = new Button {
        text = "Goodbye"
        mnemonic = Key.E
    }

    private[this] val migPanel = new MigPanel("", "[grow]", "[grow]") {

        contents += integerListView
        contents += wordListView
        contents += helloButton

        listenTo(integerListView.selection)
        listenTo(wordListView.selection)
        listenTo(helloButton)
        reactions += {
            case SelectionChanged(`integerListView`) => sortedListViewSelectionChanged(integerListView)
            case SelectionChanged(`wordListView`) => sortedListViewSelectionChanged(wordListView)
            case ButtonClicked(`helloButton`) => {
                helloButton.visible = false
                contents -= helloButton
                contents += goodbyeButton
            }
        }
    }

    private[this] def sortedListViewSelectionChanged[T](sortedListView: SortedListView[T]) = {
        // Fires on mouseDown and mouseUp..!
        println("---" + sortedListView.name)
        for (x <- sortedListView.selection.items)
            println(x)
    }

    def top(): Frame = new MainFrame {

        title = "Hello, world!"
        minimumSize = new Dimension(228, 40)
        defaultButton = helloButton
        contents = migPanel
        centerOnScreen

        listenTo(migPanel)
        listenTo(goodbyeButton)
        reactions += {
            case ComponentAdded(`migPanel`, `goodbyeButton`) => defaultButton = goodbyeButton
            case ButtonClicked(`goodbyeButton`) => quit()
        }
    }
}

package scala.swing

import scala.swing.Container.Wrapper
import scala.swing.SequentialContainer.Wrapper

class MigPanel(layoutConstraints: String, colConstraints: String, rowConstraints: String)
    extends Panel
    with SequentialContainer.Wrapper {
    override lazy val peer = new javax.swing.JPanel with SuperMixin {
        setLayout(new net.miginfocom.swing.MigLayout(layoutConstraints, colConstraints, rowConstraints))
    }
}

module com.emmerichbrowne.texteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens com.emmerichbrowne.texteditor to javafx.fxml;
    exports com.emmerichbrowne.texteditor;
}
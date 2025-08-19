module com.emmerichbrowne.texteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens com.emmerichbrowne.texteditor to javafx.fxml;
    exports com.emmerichbrowne.texteditor;
}
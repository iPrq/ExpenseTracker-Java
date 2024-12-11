module main.expensemanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens main.expensemanager to javafx.fxml;
    exports main.expensemanager;
}
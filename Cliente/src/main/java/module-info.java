module org.example.c_s {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens org.example.c_s to javafx.fxml;
    exports org.example.c_s;
}
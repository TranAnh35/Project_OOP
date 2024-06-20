module demo {
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;

    requires jbcrypt;
    requires java.base;
    requires javax.servlet.api;

    opens demo to javafx.fxml;
    opens demo.Course to javafx.base;
    exports demo;
    exports demo.Course;
    exports demo.Entity;
}

module edu.fiuba.algo3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires jdk.jfr;

    requires java.desktop;
    requires javafx.graphics;
    requires java.smartcardio;
    requires junit;
    //requires edu.fiuba.algo3;

    exports edu.fiuba.algo3;
    exports edu.fiuba.algo3.vistas;

    opens edu.fiuba.algo3.controllers to javafx.fxml;

    exports edu.fiuba.algo3.controllers;
}
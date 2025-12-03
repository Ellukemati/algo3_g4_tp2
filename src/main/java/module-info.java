module edu.fiuba.algo3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    exports edu.fiuba.algo3;
    exports edu.fiuba.algo3.vistas;

    opens edu.fiuba.algo3.controllers to javafx.fxml;

    exports edu.fiuba.algo3.controllers;
}
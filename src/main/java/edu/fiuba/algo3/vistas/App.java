package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.SystemInfo;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        //var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");

        Button btn1 = new Button("boton1");
        Button btn2 = new Button("boton2");
        Button btn3 = new Button("boton3");
        Button btn4 = new Button("boton4");
        Button btn5 = new Button("boton5");

        VistaCartaDeRecursos recursos = new VistaCartaDeRecursos();
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        HBox hb1 = recursos.inicializarVistaCarta();
        HBox hb2 = new ContruccionesVista();
        hBox.getChildren().add(hb1);
        hBox.getChildren().add(hb2);


        BorderPane root = new BorderPane();

        root.setCenter(btn1);
        btn1.setMaxWidth(Double.MAX_VALUE);
        btn1.setMaxHeight(Double.MAX_VALUE);

        root.setTop(btn2);
        btn2.setMaxWidth(Double.MAX_VALUE);
        btn2.setMaxHeight(Double.MAX_VALUE);

        //root.setBottom(hb1);
        root.setBottom(hBox);


        btn3.setMaxWidth(Double.MAX_VALUE);
        btn3.setMaxHeight(Double.MAX_VALUE);

        root.setLeft(btn4);
        btn4.setMaxWidth(Double.MAX_VALUE);
        btn4.setMaxHeight(Double.MAX_VALUE);

        root.setRight(btn5);
        btn5.setMaxWidth(Double.MAX_VALUE);
        btn5.setMaxHeight(Double.MAX_VALUE);

        var scene = new Scene(root, 1040, 880);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

}
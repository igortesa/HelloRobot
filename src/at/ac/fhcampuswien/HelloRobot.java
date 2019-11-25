package at.ac.fhcampuswien;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloRobot extends Application {
        private static int count = 0;
        private double mainSceneX, mainSceneY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group(); // neutrales Root Elemenet
        Button button = new Button();
        button.setText("Revive");
        button.setLayoutX(5);
        button.setLayoutY(5);

        Label textoutput = new Label();
        textoutput.setText("");
        textoutput.setAlignment(Pos.CENTER);


        // drawing a robot
        Circle antennapointleft = new Circle(39, 30, 4, Color.DARKBLUE);
        Circle antennapointright = new Circle(63, 30, 4, Color.DARKBLUE);
        Line antennaleft = new Line(39, 40, 39, 30);
        Line antennaright = new Line(63, 41, 63, 30);
        Circle head = new Circle(50, 66, 28, Color.GRAY); // x,y (center of circle),radius,color
        Line mouth = new Line(43, 75, 61, 75);
        Circle righteye = new Circle(61, 51, 3, Color.BLACK);
        Circle lefteye = new Circle(41, 51, 3, Color.BLACK);
        Rectangle body = new Rectangle(22, 84, 56, 65);  // x,y (left top point),width,height
        Rectangle lefthand = new Rectangle(12, 84, 10, 32);
        Rectangle righthand = new Rectangle(78, 84, 10, 32);
        Rectangle leftfingers = new Rectangle(9, 116, 13, 5);
        Rectangle rightfingers = new Rectangle(78, 116, 13, 5);
        Circle leftfoot = new Circle(27, 150, 15, Color.DARKBLUE);
        Circle rightfoot = new Circle(73, 150, 15, Color.DARKBLUE);
        Circle point1 = new Circle(27, 150, 2, Color.RED);
        Circle point2 = new Circle(73, 150, 2, Color.RED);
        Circle center = new Circle(50, 116.5, 15, Color.DARKBLUE);
        Circle centralpoint = new Circle(50, 95, 2, Color.DARKBLUE);
        body.setFill(Color.DODGERBLUE);
        lefthand.setFill(Color.DARKBLUE);
        righthand.setFill(Color.DARKBLUE);
        leftfingers.setFill(Color.RED);
        rightfingers.setFill(Color.RED);
        Group robot = new Group(textoutput, antennapointleft, antennapointright, antennaleft, antennaright, head, lefteye, righteye, body, lefthand, righthand, leftfingers, rightfingers, leftfoot, rightfoot, point1, point2, center, mouth, centralpoint);
        robot.setLayoutX(201);
        robot.setLayoutY(127);
        // drawing a robot done

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // innere Klasse, anonyme Klasse
                if (robot.isVisible()) {
                    textoutput.setText("„!Dead yet!?");
                } else {
                    robot.setVisible(true);
                    textoutput.setText("Be nice this time!");
                    count = 0;
                }
            }
        });

        root.getChildren().addAll(robot, button);
        Scene first = new Scene(root, 500, 500);

        // move robot around
        first.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               if(mouseEvent.getButton() == MouseButton.SECONDARY){
                TranslateTransition tt = new TranslateTransition(Duration.millis(500), robot);
                System.out.println(robot.getLayoutX() + " "+" "+ robot.getLayoutY()+" " + mouseEvent.getSceneX()+" "+mouseEvent.getSceneY());
                double x = mouseEvent.getSceneX() - robot.getLayoutX() - 50;
                double y = mouseEvent.getSceneY() - robot.getLayoutY() - 95;
                tt.setToX(x);
                tt.setToY(y);
                tt.setAutoReverse(true);
                tt.play();
            }
            }
        });

        robot.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY && count < 1) {
                    textoutput.setText("Ouch!");
                    count += 1;
                } else if (event.getButton() == MouseButton.SECONDARY && count == 1) {
                    textoutput.setText("Please no!");
                    count += 1;
                } else if (event.getButton() == MouseButton.SECONDARY && count == 2) {
                    robot.setVisible(false);
                }
            }
        });

        robot.addEventHandler(MouseEvent.ANY, new
                EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getEventType() ==
                                MouseEvent.MOUSE_MOVED) {
                            robot.setCursor(Cursor.HAND);
                        }
                        if (event.getEventType() ==
                                MouseEvent.MOUSE_PRESSED && event.getButton() == MouseButton.PRIMARY) {
                            System.out.println("MOUSE_PRESSED");
                            mainSceneX = event.getSceneX();
                            mainSceneY = event.getSceneY();
                        }
                        if (event.getEventType() ==
                                MouseEvent.MOUSE_DRAGGED && event.getButton() == MouseButton.PRIMARY) {
                            System.out.println("MOUSE_DRAGGED");
                            robot.setCursor(Cursor.CLOSED_HAND);
                            double offsetX = event.getSceneX() - mainSceneX;
                            double offsetY = event.getSceneY() - mainSceneY;

                            Group r = (Group) event.getSource(); // returns an object on which the event initially occurred

                            r.setLayoutX(r.getLayoutX() + offsetX);
                            r.setLayoutY(r.getLayoutY() + offsetY);

                            mainSceneX = event.getSceneX();
                            mainSceneY = event.getSceneY();
                        }
                    }
                });

        primaryStage.setTitle("Hello Robot");
        primaryStage.setScene(first);
        primaryStage.setResizable(false);
        primaryStage.show();
}


            public static void main(String[] args) {
                launch(args);
            }
        }
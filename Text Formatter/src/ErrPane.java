//needed for panes
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
//needed for panes

/*
// uses code from an assignment about laptops which required a gui.
*/
//package CSE360TFS;

public class ErrPane extends HBox {
    private TextArea errorDetails;      //for user to read errors
    private Button clear;               //clears errors
    private GridPane gridPane;          //gridpane to hold errors and button
    //
    public ErrPane(){
        errorDetails = new TextArea();
        errorDetails.setWrapText(true);
        errorDetails.setEditable(false);
        errorDetails.setText("");
        // input and output buttons and text fields
        clear = new Button("Clear Error List");  //does what it says
        clear.setOnAction(new ButtonHandler());
        gridPane = new GridPane();
        GridPane.setConstraints(clear, 1, 1, 1, 1); //top left
        GridPane.setConstraints(errorDetails, 2, 1, 1, 50); //all right side
        gridPane.setHgap(10);   //gap between columns - semi-arbitrary
        gridPane.setVgap(10);
        //set up the pane/gui bit here
        gridPane.getChildren().addAll(clear, errorDetails);
        //adds the left and right side of the input pane information and text
        //fields to our inputpane proper.
        this.getChildren().add(gridPane); //set to our HBox
    }
    
    public void setErr(String inErr){
        errorDetails.setText(inErr);
    }
    
    private class ButtonHandler implements EventHandler<ActionEvent> {
        //Override the abstact method handle()

        @Override
        public void handle(ActionEvent e) {
            //handle our button
            if (e.getSource() == clear){
                errorDetails.setText("");
            }
        }
    }
    
}

package ro.uvt.chatapp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ChatAppModel.settingController=this;
		SpinnerValueFactory<Integer> valueFactory; 
        
		if(ChatAppModel.settingThePort){
			RRAnchor.setVisible(false);
			lblSetting.setText("Change Port");
			valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 65535, ChatAppModel.port);
			helper.setTooltip(new Tooltip("Between: 1 - 65535 (inclusive)"));
			
		}
		else{
			RRAnchor.setVisible(true);
			if(ChatAppModel.refreshRate==-1){
				RRcheckbox.setSelected(true);
				valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 10000, 10);	
			}else{
				valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 10000, ChatAppModel.refreshRate);
				RRcheckbox.setSelected(false);
			}
			lblSetting.setText("Change Refresh Rate");
			
			helper.setTooltip(new Tooltip("Between: 10 - 10000 tics\\sec\\min"));
		}
		theSpinner.setEditable(true);
		theSpinner.setValueFactory(valueFactory);
		
	}
	
	@FXML
    Label lblSetting;

	@FXML
	AnchorPane RRAnchor;
	
	@FXML
	CheckBox RRcheckbox;
	
	@FXML
	Spinner<Integer> theSpinner;
	
	@FXML
	Label helper;
	
	@FXML
	private void ConfirmHandler(Event e){	
		if(theSpinner!=null){
			try{
				int temp=theSpinner.getValue();
				if(ChatAppModel.settingThePort){
					ChatAppModel.port=temp;
					Stage window = (Stage) theSpinner.getScene().getWindow();
					window.close();		
				}
				else{
					Stage window = (Stage) theSpinner.getScene().getWindow();
					window.close();
					if(RRcheckbox.isSelected()){
						ChatAppModel.refreshRate=-1;
					}else{
						ChatAppModel.refreshRate=temp;
					}
				}
				
			}catch(NumberFormatException nre){
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Field uncompleted");
				alert.setHeaderText("Warning no number entered or really out of bound!");
				alert.showAndWait();
				return;
			}
		}
	}
	
}

package ro.uvt.chatapp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SettingController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ChatAppModel.settingController=this;
		if(ChatAppModel.settingThePort){
			lblSetting.setText("Change Port");
			txtSetting.setText(Integer.toString(ChatAppModel.port));
			txtSetting.setTooltip(new Tooltip("Between: 1 - 65535 (inclusive)"));
			
		}
		else{
			lblSetting.setText("Change Refresh Rate");
			txtSetting.setText(Integer.toString(ChatAppModel.refreshRate));
			txtSetting.setTooltip(new Tooltip("Between: 10 - "+Integer.MAX_VALUE+"tics\\sec\\min or -1 to disable"));
		}
	}
	
	@FXML
    Label lblSetting;
	
	@FXML
	TextField txtSetting;
	
	@FXML
	private void ConfirmHandler(Event e){	
		if(txtSetting!=null){
			try{
				int temp=Integer.parseInt(txtSetting.getText());
				if(ChatAppModel.settingThePort){
					if(temp>=1 && temp<=65535){
						ChatAppModel.port=temp;
						Stage window = (Stage) txtSetting.getScene().getWindow();
						window.close();
					}
					else{
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Field Out Of Bound");
						alert.setHeaderText("Between: 1 - 65535 (inclusive)");
						alert.showAndWait();
					}
				}
				else{
					if((temp>=10 && temp<=Integer.MAX_VALUE) || temp==-1){
						Stage window = (Stage) txtSetting.getScene().getWindow();
						window.close();
						ChatAppModel.refreshRate=temp;
					}
					
					else{
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Field Out Of Bound");
						alert.setHeaderText("Between: 10 - "+Integer.MAX_VALUE+" tics\\sec\\min or -1 to disable");
						alert.showAndWait();
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

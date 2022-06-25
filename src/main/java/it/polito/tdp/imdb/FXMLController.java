/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimili"
    private Button btnSimili; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulazione"
    private Button btnSimulazione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGenere"
    private ComboBox<String> boxGenere; // Value injected by FXMLLoader

    @FXML // fx:id="boxAttore"
    private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

    @FXML // fx:id="txtGiorni"
    private TextField txtGiorni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAttoriSimili(ActionEvent event) {
    	Actor actor=this.boxAttore.getValue();
    	if(actor==null) {
    		this.txtResult.setText("Selezionare l'attore e riprovare\n");
    		return;
    	}
    	this.txtResult.setText("Attori simili a "+actor+":\n");
    	for(Actor a:this.model.getSimile(actor)) {
    		if(!a.equals(actor))
    			this.txtResult.appendText(a+"\n");
    	}

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String genre=this.boxGenere.getValue();
    	if(genre==null) {
    		this.txtResult.setText("Selezionare il genere e riprovare\n");
    		return;
    	}
    	String msg=model.creaGrafo(genre);
    	this.txtResult.setText(msg);
    	this.boxAttore.getItems().clear();
    	this.boxAttore.getItems().addAll(this.model.getAllVertici());
    	
    	this.txtGiorni.setDisable(false);
    	this.btnSimulazione.setDisable(false);
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	Integer giorni=0;
    	String genre=this.boxGenere.getValue();
    	List<Actor> attori=new ArrayList();
    	try {
    		giorni=Integer.parseInt(this.txtGiorni.getText());
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    	}
    	
    	//String msg=this.model.simula(giorni, genre, attori);
    	//this.txtResult.setText(msg);
    	int i=0;
    	this.txtResult.appendText("\n");
    	for(Actor a:this.model.simula(giorni, genre, attori)) {
    		this.txtResult.appendText(a+"\n");
    		i++;
    	}
    	this.txtResult.appendText(i+"\n");
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxGenere.getItems().addAll(model.getAllGenre());
    	this.txtGiorni.setDisable(true);
    	this.btnSimulazione.setDisable(true);
    }
}

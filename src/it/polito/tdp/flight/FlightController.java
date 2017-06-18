package it.polito.tdp.flight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.AeroportoConPasseggeri;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		String dist = txtDistanzaInput.getText();
		try{
			int distanza = Integer.parseInt(dist);
			boolean conn = model.creaGrafo(distanza);
			if(conn == true){
				txtResult.appendText("Grafo creato,il grafo è connesso.\n");
			}else{
				txtResult.appendText("Grafo creato,il grafo non è connesso.\n");
			}
			Airport a = model.getPiuVicino();
			txtResult.appendText("Aeroporto più vicino a Los Angeles Intl: "+a+".\n");
		}catch (NumberFormatException e) {
			txtResult.appendText("Errore: inserire una distanza kilometrica.\n");
			return;
		}
	}

	@FXML
	void doSimula(ActionEvent event) {
		String pass = txtPasseggeriInput.getText();
		List<AeroportoConPasseggeri> ris = model.avviaSimulazione(Integer.parseInt(pass));
		for(AeroportoConPasseggeri a: ris){
			txtResult.appendText(a.getA()+"-"+a.getPers()+"\n");
		}
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}

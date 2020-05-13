
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<Country> cmbCountry;

    @FXML
    private Button btnTrovaVicini;
    int y;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	y = Integer.parseInt(txtAnno.getText());
    	if(y<1816 || y>2016) {
    		txtResult.appendText("Inserire un anno nell'intervallo 1816-2016.\n");
    		return;
    	}
    	txtResult.appendText(this.model.creaGrafo(y));
    	cmbCountry.getItems().addAll(this.model.getCountries());
    }
    
    @FXML
    void trovaVicini(ActionEvent event) {
    	txtResult.clear();
    	Country c = cmbCountry.getValue();
    	
    	/*
    	List<Country> vicini = this.model.getVicini(y, c, this.model.getIdMap());
    	List<Country> vicini = this.model.getVicini2(c);
    	*/
    	
    	//List<Country> vicini = this.model.getNodiRaggiungibili(c);
    	List<Country> vicini = this.model.getNodiRaggiungibiliIterativamente(c);
    	if(vicini==null) {
    		txtResult.appendText("La nazione selezionata non ha vicini.");
    	} else {
    		txtResult.appendText("Risultati trovati: "+vicini.size()+"\n");
    		String s="";
    		for(Country c2 : vicini) {
    			s += c2.getNome()+"\n";
    		}
    		txtResult.appendText(s);
    	}
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCountry != null : "fx:id=\"cmbCountry\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}

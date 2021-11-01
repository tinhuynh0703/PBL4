package application;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Controller.ConditionMail_BLL;
import Controller.SendMail_Controller;
import DAO.ConditionMail_DAO;
import DAO.Exchange_DAO;
import DAO.Hose_DAO;
import Model.ConditionMail;
import Model.GDTT_Hose;
import Model.Hose;
import Model.Selected_Condition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class SendMailController {
	@FXML private Button btnSendMail;
	@FXML private Button btnSaveEmail;
	@FXML private TextField txtEmail;
	@FXML private CheckBox cbPriceKL;
	@FXML private CheckBox cbPriceSell;
	@FXML private CheckBox cbPriceBuy;
	@FXML private CheckBox cbAmount;
	@FXML private CheckBox cbDTNN;
	@FXML private  ComboBox<String> cbbIdStock;
	@FXML private  ComboBox<String> cbbExchange;
	@FXML private TextField txtPriceKLStart;
	@FXML private TextField txtPriceKLEnd;
	@FXML private TextField txtPriceSellStart;
	@FXML private TextField txtPriceSellEnd;
	@FXML private TextField txtPriceBuyStart;
	@FXML private TextField txtPriceBuyEnd;
	@FXML private TextField txtAmountStart;
	@FXML private TextField txtAmountEnd;
	@FXML private TextField txtDtnnStart;
	@FXML private TextField txtDtnnEnd;
	@FXML private TableView<String> tableIdStock;
	@FXML private TableColumn<String, String> colIdStock;
	@FXML private ListView<Hose> listviewIdStock;
	//@FXML private TableColumn<String> colIdStock;
	
	public void initialize() {
		cbbExchange.setItems(Exchange_DAO.GetExchangeName());
		cbbExchange.setPromptText(Exchange_DAO.GetExchangeName().get(0));
		cbbIdStock.setItems(Exchange_DAO.GetStockId("Hose"));
		cbbIdStock.setPromptText(Exchange_DAO.GetStockId("Hose").get(0));
		try {
			colIdStock.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue()));
			
			tableIdStock.setItems(ConditionMail_DAO.GetIdStock());
		}catch(Exception e) {
			
		}
    }
	public void changeCbbExchange() {
		try {
			int s = cbbExchange.getSelectionModel().getSelectedIndex();
			switch (s) {
				case 0:
					cbbIdStock.setItems(Exchange_DAO.GetStockId("Hose"));
					cbbIdStock.setPromptText(Exchange_DAO.GetStockId("Hose").get(0));
				break;
				case 1:
					cbbIdStock.setItems(Exchange_DAO.GetStockId("HNX"));
					cbbIdStock.setPromptText(Exchange_DAO.GetStockId("HNX").get(0));
				break;
				case 2:
					cbbIdStock.setItems(Exchange_DAO.GetStockId("UPCOM"));
					cbbIdStock.setPromptText(Exchange_DAO.GetStockId("UPCOM").get(0));
				break;
			}
			}
			catch(Exception e1) {
				e1.printStackTrace();
			}
	}
	public void SaveEmail()
	{	
		String email=txtEmail.getText();
		txtPriceKLStart.setText(email);
		DAO.Email_DAO.Insert(email);
		SendMail_Controller.sendmail(email, null);
	}
	public void SaveStock()
	{
		String priceKLStart,priceKLEnd,priceSellStart,priceSellEnd,priceBuyStart,priceBuyEnd,
		amountStart,amountEnd, dtnnStart,dtnnEnd;
		if(cbPriceKL.isSelected()) {
			priceKLStart=txtPriceKLStart.getText();
			priceKLEnd=txtPriceKLEnd.getText();
		}
		else {
			priceKLStart="0";
			priceKLEnd="0";
		}
		if(cbPriceSell.isSelected()) {
			priceSellStart=txtPriceSellStart.getText();
			priceSellEnd=txtPriceSellEnd.getText();
		}
		else {
			priceSellStart="0";
			priceSellEnd="0";
		}
		if(cbPriceBuy.isSelected()) {
			priceBuyStart=txtPriceBuyStart.getText();
			priceBuyEnd=txtPriceBuyEnd.getText();
		}
		else {
			priceBuyStart="0";
			priceBuyEnd="0";
		}
		if(cbAmount.isSelected()) {
			amountStart=txtAmountStart.getText();
			amountEnd=txtAmountEnd.getText();
		}
		else {
			amountStart="0";
			amountEnd="0";
		}
		if(cbDTNN.isSelected()) {
			dtnnStart=txtDtnnStart.getText();
			dtnnEnd=txtDtnnEnd.getText();
		}
		else {
			dtnnStart="0";
			dtnnEnd="0";
		}
		ConditionMail c=new ConditionMail
		(
			cbbExchange.getValue(),
			cbbIdStock.getValue(),
			Double.parseDouble(priceKLStart),
			Double.parseDouble(priceKLEnd),
			Double.parseDouble(priceSellStart),
			Double.parseDouble(priceSellEnd),
			Double.parseDouble(priceBuyStart),
			Double.parseDouble(priceBuyEnd),
			Integer.parseInt(amountStart),
			Integer.parseInt(amountEnd),
			Integer.parseInt(dtnnStart),
			Integer.parseInt(dtnnEnd)	
		);
		ConditionMail_BLL.InsertConditionMail(c);
		Handle.infoBox("Save Successfully!", null, "Warning!");
		 
	}
	public void sendMail()
	{
		String mck="";
		ObservableList<String> listM = ConditionMail_DAO.GetIdStock();
		for (String item : listM) {
			mck += ConditionMail_BLL.NotifyStock(item)+"\n";
		}
		SendMail_Controller.sendmail(txtEmail.getText(), mck);
		Handle.infoBox("Send Email!", null, "Notification");
	}
}

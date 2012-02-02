package org.gsoft.openserv.web.models;

import java.util.List;

public class StockListModel {
	private List<StockModel> stocks;
	private StockModel editingStock;
	
	public List<StockModel> getStocks() {
		return stocks;
	}

	public void setStocks(List<StockModel> stocks) {
		this.stocks = stocks;
	}

	public StockModel getEditingStock() {
		return editingStock;
	}

	public void setEditingStock(StockModel editingStock) {
		this.editingStock = editingStock;
	}
	
}

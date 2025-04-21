package Controller;

import model.BloodStock;
import dao.BloodStockDAO;
import exception.BloodStockException;

import java.util.List;

public class BloodStockController {

    private BloodStockDAO bloodStockDAO;

    public BloodStockController() {
        bloodStockDAO = new BloodStockDAO();
    }

    public void addBloodStock(BloodStock bloodStock) throws BloodStockException {
        bloodStockDAO.addBloodStock(bloodStock);
    }

    public void updateBloodStock(BloodStock bloodStock) throws BloodStockException {
        bloodStockDAO.updateBloodStock(bloodStock);
    }

    public void deleteBloodStock(String stockID) throws BloodStockException {
        bloodStockDAO.deleteBloodStock(stockID);
    }

    public BloodStock getBloodStockById(String stockID) throws BloodStockException {
        return bloodStockDAO.getBloodStockById(stockID);
    }

    public List<BloodStock> getAllBloodStocks() throws BloodStockException {
        return bloodStockDAO.getAllBloodStocks();
    }
}
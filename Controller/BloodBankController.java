package Controller;

import model.BloodBank;
import dao.BloodBankDAO;
import exception.BloodBankException;

import java.util.List;

public class BloodBankController {

    private BloodBankDAO bloodBankDAO;

    public BloodBankController() {
        bloodBankDAO = new BloodBankDAO();
    }

    public void addBloodBank(BloodBank bloodBank) throws BloodBankException {
        bloodBankDAO.addBloodBank(bloodBank);
    }

    public void updateBloodBank(BloodBank bloodBank) throws BloodBankException {
        bloodBankDAO.updateBloodBank(bloodBank);
    }

    public void deleteBloodBank(String bloodBankID) throws BloodBankException {
        bloodBankDAO.deleteBloodBank(bloodBankID);
    }

    public BloodBank getBloodBankById(String bloodBankID) throws BloodBankException {
        return bloodBankDAO.getBloodBankById(bloodBankID);
    }

    public List<BloodBank> getAllBloodBanks() throws BloodBankException {
        return bloodBankDAO.getAllBloodBanks();
    }
}
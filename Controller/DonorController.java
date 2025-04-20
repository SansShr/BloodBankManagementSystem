package Controller;

import model.Donor;
import dao.DonorDAO;
import exception.DonorException;

import java.util.List;

public class DonorController {

    private DonorDAO donorDAO;

    public DonorController() {
        donorDAO = new DonorDAO();
    }

    public void addDonor(Donor donor) throws DonorException {
        donorDAO.addDonor(donor);
    }

    public void updateDonor(Donor donor) throws DonorException {
        donorDAO.updateDonor(donor);
    }

    public void deleteDonor(String donorID) throws DonorException {
        donorDAO.deleteDonor(donorID);
    }

    public Donor getDonorById(String donorID) throws DonorException {
        return donorDAO.getDonorById(donorID);
    }

    public List<Donor> getAllDonors() throws DonorException {
        return donorDAO.getAllDonors();
    }
}
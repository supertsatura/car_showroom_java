package carshowroom.service;

import carshowroom.exception.BusinessException;
import carshowroom.model.Buyer;
import carshowroom.repository.BuyerRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


public class BuyerService {
    private final BuyerRepository repository;

    public BuyerService(BuyerRepository repository) {
        this.repository = repository;
    }

    public List<Buyer> getAllBuyers() {
        return repository.findAll();
    }

    public Optional<Buyer> getBuyerById(int id) {
        return repository.findById(id);
    }

    public List<Buyer> getBuyersByPreferredBrand(String brand) {
        return repository.findByPreferredBrand(brand);
    }

    public long getBuyersCount() {
        return repository.countAll();
    }

    public Buyer createBuyer(Buyer buyer) {
        List<Buyer> allBuyers = repository.findAll();
        boolean telExists = allBuyers.stream().anyMatch(b -> b.getBuyerTel().equals(buyer.getBuyerTel()));

        if (telExists) {
            throw new BusinessException("Пользователь с номером " + buyer.getBuyerTel() + " уже существует");
        }

        return repository.save(buyer);
    }

    public Buyer updateBuyer(Buyer buyer) {
        List<Buyer> allBuyers = repository.findAll();
        boolean telExists = allBuyers.stream().anyMatch(
                b -> b.getBuyerId() != buyer.getBuyerId() && b.getBuyerTel().equals(buyer.getBuyerTel()));

        if (telExists) {
            throw new BusinessException("Пользователь с номером " + buyer.getBuyerTel() + " уже существует");
        }

        return repository.update(buyer);
    }

    public void deleteBuyer(int id) {
        repository.deleteById(id);
    }
}
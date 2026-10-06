package carshowroom.service;

import carshowroom.exception.BusinessException;
import carshowroom.model.Buyer;
import carshowroom.repository.BuyerRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class BuyerService {
    private final BuyerRepository repository;
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{2}$");

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
        validatePhone(buyer.getBuyerTel());
        validateName(buyer.getBuyerSurname(), "Фамилия");
        validateName(buyer.getBuyerName(), "Имя");
        return repository.save(buyer);
    }

    public Buyer updateBuyer(Buyer buyer) {
        validatePhone(buyer.getBuyerTel());
        validateName(buyer.getBuyerSurname(), "Фамилия");
        validateName(buyer.getBuyerName(), "Имя");
        return repository.update(buyer);
    }

    public void deleteBuyer(int id) {
        repository.deleteById(id);
    }

    private void validatePhone(String tel) {
        if (tel == null || !PHONE_PATTERN.matcher(tel).matches())
            throw new BusinessException("Телефон должен быть в формате XX-XX-XX (например, 77-77-77)");
    }

    private void validateName(String name, String fieldName) {
        if (name == null || name.length() < 2 || name.length() > 50)
            throw new BusinessException(fieldName + " должна(-о) содержать от 2 до 50 символов");
    }
}
package carshowroom.model;

import carshowroom.model.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Buyer {
    private int buyerId;
    private String buyerSurname;
    private String buyerName;
    private String buyerPatronymic;
    private String buyerTel;
    private String buyerPreferredBrand;
    private String buyerPreferredModel;
    private CarBodyType buyerPreferredBodyType;
    private CarWheelDrive buyerPreferredWheelDrive;
    private CarGearbox buyerPreferredGearbox;
    private CarCondition buyerPreferredCondition;
    private CarCompletion buyerPreferredCompletion;
    private CarColor buyerPreferredColor;
    private LocalDate buyerCarReleaseDate;
    private Integer buyerMaxMileage;
    private BigDecimal buyerMaxPrice;

    public Buyer() {
    }

    public Buyer(int buyerId, String buyerSurname, String buyerName, String buyerPatronymic,
                 String buyerTel) {
        this.buyerId = buyerId;
        this.buyerSurname = buyerSurname;
        this.buyerName = buyerName;
        this.buyerPatronymic = buyerPatronymic;
        this.buyerTel = buyerTel;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerSurname() {
        return buyerSurname;
    }

    public void setBuyerSurname(String buyerSurname) {
        this.buyerSurname = buyerSurname;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPatronymic() {
        return buyerPatronymic;
    }

    public void setBuyerPatronymic(String buyerPatronymic) {
        this.buyerPatronymic = buyerPatronymic;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    public String getBuyerPreferredBrand() {
        return buyerPreferredBrand;
    }

    public void setBuyerPreferredBrand(String buyerPreferredBrand) {
        this.buyerPreferredBrand = buyerPreferredBrand;
    }

    public String getBuyerPreferredModel() {
        return buyerPreferredModel;
    }

    public void setBuyerPreferredModel(String buyerPreferredModel) {
        this.buyerPreferredModel = buyerPreferredModel;
    }

    public CarBodyType getBuyerPreferredBodyType() {
        return buyerPreferredBodyType;
    }

    public void setBuyerPreferredBodyType(CarBodyType buyerPreferredBodyType) {
        this.buyerPreferredBodyType = buyerPreferredBodyType;
    }

    public CarWheelDrive getBuyerPreferredWheelDrive() {
        return buyerPreferredWheelDrive;
    }

    public void setBuyerPreferredWheelDrive(CarWheelDrive buyerPreferredWheelDrive) {
        this.buyerPreferredWheelDrive = buyerPreferredWheelDrive;
    }

    public CarGearbox getBuyerPreferredGearbox() {
        return buyerPreferredGearbox;
    }

    public void setBuyerPreferredGearbox(CarGearbox buyerPreferredGearbox) {
        this.buyerPreferredGearbox = buyerPreferredGearbox;
    }

    public CarCondition getBuyerPreferredCondition() {
        return buyerPreferredCondition;
    }

    public void setBuyerPreferredCondition(CarCondition buyerPreferredCondition) {
        this.buyerPreferredCondition = buyerPreferredCondition;
    }

    public CarCompletion getBuyerPreferredCompletion() {
        return buyerPreferredCompletion;
    }

    public void setBuyerPreferredCompletion(CarCompletion buyerPreferredCompletion) {
        this.buyerPreferredCompletion = buyerPreferredCompletion;
    }

    public CarColor getBuyerPreferredColor() {
        return buyerPreferredColor;
    }

    public void setBuyerPreferredColor(CarColor buyerPreferredColor) {
        this.buyerPreferredColor = buyerPreferredColor;
    }

    public LocalDate getBuyerCarReleaseDate() {
        return buyerCarReleaseDate;
    }

    public void setBuyerCarReleaseDate(LocalDate buyerCarReleaseDate) {
        this.buyerCarReleaseDate = buyerCarReleaseDate;
    }

    public Integer getBuyerMaxMileage() {
        return buyerMaxMileage;
    }

    public void setBuyerMaxMileage(Integer buyerMaxMileage) {
        this.buyerMaxMileage = buyerMaxMileage;
    }

    public BigDecimal getBuyerMaxPrice() {
        return buyerMaxPrice;
    }

    public void setBuyerMaxPrice(BigDecimal buyerMaxPrice) {
        this.buyerMaxPrice = buyerMaxPrice;
    }

    @Override
    public String toString() {
        return buyerSurname + " " + buyerName +
                (buyerPatronymic != null ? " " + buyerPatronymic : "") + " | Тел: " + buyerTel;
    }
}
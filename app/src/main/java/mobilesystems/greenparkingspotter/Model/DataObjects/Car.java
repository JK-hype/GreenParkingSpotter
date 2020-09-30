package mobilesystems.greenparkingspotter.Model.DataObjects;

import mobilesystems.greenparkingspotter.Acquaintance.ICar;

public class Car implements ICar {
    private String brand;
    private String name;
    private String horsePower;

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getHorsePower() {
        return horsePower;
    }

    @Override
    public void setHorsePower(String horsePower) {
        this.horsePower = horsePower;
    }
}

package mobilesystems.gps.Model.Adapters;

import java.util.ArrayList;

import mobilesystems.gps.Acquaintance.ICar;
import mobilesystems.gps.Model.DataObjects.Car;

public class CarAdapter {
    public ArrayList<ICar> convertCars(int amount, String brand, String name, String horsePower) {
        ArrayList<ICar> cars = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            ICar car = new Car();
            car.setBrand(brand);
            car.setName(name);
            car.setHorsePower(horsePower);
            cars.add(car);
        }
        return cars;
    }
}
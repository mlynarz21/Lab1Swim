package pl.edu.pwr.lab1mlynarczyk.lab1;

/**
 * Created by mlyna on 15.03.2017.
 */

public interface ICountBMI {
    public boolean isMassValid(float mass);

    public boolean isHeightValid(float heigth);

    public float countBMI(float mass,float height);
}

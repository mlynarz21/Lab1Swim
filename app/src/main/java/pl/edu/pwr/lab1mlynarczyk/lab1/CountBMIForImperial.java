package pl.edu.pwr.lab1mlynarczyk.lab1;

/**
 * Created by mlyna on 23.03.2017.
 */

public class CountBMIForImperial implements ICountBMI {
    static final float MINIMAL_MASS=22.0f;
    static final float MAXIMAL_MASS=550.0f;
    static final float MINIMAL_HEIGHT=20f;
    static final float MAXIMAL_HEIGHT=98f;
    private float imperialScale = 703.0704f;
    private static final String EXCEPCION_MSG = "Something bad happened";

    @Override
    public boolean isMassValid(float mass) {
        return mass>=MINIMAL_MASS && mass<=MAXIMAL_MASS;
    }

    @Override
    public boolean isHeightValid(float height) {
        return height>=MINIMAL_HEIGHT&&height<=MAXIMAL_HEIGHT;
    }

    @Override
    public float countBMI(float mass, float height) throws IllegalArgumentException{
        if(isMassValid(mass)&&isHeightValid(height))
            return mass/(height*height)*imperialScale;
        else throw new IllegalArgumentException(EXCEPCION_MSG);
    }
}

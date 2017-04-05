package pl.edu.pwr.lab1mlynarczyk.lab1;

/**
 * Created by mlyna on 15.03.2017.
 */

public class CountBMIForKGM implements ICountBMI {
    static final float MINIMAL_MASS=10.0f;
    static final float MAXIMAL_MASS=250.0f;
    static final float MINIMAL_HEIGHT=0.5f;
    static final float MAXIMAL_HEIGHT=2.5f;
    static final String EXCEPCION_MSG="Something bad happened";

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
            return mass/(height*height);
        else throw new IllegalArgumentException(EXCEPCION_MSG);
    }
}

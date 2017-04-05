package pl.edu.pwr.lab1mlynarczyk.lab1;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mlyna on 15.03.2017.
 */

public class CountBMITest {
    @Test
    public void MassUnderZeroIsInvalid(){
        //given
        float testMass=-1.0f;
        //when
        ICountBMI test = new CountBMIForKGM();
        //then
        boolean actual=test.isMassValid(testMass);
        assertFalse(actual);
    }

    @Test
    public void HeightUnderHalfIsInvalid(){
        //given
        float testHeight=0.4f;
        //when
        ICountBMI test = new CountBMIForKGM();
        //then
        boolean actual=test.isMassValid(testHeight);
        assertFalse(actual);
    }

    @Test
    public void isBMIWorking(){
        //given
        float testBMI=22.22f;
        float testMass=50.0f;
        float testHeight=1.5f;
        //when
        ICountBMI test = new CountBMIForKGM();
        //then
        float actual=test.countBMI(testMass,testHeight);
        assertEquals(testBMI,actual,0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isBMINotWorking() throws IllegalArgumentException{
        //given
        float testMass=0.0f;
        float testHeight=0.3f;
        //when
        ICountBMI test = new CountBMIForKGM();
        //then
        float actual=test.countBMI(testMass,testHeight);
    }


}

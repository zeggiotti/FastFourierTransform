import com.tambapps.fft4j.FastFourier;
import com.tambapps.fft4j.FastFouriers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MainTest {

    private final int LENGTH = 1024;
    private double[] randomSignal;
    private FastFourierTransform myTransformer;

    private final double[] sine = {0, 1, 0, -1};
    private final double[] sineFReal = {0, 0, 0, 0};
    private final double[] sineFImg = {0, -2, 0, 2};

    private final double[] step = {1, 1, 1, 1, 0, 0, 0, 0};
    private final double[] stepFReal = {4, 1, 0, 1, 0, 1, 0, 1};
    private final double[] stepFImg = {0, -2.4142, 0, -0.4142, 0, 0.4142, 0, 2.4142};

    @Before
    public void setup() {
        randomSignal = new double[LENGTH];
        for(int i = 0; i < randomSignal.length; i++)
            randomSignal[i] = Math.random();
    }

    /**
     * Test sulla DFT di com.tambapps.fft4j
     */
    @Test
    public void fftTest() {
        myTransformer = new FastFourierTransform();
        FastFourier reliableTransformer = FastFouriers.BASIC;

        Complex[] myResult = myTransformer.FFT(randomSignal);
        double[] real = new double[myResult.length];
        double[] imag = new double[myResult.length];
        reliableTransformer.transform(randomSignal, new double[randomSignal.length], real, imag);

        double[] myReal = new double[LENGTH];
        double[] myImg = new double[LENGTH];
        for(int i = 0; i < myResult.length; i++){
            myReal[i] = myResult[i].real;
            myImg[i] = myResult[i].imaginary;
        }

        Assert.assertArrayEquals(real, myReal, 0.01d);
        Assert.assertArrayEquals(imag, myImg, 0.01d);
    }

    /**
     * Test di confronto con la DFT implementata
     */
    @Test
    public void dftAndFftTest() {
        myTransformer = new FastFourierTransform();

        Complex[] myResult = myTransformer.FFT(randomSignal);
        Complex[] dftResult = myTransformer.DFT(randomSignal);

        double[] myReal = new double[myResult.length];
        double[] myImg = new double[dftResult.length];
        for(int i = 0; i < myResult.length; i++){
            myReal[i] = myResult[i].real;
            myImg[i] = myResult[i].imaginary;
        }

        double[] dftReal = new double[dftResult.length];
        double[] dftImg = new double[dftResult.length];
        for(int i = 0; i < dftResult.length; i++){
            dftReal[i] = dftResult[i].real;
            dftImg[i] = dftResult[i].imaginary;
        }

        Assert.assertArrayEquals(dftReal, myReal, 0.01d);
        Assert.assertArrayEquals(dftImg, myImg, 0.01d);

    }

    /**
     * Test sulla trasformata del seno di periodo 1.
     */
    @Test
    public void sineTest() {
        myTransformer = new FastFourierTransform();
        Complex[] myResult = myTransformer.FFT(sine);

        double[] myReal = new double[sine.length];
        double[] myImg = new double[sine.length];
        for(int i = 0; i < myResult.length; i++){
            myReal[i] = myResult[i].real;
            myImg[i] = myResult[i].imaginary;
        }

        Assert.assertArrayEquals(sineFReal, myReal, 0.01d);
        Assert.assertArrayEquals(sineFImg, myImg, 0.01d);
    }

    /**
     * Test sulla trasformata del gradino di Heaviside ribaltato.
     */
    @Test
    public void stepTest() {
        myTransformer = new FastFourierTransform();
        Complex[] myResult = myTransformer.FFT(step);

        double[] myReal = new double[step.length];
        double[] myImg = new double[step.length];
        for(int i = 0; i < myResult.length; i++){
            myReal[i] = myResult[i].real;
            myImg[i] = myResult[i].imaginary;
        }

        Assert.assertArrayEquals(stepFReal, myReal, 0.01d);
        Assert.assertArrayEquals(stepFImg, myImg, 0.01d);
    }

    @Test
    public void powerOfTwoTest() {
        myTransformer = new FastFourierTransform();

        Assert.assertTrue(myTransformer.isPowerOfTwo(256));
        Assert.assertFalse(myTransformer.isPowerOfTwo(255));
    }

}

package dnn;

/**
 * Created by ZLO on 08.01.2017.
 */
public class Layer {
    String transferFunction; // la fonction de transition

    double [] neurons; // les valeures

    Layer(String _transferFunction, double [] _neurons){
        this.transferFunction = _transferFunction;
        this.neurons = _neurons;

    }

}

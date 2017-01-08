package dnn;

/**
 * Created by ZLO on 08.01.2017.
 */
public class Weights {
    double [][] weights;

    Weights(Layer layer1, Layer layer2){

        for (int i = 0; i < layer1.neurons.length; i++) {
            for (int j = 0; j < layer2.neurons.length; j++) {
                weights[i][j] = 0;
            }
        }

    }


}
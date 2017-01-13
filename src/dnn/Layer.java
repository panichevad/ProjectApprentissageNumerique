package dnn;

/**
 * Created by ZLO on 08.01.2017.
 */
public class Layer {
    //String transferFunction; // la fonction de transition
    int k; // nombre de parametres
    int n; // nb de neurones dans la couche

    double [] neurons; // les valeures de sortie
    double [][] weights;

    Layer(int _k, int _n){

        this.neurons = new double[n];
        this.weights = new double [k][n];

    }

    public double [] transition(double [] input){
        double [] res = new double[n];

        for (int i = 0; i < n; i++) {
            res[i] = Math.max(sum(input)[i],0);

        }
        return res;
    }

    public double [] sum(double [] input){
        double[] sum = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                sum[i] += weights[k][i]*input[i];
            }

        }
        return sum;
    }



}

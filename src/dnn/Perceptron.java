
package dnn;
import java.util.*;

/**
 * Created by ZLO on 13.01.2017.
 */
public class Perceptron {
    int nL; // nb de couches cachees
    int k; // nombre de parametres
    int n; // nb de neurones dans la couche

    Layer input; // 3 neurones
    Layer output; // sortie - 1 neurone
    List<Layer> hidden; //

    Perceptron(int _nL, int _n, int _k){

        this.input = new Layer(_k, _n);
        this.output = new Layer(_k, _n);
        for (int i = 0; i < _nL; i++) {
            hidden.add(new Layer(_k, _n));
        }
    }

}


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiPerceptron {
    private Perceptron[] weights;
    private int m;

    public MultiPerceptron(int m, int n) {  //constructor
        // this.perceptron = new double[m][n];
        this.m = m;
        // for (int i = 0; i < m; i++)
        //     for (int j = 0; j < n; j++) {
        //         this.perceptron[i][j] = 0.0;
        //     }
        weights = new Perceptron[m];

        for (int i = 0; i < m; i++) {
            weights[i] = new Perceptron(n);
        }
    }

    // Returns the number of classes m.
    public int numberOfClasses() {
        return weights.length;
    }

    // Returns the number of inputs n (length of the feature vector).
    public int numberOfInputs() {
        Perceptron temp = weights[0];
        int n = temp.numberOfInputs();
        return n;
    }

    // Returns the predicted label (between 0 and m-1) for the given input.
    public int predictMulti(double[] x) {
        Double[] xii = new Double[m];
        int i = 0;
        for (Perceptron r : weights) {
            double a = 0.0;
            for (int z = 0; z < numberOfInputs(); z++) {
                double b = r.perceptron[z] * x[z];
                a += b;
            }
            xii[i] = a;
            ++i;
        }
        List<Double> list = new ArrayList<Double>();
        for (int p = 0; p < xii.length; p++) {
            list.add(xii[p]);
        }
        Double max = Collections.max(list);
//        int index = 0;
//        for (int j = 1; j < m; j++) {
//            if (xii[j] > max) {
//                max = xii[j];
//                index = j;
//            }
//        }
        int index = -1;
        for (int q = 0; q < xii.length; q++) {
            if (max.equals(xii[q])) {
                index = q;
            }
        }
        return index;
    }

    // Trains this multi-perceptron on the labeled (between 0 and m-1) input.
    public void trainMulti(double[] x, int label) {
        int j = predictMulti(x);
//        for (Perceptron r : weights) {
//            if (i == j) {
//                r.train(x, 1);
//            }
//            r.train(x, -1);
//            i++;
//        }
        for (int w = 0; w < numberOfClasses(); w++) {
            if (w == label) {
                Perceptron n = weights[w];
                n.train(x, 1);
                weights[w] = n;
            } else {
                Perceptron n = weights[w];
                n.train(x, -1);
                weights[w] = n;
            }

        }

    }

    // Returns a string representation of this MultiPerceptron.
    public String toString() {
        String finale = "(";
        int i = 0;
        for (Perceptron n : weights) {
            finale += "(";
            int j = 0;
            for (Double x : n.perceptron) {
                int u = numberOfInputs();
                if (j == u - 1) {
                    finale += x + "";
                } else {
                    finale += x + ", ";
                }
                ++j;
            }
            int k = numberOfClasses() - 1;
            if (i == k) {
                finale += ")";
            } else {
                finale += "), ";
            }
            ++i;

        }
        finale += ")";
        return finale;


    }


    // Tests this class by directly calling all instance methods.
    public static void main(String[] args) {
        int m = 2;
        int n = 3;

        double[] training1 = {5.0, -4.0, 3.0};
        double[] training2 = {2.0, 3.0, -2.0};
        double[] training3 = {4.0, 3.0, 2.0};
        double[] training4 = {-6.0, -5.0, 7.0};

        MultiPerceptron perceptron = new MultiPerceptron(m, n);
        StdOut.println(perceptron);
        perceptron.trainMulti(training1, 1);
        StdOut.println(perceptron);
        perceptron.trainMulti(training2, 0);
        StdOut.println(perceptron);
        perceptron.trainMulti(training3, 1);
        StdOut.println(perceptron);
        perceptron.trainMulti(training4, 0);
        StdOut.println(perceptron);
    }
}

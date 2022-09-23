public class Perceptron {
    public Double[] perceptron;
    private int size;

    public Perceptron(int n) {  //constructor
        this.perceptron = new Double[n];
        this.size = n;
        for (int i = 0; i < n; i++) {
            this.perceptron[i] = 0.0;
        }
    }

    public int numberOfInputs() {   //return feature vector size
        return size;
    }

    public double weightedSum(double[] x) { //compute wi*xi + ...
        double n = 0.0;
        for (int i = 0; i < size; i++) {
            n += (this.perceptron[i] * x[i]);
        }
        return n;
    }

    public int predict(double[] x) {    //-1 for weightedsum<=0, +1 for weightedsum>0
        double weightedsum = weightedSum(x);
        int r;
        if (weightedsum <= 0.0) {
            r = -1;
        } else {
            r = 1;
        }
        return r;
    }

    public void train(double[] x, int label) {  //change feature vector double[] perceptron according to label and prediction

        int predict = predict(x);

        if (predict == label) {
            for (int i = 0; i < size; i++) {
                this.perceptron[i] = this.perceptron[i];
            }
        }
        if (predict == 1 && label == -1) {
            for (int i = 0; i < size; i++) {
                this.perceptron[i] = this.perceptron[i] - x[i];
            }

        }
        if (predict == -1 && label == 1) {
            for (int i = 0; i < size; i++) {
                this.perceptron[i] = this.perceptron[i] + x[i];
            }

        }
    }

    public String toString() {  //convert the vector to string
        String fin = "(";
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                fin += (this.perceptron[i] + "");
            } else {
                fin += (this.perceptron[i] + ", ");
            }
        }
        fin += ")";
        return fin;
    }

//    public Double[] getArray() {
//        return this.perceptron.clone();
//    }

    public static void main(String[] args) {
        double[] training1 = {5.0, -4.0, 3.0};  // yes
        double[] training2 = {2.0, 3.0, -2.0};  // no
        double[] training3 = {4.0, 3.0, 2.0};  // yes
        double[] training4 = {-6.0, -5.0, 7.0};  // no

        int n = 3;
        Perceptron perceptron = new Perceptron(n);
        StdOut.println(perceptron);
        perceptron.train(training1, +1);
        StdOut.println(perceptron);
        perceptron.train(training2, -1);
        StdOut.println(perceptron);
        perceptron.train(training3, +1);
        StdOut.println(perceptron);
        perceptron.train(training4, -1);
        StdOut.println(perceptron);
    }
}

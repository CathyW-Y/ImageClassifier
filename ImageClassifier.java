import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Item {
    private String path;
    private int label;
    private double[] data;

    public Item(String path, int label, double[] data) {
        this.path = path;
        this.label = label;
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

}

public class ImageClassifier {

    public static double[] extractFeatures(Picture picture) {
        int width = picture.width();
        int height = picture.height();
        double[] data = new double[784];
        int i = 0;
        // convert to grayscale

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color color = picture.get(col, row);
                data[i] = color.getRed();
                i++;
            }
        }
        return data;

    }

    // private static List<Item> loadData(String path) {
    //     List<Item> data = new ArrayList<Item>();
    //     In in = new In(path);
    //     int i = 0;
    //     while (in.hasNextLine()) {
    //         String line = in.readLine();
    //         if (i <= 1) {
    //             i++;
    //             continue;
    //         }
    //         else {
    //
    //             String[] content = line.split(" +");
    //
    //             String imgPath = content[0];
    //             int label = Integer.parseInt(content[1]);
    //             Picture picture = new Picture(imgPath);
    //             double[] feature = extractFeatures(picture);
    //             Item item = new Item(imgPath, label, feature);
    //             data.add(item);
    //             i++;
    //         }
    //
    //         //			System.out.println(i);
    //
    //
    //     }
    //     return data;
    //
    // }

    // private static double intensity(Color color) {
    //     int r = color.getRed();
    //     int g = color.getGreen();
    //     int b = color.getBlue();
    //     if (r == g && r == b) return r;   // to avoid floating-point issues
    //     return 0.299 * r + 0.587 * g + 0.114 * b;
    // }


    public static void main(String[] args) {
        String path1 = args[0];
        String path2 = args[1];
        In in = new In(path1);
        In in2 = new In(path2);


        MultiPerceptron m = new MultiPerceptron(10, 28 * 28);

        int i = 0;
        String line;

        while (in.hasNextLine()) {
            line = in.readLine();
            if (i <= 1) {
                i++;
                continue;
            }
            else {
                String[] content = line.split(" +");
                String imgpath = content[0];
                int label = Integer.parseInt(content[1]);
                Picture picture = new Picture(imgpath);
                double[] feature = extractFeatures(picture);
                Item item = new Item(imgpath, label, feature);
                m.trainMulti(feature, label);
                i++;
            }
        }

        int j = 0;
        String line2;
        List<Item> data2 = new ArrayList<Item>();

        while (in2.hasNextLine()) {
            line2 = in2.readLine();
            if (j <= 1) {
                j++;
                continue;
            }
            else {
                String[] content1 = line2.split(" +");
                String imgpath1 = content1[0];
                int label1 = Integer.parseInt(content1[1]);
                Picture picture1 = new Picture(imgpath1);
                double[] feature1 = extractFeatures(picture1);
                Item item = new Item(imgpath1, label1, feature1);
                data2.add(item);
                j++;
            }

        }

        int error = 0;
        List<String> errList = new ArrayList<String>();
        List<Integer> labell = new ArrayList<Integer>();
        List<Integer> predicts = new ArrayList<Integer>();

        for (int w = 0; w < data2.size(); w++) {
            Item item = data2.get(w);
            int predict = m.predictMulti(item.getData());

            if (predict != item.getLabel()) {
                error++;
                errList.add(item.getPath());
                labell.add(item.getLabel());
                predicts.add(predict);

            }
        }
        Iterator<String> it = errList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println("************************");
        Iterator<Integer> it2 = labell.iterator();
        String f1 = "";
        while (it2.hasNext()) {
            f1 += it2.next() + "";
        }
        System.out.println(f1);
        System.out.println("************************");
        Iterator<Integer> it3 = predicts.iterator();
        String f2 = "";
        while (it3.hasNext()) {
            f2 += it3.next() + "";
        }
        System.out.println(f2);

        double testerrorate = error * 1.0 / data2.size();


        System.out.println("test error rate = " + testerrorate);


    }


}


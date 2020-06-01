package com.test.tyrelocation.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.util.Base64Utils;
import org.tensorflow.*;
import org.tensorflow.types.UInt8;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date: 2020/2/16 15:12
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class TensorFlowDetection {

    private final static double SCORES = 0.8;

    private final static String MODELDIR = "E:\\pb";
    private static byte[] graphDef = null;
    static {
        graphDef = readAllBytesOrExit(Paths.get(MODELDIR, "frozen_inference_graph.pb"));
    }
    public static void main(String[] args) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String imageFile = "E:\\python\\tyre_detection\\self_dir\\images\\cat183.jpg";
        InputStream inputStream = new FileInputStream(imageFile);
        byte[] buff = new byte[inputStream.available()];
        inputStream.read(buff);
        String encode = Base64Utils.encodeToString(buff);
        Mat img = Imgcodecs.imread(imageFile);
        List<List<Float>> result = detection(encode);
        for (List<Float> data: result){
            double xMin = data.get(0);
            double yMin = data.get(1);
            double xMax = data.get(2);
            double yMax = data.get(3);
            Point point1 = new Point(xMin, yMin);
            Point point2 = new Point(xMax, yMax);
            Scalar color = new Scalar(0, 255, 0);
            Imgproc.rectangle(img, point1, point2, color, 2);
        }
        new ImageViewer(img).imshow();
    }

    public static List<List<Float>> detection(String data){
        byte[] arrayDate = Base64Utils.decodeFromString(data);
        Tensor<UInt8> image = constructAndExecuteGraphToNormalizeImage(arrayDate);
        List<List<Float>> result = executeInceptionGraph(graphDef, image);
        return result;
    }

    private static byte[] readAllBytesOrExit(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println("Failed to read [" + path + "]: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }
    private static Tensor<UInt8> constructAndExecuteGraphToNormalizeImage(byte[] imageBytes) {
        try (Graph g = new Graph()) {
            GraphBuilder b = new GraphBuilder(g);
            // Some constants specific to the pre-trained model at:
            // https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip
            //
            // - The model was trained with images scaled to 224x224 pixels.
            // - The colors, represented as R, G, B in 1-byte each were converted to
            //   float using (value - Mean)/Scale.
            final int H = 224;
            final int W = 224;
            final float mean = 0f;
            final float scale = 1f;

            // Since the graph is being constructed once per execution here, we can use a constant for the
            // input image. If the graph were to be re-used for multiple input images, a placeholder would
            // have been more appropriate.
            final Output<String> input = b.constant("input", imageBytes);
            final Output<UInt8> output =b.expandDims(
                    b.cast(b.decodeJpeg(input, 3), UInt8.class),
                    b.constant("make_batch", 0));
            try (Session s = new Session(g)) {
                // Generally, there may be multiple output tensors, all of them must be closed to prevent resource leaks.
                return s.runner().fetch(output.op().name()).run().get(0).expect(UInt8.class);
            }
        }
    }
    private static List<List<Float>> executeInceptionGraph(byte[] graphDef, Tensor<UInt8> image) {
        long wide = image.shape()[2];
        long height = image.shape()[1];
        try (Graph g = new Graph()) {
            g.importGraphDef(graphDef);
            try (Session s = new Session(g)) {
                List<Tensor<?>> result =
                        s.runner().feed("image_tensor:0", image)
                                .fetch("detection_boxes:0")
                                .fetch("detection_scores:0")
                                .fetch("detection_classes:0")
                                .fetch("num_detections:0")
                                .run();
                result.size();
                float[] array = new float[400];
                float[][] boxes = new float[100][4];
                float[] scores = new float[100];
                FloatBuffer floatBuffer = FloatBuffer.wrap(array);
                FloatBuffer scoresBuffer = FloatBuffer.wrap(scores);
                result.get(0).writeTo(floatBuffer);
                result.get(1).writeTo(scoresBuffer);
                for (int i=0; i<100; i++){
                    int index = i*4;
                    boxes[i][0] = array[index];
                    boxes[i][1] = array[index+1];
                    boxes[i][2] = array[index+2];
                    boxes[i][3] = array[index+3];
                }
                List<Integer> counts = new ArrayList<>();
                for (int i=0; i<scores.length; i++){
                    if (scores[i]>SCORES){
                        counts.add(i);
                    }
                }
                List<List<Float>> resultData = new ArrayList<>();
                for (Integer i : counts){
                    float yMin = boxes[i][0]*height;
                    float xMin = boxes[i][1]*wide;
                    float yMax = boxes[i][2]*height;
                    float xMax = boxes[i][3]*wide;
                    List<Float> data = Arrays.asList(xMin,yMin,xMax,yMax);
                    resultData.add(data);
                }
                return resultData;

            }
        }
    }
    static class GraphBuilder {

        private Graph g;

        GraphBuilder(Graph g) {
            this.g = g;
        }

        Output<Float> div(Output<Float> x, Output<Float> y) {
            return binaryOp("Div", x, y);
        }

        <T> Output<T> sub(Output<T> x, Output<T> y) {
            return binaryOp("Sub", x, y);
        }

        <T> Output<Float> resizeBilinear(Output<T> images, Output<Integer> size) {
            return binaryOp3("ResizeBilinear", images, size);
        }

        <T> Output<T> expandDims(Output<T> input, Output<Integer> dim) {
            return binaryOp3("ExpandDims", input, dim);
        }

        <T, U> Output<U> cast(Output<T> value, Class<U> type) {
            DataType dtype = DataType.fromClass(type);
            return g.opBuilder("Cast", "Cast")
                    .addInput(value)
                    .setAttr("DstT", dtype)
                    .build()
                    .<U>output(0);
        }

        Output<UInt8> decodeJpeg(Output<String> contents, long channels) {
            return g.opBuilder("DecodeJpeg", "DecodeJpeg")
                    .addInput(contents)
                    .setAttr("channels", channels)
                    .build()
                    .<UInt8>output(0);
        }

        <T> Output<T> constant(String name, Object value, Class<T> type) {
            try (Tensor<T> t = Tensor.<T>create(value, type)) {
                return g.opBuilder("Const", name)
                        .setAttr("dtype", DataType.fromClass(type))
                        .setAttr("value", t)
                        .build()
                        .<T>output(0);
            }
        }
        Output<String> constant(String name, byte[] value) {
            return this.constant(name, value, String.class);
        }

        Output<Integer> constant(String name, int value) {
            return this.constant(name, value, Integer.class);
        }

        Output<Integer> constant(String name, int[] value) {
            return this.constant(name, value, Integer.class);
        }

        Output<Float> constant(String name, float value) {
            return this.constant(name, value, Float.class);
        }

        private <T> Output<T> binaryOp(String type, Output<T> in1, Output<T> in2) {
            return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
        }

        private <T, U, V> Output<T> binaryOp3(String type, Output<U> in1, Output<V> in2) {
            return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
        }

    }
}

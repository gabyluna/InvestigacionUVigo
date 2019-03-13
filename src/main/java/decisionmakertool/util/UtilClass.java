/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.util;

/**
 *
 * @author Gaby
 */
public class UtilClass {

    public static double polygonArea(double X[], double Y[], int n) {
        // Initialze area 
        double area = 0.0;

        // Calculate value of shoelace formula 
        int j = n - 1;
        for (int i = 0; i < n; i++) {
            area += (X[j] + X[i]) * (Y[j] - Y[i]);

            // j is previous vertex to i 
            j = i;
        }

        // Return absolute value 
        return Math.abs(area / 2.0);
    }

    public static double getPolygonArea(Integer dimension[], int n) {
        double X[] = new double[n];
        double Y[] = new double[n];
        double angulo = 0;
        double intervalo = 0;
        intervalo = 360 / n;
        angulo = intervalo;

        //Coordenates X
        for (int i = 0; i < n; i++) {
            X[i] = dimension[i] * Math.cos(Math.toRadians(angulo));
            Y[i] = dimension[i] * Math.sin(Math.toRadians(angulo));
            Y[i] = format(Y[i]);
            angulo = angulo + intervalo;
        }
        double s = 0;

        return polygonArea(X, Y, n);
    }

    private static double format(double value) {
        return (double) Math.round(value * 1000000) / 1000000; //you can change this to round up the value(for two position use 100...)
    }

}

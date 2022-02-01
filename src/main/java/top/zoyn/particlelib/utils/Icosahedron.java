package top.zoyn.particlelib.utils;

import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Icosahedron {

    public static double X = 0.525731112119133606f;
    public static double Z = 0.850650808352039932f;

    public static double[][] vdata = {{-X, +0, +Z}, {+X, +0, +Z}, {-X, +0, -Z}, {+X, +0, -Z}, {+0, +Z, +X}, {+0, +Z, -X},
            {+0, -Z, +X}, {+0, -Z, -X}, {+Z, +X, +0}, {-Z, +X, +0}, {+Z, -X, +0}, {-Z, -X, +0}};

    public static int[][] tindx = {{0, 4, 1}, {0, 9, 4}, {9, 5, 4}, {4, 5, 8}, {4, 8, 1}, {8, 10, 1}, {8, 3, 10},
            {5, 3, 8}, {5, 2, 3}, {2, 7, 3}, {7, 10, 3}, {7, 6, 10}, {7, 11, 6}, {11, 0, 6}, {0, 1, 6}, {6, 1, 10},
            {9, 0, 11}, {9, 11, 2}, {9, 2, 5}, {7, 2, 11}};
    private final List<Triangle> triangles = new ArrayList<>();

    public static double mod(double[] v) {
        return Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    }

    public void drawIcosahedron(int depth, float radius) {
        for (int i = 0; i < tindx.length; i++) {
            subdivide(
                    vdata[tindx[i][0]], //
                    vdata[tindx[i][1]], //
                    vdata[tindx[i][2]], depth, radius);
        }
    }

    private void drawTriangle(double[] vA0, double[] vB1, double[] vC2, float radius) {
        Triangle triangle = new Triangle(
                new Vector(vA0[0], vA0[1], vA0[2]).multiply(radius),
                new Vector(vB1[0], vB1[1], vB1[2]).multiply(radius),
                new Vector(vC2[0], vC2[1], vC2[2]).multiply(radius));
        triangles.add(triangle);
    }


    private void subdivide(double[] vA0, double[] vB1, double[] vC2, int depth, float radius) {

        double[] vAB = new double[3];
        double[] vBC = new double[3];
        double[] vCA = new double[3];

        int i;

        if (depth == 0) {
            drawTriangle(vA0, vB1, vC2, radius);
            return;
        }

        for (i = 0; i < 3; i++) {
            vAB[i] = (vA0[i] + vB1[i]) / 2;
            vBC[i] = (vB1[i] + vC2[i]) / 2;
            vCA[i] = (vC2[i] + vA0[i]) / 2;
        }

        double modAB = mod(vAB);
        double modBC = mod(vBC);
        double modCA = mod(vCA);

        for (i = 0; i < 3; i++) {
            vAB[i] /= modAB;
            vBC[i] /= modBC;
            vCA[i] /= modCA;
        }

        subdivide(vA0, vAB, vCA, depth - 1, radius);
        subdivide(vB1, vBC, vAB, depth - 1, radius);
        subdivide(vC2, vCA, vBC, depth - 1, radius);
        subdivide(vAB, vBC, vCA, depth - 1, radius);
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public static class Triangle {
        public Vector point1;
        public Vector point2;
        public Vector point3;

        public Triangle(Vector point1, Vector point2, Vector point3) {
            this.point1 = point1;
            this.point2 = point2;
            this.point3 = point3;
        }

    }
}
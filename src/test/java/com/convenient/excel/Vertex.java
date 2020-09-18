package com.convenient.excel;

import java.util.List;

class Vertex {
    //INFI NITY
    public static final int INFINITY = -1;
    public List<Vertex> adj; // Adjacency list
    public boolean known;
    public int dist = INFINITY; // DistType is probably int
    public Vertex path;// Other fields and methods as needed

    /**
     * 无权最短路径
     *
     * @param args
     */
    public static void main(String[] args) {
        Vertex vertex1 = new Vertex();
        Vertex vertex2 = new Vertex();
        vertex2.path = vertex1;
        Vertex vertex3 = new Vertex();
        vertex3.path = vertex2;
        Vertex vertex4 = new Vertex();
        Vertex vertex5 = new Vertex();
        Vertex vertex6 = new Vertex();
        Vertex vertex7 = new Vertex();
        vertex7.path = vertex4;
        vertex7.path = vertex5;
        vertex5.path = vertex6;
        vertex5.path = vertex3;
    }


}
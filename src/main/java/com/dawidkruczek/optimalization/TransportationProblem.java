package com.dawidkruczek.optimalization;

public class TransportationProblem {
    public static void main(String[] args) {
        Destination[] destinations = new Destination[2];
        destinations[0] = new Destination(250);
        destinations[1] = new Destination(350);

        Source[] sources = new Source[3];
        sources[0] = new Source(300);
        sources[1] = new Source(200);
        sources[2] = new Source(100);

        Shipment[][] shipments = new Shipment[3][2];
        shipments[0][0] = new Shipment(destinations[0], sources[0], 3);
        shipments[0][1] = new Shipment(destinations[1], sources[0], 2);
        shipments[1][0] = new Shipment(destinations[0], sources[1], 5);
        shipments[1][1] = new Shipment(destinations[1], sources[1], 1);
        shipments[2][0] = new Shipment(destinations[0], sources[2], 2);
        shipments[2][1] = new Shipment(destinations[1], sources[2], 4);

        showMatrix(shipments,sources,destinations);
        northWestCorner(sources, destinations);
    }

    private static void showMatrix(Shipment[][] shipments, Source[] sources, Destination[] destinations) {
        System.out.println("\\\tD1\tD2\tSUPPL");
        System.out.println("S1\t" + shipments[0][0].getCost() + "\t" + shipments[0][1].getCost() + "\t" + sources[0].getSupply());
        System.out.println("S2\t" + shipments[1][0].getCost() + "\t" + shipments[1][1].getCost() + "\t" + sources[1].getSupply());
        System.out.println("S3\t" + shipments[2][0].getCost() + "\t" + shipments[2][1].getCost() + "\t" + sources[2].getSupply());
        System.out.println("DEM\t" + destinations[0].getDemand() + "\t" + destinations[1].getDemand() + "\t" + "\\");
    }

    private static Shipment[][] northWestCorner(Source[] sources, Destination[] destinations) {
        Shipment[][] newShipments = new Shipment[3][2];
        int[][] matrix = new int[4][3];
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {

                if(i == 3 && j == 2) {
                    matrix[3][2] = sources[0].getSupply() + sources[1].getSupply() + sources[2].getSupply();
                }
                else if(j == 2 ) {
                    matrix[i][2] = sources[i].getSupply();
                }
                else if(i == 3) {
                    matrix[3][j] = destinations[j].getDemand();
                }
                else {
                    matrix[i][j] = 0;
                }
            }
        }

        for(int i = 0; i<4; i++)
        {
            for(int j =0; j<3; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }

        for (int i = 0; i < 3; i++ ) {
            for(int j = 0; j < 2; j++) {
                if(matrix[i][2] == 0) {
                    continue;
                }
                else if(matrix[3][j] == 0) {
                    continue;
                }
                else {
                    int min = Math.min(matrix[i][2],matrix[3][j]);
                    matrix[i][j] = min;
                    matrix[i][2] -= min;
                    matrix[3][j] -= min;
                }
            }
        }

        for(int i = 0; i<4; i++)
        {
            for(int j =0; j<3; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }

        return newShipments;
    }
}

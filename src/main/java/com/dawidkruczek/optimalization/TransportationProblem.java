package com.dawidkruczek.optimalization;

import java.util.Arrays;

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
        // 1. rozlozenie wartosci
        // 2. sprawdzenie czy m + n - 1 = obsadzonym komorkom

        int matrix[][] = setMatrix(destinations, sources);
        northWestCorner(matrix, shipments);
        showMatrix(shipments,sources,destinations);
        /*while (checkAmountOfAllocatedCells(shipments, 3, 2)) {
            // 3. jesli nie to trzeba poprawic
            unfoldShipments(shipments);
        }*/

            //metoda UV - optymalizacja
        uvMethodOptimization(shipments);



    }

    private static void uvMethodOptimization(Shipment[][] shipments) {
        int[] u = {-50, -50, -50};
        u[0] = 0;
        int[] v = {-50, -50};
        int[] p = new int[2];

        for(int i =0; i < 3 ; i++) {
            for( int j = 0; j < 2; j++) {
                if(shipments[i][j].getCellDemand() != 0) {
                    if(u[i] == -50) {
                        u[i] = shipments[i][j].getCost() - v[j];
                    }

                    if(v[j] == -50) {
                        v[j] = shipments[i][j].getCost() - u[i];
                    }
                }
            }
        }

        for(int i =0; i < 3 ; i++) {
            for( int j = 0; j < 2; j++) {
                if(shipments[i][j].getCellDemand() == 0) {
                    p[j] = u[i] + v[j] - shipments[i][j].getCost();
                }
            }
        }

        // calkowity przychod
        // calkowity koszty
        // calkowity zysk
        // zyski jednostkowe na danej trasie

        System.out.println("u -> " + Arrays.toString(u));
        System.out.println("v -> " + Arrays.toString(v));
        System.out.println("p -> " + Arrays.toString(p));
    }

    private static void unfoldShipments(Shipment[][] shipments) {
    }

    private static boolean checkAmountOfAllocatedCells(Shipment[][] shipments, int s, int d) {
        int allocatedCells = 0;
        for (Shipment[] shipment : shipments) {
            for (Shipment shipment1 : shipment) {
                if(shipment1.getCellDemand() != 0)
                    allocatedCells++;
            }
        }

        return s + d - 1 == allocatedCells;

    }

    private static int[][] setMatrix(Destination[] destinations, Source[] sources) {
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

        return matrix;
    }

    private static void showMatrix(Shipment[][] shipments, Source[] sources, Destination[] destinations) {
        System.out.println("\\\tD1\tD2\tSUPPL");
        System.out.println("S1\t" + shipments[0][0].getCellDemand() + "\t" + shipments[0][1].getCellDemand() + "\t" + sources[0].getSupply());
        System.out.println("S2\t" + shipments[1][0].getCellDemand() + "\t" + shipments[1][1].getCellDemand() + "\t" + sources[1].getSupply());
        System.out.println("S3\t" + shipments[2][0].getCellDemand() + "\t" + shipments[2][1].getCellDemand() + "\t" + sources[2].getSupply());
        System.out.println("DEM\t" + destinations[0].getDemand() + "\t" + destinations[1].getDemand() + "\t" + "\\");
    }

    private static void northWestCorner(int[][] matrix, Shipment[][] shipments) {
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
                    shipments[i][j].setCellDemand(min);
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

    }
}

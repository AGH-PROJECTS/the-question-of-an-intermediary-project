package com.dawidkruczek.optimalization;

public class Shipment {
    private Destination destination;
    private Source source;
    private int cost;
    private int cellDemand;

    public Shipment(Destination destination, Source source, int cost) {
        this.destination = destination;
        this.source = source;
        this.cost = cost;
    }

    public Destination getDestination() {
        return destination;
    }

    public Source getSource() {
        return source;
    }

    public int getCost() {
        return cost;
    }

    public int getCellDemand() {
        return cellDemand;
    }
}

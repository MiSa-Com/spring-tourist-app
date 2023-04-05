package com.ms.tourist_app.application.service.tsp;

import com.google.maps.model.TravelMode;
import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.service.dijkstra.Dijkstra;
import com.ms.tourist_app.domain.entity.Address;

import java.util.List;

public class CompleteGraph implements Graph {

    int nbVertices;
    double[][] cost;
    double minCost;

    public CompleteGraph(List<Address> listAddress, TravelMode mode, boolean isDuration) {
        Double[][] timeInMinutes = Dijkstra.calculateTimeInMinutes(listAddress, mode, isDuration);
        this.nbVertices = timeInMinutes.length;
        if (this.nbVertices > AppConst.TSP.MAX_COST) {
            throw new IllegalArgumentException("Too many vertices: " + this.nbVertices);
        }
        this.cost = new double[this.nbVertices][this.nbVertices];
        this.minCost = Double.MAX_VALUE;

        for (int i = 0 ; i < this.nbVertices ; i++) {
            for (int j = 0 ; j < this.nbVertices ; j++) {
                cost[i][j] = timeInMinutes[i][j];
                if (cost[i][j] != 0 && minCost > cost[i][j]) {
                    minCost = cost[i][j];
                }
            }
        }

    }

    @Override
    public int getNbVertices() {
        return nbVertices;
    }

    @Override
    public double getCost(int i, int j) {
        if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
            return -1;
        return cost[i][j];
    }

    @Override
    public boolean isArc(int i, int j) {
        if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
            return false;
        return i != j;
    }

    @Override
    public double[][] getCostTable() {
        return cost;
    }

    @Override
    public double getMinCost() {
        return this.minCost;
    }
}

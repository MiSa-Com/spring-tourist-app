package com.ms.tourist_app.application.utils.tsp;

import com.google.maps.model.TravelMode;
import com.ms.tourist_app.application.constants.AppConst;
import com.ms.tourist_app.application.utils.dijkstra.Dijkstra;
import com.ms.tourist_app.domain.entity.Address;

import java.util.List;

public class CompleteGraph implements Graph {

    int nbVertices;
    double[][] cost;
    double[][] distance;
    double minCost;

    public CompleteGraph(List<Address> listAddress, TravelMode mode) {
        double[][][] timeInMinutes = Dijkstra.calculateTimeInMinutes(listAddress, mode);
        this.nbVertices = timeInMinutes.length;
        if (this.nbVertices > AppConst.TSP.MAX_COST) {
            throw new IllegalArgumentException("Too many vertices: " + this.nbVertices);
        }
        this.cost = new double[this.nbVertices][this.nbVertices];
        this.distance = new double[this.nbVertices][this.nbVertices];
        this.minCost = Double.MAX_VALUE;

        for (int i = 0 ; i < this.nbVertices ; i++) {
            for (int j = 0 ; j < this.nbVertices ; j++) {
                cost[i][j] = timeInMinutes[i][j][1];
                distance[i][j] = timeInMinutes[i][j][0];
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
    public double getCost(int i, int j, boolean isDistance) {
        if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
            return -1;
        if (!isDistance) {
            return cost[i][j];
        } else {
            return distance[i][j];
        }
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

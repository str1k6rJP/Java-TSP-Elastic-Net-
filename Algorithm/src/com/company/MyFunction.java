package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;

public class MyFunction {
    public City[] cities;
    public City[] normalizedCities;

    public Node[] nodes;
    public boolean[] isImmutable;

    public double maxK;
    public double minK;
    public double scaleK;

    public int citiesQuantity;
    public double coefficient;

    int currentIter,
            maxIter;

    int maxRadius;
    int minRadius;

    int actualRadius;

    double quazioptimalPath = 0;

    double minX, minY, maxX, maxY, rangeX, rangeY = 0;
    double[] normalixedXesForCities;
    double[] normalixedYsForCities;


    public MyFunction(int citiesQuantity, File fileWithCities, int maxIteration, int nodesPerCityCoefficient
            , int maxRadius,int minRadius,double maxScale) {
        this.citiesQuantity = citiesQuantity;

        coefficient = nodesPerCityCoefficient;

        cities = new City[citiesQuantity];
        setCitiesFromFile(fileWithCities);

        nodes = new Node[(int) (citiesQuantity * coefficient)];
        setStartPositionForNodes(0.25, new Node(0.5, 0.5));

        isImmutable = new boolean[nodes.length];
        for (int i = 0; i < isImmutable.length; i++) {
            isImmutable[i] = false;
        }

        normalixedXesForCities = new double[cities.length];
        normalixedYsForCities = new double[cities.length];

        this.maxRadius = maxRadius;
        this.minRadius = minRadius;
        adaptLearningParams();

        maxK = maxScale;
        minK = 0.0001;
        scaleK = maxK;


        this.maxIter = maxIteration;
        currentIter = 1;

        setMinsAndMaxs(cities);
        setrangesXAndY();

        setNormalixedXesAndYsForCities();

        normalizedCities = new City[citiesQuantity];
        for (int i = 0; i < normalizedCities.length; i++) {
            normalizedCities[i] = new City(normalixedXesForCities[i], normalixedYsForCities[i]);
        }
    }

/*
    public MyFunction(int citiesQuantity, int maxIteration, int nodesPerCityCoefficient, int maxRadius,int minRadius) {
        this.citiesQuantity = citiesQuantity;

        coefficient = nodesPerCityCoefficient;

        cities = new City[citiesQuantity];


        nodes = new Node[(int) (citiesQuantity * coefficient)];
        setStartPositionForNodes(0.25, new Node(0.5, 0.5));

        isImmutable = new boolean[nodes.length];
        for (int i = 0; i < isImmutable.length; i++) {
            isImmutable[i] = false;
        }

        normalixedXesForCities = new double[cities.length];
        normalixedYsForCities = new double[cities.length];

        this.maxRadius = maxRadius;
        this.minRadius = minRadius;
        adaptLearningParams();

        maxK = 0.7;
        minK = 0.0001;
        scaleK = maxK;


        this.maxIter = maxIteration;
        currentIter = 1;

        setMinsAndMaxs(cities);
        setrangesXAndY();

        setNormalixedXesAndYsForCities();

        normalizedCities = new City[citiesQuantity];
        for (int i = 0; i < normalizedCities.length; i++) {
            normalizedCities[i] = new City(normalixedXesForCities[i], normalixedYsForCities[i]);
        }
    }
*/

    public double calculateEuclideanDistance(Pointable vertex, Pointable city) // Calculates the distance between
    //two nodes(i-th city and j-th vertice in particular)
    {
        double returner = (Math.sqrt(Math.pow(vertex.getX() - city.getX(), 2) + Math.pow(vertex.getY() - city.getY(), 2)));
        return returner;
    }

    public void setStartPositionForNodes(double radius, Pointable circleCenter) // This method sets all the path-creating nodes in starting circle shape
    {
        double step = 2 * Math.PI / nodes.length;
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(0, 0, citiesQuantity);
            nodes[i].setX(circleCenter.getX() + Math.cos(step * i) * radius);
            nodes[i].setY(circleCenter.getY() + Math.sin(step * i) * radius);
/*
            Vertices[i].X += (float)Math.Cos(step * i) * radius;
            Vertices[i].X += (float)Math.Sin(step * i) * radius;
*/
        }
    }

    public void setCitiesFromFile(File file) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            for (int i = 0; i < citiesQuantity; i++) {
                String temporal = bufferedReader.readLine();
                cities[i] = new City(0, 0);
                String[] params = temporal.trim().split("\\s+");
                try {
                    cities[i].setX(Double.parseDouble(params[0]));
                } catch (Exception e) {
                    cities[i].setX(Integer.parseInt(params[0]));
                }
                try {
                    cities[i].setY(Double.parseDouble(params[1]));
                } catch (Exception e) {
                    cities[i].setY(Integer.parseInt(params[1]));
                }

            }
        } catch (Exception e) {

        }
    }

    public double pointPriority(Pointable start, Pointable end) {
        return ((start.getX() - end.getX()) + (start.getY() - end.getY()));
    }

    public int getBestNodeIndex(int cityIndex) {
            int bestIndex = 0;
            double temporalPathVariable = 0;
            for (int i = 0; i < nodes.length; i++
            ) {
                if (!(isImmutable[i])) {
                    temporalPathVariable = calculateEuclideanDistance(nodes[i], normalizedCities[cityIndex]);
                    if (temporalPathVariable < normalizedCities[cityIndex].distanceToNearestNode) {
                        normalizedCities[cityIndex].setDistanceToNearestNode(temporalPathVariable);
                        normalizedCities[cityIndex].nearestNodeIndex = i;
                        bestIndex = i;
                    }
                }
            }
            return bestIndex;
    }

    int tempScaledDifX;
    int tempScaledDifY;

    public void adaptWinnerNode(int indexOfCity, City[] destination) {
        double difX = (destination[indexOfCity].getX() - nodes[normalizedCities[indexOfCity].nearestNodeIndex].getX());
        double scaledDifX = difX * scaleK;

        double difY = (destination[indexOfCity].getY() - nodes[normalizedCities[indexOfCity].nearestNodeIndex].getY());
        double scaledDifY = difY * scaleK;

        double setToX = nodes[normalizedCities[indexOfCity].nearestNodeIndex].getX() + scaledDifX;
        double setToY = nodes[normalizedCities[indexOfCity].nearestNodeIndex].getY() + scaledDifY;

        nodes[normalizedCities[indexOfCity].nearestNodeIndex].setX(setToX);
        nodes[normalizedCities[indexOfCity].nearestNodeIndex].setY(setToY);

        adaptLeftNeighboursForTheWinnerNode(indexOfCity);
        adaptRightNeighboursForTheWinnerNode(indexOfCity);
    }


    public void adaptLearningParams() {
        scaleK = maxK * Math.exp(((double) currentIter / (double) maxIter) * Math.log(minK / maxK));
        double fraction = (double) minRadius / (double) maxRadius;
        double log = Math.log(fraction);
        double exp = Math.exp(((double) currentIter / (double) maxIter) * log);
        int tmp = (int) (maxRadius * exp) / 2;
//        if (tmp<actualRadius){
        actualRadius = tmp;
//        }
    }

    public void adaptRightNeighboursForTheWinnerNode(int cityIndex) {
        for (int i = 1; i < actualRadius; i++) {
            int neighbourNodeIndex = normalizedCities[cityIndex].nearestNodeIndex + i;
            if (neighbourNodeIndex >= nodes.length) {
                neighbourNodeIndex = neighbourNodeIndex - nodes.length;
            }
            if (!(isImmutable[neighbourNodeIndex])) {
                double gaussian = Math.exp((-Math.pow(i, 2)) / (2 * Math.pow(actualRadius, 2)));

                double difX = normalizedCities[cityIndex].getX() - nodes[neighbourNodeIndex].getX();
                double scaledDifX = difX * scaleK;
                double gaussianMX = scaledDifX * gaussian;

                double difY = normalizedCities[cityIndex].getY() - nodes[neighbourNodeIndex].getY();
                double scaledDifY = difY * scaleK;
                double gaussianMY = scaledDifY * gaussian;

                double setToX = nodes[neighbourNodeIndex].getX() + gaussianMX;
                double setToY = nodes[neighbourNodeIndex].getY() + gaussianMY;

                nodes[neighbourNodeIndex].setX(setToX);
                nodes[neighbourNodeIndex].setY(setToY);
            }

        }
    }

    public void adaptLeftNeighboursForTheWinnerNode(int cityIndex) {
        for (int i = 1; i < actualRadius; i++) {
            int neighbourNodeIndex = normalizedCities[cityIndex].nearestNodeIndex - i;
            if (neighbourNodeIndex < 0) {
                neighbourNodeIndex = neighbourNodeIndex + nodes.length;
            }

            if (!(isImmutable[i])) {
                double gaussian = Math.exp((-Math.pow(i, 2)) / (2 * Math.pow(actualRadius, 2)));

                double difX = normalizedCities[cityIndex].getX() - nodes[neighbourNodeIndex].getX();

                double scaledDifX = difX * scaleK;
                double gaussianMX = scaledDifX * gaussian;

                double difY = normalizedCities[cityIndex].getY() - nodes[neighbourNodeIndex].getY();
                double scaledDifY = difY * scaleK;
                double gaussianMY = scaledDifY * gaussian;

                double setToX = nodes[neighbourNodeIndex].getX() + gaussianMX;
                double setToY = nodes[neighbourNodeIndex].getY() + gaussianMY;

                nodes[neighbourNodeIndex].setX(setToX);
                nodes[neighbourNodeIndex].setY(setToY);
            }
        }
    }

    public void updateCityParams(int cityIndex, int bestNodeForCityIndex) {
        cities[cityIndex].nearestNodeIndex = bestNodeForCityIndex;
        cities[cityIndex].distanceToNearestNode = calculateEuclideanDistance(nodes[bestNodeForCityIndex], cities[cityIndex]);
    }


    public boolean algorithmCloseCondition() {
        return ifIterationsOver() || ifSolutionCloseEnough();
    }

    public boolean ifIterationsOver() {
        return currentIter >= maxIter;
    }

    public boolean ifSolutionCloseEnough() {
        for (int i = 0; i < normalizedCities.length; i++) {
            if (Math.abs(normalizedCities[i].distanceToNearestNode) > 2*minK) {
                return false;
            }
        }
        return true;
    }

    public void checkNodesForImmutability() {
        for (int i = 0; i < normalizedCities.length; i++) {
            boolean isNearest = false;
            for (int j = 0;j<normalizedCities.length;j++){
                if (i == normalizedCities[j].nearestNodeIndex){
                    isNearest = true;
                    break;
                }
            }
            if ((Math.abs(normalizedCities[i].distanceToNearestNode) <= 2*minK)&&(isNearest)) {
                isImmutable[i] = true;
            } else {isImmutable[i]=false;}
        }
    }


    public void sortCitiesByWinnerNode(City[] cities1) {
        Arrays.sort(cities1, (City a, City b) -> a.nearestNodeIndex - b.nearestNodeIndex);
    }

    public double getCurrentSolutionPathLength(City[] sortedCities) {
        StringBuilder path = new StringBuilder();
        double temp = 0;
        sortCitiesByWinnerNode(sortedCities);
        path.append(sortedCities[0] + " ->");
        temp += calculateEuclideanDistance(sortedCities[0], sortedCities[sortedCities.length - 1]);
        for (int i = 1; i < sortedCities.length; i++) {
            temp += calculateEuclideanDistance(sortedCities[i - 1], sortedCities[i]);
            path.append(sortedCities[i] + " -> ");
        }
        path.toString();
        quazioptimalPath = temp;
        return temp;
    }

    public double computeNormalizedRingLength(Node[] nods) {
        double ringLength = calculateEuclideanDistance(nods[0], nods[nods.length - 1]);
        for (int i = 1; i < nods.length; i++) {
            ringLength += calculateEuclideanDistance(nods[i], nods[i - 1]);
        }
        return ringLength;
    }

    public void executeOnAlgorithmSolution() {
        sortCitiesByWinnerNode(normalizedCities);
        System.out.println("Nodes ring length is  " + computeNormalizedRingLength(nodes));
        double solutionPath = getCurrentSolutionPathLength(normalizedCities);
        System.out.println(solutionPath);
    }


    //functions for normalization of parameters

    public double[] getXes(Pointable[] points) {
        double[] temp = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            temp[i] = points[i].getX();
        }
        return temp;
    }

    public double[] getYs(Pointable[] points) {
        double[] temp = new double[points.length];
        for (int i = 0; i < cities.length; i++) {
            temp[i] = points[i].getY();
        }
        return temp;
    }

    public void setMinsAndMaxs(City[] cs) {
        double[] iterSortedXes = getXes(cs);
        Arrays.sort(iterSortedXes);
        double[] iterSortedYs = getYs(cs);
        Arrays.sort(iterSortedYs);
        minX = iterSortedXes[0];
        maxX = iterSortedXes[iterSortedXes.length - 1];
        minY = iterSortedYs[0];
        maxY = iterSortedYs[iterSortedYs.length - 1];

    }

    public void setrangesXAndY() {
        rangeX = maxX - minX;
        rangeY = maxY - minY;
    }

    public void setNormalixedXesAndYsForCities() {
        normalixedXesForCities = new double[citiesQuantity];
        normalixedYsForCities = new double[citiesQuantity];
        for (int i = 0; i < cities.length; i++) {
            normalixedXesForCities[i] = (cities[i].getX() - minX) / rangeX;
            normalixedYsForCities[i] = (cities[i].getY() - minY) / rangeY;
        }
    }



    public void makeOperationForSingleNormalizedCity(int indexOfCity) {
        getBestNodeIndex(indexOfCity);
        //updateCityParams(indexOfCity, normalizedCities[indexOfCity].nearestNodeIndex);
        adaptWinnerNode(indexOfCity, normalizedCities);

        adaptLeftNeighboursForTheWinnerNode(indexOfCity);
        adaptRightNeighboursForTheWinnerNode(indexOfCity);

        adaptLearningParams();

        System.out.println("City " + indexOfCity + " :  " + normalizedCities[indexOfCity].getX() + ";" + normalizedCities[indexOfCity].getY()
                + ";Winnernode " + normalizedCities[indexOfCity].nearestNodeIndex);
    }
}

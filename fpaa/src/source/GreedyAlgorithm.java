package source;

import java.util.Arrays;

public class GreedyAlgorithm {
	private int[] mileage;
	private int[] routes;
	
	public GreedyAlgorithm(int[] mileage, int numberOfTrucks) {
		this.mileage = mileage;
		this.routes = new int[numberOfTrucks];
	}
	
	public void distributeRoutesWithGreedy() {
		Arrays.sort(this.mileage);
		int currentTruck = 0;
		
		for(int i = 0; i < this.mileage.length; i++) {
			if(currentTruck < this.routes.length) {
				if(this.mileage[i] + this.routes[currentTruck] <= totalMileage()) {
					routes[currentTruck] += this.mileage[i];
				} else {
					this.routes[currentTruck] = totalMileage() - this.routes[currentTruck];
				}
				
				if(this.routes[currentTruck] == 0)
					currentTruck++;
			} else {
				break;
			}
		}
	}
	
	public int totalMileage() {
        int total = 0;
        for (int routes : this.routes) {
            total += routes;
        }
        return total;
    }

    public int[] getRoutes() {
        return this.routes;
    }
}

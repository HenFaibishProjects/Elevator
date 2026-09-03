package domain;

import java.util.SortedSet;
import java.util.TreeSet;

public class Elevator {

    private final String elevatorId;
    private int currentFloor;
    private ElevatorState state;
    private final SortedSet<Integer> pendingStops = new TreeSet<>();

    public Elevator(
            String elevatorId,
            int currentFloor,
            ElevatorState state) {

        this.elevatorId = elevatorId;
        this.currentFloor = currentFloor;
        this.state = state;
    }

    public void addStop(int floor) {
        pendingStops.add(floor);
    }

    public String elevatorId() {
        return elevatorId;
    }

    public int currentFloor() {
        return currentFloor;
    }

    public ElevatorState state() {
        return state;
    }

    public SortedSet<Integer> pendingStops() {
        return pendingStops;
    }
}
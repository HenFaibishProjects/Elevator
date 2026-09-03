package service;

import dispatcher.ElevatorDispatcher;
import domain.Elevator;
import domain.ElevatorRequest;

import java.util.List;

public class ElevatorSystem {

    private final List<Elevator> elevators;
    private final ElevatorDispatcher dispatcher;

    public ElevatorSystem(
            List<Elevator> elevators,
            ElevatorDispatcher dispatcher) {

        this.elevators = elevators;
        this.dispatcher = dispatcher;
    }

    public Elevator requestElevator(ElevatorRequest request) {
        Elevator elevator = dispatcher.selectElevator(elevators, request);
        elevator.addStop(request.floor());
        return elevator;
    }
}
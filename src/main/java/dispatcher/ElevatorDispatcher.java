package dispatcher;

import domain.Elevator;
import domain.ElevatorRequest;

import java.util.List;

public interface ElevatorDispatcher {

    Elevator selectElevator(List<Elevator> elevators, ElevatorRequest request);
}
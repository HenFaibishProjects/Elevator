package dispatcher;

import domain.Direction;
import domain.Elevator;
import domain.ElevatorRequest;
import domain.ElevatorState;

import java.util.Comparator;
import java.util.List;

public class SmartElevatorDispatcher implements ElevatorDispatcher {

    private static final int STOP_PENALTY = 3;
    private static final int MOVING_AWAY_PENALTY = 100;

    @Override
    public Elevator selectElevator(
            List<Elevator> elevators,
            ElevatorRequest request) {

        return elevators.stream()
                .filter(elevator ->
                        elevator.state() != ElevatorState.OUT_OF_SERVICE)
                .min(Comparator.comparingInt(
                        elevator -> score(elevator, request)))
                .orElseThrow(() ->
                        new IllegalStateException("No elevator available"));
    }

    private int score(Elevator elevator, ElevatorRequest request) {

        int distance =
                Math.abs(elevator.currentFloor() - request.floor());

        int stopsOnTheWay =
                countStopsOnTheWay(elevator, request.floor());

        int score =
                distance + (stopsOnTheWay * STOP_PENALTY);

        if (elevator.state() == ElevatorState.IDLE) {
            return score;
        }

        if (isOnTheWay(elevator, request)) {
            return score;
        }

        return MOVING_AWAY_PENALTY + score;
    }

    private boolean isOnTheWay(
            Elevator elevator,
            ElevatorRequest request) {

        return switch (elevator.state()) {

            case MOVING_UP ->
                    request.direction() == Direction.UP
                            && elevator.currentFloor() <= request.floor();

            case MOVING_DOWN ->
                    request.direction() == Direction.DOWN
                            && elevator.currentFloor() >= request.floor();

            default -> false;
        };
    }

    private int countStopsOnTheWay(
            Elevator elevator,
            int requestedFloor) {

        return (int) elevator.pendingStops()
                .stream()
                .filter(stop ->
                        isBetween(
                                stop,
                                elevator.currentFloor(),
                                requestedFloor))
                .count();
    }

    private boolean isBetween(
            int floor,
            int start,
            int end) {

        return floor >= Math.min(start, end)
                && floor <= Math.max(start, end);
    }
}
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MazeSolver {

    private static final int MOVE_COST = 1;
    private static Map<Double, List<Position>> visited = new HashMap<>();

    private MazeSolver() {
    }

    private static void drawPath(final Maze maze) {

        Position currentPosition = maze.getGoal();
        MazeCell currentCell = maze.getCell(currentPosition).getPrevious();
        while (!currentCell.getCellType().equals(CellType.START)) {
            currentCell.setCellInfo(CellInfo.PATH);
            currentCell = currentCell.getPrevious();
        }



//        while (!currentCell.getCellType().equals(CellType.START)) {
//            double targetCellCost = currentCell.getPathCost() - 1;
//
//            List<Position> positionsToCheck = visited.get(targetCellCost);
//            for (Position position : positionsToCheck) {
//                if (position.getDistance(currentPosition) == 1.0) {
//                    currentCell = maze.getCell(position);
//                    currentPosition = position;
//                    break;
//                }
//            }
//            currentCell.setCellInfo(CellInfo.PATH);
//        }
    }

    public static Maze solve(final Maze maze) {

        Position currentPosition = maze.getStart();
        MazeCell currentCell = maze.getCell(currentPosition);
        currentCell.setPathCost(0);

        Queue<Position> mazeCellsQueue = new LinkedList<>();
        mazeCellsQueue.add(currentPosition);

        while (!mazeCellsQueue.isEmpty() && !currentCell.getCellType().equals(CellType.GOAL)) {

            currentPosition = mazeCellsQueue.poll();
            currentCell = maze.getCell(currentPosition);
            currentCell.setCellInfo(CellInfo.VISITED);
//            addCellToVisited(currentCell.getPathCost(), currentPosition);

            List<Position> pathCandidates = maze.getPathCandidates(currentPosition);

            for (Position pos : pathCandidates) {
                MazeCell cell = maze.getCell(pos);
                if (!cell.getCellInfo().equals(CellInfo.VISITED)) {
                    cell.setPathCost(currentCell.getPathCost() + MOVE_COST);
                    cell.setPrevious(currentCell);
                }
            }
            mazeCellsQueue.addAll(pathCandidates);
        }
        if (currentCell.getCellType().equals(CellType.GOAL)) {
            drawPath(maze);
        }
        return maze;
    }

//    private static void addCellToVisited(Double pathcost, Position currentPosition) {
//        List<Position> visitedWithSameCost = visited.computeIfAbsent(pathcost, k -> new LinkedList<>());
//        visitedWithSameCost.add(currentPosition);
//    }
}

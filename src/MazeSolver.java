import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MazeSolver {

    private static final int MOVE_COST = 1;

    private MazeSolver() {
    }

    private static Maze drawPath(final Maze maze) {

        Position currentPosition = maze.getGoal();
        MazeCell currentCell = maze.getCell(currentPosition);

        while (!currentCell.getCellType().equals(CellType.START)) {
            final double targetCellCost = currentCell.getPathCost() - MOVE_COST;
            List<Position> pathCandidates = maze.getPathCandidates(currentPosition);

            for (Position pathCandidatePosition : pathCandidates) {
                 if(maze.getCell(pathCandidatePosition).getPathCost() == targetCellCost){
                     currentCell = maze.getCell(pathCandidatePosition);
                     currentPosition = pathCandidatePosition;
                     break;
                 }
            }
            currentCell.setCellInfo(CellInfo.PATH);
        }
        return maze;
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

            List<Position> movesCandidates = maze.getMoves(currentPosition);
            for (Position pos : movesCandidates) {
                MazeCell cell = maze.getCell(pos);
                if (!cell.getCellInfo().equals(CellInfo.VISITED)) {
                    cell.setPathCost(currentCell.getPathCost() + MOVE_COST);
                }
            }
            mazeCellsQueue.addAll(movesCandidates);
        }
        if (currentCell.getCellType().equals(CellType.GOAL)) {
            return drawPath(maze);
        }
        return maze;
    }
}

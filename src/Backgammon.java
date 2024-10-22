/**
 * Card game
 *
 * @author Zhao Mengqi
 * @version 1.00 2024/10/21
 */
import java.util.Random;
import java.util.Scanner;

public class Backgammon {
    private static final int NUM_POINTS = 24;
    private static final int TOTAL_CHECKERS = 15;
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    // 棋盘：正数表示玩家1的棋子，负数表示玩家2的棋子
    private int[] board = new int[NUM_POINTS];

    // 棋子剩余数：分别存储玩家1和玩家2剩余的棋子数
    private int[] checkers = {TOTAL_CHECKERS, TOTAL_CHECKERS};

    // 初始化棋盘
    public void initializeBoard() {
        // 示例: 玩家1在起点有两颗棋子，玩家2也在对角位置有两颗棋子
        board[0] = 2;   // 玩家1的起点
        board[23] = -2; // 玩家2的起点
    }

    // 掷骰子
    public int[] rollDice() {
        return new int[]{random.nextInt(6) + 1, random.nextInt(6) + 1};
    }

    // 显示棋盘
    public void displayBoard() {
        System.out.println("当前棋盘状态：");
        for (int i = 0; i < NUM_POINTS; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();
        for (int i = 0; i < NUM_POINTS; i++) {
            System.out.printf("%2d ", board[i]);
        }
        System.out.println();
    }

    // 棋子移动
    public boolean moveChecker(int player, int startPoint, int diceRoll) {
        int endPoint = player == 1 ? startPoint + diceRoll : startPoint - diceRoll;

        if (endPoint < 0 || endPoint >= NUM_POINTS) {
            System.out.println("无法移动，超出边界！");
            return false;
        }

        if (board[endPoint] == 0 || board[endPoint] * player > 0) {
            // 目标点没有棋子或是同一方棋子
            board[endPoint] += player;
            board[startPoint] -= player;
            return true;
        } else if (Math.abs(board[endPoint]) == 1) {
            // 可以吃掉对方的棋子
            System.out.println("吃掉对方的棋子！");
            board[endPoint] = player;
            board[startPoint] -= player;
            return true;
        } else {
            System.out.println("无法移动，目标点被对方占据！");
            return false;
        }
    }

    // 检查是否获胜
    public boolean checkWin(int player) {
        int homeStart = player == 1 ? 18 : 0;
        int homeEnd = player == 1 ? 24 : 6;

        for (int i = homeStart; i < homeEnd; i++) {
            if (board[i] * player > 0) {
                return false; // 还有棋子没有移出棋盘
            }
        }
        return true; // 所有棋子移出棋盘，获胜
    }

    public void playGame() {
        initializeBoard();

        int currentPlayer = 1;

        while (true) {
            displayBoard();
            System.out.println("玩家 " + currentPlayer + " 的回合。按Enter掷骰子...");
            scanner.nextLine();

            int[] diceRoll = rollDice();
            System.out.println("掷骰子结果：" + diceRoll[0] + " 和 " + diceRoll[1]);

            for (int i = 0; i < 2; i++) {
                System.out.println("玩家 " + currentPlayer + " 请选择移动的起始位置 (0-23):");
                int startPoint = scanner.nextInt();

                if (moveChecker(currentPlayer, startPoint, diceRoll[i])) {
                    if (checkWin(currentPlayer)) {
                        System.out.println("玩家 " + currentPlayer + " 获胜！");
                        return;
                    }
                }
            }
            currentPlayer = -currentPlayer; // 切换玩家
        }
    }

    public static void main(String[] args) {
        Backgammon game = new Backgammon();
        game.playGame();
    }
}

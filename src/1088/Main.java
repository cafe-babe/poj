import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/** 
 * poj1088 : <b>滑雪</b> <br/>
 * @author Ervin.zhang
 */
public class Main {
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;
    public Block currentBlock;
    public Block[][] blocks;
    public int maxLength = 0;
    public int length = 0;
    public Map<Block, Integer> map = new HashMap<Block, Integer>();
    public Block[] array;

    public boolean isMovable(Block b, int orientation) {
        if (blocks != null && b != null) {
            Block block = getNextBlock(b, orientation);
            if (block != null && block.altitude < b.altitude) {
                return true;
            }
        }
        return false;
    }

    public Block getNextBlock(Block b, int orientation) {
        int row = b.row;
        int col = b.col;
        switch (orientation) {
        case UP:
            row--;
            break;
        case RIGHT:
            col++;
            break;
        case DOWN:
            row++;
            break;
        case LEFT:
            col--;
            break;
        }
        if (!isOverflow(row, col)) {
            return blocks[row][col];
        }
        return null;
    }

    public boolean isOverflow(int row, int col) {
        if (row >= 0 && col >= 0 && row < blocks.length && col < blocks[0].length) {
            return false;
        }
        return true;
    }

    public void init() throws Exception {
        Scanner scan = new Scanner(System.in);

        int rowCount = scan.nextInt();
        int colCount = scan.nextInt();
        blocks = new Block[rowCount][colCount];
        array = new Block[rowCount * colCount];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                blocks[i][j] = new Block();
                blocks[i][j].altitude = scan.nextInt();
                blocks[i][j].row = i;
                blocks[i][j].col = j;
                array[i * colCount + j] = blocks[i][j];
            }
        }
        Arrays.sort(array);
        System.out.println(getMaxSize());
        scan.close();
    }
    
    public int getMaxSize() {
        int maxSize = 0;
        for (int i = 0; i < array.length; i++) {
            if (map.containsKey(array[i])) {
                break;
            }
            int subMaxSize = cal(array[i]);
            if (subMaxSize > maxSize) {
                maxSize = subMaxSize;
            }
        }
        return maxSize;
    }

    /**
     * 计算从当前位置到走完的最大步长
     * 
     * @param currentBlock
     * @return
     */
    public int cal(Block currentBlock) {
        if (map.containsKey(currentBlock)) {
            return map.get(currentBlock);
        }
        int subMaxSize = 1;
        for (int orientation = 1; orientation <= 4; orientation++) {
            if (isMovable(currentBlock, orientation)) {
                Block nextBlock = getNextBlock(currentBlock, orientation);
                int size = cal(nextBlock);
                if (size + 1 > subMaxSize) {
                    subMaxSize = size + 1;
                }
            }
        }
        map.put(currentBlock, subMaxSize);
        return subMaxSize;
    }

    public static void main(String[] args){
        Main ski = new Main();
        try {
            ski.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class Block implements Comparable<Block> {
        public int altitude = 0;
        public int row;
        public int col;

        @Override
        public int compareTo(Block o) {
            if (this.altitude >= o.altitude) {
                return 1;
            }
            return 0;
        }
    }
}

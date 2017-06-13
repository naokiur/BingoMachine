package jp.ne.naokiur.bingomachine.service;

import java.util.List;
import java.util.Random;

/**
 * Created by nao-ur on 2016/11/21.
 */
public class BingoNumber {
    private final int maxNumber;
    private final Random random;
    private final int number;

    /**
     * @param historyList already displayed numbers
     * @return Result view
     */
    public BingoNumber(List<Integer> historyList, Integer maxNumber) {
        this.maxNumber = maxNumber;
        this.random = new Random();

        Integer candidateNumber = createRandom();
        while (historyList.contains(candidateNumber) && historyList.size() < this.maxNumber) {
            candidateNumber = createRandom();

        }

        this.number = candidateNumber;
    }

    public int getNumber() {
        return number;
    }

    /**
     * random.nextInt will return 0 ~ maxNumber -1.
     * So, Need to add 1.
     *
     * @return (0 ~ maxNumber -1) + 1
     */
    private Integer createRandom() {
        return random.nextInt(maxNumber) + 1;
    }
}

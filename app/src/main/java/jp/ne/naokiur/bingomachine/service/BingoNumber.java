package jp.ne.naokiur.bingomachine.service;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by nao-ur on 2016/11/21.
 */
public class BingoNumber {
    //    public static final int MAX_BINGO_NUMBER = 50;
    public static final int MAX_BINGO_NUMBER = 10;
    private Random random;
    private int number;

    /**
     * @param historyList already displayed numbers
     * @return Result view
     */
    public BingoNumber(List<Integer> historyList) {
        this.random = new Random();

        Integer candidateNumber = createRandom();
        while (historyList.contains(candidateNumber) && historyList.size() < MAX_BINGO_NUMBER) {
            candidateNumber = createRandom();

        }

        this.number = candidateNumber;
    }

    public int getNumber() {
        return number;
    }

    public String createHistoryNumbers(List<Integer> historyNumberList) {
        if (historyNumberList.isEmpty()) {
            return "";
        }


        return StringUtils.join(historyNumberList.toArray(), ",");
    }

    private Integer createRandom() {
        return random.nextInt(MAX_BINGO_NUMBER) + 1;
    }
}

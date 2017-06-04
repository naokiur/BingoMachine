package jp.ne.naokiur.bingomachine.service;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by nao-ur on 2016/11/21.
 */
public class BingoNumber {
    private final int maxNumber;
    private Random random;
    private int number;

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

    public String createHistoryNumbers(List<Integer> historyNumberList) {
        if (historyNumberList.isEmpty()) {
            return "";
        }


        return StringUtils.join(historyNumberList.toArray(), ",");
    }

    private Integer createRandom() {
        return random.nextInt(maxNumber) + 1;
    }
}

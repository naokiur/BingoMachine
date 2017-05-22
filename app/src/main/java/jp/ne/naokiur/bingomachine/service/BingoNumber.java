package jp.ne.naokiur.bingomachine.service;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
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
     * @param viewText Split comma
     * @return Result view
     */
    public BingoNumber(String viewText) {
        this.random = new Random();

        String[] historyNumberArray = viewText.split(", ");
        List<Integer> historyNumberList = new ArrayList<>();
        for (int i = 0; i < historyNumberArray.length && !StringUtils.isBlank(historyNumberArray[i]); i++) {
            historyNumberList.add(Integer.valueOf(historyNumberArray[i]));
        }

        Integer candidateNumber = createRandom();
        while (historyNumberList.contains(candidateNumber) && historyNumberList.size() < MAX_BINGO_NUMBER) {
            candidateNumber = createRandom();

        }

        this.number = candidateNumber;
    }

    public int getNumber() {
        return number;
    }

    public String createHistoryNumbers(String viewText) {
        String[] historyNumberArray = viewText.split(", ");
        List<Integer> historyNumberList = new ArrayList<>();
        for (int i = 0; i < historyNumberArray.length && !StringUtils.isBlank(historyNumberArray[i]); i++) {
            historyNumberList.add(Integer.valueOf(historyNumberArray[i]));
        }

        if (historyNumberList.size() >= MAX_BINGO_NUMBER) {
            return "";
        }

        if (StringUtils.isEmpty(viewText)) {
            return String.valueOf(this.number);
        }


        return viewText + ", " + this.number;
    }

    private Integer createRandom() {
        return random.nextInt(MAX_BINGO_NUMBER) + 1;
    }
}

package jp.ne.naokiur.bingomachine.service;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by nao-ur on 2016/11/21.
 */
public class BingoNumberService {

    /**
     *
     *
     * @param viewText Split comma
     * @return Result view
     */
    public  String createHistoryNumbers(String viewText) {
        Random random = new Random();
        int rand = random.nextInt(49) + 1;
        System.out.println(rand);

        if (StringUtils.isEmpty(viewText)) {
            return String.valueOf(rand);
        }


        return String.valueOf(rand);
    }
    private int judgeNumber(List<Integer> currentNumbers) {
        return 0;
    }
}

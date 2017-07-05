package jp.ne.naokiur.bingomachine.service;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;

import jp.ne.naokiur.bingomachine.R;
import jp.ne.naokiur.bingomachine.common.exception.ValidateException;
import jp.ne.naokiur.bingomachine.service.dao.BingoGameDao;

/**
 * Created by nao-ur on 2017/06/04.
 */

public class BingoGame {
    private final String title;
    private final Integer maxNumber;
    private long gameId;
    private final BingoGameDao bingoGameDao;
    private final Context context;

    private static final int MAX_TITLE_SIZE = 100;
    private static final int MAX_BINGO_NUMBER = 75;

    public BingoGame(String title, Integer maxNumber, Context context) {
        this.title = title;
        this.maxNumber = maxNumber;
        this.context = context;
        this.bingoGameDao = new BingoGameDao(context);
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public long getGameId() {
        return gameId;
    }

    public void register() throws ValidateException {
        validate();
        this.gameId = bingoGameDao.insert(title, maxNumber);
    }

    private void validate() throws ValidateException {
        if (StringUtils.isBlank(this.title)) throw new ValidateException(context.getString(R.string.message_error_mandatory_title));

        if (this.title.length() > MAX_TITLE_SIZE) throw new ValidateException(context.getString(R.string.message_error_max_size_title));

        if (maxNumber < 1 || maxNumber > MAX_BINGO_NUMBER) {
            throw new ValidateException(context.getString(R.string.message_error_invalid_max_number));
        }
    }
}

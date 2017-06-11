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
    private String title;
    private Integer maxNumber;
    private long gameId;
    private BingoGameDao bingoGameDao;
    private Context context;

    public BingoGame(String title, Integer maxNumber, Context context) {
        this.title = title;
        this.maxNumber = maxNumber;
        this.context = context;
        this.bingoGameDao = new BingoGameDao(context);
    }

    public String getTitle() {
        return title;
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
        if (StringUtils.isBlank(this.title)) throw new ValidateException("Need to input title.");

        if (maxNumber < 1 || maxNumber > 75) {
            throw new ValidateException(context.getString(R.string.message_error_invalid_max_number));
        }
    }
}

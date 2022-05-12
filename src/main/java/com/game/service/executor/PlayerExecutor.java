package com.game.service.executor;

import com.game.entity.Player;
import com.game.exception.BadRequestException;

import java.util.Calendar;

public class PlayerExecutor {

    private Player player;

    public PlayerExecutor(Player player) {
        this.player = player;
    }

    public void executeCheck(CheckQuery checkQuery) {
        switch (checkQuery) {
            case BODY_NOT_NULL:
                checkBodyParamsNotNull();
                break;
            case NAME_NOT_EMPTY:
                checkNameNotEmpty();
                break;
            case NAME_LENGTH:
                checkNameLength();
                break;
            case TITLE_NOT_EMPTY:
                checkTitleNotEmpty();
                break;
            case TITLE_LENGTH:
                checkTitleLength();
                break;
            case EXPERIENCE_CORRECT:
                checkExperienceCorrect();
                break;
            case DATE_CORRECT:
                checkDateCorrect();
                break;
        }
    }

    public Integer executeCalculate(CalculateQuery calculateQuery) {
        if (calculateQuery == CalculateQuery.LEVEL) {
            return calculateLevel();
        }
        return calculateUntilLevel();
    }

    private void checkBodyParamsNotNull() {
        if (player.getName() == null
                || player.getTitle() == null
                || player.getRace() == null
                || player.getProfession() == null
                || player.getBirthday() == null
                || player.getExperience() == null
        )
            throw new BadRequestException();
    }

    private void checkNameLength() {
        if (player.getName() != null) {
            if (player.getName().length() > 12) {
                throw new BadRequestException();
            }
        }
    }

    private void checkTitleLength() {
        if (player.getTitle().length() > 30) {
            throw new BadRequestException();
        }
    }

    private void checkNameNotEmpty() {
        if (player.getName().isEmpty()) {
            throw new BadRequestException();
        }
    }

    private void checkTitleNotEmpty() {
        if (player.getTitle().isEmpty()) {
            throw new BadRequestException();
        }
    }

    private void checkExperienceCorrect() {
        if (player.getExperience() < 0 || player.getExperience() > 10_000_000) {
            throw new BadRequestException();
        }
    }

    private void checkDateCorrect() {
        if (player.getBirthday().getTime() < 0) {
            throw new BadRequestException();
        }
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        minDate.set(2000, Calendar.JANUARY, 1);
        maxDate.set(3000, Calendar.DECEMBER, 31);
        if (player.getBirthday().getTime() < minDate.getTimeInMillis()
                || player.getBirthday().getTime() > maxDate.getTimeInMillis()) {
            throw new BadRequestException();
        }
    }

    private Integer calculateLevel() {
        int experience = 0;
        if (player.getExperience() != null)
            experience = player.getExperience();
        return (int) (Math.sqrt(2500 + 200 * experience) - 50) / 100;
    }

    private Integer calculateUntilLevel() {
        Integer level = calculateLevel();
        int experience = 0;
        if (player.getExperience() != null)
            experience = player.getExperience();
        return 50 * (level + 1) * (level + 2) - experience;
    }
}

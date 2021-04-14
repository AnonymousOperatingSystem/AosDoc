package com.go.basetool;

import java.util.HashMap;
import java.util.Map;

public enum APIResultCode {

    SUCCESS("0", "成功", "success"),
    FAILURE("1", "失败", "failure"),
    IMAGE_CHECK_CODE_NOT_RIGHT("18", "IMAGE_CHECK_CODE_NOT_RIGHT", "IMAGE_CHECK_CODE_NOT_RIGHT"),
    UNKNOWN_ERROR("3", "UNKNOWN_ERROR", "UNKNOWN_ERROR"),
    CHECK_CODE_ERROR("4", "CHECK_CODE_ERROR", "CHECK_CODE_ERROR"),
    EMAIL_USED("5", "EMAIL_USED", "EMAIL_USED"),
    NO_FREE_ACCOUNT_TODAY("6", "NO_FREE_ACCOUNT_TODAY", "NO_FREE_ACCOUNT_TODAY"),
    USER_NOT_LOGIN_WX("7", "USER_NOT_LOGIN_WX", "USER_NOT_LOGIN_WX"),
    LOGIN_WX_ERROR("8", "LOGIN_WX_ERROR", "LOGIN_WX_ERROR"),
    NOT_LOGIN("9", "NOT_LOGIN", "NOT_LOGIN"),
    PHONENUM_PASSWORD_NOT_RIGHT("10", "PHONENUM_PASSWORD_NOT_RIGHT", "PHONENUM_PASSWORD_NOT_RIGHT"),
    GAME_RUNNING_NOT_START_AGAIN("11", "GAME_RUNNING_NOT_START_AGAIN", "GAME_RUNNING_NOT_START_AGAIN"),
    GAME_NOT_START("12", "GAME_NOT_START", "GAME_NOT_START"),
    NO_USER_CAN_ANSWER_NOW("13", "NO_USER_CAN_ANSWER_NOW", "NO_USER_CAN_ANSWER_NOW"),
    ROLE_NOT_EXIST("14", "ROLE_NOT_EXIST", "ROLE_NOT_EXIST"),
    NO_PRIGILEGE("15", "NO_PRIGILEGE", "NO_PRIGILEGE"),
    PARAMETER_ERROR("16", "PARAMETER_ERROR", "PARAMETER_ERROR"),
    ASK_TYPE_NOT_EXIST("17", "ASK_TYPE_NOT_EXIST", "ASK_TYPE_NOT_EXIST"),
    USER_NOT_EXIST("18", "USER_NOT_EXIST", "USER_NOT_EXIST"),
    ASK_NOT_EXIST("19", "ASK_NOT_EXIST", "ASK_NOT_EXIST"),
    GAME_START_BY_ANOTHER_MANAGER("20", "GAME_START_BY_ANOTHER_MANAGER", "GAME_START_BY_ANOTHER_MANAGERx"),
    GAME_NOT_EXIST("21", "GAME_NOT_EXIST", "GAME_NOT_EXIST"),
    NO_ASKS_NOW("22", "NO_ASKS_NOW", "NO_ASKS_NOW"),
    USER_MEMBER_COUNT_NOT_RIGHT("23", "USER_MEMBER_COUNT_NOT_RIGHT", "USER_MEMBER_COUNT_NOT_RIGHT"),
    GAME_FINISHED("24", "GAME_FINISHED", "GAME_FINISHED"),
    NO_GAME_RUNNING("25", "NO_GAME_RUNNING", "NO_GAME_RUNNING"),
    GAME_NOT_FINISHED("26", "GAME_NOT_FINISHED", "GAME_NOT_FINISHED"),
    NO_MONTHS_ON_LINE("27", "没有这个月数的理财产品，请刷新重新选择", "no this product,choose again"),
    CANNOT_LESS_THAN_100("28", "数量不能小于最低要求抵押数", "cannot less than requested"),
    ORDER_TIME_NOT_ARRAIVED("29", "订单没有到期，不可赎回", "not arraive time,non-redeemable"),

    ;

    private static final Map<String, APIResultCode> interToEnum = new HashMap<String, APIResultCode>();

    static {
        for (APIResultCode type : APIResultCode.values()) {
            interToEnum.put(type.getCode(), type);
        }
    }

    private String result;
    private String message;
    private String englishMessage;

    private APIResultCode(String code, String message, String englishMessage) {
        this.result = code;
        this.message = message;
        this.englishMessage = englishMessage;
    }

    public static APIResultCode fromCode(String code) {
        return interToEnum.get(code);
    }

    public String getCode() {
        return result;
    }

    public void setCode(String code) {
        this.result = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEnglishMessage() {
        return englishMessage;
    }

    public void setEnglishMessage(String englishMessage) {
        this.englishMessage = englishMessage;
    }
}

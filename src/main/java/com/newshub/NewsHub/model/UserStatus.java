package com.newshub.NewsHub.model;

public enum UserStatus {
    ACTIVE,         //активный пользователь
    BLOCKED,        //заблокированный пользователь
    DELETED,        //пользователь удален
    TIME_DELETED    //временно удален, с возможностью восстановления
}
